package org.visitCalendar;

import com.calendarfx.view.CalendarFXControl;
import com.calendarfx.view.CalendarView;
import javafx.scene.control.Skin;

public class VisitCalendarAppView extends CalendarFXControl {

    private final CalendarView calendarView;

    public VisitCalendarAppView(CalendarView calendarView) {
        super();
        this.calendarView = calendarView;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new VisitCalendarAppViewSkin(this);
    }



    public CalendarView getCalendarView() {
        return calendarView;
    }
}
