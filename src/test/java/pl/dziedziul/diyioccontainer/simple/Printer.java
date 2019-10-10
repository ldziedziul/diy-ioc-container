package pl.dziedziul.diyioccontainer.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Printer {
    private static final Logger log = LoggerFactory.getLogger(Printer.class);

    void print(String s) {
        log.info(s);
    }
}
