package org.visitCalendar;

import com.calendarfx.model.Entry;
import javafx.beans.property.SimpleObjectProperty;
import org.database.employee.EmployeeEntity;
import org.database.patient.PatientEntity;
import org.database.service.ServiceEntity;
import org.database.visit.VisitEntity;

public class VisitEntry extends Entry<VisitEntity> {

    private SimpleObjectProperty<EmployeeEntity> employee;
    private SimpleObjectProperty<PatientEntity> patient;
    private SimpleObjectProperty<ServiceEntity> service;
    private SimpleObjectProperty<Boolean> isPaid;
    private SimpleObjectProperty<Boolean> isFinished;
    private SimpleObjectProperty<String> note;
    private VisitEntity visitEntity;
    public Boolean isFromDatabase;
    public VisitEntry() {
        super();

        setEmployeeProperty();
        setPatientProperty();
        setServiceProperty();
        setIsPaidProperty();
        setIsFinishedProperty();
        setNoteProperty();
    }


    private void setEmployeeProperty(){
        this.employee = new SimpleObjectProperty<>(this,"employee"){
            @Override
            public void set(EmployeeEntity employeeEntity) {
                if (!isFromDatabase) {
                    EmployeeEntity employeeEntity1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if(employeeEntity!=null ){
                        super.set(employeeEntity);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_EMPLOYEE_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(employeeEntity!=null ){
                        super.set(employeeEntity);
                    }
                    isFromDatabase = false;
                }

            }
        };
    }

    private void setPatientProperty(){
        this.patient = new SimpleObjectProperty<>(this,"patient"){
            @Override
            public void set(PatientEntity patientEntity) {
                if (!isFromDatabase) {
                    PatientEntity patientEntity1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (patientEntity != null) {
                        super.set(patientEntity);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_PATIENT_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(patientEntity !=null){
                        super.set(patientEntity);
                    }
                    isFromDatabase = false;
                }
            }
        };

    }

    public void setServiceProperty(){
        this.service = new SimpleObjectProperty<>(this,"service"){
            @Override
            public void set(ServiceEntity serviceEntity) {
                if (!isFromDatabase) {
                    ServiceEntity serviceEntity1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (serviceEntity != null) {
                        super.set(serviceEntity);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_SERVICE_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(serviceEntity !=null){
                        super.set(serviceEntity);
                    }
                    isFromDatabase = false;
                }
            }
        };
    }

    private void setIsPaidProperty(){
        this.isPaid = new SimpleObjectProperty<>(this,"isPaid"){
            @Override
            public void set(Boolean isPaid) {
                if (!isFromDatabase) {
                    Boolean ispaid1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (isPaid != null) {
                        super.set(isPaid);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_IS_PAID_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(isPaid !=null){
                        super.set(isPaid);
                    }
                    isFromDatabase = false;
                }
            }
        };
    }

    private void setIsFinishedProperty(){
        this.isFinished = new SimpleObjectProperty<>(this,"isFinished"){
            @Override
            public void set(Boolean isFinished) {
                if (!isFromDatabase) {
                    Boolean isFinished1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (isFinished != null) {
                        super.set(isFinished);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_IS_FINISHED_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(isFinished !=null){
                        super.set(isFinished);
                    }
                    isFromDatabase = false;
                }
            }
        };
    }

    private void setNoteProperty(){
        this.note = new SimpleObjectProperty<>(this,"note"){
            @Override
            public void set(String note) {
                if (!isFromDatabase) {
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (note != null) {
                        super.set(note);
                        visitCalendar.fireEvent(new VisitCalendarEvent(VisitCalendarEvent.ENTRY_NOTE_CHANGED, visitCalendar, VisitEntry.this));
                    }
                }else {
                    if(note !=null){
                        super.set(note);
                    }
                    isFromDatabase = false;
                }
            }
        };
    }


    public VisitEntity getVisitEntity() {
        return visitEntity;
    }

    public void setVisitEntity(VisitEntity visitEntity, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.visitEntity = visitEntity;
    }

    public EmployeeEntity getEmployee() {
        return employee.get();
    }

    public SimpleObjectProperty  employeeProperty() {
        return this.employee;
    }

    public void setEmployee(EmployeeEntity employee, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.employee.set(employee);
    }

    public PatientEntity getPatient() {
         return patient.get();
    }



    public SimpleObjectProperty<PatientEntity> patientProperty() {
        return patient;
    }


    public void setPatient(PatientEntity patient, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.patient.set(patient);
    }

    public ServiceEntity getService() {
        return service.get();
    }



    public SimpleObjectProperty<ServiceEntity> serviceProperty() {
        return service;
    }


    public void setService(ServiceEntity service, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.service.set(service);
    }


    public Boolean getIsPaid() {
        return isPaid.get();
    }


    public SimpleObjectProperty<Boolean> isPaidProperty() {
        return isPaid;
    }


    public void setIsPaid(Boolean isPaid, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.isPaid.set(isPaid);
    }

    public Boolean getIsFinished() {
        return isFinished.get();
    }

    public SimpleObjectProperty<Boolean> isFinishedProperty() {
        return isFinished;
    }

    public void setIsFinished(Boolean isFinished, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.isFinished.set(isFinished);
    }

    public String getNote() {
        return note.get();
    }

    public SimpleObjectProperty<String> noteProperty() {
        return note;
    }

    public void setNote(String note, Boolean isFromDatabase) {
        this.isFromDatabase = isFromDatabase;
        this.note.set(note);
    }
}
