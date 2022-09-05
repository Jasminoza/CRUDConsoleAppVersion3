package repository.hibernate;

import model.Developer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.DeveloperRepository;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public List<Developer> getAll() {
        try (Session session = getSession()) {

            List<Developer> developers = session.createQuery("From Developer", Developer.class).getResultList();

            for (Developer developer : developers) {
                Hibernate.initialize(developer.getSkills());
            }

            return developers;
        }
    }

    @Override
    public Developer create(Developer developer) {
        return saveDeveloperToDB(developer);
    }

    @Override
    public Developer getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Developer.class, id);
        }
    }

    @Override
    public Developer update(Developer developer) {
        return saveDeveloperToDB(developer);
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

    private Developer saveDeveloperToDB(Developer developer) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            developer = session.merge(developer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return ((developer.getId() != null) ? developer : null);
    }

    private Session getSession() {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}