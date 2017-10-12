package be.solid.social.impl;

import be.solid.social.ManualClock;
import be.solid.social.domain.Message;
import be.solid.social.domain.ReaderService;

import java.util.List;

public class PrintMessagesDecorator implements ReaderService {
    private final ManualClock clock;
    private final ReaderService readerService;

    private PrintMessagesDecorator(ReaderService readerService, ManualClock clock) {
        this.clock = clock;
        this.readerService = readerService;
    }

    public static ReaderService decorate(ReaderService r, ManualClock clock) {
        return new PrintMessagesDecorator(r, clock);
    }

    @Override
    public List<Message> read(String user) {
        printStartRead(user);
        final List<Message> messages = readerService.read(user);
        printResult(messages);

        return messages;
    }

    @Override
    public List<Message> readWall(String user) {
        printStartWall(user);
        final List<Message> messages = readerService.readWall(user);
        printResult(messages);
        return messages;
    }

    private void printStartRead(String user) {
        System.out.println("Reading messages for " + user);
    }

    private void printStartWall(String user) {
        System.out.println("Reading messages from wall for " + user);
    }

    private void printResult(List<Message> messages) {
        messages.forEach(this::print);
        System.out.println("----------- ");
    }

    private void print(Message message) {
        final long timePassed = clock.getFinalTime()
                                     .getEpochSecond() - message.time.getEpochSecond();
        System.out.println(message.user + " : " + message.content + " ( " + timePassed + " seconds ago)");
    }
}
