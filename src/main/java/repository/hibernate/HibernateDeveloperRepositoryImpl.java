package repository.hibernate;

import model.Developer;
import model.Skill;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.MutationQuery;
import org.hibernate.query.Query;
import repository.DeveloperRepository;
import util.HibernateSessionFactoryUtil;

import java.util.ArrayList;
import java.util.List;

public class HibernateDeveloperRepositoryImpl implements DeveloperRepository {
    @Override
    public List<Developer> getAll() {
        try (Session session = getSession()) {

            List<Developer> developers = session.createQuery("From Developer", Developer.class).getResultList();
            MutationQuery query = session.createNativeMutationQuery("Select * from developers_skills");
            query.executeUpdate();

            for (Developer developer : developers) {
                List<Skill> skills = new ArrayList<>();
                developer.setSkills(skills);
            }
            return developers;
        }
    }

    @Override
    public Developer create(Developer developer) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(developer);
            transaction.commit();
            return getById(developer.getId());
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