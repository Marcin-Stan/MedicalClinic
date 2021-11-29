package org.database.visit;

import org.database.employee.EmployeeEntity;
import org.database.patient.PatientEntity;
import org.database.service.ServiceEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Objects;

@Entity
@Table(name="visit")
public class VisitEntity implements Serializable {

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

    @Column(name="is_paid")
    private Boolean isPaid;

    @Column(name="is_finished")
    private Boolean isFinished;

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

    public VisitEntity(int id, LocalDate startDate, LocalDate endDate, LocalTime timeFrom, LocalTime timeTo, Boolean isPaid, String patientNote,
                       byte[] medicalExamination, ServiceEntity service, EmployeeEntity employee, PatientEntity patient) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isPaid = isPaid;
        this.patientNote = patientNote;
        this.medicalExamination = medicalExamination;
        this.service = service;
        this.employee = employee;
        this.patient = patient;
    }

    public VisitEntity(int id, LocalDate startDate, LocalDate endDate, LocalTime timeFrom, LocalTime timeTo, Boolean isPaid, Boolean isFinished,
                       ServiceEntity service, EmployeeEntity employee, PatientEntity patient) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
        this.isPaid = isPaid;
        this.isFinished = isFinished;
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

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisitEntity)) return false;
        VisitEntity that = (VisitEntity) o;
        return id == that.id && startDate.equals(that.startDate) && endDate.equals(that.endDate) && timeFrom.equals(that.timeFrom) && timeTo.equals(that.timeTo) && isPaid.equals(that.isPaid) && isFinished.equals(that.isFinished) && patientNote.equals(that.patientNote) && Arrays.equals(medicalExamination, that.medicalExamination) && service.equals(that.service) && employee.equals(that.employee) && patient.equals(that.patient);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, startDate, endDate, timeFrom, timeTo, isPaid, isFinished, patientNote, service, employee, patient);
        result = 31 * result + Arrays.hashCode(medicalExamination);
        return result;
    }
}
