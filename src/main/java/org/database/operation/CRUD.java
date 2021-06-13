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

    private static final SessionFactory sessionFactory;
    static {
        sessionFactory = new Configuration().configure().buildSessionFactory();
    }

    public List<T> getAll(Class<T> type) {

        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM " + type.getSimpleName(), type).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(T entity, boolean showInfo){
        StringBuilder errorMessage = new StringBuilder();
        try (Session session = sessionFactory.openSession()) {
            Set<ConstraintViolation<T>> violations = validator.validate(entity);

            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
            if (showInfo) {
                AlertValidator.printALert("Informacja", "Sukces", "Pomyślnie zaktualizowano dane", Alert.AlertType.INFORMATION);
            }
        } catch (ConstraintViolationException e) {
            AlertValidator.printALert("Błąd", "Nie można zaktualizować danych", errorMessage.toString(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean delete(T entity){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.delete(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja", "Sukces", "Pomyślnie usunięto obiekt ", Alert.AlertType.INFORMATION);
            return true;
        } catch (PersistenceException e) {
            AlertValidator.printALert("Błąd",
                    "Podany obiekt nie może zostać usunięty",
                    "Sprawdź, czy nie jest przypisany do innych tabel",
                    Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return false;
    }

    public boolean save(T entity){

        StringBuilder errorMessage = new StringBuilder();
        try (Session session = sessionFactory.openSession()) {
            Set<ConstraintViolation<T>> violations = validator.validate(entity);
            for (ConstraintViolation<T> violation : violations) {
                errorMessage.append(violation.getMessage()).append("\n");
            }
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
            AlertValidator.printALert("Informacja", "Sukces", "Utworzono nowy obiekt!", Alert.AlertType.INFORMATION);
            return true;
        } catch (ConstraintViolationException e) {
            AlertValidator.printALert("Błąd", "Nie można utworzyć obiektu", errorMessage.toString(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }




}
