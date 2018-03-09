package be.solid.social.domain;

import java.util.ArrayList;
import java.util.List;

public class ReaderSpy implements ReaderService {
    private final List<String> reads = new ArrayList<>();
    private final List<String> readWall = new ArrayList<>();

    @Override
    public List<Message> read(String user) {
        reads.add(user);
        return List.of();
    }

    @Override
    public List<Message> readWall(String user) {
        readWall.add(user);
        return List.of();
    }

    public String getRequiredRead() {
        ensureOneRead();
        return this.reads.get(0);
    }

    public String getRequiredTopic() {
        ensureOneWall();
        return this.readWall.get(0);
    }

    private void ensureOneWall() {
        if (readWall.size() != 1) throw new ExactlyOneReadWallShouldHaveOccured(readWall.size());
    }

    private void ensureOneRead() {
        if (reads.size() != 1) throw new ExactlyOneReadShouldHaveOccured(reads.size());
    }

    private class ExactlyOneReadWallShouldHaveOccured extends RuntimeException {
        public ExactlyOneReadWallShouldHaveOccured(int nrOfReadWall) {
            super("Contained " + nrOfReadWall + " readwalls");
        }
    }

    private class ExactlyOneReadShouldHaveOccured extends RuntimeException {
        public ExactlyOneReadShouldHaveOccured(int nrOfSubscriptions) {
            super("Contained " + nrOfSubscriptions + " subscriptions");
        }
    }


}
