package be.solid.social.usecase;

import be.solid.social.domain.Message;

import java.util.List;

import static java.util.stream.Collectors.toList;

class EventMapper {
    private EventMapper() {
        //no instance
    }

    static List<Event> map(List<Message> messages) {
        return messages.stream()
                       .map(EventMapper::mapToEvent)
                       .collect(toList());
    }

    static Event mapToEvent(Message x) {
        return Event.newBuilder()
                    .withUser(x.user)
                    .withContent(x.content)
                    .withTime(x.time)
                    .build();
    }
}
