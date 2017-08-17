package be.solid.social.console;

import java.util.Scanner;

class SocialNetworkApplication {
    public static void main(String[] args) {
        System.out.println("Starting SocialNetworkApplication");
        System.out.println("*************************");
        final Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("");
            System.out.println("[SNA] Please enter a command");
            final String line = scanner.nextLine();
            System.out.println("[SNA] i read: " + line);
            if(line.equalsIgnoreCase("exit"))
                break;
        }
        System.out.println("*************************");
        System.out.println("Stopping SocialNetworkApplication");
    }
}
