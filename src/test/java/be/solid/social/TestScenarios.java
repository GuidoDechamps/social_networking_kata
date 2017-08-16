package be.solid.social;

import com.google.common.collect.ImmutableList;

import java.util.List;

class TestScenarios {

    private static final String I_LOVE_THE_WEATHER_TODAY = "I love the weather today";
     static final String ALICE = "Alice";
     static final String BOB = "Bob";
     static final String CHARLIE = "Charlie";
    private static final String DAMN_WE_LOST = "Damn! We lost! ";
    private static final String GOOD_GAME_THOUGH = "Good game though.";
    private static final String COFFEE_ANYONE = "I'm in New York today! Anyone wants to have a coffee?";

    private TestScenarios() {
    }

    static ImmutableList<MessageInput> messagePosts() {
        return ImmutableList.<MessageInput>builder().add(new MessageInput(ALICE, I_LOVE_THE_WEATHER_TODAY))
                .add(new MessageInput(BOB, DAMN_WE_LOST))
                .add(new MessageInput(BOB, GOOD_GAME_THOUGH))
                .add(new MessageInput(CHARLIE, COFFEE_ANYONE))
                .build();
    }

    static List<String> senders(){
        return ImmutableList.of(ALICE,BOB,CHARLIE);
    }
}
