package org.database.schedule;

import org.database.department.DepartmentEntity;
import org.database.employee.EmployeeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "schedule")
public class ScheduleEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "date")
    private Date date;

    @Column(name = "time_from")
    private Time timeFrom;

    @Column(name="time_to")
    private Time timeTo;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name= "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    public ScheduleEntity(){}
    public ScheduleEntity(int id, Date date, Time timeFrom, Time timeTo, EmployeeEntity employee, DepartmentEntity department) {
        this.id = id;
        this.date = date;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.employee = employee;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(Time timeFrom) {
        this.timeFrom = timeFrom;
    }

    public Time getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(Time timeTo) {
        this.timeTo = timeTo;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public DepartmentEntity getDepartment() {
        return department;
    }

    public void setDepartment(DepartmentEntity department) {
        this.department = department;
    }
}
