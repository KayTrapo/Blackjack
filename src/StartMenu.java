//imports
import java.util.Scanner;

//Start of class
public class StartMenu {
	// Fields
	private boolean active = true;
	private String[] options = {"START", "EXIT"};
	
	// Constructor
	public StartMenu(Scanner input) {
		menu(input);
	}

	//Methods
	private void menu(Scanner input){
		while(active){
			for(String option:options){
				System.out.println(option);
			}

			System.out.print(">>> ");

			String navigate = input.nextLine();
			if (navigate.equalsIgnoreCase(options[0])) {
				Game game = new Game(input);
			} else if (navigate.equalsIgnoreCase(options[1])) {
				System.out.println("Thanks For Playing!");
				active = false;
				input.close();


			} else {
				System.out.println("\nInvalid Option\n");
			}
		}
	}
}

