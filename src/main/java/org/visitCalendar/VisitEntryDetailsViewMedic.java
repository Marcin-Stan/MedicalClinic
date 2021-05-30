package org.visitCalendar;

import com.calendarfx.view.popover.EntryDetailsView;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.database.department.Department;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.patient.Patient;
import org.database.patient.PatientEntity;
import org.database.schedule.Schedule;
import org.database.service.Service;
import org.database.service.ServiceEntity;
import org.fxPrint.FxPrint;
import org.validator.AlertValidator;

import java.util.List;


public class VisitEntryDetailsViewMedic extends EntryDetailsView {
    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();

    CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();
    ComboBox<PatientEntity> patientEntityComboBox = new ComboBox<>();

    ComboBox<ServiceEntity> serviceEntityComboBox = new ComboBox<>();

    CheckBox isPaid= new CheckBox();
    CheckBox isFinished= new CheckBox();
    TextArea textArea = new TextArea();
    Button buttonPrint = new Button("Print");
    public VisitEntryDetailsViewMedic(VisitEntry entry){
        super(entry);
        setButtonPrint(entry);
        if(entry.getEmployee()!=null){
            employeeEntityComboBox.valueProperty().bindBidirectional(entry.employeeProperty());
        }

        employeeEntityComboBox.valueProperty().bindBidirectional(entry.employeeProperty());
        employeeEntityComboBox.setDisable(true);

        serviceEntityComboBox.getItems().setAll(Service.getServiceByDepartmentName(Department.getDepartmentByName(entry.getCalendar().getName())));
        serviceEntityComboBox.valueProperty().bindBidirectional(entry.serviceProperty());
        serviceEntityComboBox.setDisable(true);

        setPatientEntityComboBox(entry);

        isPaid.selectedProperty().bindBidirectional(entry.isPaidProperty());
        isPaid.setDisable(true);
        isFinished.selectedProperty().bindBidirectional(entry.isFinishedProperty());
        textArea.textProperty().bindBidirectional(entry.noteProperty());

        GridPane box = (GridPane) getChildren().get(0);
        Label employee = new Label("Employee");
        Label patient = new Label("Patient");
        Label service = new Label("Service");
        Label paid = new Label("Paid");
        Label note = new Label("Note");
        Label finished = new Label("Finished");

        textArea.setWrapText(true);

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

        box.add(textArea,1,7);
        box.add(note,0,7);
        GridPane.setValignment(textArea, VPos.TOP);

        box.add(isFinished ,1,8);
        box.add(finished,0,8);
        GridPane.setValignment(isFinished,VPos.TOP);

        box.add(buttonPrint,1,9);
        GridPane.setValignment(buttonPrint,VPos.TOP);
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
        patientEntityComboBox.setDisable(true);
    }

    private void setButtonPrint(VisitEntry entry){
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);
        textArea.setText("Pacjent:"+entry.getPatient()+"\n"+
                "Pracownik: "+ entry.getEmployee()+"\n"+
                "Data:" + entry.getEndDate()+"\n"+
                "Godzina: " + entry.getEndTime()+"\n"+
                "Notatka: "+entry.getNote());

        buttonPrint.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = new Stage();
                FxPrint fxPrint = new FxPrint(textArea);
                fxPrint.start(stage);
            }
        });
    }
}
