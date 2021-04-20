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

    public void update(T entity,String updatedEntity){
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
            AlertValidator.printALert("Błąd","Nie można zaktualizować danych "+updatedEntity,errorMessage, Alert.AlertType.ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }

    }

    public boolean delete(T entity, String deltedEntity){
        Session session = new Configuration().configure().buildSessionFactory().openSession();
        try {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja","Sukces","Pomyślnie usunięto "+ deltedEntity +"a", Alert.AlertType.INFORMATION);
            return true;
        } catch (PersistenceException e) {
            AlertValidator.printALert("Błąd",
                    "Podany "+ deltedEntity + "nie może zostać usunięty",
                    "Sprawdź, czy "+ deltedEntity + "nie jest przypisany do innych tabel",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }finally {
            session.close();
        }
        return false;
    }

    public boolean save(T entity, String savedEntity){
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
            AlertValidator.printALert("Informacja","Sukces","Utworzono pacjenta", Alert.AlertType.INFORMATION);
            return true;
        }catch (ConstraintViolationException e){
            AlertValidator.printALert("Błąd","Nie można utworzyć pacjenta",errorMessage, Alert.AlertType.ERROR);
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            session.close();
        }
        return false;
    }




}
