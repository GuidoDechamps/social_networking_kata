package be.solid.social.main;

import static be.solid.social.main.SocialNetworkFactory.create;

public class SocialNetworkApplication {


    private SocialNetworkApplication() {
        //no instance
    }

    public static void main(String[] args) {

        final SocialNetwork socialNetworkApplication = create();
        printStartMessage();
        socialNetworkApplication.run();
        printCloseApplication();
    }

    private static void printStartMessage() {
        System.out.println("Starting SocialNetworkApplication");
        System.out.println("*************************");

    }

    private static void printBuilding() {
        System.out.println("Building SocialNetworkApplication...");

    }

    private static void printCloseApplication() {
        System.out.println("*************************");
        System.out.println("Stopping SocialNetworkApplication");
    }


}

