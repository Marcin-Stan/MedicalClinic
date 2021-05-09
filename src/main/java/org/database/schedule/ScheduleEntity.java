package org.database.schedule;

import org.database.department.DepartmentEntity;
import org.database.employee.EmployeeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "schedule")
public class ScheduleEntity implements Serializable {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date_start")
    private LocalDate startDate;

    @Column(name = "date_end")
    private LocalDate endDate;

    @Column(name = "time_from")
    private LocalTime timeFrom;

    @Column(name="time_to")
    private LocalTime timeTo;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name= "department_id", referencedColumnName = "id")
    private DepartmentEntity department;

    public ScheduleEntity(){}
    public ScheduleEntity(int id,LocalDate startDate,LocalDate endDate, LocalTime timeFrom, LocalTime timeTo, EmployeeEntity employee, DepartmentEntity department) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalTime getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(LocalTime timeFrom) {
        this.timeFrom = timeFrom;
    }

    public LocalTime getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(LocalTime timeTo) {
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
