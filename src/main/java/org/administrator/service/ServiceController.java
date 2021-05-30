package org.administrator.service;

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
import org.database.service.ServiceEntity;
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

    @FXML
    TextField filterTextField;

    @FXML
    JFXButton addButton;

    @FXML
    StackPane stackPaneService;

    @FXML
    TableView<ServiceEntity> tableViewService;

    CRUD<ServiceEntity> serviceEntityCRUD = new CRUD<>();
    List<ServiceEntity> servicesList = serviceEntityCRUD.getAll(ServiceEntity.class);

    InitializationTable<ServiceEntity> initTable = new InitializationTable<>();

    Manage<ServiceEntity> serviceEntityManage = new ManageServiceController();

    private final ObservableList<ServiceEntity> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewService.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();
    }

    private TableView<ServiceEntity> initTableView() {

        TableView<ServiceEntity> tableView = new TableView<>();

        TableColumn<ServiceEntity, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ServiceEntity, String> column2 = new TableColumn<>("Nazwa");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<ServiceEntity, Integer> column3 = new TableColumn<>("Cena");
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<ServiceEntity, String> column4 = new TableColumn<>("Nazwa departamentu");
        column4.setCellValueFactory(new PropertyValueFactory<>("departmentEntity"));

        tableView.getColumns().addAll(column1, column2, column3, column4);
        initTable.addButtonToTableAndInitManageWindow(tableView, serviceEntityManage, "administrator/service/ManageService.fxml", stackPaneService);
        return tableView;

    }

    @FXML
    public void setAddButton() {
        Add addServiceController = new AddServiceController();
        initTable.setAddButton("administrator/service/AddService.fxml",addServiceController, stackPaneService);
    }

    private void fillTableWithFilter(){
        dataList.addAll(servicesList);
        FilteredList<ServiceEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(service -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (service.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<ServiceEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewService.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewService.setItems(sortedData);

    }
}


