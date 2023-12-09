package ru.mipt.tasks.service;

import ru.mipt.tasks.dto.Person;

public class ScoreServiceImpl implements ScoreService {

    @Override
    public double score(Person person) {

        return person.getAge() * 1.5;
    }
}
