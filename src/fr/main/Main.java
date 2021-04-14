package fr.main;

import fr.jeux.fichier.FichierCharger;

public class Main {
	private static FichierCharger jeux;

	public static void main(String[] args) {
		jeux = new FichierCharger();
		try {
			jeux.chargerNiveau("./fic/niveau2.pac");
			jeux.chargerJoueur("./fic/joueur2.pac");
			//jeux.ajouterNiveau("./fic/niveau2.pac");
			//jeux.chargerJoueur("./fic/joueur2.pac");
			
			
			//jeux.getJoueurConecter().ajouterRecord(new RecordJoueur("0000000154", "niveau2", 154748845,1544));

			System.out.println(jeux);
			//jeux.getJoueurConecter().suprimerRecord("0000000154");
			
			

			jeux.dechargerJoueur();
			jeux.dechargerNiveau("N0000000019");
			

		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println(jeux);
	}
	
}
