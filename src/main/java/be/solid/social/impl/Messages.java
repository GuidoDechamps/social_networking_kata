package be.solid.social.impl;

import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import java.time.Clock;
import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

//TODO remove public
public class Messages implements PublishingService, ReaderService {
    private final ImmutableListMultimap.Builder<String, MessagePost> messages = ImmutableListMultimap.builder();
    private final ImmutableListMultimap.Builder<String, String> subscriptions = ImmutableListMultimap.builder();
    private final Clock clock;

    public Messages(Clock clock) {
        this.clock = clock;
    }

    @Override
    public void post(String sender, String content) {
        final MessagePost messagePost = createMessagePost(sender, content);
        messages.put(sender, messagePost);
    }

    @Override
    public void subscribe(String subscriber, String topic) {
        subscriptions.put(subscriber, topic);
    }

    @Override
    public List<Message> read(String user) {
        final ImmutableList<MessagePost> messagePosts = messages.build()
                                                                .get(user);
        return messagePosts.stream()
                           .map(this::map)
                           .collect(Collectors.toList());
    }

    @Override
    public List<Message> readWall(String user) {
        final ImmutableList<String> topics = subscriptions(user);
        final ImmutableList<String> merged = merge(user, topics);
        return aggregateTopicTimeLines(merged);
    }

    private MessagePost createMessagePost(String sender, String content) {
        return MessagePost.newBuilder()
                          .withUser(sender)
                          .withContent(content)
                          .withCreationTime(Instant.now(clock))
                          .build();
    }

    private Message map(MessagePost messagePost) {
        return Message.newBuilder()
                      .withContent(messagePost.content)
                      .withUser(messagePost.user)
                      .withTime(messagePost.creationTime)
                      .build();
    }

    private ImmutableList<String> merge(String user, ImmutableList<String> topics) {
        return ImmutableList.<String>builder().addAll(topics)
                                              .add(user)
                                              .build();
    }

    private List<Message> aggregateTopicTimeLines(ImmutableList<String> topics) {
        return topics.stream()
                     .map(this::read)
                     .flatMap(Collection::stream)
                     .sorted(Comparator.comparing(o -> o.time))
                     .collect(Collectors.toList());
    }

    private ImmutableList<String> subscriptions(String user) {
        return subscriptions.build()
                            .get(user);
    }
}
