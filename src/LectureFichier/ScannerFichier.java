package LectureFichier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * Class technique
 * cette classe permet de simplifier la lecture de fichier. 
 * 
 * @author gillian
 *
 */
public class ScannerFichier {
	private Scanner scannerDeFichier;
	/**
	 * Constructeur
	 * Demande le nom du ficher en parametre pour l'ouvrir
	 * @param nomfic
	 * @throws FileNotFoundException si le fichier n'est pas trouvable.
	 */
	public ScannerFichier(String nomfic) throws FileNotFoundException {
		FileInputStream file = new FileInputStream(nomfic);
		scannerDeFichier = new Scanner(file);
		System.out.println("Fichier ouvert");
	}
	/**
	 * Permets de renvoyer une ligne de fichier à chacun appelle de la méthode
	 * @return renvoi un String de la ligne lu
	 * @throws Exception si il n'y a plus de ligne a lire
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
	 * Ferme le scanner
	 */
	void close() {
		try {
			scannerDeFichier.close();
		} catch (NullPointerException e) {
			System.out.println("Erreur : Fermer");
			System.out.println(e);
		}
	}
	/**
	 * permet de verifier si le scanner esr ouvert ou fermé
	 * @return
	 */
	public Scanner getScannerDeFichier() {
		return scannerDeFichier;
	}
}
