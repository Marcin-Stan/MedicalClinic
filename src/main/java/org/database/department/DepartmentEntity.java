package org.database.department;

import org.database.schedule.ScheduleEntity;
import org.database.service.ServiceEntity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="department")
public class DepartmentEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Size(min = 2, max = 40, message
            = "Pole nazwa nie może być puste i musi zawierać co najmniej dwa znaki")
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "departmentEntity",targetEntity = ServiceEntity.class)
    private List<ServiceEntity> serviceEntities;

    @OneToMany(mappedBy = "department")
    private List<ScheduleEntity> scheduleList;

    public DepartmentEntity(){}

    public DepartmentEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DepartmentEntity)) return false;
        DepartmentEntity that = (DepartmentEntity) o;
        return id == that.id && name.equals(that.name) && serviceEntities.equals(that.serviceEntities) && scheduleList.equals(that.scheduleList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, serviceEntities, scheduleList);
    }
}
