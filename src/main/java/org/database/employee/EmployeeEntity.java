package org.database.employee;

import org.database.schedule.ScheduleEntity;
import org.database.specialization.SpecializationEntity;
import org.database.visit.VisitEntity;
import org.employee.EmployeeType;
import org.employee.Sex;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.validation.constraints.*;


@DynamicUpdate()
@Entity
@Table(name="employee")
public class EmployeeEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Size(min = 2, max = 40, message
            = "Pole imię nie może być puste i musi zawierać co najmniej dwa znaki")
    @Column(name="first_name")
    private String firstName;

    @Size(min = 2, max = 40, message
            = "Pole nazwisko nie może być puste i musi zawierać co najmniej dwa znaki")
    @Column(name = "last_name")
    private String lastName;

    @NotBlank(message = "Pole adresu nie moze być puste")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "Pole numeru telefonu nie moze być puste")
    @Pattern(regexp="(^$|[0-9]{9})",message = "Numer telefonu musi dziewięciocyfrową liczbą  ")
    @Column(name = "tel_number")
    private String  telNumber;

    @NotNull(message = "Pole płeć nie może być puste")
    @Column(name = "sex",columnDefinition ="ENUM('Kobieta','Mężczyzna')" )
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @PastOrPresent(message = "Data nie urodzenia może być większa, niż data dzisiejsza")
    @Column(name = "birth_date")
    private LocalDate birtDate;

    @PastOrPresent(message = "Data utworzenia nie może być większa, niż data dzisiejsza")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @NotBlank(message = "Pole loginu nie moze być puste")
    @Column(name="login")
    private String login;

    @NotBlank(message = "Pole hasła nie moze być puste")
    @Column(name = "password")
    private String password;

    @Lob
    @Column(name = "photo",columnDefinition="BLOB")
    private byte[] photo;

    @NotNull(message = "Pole rola nie może być puste")
    @Column(name = "role",columnDefinition = "ENUM('doctor','nurse','administrator','receptionist')")
    @Enumerated(EnumType.STRING)
    private EmployeeType employeeType;

    @ManyToOne()
    @JoinColumn(name = "id_spec",referencedColumnName = "id")
    private SpecializationEntity specialization;

    @OneToMany(mappedBy = "employee")
    private List<ScheduleEntity> scheduleEntity;

    @OneToMany(mappedBy = "employee")
    private List<VisitEntity> listVisit;

    public EmployeeEntity(){}

    public EmployeeEntity(int id, String firstName, String lastName, String address,
                          String telNumber, Sex sex, LocalDate birtDate, LocalDate creationDate,
                          String login, String password,EmployeeType employeeType, byte[] photo,
                          SpecializationEntity specialization) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telNumber = telNumber;
        this.sex = sex;
        this.birtDate = birtDate;
        this.creationDate = creationDate;
        this.login = login;
        this.password = password;
        this.employeeType = employeeType;
        this.photo = photo;
        this.specialization = specialization;
    }

    public EmployeeEntity(int id, String firstName, String lastName, String address, String telNumber, Sex sex, LocalDate birtDate,
                          LocalDate creationDate, String login, String password, byte[] photo, EmployeeType employeeType,
                          SpecializationEntity specializationEntity) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telNumber = telNumber;
        this.sex = sex;
        this.birtDate = birtDate;
        this.creationDate = creationDate;
        this.login = login;
        this.password = password;
        this.photo = photo;
        this.employeeType = employeeType;
        this.specialization = specializationEntity;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public LocalDate getBirtDate() {
        return birtDate;
    }

    public void setBirtDate(LocalDate birtDate) {
        this.birtDate = birtDate;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public EmployeeType getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(EmployeeType employeeType) {
        this.employeeType = employeeType;
    }

    public SpecializationEntity getSpecializationEntity() {
        return specialization;
    }

    public void setSpecializationEntity(SpecializationEntity specializationEntity) {
        this.specialization = specializationEntity;
    }

    @Override
    public String toString() {
        return firstName +" "+lastName;

    }
}
