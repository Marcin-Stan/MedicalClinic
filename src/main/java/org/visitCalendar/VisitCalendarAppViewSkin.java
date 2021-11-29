package org.visitCalendar;

import com.calendarfx.model.*;
import com.calendarfx.view.*;
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
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.visit.VisitEntity;
import org.scheduleCalendar.*;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public VisitCalendarAppViewSkin(VisitCalendarAppView control, boolean isMedic, EmployeeEntity employeeEntity) {
        super(control);
        calendarView = control.getCalendarView();
        setCalendars(isMedic);
        if(employeeEntity==null)
            setEntryFromDatabase();
        else
            setEntryFromDatabase(employeeEntity);


        calendarView.setEntryFactory(new VisitCalendarAppViewSkin.VisitEntryCreateCallback());
        if(!isMedic)
            calendarView.setEntryDetailsPopOverContentCallback(new VisitCalendarAppViewSkin.VisitEntryPopOverContentProvider());
        else
            calendarView.setEntryDetailsPopOverContentCallback(new VisitCalendarAppViewSkin.VisitEntryPopOverContentProviderMedic());

        CalendarViewTimeUpdateThread timeUpdateThread = new CalendarViewTimeUpdateThread(calendarView);
        calendarView.setShowAddCalendarButton(false);
        calendarPane = new BorderPane();
        calendarPane.setCenter(calendarView);

        setStackPane(employeeEntity, isMedic);

        timeUpdateThread.start();
    }

    private void setStackPane(EmployeeEntity employeeEntity, Boolean isMedic){
        StackPane stackPane = new StackPane(calendarPane);
        Button scheduleButton = new Button("Schedule");
        Button patientButton = new Button("Patient");
        Button refreshButton = new Button("Refresh");
        Button visitButton = new Button("Visits");
        scheduleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                Schedule schedule = new Schedule(false);
                schedule.start(stage);
            }
        });

        refreshButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) refreshButton.getScene().getWindow();
                Visit visit = new Visit(isMedic,employeeEntity);
                visit.start(stage);
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

        visitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                try {
                    StackPane pane =  FXMLLoader.load(((getClass().getResource("visit/Visit.fxml"))));
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
        if(employeeEntity==null)
            hBox.getChildren().addAll(scheduleButton,patientButton,refreshButton);
        else
            hBox.getChildren().addAll(scheduleButton,refreshButton,visitButton);
        hBox.setMinSize(200,25);
        hBox.setMaxSize(200,25);

        stackPane.getChildren().add(hBox);
        StackPane.setAlignment(hBox,Pos.TOP_CENTER);

        StackPane.setMargin(hBox,new Insets(10,500,0,0));

        getChildren().add(stackPane);
    }

    private EventHandler<CalendarEvent> setCalendarEvent(){
        final VisitEntity[] visitEntity = new VisitEntity[1];

        EventHandler<CalendarEvent> handler = new EventHandler<CalendarEvent>() {
            @Override
            public void handle(CalendarEvent calendarEvent) {
                VisitEntry visitEntry = (VisitEntry) calendarEvent.getEntry();

                if(!calendarEvent.getSource().equals(visitEntry.getCalendar())){
                    visitEntityCRUD.delete(visitEntry.getVisitEntity());
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getOldCalendar()!=null && calendarEvent.getSource().equals(visitEntry.getCalendar())){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setService(null);
                    visitEntry.setService(null,true);
                    visitEntry.setServiceProperty();
                    printTitle(visitEntry, visitEntry.getVisitEntity());
                    visitEntityCRUD.update(visitEntityTemp,true);
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_CALENDAR_CHANGED)
                        && calendarEvent.getOldCalendar()==null
                        && !fromDatabase) {

                    visitEntity[0] = new VisitEntity(Integer.parseInt(visitEntry.getId()),
                            visitEntry.getStartDate(),
                            visitEntry.getEndDate(),
                            visitEntry.getStartTime(),
                            visitEntry.getEndTime(),
                            false,
                            false,
                            visitEntry.getService(),
                            visitEntry.getEmployee(),
                            visitEntry.getPatient());

                    visitEntityCRUD.save(visitEntity[0]);
                    visitEntry.setVisitEntity(visitEntity[0],false);
                    //visitEntry.setIsPaid(visitEntity[0].getPaid(),false);
                    //calendarView.getDayPage().getAgendaView().refreshData();
                }

                if(calendarEvent.getEventType().equals(CalendarEvent.ENTRY_INTERVAL_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setStartDate(visitEntry.getStartDate());
                    visitEntityTemp.setEndDate(visitEntry.getEndDate());
                    visitEntityTemp.setTimeFrom(visitEntry.getStartTime());
                    visitEntityTemp.setTimeTo(visitEntry.getEndTime());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();

                }

                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_EMPLOYEE_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setEmployee(visitEntry.getEmployee());
                    printTitle(visitEntry, visitEntry.getVisitEntity());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_SERVICE_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setService(visitEntry.getService());
                    printTitle(visitEntry, visitEntry.getVisitEntity());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_PATIENT_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setPatient(visitEntry.getPatient());
                    visitEntityCRUD.update(visitEntityTemp,false);

                    printTitle(visitEntry, visitEntry.getVisitEntity());
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_IS_PAID_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setPaid(visitEntry.getIsPaid());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_IS_FINISHED_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setFinished(visitEntry.getIsFinished());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    printTitle(visitEntry, visitEntry.getVisitEntity());
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }
                if(calendarEvent.getEventType().equals(VisitCalendarEvent.ENTRY_NOTE_CHANGED)){
                    VisitEntity visitEntityTemp = visitEntry.getVisitEntity();
                    visitEntityTemp.setPatientNote(visitEntry.getNote());
                    visitEntityCRUD.update(visitEntityTemp,false);
                    calendarView.getDayPage().getAgendaView().refreshData();
                    calendarView.getDayPage().refreshData();
                }

            }

        };
        return handler;

    }

    private void setCalendars(boolean isMedic){

        for(int i=0;i<departmentList.size();i++){
            calendarList.add(new VisitCalendar());
            calendarList.get(i).setName(departmentList.get(i).getName());
            calendarList.get(i).setShortName(departmentList.get(i).getName().substring(8,12));
            calendarList.get(i).setStyle(Calendar.Style.valueOf("STYLE"+String.valueOf(1+i)));
            calendarList.get(i).addEventHandler(setCalendarEvent());
            if(isMedic){
                calendarList.get(i).setReadOnly(true);
            }
        }

        CalendarSource calendarSource = new CalendarSource("Gabinety");
        calendarSource.getCalendars().addAll(calendarList);
        this.calendarView.getCalendarSources().setAll(calendarSource);

    }

    private void setEntryFromDatabase() {
        List<VisitEntry> entryList = getEntryListFromDatabase();
        fromDatabase = true;
        for (int i = 0; i <entryList.size(); i++){
            if (entryList.get(i).getService() != null) {
                for (int j = 0; j < calendarList.size(); j++) {
                    if (entryList.get(i).getVisitEntity().getService().getDepartmentEntity().getName().equals(calendarList.get(j).getName())) {
                        calendarList.get(j).addEntry(entryList.get(i));
                        break;
                    }
                }
            } else {
                calendarList.get(0).addEntry(entryList.get(i));
            }

        }
        fromDatabase = false;
    }

    private void setEntryFromDatabase(EmployeeEntity employeeEntity) {
        List<VisitEntry> entryList = getEntryListFromDatabase();
        fromDatabase = true;
        for (int i = 0; i <entryList.size(); i++){
            if(entryList.get(i).getEmployee()!=null && entryList.get(i).getEmployee().getId()==employeeEntity.getId()){
                if (entryList.get(i).getService() != null) {
                    for (int j = 0; j < calendarList.size(); j++) {
                        if (entryList.get(i).getVisitEntity().getService().getDepartmentEntity().getName().equals(calendarList.get(j).getName())) {
                            calendarList.get(j).addEntry(entryList.get(i));
                            break;
                        }
                    }
                }
            }
        }
        fromDatabase = false;
    }


    private List<VisitEntry> getEntryListFromDatabase(){
        for(int i=0;i<visitList.size();i++){
            Interval interval = new Interval(visitList.get(i).getStartDate(),
                    visitList.get(i).getTimeFrom(),
                    visitList.get(i).getEndDate(),
                    visitList.get(i).getTimeTo()
            );

            entryList.add(new VisitEntry());
            entryList.get(i).setInterval(interval);
            entryList.get(i).setId(String.valueOf(visitList.get(i).getId()));
            entryList.get(i).setVisitEntity(visitList.get(i),true);
            entryList.get(i).setEmployee(visitList.get(i).getEmployee(),true);
            entryList.get(i).setService(visitList.get(i).getService(),true);
            entryList.get(i).setPatient(visitList.get(i).getPatient(),true);
            entryList.get(i).setIsPaid(visitList.get(i).getPaid(),true);
            entryList.get(i).setIsFinished(visitList.get(i).getFinished(),true);
            entryList.get(i).setNote(visitList.get(i).getPatientNote(),true);
            printTitle(entryList.get(i),entryList.get(i).getVisitEntity());

        }
        return entryList;
    }

    private void printTitle(VisitEntry visitEntry, VisitEntity visitEntity){
        if(visitEntity.getPatient()!=null
                && visitEntity.getService()!=null
                && visitEntity.getEmployee()!=null){

            if(visitEntity.getFinished()){
                visitEntry.setTitle("Pacjent "+visitEntity.getPatient().getFirstName()+" " +
                        visitEntity.getPatient().getLastName()+
                        "\n"+
                        "Pesel "+visitEntity.getPatient().getPeselNumber()+
                        "\n"+
                        "Usługa " +visitEntity.getService()+
                        "\n"+
                        "Wizyta zakończona");
            }else{
                visitEntry.setTitle("Pacjent "+visitEntity.getPatient().getFirstName()+" " +
                        visitEntity.getPatient().getLastName()+
                        "\n"+
                        "Pesel "+visitEntity.getPatient().getPeselNumber()+
                        "\n"+
                        "Usługa " +visitEntity.getService());
            }

        }else {
            visitEntry.setTitle("Prosze uzupelnić wszystkie dane wizyty");
        }

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
            System.out.println("VisitEntryPopOverContentProvider");
            return new VisitEntryPopOverContentPane(entry, param.getDateControl().getCalendars());

        }
    }

    private static class VisitEntryPopOverContentProviderMedic implements Callback<DateControl.EntryDetailsPopOverContentParameter, Node> {
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
            System.out.println("VisitEntryPopOverContentProvider");
            return new VisitEntryPopOverContentPaneMedic(entry, param.getDateControl().getCalendars());

        }
    }

}
