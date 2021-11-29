package org.database.specialization;

import org.database.employee.EmployeeEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="specialization")
public class SpecializationEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;


    @Column(name = "name")
    private String specName;

    @OneToMany(mappedBy = "specialization")
    private List<EmployeeEntity> employeeEntity;


    public SpecializationEntity() {

    }

    public SpecializationEntity(int id, String specName) {
        this.id = id;
        this.specName = specName;
    }

    public int getId() {
        return id;
    }

    public void setId(int specId) {
        this.id = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    @Override
    public String toString() {
        return specName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpecializationEntity)) return false;
        SpecializationEntity that = (SpecializationEntity) o;
        return id == that.id && specName.equals(that.specName) && Objects.equals(employeeEntity, that.employeeEntity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, specName, employeeEntity);
    }
}
