package ru.mipt.tasks;

import org.junit.jupiter.api.Test;
import ru.mipt.tasks.dto.Person;
import ru.mipt.tasks.service.ScoreService;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ApplicationTest {

    @Test
    public void testSockets() {
        Application.configureServer();

        ScoreService scoreService = Application.createScoreClient();
        Person person = new Person("Name Surname", 30);
        double result = scoreService.score(person);

        assertEquals(45.0, result);
    }
}