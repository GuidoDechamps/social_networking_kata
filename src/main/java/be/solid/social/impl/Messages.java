package be.solid.social.impl;

import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

//TODO remove public
public class Messages implements PublishingService, ReaderService {
    private final ImmutableListMultimap.Builder<String, Message> messages = ImmutableListMultimap.builder();
    private final ImmutableListMultimap.Builder<String, String> subscriptions = ImmutableListMultimap.builder();

    @Override
    public void publish(String sender, String content) {
        messages.put(sender, Message.create(sender, content));
    }

    @Override
    public void subscribe(String subscriber, String topic) {
        subscriptions.put(subscriber, topic);
    }

    @Override
    public List<Message> read(String receiver) {
        return messages.build()
                       .get(receiver);
    }

    @Override
    public List<Message> readWall(String user) {
        final ImmutableList<String> topics = subscriptions(user);
        final ImmutableList<String> merged = merge(user, topics);
        return aggregateTopicTimeLines(merged);
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
                     .collect(Collectors.toList());
    }

    private ImmutableList<String> subscriptions(String user) {
        return subscriptions.build()
                            .get(user);
    }
}
