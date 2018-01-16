package be.solid.social;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.stream.Collectors;

import static be.solid.social.console.CommandTokens.*;

public class TestScenarios {
    static final String ALICE = "Alice";
    static final String BOB = "Bob";
    static final String CHARLIE = "Charlie";
    private static final String I_LOVE_THE_WEATHER_TODAY = "I love the weather today";
    private static final String DAMN_WE_LOST = "Damn! We lost!";
    private static final String GOOD_GAME_THOUGH = "Good game though.";
    private static final String COFFEE_ANYONE = "I'm in New York today! Anyone wants to have a coffee?";


    private TestScenarios() {
    }

    public static List<String> allCommandLines() {
        return ImmutableList.<String>builder().addAll(followsGivenFromCommandLine())
                                              .addAll(postsGivenFromCommandLine())
                                              .addAll(userList())
                                              .addAll(wallCommandFromCommandLine())
                                              .build();
    }

    public static List<String> postsGivenFromCommandLine() {
        return sequenceOfPosts().stream()
                                .map(x -> (x.sender + SPACE + ARROW + SPACE + x.message))
                                .collect(Collectors.toList());
    }

    public static List<String> followsGivenFromCommandLine() {
        return ImmutableList.<String>builder().add(ALICE + SPACE + FOLLOWS + SPACE + BOB)
                                              .add(ALICE + SPACE + FOLLOWS + SPACE + CHARLIE)
                                              .add(BOB + SPACE + FOLLOWS + SPACE + ALICE)
                                              .add(BOB + SPACE + FOLLOWS + SPACE + CHARLIE)
                                              .add(CHARLIE + SPACE + FOLLOWS + SPACE + ALICE)
                                              .add(CHARLIE + SPACE + FOLLOWS + SPACE + BOB)
                                              .build();
    }

    public static List<String> userList() {
        return ImmutableList.of(ALICE, BOB, CHARLIE);
    }

    public static List<String> withExitCommand(List<String> commands) {
        return ImmutableList.<String>builder().addAll(commands)
                                              .add(EXIT)
                                              .build();
    }

    public static List<String> wallCommandFromCommandLine() {
        return userList().stream()
                         .map(TestScenarios::commandWall)
                         .collect(Collectors.toList());
    }

    public static List<MessageData> sequenceOfPosts() {
        return ImmutableList.<MessageData>builder().add(new MessageData(1, ALICE, I_LOVE_THE_WEATHER_TODAY))
                                                   .add(new MessageData(2, BOB, DAMN_WE_LOST))
                                                   .add(new MessageData(3, BOB, GOOD_GAME_THOUGH))
                                                   .add(new MessageData(4, CHARLIE, COFFEE_ANYONE))
                                                   .build();
    }

    static List<MessageData> individualPosts() {
        return ImmutableList.<MessageData>builder().add(new MessageData(1, ALICE, I_LOVE_THE_WEATHER_TODAY))
                                                   .add(new MessageData(1, BOB, DAMN_WE_LOST))
                                                   .add(new MessageData(1, BOB, GOOD_GAME_THOUGH))
                                                   .add(new MessageData(1, CHARLIE, COFFEE_ANYONE))
                                                   .build();
    }

    private static String commandWall(String user) {
        return user + SPACE + WALL;
    }
}
