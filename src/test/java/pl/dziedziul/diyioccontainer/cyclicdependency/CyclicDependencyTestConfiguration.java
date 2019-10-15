package pl.dziedziul.diyioccontainer.cyclicdependency;

import pl.dziedziul.myowndicontainer.engine.Bean;
import pl.dziedziul.myowndicontainer.engine.Configuration;

@Configuration
class CyclicDependencyTestConfiguration {

    @Bean
    Car car(Engine engine) {
        return new Car(engine);
    }

    @Bean
    Engine engine(Fuel fuel) {
        return new Engine(fuel);
    }

    @Bean
    Fuel fuel(Car car) {
        return new Fuel(car);
    }
}
