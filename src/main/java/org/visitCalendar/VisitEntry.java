package org.visitCalendar;

import com.calendarfx.model.CalendarEvent;
import com.calendarfx.model.Entry;
import javafx.beans.property.SimpleObjectProperty;
import org.database.employee.EmployeeEntity;
import org.database.patient.PatientEntity;
import org.database.schedule.ScheduleEntity;
import org.database.service.ServiceEntity;
import org.database.visit.VisitEntity;
import org.scheduleCalendar.ScheduleCalendar;
import org.scheduleCalendar.ScheduleEntry;

public class VisitEntry extends Entry<VisitEntity> {

    private SimpleObjectProperty<EmployeeEntity> employee;
    private SimpleObjectProperty<PatientEntity> patient;
    private SimpleObjectProperty<ServiceEntity> service;
    private SimpleObjectProperty<Boolean> isPaid;

    public Boolean isFromDatabase;
    public VisitEntry(boolean isFromDatabased) {
        super();
        this.isFromDatabase = isFromDatabased;

        setEmployeeProperty();
        setPatientProperty();
        setServiceProperty();
        setIsPaidProperty();
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
                        visitCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, visitCalendar,VisitEntry.this,employeeEntity1));
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
                        visitCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, visitCalendar, VisitEntry.this, patientEntity1));
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

    private void setServiceProperty(){
        this.service = new SimpleObjectProperty<>(this,"service"){
            @Override
            public void set(ServiceEntity serviceEntity) {
                if (!isFromDatabase) {
                    ServiceEntity serviceEntity1 = this.get();
                    VisitCalendar visitCalendar = (VisitCalendar) VisitEntry.this.getCalendar();
                    if (serviceEntity != null) {
                        super.set(serviceEntity);
                        visitCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, visitCalendar, VisitEntry.this, serviceEntity1));
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
                        visitCalendar.fireEvent(new CalendarEvent(CalendarEvent.ENTRY_USER_OBJECT_CHANGED, visitCalendar, VisitEntry.this, ispaid1));
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



    public EmployeeEntity getEmployee() {
        return employee.get();
    }

    public SimpleObjectProperty  employeeProperty() {
        return this.employee;
    }

    public void setEmployee(EmployeeEntity employee) {
        this.employee.set(employee);
    }

    public PatientEntity getPatient() {
         return patient.get();
    }



    public SimpleObjectProperty<PatientEntity> patientProperty() {
        return patient;
    }


    public void setPatient(PatientEntity patient) {
        this.patient.set(patient);
    }

    public ServiceEntity getService() {
        return service.get();
    }



    public SimpleObjectProperty<ServiceEntity> serviceProperty() {
        return service;
    }


    public void setService(ServiceEntity service) {
        this.service.set(service);
    }

    /*
    public Boolean getIsPaid() {
        return isPaid.get();
    }

     */

    public SimpleObjectProperty<Boolean> isPaidProperty() {
        return isPaid;
    }

    /*
    public void setIsPaid(Boolean isPaid) {
        this.isPaid.set(isPaid);
    }

     */


}
