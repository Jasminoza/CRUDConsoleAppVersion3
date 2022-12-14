package view;

import controller.DeveloperController;
import controller.SkillController;
import controller.SpecialtyController;
import model.Developer;
import model.Skill;
import model.Specialty;
import model.Status;

import java.util.*;
import java.util.stream.Collectors;

public class DeveloperView {

    private final DeveloperController developerController;
    private final SkillController skillController;
    private final SpecialtyController specialtyController;
    private final Scanner scanner;
    private final SkillView skillView;
    private final SpecialtyView specialtyView;

    public DeveloperView() {
        this.developerController = new DeveloperController();
        this.skillController= new SkillController();
        this.specialtyController = new SpecialtyController();
        this.scanner = new Scanner(System.in);
        this.skillView = new SkillView();
        this.specialtyView = new SpecialtyView();
    }

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
        Developer createdDeveloper = developerController.createDeveloper(firstname, lastname, skills, specialty, status);
        System.out.println("Developer was created successfully:\n" + createdDeveloper);
    }

    private List<Skill> addSkillsToList() {
        List<Skill> skills = skillController.getAllSkills();
        if (skills == null || skills.size() == 0) {
            System.out.println("Please, add some skills to skills list first, its empty.");
            return null;
        } else {
            boolean choiceIsOver = false;
            Map<Long, Skill> chosenSkills = new HashMap<>();
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
            return new ArrayList<>(chosenSkills.values());
        }
    }

    private Specialty chooseSpecialty() {
        boolean idIsCorrect = false;
        Long id;

        List<Specialty> specialties = specialtyController.getAllSpecialties();

        if (specialties == null || specialties.size() == 0) {
            System.out.println("Please, add some skills to skills list first, its empty.");
        } else {
            specialtyView.showAllSpecialties();
            System.out.println("Please, enter id number of specialty you want to choose: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (specialties.stream().anyMatch(s -> s.getId().equals(finalId))) {
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
        List<Developer> developers = developerController.getAllDevelopers();

        if (developers == null || developers.size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            System.out.println("Developers:\n=============================================================================================");
            developers
                    .forEach(dev -> System.out.println("id: " + dev.getId() + ", first name: " + dev.getFirstName()
                            + ", last name: " + dev.getLastName() + ", skills: " + dev.showSkills(dev.getSkills())
                            + "specialty: " + dev.getSpecialty().getName() + ", status: " + dev.getStatus().toString()));
            System.out.println("=============================================================================================");
        }
    }

    public void deleteDeveloper() {
        boolean idIsCorrect = false;
        Long id;

        List<Developer> developers = developerController.getAllDevelopers();

        if (developers == null || developers.size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            System.out.println("Enter id number to delete developer from the list: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (developers.stream().anyMatch(s -> s.getId().equals(finalId))) {
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
        List<Developer> developers = developerController.getAllDevelopers();

        if (developers == null || developers.size() == 0) {
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

        List<Developer> developers = developerController.getAllDevelopers();

        if (developers == null || developers.size() == 0) {
            System.out.println("Developers list is empty.");
        } else {
            showAllDevelopers();
            System.out.println("Please, enter the ID of the developer you want to view: ");
            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (developers.stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        developers.stream().filter(s -> s.getId().equals(finalId))
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