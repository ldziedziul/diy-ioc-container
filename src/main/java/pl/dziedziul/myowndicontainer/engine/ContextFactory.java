package pl.dziedziul.myowndicontainer.engine;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toUnmodifiableList;
import static java.util.stream.Collectors.toUnmodifiableSet;

public class ContextFactory {
    private static final Logger log = LoggerFactory.getLogger(ContextFactory.class);
    private final Reflections reflections;

    public ContextFactory(final String basePackage) {
        reflections = new Reflections(basePackage);
    }

    public Context createContext() {
        var configurationClasses = reflections.getTypesAnnotatedWith(Configuration.class);
        log.info("Creating context from configurations: {}", configurationClasses);
        Set<Object> configurations = createConfigurations(configurationClasses);
        return new Context();
    }

    private Set<Object> createConfigurations(final Set<Class<?>> configurationClasses) {
        return configurationClasses.stream().map(this::createConfigurationInstance)
                .collect(toUnmodifiableSet());
    }

    private Object createConfigurationInstance(final Class<?> configurationClass) {
        try {
            Constructor<?> defaultConstructor = configurationClass.getDeclaredConstructor();
            defaultConstructor.setAccessible(true);
            return defaultConstructor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Configuration " + configurationClass.getSimpleName() + " must have parameterless constructor", e);
        }
    }

}
