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
public class GestionFichier {
	protected String nomFichier;
	private Scanner scannerDeFichier;

	/**
	 * Demande juste le nom du fichier
	 * 
	 * @param nomFichier
	 */
	public GestionFichier(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	/**
	 * Ouvre le fichier qui port le nom "nomFichier"
	 * 
	 * @throws FileNotFoundException Si le fichier est introuvable
	 */
	public void ouvrirFichier() throws FileNotFoundException {
		FileInputStream file = new FileInputStream(nomFichier);
		scannerDeFichier = new Scanner(file);
		System.out.println("Fichier ouvert");

	}

	/**
	 * fermer le fichier portant le nom "nomFichier"
	 */
	public void fermerFichier() {
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
	public void lireFichier() {

	}

	/**
	 * Cette méthode est appelée si on rencontre les mots "//ID".
	 * 
	 * @return le l'id
	 * @throws Exception
	 */
	public String lectureID() throws Exception {
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
	public String lectureNom() throws Exception {
		String autorisation = "^[0-9a-zA-Z]{1,20}$";
		String nom;
		nom = lectureLigne();
		if (!nom.matches(autorisation)) {
			throw new Exception("Erreur : nom avec des caractere interdit");
		}

		return nom;
	}

	/**
	 * Cette méthode est appelée si on rencontre les mots "//MAP". Elle lit le parti
	 * du fichier contenant la map et vérifie plusieurs critères. La longueur, la
	 * largeur, les valeursSi la map répond pas à un seul de ces critères la lecture
	 * est abandonnée
	 * 
	 * @return retourne un tableau de boolean a deux dimension.
	 * @throws Exception Si la map de corespond pas au critère.
	 */
	public boolean[][] lectureMap() throws Exception {
		boolean[][] map = new boolean[Niveau.largeurMap][Niveau.longueurMap];
		String ligneString;

		for (int largeur = 0; largeur < Niveau.largeurMap; largeur++) {
			ligneString = this.lectureLigne();

			if (ligneString == null) {
				throw new Exception("Erreur : Ligne vide");
			}

			if (ligneString.length() != Niveau.longueurMap) {
				throw new Exception("Erreur : La ligne " + ligneString + " n'est pas a la bonne taille");
			}

			for (int longueur = 0; longueur < Niveau.longueurMap; longueur++) {
				if (ligneString.charAt(longueur) == '0' || ligneString.charAt(longueur) == '1') {
					map[largeur][longueur] = ligneString.charAt(longueur) == '1';
				} else {
					throw new Exception(
							"Erreur : le caractere " + ligneString.charAt(longueur) + " est interdit dans une map");
				}

			}

		}

		return map;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	public MeilleurScoreNiveau lectureRecord() throws Exception {
		MeilleurScoreNiveau record = null;
		int i = 3;
		try {
			record = new MeilleurScoreNiveau(this.lectureID());
			i--;
			record.setNomNiveau(this.lectureNom());
			i--;
			record.setMeilleurScrore(this.lectureScore());
			i--;
			record.setMeilleurTemps(this.lectureTemps());
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Erreur : Lecture record illisible");
			while (i > 0) {
				this.lectureLigne();
				i--;
			}
			record = null;
		}

		return record;
	}

	/**
	 * Cette méthode est appelée si on rencontre les mots "//SCORE".
	 * 
	 * @return retourne le score
	 * @throws Exception
	 * @throws NumberFormatException
	 */
	public int lectureScore() throws NumberFormatException, Exception {
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
	public int lectureTemps() throws NumberFormatException, Exception {
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
	public String lectureLigne() throws Exception {
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
	public void ecrireFichier() {

	}

	/**
	 * Vide le fichier partant le nom "nomFichier"
	 */
	public void vidageFichier() {
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
	public void ecrireId(String id) throws IOException {
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
	public void ecrireNom(String nom) throws IOException {
		System.out.println("Ecriture nom");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//NOM\n");
		lefichier.write(nom + "\n");
		lefichier.close();
	}

	public void ecrireMap(boolean[][] map) throws IOException {
		System.out.println("Ecriture map");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//MAP\n");
		for (int largeur = 0; largeur < Niveau.largeurMap; largeur++) {
			for (int longueur = 0; longueur < Niveau.longueurMap; longueur++) {
				if (map[largeur][longueur]) {
					lefichier.write("1");
				} else {
					lefichier.write("0");
				}
			}
			lefichier.write("\n");
		}
		lefichier.close();
	}

	public void ecrireRecord(MeilleurScoreNiveau record) throws Exception {
		System.out.println("Ecriture record");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//RECORD\n");
		lefichier.write(record.getId() + "\n");
		lefichier.write(record.getNomNiveau() + "\n");
		lefichier.write(record.getMeilleurScrore() + "\n");
		lefichier.write(record.getMeilleurTemps() + "\n");
		lefichier.close();
	}

	/**
	 * Remplie le ficher avec le score passer en parametre. Et ecrit une entête
	 * //SCORE
	 * 
	 * @param score
	 * @throws IOException
	 */
	public void ecrireScore(int score) throws IOException {
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
	public void ecrireTemp(int temps) throws IOException {
		System.out.println("Ecriture temps");
		FileWriter lefichier = new FileWriter(this.nomFichier, true);
		lefichier.write("//TEMPS\n");
		lefichier.write(String.valueOf(temps) + "\n");
		lefichier.close();

	}
}
