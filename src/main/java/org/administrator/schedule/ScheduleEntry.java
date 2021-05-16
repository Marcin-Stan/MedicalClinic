package org.administrator.schedule;
import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import com.calendarfx.model.Interval;
import javafx.beans.Observable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import org.database.employee.EmployeeEntity;
import org.database.operation.CRUD;
import org.database.schedule.ScheduleEntity;
import org.joda.time.DateTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class ScheduleEntry extends Entry<ScheduleEntity>{

    private ScheduleEntity scheduleEntity;
    private SimpleObjectProperty<EmployeeEntity> employee;

    public Boolean isFromDatabase;

    public ScheduleEntry(boolean isFromDatabased){
        super();
        this.isFromDatabase = isFromDatabased;

        this.employee = new SimpleObjectProperty<>(this,"employee"){
            @Override
            public void set(EmployeeEntity employeeEntity) {
                if (!isFromDatabase) {
                    EmployeeEntity employeeEntity1 = this.get();
                    ScheduleCalendar scheduleCalendar = (ScheduleCalendar) ScheduleEntry.this.getCalendar();
                    if(employeeEntity!=null ){
                        super.set(employeeEntity);
                        scheduleCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, scheduleCalendar,ScheduleEntry.this,employeeEntity1));
                        //scheduleCalendar.fireEvent(new ScheduleCalendarEvent(ScheduleCalendarEvent.ENTRY_ATTENDEES_CHANGED,scheduleCalendar,ScheduleEntry.this,employeeEntity1));
                    }
                }else {
                    if(employeeEntity!=null ){
                        super.set(employeeEntity);
                    }
                    isFromDatabase = false;
                }


            }
        };

        /*
        this.employee.addListener((Observable obs) -> {
                System.out.println("Testorowo");
                getCalendar().fireEvent(new CalendarEvent(
                        CalendarEvent.ENTRY_USER_OBJECT_CHANGED, (ScheduleCalendar) getCalendar(), ScheduleEntry.this));
        });
         */
        //this.setEmployee(emp);


    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee.set(employee);
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }

    public SimpleObjectProperty<EmployeeEntity> employeeProperty() {
        return employee;
    }

    public final EmployeeEntity getEmployee(){
        return this.employeeProperty().get();
    }

    public final SimpleObjectProperty getEmployeeProperty(){
        return this.employee;
    }


}