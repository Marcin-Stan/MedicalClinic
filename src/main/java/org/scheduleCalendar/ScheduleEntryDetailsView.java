package org.scheduleCalendar;

import com.calendarfx.view.popover.EntryDetailsView;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.employee.EmployeeType;

import java.util.ArrayList;
import java.util.List;

public class ScheduleEntryDetailsView extends EntryDetailsView {

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();

    public ScheduleEntryDetailsView(ScheduleEntry entry) {
        super(entry);

        employeeEntityComboBox.getItems().setAll(getMedicalEmployeeFromEmployeeList(employeeEntityCRUD.getAll(EmployeeEntity.class)));
        employeeEntityComboBox.valueProperty().bindBidirectional(entry.getEmployeeProperty());

        GridPane box = (GridPane) getChildren().get(0);
        Label employee = new Label("Employee");
        box.getChildren().remove(6);
        box.getChildren().remove(6);

        box.getChildren().remove(1);
        box.getChildren().remove(0);

        box.getChildren().remove(5);
        box.getChildren().remove(4);


        box.add(employeeEntityComboBox, 1, 3);
        box.add(employee,0,3);
        GridPane.setValignment(employeeEntityComboBox, VPos.TOP);

        box.getChildren().get(0);

    }

    private List<EmployeeEntity> getMedicalEmployeeFromEmployeeList(List<EmployeeEntity> employeeEntities){
        List<EmployeeEntity> medicalEmployeeList = new ArrayList<>();

        for(EmployeeEntity employeeEntity:employeeEntities){
            if(employeeEntity.getEmployeeType()!=EmployeeType.Administrator){
                medicalEmployeeList.add(employeeEntity);
            }
        }

        return medicalEmployeeList;
    }




}
