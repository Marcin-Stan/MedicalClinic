package org.administrator.schedule;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
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
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class ScheduleCalendarAppViewSkin extends SkinBase<ScheduleCalendarAppView> {

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);
    List<ScheduleCalendar> calendarList = new ArrayList<>();

    private final BorderPane calendarPane;
    private final CalendarView calendarView;
    //private final ScheduleSynManager syncManager;

    public ScheduleCalendarAppViewSkin(ScheduleCalendarAppView control) {
        super(control);

        calendarView = control.getCalendarView();
        //syncManager = new ScheduleSynManager();

        for(int i=0;i<departmentList.size();i++){
            calendarList.add(new ScheduleCalendar());
            calendarList.get(i).setName(departmentList.get(i).getName());
            calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
            calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
        }

        CalendarSource familyCalendarSource = new CalendarSource("Gabinety");
        familyCalendarSource.getCalendars().addAll(calendarList);
        calendarView.getCalendarSources().setAll(familyCalendarSource);



        calendarView.setEntryFactory(new ScheduleEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new ScheduleEntryPopOverContentProvider());
        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);

        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        //ScheduleAccount scheduleAccount = new ScheduleAccount();

        //scheduleAccount.addCalendarListeners(syncManager);
        //ScheduleTaskExecutor.getInstance().execute(new LoadAllCalendarsTask(scheduleAccount));

        getChildren().add(new StackPane(calendarPane));

        timeUpdateThread.start();

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
