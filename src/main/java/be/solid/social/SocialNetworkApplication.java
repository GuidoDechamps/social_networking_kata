package be.solid.social;

import be.solid.social.console.ConsoleAdapter;

import java.io.PrintStream;

public class SocialNetworkApplication {
    private final PrintStream outputStream;
    private final ConsoleAdapter consoleAdapter;

    public SocialNetworkApplication(PrintStream outputStream, ConsoleAdapter consoleAdapter) {
        this.outputStream = outputStream;
        this.consoleAdapter = consoleAdapter;
    }

    public static void main(String[] args) {
        final SocialNetworkApplication socialNetworkApplication = new SocialNetworkApplication(System.out, new ConsoleAdapter(System.in, System.out));
        socialNetworkApplication.run();
    }

    public void run() {
        printStartMessage();
        consoleAdapter.scanContinuously();
        printCloseApplication();
    }


    private void printCloseApplication() {
        System.out.println("*************************");
        System.out.println("Stopping SocialNetworkApplication");
    }

    //TODO shoudl this be here? Or in adapter
    private void printStartMessage() {
        System.out.println("Starting SocialNetworkApplication");
        System.out.println("*************************");
        System.out.println("Commands : ");
        System.out.println("- posting: <user name> -> <message>");
        System.out.println("- reading: <user name>");
        System.out.println("- following: <user name> follows <another user>");
        System.out.println("- wall: <user name> wall");
        System.out.println("- exit: quit application");
    }
}

