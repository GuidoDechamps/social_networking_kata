package be.solid.social.api;

import java.util.List;

public interface ReaderService {
    List<Message> read(String user);

    List<Message> readWall(String user);
}
