package pl.dziedziul.diyioccontainer.simple;

import java.util.List;

class UserService {
    private final Printer printer;
    private final UserRepository userRepository;

    UserService(final Printer printer, final UserRepository userRepository) {
        this.printer = printer;
        this.userRepository = userRepository;
    }

    void printUsers() {
        List<String> users = userRepository.getAllUsers();
        users.forEach(printer::print);
    }
}
