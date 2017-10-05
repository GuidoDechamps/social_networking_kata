package be.solid.social.console;

import java.util.List;

public class CommandTokens {
    public static final String ARROW = "->";
    public static final String SPACE = " ";
    public static final String FOLLOWS = "follow";
    public static final String WALL = "wall";
    public static final String EXIT = "exit";
    //TODO what is the point again?
    private final List<String> spaceSplittokens;
    private final List<String> arrowSplitTokens;

    private CommandTokens(List<String> spaceSplittokens, List<String> arrowSplitTokens) {
        this.spaceSplittokens = spaceSplittokens;
        this.arrowSplitTokens = arrowSplitTokens;
    }
}
