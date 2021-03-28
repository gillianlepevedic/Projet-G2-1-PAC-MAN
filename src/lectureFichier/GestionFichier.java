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
/**
 * Cette méthode est appelée si on rencontre les mots "//ID".
 * @return le l'id 
 * @throws Exception
 */
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
/**
 * 
 * @return
 * @throws Exception
 */
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
	 * @return retourne le score
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
	 * @return retourne le temps lu par le fichier
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
	
	/**
	 * Remplie le ficher avec l'id passer en parametre. Et ecrit une entête //ID
	 * 
	 * @param id qui sera ecrit dans le fichier
	 * @throws IOException
	 */
	protected void ecrireId(String id) throws IOException {
		System.out.println("Ecriture id");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//ID\n");
		lefichier.write(id + "\n");
		lefichier.close();
	}
	
	/**
	 * Remplie le ficher avec l'nom passer en parametre. Et ecrit une entête //NOM
	 * 
	 * @param nom
	 * @throws IOException
	 */
	protected void ecrireNom(String nom) throws IOException {
		System.out.println("Ecriture nom");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//NOM\n");
		lefichier.write(nom + "\n");
		lefichier.close();
	}
	
	/**
	 * Remplie le ficher avec le score passer en parametre. Et ecrit une entête
	 * //SCORE
	 * 
	 * @param score
	 * @throws IOException
	 */
	protected void ecrireScore(int score) throws IOException {
		System.out.println("Ecriture score");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//SCORE\n");
		lefichier.write(String.valueOf(score) + "\n");
		lefichier.close();
	}
	
	/**
	 * Remplie le ficher avec le temps passer en parametre. Et ecrit une entête
	 * //TEMPS
	 * 
	 * @param temps
	 * @throws IOException
	 */
	protected void ecrireTemp(int temps) throws IOException {
		System.out.println("Ecriture temps");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//TEMPS\n");
		lefichier.write(String.valueOf(temps) + "\n");
		lefichier.close();

	}
}
