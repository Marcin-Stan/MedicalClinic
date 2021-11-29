package org.database.patient;

import org.database.HibernateFactory;
import org.database.department.DepartmentEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.validation.*;


public class PatientController {


    public static PatientEntity getPatientByPeselNumber(String peselNumber){
        Session session = HibernateFactory.getCurrentSessionFromConfig();
        String hql = "SELECT pe from PatientEntity pe WHERE pe.peselNumber = :peselNumber";
        Query<PatientEntity> query = session.createQuery(hql,PatientEntity.class);
        query.setParameter("peselNumber",peselNumber);
        PatientEntity patientEntity = null;

        try {
            patientEntity = query.getSingleResult();
            return patientEntity;
        }catch (NoResultException ex) {
            ex.printStackTrace();
            System.out.println("Nie ma takiego pacjenta");
            return null;
        }finally {
            session.close();
        }

    }
}
