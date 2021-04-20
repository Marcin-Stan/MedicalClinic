package org.database.patient;

import org.database.visit.VisitEntity;
import org.employee.Sex;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="patient")
public class PatientEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name="first_name")
    @Size(min = 2, max = 40, message
            = "Pole imię nie może być puste i musi zawierać co najmniej dwa znaki")
    private String firstName;


    @Column(name = "last_name")
    @Size(min = 2, max = 40, message
            = "Pole nazwisko nie może być puste i musi zawierać co najmniej dwa znaki")
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

    @NotNull(message = "Pole PESEL nie może być puste")
    @Column(name="pesel_number")
    private String peselNumber;

    @PastOrPresent(message = "Data nie urodzenia może być większa, niż data dzisiejsza")
    @Column(name = "birth_date")
    private LocalDate birtDate;

    @PastOrPresent(message = "Data utworzenia nie może być większa, niż data dzisiejsza")
    @Column(name = "creation_date")
    private LocalDate creationDate;

    @OneToMany(mappedBy = "patient")
    List<VisitEntity> listPatient;

    public PatientEntity(){}

    public PatientEntity(int id, String firstName, String lastName, String address, String telNumber, Sex sex, String peselNumber, LocalDate birtDate, LocalDate creationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telNumber = telNumber;
        this.sex = sex;
        this.peselNumber = peselNumber;
        this.birtDate = birtDate;
        this.creationDate = creationDate;
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

    public String getPeselNumber() {
        return peselNumber;
    }

    public void setPeselNumber(String peselNumber) {
        this.peselNumber = peselNumber;
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
}
