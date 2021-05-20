package org.visitCalendar;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SkinBase;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;
import org.database.visit.VisitEntity;
import org.scheduleCalendar.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VisitCalendarAppViewSkin extends SkinBase<VisitCalendarAppView> {

    private final BorderPane calendarPane;
    private final CalendarView calendarView;

    List<VisitCalendar> calendarList = new ArrayList<>();

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);

    CRUD<VisitEntity> visitEntityCRUD = new CRUD<>();
    List<VisitEntity> visitList = visitEntityCRUD.getAll(VisitEntity.class);

    List<VisitEntry> entryList = new ArrayList<>();

    private boolean fromDatabase;


    public VisitCalendarAppViewSkin(VisitCalendarAppView control) {
        super(control);

        calendarView = control.getCalendarView();
        setCalendars();
        setEntryFromDatabase();

        calendarView.setEntryFactory(new VisitCalendarAppViewSkin.VisitEntryCreateCallback());
        calendarView.setEntryDetailsPopOverContentCallback(new VisitCalendarAppViewSkin.VisitEntryPopOverContentProvider());
        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);
        calendarView.setShowAddCalendarButton(false);
        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        setStackPane();

        timeUpdateThread.start();
    }

    private void setStackPane(){
        StackPane stackPane = new StackPane(calendarPane);
        Button scheduleButton = new Button("Schedule");
        Button patientButton = new Button("Patient");

        scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                Schedule schedule = new Schedule(false);
                schedule.start(stage);
            }
        });

        patientButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    StackPane pane =  FXMLLoader.load(((getClass().getResource("patient/Patient.fxml"))));
                    Stage stage = new Stage();
                    Scene scene = new Scene(pane);
                    stage.setScene(scene);
                    stage.setResizable(true);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        HBox hBox = new HBox();
        hBox.getChildren().addAll(scheduleButton,patientButton);
        hBox.setMinSize(135,25);
        hBox.setMaxSize(135,25);

        stackPane.getChildren().add(hBox);
        StackPane.setAlignment(hBox,Pos.TOP_CENTER);

        StackPane.setMargin(hBox,new Insets(10,400,0,0));

        getChildren().add(stackPane);
    }

    private void setCalendars(){

        for(int i=0;i<departmentList.size();i++){
            calendarList.add(new VisitCalendar());
            calendarList.get(i).setName(departmentList.get(i).getName());
            calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
            calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
            //calendarList.get(i).addEventHandler(setCalendarEvent());
        }

        CalendarSource calendarSource = new CalendarSource("Gabinety");
        calendarSource.getCalendars().addAll(calendarList);
        this.calendarView.getCalendarSources().setAll(calendarSource);

    }

    private void setEntryFromDatabase() {
        List<VisitEntry> entryList = getEntryListFromDatabase();
        fromDatabase = true;
        for (int i = 0; i < entryList.size(); i++) {
            for (int j = 0; i < calendarList.size(); j++) {
                if (entryList.get(i).getVisitEntity().getService().getDepartmentEntity().getName().equals(calendarList.get(j).getName())) {
                    calendarList.get(j).addEntry(entryList.get(i));
                    break;
                }
            }
            fromDatabase = false;
        }
    }


    private List<VisitEntry> getEntryListFromDatabase(){
        for(int i=0;i<visitList.size();i++){
            Interval interval = new Interval(visitList.get(i).getStartDate(),
                    visitList.get(i).getTimeFrom(),
                    visitList.get(i).getEndDate(),
                    visitList.get(i).getTimeTo()
            );

            entryList.add(new VisitEntry(true));
            entryList.get(i).setInterval(interval);
            entryList.get(i).setId(String.valueOf(visitList.get(i).getId()));
            entryList.get(i).setVisitEntity(visitList.get(i));
        }
        return entryList;
    }



    private static class VisitEntryCreateCallback implements Callback<DateControl.CreateEntryParameter, Entry<?>> {

        @Override
        public Entry<?> call(DateControl.CreateEntryParameter param) {
            VisitCalendar primaryCalendar = new VisitCalendar();
            VisitEntry entry = null;
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

    private static class VisitEntryPopOverContentProvider implements Callback<DateControl.EntryDetailsPopOverContentParameter, Node> {
        @Override
        public Node call(DateControl.EntryDetailsPopOverContentParameter param) {
            PopOver popOver = param.getPopOver();
            VisitEntry entry = (VisitEntry) param.getEntry();

            InvalidationListener listener = obs -> {
                if (entry.isFullDay() && !popOver.isDetached()) {
                    popOver.setDetached(true);
                }
            };

            entry.fullDayProperty().addListener(listener);
            popOver.setOnHidden(evt -> entry.fullDayProperty().removeListener(listener));

            return new VisitEntryPopOverContentPane(entry, param.getDateControl().getCalendars());
        }
    }
}
