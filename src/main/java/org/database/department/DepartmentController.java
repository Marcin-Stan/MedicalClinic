package org.database.department;

import org.database.HibernateFactory;
import org.database.schedule.ScheduleEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class DepartmentController {


    public static DepartmentEntity getDepartmentByName(String name){
        Session session = HibernateFactory.getCurrentSessionFromConfig();
        String hql = "SELECT de from DepartmentEntity de WHERE de.name = :name";
        Query<DepartmentEntity> query = session.createQuery(hql,DepartmentEntity.class);
        query.setParameter("name",name);
        DepartmentEntity departmentEntity = null;

        try {
            departmentEntity = query.getSingleResult();
            return departmentEntity;
        }catch (NoResultException ex) {
            ex.printStackTrace();
            System.out.println("Nie ma takiego departamentu");
            return null;
        }finally {
            session.close();
        }

    }
}
