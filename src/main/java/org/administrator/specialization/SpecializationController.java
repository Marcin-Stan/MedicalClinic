package org.administrator.specialization;

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
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.specialization.SpecializationEntity;
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SpecializationController implements Initializable {
    @FXML
    StackPane stackPaneSpecialization;

    @FXML
    TableView<SpecializationEntity> tableViewSpecialization;

    @FXML
    JFXButton addButton;

    @FXML
    TextField filterTextField;

    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();
    List<SpecializationEntity> specializationList = specializationEntityCRUD.getAll(SpecializationEntity.class);

    InitializationTable<SpecializationEntity> initTable = new InitializationTable<>();

    Manage<SpecializationEntity> specializationEntityManage = new ManageSpecializationController();

    private final ObservableList<SpecializationEntity> dataList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewSpecialization.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();
    }

    private TableView<SpecializationEntity> initTableView() {

        TableView<SpecializationEntity> tableView = new TableView<>();

        TableColumn<SpecializationEntity, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<SpecializationEntity, String> column2 = new TableColumn<>("Nazwa");
        column2.setCellValueFactory(new PropertyValueFactory<>("specName"));


        tableView.getColumns().addAll(column1, column2);
        initTable.addButtonToTableAndInitManageWindow(tableView, specializationEntityManage, "administrator/specialization/ManageSpecialization.fxml", stackPaneSpecialization);
        return tableView;

    }

    @FXML
    public void setAddButton() {
        Add addSpecializationController = new AddSpecializationController();
        initTable.setAddButton("administrator/specialization/AddSpecialization.fxml",addSpecializationController, stackPaneSpecialization);
    }

    private void fillTableWithFilter(){
        dataList.addAll(specializationList);
        FilteredList<SpecializationEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(specialization -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (specialization.getSpecName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<SpecializationEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewSpecialization.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewSpecialization.setItems(sortedData);

    }


}
