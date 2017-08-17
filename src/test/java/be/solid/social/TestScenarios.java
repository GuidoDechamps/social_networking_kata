package be.solid.social;

import com.google.common.collect.ImmutableList;

import java.util.List;

class TestScenarios {

    static final String ALICE = "Alice";
    static final String BOB = "Bob";
    static final String CHARLIE = "Charlie";
    private static final String I_LOVE_THE_WEATHER_TODAY = "I love the weather today";
    private static final String DAMN_WE_LOST = "Damn! We lost! ";
    private static final String GOOD_GAME_THOUGH = "Good game though.";
    private static final String COFFEE_ANYONE = "I'm in New York today! Anyone wants to have a coffee?";

    private TestScenarios() {
    }

    static ImmutableList<MessageData> messagePosts() {
        return ImmutableList.<MessageData>builder().add(new MessageData(ALICE, I_LOVE_THE_WEATHER_TODAY))
                                                   .add(new MessageData(BOB, DAMN_WE_LOST))
                                                   .add(new MessageData(BOB, GOOD_GAME_THOUGH))
                                                   .add(new MessageData(CHARLIE, COFFEE_ANYONE))
                                                   .build();
    }

    static List<String> senders() {
        return ImmutableList.of(ALICE, BOB, CHARLIE);
    }
}
