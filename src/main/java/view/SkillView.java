package view;

import controller.SkillController;
import model.Skill;

import java.util.List;
import java.util.Scanner;

public class SkillView {
    private final Scanner scanner;
    private final SkillController skillController;

    public SkillView() {
        this.scanner = new Scanner(System.in);
        this.skillController = new SkillController();
    }

    public void createSkill() {
        boolean nameIsAlreadyExist = true;
        String name;

        List<Skill> skills = skillController.getAllSkills();

        if (skills == null || skills.size() == 0) {
            System.out.println("Skill list is empty.");
        } else {
            System.out.println("Enter skill name: ");

            while (nameIsAlreadyExist) {
                name = scanner.nextLine();
                final String finalName = name;
                if (skills.stream().anyMatch(s -> s.getName().equals(finalName))) {
                    System.out.println("Skill with such name is already exist. Please, enter another skill name.");
                } else {
                    nameIsAlreadyExist = false;
                    Skill skill = skillController.createSkill(name);
                    System.out.println("Created skill: " + skill);
                    System.out.println("id: " + skill.getId() + ", name: " + skill.getName());
                }
            }
        }
    }

    public void showAllSkills() {
        List<Skill> skills = skillController.getAllSkills();

        if (skills == null || skills.size() == 0) {
            System.out.println("Skill list is empty.");
        } else {
            System.out.println("Skills:\n===============================");
            skills.forEach(a -> System.out.println(" id: " + a.getId() + ", name: " + a.getName() + ";"));
            System.out.println("===============================");
        }

    }

    public void deleteSkill() {
        boolean idIsCorrect = false;
        Long id;

        List<Skill> skills = skillController.getAllSkills();

        if (skills == null || skills.size() == 0) {
            System.out.println("Skill list is empty.");
        } else {
            System.out.println("Enter id number to delete skill from the list: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (skills.stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        skillController.deleteSkill(id);
                    } else {
                        System.out.println("There is no skill with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
    }

    public void updateSkill() {
        boolean idIsCorrect = false;
        Long id;

        List<Skill> skills = skillController.getAllSkills();

        if (skills == null || skills.size() == 0) {
            System.out.println("Skill list is empty.");
        } else {
            showAllSkills();
            System.out.println("Please, enter id number of skill you want to update: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (skills.stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        System.out.println("Please, enter new name: ");
                        String name = scanner.nextLine();
                        skillController.updateSkill(id, name);
                    } else {
                        System.out.println("There is no skill with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
    }

    public void getById() {
        boolean idIsCorrect = false;
        Long id;
        List<Skill> skills = skillController.getAllSkills();

        if (skills == null || skills.size() == 0) {
            System.out.println("Skill list is empty.");
        } else {
            showAllSkills();
            System.out.println("Please, enter number of skill you want to see: ");

            while (!idIsCorrect) {
                try {
                    id = Long.parseLong(scanner.nextLine());
                    final Long finalId = id;
                    if (skills.stream().anyMatch(s -> s.getId().equals(finalId))) {
                        idIsCorrect = true;
                        System.out.println("id: " + skillController.getById(id).getId() +
                                ", name: " + skillController.getById(id).getName() + ".");
                    } else {
                        System.out.println("There is no skill with such id. Please, try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Please, enter correct id.");
                }
            }
        }
    }
}