package pl.dziedziul.myowndicontainer.engine.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class SomeService {
    Logger log = LoggerFactory.getLogger(SomeService.class);
    private final int number;
    private boolean throwExceptionOnNextCall = false;


    SomeService(final int number) {
        this.number = number;
    }

    void doSomething() {
        if (throwExceptionOnNextCall){
            throw new RuntimeException("Validation failed or sth similar");
        }
        log.info("Do something");
        throwExceptionOnNextCall = !throwExceptionOnNextCall;
    }

    int getNumber() {
        log.info("Get number");
        return number;
    }
}
