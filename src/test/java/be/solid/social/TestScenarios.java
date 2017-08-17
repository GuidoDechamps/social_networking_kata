package be.solid.social;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

public class TestScenarios {
    static final String ARROW = "->";
    public static final String ALICE = "Alice";
    public static final String BOB = "Bob";
    public static final String CHARLIE = "Charlie";
    private static final String I_LOVE_THE_WEATHER_TODAY = "I love the weather today";
    private static final String DAMN_WE_LOST = "Damn! We lost! ";
    private static final String GOOD_GAME_THOUGH = "Good game though.";
    private static final String COFFEE_ANYONE = "I'm in New York today! Anyone wants to have a coffee?";
    private static final String SPACE = " ";

    private TestScenarios() {
    }

    public static List<String> commandLinePosts() {
        return messagePosts().stream()
                             .map(x -> (x.sender + SPACE + ARROW + SPACE + x.message))
                             .collect(Collectors.toList());
    }

    static List<MessageData> messagePosts() {
        return ImmutableList.<MessageData>builder().add(new MessageData(ALICE, I_LOVE_THE_WEATHER_TODAY))
                                                   .add(new MessageData(BOB, DAMN_WE_LOST))
                                                   .add(new MessageData(BOB, GOOD_GAME_THOUGH))
                                                   .add(new MessageData(CHARLIE, COFFEE_ANYONE))
                                                   .build();
    }

    public static List<String> senders() {
        return ImmutableList.of(ALICE, BOB, CHARLIE);
    }
}
