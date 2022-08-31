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
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(skill);
            transaction.commit();
            return getById(skill.getId());
        }
    }

    @Override
    public Skill getById(Long id) {
        try (Session session = getSession()) {
            return session.get(Skill.class, id);
        }
    }

    @Override
    public Skill update(Skill skill) {
        try (Session session = getSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(skill);
            transaction.commit();
            return getById(skill.getId());
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