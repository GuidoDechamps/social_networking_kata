package be.solid.social;

import be.solid.social.api.Messages;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class UseCaseParameterResolver implements ParameterResolver {
    private final Map<Class, Object> instances = new HashMap<>();
    private final Messages messages = new Messages();

    public UseCaseParameterResolver() {
        final StateFullInstancePerTest<Messages> messagesPerTest = createMessagesPerTest();
        instances.put(PublishingService.class, messagesPerTest);
        instances.put(ReaderService.class, messagesPerTest);
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = getParameterType(parameterContext);
        return instances.containsKey(type);
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = getParameterType(parameterContext);
        final Object o = instances.get(type);
        if (StateFullInstancePerTest.class.isInstance(o)) return StateFullInstancePerTest.class.cast(o)
                                                                                               .getInstanceForTest(extensionContext);
        else return o;
    }

    private Class<?> getParameterType(ParameterContext parameterContext) {
        final Parameter parameter = parameterContext.getParameter();
        return parameter.getType();
    }

    private StateFullInstancePerTest<Messages> createMessagesPerTest() {
        return new StateFullInstancePerTest<>(Messages::new);
    }

    private class StateFullInstancePerTest<T> {
        private final Map<String, T> instances = new HashMap<>();
        private final Supplier<T> factory;

        private StateFullInstancePerTest(Supplier<T> factory) {
            this.factory = factory;
        }

        private Object getInstanceForTest(ExtensionContext extensionContext) {
            final String uniqueId = extensionContext.getUniqueId();
            if (!instances.containsKey(uniqueId)) {
                addNewInstance(uniqueId);
            }
            return instances.get(uniqueId);
        }

        private void addNewInstance(String uniqueId) {
            final T t = factory.get();
            instances.put(uniqueId, t);
        }
    }
}
