package be.solid.social;

import be.solid.social.console.ConsoleAdapter;

import java.util.Scanner;

public class SocialNetworkApplication {
    private static final String EXIT = "exit";
    private final ConsoleAdapter consoleAdapter;

    public SocialNetworkApplication(ConsoleAdapter consoleAdapter) {
        this.consoleAdapter = consoleAdapter;
    }

    public static void main(String[] args) {
        final SocialNetworkApplication socialNetworkApplication = new SocialNetworkApplication(new ConsoleAdapter());
        socialNetworkApplication.run();
    }

    public void run() {
        printStartMessage();
        scanContinuously();
        printCloseApplication();
    }

    public void scanContinuously() {
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("[SNA] Please enter a command - exit to quit");

            final String line = scanner.nextLine();
            if (line.equalsIgnoreCase(EXIT)) break;

            consoleAdapter.processCommand(line);

        }
    }


    private void printCloseApplication() {
        System.out.println("*************************");
        System.out.println("Stopping SocialNetworkApplication");
    }

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

