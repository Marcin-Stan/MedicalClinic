package org.administrator.department;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.InitializationTable;
import org.administrator.service.AddServiceController;
import org.administrator.service.ManageServiceController;
import org.database.department.DepartmentEntity;
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

    CRUD<DepartmentEntity> departmentEntityCRUD = new CRUD<>();
    List<DepartmentEntity> departmentList = departmentEntityCRUD.getAll(DepartmentEntity.class);

    Manage<DepartmentEntity> departmentEntityManage = new ManageDepartmentController();

    InitializationTable<DepartmentEntity> initTable = new InitializationTable<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewDepartment.getColumns().setAll(initTableView().getColumns());
        initTable.fillTable(tableViewDepartment,departmentList);
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
}
