package org.database.service;

import org.database.department.DepartmentEntity;
import org.database.visit.VisitEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "service")
public class ServiceEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Size(min = 2, max = 40, message
            = "Pole nazwa nie może być puste i musi zawierać co najmniej dwa znaki")
    @Column(name="name")
    private String name;

    @NotNull(message = "Pole cena nie moze być puste")
    @Column(name = "price")
    private Integer price;

    @NotNull(message = "Pole departamentu nie może być puste")
    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private DepartmentEntity departmentEntity;

    @OneToMany(mappedBy = "service")
    private List<VisitEntity> listVisit;

    public ServiceEntity(){}
    public ServiceEntity(int id, String name, Integer price, DepartmentEntity departmentEntity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.departmentEntity = departmentEntity;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public DepartmentEntity getDepartmentEntity() {
        return departmentEntity;
    }

    public void setDepartmentEntity(DepartmentEntity departmentEntity) {
        this.departmentEntity = departmentEntity;
    }

    @Override
    public String toString() {
        return name ;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceEntity)) return false;
        ServiceEntity that = (ServiceEntity) o;
        return id == that.id && name.equals(that.name) && price.equals(that.price) && departmentEntity.equals(that.departmentEntity) && Objects.equals(listVisit, that.listVisit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, departmentEntity, listVisit);
    }
}
