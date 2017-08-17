package be.solid.social;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

public class SecondIncrementClock extends Clock {
    private final int secondIncrement;
    private final Instant creationTime = Instant.now();
    private int sequenceNr = 0;

    SecondIncrementClock(int increment) {
        secondIncrement = increment;
    }

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
        return creationTime.plusSeconds(secondIncrement * sequenceNr++);
    }

    public Instant getFinalTime() {
        return creationTime.plusSeconds(secondIncrement * sequenceNr);
    }
}
