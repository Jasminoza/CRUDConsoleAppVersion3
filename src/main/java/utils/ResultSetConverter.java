package utils;

import model.Developer;
import model.Skill;
import model.Specialty;
import model.Status;
import repository.SpecialtyRepository;
import repository.jdbc.JDBCSpecialtyRepositoryImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultSetConverter {
    private static final SpecialtyRepository specialtyRepository = new JDBCSpecialtyRepositoryImpl();

    public static Skill convertToSkill(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
        }
        Skill skill = new Skill();
        skill.setId(resultSet.getLong("id"));
        skill.setName(resultSet.getString("name"));
        return skill;
    }

    public static List<Skill> convertToSkillsList(ResultSet resultSet) throws SQLException {
        List<Skill> allSkills = new ArrayList<>();

        while (resultSet.next()) {
            Skill skill = convertToSkill(resultSet);
            allSkills.add(skill);
        }

        return allSkills;
    }

    public static Specialty convertToSpecialty(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
        }
        Specialty specialty = new Specialty();
        specialty.setId(resultSet.getLong("id"));
        specialty.setName(resultSet.getString("name"));
        return specialty;
    }

    public static List<Specialty> convertToSpecialtiesList(ResultSet resultSet) throws SQLException {
        List<Specialty> allSpecialties = new ArrayList<>();

        while (resultSet.next()) {
            Specialty specialty = convertToSpecialty(resultSet);
            allSpecialties.add(specialty);
        }

        return allSpecialties;
    }

    public static Developer convertToDeveloper(ResultSet resultSet) throws SQLException {
        if (resultSet.isBeforeFirst()) {
            resultSet.next();
        }

        Developer developer = new Developer();
        developer.setId(resultSet.getLong("id"));
        developer.setFirstName(resultSet.getString("firstName"));
        developer.setLastName(resultSet.getString("lastName"));
        developer.setSpecialty(specialtyRepository.getById(resultSet.getLong("specialty")));
        developer.setStatus(Status.getStatusById(resultSet.getLong("status")));
        return developer;
    }

    public static List<Developer> convertToDevelopersListWithoutSkills(ResultSet resultSet) throws SQLException {
        List<Developer> allDevelopers = new ArrayList<>();
        while (resultSet.next()) {
            Developer developer = convertToDeveloper(resultSet);
            allDevelopers.add(developer);
        }
        return allDevelopers;
    }
}
