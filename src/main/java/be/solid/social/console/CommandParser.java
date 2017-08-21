package be.solid.social.console;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

/**
 * User submits commands to the application:
 * - Command posting: \<user name> -> \<message>
 * - Command reading: \<user name>
 * - Command following: \<user name> follows \<another user>
 * - Command wall: \<user name> wall
 */
public class CommandParser {
    public static final String ARROW = "->";
    public static final String SPACE = " ";
    static final String WALL = "wall";

    public static Optional<Command> parseCommand(String line) {
        if (isInvalidInput(line)) return Optional.empty();

        final List<String> tokens = splitIntoTokens(line);
        if (hasSingleToken(tokens)) return Optional.of(buildViewTimeLine(line));
        if (hasTwoTokens(line)) {
            if (tokens.get(1)
                      .equalsIgnoreCase(WALL)) return Optional.of(buildViewTimeLine(line));
        }


        if (commandIsSeparatedByArrow(tokens)) {
            return Optional.of(Posting.newBuilder()
                                      .withActor(tokens.get(0))
                                      .withContent(tokens.get(1))
                                      .build());
        }
        return Optional.empty();
    }

    private static boolean hasSingleToken(List<String> tokens) {
        return tokens.size() == 1;
    }

    private static List<String> splitIntoTokens(String line) {
        final String[] tokens = line.split(SPACE);
        return Lists.newArrayList(tokens);
    }

    private static boolean isInvalidInput(String line) {
        return line.trim()
                   .isEmpty() || line.startsWith(ARROW);
    }

    private static boolean hasTwoTokens(String line) {
        return line.split(SPACE).length == 2;
    }

    private static ViewTimeLine buildViewTimeLine(String line) {
        return ViewTimeLine.newBuilder()
                           .withUser(line.trim())
                           .build();
    }

    private static boolean commandIsSeparatedByArrow(List<String> tokens) {

        return tokens.get(1)
                     .equalsIgnoreCase(ARROW) && tokens.size() > 2;
    }


}
