package org.administrator.department;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.department.DepartmentEntity;
import org.database.operation.CRUD;
import org.table.Add;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AddDepartmentController implements Initializable, Add {

    @FXML
    AnchorPane AddDepartmentAnchorPane;

    @FXML
    TextField nameTextField;
    @FXML
    Button closeButton,
            saveButton;

    StackPane parentStackPane;

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
        DepartmentEntity newDepartment = new DepartmentEntity();

        newDepartment.setName(nameTextField.getText());

        if(departmentEntityCRUD.save(newDepartment)){
            try {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane pane = FXMLLoader.load(getClass().getResource("Department.fxml"));
                parentStackPane.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
