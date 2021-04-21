package org.administrator.specialization;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.operation.CRUD;
import org.database.specialization.SpecializationEntity;
import org.table.Add;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddSpecializationController implements Initializable, Add {

    @FXML
    AnchorPane AddSpecializationAnchorPane;

    @FXML
    TextField nameTextField;
    @FXML
    Button closeButton,
            saveButton;

    StackPane parentStackPane;

    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void setCloseButton(){
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void setParentStackPane(StackPane parentStackPane) {
        this.parentStackPane=parentStackPane;
    }

    @FXML
    public void setSaveButton(){
        SpecializationEntity newSpecialization = new SpecializationEntity();

        newSpecialization.setSpecName(nameTextField.getText());

        if(specializationEntityCRUD.save(newSpecialization)){
            try {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
                StackPane pane = FXMLLoader.load(getClass().getResource("Specialization.fxml"));
                parentStackPane.getChildren().add(pane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
