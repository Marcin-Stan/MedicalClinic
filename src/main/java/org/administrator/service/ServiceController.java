package org.administrator.service;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;
import org.database.service.ServiceEntity;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ServiceController implements Initializable {

    @FXML
    TableView<ServiceEntity> tableViewService;

    CRUD<ServiceEntity> serviceEntityCRUD = new CRUD<>();

    List<ServiceEntity> serviceEntities = serviceEntityCRUD.getAll(ServiceEntity.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewService.getColumns().setAll(initTableView().getColumns());
        fillTable(tableViewService);
    }

    private TableView<ServiceEntity> initTableView(){

        TableView<ServiceEntity> tableView = new TableView<>();

        TableColumn<ServiceEntity,String> column1 = new TableColumn<>("ID");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<ServiceEntity,String> column2 = new TableColumn<> ("Nazwa");
        column2.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn <ServiceEntity,Integer>column3 = new TableColumn<>("Cena");
        column3.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn <ServiceEntity,String>column4 = new TableColumn<>("Nazwa departamentu");
        column4.setCellValueFactory(new PropertyValueFactory<>("departmentEntity"));


        tableView.getColumns().addAll(column1,column2,column3,column4);
        addButtonToTable(tableView);
        return tableView;

    }

    private void addButtonToTable(TableView<ServiceEntity> tableView) {
        TableColumn<ServiceEntity, Void> colBtn = new TableColumn("");

        Callback<TableColumn<ServiceEntity, Void>, TableCell<ServiceEntity, Void>> cellFactory = new Callback<>() {

            @Override
            public TableCell<ServiceEntity, Void> call(final TableColumn<ServiceEntity, Void> param) {
                final TableCell<ServiceEntity, Void> cell = new TableCell<>() {

                    private final JFXButton btn = new JFXButton("Zarządzaj");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            ServiceEntity service = getTableView().getItems().get(getIndex());
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

    private void fillTable(TableView<ServiceEntity> tableView){

        for (int i = 0; i <serviceEntities.size() ; i++) {
            tableView.getItems().addAll(new ServiceEntity(serviceEntities.get(i).getId(),
                    serviceEntities.get(i).getName(),
                    serviceEntities.get(i).getPrice(),
                    serviceEntities.get(i).getDepartmentEntity()
            ));
        }

    }
}
