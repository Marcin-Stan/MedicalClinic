package org.administrator.schedule;
import com.calendarfx.model.*;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.*;
import com.calendarfx.view.popover.EntryDetailsView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.department.DepartmentEntity;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//EntryDetailsVIew trzeba zmienić
public class Schedule extends Application {

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);

    CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    List<EmployeeEntity> entityList = employeeEntityCRUD.getAll(EmployeeEntity.class);

    List<Calendar> calendarList = new ArrayList<>();
    List<ScheduleEntry> entryList = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView();

        for(int i=0;i<departmentList.size();i++){
           calendarList.add(new Calendar(departmentList.get(i).getName()));
           calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
           calendarList.get(i).setStyle(Style.valueOf("STYLE"+String.valueOf(1+i)));
        }

        for(int i = 0; i<scheduleList.size();i++){
            entryList.add(new ScheduleEntry());
            Interval interval = new Interval(scheduleList.get(i).getDate(),scheduleList.get(i).getTimeFrom(),scheduleList.get(i).getDate(),scheduleList.get(i).getTimeTo());
            entryList.get(i).setInterval(interval);
            entryList.get(i).setEmployeeEntity(entityList.get(i));
            calendarList.get(i).addEntry(entryList.get(i));
        }

        CalendarSource familyCalendarSource = new CalendarSource("Gabinety");
        familyCalendarSource.getCalendars().addAll(calendarList);
        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);
        //System.out.println(    calendarView.showDeveloperConsoleProperty().get());

        calendarView.setEntryFactory(param-> new ScheduleEntry());

        calendarView.getDayPage().getDetailedDayView().getDayView().setEntryViewFactory(entryy -> {
            DayEntryView entryView = new DayEntryView(entryy);

            entryView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    EntryDetailsView entryDetailsView = new EntryDetailsView(entryView.getEntry());
                    EntryViewBase base = (EntryViewBase) mouseEvent.getSource();
                    ScheduleEntry scheduleEntry = (ScheduleEntry) base.getEntry();
                    calendarView.setEntryDetailsPopOverContentCallback(entryDetailsPopOverContentParameter -> new ScheduleEntryPopOverContentPane(scheduleEntry,calendarList));

                }
            });

            entryView.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    EntryDetailsView entryDetailsView = new EntryDetailsView(entryView.getEntry());
                    EntryViewBase base = (EntryViewBase) mouseEvent.getSource();
                    System.out.println(base.getEntry().getEndTime());
                }
            });
            return entryView;
        } );

        /*
        calendarView.getWeekPage().getDetailedWeekView().getAllDayView().setEntryViewFactory(e -> {
            AllDayEntryView entryView = new AllDayEntryView(e);
            calendarView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    System.out.println("test");
                    AllDayEntryView entryDetailsView = new AllDayEntryView(entryView.getEntry());
                    EntryViewBase base = (EntryViewBase) mouseEvent.getSource();
                    calendarView.setEntryDetailsPopOverContentCallback(entryDetailsPopOverContentParameter -> new ScheduleEntryPopOverContentPane(base.getEntry(),calendarList));

                }
            });
            return entryView;

        });

         */


        StackPane stackPane = new StackPane();
        stackPane.getChildren().addAll(calendarView); // introPane);

        Thread updateTimeThread = new Thread("Calendar: Update Time Thread") {
            @Override
            public void run() {
                while (true) {
                    Platform.runLater(() -> {
                        calendarView.setToday(LocalDate.now());
                        calendarView.setTime(LocalTime.now());
                    });

                    try {
                        // update every 10 seconds
                        sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        };

        updateTimeThread.setPriority(Thread.MIN_PRIORITY);
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();

        Scene scene = new Scene(stackPane);
        primaryStage.setTitle("Calendar");
        primaryStage.setScene(scene);
        primaryStage.setWidth(1300);
        primaryStage.setHeight(1000);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public static void main(String[] args) {
        System.setProperty("calendarfx.developer", "true");
        launch(args);
    }

}