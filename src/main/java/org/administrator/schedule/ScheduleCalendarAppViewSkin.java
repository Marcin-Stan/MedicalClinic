package org.administrator.schedule;

import com.calendarfx.model.*;
import com.calendarfx.view.*;
import javafx.beans.InvalidationListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import org.database.department.Department;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


public class ScheduleCalendarAppViewSkin extends SkinBase<ScheduleCalendarAppView> {

    private final BorderPane calendarPane;
    private final CalendarView calendarView;
    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);
    List<ScheduleCalendar> calendarList = new ArrayList<>();

    CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    //private final ScheduleSynManager syncManager;

    public ScheduleCalendarAppViewSkin(ScheduleCalendarAppView control) {
        super(control);

        calendarView = control.getCalendarView();
        setCalendars();
        //syncManager = new ScheduleSynManager();

        calendarView.setEntryFactory(new ScheduleEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new ScheduleEntryPopOverContentProvider());
        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);
        calendarView.setShowAddCalendarButton(false);
        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        //ScheduleAccount scheduleAccount = new ScheduleAccount();

        //scheduleAccount.addCalendarListeners(syncManager);
        //ScheduleTaskExecutor.getInstance().execute(new LoadAllCalendarsTask(scheduleAccount));

        getChildren().add(new StackPane(calendarPane));

        timeUpdateThread.start();

    }

    private void setCalendars(){

        final ScheduleEntity[] scheduleEntity = new ScheduleEntity[1];

        EventHandler<CalendarEvent> handler = new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                ScheduleEntry scheduleEntry = (ScheduleEntry) calendarEvent.getEntry();
                if(!calendarEvent.getSource().equals(scheduleEntry.getCalendar())){
                    scheduleEntityCRUD.delete(scheduleEntry.getScheduleEntity());
                }

                if(calendarEvent.getOldCalendar()!=null && calendarEvent.getSource().equals(scheduleEntry.getCalendar())){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setDepartment(Department.getDepartmentByName(calendarEvent.getCalendar().getName()));
                    scheduleEntityCRUD.update(scheduleEntityTemp,true);
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_CALENDAR_CHANGED) && calendarEvent.getOldCalendar()==null) {
                    scheduleEntity[0] = new ScheduleEntity(Integer.parseInt(scheduleEntry.getId()),
                            scheduleEntry.getStartDate(),
                            scheduleEntry.getEndDate(),
                            scheduleEntry.getStartTime(),
                            scheduleEntry.getEndTime(),
                            null,
                            Department.getDepartmentByName(scheduleEntry.getCalendar().getName()));

                    scheduleEntityCRUD.save(scheduleEntity[0]);
                    scheduleEntry.setScheduleEntity(scheduleEntity[0]);
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_FULL_DAY_CHANGED)){
                    System.out.println(scheduleEntry.isFullDay());
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_USER_OBJECT_CHANGED)){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setEmployee(scheduleEntry.getEmployee());
                    scheduleEntityCRUD.update(scheduleEntityTemp,true);
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setStartDate(scheduleEntry.getStartDate());
                    scheduleEntityTemp.setEndDate(scheduleEntry.getEndDate());
                    scheduleEntityTemp.setTimeFrom(scheduleEntry.getStartTime());
                    scheduleEntityTemp.setTimeTo(scheduleEntry.getEndTime());
                    scheduleEntityCRUD.update(scheduleEntityTemp,false);

                }

            }


        };

        for(int i=0;i<departmentList.size();i++){
            calendarList.add(new ScheduleCalendar());
            calendarList.get(i).setName(departmentList.get(i).getName());
            calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
            calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
            calendarList.get(i).addEventHandler(handler);
        }

        CalendarSource familyCalendarSource = new CalendarSource("Gabinety");
        familyCalendarSource.getCalendars().addAll(calendarList);
        this.calendarView.getCalendarSources().setAll(familyCalendarSource);

    }

    private List<ScheduleEntry> setEntryFromDatabase(){
        CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
        List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);
        List<ScheduleEntry> entryList = new ArrayList<>();
        for(int i = 0; i<scheduleList.size();i++){
            Interval interval = new Interval(scheduleList.get(i).getStartDate(),scheduleList.get(i).getTimeFrom(),scheduleList.get(i).getEndDate(),scheduleList.get(i).getTimeTo());
            entryList.add(new ScheduleEntry(scheduleList.get(i).getEmployee()));
            entryList.get(i).setInterval(interval);
            entryList.get(i).setId(String.valueOf(scheduleList.get(i).getId()));
        }
        return entryList;
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
