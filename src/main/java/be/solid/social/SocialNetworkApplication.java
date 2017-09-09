package be.solid.social;

import be.solid.social.console.Command;
import be.solid.social.console.CommandParser;

import java.util.Optional;
import java.util.Scanner;

public class SocialNetworkApplication {

    public static void main(String[] args) {
        System.out.println("Starting SocialNetworkApplication");
        System.out.println("*************************");
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("[SNA] Please enter a command");

            final String line = scanner.nextLine();
            if (line.equalsIgnoreCase("exit")) break;

            processCommand(line);

        }
        System.out.println("*************************");
        System.out.println("Stopping SocialNetworkApplication");
    }

    private static void processCommand(String line) {
        final Optional<Command> command = CommandParser.parseCommand(line);
        System.out.println("[SNA] i read: " + line);
        command.ifPresent(x -> System.out.println("[SNA] parsed to command: " + x.toString()));
    }


}
