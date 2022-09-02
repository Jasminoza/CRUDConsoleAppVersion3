package repository.hibernate;

import model.Developer;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.engine.spi.ActionQueue;
import org.hibernate.internal.SessionImpl;
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
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            developer = session.merge(developer);
            transaction.commit();
            return session.get(Developer.class, developer.getId());
        }
    }

    @Override
    public Developer getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Developer.class, id);
        }
    }

    @Override
    public Developer update(Developer developer) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(developer);
            transaction.commit();
            return getById(developer.getId());
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
