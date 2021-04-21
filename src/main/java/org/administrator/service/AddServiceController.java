package org.administrator.service;

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
import org.table.Add;
import org.validator.TextFieldValidator;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddServiceController implements Initializable, Add {

    @FXML
    AnchorPane addServiceAnchorPane;

    @FXML
    TextField nameTextField,
            priceTextField;
    @FXML
    Button closeButton,
            saveButton;

    @FXML
    ComboBox<DepartmentEntity> departmentComboBox;

    StackPane parentStackPane;

    CRUD<ServiceEntity> serviceEntityCRUD = new CRUD<>();
    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();

    // force the field to be numeric only

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        departmentComboBox.getItems().setAll(departmentEntityCRUD.getAll(DepartmentEntity.class));
        TextFieldValidator.allowOnlyNumber(priceTextField);
    }

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
        ServiceEntity newService = new ServiceEntity();

        newService.setName(nameTextField.getText());

        if(!priceTextField.getText().isEmpty())
            newService.setPrice(Integer.parseInt(priceTextField.getText()));

        newService.setDepartmentEntity(departmentComboBox.getValue());

        if(serviceEntityCRUD.save(newService)){
            try {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane pane = FXMLLoader.load(getClass().getResource("Service.fxml"));
                parentStackPane.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }






}
