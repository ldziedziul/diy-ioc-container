package pl.dziedziul.myowndicontainer.engine;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
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
        Set<BeanDefinition> beanDefinitions = createBeanDefinitions(configurations);
        BeanFactory beanFactory = new BeanFactory(beanDefinitions);
        Context context = new Context();
        beanFactory.init(context);
        return context;
    }

    private Set<BeanDefinition> createBeanDefinitions(final Set<Object> configurations) {
        return configurations.stream()
                    .map(this::createBeanDefinitions)
                    .flatMap(Set::stream)
                    .collect(toUnmodifiableSet());
    }

    private Set<BeanDefinition> createBeanDefinitions(Object configuration) {
        return Arrays.stream(configuration.getClass().getDeclaredMethods())
                .filter(meth -> meth.isAnnotationPresent(Bean.class))
                .map(meth -> createBeanDefinition(meth, configuration))
                .collect(toUnmodifiableSet());
    }

    private BeanDefinition createBeanDefinition(final Method beanMethod, final Object configuration) {
        log.debug("Creating bean definition from {}.{}", beanMethod.getDeclaringClass().getName(), beanMethod.getName());
        Class<?> beanType = beanMethod.getReturnType();
        List<Class<?>> dependencies = Stream.of(beanMethod.getParameters()).map(Parameter::getType)
                .collect(toUnmodifiableList());
        return new BeanDefinition(beanType, dependencies, configuration, beanMethod);
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
