package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import javafx.collections.ListChangeListener;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAccount extends CalendarSource {

    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public ScheduleCalendar getPrimaryCalendar() {
        return (ScheduleCalendar) getCalendars().stream()
                .filter(calendar -> ((ScheduleCalendar) calendar).isPrimary())
                .findFirst()
                .orElse(null);
    }

    public List<ScheduleCalendar> getScheduleCalendars(){
        List<ScheduleCalendar> scheduleCalendars = new ArrayList<>();
        for (Calendar calendar : getCalendars()) {
            if (!(calendar instanceof ScheduleCalendar)) {
                continue;
            }
            scheduleCalendars.add((ScheduleCalendar) calendar);
        }
        return scheduleCalendars;
    }

    @SafeVarargs
    public final void addCalendarListeners(ListChangeListener<Calendar>... listeners) {
        if (listeners != null) {
            for (ListChangeListener<Calendar> listener : listeners) {
                getCalendars().addListener(listener);
            }
        }
    }

    @SafeVarargs
    public final void removeCalendarListeners(ListChangeListener<Calendar>... listeners) {
        if (listeners != null) {
            for (ListChangeListener<Calendar> listener : listeners) {
                getCalendars().removeListener(listener);
            }
        }
    }

}
