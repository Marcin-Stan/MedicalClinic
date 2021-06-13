package org.database.service;

import org.database.department.DepartmentEntity;
import org.database.patient.PatientEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;

public class ServiceController {

    private static final SessionFactory sessionFactory;
    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static List<ServiceEntity> getServiceByDepartmentName(DepartmentEntity departmentEntity){
        Session session = sessionFactory.openSession();
        String hql = "SELECT se from ServiceEntity se WHERE se.departmentEntity = :departmentEntity";
        Query<ServiceEntity> query = session.createQuery(hql,ServiceEntity.class);
        query.setParameter("departmentEntity",departmentEntity);

        List<ServiceEntity>  serviceEntity = null;
        try {
            serviceEntity = query.getResultList();
            return serviceEntity;
        }catch (NoResultException ex) {
            ex.printStackTrace();
            System.out.println("Nie ma takiej usługi");
            return null;
        }finally {
            session.close();
        }

    }
}
