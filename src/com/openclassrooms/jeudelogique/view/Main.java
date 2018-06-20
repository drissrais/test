package com.openclassrooms.jeudelogique.view;

import java.util.Scanner;

import com.openclassrooms.jeudelogique.controler.SearchChallengerControler;
import com.openclassrooms.jeudelogique.model.SearchModel;

public class Main {

	public static void main(String[] args) {
		// Activation ou non du mode développeur via la console.
				Scanner sc = new Scanner(System.in);
				String reponse = "";
				boolean developerMode = false;
				do {
					System.out.println(
							"Voulez-vous lancer l'application en mode développeur (solution affichée) ? O pour oui/N pour non");
					reponse = sc.nextLine();
				} while ((!reponse.equals("O")) && (!reponse.equals("N")));
				sc.close();

				if (reponse.equals("O")) {
					developerMode = true;
				} else {
					developerMode = false;
				}
		
		SearchModel model = new SearchModel();
		SearchChallengerControler controler = new SearchChallengerControler(model, developerMode);
		controler.displayView();
	}

}
