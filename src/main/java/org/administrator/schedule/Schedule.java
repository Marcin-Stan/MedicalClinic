package org.administrator.schedule;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.ButtonBar;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DayEntryView;
import com.calendarfx.view.DetailedDayView;
import com.calendarfx.view.popover.EntryDetailsView;
import com.calendarfx.view.popover.EntryPopOverContentPane;
import com.calendarfx.view.popover.EntryPropertiesView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;

import java.awt.event.InputMethodEvent;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
//EntryDetailsVIew trzeba zmienić
public class Schedule extends Application {

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);

    CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);


    List<Calendar> calendarList = new ArrayList<>();
    List<Entry> entryList = new ArrayList<>();


    @Override
    public void start(Stage primaryStage) {
        CalendarView calendarView = new CalendarView();

        for(int i = 0; i<scheduleList.size();i++){
            entryList.add(new Entry(scheduleList.get(i).getEmployee().getFirstName()+"\n"+scheduleList.get(i).getEmployee().getFirstName()));
            Interval interval = new Interval(scheduleList.get(i).getDate(),scheduleList.get(i).getTimeFrom(),scheduleList.get(i).getDate(),scheduleList.get(i).getTimeTo());
            entryList.get(i).setInterval(interval);

        }

        for(int i=0;i<departmentList.size();i++){
           calendarList.add(new Calendar(departmentList.get(i).getName()));
           calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
           calendarList.get(i).setStyle(Style.valueOf("STYLE"+String.valueOf(1+i)));
           calendarList.get(i).addEntry(entryList.get(0));
        }

        ScheduleEntryDetailsView detailsView = new ScheduleEntryDetailsView(entryList.get(0));


        CalendarSource familyCalendarSource = new CalendarSource("Gabinety");
        familyCalendarSource.getCalendars().addAll(calendarList);
        calendarView.getCalendarSources().setAll(familyCalendarSource);
        calendarView.setRequestedTime(LocalTime.now());
        calendarView.setShowAddCalendarButton(false);
        calendarView.setShowDeveloperConsole(true);
        System.out.println(    calendarView.showDeveloperConsoleProperty().get());

        /*
        calendarView.getDayPage().getDetailedDayView().getDayView().setEntryViewFactory(entry -> {
            DayEntryView entryView = new DayEntryView(entry);
            entryView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    EntryDetailsView entryDetailsView = new EntryDetailsView(entryView.getEntry());
                    System.out.println("test");
                }
            });
            return entryView;
        } );

         */
       calendarView.setEntryDetailsPopOverContentCallback(entryDetailsPopOverContentParameter -> new ScheduleEntryPopOverContentPane(entryList.get(0),calendarList));

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


