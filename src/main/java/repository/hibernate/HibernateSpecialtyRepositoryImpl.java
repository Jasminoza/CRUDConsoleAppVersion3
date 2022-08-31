package repository.hibernate;

import model.Specialty;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SpecialtyRepository;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class HibernateSpecialtyRepositoryImpl implements SpecialtyRepository {
    @Override
    public List<Specialty> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("From Specialty", Specialty.class).list();
        }
    }

    @Override
    public Specialty create(Specialty specialty) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(specialty);
            transaction.commit();
            return getById(specialty.getId());
        }
    }

    @Override
    public Specialty getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Specialty.class, id);
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(specialty);
            transaction.commit();
            return getById(specialty.getId());
        }
    }

    @Override
    public void delete(Long id) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.remove(getById(id));
            transaction.commit();
        }
    }

    private Session getSession(){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}
