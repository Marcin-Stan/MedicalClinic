package org.database.specialization;

import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class Specialization {

    public static List<SpecializationEntity> getAllSpecialization() {
        try (Session session = new Configuration().configure().buildSessionFactory().openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<SpecializationEntity> cq = cb.createQuery(SpecializationEntity.class);
            Root<SpecializationEntity> rootEntry = cq.from(SpecializationEntity.class);
            CriteriaQuery<SpecializationEntity> all = cq.select(rootEntry);
            TypedQuery<SpecializationEntity> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
