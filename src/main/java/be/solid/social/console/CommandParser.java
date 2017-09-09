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


        if (tokens.doesSecondTokenMatch(ARROW)) {
            final String asStringFrom = tokens.getContentAfterSecondToken();
            return of(Posting.newBuilder()
                             .withActor(tokens.getFirst())
                             .withContent(asStringFrom)
                             .build());
        }
        if (tokens.doesSecondTokenMatch(FOLLOWS)) {
            final String[] follows = line.split(FOLLOWS);
            return of(Following.newBuilder()
                               .withUser(follows[0])
                               .withSubscriptionTopic(follows[1])
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

        private static final int FIRST_TOKEN_INDEX = 0;
        private static final int SECOND_TOKEN_INDEX = 1;
        private final List<String> tokens;
        private final String sourceLine;


        private Tokens(String lineToParse) {
            this.sourceLine = lineToParse;
            this.tokens = splitIntoTokens(lineToParse);
        }

        public String get(int index) {
            if (index < FIRST_TOKEN_INDEX || index > tokens.size()) throw new RuntimeException("No token on index " + index);
            else return tokens.get(index);
        }

        public String getFirst() {
            if (tokens.isEmpty()) throw new RuntimeException("No tokens available");
            else return tokens.get(FIRST_TOKEN_INDEX);
        }


        public String getContentAfterSecondToken() {
            final String s = get(SECOND_TOKEN_INDEX);
            final int i = sourceLine.indexOf(s);
            return sourceLine.substring(i + s.length());
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
            return doesTokenMatch(SECOND_TOKEN_INDEX, token);
        }

        private boolean hasSingleToken() {
            return tokens.size() == 1;
        }


        private boolean hasTwoTokens() {
            return tokens.size() == 2;
        }


    }


}
