package be.solid.social.main;

import be.solid.social.console.ConsoleAdapter;

class SocialNetwork {
    private final ConsoleAdapter consoleAdapter;

    public SocialNetwork(ConsoleAdapter consoleAdapter) {
        this.consoleAdapter = consoleAdapter;
    }

    public void run() {
        printStartMessage();
        consoleAdapter.scanContinuously();
    }




    //TODO should this be here? Or in adapter
    private void printStartMessage() {
        System.out.println("Commands : ");
        System.out.println("- posting: <user name> -> <message>");
        System.out.println("- reading: <user name>");
        System.out.println("- following: <user name> follows <another user>");
        System.out.println("- wall: <user name> wall");
        System.out.println("- exit: quit application");
    }
}
