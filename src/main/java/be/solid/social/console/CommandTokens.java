package be.solid.social.console;

import java.util.List;

class CommandTokens {
    static final String ARROW = "->";
    static final String SPACE = " ";
    static final String WALL = "wall";

   private final List<String> spaceSplittokens;
   private final List<String> arrowSplitTokens;

    private CommandTokens(List<String> spaceSplittokens, List<String> arrowSplitTokens) {
        this.spaceSplittokens = spaceSplittokens;
        this.arrowSplitTokens = arrowSplitTokens;
    }
}
