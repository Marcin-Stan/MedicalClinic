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
import org.table.Manage;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageEmployeeController implements Initializable, Manage<EmployeeEntity> {

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
    ComboBox<org.employee.EmployeeType> roleComboBox;
    @FXML
    ComboBox<org.database.specialization.SpecializationEntity> specializationComboBox;
    @FXML
    ComboBox<org.employee.Sex> sexComboBox;

    @FXML
    Button closeButton,
            saveButton,
            deleteButton;

    @FXML
    JFXButton addButton,
            editButton;

    @FXML
    DatePicker birthDateDatePicker,
            creationDateDatePicker;

    @FXML
    AnchorPane manageEmployeeAnchorPane;

    StackPane parentStackPane;
    EmployeeEntity employee;
    ImageFx imageFx = new ImageFx();

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void setCloseButton(){
        setCloseButton(closeButton,"Employee.fxml",parentStackPane);
    }

    public void setParentStackPane(StackPane parentStackPane) {
        this.parentStackPane = parentStackPane;
    }

    public void initData(EmployeeEntity employee){
        this.employee = employee;

        firstNameTextField.setText(employee.getFirstName());
        addressTextField.setText(employee.getAddress());
        lastNameTextField.setText(employee.getLastName());
        telNumberTextField.setText(employee.getTelNumber());
        loginTextField.setText(employee.getLogin());
        passwordTextField.setText(employee.getPassword());

        birthDateDatePicker.setValue(employee.getBirtDate());
        creationDateDatePicker.setValue(employee.getCreationDate());

        roleComboBox.setValue(employee.getEmployeeType());
        if(employee.getSpecializationEntity()!=null)
            specializationComboBox.setValue(employee.getSpecializationEntity());

        sexComboBox.setValue(employee.getSex());

        if(employee.getPhoto()!=null)
            itemImage.setImage(ImageFx.convertToJavaFXImage(employee.getPhoto(),300,300));
    }

    @FXML
    private void setDeleteButton(){
        setDeleteButton(employeeEntityCRUD,employee,closeButton,parentStackPane,"Employee.fxml");
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

        loginTextField.setDisable(false);
        loginTextField.setEditable(true);

        passwordTextField.setDisable(false);
        passwordTextField.setEditable(true);

        imagePathTxtField.setDisable(false);
        imagePathTxtField.setEditable(true);
        imagePathTxtField.setVisible(true);

        roleComboBox.setDisable(false);
        roleComboBox.getItems().setAll(EmployeeType.values());

        specializationComboBox.setDisable(false);
        specializationComboBox.getItems().setAll(specializationEntityCRUD.getAll(SpecializationEntity.class));
        specializationComboBox.getItems().add(null);

        sexComboBox.setDisable(false);
        sexComboBox.getItems().setAll(Sex.values());

        addButton.setDisable(false);
        addButton.setVisible(true);

        birthDateDatePicker.setDisable(false);

        creationDateDatePicker.setDisable(false);

        imageLabel.setVisible(true);
    }

    @FXML
    public void addsImage() throws MalformedURLException {
        imageFx.addImage(imagePathTxtField,itemImage);
    }

    @FXML
    public void setSaveButton(){

        EmployeeEntity updatedEmployee = new EmployeeEntity();
        updatedEmployee.setId(employee.getId());
        updatedEmployee.setFirstName(firstNameTextField.getText());
        updatedEmployee.setLastName(lastNameTextField.getText());
        updatedEmployee.setAddress(addressTextField.getText());
        updatedEmployee.setTelNumber(telNumberTextField.getText());
        updatedEmployee.setLogin(loginTextField.getText());
        updatedEmployee.setPassword(passwordTextField.getText());

        if(imagePathTxtField.getText().equals(""))
            updatedEmployee.setPhoto(employee.getPhoto());
        else
            updatedEmployee.setPhoto(ImageFx.getimage(imagePathTxtField));

        if(specializationComboBox.getValue()!=null)
            updatedEmployee.setSpecializationEntity(specializationComboBox.getValue());

        if(roleComboBox.getValue()!=null)
            updatedEmployee.setEmployeeType(roleComboBox.getValue());

        if(sexComboBox.getValue()!=null)
            updatedEmployee.setSex(sexComboBox.getValue());

        updatedEmployee.setCreationDate((creationDateDatePicker.getValue()));
        updatedEmployee.setBirtDate((birthDateDatePicker.getValue()));

        employeeEntityCRUD.update(updatedEmployee,true);

    }

}
