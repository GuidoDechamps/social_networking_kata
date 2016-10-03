package be.solid.social.api;

import java.util.List;

public interface ReaderService {
    List<Message> read(String receiver);
}
