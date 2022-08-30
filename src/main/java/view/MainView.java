package view;

import java.util.Scanner;

public class MainView {
    private final SkillView skillView = new SkillView();
    private final SpecialtyView specialtyView = new SpecialtyView();
    private final DeveloperView developerView = new DeveloperView();
    private final Scanner sc = new Scanner(System.in);

    public void mainMenu() {

        String choice = "";
        while (!choice.equals("0")) {
            System.out.println("""
                    \nHello. Enter a digit from the following:\s
                    1. to see all developers.            11. to see all skills.\s             21. to see all specialties.\s
                    2. to show developer by its id.      12. to show skill by its id.\s       22. to show specialty by its id.\s
                    3. to add a developer.               13. to add a skill.\s                23. to add a specialty.\s
                    4. to delete a developer.            14. to delete a skill.\s             24. to delete a specialty.\s
                    5. to update developer.              15. to update a skill.\s             25. to update a specialty.\s
                                        
                    0. to exit the program.
                    """);
            choice = sc.nextLine();
            switch (choice) {

                case "1" -> developerView.showAllDevelopers();
                case "2" -> developerView.getById();
                case "3" -> developerView.createDeveloper();
                case "4" -> developerView.deleteDeveloper();
                case "5" -> developerView.updateDeveloper();

                case "11" -> skillView.showAllSkills();
                case "12" -> skillView.getById();
                case "13" -> skillView.createSkill();
                case "14" -> skillView.deleteSkill();
                case "15" -> skillView.updateSkill();

                case "21" -> specialtyView.showAllSpecialties();
                case "22" -> specialtyView.getById();
                case "23" -> specialtyView.createSpecialty();
                case "24" -> specialtyView.deleteSpecialty();
                case "25" -> specialtyView.updateSpecialty();

                case "0" -> {
                    System.out.println("Goodbye.");
                    System.exit(0);
                }
                default -> System.out.println("Please, enter a correct digit.");
            }
        }
    }
}
