package org.database.operation;

import javafx.scene.control.Alert;
import org.database.patient.PatientEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.validator.AlertValidator;

import javax.persistence.PersistenceException;
import javax.validation.*;
import java.util.List;
import java.util.Set;

public class CRUD<T> {


    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    public List<T> getAll(Class<T> type) {
        Session session = new Configuration().configure().buildSessionFactory().openSession();

        try{
            return session.createQuery("FROM "+type.getSimpleName(),type).getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    public void update(T entity){
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        String errorMessage="";
        try{

            for (ConstraintViolation<T> violation : violations) {
                errorMessage+=violation.getMessage()+"\n";
            }
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja","Sukces","Pomyślnie zaktualizowano dane", Alert.AlertType.INFORMATION);
        }catch (ConstraintViolationException e){
            AlertValidator.printALert("Błąd","Nie można zaktualizować danych",errorMessage, Alert.AlertType.ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public boolean delete(T entity){
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja","Sukces","Pomyślnie usunięto obiekt ", Alert.AlertType.INFORMATION);
            return true;
        } catch (PersistenceException e) {
            AlertValidator.printALert("Błąd",
                    "Podany obiekt nie może zostać usunięty",
                    "Sprawdź, czy nie jest przypisany do innych tabel",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public boolean save(T entity){
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        Set<ConstraintViolation<T>> violations = validator.validate(entity);
        String errorMessage="";
        try{
            for (ConstraintViolation<T> violation : violations) {
                errorMessage+=violation.getMessage()+"\n";
            }
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja","Sukces","Utworzono nowy obiekt!", Alert.AlertType.INFORMATION);
            return true;
        }catch (ConstraintViolationException e){
            AlertValidator.printALert("Błąd","Nie można utworzyć obiektu",errorMessage, Alert.AlertType.ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return false;
    }




}
