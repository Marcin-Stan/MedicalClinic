package org.administrator.patient;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.employee.Sex;
import org.table.Manage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManagePatientController implements Initializable, Manage<PatientEntity> {

    @FXML
    Button closeButton,
            saveButton,
            deleteButton;

    @FXML
    JFXButton editButton;

    @FXML
    ComboBox<Sex> sexComboBox;

    @FXML
    TextField firstNameTextField,
            addressTextField,
            lastNameTextField,
            telNumberTextField,
            peselTextField;

    @FXML
    DatePicker birthDateDatePicker,
            creationDateDatePicker;

    @FXML
    AnchorPane managePatientAnchorPane;


    StackPane parentStackPane;
    PatientEntity patient;

    CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void initData(PatientEntity patient){
        this.patient = patient;
        firstNameTextField.setText(patient.getFirstName());
        addressTextField.setText(patient.getAddress());
        lastNameTextField.setText(patient.getLastName());
        telNumberTextField.setText(patient.getTelNumber());
        peselTextField.setText(patient.getPeselNumber());

        birthDateDatePicker.setValue(patient.getBirtDate());
        creationDateDatePicker.setValue(patient.getCreationDate());

        sexComboBox.setValue(patient.getSex());
    }

    public void setParentStackPane(StackPane parentStackPane) {
        this.parentStackPane = parentStackPane;
    }

    @FXML
    private void setEditButton(){
        firstNameTextField.setDisable(false);
        firstNameTextField.setEditable(true);

        addressTextField.setDisable(false);
        addressTextField.setEditable(true);

        lastNameTextField.setDisable(false);
        lastNameTextField.setEditable(true);

        telNumberTextField.setDisable(false);
        telNumberTextField.setEditable(true);

        peselTextField.setDisable(false);
        peselTextField.setEditable(true);

        sexComboBox.setDisable(false);
        sexComboBox.getItems().setAll(Sex.values());

        birthDateDatePicker.setDisable(false);
        creationDateDatePicker.setDisable(false);

    }

    @FXML
    public void setCloseButton() {
        setCloseButton(closeButton,"Patient.fxml",parentStackPane);
    }


    @FXML
    private void setDeleteButton(){
      setDeleteButton(patientEntityCRUD,patient,closeButton,parentStackPane,"Patient.fxml");
    }


    @FXML
    public void setSaveButton(){

        PatientEntity updatedPatient = new PatientEntity();
        updatedPatient.setId(patient.getId());
        updatedPatient.setFirstName(firstNameTextField.getText());
        updatedPatient.setLastName(lastNameTextField.getText());
        updatedPatient.setAddress(addressTextField.getText());
        updatedPatient.setTelNumber(telNumberTextField.getText());
        updatedPatient.setPeselNumber(peselTextField.getText());

        updatedPatient.setSex(sexComboBox.getValue());

        updatedPatient.setCreationDate((creationDateDatePicker.getValue()));
        updatedPatient.setBirtDate((birthDateDatePicker.getValue()));

        patientEntityCRUD.update(updatedPatient,true);

    }

}
