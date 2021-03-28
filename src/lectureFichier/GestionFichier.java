package lectureFichier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class technique cette classe permet de simplifier la lecture de fichier de
 * Niveau ou de Joueur. Classe abstraite
 * 
 * @author gillian
 *
 */
public abstract class GestionFichier {
	protected String nomFichier;
	private Scanner scannerDeFichier;

	/**
	 * Demande juste le nom du fichier
	 * 
	 * @param nomFichier
	 */
	protected GestionFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	/**
	 * Ouvre le fichier qui port le nom "nomFichier"
	 * 
	 * @throws FileNotFoundException Si le fichier est introuvable
	 */
	protected void ouvrirFichier() throws FileNotFoundException {
		FileInputStream file = new FileInputStream(nomFichier);
		scannerDeFichier = new Scanner(file);
		System.out.println("Fichier ouvert");

	}

	/**
	 * fermer le fichier portant le nom "nomFichier"
	 */
	protected void fermerFichier() {
		try {
			scannerDeFichier.close();
		} catch (NullPointerException e) {
			System.out.println("Erreur : Scanner dejas ferme");
			System.out.println(e);
		}
	}

	/**
	 * Coming soon
	 */
	protected void lireFichier() {

	}

	protected String lectureID() throws Exception {
		String autorisation = "^[0-9]{10}$";
		String id;
		id = lectureLigne();

		if (id.length() != 10) {
			throw new Exception("Erreur : ID pas de la bonne taille");
		}
		if (!id.matches(autorisation)) {
			throw new Exception("Erreur : ID avec des caractere interdit");
		}

		return id;
	}

	protected String lectureNom() throws Exception {
		String autorisation = "^[0-9a-zA-Z]{1,20}$";
		String nom;
		nom = lectureLigne();
		if (!nom.matches(autorisation)) {
			throw new Exception("Erreur : nom avec des caractere interdit");
		}

		return nom;
	}

	/**
	 * Cette méthode est appelée si on rencontre les mots "//SCORE".
	 * 
	 * @return retourne le score lu par le fichier sinon retourne 0
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	protected int lectureScore() throws NumberFormatException, Exception {
		int score;
		score = Integer.parseInt(lectureLigne());
		return score;
	}

	/**
	 * Cette méthode est appelée si on rencontre les mots "//TEMPS".
	 * 
	 * @return retourne le temps lu par le fichier sinon retourne 0
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	protected int lectureTemps() throws NumberFormatException, Exception {
		int temps;
		temps = Integer.parseInt(lectureLigne());
		return temps;
	}

	/**
	 * Lit la ligne suivante dans le fichier partant le nom "nomFichier".
	 * 
	 * @return la ligne lue
	 * @throws Exception si il y a plus de ligne a lire dans le fichier
	 */
	protected String lectureLigne() throws Exception {
		String ligne = null;
		if (scannerDeFichier.hasNext()) {
			ligne = scannerDeFichier.nextLine();
		} else {
			throw new Exception("Erreur : Pas de ligne a lire");
		}

		return ligne;
	}

	/**
	 * Coming soon
	 */
	protected void ecrireFichier() {

	}

	/**
	 * Vide le fichier partant le nom "nomFichier"
	 */
	protected void vidageFichier() {
		try (FileWriter lefichier = new FileWriter(nomFichier);) {
			lefichier.write("");
		} catch (IOException e) {
			System.out.println("Erreur : Vidage impossible");
			System.out.println(e);
		}
	}
}
