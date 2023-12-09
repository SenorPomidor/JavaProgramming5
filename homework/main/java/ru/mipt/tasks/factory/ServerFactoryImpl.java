package ru.mipt.tasks.factory;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class ServerFactoryImpl implements ServerFactory {

    @Override
    public void listen(int port, Object service) {
        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                while (true) {
                    Socket clientSocket = serverSocket.accept();
                    new Thread(() -> handleClientRequest(clientSocket, service)).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void handleClientRequest(Socket clientSocket, Object service) {
        try (clientSocket;
             ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream())) {

            String methodName = (String) in.readObject();
            Object[] args = (Object[]) in.readObject();

            Method method = findMethod(service, methodName, args);
            Object result = method.invoke(service, args);

            out.writeObject(result);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Method findMethod(Object service, String methodName, Object[] args) throws NoSuchMethodException {
        return service.getClass()
                .getMethod(methodName, Arrays.stream(args)
                        .map(Object::getClass)
                        .toArray(Class<?>[]::new));
    }
}
