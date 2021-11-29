package org.database.employee;

import org.administrator.employee.AES256;
import org.database.HibernateFactory;
import org.employee.EmployeeType;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;

public class EmployeeController {


    public static boolean checkEmployeeLoginAndPassword(String login, String password){
        String encryptedString = AES256.encrypt(password);

        Session session = HibernateFactory.getCurrentSessionFromConfig();
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
        Session session = HibernateFactory.getCurrentSessionFromConfig();
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
            return 0;
        }finally {
            session.close();
        }

    }

    public static EmployeeType getEmployeeTypeByLogin(String login){
        Session session = HibernateFactory.getCurrentSessionFromConfig();
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
        Session session = HibernateFactory.getCurrentSessionFromConfig();
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
