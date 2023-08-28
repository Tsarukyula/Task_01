package de.ait.events.repositories.impl;

import de.ait.events.models.Event;
import org.junit.jupiter.api.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("EventsRepositoryFileImpl is works ...")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)

class EventsRepositoryFileImplTest {

    private static final String TEMP_EVENTS_FILE_NAME = "events_test.txt";

    private EventsRepositoryFileImpl eventsRepository;

    @BeforeEach
    public void setUp() throws Exception {
        createNewFileForTest(TEMP_EVENTS_FILE_NAME);
        eventsRepository = new EventsRepositoryFileImpl(TEMP_EVENTS_FILE_NAME);
    }

    @AfterEach
    public void tearDown() throws Exception {
        deleteFileAfterTest(TEMP_EVENTS_FILE_NAME);
    }

    @DisplayName("save():")
    @Nested
    class Save {

        @Test
        public void writes_correct_line_to_file() throws Exception {
            double v = .06;
            Event event = new Event("Birthday", LocalDate.of(2023, 11, 11),
                    LocalDate.of(2023, 11, 11));

            eventsRepository.save(event);

            String expected = "1#Birthday#2023.06.06#2023.06.06"; // строка, которую мы ожидаем

            BufferedReader reader = new BufferedReader(new FileReader(TEMP_EVENTS_FILE_NAME));

            String actual = reader.readLine();

            reader.close();

            assertEquals(expected, actual);
        }
    }

    @DisplayName("findAll():")
    @Nested
    class FindAll {

        @Test
        public void returns_correct_list_of_events() throws Exception {
            // запишем в файл напрямую список каких-либо людей
            BufferedWriter writer = new BufferedWriter(new FileWriter(TEMP_EVENTS_FILE_NAME));

            writer.write("1#Birthday#2023.11.11, 2023.11.11");
            writer.newLine();
            writer.write("2#Trip#2023.9.28, 2023.9.30");
            writer.newLine();
            writer.close();

            List<Event> expected = Arrays.asList(
                    new Event(1L, "Birthday",
                            LocalDate.of(2023, 11, 11),
                            LocalDate.of(2023, 11, 11)),
                    new Event(2L, "Trip",
                            LocalDate.of(2023, 9, 28),
                            LocalDate.of(2023, 9, 30))
            );

            List<Event> actual = eventsRepository.findAll();

            assertEquals(expected, actual);
        }
    }

    private static void createNewFileForTest(String fileName) throws IOException {

        File file = new File(fileName);

        deleteIfExists(file);

        boolean result = file.createNewFile();

        if (!result) {
            throw new IllegalStateException("Problems with file create");
        }
    }

    private static void deleteFileAfterTest(String fileName) {
        File file = new File(fileName);

        deleteIfExists(file);
    }

    private static void deleteIfExists(File file) {

        if (file.exists()) {
            boolean result = file.delete();

            if (!result) {
                throw new IllegalStateException("Problems with file delete");
            }
        }
    }
}