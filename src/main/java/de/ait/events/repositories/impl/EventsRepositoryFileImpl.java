package de.ait.events.repositories.impl;

import de.ait.events.models.Event;
import de.ait.events.repositories.EventsRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class EventsRepositoryFileImpl implements EventsRepository {

    private final String fileName;

    public EventsRepositoryFileImpl(String fileName) {
        this.fileName = fileName;
    }

    private Long generatedId = 1L;

    //DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public Event findById(Long id) {
        return null;
    }

    @Override
    public List<Event> findAll() {

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            return reader.lines() // получаем все строки из файла
                    .map(line -> line.split("#"))
                    .map(parsed -> new Event(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3]))
                    .collect(Collectors.toList());

        } catch (IOException e)  {
            throw new IllegalStateException("Проблемы с чтением из файла: " + e.getMessage());
        }
    }

    @Override
    public void save(Event event) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            event.setId(generatedId);

            writer.write(event.getId() + "#" + event.getTitle() + "#" + event.getStartDate() + "#" + event.getEndDate());
            writer.newLine();

        } catch (IOException e) {
            throw new IllegalStateException("Проблемы с записью в файл: " + e.getMessage());
        }
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

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

                return reader.lines()
                        .map(line -> line.split("#"))
                        .filter(parsed -> LocalDate.parse(parsed[2]).isBefore(startDate))
                        .findFirst() // берем первую строку, которая нам подошла
                        .map(parsed -> new Event(Long.parseLong(parsed[0]), parsed[1], parsed[2], parsed[3]))
                        .orElse(null);

            } catch (IOException e)  {
                throw new IllegalStateException("Проблемы с чтением из файла: " + e.getMessage());
            }
        }
    }

