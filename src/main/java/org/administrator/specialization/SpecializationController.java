package org.administrator.specialization;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import org.InitializationTable;
import org.administrator.department.AddDepartmentController;
import org.administrator.department.ManageDepartmentController;
import org.database.department.DepartmentEntity;
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

    CRUD<SpecializationEntity> specializationEntityCRUD = new CRUD<>();
    List<SpecializationEntity> servicesList = specializationEntityCRUD.getAll(SpecializationEntity.class);

    InitializationTable<SpecializationEntity> initTable = new InitializationTable<>();

    Manage<SpecializationEntity> specializationEntityManage = new ManageSpecializationController();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableViewSpecialization.getColumns().setAll(initTableView().getColumns());
        initTable.fillTable(tableViewSpecialization,servicesList);
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

}
