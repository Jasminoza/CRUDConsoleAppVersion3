package service;

import model.Skill;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SkillRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SkillServiceTest {
    @Mock
    private SkillRepository skillRepository;
    private SkillService skillService;
    public SkillServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.skillService = new SkillService(skillRepository);
    }

    @Test
    public void checkGetAllReturnsNotNull() {
        given(skillRepository.getAll()).willReturn(Arrays.asList(
                new Skill("write code"),
                new Skill("drink cofee"),
                new Skill("sleep"),
                new Skill("code review"))
        );
        List<Skill> skills = skillService.getAll();
        assertNotNull(skills);
        assertEquals(4, skills.size());
    }

    @Test
    public void checkGetAllReturnsNull() {
        given(skillRepository.getAll()).willReturn(null);
        List<Skill> skills = skillService.getAll();
        assertNull(skills);
    }

    @Test
    public void checkCreateReturnsNotNull() {
        Skill skill = new Skill(5L, "Abrakadabra");

        given(skillRepository.create(skill)).willReturn(skill);
        given(skillRepository.getById(5L)).willReturn(skill);

        Skill abrakadabra = skillService.create(new Skill(5L, "Abrakadabra"));

        assertNotNull(abrakadabra);
        assertEquals(Optional.of(5L), Optional.of(skillService.getById(abrakadabra.getId()).getId()));

    }

    @Test
    public void checkCreateAddsARecordToDB() {
        List<Skill> skills = new ArrayList<>();
        given(skillRepository.getAll()).willReturn(skills);

        Skill skill = new Skill("Abrakadabra");
        int skillsCountBeforeAdd = skillService.getAll().size();

        skills.add(skillService.create(skill));

        int skillsCountAfterAdd = skillService.getAll().size();
        assertEquals(skillsCountBeforeAdd + 1, skillsCountAfterAdd);
    }

    @Test
    public void checkGetById() {
        Skill skill = new Skill(1L,"Abrakadabra");
        given(skillRepository.getById(1L)).willReturn(skill);
        given(skillRepository.create(skill)).willReturn(skill);

        skill = skillService.create(skill);
        Skill skillFromDB = skillService.getById(1L);

        assertEquals(skill.getId(), skillFromDB.getId());
        assertEquals(skill.getName(), skillFromDB.getName());
        skillService.delete(skill.getId());
    }

    @Test
    public void checkUpdate() {

        Skill skill = new Skill(1L,"Abrakadabra");
        given(skillRepository.create(skill)).willReturn(skill);
        given(skillRepository.getById(1L)).willReturn(skill);
        given(skillRepository.update(skill)).willReturn(new Skill(1L, "Ahalaimahalai"));

        skill = skillService.create(skill);
        Skill skillFromDB = skillService.getById(skill.getId());

        assertEquals(skill, skillFromDB);

        skill.setName("Ahalaimahalai");
        skillFromDB = skillService.update(skill);

        assertEquals(skill, skillFromDB);
    }

    @Test
    public void checkDelete() {
        doNothing().when(skillRepository).delete(1L);

        skillService.delete(1L);
        verify(skillRepository, times(1)).delete(1L);
    }
}