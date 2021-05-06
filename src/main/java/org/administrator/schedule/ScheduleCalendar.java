package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Interval;

import java.time.ZonedDateTime;

public class ScheduleCalendar extends Calendar {

    private static int entryConsecutive = 1;

    public ScheduleCalendar() {
        super();
    }

    private static int generateEntryConsecutive() {
        return entryConsecutive++;
    }

    public final ScheduleEntry createEntry(ZonedDateTime start, boolean fullDay){
        ScheduleEntry entry = new ScheduleEntry();
        entry.setTitle("New Entry " + generateEntryConsecutive());
        entry.setInterval(new Interval(start.toLocalDate(), start.toLocalTime(), start.toLocalDate(), start.toLocalTime().plusHours(1)));
        entry.setFullDay(fullDay);
        return entry;
    }
    private String id;
    public final String getId() {
        return id;
    }
    public final void setId(String id) {
        this.id = id;
    }
    private boolean primary;
    public final boolean isPrimary() {
        return primary;
    }
    public final void setPrimary(boolean primary) {
        this.primary = primary;
    }


}
