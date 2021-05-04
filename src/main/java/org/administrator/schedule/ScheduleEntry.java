package org.administrator.schedule;
import com.calendarfx.model.Entry;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.database.employee.EmployeeEntity;
import org.joda.time.DateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ScheduleEntry extends Entry<EmployeeEntity>{

    EmployeeEntity employeeEntity;

    public ScheduleEntry(){
        super();
        DateTime startTime = getDateTime(this.getStartDate(), this.getStartTime());
        DateTime endTime = getDateTime(this.getEndDate(), this.getEndTime());

    }

    public void setEmployeeEntity(EmployeeEntity employeeEntity) {
        this.employeeEntity = employeeEntity;
    }

    public EmployeeEntity getEmployeeEntity() {
        return employeeEntity;
    }

    @Override
    public ScheduleEntry createRecurrence() {
        ScheduleEntry scheduleEntry = new ScheduleEntry();
        scheduleEntry.setEmployeeEntity(getEmployeeEntity());
        System.out.println("HEHEHHEHE");
        return scheduleEntry;
    }

    private DateTime getDateTime(LocalDate date, LocalTime time) {
        return new DateTime(date.getYear(), date.getMonthValue(), date.getDayOfMonth(), time.getHour(),
                time.getMinute());
    }

}
