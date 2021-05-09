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
    private final SimpleObjectProperty<EmployeeEntity> employee;

    //CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    //List<EmployeeEntity> entityList = employeeEntityCRUD.getAll(EmployeeEntity.class);

    //CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    //List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);
    public ScheduleEntry(EmployeeEntity employeeEntity){
        super();

        this.employee = new SimpleObjectProperty<>(this,"employee"){
            @Override
            public void set(EmployeeEntity employeeEntity) {
                //EmployeeEntity employeeEntity1 = this.get();
                ScheduleCalendar scheduleCalendar = (ScheduleCalendar) ScheduleEntry.this.getCalendar();
                if(employeeEntity!=null ){
                    super.set(employeeEntity);
                    scheduleCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, scheduleCalendar,ScheduleEntry.this,employeeEntity));
                    //scheduleCalendar.fireEvent(new ScheduleCalendarEvent(ScheduleCalendarEvent.ENTRY_ATTENDEES_CHANGED,scheduleCalendar,ScheduleEntry.this,employeeEntity1));
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
        this.setEmployee(employeeEntity);


    }
    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity = scheduleEntity;
    }

    public ScheduleEntity getScheduleEntity() {
        return scheduleEntity;
    }
    /*
    public void setEmployeeEntity(EmployeeEntity employeeEntity){
        this.scheduleEntity.get().setEmployee(employeeEntity);

    }

     */


    public SimpleObjectProperty<EmployeeEntity> employeeProperty() {
        return employee;
    }

    public final void setEmployee(EmployeeEntity employeeEntity){
        this.employeeProperty().set(employeeEntity);
    }

    public final EmployeeEntity getEmployee(){
        return this.employeeProperty().get();
    }

    public final SimpleObjectProperty getEmployeeProperty(){
        return this.employee;
    }



    @Override
    public ScheduleEntry createRecurrence() {
        return new ScheduleEntry(null);
    }

    public void test(){
        ScheduleCalendar scheduleCalendar = (ScheduleCalendar) this.getCalendar();
        System.out.println(scheduleCalendar.getName());
    }




}
