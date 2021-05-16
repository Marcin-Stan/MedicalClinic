package org.administrator.schedule;
import com.calendarfx.model.*;
import com.calendarfx.model.Calendar.Style;
import com.calendarfx.view.*;
import com.calendarfx.view.popover.EntryDetailsView;
import impl.com.calendarfx.view.AllDayEntryViewSkin;
import impl.com.calendarfx.view.CalendarViewSkin;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView();
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowDeveloperConsole(Boolean.getBoolean("calendarfx.developer"));

        //calendarView.setEntryEditPolicy(entryEditParameter -> false);

        ScheduleCalendarAppView appView = new ScheduleCalendarAppView(calendarView);
        appView.getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        primaryStage.setTitle("Google Calendar");
        primaryStage.setScene(new Scene(appView));
        primaryStage.setWidth(1400);
        primaryStage.setHeight(950);
        primaryStage.centerOnScreen();
        primaryStage.show();

    }


    public static void main(String[] args) {
        System.setProperty("calendarfx.developer", "true");
        launch(args);
    }



}