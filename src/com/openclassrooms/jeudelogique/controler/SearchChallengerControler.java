package com.openclassrooms.jeudelogique.controler;

import com.openclassrooms.jeudelogique.model.SearchModel;
import com.openclassrooms.jeudelogique.view.Fenetre;

public class SearchChallengerControler {
	private SearchModel searchModel;
	private Fenetre fenetre;

	public SearchChallengerControler(SearchModel model, boolean developerMode) {
		this.searchModel = model;
		this.fenetre = new Fenetre(this, developerMode);
		addObserversToModel();
	}
	
	private void addObserversToModel() {
		searchModel.addObserver(fenetre);
	}
	
	public void setProposition(String proposition) {
		this.searchModel.setProposition(proposition);
	}
	
	public void setCombinaisonSecrete(String combinaisonSecrete) {
		this.searchModel.setCombinaisonSecreteModeChallenger(combinaisonSecrete);
	}
	
	public void setChoixFinDePartie(String choixFinDePartie) {
		this.searchModel.setChoixFinDePartie(choixFinDePartie);
	}
	
	public SearchModel getSearchModel() {
		return searchModel;
	}
	
	public void displayView() {
		this.fenetre.setVisible(true);
	}
	
}
