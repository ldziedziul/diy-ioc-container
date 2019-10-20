package pl.dziedziul.diyioccontainer.simple;

import pl.dziedziul.myowndicontainer.engine.transaction.Transactional;

import java.util.List;

@Transactional
class UserService {
    private final Printer printer;
    private final UserRepository userRepository;

    UserService(final Printer printer, final UserRepository userRepository) {
        this.printer = printer;
        this.userRepository = userRepository;
    }

    public void printUsers() {
        List<String> users = userRepository.getAllUsers();
        print(users);
    }

    public void printUsersThrowing() {
        throw new RuntimeException("some exception");
    }

    void print(final List<String> users) {
        users.forEach(printer::print);
    }
}
