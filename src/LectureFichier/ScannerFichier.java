package LectureFichier;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScannerFichier {
	private Scanner scannerDeFichier;

	public ScannerFichier(String nomfic) throws FileNotFoundException {
		FileInputStream file = new FileInputStream(nomfic);
		scannerDeFichier = new Scanner(file);
		System.out.println("Fichier ouvert");
	}

	public String lectureLigne() throws Exception {
		String ligne = null;
		if (scannerDeFichier.hasNext()) {
			ligne = scannerDeFichier.nextLine();
		} else {
			throw new Exception("Erreur : Pas de ligne a lire");
		}

		return ligne;
	}

	void close() {
		try {
			scannerDeFichier.close();
		} catch (NullPointerException e) {
			System.out.println("Erreur : Fermer");
			System.out.println(e);
		}
	}

	public Scanner getScannerDeFichier() {
		return scannerDeFichier;
	}
}
