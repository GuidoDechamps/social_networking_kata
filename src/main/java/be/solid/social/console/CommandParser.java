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
    public static final String WALL = "wall";

    public static Optional<Command> parseCommand(String line) {
        if (isInvalidInput(line)) return empty();

        final Tokens tokens = new Tokens(line);
        if (tokens.hasSingleToken()) return of(buildViewTimeLine(line));
        if (tokens.hasTwoTokens()) {
            if (tokens.doesSecondTokenMatch(WALL)) {
                final String first = tokens.getFirst();
                return of(buildViewWall(first));
            }
        }


        if (tokens.isCommandSeparatedByArrow()) {
            final String[] arrowTokens = line.split(ARROW);
            return of(Posting.newBuilder()
                             .withActor(arrowTokens[0])
                             .withContent(arrowTokens[1])
                             .build());
        }
        if (tokens.isCommandSeparatedByFollows()) {
            final String[] arrowTokens = line.split(FOLLOWS);
            return of(Following.newBuilder()
                               .withUser(arrowTokens[0])
                               .withSubscriptionTopic(arrowTokens[1])
                               .build());
        }
        return empty();
    }

    private static boolean isInvalidInput(String line) {
        return line.trim()
                   .isEmpty() || line.startsWith(ARROW);
    }


    private static ViewTimeLine buildViewTimeLine(String user) {
        return ViewTimeLine.newBuilder()
                           .withUser(user.trim())
                           .build();
    }

    private static ViewWall buildViewWall(String user) {
        return ViewWall.newBuilder()
                       .withUser(user.trim())
                       .build();
    }


    private static class Tokens {

        private final List<String> tokens;


        private Tokens(String lineToParse) {
            this.tokens = splitIntoTokens(lineToParse);
        }

        public Optional<String> get(int i) {
            if (i < 0 || i > tokens.size()) return empty();
            else return of(tokens.get(i));
        }

        public String getFirst() {
            if (tokens.isEmpty()) throw new RuntimeException("No tokens available");
            else return tokens.get(0);
        }

        private static List<String> splitIntoTokens(String line) {
            final String[] tokens = line.split(SPACE);
            return Lists.newArrayList(tokens);
        }

        private boolean doesTokenMatch(int index, String token) {
            return tokens.get(index)
                         .equalsIgnoreCase(token);
        }

        private boolean doesSecondTokenMatch(String token) {
            return doesTokenMatch(1, token);
        }

        private boolean hasSingleToken() {
            return tokens.size() == 1;
        }


        private boolean hasTwoTokens() {
            return tokens.size() == 2;
        }

        private boolean isCommandSeparatedByArrow() {
            return isSeparatedBy(ARROW);
        }

        private boolean isCommandSeparatedByFollows() {
            return isSeparatedBy(FOLLOWS);
        }

        private boolean isSeparatedBy(String separator) {
            return tokens.size() > 2 && tokens.get(1)
                                              .equalsIgnoreCase(separator);
        }

    }


}
