package be.solid.social.usecase;

public interface Command<T> {

    T execute(SocialNetworkUseCases useCases);
}
