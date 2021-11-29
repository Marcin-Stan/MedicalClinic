package org.administrator.department;

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
import org.administrator.service.AddServiceController;
import org.administrator.service.ManageServiceController;
import org.database.department.DepartmentEntity;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.service.ServiceEntity;
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentController implements Initializable {

    @FXML
    TableView<DepartmentEntity> tableViewDepartment;
    @FXML
    JFXButton addButton;
    @FXML
    StackPane stackPaneDepartment;
    @FXML
    TextField filterTextField;

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);

    Manage<DepartmentEntity> departmentEntityManage = new ManageDepartmentController();

    InitializationTable<DepartmentEntity> initTable = new InitializationTable<>();

    private final ObservableList<DepartmentEntity> dataList = FXCollections.observableArrayList();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewDepartment.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();
    }

    private TableView<DepartmentEntity> initTableView() {

        TableView<DepartmentEntity> tableView = new TableView<>();

        TableColumn<DepartmentEntity, String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<DepartmentEntity, String> column2 = new TableColumn<>("Nazwa");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));


        tableView.getColumns().addAll(column1, column2);
        initTable.addButtonToTableAndInitManageWindow(tableView, departmentEntityManage, "administrator/department/ManageDepartment.fxml", stackPaneDepartment);
        return tableView;

    }

    @FXML
    public void setAddButton() {
        Add addDepartmentController = new AddDepartmentController();
        initTable.setAddButton("administrator/department/AddDepartment.fxml",addDepartmentController, stackPaneDepartment);
    }

    private void fillTableWithFilter(){
        dataList.addAll(departmentList);
        FilteredList<DepartmentEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(department -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (department.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                }
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<DepartmentEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewDepartment.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewDepartment.setItems(sortedData);

    }
}
