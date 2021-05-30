package org.visitCalendar.patient;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.employee.Sex;
import org.table.Add;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPatientController implements Initializable, Add {

    @FXML
    TextField firstNameTextField,
            addressTextField,
            lastNameTextField,
            telNumberTextField,
            peselTextField;
    @FXML
    Button closeButton,saveButton;

    @FXML
    ComboBox<Sex> sexComboBox;

    @FXML
    DatePicker birthDateDatePicker,
            creationDateDatePicker;

    StackPane parentStackPane;

    CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sexComboBox.getItems().setAll(Sex.values());
    }

    @Override
    public void setParentStackPane(StackPane parentStackPane) {
        this.parentStackPane = parentStackPane;
    }

    @FXML
    public void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setSaveButton(){
        PatientEntity newPatient = new PatientEntity();

        newPatient.setFirstName(firstNameTextField.getText());
        newPatient.setLastName(lastNameTextField.getText());
        newPatient.setAddress(addressTextField.getText());
        newPatient.setTelNumber(telNumberTextField.getText());
        newPatient.setPeselNumber(peselTextField.getText());

        newPatient.setSex(sexComboBox.getValue());

        newPatient.setCreationDate((creationDateDatePicker.getValue()));
        newPatient.setBirtDate((birthDateDatePicker.getValue()));

        if(patientEntityCRUD.save(newPatient)){
            try {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane pane = FXMLLoader.load(getClass().getResource("Patient.fxml"));
                parentStackPane.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
