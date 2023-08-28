package de.ait.events.services.impl;

import de.ait.events.models.Event;
import de.ait.events.repositories.EventsRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("EventsServiceImpl is works ...")
@DisplayNameGeneration(value = DisplayNameGenerator.ReplaceUnderscores.class)

class EventsServiceImplTest {

    private static final LocalDate EXIST_EVENT_DATE = LocalDate.of(2023, 11, 11);
    private static final LocalDate NOT_EXIST_EVENT_DATE = LocalDate.of(2023, 9, 16);
    private static final String DEFAULT_TITLE = "Birthday";
    private static final LocalDate DEFAULT_END_DATE = LocalDate.of(2023, 11, 11);
    private static final Event EXIST_EVENT = new Event(DEFAULT_TITLE, EXIST_EVENT_DATE, DEFAULT_END_DATE);
    private static final Event NOT_EXIST_EVENT = new Event("Party", NOT_EXIST_EVENT_DATE, LocalDate.of(2023, 9, 16));

    private EventsServiceImpl eventsService;

    private EventsRepository eventsRepository;


    @BeforeEach
    public void setUp() {
        eventsRepository = Mockito.mock(EventsRepository.class);

        when(eventsRepository.findOneByStartDate(EXIST_EVENT_DATE)).thenReturn(EXIST_EVENT);
        when(eventsRepository.findOneByStartDate(NOT_EXIST_EVENT_DATE)).thenReturn(null);
        this.eventsService = new EventsServiceImpl(eventsRepository);
    }

    @Nested
    @DisplayName(("addEvent():"))
    class AddEvent {
        @Test
        public void on_incorrect_startDate_throws_exception() {

            assertThrows(IllegalArgumentException.class, () -> eventsService.addEvent(String.valueOf(NOT_EXIST_EVENT), null, DEFAULT_END_DATE));
        }

        @Test
        public void on_existed_event_throws_exception() {
            assertThrows(IllegalArgumentException.class, () -> eventsService.addEvent(DEFAULT_TITLE, EXIST_EVENT_DATE, DEFAULT_END_DATE));
        }

        @Test
        public void returns_created_user() {
            Event actual = eventsService.addEvent(DEFAULT_TITLE, EXIST_EVENT_DATE, DEFAULT_END_DATE);

            verify(eventsRepository).save(NOT_EXIST_EVENT);

            assertNotNull(actual);
        }
    }

}