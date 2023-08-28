package de.ait.events.services.impl;

import de.ait.events.models.Event;
import de.ait.events.repositories.EventsRepository;
import de.ait.events.services.EventsService;

import java.time.LocalDate;
import java.util.List;

public class EventsServiceImpl implements EventsService {

    private final EventsRepository eventsRepository;

    public EventsServiceImpl(EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @Override
    public Event addEvent(String title, LocalDate startDate, LocalDate endDate) {
        if (title == null || title.equals("") || title.equals(" ")) { // валидируем email
            throw new IllegalArgumentException("Email не может быть пустым");
        }

        if (startDate == null) {
            throw new IllegalArgumentException("Поле с датой начала события не может быть пустым");
        }

        if (endDate == null) {
            throw new IllegalArgumentException("Поле с датой окончания события не может быть пустым");
        }

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("Дата окончания события не может быть раньше даты его начала");
        }

        Event event = new Event(title, startDate, endDate);
        eventsRepository.save(event);

        return event;
    }

    @Override
    public List<Event> getAllEvents() {
        return eventsRepository.findAll();
    }
}
