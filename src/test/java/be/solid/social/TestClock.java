package be.solid.social;

import java.time.Instant;

public interface TestClock {
    Instant getPostTime(int i);
}
