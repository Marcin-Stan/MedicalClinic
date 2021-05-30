package org.visitCalendar;

import com.calendarfx.model.CalendarEvent;
import javafx.event.EventType;

public class VisitCalendarEvent extends CalendarEvent {

    public static final EventType<VisitCalendarEvent> ENTRY_PATIENT_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_PATIENT_CHANGED");

    public static final EventType<VisitCalendarEvent> ENTRY_EMPLOYEE_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_EMPLOYEE_CHANGED");

    public static final EventType<VisitCalendarEvent> ENTRY_SERVICE_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_SERVICE_CHANGED");

    public static final EventType<VisitCalendarEvent> ENTRY_IS_PAID_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_IS_PAID_CHANGED");

    public static final EventType<VisitCalendarEvent> ENTRY_IS_FINISHED_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_IS_FINISHED_CHANGED");

    public static final EventType<VisitCalendarEvent> ENTRY_NOTE_CHANGED = new EventType<>(ENTRY_CHANGED,"ENTRY_NOTE_CHANGED");

    VisitCalendarEvent(EventType<VisitCalendarEvent> eventType, VisitCalendar calendar, VisitEntry entry) {
        super(eventType, calendar, entry);
    }

}
