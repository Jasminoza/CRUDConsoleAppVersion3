package service;

import model.Specialty;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import repository.SpecialtyRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class SpecialtyServiceTest {
    @Mock
    private SpecialtyRepository specialtyRepository;
    private SpecialtyService specialtyService;
    public SpecialtyServiceTest() {
        MockitoAnnotations.openMocks(this);
        this.specialtyService = new SpecialtyService(specialtyRepository);
    }

    @Test
    public void checkGetAllReturnsNotNull() {
        given(specialtyRepository.getAll()).willReturn(Arrays.asList(
                new Specialty("Java"),
                new Specialty("SQL"),
                new Specialty("Python"),
                new Specialty("C++"))
        );
        List<Specialty> specialties = specialtyService.getAll();
        assertNotNull(specialties);
        assertEquals(4, specialties.size());
        assertEquals("Java", specialties.get(0).getName());
    }

    @Test
    public void checkCreate() {

        given(specialtyRepository.create(new Specialty(1L, "Java"))).willReturn(new Specialty(1L, "Java"));

        Specialty specialty = specialtyService.create(new Specialty(1L, "Java"));
        assertEquals(specialty.getName(), "Java");
        assertEquals(1L, specialty.getId().longValue());
    }

    @Test
    public void checkGetByID() {
        given(specialtyRepository.getById(1L)).willReturn(new Specialty(1L, "Java"));

        Specialty specialty = specialtyService.getById(1L);
        assertEquals(specialty.getName(), "Java");
        assertEquals(1L, specialty.getId().longValue());
    }

    @Test
    public void checkUpdate() {
        given(specialtyRepository.getById(1L)).willReturn(new Specialty(1L, "Java"));
        given(specialtyRepository.update(new Specialty(1L, "Kotlin"))).willReturn(new Specialty(1L, "Kotlin"));

        Specialty specialty = specialtyService.getById(1L);
        assertEquals(specialty.getName(), "Java");
        assertEquals(1L, specialty.getId().longValue());

        specialty.setName("Kotlin");
        specialtyService.update(specialty);

        assertEquals(specialtyService.getById(1L).getName(), "Kotlin");
    }
    @Test
    public void checkDelete() {
        doNothing().when(specialtyRepository).delete(1L);

        specialtyService.delete(1L);
        verify(specialtyRepository, times(1)).delete(1L);
    }
}
