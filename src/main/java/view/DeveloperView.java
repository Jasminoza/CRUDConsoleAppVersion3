package view;

import controller.DeveloperController;
import controller.SkillController;
import controller.SpecialtyController;
import model.Skill;
import model.Specialty;
import model.Status;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class DeveloperView {

    private final DeveloperController developerController = new DeveloperController();
    private final SkillController skillController = new SkillController();
    private final SpecialtyController specialtyController = new SpecialtyController();
    private final Scanner scanner = new Scanner(System.in);
    private final SkillView skillView = new SkillView();
    private final SpecialtyView specialtyView = new SpecialtyView();

    public void createDeveloper() {
        System.out.println("Enter developer's first name: ");
        String firstname = scanner.nextLine();
        System.out.println("Enter developer's last name: ");
        String lastname = scanner.nextLine();
        System.out.println("Enter id of skill you want to add: ");
        List<Skill> skills = addSkillsToList();
        System.out.println("Enter id of specialty you want to add: ");
        Specialty specialty = chooseSpecialty();
        Status status = Status.ACTIVE;
        developerController.createDeveloper(firstname, lastname, skills, specialty, status);
    }

    private List<Skill> addSkillsToList() {
        if (skillController.getAllSkills() == null && skillController.getAllSkills().size() == 0) {
            System.out.println("Please, add some skills to skills list first, its empty.");
            return null;
        } else {
            boolean choiceIsOver = false;
            HashMap<Long, Skill> chosenSkills = new HashMap<>();
            skillView.showAllSkills();

            while (!choiceIsOver) {
                System.out.println("Please, enter id number of skill you want to add: ");
                while (true) {
                    try {
                        Long id = Long.parseLong(scanner.nextLine());
                        if (skillController.getById(id) == null) {
                            System.out.println("There is no skill with such id. Please, try again.");
                        } else {
                            if (!chosenSkills.containsKey(id)) {
                                chosenSkills.put(id, skillController.getById(id));
                            } else {
                                System.out.println("Chosen skill is already selected.");
                            }
                            System.out.println("Do you want to add another skill? (y/n)");
                            String answer = scanner.nextLine();
                            if (answer.equalsIgnoreCase("no") || answer.equalsIgnoreCase("n")) {
                                choiceIsOver = true;
                            }
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Please, enter correct id.");
                    }
                }
            }
            return chosenSkills.values().stream().toList();
        }
    }

    private Specialty chooseSpecialty() {
        boolean idIsCorrect = false;
        Long id;

        if (specialtyController.getAllSpecialties() == null && specialtyController.getAllSpecialties().size() == 0) {
            System.out.println("Please, add some skills to skills list first, its empty.");
        } else {
            specialtyView.showAllSpecialties();
            System.out.println("Please, enter id number of specialty you want to choose: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (specialtyController.getAllSpecialties().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        return specialtyController.getById(id);
                    } else {
                        System.out.println("There is no specialty with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
        return null;
    }

    public void showAllDevelopers() {
        if (developerController.getAllDevelopers() == null && developerController.getAllDevelopers().size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            System.out.println("Developers:\n=============================================================================================");
            developerController.getAllDevelopers()
                    .forEach(dev -> System.out.println("id: " + dev.getId() + ", first name: " + dev.getFirstName()
                            + ", last name: " + dev.getLastName() + ", skills: " + dev.showSkills(dev.getSkills())
                            + "specialty: " + dev.getSpecialty().getName() + ", status: " + dev.getStatus().toString()));
            System.out.println("=============================================================================================");
        }
    }

    public void deleteDeveloper() {
        boolean idIsCorrect = false;
        Long id;
        if (developerController.getAllDevelopers() == null && developerController.getAllDevelopers().size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            System.out.println("Enter id number to delete developer from the list: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (developerController.getAllDevelopers().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        developerController.deleteDeveloper(id);
                    } else {
                        System.out.println("There is no developer with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
    }

    public void updateDeveloper() {
        if (developerController.getAllDevelopers() == null && developerController.getAllDevelopers().size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            showAllDevelopers();
            Long id = checkIdForExist();
            System.out.println("Enter developer's first name: ");
            String firstname = scanner.nextLine();
            System.out.println("Enter developer's last name: ");
            String lastname = scanner.nextLine();
            System.out.println("Enter id of skill you want to add: ");
            List<Skill> skills = addSkillsToList();
            System.out.println("Enter id of specialty you want to add: ");
            Specialty specialty = chooseSpecialty();
            developerController.updateDeveloper(id, firstname, lastname, skills, specialty);
        }

    }

    private Long checkIdForExist() {
        Long id;
        System.out.println("Enter developer's id you want to update: ");
        while (true) {
            try {
                id = Long.parseLong(scanner.nextLine());
                final Long finalId = id;
                if (developerController.getAllDevelopers().stream().anyMatch(s -> s.getId().equals(finalId))) {
                    return id;
                } else {
                    System.out.println("There is no developers with such id.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please, enter correct id.");
            }
        }
    }

    public void getById() {
        boolean idIsCorrect = false;
        Long id;

        if (developerController.getAllDevelopers() == null &&
                developerController.getAllDevelopers().size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            showAllDevelopers();
            System.out.println("Please, enter the ID of the developer you want to view: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (developerController.getAllDevelopers().stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        developerController.getAllDevelopers().stream().filter(s -> s.getId().equals(finalId))
                                .forEach(dev -> System.out.println("id: " + dev.getId() + ", first name: " + dev.getFirstName()
                                        + ", last name: " + dev.getLastName() + ", skills: " + dev.showSkills(dev.getSkills())
                                        + "specialty: " + dev.getSpecialty().getName() + ", status: " + dev.getStatus().toString()));
                    } else {
                        System.out.println("There is no developer with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
    }
}


