package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;

import java.util.HashMap;
import java.util.Map;

public class ScheduleSynManager implements EventHandler<CalendarEvent>, ListChangeListener<Calendar>{

    @Override
    public void onChanged(Change<? extends Calendar> c) {
        while (c.next()) {
            for (Calendar calendar : c.getRemoved()) {
                calendar.removeEventHandler(this);
            }

            for (Calendar calendar : c.getAddedSubList()) {
                calendar.addEventHandler(this);
            }
        }
    }

    @Override
    public void handle(CalendarEvent evt) {

            if (requiresInsertEntry(evt)) {
                insertEntry(evt);
            } else if (requiresDeleteEntry(evt)) {
                deleteEntry(evt);
            }
    }


    private void deleteEntry(CalendarEvent evt) {
        ScheduleEntry entry = (ScheduleEntry) evt.getEntry();
        ScheduleCalendar calendar = (ScheduleCalendar) evt.getOldCalendar();
        ScheduleTaskExecutor.getInstance().execute(new DeleteEntryTask(entry, calendar));
        System.out.println("usunąłem");
    }

    private void insertEntry(CalendarEvent evt) {
        ScheduleEntry entry = (ScheduleEntry) evt.getEntry();
        ScheduleCalendar calendar = (ScheduleCalendar) evt.getCalendar();
        //ScheduleTaskExecutor.getInstance().execute(new InsertEntryTask(entry, calendar));
    }

    private boolean requiresInsertEntry(CalendarEvent evt) {
        if (evt != null && evt.getEventType().equals(ScheduleCalendarEvent.ENTRY_CALENDAR_CHANGED) && evt.getCalendar() != null && evt.getOldCalendar() == null) {
            Entry<?> entry = evt.getEntry();
            if (entry instanceof ScheduleEntry && !entry.isRecurrence()) {
                return true;
            }
        }
        return false;
    }

    private boolean requiresDeleteEntry(CalendarEvent evt) {
        if (evt != null && evt.getEventType().equals(ScheduleCalendarEvent.ENTRY_CALENDAR_CHANGED) && evt.getCalendar() == null && evt.getOldCalendar() != null) {
            Entry<?> entry = evt.getEntry();
            if (entry instanceof ScheduleEntry && !entry.isRecurrence()) {
                return true;
            }
        }
        return false;
    }
}
