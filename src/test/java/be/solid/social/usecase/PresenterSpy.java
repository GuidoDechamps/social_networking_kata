package be.solid.social.usecase;

import java.util.List;

import static java.util.Objects.requireNonNull;

public class PresenterSpy implements Presenter {
    private List<Event> allEvents = null;

    @Override
    public void allEvents(List<Event> events) {
        requireNonNull(events);
        if (allEvents != null)
            throw new AllEventsCalledMoreThenOnce();
        allEvents = events;
    }

    public List<Event> getAllEvents() {
        if (allEvents == null)
            throw new AllEventsNotCalledYet();
        return allEvents;
    }

    private class AllEventsCalledMoreThenOnce extends RuntimeException {
    }

    private class AllEventsNotCalledYet extends RuntimeException {
    }
}
