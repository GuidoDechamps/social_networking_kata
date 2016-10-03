package be.solid.social;

import be.solid.social.api.Messages;
import be.solid.social.api.PublishingService;
import be.solid.social.api.ReaderService;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolutionException;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.HashMap;
import java.util.Map;

public class UseCaseParameterResolver implements ParameterResolver {
    private final Map<Class, Object> instances = new HashMap<>();
    private final Messages messages = new Messages();

    public UseCaseParameterResolver() {
        instances.put(PublishingService.class, createPublisher());
        instances.put(ReaderService.class, createReader());
    }

    @Override
    public boolean supports(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = parameterContext.getParameter().getType();
        return instances.containsKey(type);
    }

    @Override
    public Object resolve(ParameterContext parameterContext, ExtensionContext extensionContext) throws ParameterResolutionException {
        final Class<?> type = parameterContext.getParameter().getType();
        return instances.get(type);
    }


    private PublishingService createPublisher() {
        return messages;
    }

    private ReaderService createReader() {
        return messages;
    }
}
