package view;

import controller.SpecialtyController;
import model.Specialty;

import java.util.Scanner;

public class SpecialtyView {
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner scanner = new Scanner(System.in);

    public void showAllSpecialties() {

        if (specialtyController.getAllSpecialties() != null && specialtyController.getAllSpecialties().size() != 0) {
            System.out.println("Specialties:\n===============================");
            specialtyController.getAllSpecialties()
                    .forEach(s -> System.out.println("id: " + s.getId() + ", name: " + s.getName()));
            System.out.println("===============================");
        } else {
            System.out.println("Specialties list is empty.");
        }
    }

    public void createSpecialty() {
        System.out.println("Please, enter name for a new specialty:");
        String name = scanner.nextLine();
        Specialty specialty = specialtyController.createSpecialty(name);
        System.out.println("Created specialty: " + specialty);
        System.out.println("id: " + specialty.getId() + ", name: " + specialty.getName());
    }

    public void deleteSpecialty() {
        boolean idIsCorrect = false;
        Long id;

        if (specialtyController.getAllSpecialties() != null && specialtyController.getAllSpecialties().size() != 0) {
            System.out.println("Enter id number to delete specialty from the list: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (specialtyController.getAllSpecialties().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        specialtyController.deleteSpecialty(id);
                    } else {
                        System.out.println("There is no specialty with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        } else {
            System.out.println("Specialties list is empty.");
        }
    }

    public void updateSpecialty() {
        boolean idIsCorrect = false;
        Long id;

        if (specialtyController.getAllSpecialties() != null && specialtyController.getAllSpecialties().size() != 0) {
            showAllSpecialties();
            System.out.println("Please, enter id number of specialty you want to update: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (specialtyController.getAllSpecialties().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        System.out.println("Please, enter new name: ");
                        String name = scanner.nextLine();
                        specialtyController.updateSpecialty(id, name);
                    } else {
                        System.out.println("There is no specialty with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        } else {
            System.out.println("Specialties list is empty.");
        }
    }

    public void getById() {
        boolean idIsCorrect = false;
        Long id;

        if (specialtyController.getAllSpecialties() != null && specialtyController.getAllSpecialties().size() != 0) {

            showAllSpecialties();
            System.out.println("Please, enter number of specialty you want to see: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (specialtyController.getAllSpecialties().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        System.out.println("id: " + specialtyController.getById(id).getId() + ", name: " + specialtyController.getById(id).getName() + ".");
                    } else {
                        System.out.println("There is no skill with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        } else {
            System.out.println("Specialties list is empty.");
        }
    }

}
