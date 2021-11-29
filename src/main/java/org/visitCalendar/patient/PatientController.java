package org.visitCalendar.patient;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.InitializationTable;
import org.administrator.patient.AddPatientController;
import org.administrator.patient.ManagePatientController;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PatientController implements Initializable {

    @FXML
    TextField filterTextField;

    @FXML
    JFXButton addButton;

    @FXML
    StackPane stackPanePatient;

    @FXML
    TableView<PatientEntity> tableViewPatient;

    CRUD<PatientEntity> patientEntityCRUD = new CRUD<>();
    List<PatientEntity> patientList = patientEntityCRUD.getAll(PatientEntity.class);

    InitializationTable<PatientEntity> initTable = new InitializationTable<>();

    Manage<PatientEntity> patientEntityManage = new ManagePatientController();

    private final ObservableList<PatientEntity> dataList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewPatient.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();
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
        initTable.addButtonToTableAndInitManageWindow(tableView, patientEntityManage,"visitCalendar/patient/ManagePatient.fxml",stackPanePatient);
        return tableView;

    }

    @FXML
    private void setAddButton(){
        Add addPatientController = new AddPatientController();
        initTable.setAddButton("visitCalendar/patient/AddPatient.fxml",addPatientController,stackPanePatient);
    }

    private void fillTableWithFilter(){
        dataList.addAll(patientList);
        FilteredList<PatientEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(patient -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (patient.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (patient.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<PatientEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewPatient.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewPatient.setItems(sortedData);

    }
}


