package org.administrator.schedule;

import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class TestowySchedule extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {

        CalendarView calendarView = new CalendarView();
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowDeveloperConsole(Boolean.getBoolean("calendarfx.developer"));

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
        launch(args);
    }
}
