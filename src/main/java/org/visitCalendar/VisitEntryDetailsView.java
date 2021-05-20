package org.visitCalendar;

import com.calendarfx.view.popover.EntryDetailsView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import org.database.department.Department;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.patient.Patient;
import org.database.patient.PatientEntity;
import org.database.schedule.Schedule;
import org.database.service.Service;
import org.database.service.ServiceEntity;
import org.validator.AlertValidator;

import java.util.List;
import java.util.Locale;


public class VisitEntryDetailsView extends EntryDetailsView {
        CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
        ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();

        CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();
        ComboBox<PatientEntity> patientEntityComboBox = new ComboBox<>();

        ComboBox<ServiceEntity> serviceEntityComboBox = new ComboBox<>();

        CheckBox isPaid= new CheckBox();

    public VisitEntryDetailsView(VisitEntry entry){
        super(entry);

       // employeeEntityComboBox.getItems().setAll(employeeEntityCRUD.getAll(EmployeeEntity.class));

        employeeEntityComboBox.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                employeeEntityComboBox.getItems().setAll(Schedule.getEmployeeByInterval(entry.getStartDate(),
                            entry.getEndDate(),
                            entry.getStartTime(),
                            entry.getEndTime(),
                            Department.getDepartmentByName(entry.getCalendar().getName())));

                if(employeeEntityComboBox.getItems().isEmpty()){
                    AlertValidator.printALert("Błąd",
                            "Nie mozna wybrac pracownika",
                            "Sprawdź grafik",
                            Alert.AlertType.ERROR);
                }else
                    employeeEntityComboBox.valueProperty().bindBidirectional(entry.employeeProperty());
            }
        });

        serviceEntityComboBox.getItems().setAll(Service.getServiceByDepartmentName(Department.getDepartmentByName(entry.getCalendar().getName())));
        serviceEntityComboBox.valueProperty().bindBidirectional(entry.serviceProperty());

        setPatientEntityComboBox(entry);

        isPaid.selectedProperty().bindBidirectional(entry.isPaidProperty());

        GridPane box = (GridPane) getChildren().get(0);
        Label employee = new Label("Employee");
        Label patient = new Label("Patient");
        Label service = new Label("Service");
        Label paid = new Label("Paid");

        box.getChildren().remove(6);
        box.getChildren().remove(6);

        box.getChildren().remove(1);
        box.getChildren().remove(0);

        box.getChildren().remove(5);
        box.getChildren().remove(4);


        box.add(patientEntityComboBox,1,3);
        box.add(patient,0,3);
        GridPane.setValignment(patientEntityComboBox,VPos.TOP);

        box.add(serviceEntityComboBox,1,4);
        box.add(service,0,4);
        GridPane.setValignment(serviceEntityComboBox,VPos.TOP);

        box.add(employeeEntityComboBox, 1, 5);
        box.add(employee,0,5);
        GridPane.setValignment(employeeEntityComboBox, VPos.TOP);

        box.add(isPaid,1,6);
        box.add(paid,0,6);
        GridPane.setValignment(isPaid, VPos.TOP);

        //box.getChildren().get(0);
    }

    private void setPatientEntityComboBox(VisitEntry entry){

        patientEntityComboBox.setEditable(true);
        patientEntityComboBox.setConverter(new StringConverter<PatientEntity>() {
            @Override
            public String toString(PatientEntity patientEntity) {
                if (patientEntity == null) return null;
                return patientEntity.toString();
            }

            @Override
            public PatientEntity fromString(String s) {
                if(!s.isEmpty())
                {
                    String pesel = s.replaceAll("\\D+","");
                    return Patient.getPatientByPeselNumber(pesel);
                }

                return null;
            }
        });

        List<PatientEntity> items2 = patientEntityCRUD.getAll(PatientEntity.class);
        ObservableList<PatientEntity> items = FXCollections.observableList(items2);
        FilteredList<PatientEntity> filteredItems = new FilteredList<PatientEntity>(items, p -> true);
        patientEntityComboBox.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
            final TextField editor = patientEntityComboBox.getEditor();
            final PatientEntity selected = patientEntityComboBox.getSelectionModel().getSelectedItem();

            Platform.runLater(() -> {
                if (selected == null || !selected.toString().equals(editor.getText())){
                    filteredItems.setPredicate(item -> {

                        if (item.toString().toUpperCase().startsWith(newValue.toUpperCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    });
                }
            });
        });

        patientEntityComboBox.setItems(filteredItems);
        patientEntityComboBox.valueProperty().bindBidirectional(entry.patientProperty());
    }

    private void setEmployeeEntityComboBox(VisitEntry entry){
       // employeeEntityComboBox.addEventHandler();
    }
}
