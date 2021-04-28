package org.administrator.schedule;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import javafx.beans.property.SimpleObjectProperty;
import org.database.employee.EmployeeEntity;

public class ScheduleEntry extends Entry<EmployeeEntity>{

    public ScheduleEntry(EmployeeEntity employeeEntity){
        super(employeeEntity.getFirstName()+employeeEntity.getFirstName());

    }
}
