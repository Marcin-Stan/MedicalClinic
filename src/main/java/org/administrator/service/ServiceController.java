package org.administrator.service;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.InitializationTable;
import org.database.operation.CRUD;
import org.database.service.ServiceEntity;
import org.table.Add;
import org.table.Manage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewService.getColumns().setAll(initTableView().getColumns());
        initTable.fillTable(tableViewService, servicesList);
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
}


