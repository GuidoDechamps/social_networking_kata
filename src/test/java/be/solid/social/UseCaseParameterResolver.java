package be.solid.social;

import be.solid.social.impl.Messages;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.time.Clock;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UseCaseParameterResolver implements ParameterResolver {
    private final Map<Class, Supplier<?>> supportedInstances = new HashMap<>();

    public UseCaseParameterResolver() {
        final Clock myClock = new SecondIncrementClock(5);
        supportedInstances.put(SecondIncrementClock.class, () -> myClock);
        supportedInstances.put(Messages.class, () -> new Messages(myClock));
        supportedInstances.put(Messages.class, () -> new Messages(myClock));
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = getParameterType(parameterContext);
        return supportedInstances.containsKey(type);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = getParameterType(parameterContext);
        final Supplier<?> s = supportedInstances.get(type);
        return s.get();
    }


    private Class<?> getParameterType(ParameterContext parameterContext) {
        final Parameter parameter = parameterContext.getParameter();
        return parameter.getType();
    }


}
