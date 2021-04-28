package org.administrator.schedule;

import com.calendarfx.model.Entry;
import com.calendarfx.view.popover.EntryDetailsView;
import javafx.geometry.VPos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.specialization.SpecializationEntity;

public class ScheduleEntryDetailsView extends EntryDetailsView {

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    ComboBox<EmployeeEntity> employeeEntityComboBox = new ComboBox<>();
    public ScheduleEntryDetailsView(Entry<?> entry) {
        super(entry);

        employeeEntityComboBox.getItems().setAll(employeeEntityCRUD.getAll(EmployeeEntity.class));
        GridPane box = (GridPane) getChildren().get(0);
        box.add(employeeEntityComboBox, 0, 5);
        GridPane.setValignment(employeeEntityComboBox, VPos.TOP);
    }
}
