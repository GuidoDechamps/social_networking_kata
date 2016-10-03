package be.solid.social.api;

import com.google.common.collect.ImmutableListMultimap;

import java.util.List;

public class Messages implements PublishingService, ReaderService {
    final ImmutableListMultimap.Builder<String, Message> b = ImmutableListMultimap.builder();

    @Override
    public void publish(String sender, String content) {
        b.put(sender, Message.create(sender, content));
    }

    @Override
    public List<Message> read(String receiver) {
        return b.build().get(receiver);
    }
}
