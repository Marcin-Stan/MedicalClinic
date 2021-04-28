package org.administrator.patient;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.administrator.employee.AddEmployeeController;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.InitializationTable;
import org.table.Add;
import org.table.Manage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    JFXButton addButton;

    @FXML
    StackPane stackPanePatient;

    @FXML
    TableView<PatientEntity>tableViewPatient;

    CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();
    List<PatientEntity> patientList = patientEntityCRUD.getAll(PatientEntity.class);

    InitializationTable<PatientEntity> initTable = new InitializationTable<>();

    Manage<PatientEntity> patientEntityManage = new ManagePatientController();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewPatient.getColumns().setAll(initTableView().getColumns());
        initTable.fillTable(tableViewPatient,patientList);
    }


    private TableView<PatientEntity> initTableView(){

        TableView<PatientEntity> tableView = new TableView<>();

        TableColumn<PatientEntity,String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<PatientEntity,String> column2 = new TableColumn<> ("Imię");
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn <PatientEntity,String>column3 = new TableColumn<>("Nazwisko");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn <PatientEntity,String>column4 = new TableColumn<>("Adres");
        column4.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn <PatientEntity,String>column5 = new TableColumn<>("Numer telefonu");
        column5.setCellValueFactory(new PropertyValueFactory<>("telNumber"));

        TableColumn<PatientEntity,String> column6 = new TableColumn<>("Data urodzenia");
        column6.setCellValueFactory(new PropertyValueFactory<>("birtDate"));

        TableColumn<PatientEntity,String> column7 = new TableColumn<>("Data utworzenia");
        column7.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        TableColumn<PatientEntity,String> column8 = new TableColumn<>("Numer Pesel");
        column8.setCellValueFactory(new PropertyValueFactory<>("peselNumber"));


        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8);
        initTable.addButtonToTableAndInitManageWindow(tableView, patientEntityManage,"administrator/patient/ManagePatient.fxml",stackPanePatient);
        return tableView;

    }

    @FXML
    private void setAddButton(){
        Add addPatientController = new AddPatientController();
        initTable.setAddButton("administrator/patient/AddPatient.fxml",addPatientController,stackPanePatient);
    }

}
