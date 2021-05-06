package org.administrator.schedule;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;
import org.joda.time.DateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduleEntry extends Entry<ScheduleEntity>{

    SimpleObjectProperty<ScheduleEntity> scheduleEntity;
    EmployeeEntity employeeEntity;

    public ScheduleEntry(){
        super();

        this.scheduleEntity = new SimpleObjectProperty<ScheduleEntity>(this,"scheduleEntity"){
            @Override
            public void set(ScheduleEntity scheduleEntity) {
                super.set(scheduleEntity);
            }
        };
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity.set(scheduleEntity);
    }

    /*
    public void setEmployeeEntity(EmployeeEntity employeeEntity){
        this.scheduleEntity.get().setEmployee(employeeEntity);

    }

     */

    public ScheduleEntity getScheduleEntity() {
        return this.scheduleEntity.get();
    }

    @Override
    public ScheduleEntry createRecurrence() {
        return new ScheduleEntry();
    }




}
