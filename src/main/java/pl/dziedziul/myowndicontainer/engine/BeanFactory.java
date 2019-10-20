package pl.dziedziul.myowndicontainer.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

class BeanFactory {
    private static final Logger log = LoggerFactory.getLogger(BeanFactory.class);

    private final Set<BeanDefinition> beanDefinitions;
    private final Set<Class<?>> beansCurrentlyInCreation = new HashSet<>();
    private final Set<BeanPostProcessor> beanPostProcessors;

    public BeanFactory(final Set<BeanDefinition> beanDefinitions, Set<BeanPostProcessor> beanPostProcessors) {
        this.beanDefinitions = beanDefinitions;
        this.beanPostProcessors = beanPostProcessors;
    }

    public void init(Context context) {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> beanType = beanDefinition.getBeanType();
            if (!context.containsBean(beanType)) {
                Object bean = createBean(beanDefinition, context);
                context.registerBean(beanType, bean);
            }
        }
    }

    Object createBean(BeanDefinition beanDefinition, Context context) {
        Class<?> beanType = beanDefinition.getBeanType();
        log.info("Creating bean {}", beanType.getName());
        Object[] constructorArgs = beanDefinition.getDependencies().stream()
                .map(depType -> getOrCreateDependencyBean(context, depType)).toArray();
        Object instance = beanDefinition.createInstance(constructorArgs);
        for (final BeanPostProcessor beanPostProcessor : beanPostProcessors) {
            log.debug("Post processing bean {} with {} ", beanType, beanPostProcessor.getClass());
            instance = beanPostProcessor.postProcess(instance);
        }
        return instance;
    }

    private Object getOrCreateDependencyBean(final Context context, final Class<?> beanType) {
        if (context.containsBean(beanType)) {
            return context.getBean(beanType);
        } else {
            BeanDefinition dependencyBeanDefinition = getBeanDefinition(beanType);
            Object bean = withCyclicCreationCheck(beanType, () -> createBean(dependencyBeanDefinition, context));
            context.registerBean(beanType, bean);
            return bean;
        }
    }

    private Object withCyclicCreationCheck(Class<?> beanType, Supplier<?> supplier) {
        if (beansCurrentlyInCreation.contains(beanType)) {
            throw new BeanCurrentlyInCreationException(beanType);
        }
        beansCurrentlyInCreation.add(beanType);
        Object result = supplier.get();
        beansCurrentlyInCreation.remove(beanType);
        return result;
    }

    private BeanDefinition getBeanDefinition(final Class<?> beanType) {
        return beanDefinitions.stream()
                .filter(def -> def.getBeanType().equals(beanType))
                .findFirst()
                .orElseThrow(() -> new BeanNotFoundException(beanType));
    }

}
