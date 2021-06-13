package org.database.schedule;

import org.database.department.DepartmentEntity;
import org.database.employee.EmployeeEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class ScheduleController {

    private static final SessionFactory sessionFactory;
    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public static List<EmployeeEntity> getEmployeeByInterval(LocalDate startDate, LocalDate endDate,
                                                             LocalTime timeFrom, LocalTime timeTo,
                                                             DepartmentEntity department){

        Session session = sessionFactory.openSession();
        String hql = "SELECT se.employee from ScheduleEntity se WHERE se.startDate = :startDate " +
                "and se.endDate=:endDate " +
                "and se.timeFrom < :timeFrom  " +
                "and se.timeTo> :timeTo " +
                "and se.department = :department";

        Query<EmployeeEntity> query = session.createQuery(hql,EmployeeEntity.class);
        query.setParameter("startDate",startDate);
        query.setParameter("endDate",endDate);
        query.setParameter("timeFrom",timeFrom);
        query.setParameter("timeTo",timeTo);
        query.setParameter("department",department);

        List<EmployeeEntity> employeeEntities;
        try {
            employeeEntities = query.getResultList();
            return employeeEntities;
        }catch (NoResultException ex) {
            ex.printStackTrace();
            return null;
        }finally {
            session.close();
        }

    }


}
