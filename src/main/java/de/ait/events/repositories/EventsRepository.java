package de.ait.events.repositories;

import de.ait.events.models.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventsRepository extends CrudRepository<Event> {
//    Event findById(Long id);
//    List<Event> findAll();
//    void save(Event event);
//    void deleteById(Long id);
//    void update(Event event);

    Event findOneByStartDate(LocalDate startDate);
}
