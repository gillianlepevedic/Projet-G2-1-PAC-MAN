package LectureFichier;

import java.io.FileWriter;
import java.io.IOException;

import Niveau.Niveau;

public class SauvegardeNiveau {
	private String nomFichier;

	public SauvegardeNiveau(String nomFichier, Niveau niveau) {
		this.nomFichier = nomFichier;
		System.out.println("Debut ecriture");
		viderFichier();
		ecrireMap(niveau.getMap());
		ecrireScore(niveau.getMeilleurTempsEnSeconde(),niveau.getMeilleurScore());
		System.out.println("Fin ecriture");
	}
	
	public void EcritureDuNiveau() {

	}

	private void viderFichier() {
		try (FileWriter lefichier = new FileWriter(nomFichier);) {
			lefichier.write("");
		} catch (IOException e) {
			System.out.println("Erreur : Vidage impossible");
		}
	}
	

	private void ecrireMap(boolean[][] map) {
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			lefichier.write("//MAP\n");
			for (int largeur = 0; largeur< Niveau.getLargeurMap(); largeur++) {
				for (int longueur = 0; longueur < Niveau.getLongueurMap(); longueur++) {
					if (map[largeur][longueur]) {
						lefichier.write("1");
					} else {
						lefichier.write("0");
					}
				}
				lefichier.write("\n");
			}
			
		}catch (IOException e) {
			System.out.println("Erreur : Ecriture Map Impossible");
		}
	}
	
	private void ecrireScore(int temps, int score) {
		
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
						lefichier.write("//SCORE\n");
						lefichier.write(String.valueOf(temps)+ "\n");
						lefichier.write(String.valueOf(score));
			
		}catch (IOException e) {
			System.out.println("Erreur : Ecriture Score Imposible");
		}
	}
}
