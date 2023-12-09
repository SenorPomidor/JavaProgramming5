package ru.mipt.tasks.factory;

public interface ClientFactory {

    <T> T newClient(Class<T> client);
}
