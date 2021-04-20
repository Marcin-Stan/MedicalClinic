package org.administrator.employee;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.database.employee.Employee;
import org.database.employee.EmployeeEntity;
import javafx.util.Callback;
import org.database.operation.CRUD;
import org.database.patient.PatientEntity;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    JFXButton addButton;

    @FXML
    TableView<EmployeeEntity> tableViewEmployee;

    @FXML
    StackPane stackPaneEmployee;

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    List<EmployeeEntity> listEmployee = employeeEntityCRUD.getAll(EmployeeEntity.class);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableViewEmployee.getColumns().setAll(initTableView().getColumns());
        fillTable(tableViewEmployee);
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
        addButtonToTable(tableView);
        return tableView;

    }

    private void addButtonToTable(TableView<EmployeeEntity> tableView) {
        TableColumn<EmployeeEntity, Void> colBtn = new TableColumn("");

        Callback<TableColumn<EmployeeEntity, Void>, TableCell<EmployeeEntity, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<EmployeeEntity, Void> call(final TableColumn<EmployeeEntity, Void> param) {
                final TableCell<EmployeeEntity, Void> cell = new TableCell<>() {

                    private final JFXButton btn = new JFXButton("Zarządzaj");

                    {
                        btn.setOnAction((ActionEvent event) -> {
                            EmployeeEntity employee = getTableView().getItems().get(getIndex());
                            openAndInitNewWindow(employee);
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

    private void fillTable(TableView<EmployeeEntity> tableView){

        /*
        for (int i = 0; i <listEmployee.size() ; i++) {
            tableView.getItems().addAll(new EmployeeEntity(listEmployee.get(i).getId(),
                    listEmployee.get(i).getFirstName(),
                    listEmployee.get(i).getLastName(),
                    listEmployee.get(i).getAddress(),
                    listEmployee.get(i).getTelNumber(),
                    listEmployee.get(i).getSex(),
                    listEmployee.get(i).getBirtDate(),
                    listEmployee.get(i).getCreationDate(),
                    listEmployee.get(i).getLogin(),
                    listEmployee.get(i).getPassword(),
                    listEmployee.get(i).getEmployeeType(),
                    listEmployee.get(i).getPhoto(),
                    listEmployee.get(i).getSpecializationEntity()
            ));
        }

         */
        for (int i = 0; i <listEmployee.size() ; i++) {
            tableView.getItems().addAll(listEmployee.get(i));
        }

    }

    private void openAndInitNewWindow(EmployeeEntity employee){

        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("ManageEmployee.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            ManageEmployeeController manageEmployeeController = fxmlLoader.getController();
            manageEmployeeController.setParentStackPane(stackPaneEmployee);
            manageEmployeeController.initData(employee);
            Stage stage = new Stage();
            stage.setTitle("Zarządzanie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void setAddButton(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AddEmployee.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            AddEmployeeController addEmployeeController = fxmlLoader.getController();
            addEmployeeController.setParentStackPane(stackPaneEmployee);
            Stage stage = new Stage();
            stage.setTitle("Dodawanie");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
