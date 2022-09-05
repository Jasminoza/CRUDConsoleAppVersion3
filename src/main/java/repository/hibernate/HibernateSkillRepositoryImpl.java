package repository.hibernate;

import model.Skill;
import org.hibernate.Session;
import org.hibernate.Transaction;
import repository.SkillRepository;
import util.HibernateSessionFactoryUtil;

import java.util.List;

public class HibernateSkillRepositoryImpl implements SkillRepository {
    @Override
    public List<Skill> getAll() {
        try (Session session = getSession()) {
            return session.createQuery("From Skill", Skill.class).list();
        }
    }

    @Override
    public Skill create(Skill skill) {
        return saveSkillToDB(skill);
    }

    @Override
    public Skill getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Skill.class, id);
        }
    }

    @Override
    public Skill update(Skill skill) {
        return saveSkillToDB(skill);
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

    private Skill saveSkillToDB(Skill skill) {
        Transaction transaction = null;

        try (Session session = getSession()) {
            transaction = session.beginTransaction();
            skill = session.merge(skill);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }

        return ((skill.getId() != null) ? skill : null);
    }

    private Session getSession(){
        return HibernateSessionFactoryUtil.getSessionFactory().openSession();
    }
}