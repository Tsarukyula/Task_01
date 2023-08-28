package de.ait.events;

import de.ait.events.controllers.EventsController;
import de.ait.events.repositories.EventsRepository;
import de.ait.events.repositories.impl.EventsRepositoryFileImpl;
import de.ait.events.repositories.impl.EventsRepositoryListImpl;
import de.ait.events.services.EventsService;
import de.ait.events.services.impl.EventsServiceImpl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        EventsRepository eventsRepositoryList = new EventsRepositoryListImpl();
        EventsRepository eventsRepositoryFile = new EventsRepositoryFileImpl("events.txt");
        EventsService eventsService = new EventsServiceImpl(eventsRepositoryList);
        EventsController eventsController = new EventsController(scanner, eventsService);
        boolean isRun = true;

        while (isRun) {
            String command = scanner.nextLine();
            switch (command) {
                case "/addEvent" ->
                        eventsController.addEvent();
                case "/events" ->
                        eventsController.getAllEvents();
                case "/exit" -> isRun = false;
            }
        }
    }
}
