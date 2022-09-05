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
        return saveSpecialtyToDB(specialty);
    }

    @Override
    public Specialty getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Specialty.class, id);
        }
    }

    @Override
    public Specialty update(Specialty specialty) {
        return saveSpecialtyToDB(specialty);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            session.remove(getById(id));
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    private Specialty saveSpecialtyToDB(Specialty specialty) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            specialty = session.merge(specialty);
            transaction.commit();
            return getById(specialty.getId());
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return ((specialty.getId() != null) ? specialty : null);
    }

    private Session getSession(){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}
