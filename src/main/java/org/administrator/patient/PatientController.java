package org.administrator.patient;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.InitializationTable;
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
        initTable.addButtonToTable(tableView, patientEntityManage,"administrator/patient/ManagePatient.fxml",stackPanePatient);
        //addButtonToTable(tableView);
        return tableView;

    }

    private void addButtonToTable(TableView<PatientEntity> tableView) {
        TableColumn<PatientEntity, Void> colBtn = new TableColumn("");

        Callback<TableColumn<PatientEntity, Void>, TableCell<PatientEntity, Void>> cellFactory = new Callback<>() {

            @Override
            public TableCell<PatientEntity, Void> call(final TableColumn<PatientEntity, Void> param) {
                final TableCell<PatientEntity, Void> cell = new TableCell<>() {

                    private final JFXButton btn = new JFXButton("Zarządzaj");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            PatientEntity patient = getTableView().getItems().get(getIndex());
                            openAndInitNewWindow(patient);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        };

        colBtn.setCellFactory(cellFactory);

        tableView.getColumns().add(colBtn);

    }

    private void fillTable(TableView<PatientEntity> tableView){

        for (int i = 0; i <patientList.size() ; i++) {
            tableView.getItems().addAll(patientList.get(i));
        }


    }

    private void openAndInitNewWindow(PatientEntity patient){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ManagePatient.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ManagePatientController managePatientController = fxmlLoader.getController();
            managePatientController.initData(patient);
            managePatientController.setParentStackPane(stackPanePatient);
            Stage stage = new Stage();
            stage.setTitle("Zarządzanie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void setAddButton(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddPatient.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddPatientController addPatientController = fxmlLoader.getController();
            addPatientController.setParentStackPane(stackPanePatient);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
