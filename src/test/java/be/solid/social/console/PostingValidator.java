package be.solid.social.console;

import be.solid.social.domain.PublishingSpy;

import static be.solid.social.console.CommandTokens.ARROW;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PostingValidator {
    private final PublishingSpy spy;

    public PostingValidator(PublishingSpy spy) {
        this.spy = spy;
    }

    public void validatePostCommand(String inputLine) {
        final String actor = spy.getRequiredPoster();
        final String content = spy.getRequiredContent();

        assertTrue(inputLine.startsWith(actor), "Invalid actor " + actor);
        assertFalse(content.startsWith(ARROW), "Invalid content :" + content);
        assertTrue(inputLine.endsWith(content), "Invalid content :" + content);
    }


}
