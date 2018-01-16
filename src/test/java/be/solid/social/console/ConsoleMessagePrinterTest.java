package be.solid.social.console;

import be.solid.social.ManualClock;
import be.solid.social.MessageData;
import be.solid.social.TestScenarios;
import be.solid.social.usecase.Event;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class ConsoleMessagePrinterTest {
    private final ManualClock clock = new ManualClock(10);
    private ByteArrayOutputStream byteArrayOutputStream;
    private ConsoleMessagePrinter messagePrinter;

    @BeforeEach
    void setUp() {
        byteArrayOutputStream = new ByteArrayOutputStream();
        messagePrinter = new ConsoleMessagePrinter(new PrintStream(byteArrayOutputStream));
    }

    @ParameterizedTest()
    @MethodSource("allPostsToBeMade")
    @DisplayName("Print wall message")
    void printResult(MessageData messageData) {

        messagePrinter.printResult(toEvent(messageData));

        assertEquals(messageData.message, getPrintedMessage());
    }

    private static List<MessageData> allPostsToBeMade() {
        return TestScenarios.sequenceOfPosts();
    }

    private String getPrintedMessage() {
        return byteArrayOutputStream.toString()
                                    .trim();
    }

    private Event toEvent(MessageData x) {
        return Event.newBuilder()
                    .withUser(x.sender)
                    .withContent(x.message)
                    .withTime(clock.getPostTime(x.messageSequenceNumber))
                    .build();
    }
}