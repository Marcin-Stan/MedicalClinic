package org.database.employee;

import javafx.scene.control.Alert;
import org.administrator.employee.AES256;
import org.employee.EmployeeType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.validator.AlertValidator;

import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.validation.*;

import java.util.List;
import java.util.Set;

public class Employee {

    private static final SessionFactory sessionFactory;
    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }
    public static boolean checkEmployeeLoginAndPassword(String login, String password){
        String encryptedString = AES256.encrypt(password);

        Session session = sessionFactory.openSession();
        if(getEmployeeIdByLogin(login)!=0){
            String hql = "SELECT e from EmployeeEntity e WHERE e.password = :encryptedString and e.id = :id";
            Query<EmployeeEntity> query = session.createQuery(hql,EmployeeEntity.class);
            query.setParameter("encryptedString",encryptedString);
            query.setParameter("id",getEmployeeIdByLogin(login));

            EmployeeEntity employeeEntity = null;
            try{
                employeeEntity = query.getSingleResult();
                return true;
            }catch (NoResultException e){
                return false;
            }finally {
                session.close();
            }
        }
        return false;
    }


    private static int getEmployeeIdByLogin(String login){
        Session session = sessionFactory.openSession();
        String hql = "SELECT e from EmployeeEntity e WHERE e.login = :login";
        Query<EmployeeEntity> query = session.createQuery(hql,EmployeeEntity.class);
        query.setParameter("login",login);
        EmployeeEntity employeeEntity = null;

        try {
            employeeEntity = query.getSingleResult();
            int id = employeeEntity.getId();
            return id;
        }catch (NoResultException ex) {
            ex.printStackTrace();
            System.out.println("Nie ma takiego użytkownika");
            return 0;
        }finally {
            session.close();
        }

    }

    public static EmployeeType getEmployeeTypeByLogin(String login){
        Session session = sessionFactory.openSession();
        String hql = "SELECT e from EmployeeEntity e WHERE e.login = :login";
        Query<EmployeeEntity> query = session.createQuery(hql,EmployeeEntity.class);
        query.setParameter("login",login);

        EmployeeEntity employeeEntity = null;

        try{
            employeeEntity = query.getSingleResult();
            EmployeeType employeeType = employeeEntity.getEmployeeType();
            return employeeType;
        }catch (NoResultException e){
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

    public static EmployeeEntity getEmployeeByLogin(String login){
        Session session = sessionFactory.openSession();
        String hql = "SELECT e from EmployeeEntity e WHERE e.login = :login";
        Query<EmployeeEntity> query = session.createQuery(hql,EmployeeEntity.class);
        query.setParameter("login",login);

        EmployeeEntity employeeEntity = null;

        try{
            employeeEntity = query.getSingleResult();
            return employeeEntity;
        }catch (NoResultException e){
            e.printStackTrace();
            return null;
        }finally {
            session.close();
        }
    }

}
