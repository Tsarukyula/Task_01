package de.ait.events.repositories.impl;

import de.ait.events.models.Event;
import de.ait.events.repositories.EventsRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventsRepositoryListImpl implements EventsRepository {

    private final List<Event> events = new ArrayList<>();
    private Long generatedId = 1L;

    @Override
    public Event findById(Long id) {
        return null;
    }

    @Override
    public List<Event> findAll() {
        return new ArrayList<>(events);
    }

    @Override
    public void save(Event event) {
        events.add(event); // положили пользователя в хранилище-список
        event.setId(generatedId);
        generatedId++;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public void update(Event event) {

    }

    @Override
    public Event findOneByStartDate(LocalDate startDate) {
        return events.stream()
                .filter(user -> user.getStartDate().equals(startDate))
                .findFirst()
                .orElse(null);
    }
}


