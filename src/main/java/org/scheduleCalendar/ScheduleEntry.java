package org.scheduleCalendar;
import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import javafx.beans.property.SimpleObjectProperty;
import org.database.employee.EmployeeEntity;
import org.database.schedule.ScheduleEntity;

public class ScheduleEntry extends Entry<ScheduleEntity>{

    private ScheduleEntity scheduleEntity;
    private SimpleObjectProperty<EmployeeEntity> employee;

    public Boolean isFromDatabase;

    public ScheduleEntry(){
        super();
        setEmployeeProperty();

    }

    private void setEmployeeProperty(){
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
    }

    public void setEmployee(EmployeeEntity employee, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.employee.set(employee);
    }

    public void setScheduleEntity(ScheduleEntity scheduleEntity, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
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