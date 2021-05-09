package org.administrator.schedule;

import com.calendarfx.model.CalendarEvent;
import javafx.event.EventType;
import org.database.employee.EmployeeEntity;

public class ScheduleCalendarEvent extends CalendarEvent {

    public static final EventType<ScheduleCalendarEvent> ENTRY_ATTENDEES_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_ATTENDEES_CHANGED");

    public static final EventType<ScheduleCalendarEvent> ENTRY_ATTENDEES_CAN_SEE_OTHERS_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_ATTENDEES_CAN_SEE_OTHERS_CHANGED");

    public static final EventType<ScheduleCalendarEvent> ENTRY_ATTENDEES_CAN_MODIFY_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_ATTENDEES_CAN_MODIFY_CHANGED");

    public static final EventType<ScheduleCalendarEvent> ENTRY_REMINDERS_CHANGED = new EventType<>(
            ENTRY_CHANGED, "ENTRY_REMINDERS_CHANGED");

    ScheduleCalendarEvent(EventType<ScheduleCalendarEvent> eventType, ScheduleCalendar calendar, ScheduleEntry entry, Object oldobject) {
        super(eventType, calendar, entry);
    }


}
