package controller;

import model.Developer;
import model.Skill;
import model.Specialty;
import model.Status;
import repository.jdbc.JDBCDeveloperRepositoryImpl;
import service.DeveloperService;

import java.util.List;

public class DeveloperController {

    private final DeveloperService developerService = new DeveloperService(new JDBCDeveloperRepositoryImpl());

    public Developer createDeveloper(String firstName, String lastName, List<Skill> skills, Specialty specialty, Status status) {
        Developer developer = new Developer();
        developer.setFirstName(firstName);
        developer.setLastName(lastName);
        developer.setSkills(skills);
        developer.setSpecialty(specialty);
        developer.setStatus(status);
        return developerService.create(developer);
    }

    public List<Developer> getAllDevelopers() {
        return developerService.getAll();
    }

    public void deleteDeveloper(Long id) {
        developerService.delete(id);
    }

    public Developer updateDeveloper(Long id, String firstName, String lastName, List<Skill> skills, Specialty specialty) {
        Developer developer = new Developer(id, firstName, lastName, skills, specialty);
        return developerService.update(developer);
    }

    public Developer getById(Long id) {
        return developerService.getById(id);
    }
}
