package fr.main;

import fr.jeux.joueur.MeilleurScoreNiveau;
import fr.jeux.lectureFichier.FichierCharger;

public class Main {
	private static FichierCharger jeux;

	public static void main(String[] args) {
		jeux = new FichierCharger();
		try {
			jeux.ajouterNiveau("./fic/niveau1.pac");
			jeux.chargerJoueur("./fic/joueur1.pac");
			//jeux.ajouterNiveau("./fic/niveau2.pac");
			//jeux.chargerJoueur("./fic/joueur2.pac");
			
			
			jeux.getJoueurConecter().ajouterRecord(new MeilleurScoreNiveau("0000000154", "niveau2", 154748845,1544));

			System.out.println(jeux);
			jeux.getJoueurConecter().suprimerRecord("0000000154");
			
			

			//System.out.println(jeux);

			//jeux.dechargerNiveau("0000000019");
			//jeux.dechargerNiveau("0000000018");
			//jeux.dechargerJoueur();

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
}
