package org.administrator.service;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.database.service.ServiceEntity;
import org.table.Manage;
import org.validator.TextFieldValidator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ManageServiceController implements Initializable, Manage<ServiceEntity> {
    @FXML
    AnchorPane manageServiceAnchorPane;

    @FXML
    TextField nameTextField,
            priceTextField;
    @FXML
    Button closeButton,
            saveButton,
            deleteButton;
    @FXML
    JFXButton editButton;

     @FXML
    ComboBox<DepartmentEntity> departmentComboBox;

    StackPane parentStackPane;
    ServiceEntity service;

    CRUD<ServiceEntity> serviceEntityCRUD = new CRUD<>();
    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TextFieldValidator.allowOnlyNumber(priceTextField);
    }

    @Override
    public void initData(ServiceEntity entity) {
        this.service = entity;

        nameTextField.setText(entity.getName());
        priceTextField.setText(entity.getPrice().toString());
        departmentComboBox.setValue(entity.getDepartmentEntity());

    }

    @Override
    public void setParentStackPane(StackPane stackPane) {
        this.parentStackPane=stackPane;
    }

    @FXML
    public void setCloseButton(){
        setCloseButton(closeButton,"Service.fxml",parentStackPane);
    }

    @FXML
    private void setDeleteButton(){
        setDeleteButton(serviceEntityCRUD,service,closeButton,parentStackPane,"Service.fxml");
    }

    @FXML
    private void setEditButton(){
        nameTextField.setDisable(false);
        nameTextField.setEditable(true);

        priceTextField.setDisable(false);
        priceTextField.setEditable(true);

        departmentComboBox.setDisable(false);
        departmentComboBox.getItems().setAll(departmentEntityCRUD.getAll(DepartmentEntity.class));

    }

    @FXML
    public void setSaveButton(){
        ServiceEntity updatedService = new ServiceEntity();

        updatedService.setId(service.getId());
        updatedService.setName(nameTextField.getText());

        if(!priceTextField.getText().isEmpty())
            updatedService.setPrice(Integer.parseInt(priceTextField.getText()));

        updatedService.setDepartmentEntity(departmentComboBox.getValue());

        serviceEntityCRUD.update(updatedService,true);
    }

}
