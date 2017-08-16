package be.solid.social.impl;

import be.solid.social.api.Message;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import com.google.common.collect.ImmutableListMultimap;

import java.util.List;
//TODO remove public
public class Messages implements PublishingService, ReaderService {
    private final ImmutableListMultimap.Builder<String, Message> builder = ImmutableListMultimap.builder();

    @Override
    public void publish(String sender, String content) {
        builder.put(sender, Message.create(sender, content));
    }

    @Override
    public List<Message> read(String receiver) {
        return builder.build()
                      .get(receiver);
    }

    @Override
    public List<Message> readWall(String user) {
        return builder.build()
                      .get(user);
    }
}
