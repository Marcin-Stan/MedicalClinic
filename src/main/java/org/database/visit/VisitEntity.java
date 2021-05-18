package org.database.visit;

import org.database.employee.EmployeeEntity;
import org.database.patient.PatientEntity;
import org.database.service.ServiceEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name="visit")
public class VisitEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private int id;

    @Column(name = "date_start")
    private Date dateStart;

    @Column(name = "date_end")
    private Date dateEnd;

    @Column(name = "time_from")
    private Time startTime;

    @Column(name="time_to")
    private Time endTime;

    @Column(name="is_paid")
    private Boolean isPaid;

    @Column(name = "patient_note")
    private String patientNote;

    @Column(name = "medical_examination")
    private byte[] medicalExamination;

    @ManyToOne
    @JoinColumn(name="service_id")
    private ServiceEntity service;

    @ManyToOne
    @JoinColumn(name="employee_id")
    private EmployeeEntity employee;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private PatientEntity patient;

    public VisitEntity() { }

    public VisitEntity(int id, Date dateStart, Date dateEnd, Time startTime, Time endTime, Boolean isPaid, String patientNote,
                       byte[] medicalExamination, ServiceEntity service, EmployeeEntity employee, PatientEntity patient) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isPaid = isPaid;
        this.patientNote = patientNote;
        this.medicalExamination = medicalExamination;
        this.service = service;
        this.employee = employee;
        this.patient = patient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date date) {
        this.dateStart = date;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public String getPatientNote() {
        return patientNote;
    }

    public void setPatientNote(String patientNote) {
        this.patientNote = patientNote;
    }

    public byte[] getMedicalExamination() {
        return medicalExamination;
    }

    public void setMedicalExamination(byte[] medicalExamination) {
        this.medicalExamination = medicalExamination;
    }

    public ServiceEntity getService() {
        return service;
    }

    public void setService(ServiceEntity service) {
        this.service = service;
    }

    public EmployeeEntity getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee = employee;
    }

    public PatientEntity getPatient() {
        return patient;
    }

    public void setPatient(PatientEntity patient) {
        this.patient = patient;
    }
}
