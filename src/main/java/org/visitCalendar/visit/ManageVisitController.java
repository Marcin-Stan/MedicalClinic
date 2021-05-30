package org.visitCalendar.visit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.database.visit.VisitEntity;
import org.table.Manage;

import java.net.URL;
import java.util.ResourceBundle;

public class ManageVisitController implements Initializable, Manage<VisitEntity> {

    @FXML
    TextArea textAreaVisit;

    @FXML
    Button closeButton;

    @FXML
    AnchorPane manageVisitAnchorPane;

    StackPane parentStackPane;

    VisitEntity visitEntity;

    CRUD<VisitEntity> visitEntityCRUD = new CRUD<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @Override
    public void initData(VisitEntity entity) {
        this.visitEntity = entity;
        textAreaVisit.setWrapText(true);
        if(!entity.getPatientNote().equals("")){
            textAreaVisit.setText(entity.getPatientNote());
        }
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

}
