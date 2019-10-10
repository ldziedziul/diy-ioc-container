package pl.dziedziul.diyioccontainer.simple;

import java.util.List;

class UserRepository {
    List<String> getAllUsers() {
        return List.of("Alice", "Bob", "Camila");
    }
}
