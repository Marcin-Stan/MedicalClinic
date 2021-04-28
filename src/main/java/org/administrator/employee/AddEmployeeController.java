package org.administrator.employee;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.specialization.Specialization;
import org.database.specialization.SpecializationEntity;
import org.employee.EmployeeType;
import org.employee.Sex;
import org.image.ImageFx;
import org.table.Add;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable, Add {

    @FXML
    ComboBox<SpecializationEntity> specializationComboBox;

    @FXML
    AnchorPane addEmployeeAnchorPane;

    @FXML
    Label imageLabel;

    @FXML
    ImageView itemImage;

    @FXML
    TextField firstNameTextField,
            addressTextField,
            lastNameTextField,
            telNumberTextField,
            loginTextField,
            passwordTextField,
            imagePathTxtField;
    @FXML
    ComboBox<EmployeeType> roleComboBox;

    @FXML
    ComboBox<org.employee.Sex> sexComboBox;

    @FXML
    Button closeButton,
            saveButton;

    @FXML
    JFXButton addButton;

    @FXML
    DatePicker birthDateDatePicker,
            creationDateDatePicker;


    ImageFx imageFx = new ImageFx();

    StackPane parentStackPane;
    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();

    public void setParentStackPane(StackPane parentStackPane) {
        this.parentStackPane = parentStackPane;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        specializationComboBox.getItems().setAll(specializationEntityCRUD.getAll(SpecializationEntity.class));
        specializationComboBox.getItems().add(null);
        sexComboBox.getItems().setAll(Sex.values());
        roleComboBox.getItems().setAll(EmployeeType.values());
    }

    @FXML
    public void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void setSaveButton(){

        EmployeeEntity newEmploee = new EmployeeEntity();
        newEmploee.setFirstName(firstNameTextField.getText());
        newEmploee.setLastName(lastNameTextField.getText());
        newEmploee.setAddress(addressTextField.getText());
        newEmploee.setTelNumber(telNumberTextField.getText());
        newEmploee.setLogin(loginTextField.getText());
        newEmploee.setPassword(passwordTextField.getText());
        newEmploee.setPhoto(ImageFx.getimage(imagePathTxtField));
        newEmploee.setSpecializationEntity(specializationComboBox.getValue());
        newEmploee.setEmployeeType(roleComboBox.getValue());
        newEmploee.setSex(sexComboBox.getValue());
        newEmploee.setCreationDate((creationDateDatePicker.getValue()));
        newEmploee.setBirtDate((birthDateDatePicker.getValue()));


        if(employeeEntityCRUD.save(newEmploee)){

            try {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane pane = FXMLLoader.load(getClass().getResource("Employee.fxml"));
                parentStackPane.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    @FXML
    public void addsImage() throws MalformedURLException {
        imageFx.addImage(imagePathTxtField,itemImage);
    }



}
