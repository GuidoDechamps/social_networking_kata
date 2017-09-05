package be.solid.social.console;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

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
    public static final String FOLLOWS = "follow";
    static final String WALL = "wall";

    public static Optional<Command> parseCommand(String line) {
        if (isInvalidInput(line)) return empty();

        final List<String> tokens = splitIntoTokens(line);
        if (hasSingleToken(tokens)) return of(buildViewTimeLine(line));
        if (hasTwoTokens(line)) {
            if (tokens.get(1)
                      .equalsIgnoreCase(WALL)) return of(buildViewWall(tokens.get(0)));
        }


        if (commandIsSeparatedByArrow(tokens)) {
            final String[] arrowTokens = line.split(ARROW);
            return of(Posting.newBuilder()
                             .withActor(arrowTokens[0])
                             .withContent(arrowTokens[1])
                             .build());
        }
        if (commandIsSeparatedByFollows(tokens)) {
            final String[] arrowTokens = line.split(FOLLOWS);
            return of(Following.newBuilder()
                               .withUser(arrowTokens[0])
                               .withSubscriptionTopic(arrowTokens[1])
                               .build());
        }
        return empty();
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

    private static ViewWall buildViewWall(String line) {
        return ViewWall.newBuilder()
                       .withUser(line.trim())
                       .build();
    }

    private static boolean commandIsSeparatedByArrow(List<String> tokens) {
        return isSeparatedBy(tokens, ARROW);
    }

    private static boolean commandIsSeparatedByFollows(List<String> tokens) {
        return isSeparatedBy(tokens, FOLLOWS);
    }

    private static boolean isSeparatedBy(List<String> tokens, String separator) {
        return tokens.size() > 2 && tokens.get(1)
                                          .equalsIgnoreCase(separator);
    }


}
