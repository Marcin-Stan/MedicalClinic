package org.administrator.schedule;

import com.calendarfx.view.CalendarFXControl;
import com.calendarfx.view.CalendarView;
import javafx.scene.control.Skin;
public class ScheduleCalendarAppView extends CalendarFXControl {

    private final CalendarView calendarView;

    public ScheduleCalendarAppView(CalendarView calendarView) {
        super();
        this.calendarView = calendarView;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ScheduleCalendarAppViewSkin(this);
    }



    public CalendarView getCalendarView() {
        return calendarView;
    }
}
