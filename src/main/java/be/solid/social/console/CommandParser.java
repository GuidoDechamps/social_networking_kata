package be.solid.social.console;

import java.util.Optional;

class CommandParser {
    public static final String ARROW = "->";
    private static final String SPACE = " ";

    public static Optional<Command> parseCommand(String line) {
        if (isInvalidInput(line)) return Optional.empty();
        if (isSingleToken(line)) return Optional.of(buildViewTimeLine(line));

        final String[] tokens = line.split(ARROW);
        if (commandIsSeparatedByArrow(tokens)) {
            return Optional.of(buildPosting(tokens));
        }
        return Optional.empty();
    }

    private static boolean isInvalidInput(String line) {
        return line.trim()
                   .isEmpty() || line.startsWith(ARROW);
    }

    private static boolean isSingleToken(String line) {
        return line.split(SPACE).length == 1;
    }

    private static ViewTimeLine buildViewTimeLine(String line) {
        return ViewTimeLine.newBuilder()
                           .withUser(line.trim())
                           .build();
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
