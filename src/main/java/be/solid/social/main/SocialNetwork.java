package be.solid.social.main;

import be.solid.social.console.ConsoleAdapter;

class SocialNetwork {
    private final ConsoleAdapter consoleAdapter;

    SocialNetwork(ConsoleAdapter consoleAdapter) {
        this.consoleAdapter = consoleAdapter;
    }

    public void run() {
        consoleAdapter.scanContinuously();
    }


}
