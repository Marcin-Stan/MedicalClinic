package org.visitCalendar.visit;

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
import org.administrator.patient.ManagePatientController;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.database.visit.VisitEntity;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VisitController implements Initializable {

    @FXML
    TextField filterTextField;

    @FXML
    StackPane stackPaneVisit;

    @FXML
    TableView<VisitEntity> tableViewVisit;

    CRUD<VisitEntity> visitEntityCRUD = new CRUD<>();
    List<VisitEntity> visitEntityList = visitEntityCRUD.getAll(VisitEntity.class);

    InitializationTable<VisitEntity> initTable = new InitializationTable<>();

    Manage<VisitEntity> visitEntityManage = new ManageVisitController();

    private final ObservableList<VisitEntity> dataList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewVisit.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();
    }



    private TableView<VisitEntity> initTableView() {

        TableView<VisitEntity> tableView = new TableView<>();

        TableColumn<VisitEntity, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<VisitEntity, String> column2 = new TableColumn<>("Pacjent");
        column2.setCellValueFactory(new PropertyValueFactory<>("patient"));

        TableColumn<VisitEntity, String> column3 = new TableColumn<>("Serwis");
        column3.setCellValueFactory(new PropertyValueFactory<>("service"));

        TableColumn<VisitEntity, String> column4 = new TableColumn<>("Pracownik");
        column4.setCellValueFactory(new PropertyValueFactory<>("employee"));

        TableColumn<VisitEntity, String> column5 = new TableColumn<>("Data");
        column5.setCellValueFactory(new PropertyValueFactory<>("endDate"));


        tableView.getColumns().addAll(column1, column2, column3, column4, column5);
        initTable.addButtonToTableAndInitManageWindow(tableView, visitEntityManage, "visitCalendar/visit/ManageVisit.fxml", stackPaneVisit);
        return tableView;
    }

    private void fillTableWithFilter(){
        dataList.addAll(visitEntityList);
        FilteredList<VisitEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(visit -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (visit.getService().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (visit.getEndDate().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }else if (visit.getPatient().toString().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<VisitEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewVisit.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewVisit.setItems(sortedData);

    }
}