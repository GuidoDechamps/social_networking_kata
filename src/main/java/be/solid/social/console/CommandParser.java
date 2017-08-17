package be.solid.social.console;

import java.util.Optional;

class CommandParser {
    public static final String ARROW = "->";
    private static final String SPACE = " ";

    public static Optional<Command> parseCommand(String line) {
        if (line.startsWith(ARROW)) return Optional.empty();

        final String[] tokens = line.split(ARROW);
        if (commandIsSeparatedByArrow(tokens)) {
            return Optional.of(buildPosting(tokens));
        }
        return Optional.empty();
    }

    private static boolean commandIsSeparatedByArrow(String[] tokens) {
        return tokens.length == 2;
    }



    private static Command buildPosting(String[] tokens) {
        return Posting.newBuilder()
                      .withActor(tokens[0])
                      .withContent(tokens[1])
                      .build();
    }
}
