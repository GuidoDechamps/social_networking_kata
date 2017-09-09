package be.solid.social.console;

import com.google.common.collect.Lists;

import java.util.List;


class Tokens {

    private static final int FIRST_TOKEN_INDEX = 0;
    private static final int SECOND_TOKEN_INDEX = 1;
    private static final int THIRD_TOKEN_INDEX = 2;
    private final List<String> tokens;
    private final String sourceLine;

    private Tokens(String sourceLine, List<String> tokens) {
        this.sourceLine = sourceLine;
        this.tokens = tokens;
    }


    static Tokens create(String lineToParse, String separator) {
        return new Tokens(lineToParse, splitIntoTokens(lineToParse, separator));
    }

    String getFirst() {
        return get(FIRST_TOKEN_INDEX);
    }

    String getThird() {
        return get(THIRD_TOKEN_INDEX);
    }

    String getContentAfterSecondToken() {
        final String s = get(SECOND_TOKEN_INDEX);
        final int i = sourceLine.indexOf(s);
        return sourceLine.substring(i + s.length());
    }

    boolean doesSecondTokenMatch(String token) {
        return doesTokenMatch(SECOND_TOKEN_INDEX, token);
    }

    boolean hasSingleToken() {
        return tokens.size() == 1;
    }

    boolean hasTwoTokens() {
        return tokens.size() == 2;
    }

    private static List<String> splitIntoTokens(String line, String separator) {
        final String[] tokens = line.split(separator);
        return Lists.newArrayList(tokens);
    }

    private String get(int index) {
        if (index < FIRST_TOKEN_INDEX || index > tokens.size()) throw new RuntimeException("No token on index " + index);
        else return tokens.get(index);
    }

    private boolean doesTokenMatch(int index, String token) {
        return tokens.get(index)
                     .equalsIgnoreCase(token);
    }


}

