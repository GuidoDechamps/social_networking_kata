package be.solid.social;

import java.time.*;

class MinuteIncrementClock extends Clock {
    private int sequenceNr = 0;
    private final Instant creationTime = Instant.now();

    @Override
    public ZoneId getZone() {
        return ZoneId.systemDefault();
    }

    @Override
    public Clock withZone(ZoneId zone) {
        return this;
    }

    @Override
    public Instant instant() {
        return creationTime.plusSeconds(60 * sequenceNr++);
    }
}
