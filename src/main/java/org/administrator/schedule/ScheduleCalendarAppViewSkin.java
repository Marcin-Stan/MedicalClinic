package org.administrator.schedule;

import com.calendarfx.model.Entry;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import javafx.beans.InvalidationListener;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;

public class ScheduleCalendarAppViewSkin extends SkinBase<ScheduleCalendarAppView> {

    private final BorderPane calendarPane;
    private final CalendarView calendarView;
    private final ScheduleSynManager syncManager;

    public ScheduleCalendarAppViewSkin(ScheduleCalendarAppView control) {
        super(control);

        calendarView = control.getCalendarView();
        syncManager = new ScheduleSynManager();

        calendarView.setEntryFactory(new ScheduleEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new ScheduleEntryPopOverContentProvider());
        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);

        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        ScheduleAccount scheduleAccount = new ScheduleAccount();

        scheduleAccount.addCalendarListeners(syncManager);
        ScheduleTaskExecutor.getInstance().execute(new LoadAllCalendarsTask(scheduleAccount));

        getChildren().add(new StackPane(calendarPane));

        timeUpdateThread.start();

    }

    public static CalendarView test() {
        CalendarView calendarView = new CalendarView();
        calendarView.setEntryFactory(new ScheduleEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new ScheduleEntryPopOverContentProvider());
        return calendarView;
    }


    private static class ScheduleEntryCreateCallback implements Callback<DateControl.CreateEntryParameter, Entry<?>> {

        @Override
        public Entry<?> call(DateControl.CreateEntryParameter param) {
            ScheduleCalendar primaryCalendar = new ScheduleCalendar();
            ScheduleEntry entry = null;
            if (primaryCalendar != null) {
                DateControl control = param.getDateControl();
                VirtualGrid grid = control.getVirtualGrid();
                ZonedDateTime start = param.getZonedDateTime();
                DayOfWeek firstDayOfWeek = control.getFirstDayOfWeek();
                ZonedDateTime lowerTime = grid.adjustTime(start, false, firstDayOfWeek);
                ZonedDateTime upperTime = grid.adjustTime(start, true, firstDayOfWeek);

                if (Duration.between(start, lowerTime).abs().minus(Duration.between(start, upperTime).abs()).isNegative()) {
                    start = lowerTime;
                }
                entry = primaryCalendar.createEntry(start, control instanceof AllDayView);
                //ScheduleTaskExecutor.getInstance().execute(new InsertEntryTask(entry,primaryCalendar));
            }
            return entry;
        }
    }

    private static class ScheduleEntryPopOverContentProvider implements Callback<DateControl.EntryDetailsPopOverContentParameter, Node> {
        @Override
        public Node call(DateControl.EntryDetailsPopOverContentParameter param) {
            PopOver popOver = param.getPopOver();
            ScheduleEntry entry = (ScheduleEntry) param.getEntry();

            InvalidationListener listener = obs -> {
                if (entry.isFullDay() && !popOver.isDetached()) {
                    popOver.setDetached(true);
                }
            };

            entry.fullDayProperty().addListener(listener);
            popOver.setOnHidden(evt -> entry.fullDayProperty().removeListener(listener));

            return new ScheduleEntryPopOverContentPane(entry, param.getDateControl().getCalendars());
        }
    }


}
