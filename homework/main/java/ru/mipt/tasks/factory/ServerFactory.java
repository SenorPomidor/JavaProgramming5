package ru.mipt.tasks.factory;

public interface ServerFactory {

    void listen(int port, Object service);
}
