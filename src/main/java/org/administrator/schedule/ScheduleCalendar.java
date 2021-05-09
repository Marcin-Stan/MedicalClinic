package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Interval;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;

import java.time.ZonedDateTime;
import java.util.List;

public class ScheduleCalendar extends Calendar {

    private static int entryConsecutive = 1;

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    List<EmployeeEntity> entityList = employeeEntityCRUD.getAll(EmployeeEntity.class);

    public ScheduleCalendar() {
        super();
        this.getName();
    }

    private static int generateEntryConsecutive() {
        return entryConsecutive++;
    }

    public final ScheduleEntry createEntry(ZonedDateTime start, boolean fullDay){
        ScheduleEntry entry = new ScheduleEntry(null);
        entry.setTitle("New Entry" + generateEntryConsecutive());
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
