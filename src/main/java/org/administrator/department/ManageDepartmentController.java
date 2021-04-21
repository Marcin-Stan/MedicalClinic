package org.administrator.department;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.database.service.ServiceEntity;
import org.table.Manage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageDepartmentController implements Initializable, Manage<DepartmentEntity> {
    @FXML
    AnchorPane manageDepartmentAnchorPane;

    @FXML
    TextField nameTextField;
    @FXML
    Button closeButton,
            saveButton,
            deleteButton;
    @FXML
    JFXButton editButton;

    StackPane parentStackPane;
    DepartmentEntity department;

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initData(DepartmentEntity entity) {
        this.department = entity;
        nameTextField.setText(entity.getName());

    }

    @Override
    public void setParentStackPane(StackPane stackPane) {
        this.parentStackPane = stackPane;
    }

    @FXML
    public void setCloseButton() {
        setCloseButton(closeButton,"Department.fxml",parentStackPane);
    }

    @FXML
    public void setDeleteButton() {
        setDeleteButton(departmentEntityCRUD,department,closeButton,parentStackPane,"Department.fxml");
    }

    @FXML
    private void setEditButton(){
        nameTextField.setDisable(false);
        nameTextField.setEditable(true);

    }

    @FXML
    public void setSaveButton(){
        DepartmentEntity updatedDepartment = new DepartmentEntity();

        updatedDepartment.setId(department.getId());
        updatedDepartment.setName(nameTextField.getText());

        departmentEntityCRUD.update(updatedDepartment);
    }

}
