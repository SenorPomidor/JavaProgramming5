package ru.mipt.tasks.factory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class ClientFactoryImpl implements ClientFactory {

    private final String host;
    private final int port;

    public ClientFactoryImpl(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public <T> T newClient(Class<T> clientInterface) {
        return (T) Proxy.newProxyInstance(
                clientInterface.getClassLoader(),
                new Class<?>[] {clientInterface},
                (proxy, method, args) -> invokeRemoteMethod(method, args)
        );
    }

    private Object invokeRemoteMethod(Method method, Object[] args) throws IOException, ClassNotFoundException {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(method.getName());
            out.writeObject(args);
            out.flush();

            return in.readObject();
        }
    }
}
