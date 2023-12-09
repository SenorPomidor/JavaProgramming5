package ru.mipt.tasks;

import ru.mipt.tasks.dto.Person;
import ru.mipt.tasks.factory.ClientFactory;
import ru.mipt.tasks.factory.ClientFactoryImpl;
import ru.mipt.tasks.factory.ServerFactory;
import ru.mipt.tasks.factory.ServerFactoryImpl;
import ru.mipt.tasks.service.ScoreService;
import ru.mipt.tasks.service.ScoreServiceImpl;

public class Application {

    public static void main(String[] args) {

    }

    public static void configureServer() {
        ServerFactory serverFactory = new ServerFactoryImpl();
        serverFactory.listen(8000, new ScoreServiceImpl());
    }

    public static ScoreService createScoreClient() {
        ClientFactory factory = new ClientFactoryImpl("127.0.0.1", 8000);
        return factory.newClient(ScoreService.class);
    }
}
