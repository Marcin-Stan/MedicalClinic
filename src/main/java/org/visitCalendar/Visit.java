package org.visitCalendar;

import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visit extends Application {

    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView();
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowDeveloperConsole(Boolean.getBoolean("calendarfx.developer"));

        //calendarView.setEntryEditPolicy(entryEditParameter -> false);

        VisitCalendarAppView appView = new VisitCalendarAppView(calendarView);
        appView.getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        primaryStage.setTitle("Visit Calendar");
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
