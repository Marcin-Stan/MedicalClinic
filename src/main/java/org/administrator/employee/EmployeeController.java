package org.administrator.employee;

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
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    TextField filterTextField;

    @FXML
    JFXButton addButton;

    @FXML
    TableView<EmployeeEntity> tableViewEmployee;

    @FXML
    StackPane stackPaneEmployee;

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    List<EmployeeEntity> listEmployee = employeeEntityCRUD.getAll(EmployeeEntity.class);

    Manage<EmployeeEntity> employeeEntityManage = new ManageEmployeeController();
    InitializationTable<EmployeeEntity> initTable = new InitializationTable<>();

    //observalble list to store data
    private final ObservableList<EmployeeEntity> dataList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewEmployee.getColumns().setAll(initTableView().getColumns());
        fillTableWithFilter();

    }

    private TableView<EmployeeEntity> initTableView(){

        TableView<EmployeeEntity> tableView = new TableView<EmployeeEntity>();
        TableColumn<EmployeeEntity,String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<EmployeeEntity,String> column2 = new TableColumn<> ("Imię");
        column2.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn <EmployeeEntity,String>column3 = new TableColumn<>("Nazwisko");
        column3.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        TableColumn <EmployeeEntity,String>column4 = new TableColumn<>("Adres");
        column4.setCellValueFactory(new PropertyValueFactory<>("address"));

        TableColumn <EmployeeEntity,String>column5 = new TableColumn<>("Numer telefonu");
        column5.setCellValueFactory(new PropertyValueFactory<>("telNumber"));

        TableColumn<EmployeeEntity,String> column6 = new TableColumn<>("Data urodzenia");
        column6.setCellValueFactory(new PropertyValueFactory<>("birtDate"));

        TableColumn<EmployeeEntity,String> column7 = new TableColumn<>("Data utworzenia");
        column7.setCellValueFactory(new PropertyValueFactory<>("creationDate"));

        TableColumn<EmployeeEntity,String> column8 = new TableColumn<>("login");
        column8.setCellValueFactory(new PropertyValueFactory<>("login"));

        TableColumn<EmployeeEntity,String> column9 = new TableColumn<>("Hasło");
        column9.setCellValueFactory(new PropertyValueFactory<>("password"));

        TableColumn<EmployeeEntity,String> column10 = new TableColumn<>("Stanowisko");
        column10.setCellValueFactory(new PropertyValueFactory<>("employeeType"));

        tableView.getColumns().addAll(column1,column2,column3,column4,column5,column6,column7,column8,column9,column10);

        initTable.addButtonToTableAndInitManageWindow(tableView,employeeEntityManage,"administrator/employee/ManageEmployee.fxml",stackPaneEmployee);
        return tableView;

    }

    @FXML
    private void setAddButton(){
        Add addEmployeeController = new AddEmployeeController();
        initTable.setAddButton("administrator/employee/AddEmployee.fxml",addEmployeeController,stackPaneEmployee);
    }


    private void fillTableWithFilter(){
        dataList.addAll(listEmployee);
        FilteredList<EmployeeEntity> filteredData = new FilteredList<>(dataList, b -> true);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(employee -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (employee.getFirstName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else if (employee.getLastName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches last name.
                }
                else if (String.valueOf(employee.getLogin()).contains(lowerCaseFilter))
                    return true;
                else
                    return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<EmployeeEntity> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(tableViewEmployee.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        tableViewEmployee.setItems(sortedData);


    }


}
