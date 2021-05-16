package org.scheduleCalendar;

import com.calendarfx.view.popover.EntryDetailsView;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;

public class ScheduleEntryDetailsView extends EntryDetailsView {

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();


    public ScheduleEntryDetailsView(ScheduleEntry entry) {
        super(entry);

        employeeEntityComboBox.getItems().setAll(employeeEntityCRUD.getAll(EmployeeEntity.class));
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


}