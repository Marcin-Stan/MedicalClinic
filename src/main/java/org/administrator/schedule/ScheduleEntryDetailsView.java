package org.administrator.schedule;

import com.calendarfx.model.Entry;
import com.calendarfx.view.popover.EntryDetailsView;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.specialization.SpecializationEntity;

public class ScheduleEntryDetailsView extends EntryDetailsView {

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();



    //private ScheduleEntry entry;

    public ScheduleEntryDetailsView(ScheduleEntry entry) {
        super(entry);
        //this.entry=entry;
        employeeEntityComboBox.getItems().setAll(employeeEntityCRUD.getAll(EmployeeEntity.class));
        //employeeEntityComboBox.setValue(entry.getScheduleEntity().getEmployee());
        employeeEntityComboBox.setValue(entry.getEmployee());
        employeeEntityComboBox.valueProperty().bindBidirectional(entry.getEmployeeProperty());


        GridPane box = (GridPane) getChildren().get(0);
        Label employee = new Label("Employee");
        box.add(employeeEntityComboBox, 1, 5);
        box.add(employee,0,5);
        GridPane.setValignment(employeeEntityComboBox, VPos.TOP);

        box.getChildren().get(0);

    }
}
