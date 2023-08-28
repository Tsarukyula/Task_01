package de.ait.events.controllers;

import de.ait.events.models.Event;
import de.ait.events.services.EventsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EventsController {

    private final Scanner scanner;
    private final EventsService eventsService;

    public EventsController(Scanner scanner, EventsService eventsService) {
        this.scanner = scanner;
        this.eventsService = eventsService;
    }

    public void addEvent() {
        System.out.println("Введите название события:");
        String title = scanner.nextLine();
        System.out.println("Введите дату начала (ГГГГ-ММ-ДД):");
        LocalDate startDate = LocalDate.parse(scanner.nextLine());
        System.out.println("Введите дату окончания (ГГГГ-ММ-ДД):");
        LocalDate endDate = LocalDate.parse(scanner.nextLine());

        Event event = eventsService.addEvent(title, startDate, endDate);
        System.out.println(event);
    }

    public void getAllEvents() {
        List<Event> events = eventsService.getAllEvents();
        System.out.println(events);
    }
}
