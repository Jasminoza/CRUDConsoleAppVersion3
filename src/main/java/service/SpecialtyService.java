package service;

import model.Specialty;
import repository.SpecialtyRepository;
import repository.hibernate.HibernateSpecialtyRepositoryImpl;

import java.util.List;

public class SpecialtyService {
    private final SpecialtyRepository specialtyRepository;

    public SpecialtyService() {
        this.specialtyRepository = new HibernateSpecialtyRepositoryImpl();
    }

    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Specialty> getAll() {
        return specialtyRepository.getAll();
    }

    public Specialty create(Specialty specialty) {
        return specialtyRepository.create(specialty);
    }

    public Specialty getById(Long id) {
        return specialtyRepository.getById(id);
    }

    public Specialty update(Specialty specialty) {
        return specialtyRepository.update(specialty);
    }

    public void delete(Long id) {
        specialtyRepository.delete(id);
    }
}