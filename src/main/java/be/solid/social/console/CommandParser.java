package be.solid.social.console;

import java.util.Optional;

import static be.solid.social.console.CommandTokens.*;
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


    public static Optional<Command> parseCommand(String line) {
        if (isInvalidInput(line)) return empty();

        final Tokens tokens = Tokens.create(line, SPACE);
        return buildCommand(tokens);
    }

    private static Optional<Command> buildCommand(Tokens tokens) {
        if (tokens.hasSingleToken()) return of(buildViewTimeLine(tokens.getFirst()));
        if (tokens.hasTwoTokens()) {
            if (tokens.doesSecondTokenMatch(WALL)) {
                return of(buildViewWallCommand(tokens));
            }
        }


        if (tokens.doesSecondTokenMatch(ARROW)) return of(buildPostCommand(tokens));

        if (tokens.doesSecondTokenMatch(FOLLOWS)) return of(buildFollowingCommand(tokens));

        return empty();
    }

    private static ViewWall buildViewWallCommand(Tokens tokens) {
        final String first = tokens.getFirst();
        return buildViewWall(first);
    }

    private static Posting buildPostCommand(Tokens tokens) {
        final String content = tokens.getContentAfterSecondToken();
        return Posting.newBuilder()
                      .withActor(tokens.getFirst())
                      .withContent(content)
                      .build();
    }

    private static Following buildFollowingCommand(Tokens tokens) {
        return Following.newBuilder()
                        .withUser(tokens.getFirst())
                        .withSubscriptionTopic(tokens.getThird())
                        .build();
    }

    private static boolean isInvalidInput(String line) {
        return line.trim()
                   .isEmpty() || line.startsWith(ARROW) || line.startsWith(FOLLOWS) || line.startsWith(WALL);
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


}
