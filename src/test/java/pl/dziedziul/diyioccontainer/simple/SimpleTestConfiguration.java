package pl.dziedziul.diyioccontainer.simple;

import pl.dziedziul.myowndicontainer.engine.Bean;
import pl.dziedziul.myowndicontainer.engine.Configuration;

@Configuration
class SimpleTestConfiguration {

    @Bean
    UserService userService(Printer printer, UserRepository userRepository) {
        return new UserService(printer, userRepository);
    }

    @Bean
    Printer printer() {
        return new Printer();
    }

    @Bean
    UserRepository userRepository() {
        return new UserRepository();
    }
}
