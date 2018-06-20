package com.openclassrooms.jeudelogique.model;

import java.util.ArrayList;

import com.openclassrooms.jeudelogique.observer.Observable;
import com.openclassrooms.jeudelogique.observer.Observer;

public class SearchModel implements Observable {
	private ArrayList<Observer> listObserver;

	// Attributs relatifs au mode challenger
	private String propositionJoueurModeChallenger;
	private String combinaisonSecreteModeChallenger;

	// Attributs communs
	private String choixFinDePartie;

	// Constructeur par defaut
	public SearchModel() {
		listObserver = new ArrayList<>();
	}

	// Methodes relatives au mode challenger
	public void setProposition(String proposition) {
		this.propositionJoueurModeChallenger = proposition;
		this.compare(this.combinaisonSecreteModeChallenger, this.propositionJoueurModeChallenger);
		this.notifyObserver();
	}

	public void setCombinaisonSecreteModeChallenger(String combinaisonSecrete) {
		this.combinaisonSecreteModeChallenger = combinaisonSecrete;
	}

	// Methodes communes
	public String compare(String proposition1, String proposition2) {
		char[] tab = new char[proposition1.length()];
		for (int i = 0; i < proposition1.length(); i++) {
			if (proposition1.charAt(i) == proposition2.charAt(i))
				tab[i] = '=';
			if (proposition1.charAt(i) < proposition2.charAt(i))
				tab[i] = '-';
			if (proposition1.charAt(i) > proposition2.charAt(i))
				tab[i] = '+';
		}
		return String.valueOf(tab);
	}

	public void setChoixFinDePartie(String choixFinDePartie) {
		this.choixFinDePartie = choixFinDePartie;
		if (this.choixFinDePartie.equals("Quitter"))
			this.exitApplicationObserver();
		else if (this.choixFinDePartie.equals("Revenir au menu"))
			this.accueilObserver();
		else if (this.choixFinDePartie.equals("Rejouer")) {
			this.restartObserver();
		}
	}

	// Methodes a redefinir
	@Override
	public void addObserver(Observer o) {
		this.listObserver.add(o);
	}

	@Override
	public void deleteObserver() {
		this.listObserver = new ArrayList<>();
	}

	@Override
	public void notifyObserver() {
		for (Observer obs : listObserver) {
			obs.update(this.propositionJoueurModeChallenger,
					this.compare(this.combinaisonSecreteModeChallenger, this.propositionJoueurModeChallenger));
		}
	}

	@Override
	public void restartObserver() {
		for (Observer obs : listObserver) {
			obs.restart();
		}
	}

	@Override
	public void accueilObserver() {
		for (Observer obs : listObserver) {
			obs.accueil();
		}
	}

	@Override
	public void exitApplicationObserver() {
		for (Observer obs : listObserver) {
			obs.exitApplication();
		}
	}

}
