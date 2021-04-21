package org.administrator.specialization;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.database.operation.CRUD;
import org.database.specialization.SpecializationEntity;
import org.table.Manage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageSpecializationController implements Initializable, Manage<SpecializationEntity> {

    @FXML
    AnchorPane manageSpecializationAnchorPane;

    @FXML
    TextField nameTextField;
    @FXML
    Button closeButton,
            saveButton,
            deleteButton;
    @FXML
    JFXButton editButton;

    StackPane parentStackPane;
    SpecializationEntity specialization;

    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initData(SpecializationEntity entity) {
        this.specialization = entity;
        nameTextField.setText(entity.getSpecName());
    }

    @Override
    public void setParentStackPane(StackPane stackPane) {
        this.parentStackPane=stackPane;
    }

    @FXML
    public void setCloseButton() {
        setCloseButton(closeButton,"Specialization.fxml",parentStackPane);
    }

    @FXML
    public void setDeleteButton() {
        setDeleteButton(specializationEntityCRUD,specialization,closeButton,parentStackPane,"Specialization.fxml");

    }

    @FXML
    private void setEditButton(){
        nameTextField.setDisable(false);
        nameTextField.setEditable(true);

    }

    @FXML
    public void setSaveButton(){
        SpecializationEntity updatedSpecialization = new SpecializationEntity();

        updatedSpecialization.setId(specialization.getId());
        updatedSpecialization.setSpecName(nameTextField.getText());

        specializationEntityCRUD.update(updatedSpecialization);
    }

}
