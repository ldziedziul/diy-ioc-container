package pl.dziedziul.myowndicontainer.engine;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextFactory {
    private static final Logger log = LoggerFactory.getLogger(ContextFactory.class);
    private final Reflections reflections;

    public ContextFactory(final String basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Context createContext() {
        var configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        log.info("Creating context from configurations: {}", configurationClasses);
        return new Context();
    }
}
