package org.scheduleCalendar;

import com.calendarfx.view.CalendarFXControl;
import com.calendarfx.view.CalendarView;
import javafx.scene.control.Skin;
public class ScheduleCalendarAppView extends CalendarFXControl {

    private final CalendarView calendarView;
    private boolean isEditable;
    public ScheduleCalendarAppView(CalendarView calendarView, boolean isEditable) {
        super();
        this.calendarView = calendarView;
        this.isEditable = isEditable;
    }

    @Override
    protected Skin<?> createDefaultSkin() {
        return new ScheduleCalendarAppViewSkin(this,isEditable);
    }



    public CalendarView getCalendarView() {
        return calendarView;
    }
}
