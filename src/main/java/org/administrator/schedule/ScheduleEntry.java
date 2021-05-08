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

    private final SimpleObjectProperty<ScheduleEntity> scheduleEntity;
    private final SimpleObjectProperty<EmployeeEntity> employee;

    CRUD<EmployeeEntity> employeeEntityCRUD = new CRUD<>();
    List<EmployeeEntity> entityList = employeeEntityCRUD.getAll(EmployeeEntity.class);

    CRUD<ScheduleEntity> scheduleEntityCRUD = new CRUD<>();
    List<ScheduleEntity> scheduleList = scheduleEntityCRUD.getAll(ScheduleEntity.class);
    public ScheduleEntry(EmployeeEntity employeeEntity){
        super();
        this.scheduleEntity = new SimpleObjectProperty<>(this,"scheduleEntity"){
            @Override
            public void set(ScheduleEntity scheduleEntity) {
                super.set(scheduleList.get(0));
            }
        };

        System.out.println(getCalendar());

        //this.employee = new SimpleObjectProperty<>(this,"employee");
        this.employee = new SimpleObjectProperty<>(this,"employee"){
            @Override
            public void set(EmployeeEntity employeeEntity) {
                EmployeeEntity employeeEntity1 = this.get();
                ScheduleCalendar scheduleCalendar = (ScheduleCalendar) ScheduleEntry.this.getCalendar();
                if(employeeEntity!=null ){
                    System.out.println("jazda");
                    super.set(employeeEntity);
                    scheduleCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, scheduleCalendar,ScheduleEntry.this,employeeEntity1));
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
    ObservableList<ComboBox<EmployeeEntity>> comboBoxes = FXCollections.observableArrayList();
    public void setScheduleEntity(ScheduleEntity scheduleEntity) {
        this.scheduleEntity.set(scheduleEntity);
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

    public ScheduleEntity getScheduleEntity() {
        return this.scheduleEntity.get();
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
