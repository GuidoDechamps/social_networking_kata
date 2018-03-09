package be.solid.social.console;

import be.solid.social.usecase.Event;
import be.solid.social.usecase.Presenter;

import java.io.PrintStream;
import java.util.List;

public class ConsoleMessagePrinter implements Presenter {
    private final PrintStream outputStream;

    public ConsoleMessagePrinter(PrintStream outputStream) {
        this.outputStream = outputStream;
    }

    public void printNoInputReceived() {
        outputStream.println("[No input given]");
    }

    public void noCommand(String input) {
        outputStream.println("[Unknown command {" + input + "}]");
    }

    public void printCommand(String input) {
        outputStream.println("[" + input + "]");
    }

    public void allEvents(List<Event> events) {
        events.forEach(this::eventContent);
    }

    void printRequestForCommand() {
        outputStream.println("");
        outputStream.println("[Please enter a command - exit to quit]");
    }

    void printResult(Object result) {
        if (result == null) {
            commandExecuted();
        } else if (Event.class.isInstance(result)) {
            final Event event = Event.class.cast(result);
            eventContent(event);
        } else if (List.class.isInstance(result)) {
            final List events = List.class.cast(result);
            allEvents(events);
        } else throw new RuntimeException("Unknown result type " + result.getClass()
                                                                         .getSimpleName());
    }

    void printStartMessage() {
        outputStream.println("[Commands :                                     ]");
        outputStream.println("[- posting: <user name> -> <message>            ]");
        outputStream.println("[- reading: <user name>                         ]");
        outputStream.println("[- following: <user name> follows <another user>]");
        outputStream.println("[- wall: <user name> wall                       ]");
        outputStream.println("[- exit: quit application                       ]");
    }

    private void eventContent(Event event) {
        outputStream.println(event.content);
    }

    private void commandExecuted() {
        outputStream.println("[executed command]");
    }

}
