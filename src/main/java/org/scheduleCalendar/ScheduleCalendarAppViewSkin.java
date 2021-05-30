package org.scheduleCalendar;

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
    List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);
    List<ScheduleEntry> entryList = new ArrayList<>();

    private boolean fromDatabase;

    public ScheduleCalendarAppViewSkin(ScheduleCalendarAppView control,boolean isEditable) {
        super(control);

        calendarView = control.getCalendarView();
        setCalendars(isEditable);
        setEntryFromDatabase();

        calendarView.setEntryFactory(new ScheduleEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new ScheduleEntryPopOverContentProvider());
        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);
        calendarView.setShowAddCalendarButton(false);
        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        getChildren().add(new StackPane(calendarPane));

        timeUpdateThread.start();
      }

    private void setCalendars(boolean isEditable){

        for(int i=0;i<departmentList.size();i++){
            calendarList.add(new ScheduleCalendar());
            calendarList.get(i).setName(departmentList.get(i).getName());
            calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
            calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
            calendarList.get(i).addEventHandler(setCalendarEvent());
            if (!isEditable)
                calendarList.get(i).setReadOnly(true);
        }

        CalendarSource calendarSource = new CalendarSource("Gabinety");
        calendarSource.getCalendars().addAll(calendarList);
        this.calendarView.getCalendarSources().setAll(calendarSource);

    }

    private EventHandler<CalendarEvent> setCalendarEvent(){
        final ScheduleEntity[] scheduleEntity = new ScheduleEntity[1];

        EventHandler<CalendarEvent> handler = new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                ScheduleEntry scheduleEntry = (ScheduleEntry) calendarEvent.getEntry();

                if(!calendarEvent.getSource().equals(scheduleEntry.getCalendar())){
                    scheduleEntityCRUD.delete(scheduleEntry.getScheduleEntity());
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getOldCalendar()!=null && calendarEvent.getSource().equals(scheduleEntry.getCalendar())){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setDepartment(Department.getDepartmentByName(calendarEvent.getCalendar().getName()));
                    printTitle(scheduleEntry,scheduleEntry.getScheduleEntity());
                    scheduleEntityCRUD.update(scheduleEntityTemp,true);
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_CALENDAR_CHANGED)
                        && calendarEvent.getOldCalendar()==null
                        && !fromDatabase) {

                    scheduleEntity[0] = new ScheduleEntity(Integer.parseInt(scheduleEntry.getId()),
                            scheduleEntry.getStartDate(),
                            scheduleEntry.getEndDate(),
                            scheduleEntry.getStartTime(),
                            scheduleEntry.getEndTime(),
                            scheduleEntry.getEmployee(),
                            Department.getDepartmentByName(scheduleEntry.getCalendar().getName()));

                    scheduleEntityCRUD.save(scheduleEntity[0]);
                    scheduleEntry.setScheduleEntity(scheduleEntity[0],false);
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_USER_OBJECT_CHANGED)){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setEmployee(scheduleEntry.getEmployee());
                    printTitle(scheduleEntry,scheduleEntry.getScheduleEntity());
                    scheduleEntityCRUD.update(scheduleEntityTemp,true);
                    calendarEvent.getEntry().setTitle(scheduleEntityTemp.getEmployee().getFirstName()+" " + scheduleEntityTemp.getEmployee().getLastName());
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)){
                    ScheduleEntity scheduleEntityTemp = scheduleEntry.getScheduleEntity();
                    scheduleEntityTemp.setStartDate(scheduleEntry.getStartDate());
                    scheduleEntityTemp.setEndDate(scheduleEntry.getEndDate());
                    scheduleEntityTemp.setTimeFrom(scheduleEntry.getStartTime());
                    scheduleEntityTemp.setTimeTo(scheduleEntry.getEndTime());
                    scheduleEntityCRUD.update(scheduleEntityTemp,false);
                    calendarView.getDayPage().refreshData();
                }

            }

        };
        return handler;
    }

    private void setEntryFromDatabase(){
        List<ScheduleEntry> entryList = getEntryListFromDatabase();
        fromDatabase=true;
        for(int i=0;i<entryList.size();i++){

            for(int j=0; j<calendarList.size();j++){
                if(entryList.get(i).getScheduleEntity().getDepartment().getName().equals(calendarList.get(j).getName())){
                    calendarList.get(j).addEntry(entryList.get(i));
                    break;
                }
            }
        }
        fromDatabase=false;
    }

    private List<ScheduleEntry> getEntryListFromDatabase(){

        for(int i = 0; i<scheduleList.size();i++){
            Interval interval = new Interval(scheduleList.get(i).getStartDate(),
                    scheduleList.get(i).getTimeFrom(),
                    scheduleList.get(i).getEndDate(),
                    scheduleList.get(i).getTimeTo()
            );

            entryList.add(new ScheduleEntry());
            entryList.get(i).setInterval(interval);
            entryList.get(i).setId(String.valueOf(scheduleList.get(i).getId()));
            entryList.get(i).setScheduleEntity(scheduleList.get(i),true);
            entryList.get(i).setEmployee(scheduleList.get(i).getEmployee(),true);
            printTitle(entryList.get(i),entryList.get(i).getScheduleEntity());
            if(scheduleList.get(i).getEmployee() != null){
                entryList.get(i).setTitle(scheduleList.get(i).getEmployee().getFirstName()+" "+ scheduleList.get(i).getEmployee().getLastName());
            }

        }
        return entryList;
    }

    private void printTitle(ScheduleEntry scheduleEntry, ScheduleEntity scheduleEntity){
        if(scheduleEntity.getEmployee()!=null){
            scheduleEntry.setTitle(scheduleEntity.getEmployee().getFirstName()+" "+scheduleEntity.getEmployee().getLastName());
        }else {
            scheduleEntry.setTitle("Prosze wybrać pracownika");
        }

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
