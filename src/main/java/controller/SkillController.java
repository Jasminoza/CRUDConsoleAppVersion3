package controller;

import model.Skill;
import repository.hibernate.HibernateSkillRepositoryImpl;
import service.SkillService;

import java.util.List;

public class SkillController {
    private final SkillService skillService;

    public SkillController() {
        this.skillService = new SkillService(new HibernateSkillRepositoryImpl());
    }

    public Skill createSkill(String name) {
        Skill skill = new Skill();
        skill.setName(name);
        return skillService.create(skill);
    }

    public List<Skill> getAllSkills() {
        return skillService.getAll();
    }

    public void deleteSkill(Long id) {
        skillService.delete(id);
    }

    public Skill updateSkill(Long id, String name) {
        Skill skill = new Skill(id, name);
        return skillService.update(skill);
    }

    public Skill getById(Long id) {
        return skillService.getById(id);
    }
}
