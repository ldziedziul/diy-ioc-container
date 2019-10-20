package pl.dziedziul.myowndicontainer.engine.transaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class TransactionalHandler implements InvocationHandler {
    private final Object target;
    private static Logger log = LoggerFactory.getLogger(TransactionalHandler.class);

    public TransactionalHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (!shouldBeTransactional(method)) {
            try {
                return invokeOnTarget(method, args);
            } catch (InvocationTargetException ex) {
                throw ex.getCause();
            }
        }
        log.info("Start transaction before {}.{}", method.getDeclaringClass(), method.getName());
        try {
            Object result = invokeOnTarget(method, args);
            log.info("Committing transaction after {}.{}", method.getDeclaringClass(), method.getName());
            return result;
        } catch (InvocationTargetException ex) {
            if (ex.getCause() instanceof RuntimeException) {
                log.info("Rollback transaction after {}.{}", method.getDeclaringClass(), method.getName());
            }
            throw ex.getCause();
        }
    }

    private Object invokeOnTarget(final Method method, final Object[] args) throws IllegalAccessException, InvocationTargetException {
        method.setAccessible(true);
        return method.invoke(target, args);
    }

    private boolean shouldBeTransactional(final Method method) {
        return method.getModifiers() == Modifier.PUBLIC;
    }
}
