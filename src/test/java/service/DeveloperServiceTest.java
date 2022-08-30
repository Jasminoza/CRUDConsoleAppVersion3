package service;

import model.Developer;
import model.Skill;
import model.Specialty;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.DeveloperRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class DeveloperServiceTest {
    @Mock
    private DeveloperRepository developerRepository;
    private DeveloperService serviceUnderTest;
    public DeveloperServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.serviceUnderTest = new DeveloperService(developerRepository);
    }

    private List<Developer> getDevelopers() {
        List<Skill> skills1 = new ArrayList<>();
        skills1.add(new Skill("Coding"));
        skills1.add(new Skill("Coding1"));

        return List.of(
                new Developer(1L,
                        "Vasya",
                        "Pupkin",
                        skills1,
                        new Specialty("Java")),
                new Developer(2L,
                        "Petya",
                        "Vasechkin",
                        skills1,
                        new Specialty("SQL")
                )
        );
    }

    @Test
    public void checkGetAll() {
        given(developerRepository.getAll())
                .willReturn(getDevelopers());

        List<Developer> developers = serviceUnderTest.getAll();
        assertNotNull(developers);
        assertEquals(2, developers.size());
        assertEquals("Vasya", developers.get(0).getFirstName());
    }

    @Test
    public void checkCreate() {
        List<Skill> skills1 = new ArrayList<>();
        skills1.add(new Skill("Coding"));
        skills1.add(new Skill("Coding1"));

        given(developerRepository
                .create(new Developer(1L,
                    "Vasya",
                    "Pupkin",
                    skills1,
                    new Specialty("Java"))))
                .willReturn(new Developer(1L,
                    "Vasya",
                    "Pupkin",
                    skills1,
                    new Specialty("Java")));

        Developer developer = serviceUnderTest
                .create(new Developer(1L,
                    "Vasya",
                    "Pupkin",
                    skills1,
                    new Specialty("Java")));
        assertEquals("Vasya", developer.getFirstName());
        assertEquals(1L, developer.getId().longValue());
    }

    @Test
    public void checkGetByID() {
        List<Skill> skills1 = new ArrayList<>();
        skills1.add(new Skill("Coding"));
        skills1.add(new Skill("Coding1"));

        given(developerRepository.getById(1L)).willReturn(new Developer(1L,
                "Vasya",
                "Pupkin",
                skills1,
                new Specialty("Java")));

        Developer developer = serviceUnderTest.getById(1L);
        assertEquals("Vasya", developer.getFirstName());
        assertEquals(1L, developer.getId().longValue());
    }

    @Test
    public void checkUpdate() {
        List<Skill> skills1 = new ArrayList<>();
        skills1.add(new Skill("Coding"));
        skills1.add(new Skill("Coding1"));

        given(developerRepository.getById(1L)).willReturn(new Developer(1L,
                "Vasya",
                "Pupkin",
                skills1,
                new Specialty("Java")));


        given(developerRepository
                .update(new Developer(1L,
                    "Petya",
                    "Pupkin",
                    skills1,
                    new Specialty("Java"))))
                .willReturn(new Developer(1L,
                    "Vasya",
                    "Pupkin",
                    skills1,
                    new Specialty("Java")));

        Developer developer = serviceUnderTest.getById(1L);
        assertEquals("Vasya", developer.getFirstName());
        assertEquals(1L, developer.getId().longValue());

        developer.setFirstName("Petya");
        serviceUnderTest.update(developer);

        assertEquals("Petya", serviceUnderTest.getById(1L).getFirstName());
    }

    @Test
    public void checkDelete() {
        doNothing().when(developerRepository).delete(1L);

        serviceUnderTest.delete(1L);
        verify(developerRepository, times(1)).delete(1L);
    }
}
