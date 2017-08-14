package be.solid.social;

import be.solid.social.api.Message;

import static be.solid.social.api.Message.create;

class MessageInput {
    final String sender;
    final String message;


    MessageInput(String sender, String message) {
        this.sender = sender;
        this.message = message;
    }

    Message toMessage() {
        return create(sender, message);
    }
}
