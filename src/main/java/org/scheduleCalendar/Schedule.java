package org.scheduleCalendar;
import com.calendarfx.view.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.security.Policy;
import java.time.LocalDate;
import java.time.LocalTime;

//EntryDetailsVIew trzeba zmienić
public class Schedule extends Application {
    private final boolean isEditable;

    public Schedule(boolean isEditable) {
        this.isEditable = isEditable;
    }

    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView();
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowDeveloperConsole(Boolean.getBoolean("calendarfx.developer"));
        calendarView.setShowPrintButton(false);
        ScheduleCalendarAppView appView = new ScheduleCalendarAppView(calendarView,isEditable);
        appView.getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        if(!isEditable) {
            calendarView.setEntryEditPolicy(entryEditParameter -> false);
            calendarView.setEntryDetailsCallback(entryDetailsParameter -> false);
        }

        primaryStage.setTitle("Schedule Calendar");
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