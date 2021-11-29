package org.visitCalendar;

import com.calendarfx.view.CalendarView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.database.employee.EmployeeEntity;

import java.time.LocalDate;
import java.time.LocalTime;

public class Visit extends Application {

    private final boolean isMedic;
    private final EmployeeEntity userId;
    public Visit(boolean isMedic, EmployeeEntity userId){
        this.isMedic = isMedic;
        this.userId = userId;
    }

    @Override
    public void start(Stage primaryStage) {

        CalendarView calendarView = new CalendarView();
        calendarView.setToday(LocalDate.now());
        calendarView.setTime(LocalTime.now());
        calendarView.setShowDeveloperConsole(Boolean.getBoolean("calendarfx.developer"));
        calendarView.setShowPrintButton(false);
        //calendarView.setEntryEditPolicy(entryEditParameter -> false);

        VisitCalendarAppView appView = new VisitCalendarAppView(calendarView, isMedic, userId);
        appView.getStylesheets().add(CalendarView.class.getResource("calendar.css").toExternalForm());

        primaryStage.setTitle("Visit Calendar");
        primaryStage.setScene(new Scene(appView));
        primaryStage.setWidth(primaryStage.getWidth());
        primaryStage.setHeight(primaryStage.getHeight());
        //primaryStage.centerOnScreen();
        primaryStage.show();

    }




    public static void main(String[] args) {
        System.setProperty("calendarfx.developer", "true");
        launch(args);
    }
}
