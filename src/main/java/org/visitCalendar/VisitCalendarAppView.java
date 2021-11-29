package org.visitCalendar;

import com.calendarfx.view.CalendarFXControl;
import com.calendarfx.view.CalendarView;
import javafx.scene.control.Skin;
import org.database.employee.EmployeeEntity;

public class VisitCalendarAppView extends CalendarFXControl {

    private final CalendarView calendarView;
    private boolean isMedic;
    private EmployeeEntity userId;
    public VisitCalendarAppView(CalendarView calendarView, boolean isMedic, EmployeeEntity userId ) {
        super();
        this.calendarView = calendarView;
        this.isMedic = isMedic;
        this.userId = userId;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new VisitCalendarAppViewSkin(this, isMedic, userId);
    }



    public CalendarView getCalendarView() {
        return calendarView;
    }
}
