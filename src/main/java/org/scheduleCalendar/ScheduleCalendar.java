package org.scheduleCalendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Interval;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;
import org.joda.time.DateTime;

import java.time.ZonedDateTime;
import java.util.List;

public class ScheduleCalendar extends Calendar {

    CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);

    public ScheduleCalendar() {
        super();
        this.getName();
    }

    public final ScheduleEntry createEntry(ZonedDateTime start, boolean fullDay) {
        int id;
        ScheduleEntry entry;
        entry = new ScheduleEntry();
        entry.setTitle("Prosze wybrać pracownika");
        entry.setInterval(new Interval(start.toLocalDate(), start.toLocalTime(), start.toLocalDate(), start.toLocalTime().plusHours(1)));
        entry.setFullDay(fullDay);
        if(scheduleList.isEmpty()) id=0;
        else id = Integer.parseInt(DateTime.now().toString("yymmddsss"));

        entry.setId(String.valueOf(id));

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
