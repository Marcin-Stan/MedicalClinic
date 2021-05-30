package org.visitCalendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.Interval;
import org.database.operation.CRUD;
import org.database.visit.VisitEntity;
import org.joda.time.DateTime;

import java.time.ZonedDateTime;
import java.util.List;

public class VisitCalendar extends Calendar {
    CRUD<VisitEntity> visitEntityCRUD = new CRUD<>();
    List<VisitEntity> visitEntityList = visitEntityCRUD.getAll(VisitEntity.class);

    public VisitCalendar(){
        super();
        this.getName();
    }

    public final VisitEntry createEntry(ZonedDateTime start, boolean fullDay) {
        int id;
        VisitEntry entry;
        entry = new VisitEntry();
        entry.setTitle("Prosze uzupelnić wszystkie dane wizyty");
        entry.setInterval(new Interval(start.toLocalDate(), start.toLocalTime(), start.toLocalDate(), start.toLocalTime().plusHours(1)));
        entry.setFullDay(fullDay);
        if(visitEntityList.isEmpty())
            id=0;
        else
            id = Integer.parseInt(DateTime.now().toString("yymmddsss"));
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
