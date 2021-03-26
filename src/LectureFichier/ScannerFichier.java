package LectureFichier;

import java.io.FileInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class LecteurDeLigne {
	private String nomFichier;
	private Scanner scannerDeFichier;
	private FileInputStream fichier;
	
	public LecteurDeLigne(String nomfic) {
		nomFichier = nomfic;
	}
	
	void ouvrirFichier() {
		try {
			fichier = new FileInputStream(nomFichier);
			scannerDeFichier = new Scanner(fichier);
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Erreur : Fichier introuvable");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Erreur : Lecture imposible");
			System.out.println(e);
		}
		System.out.println("Fichier ouvert");
	}
	
	public String lectureLigne() throws Exception {
		String ligne =null;
		if (scannerDeFichier.hasNext()) {
			try {
				ligne = scannerDeFichier.nextLine();
			}catch(NoSuchElementException e) {
				System.out.println("Erreur : Imposible de lire la ligne");
				System.out.println(e);
				ligne = null;
			}catch(IllegalStateException e) {
				System.out.println("Erreur : Scanner fermer");
				System.out.println(e);
				ligne = null;
			}
		}else {
			throw new Exception("Erreur : Pas de ligne a lire");
		}
		
		return ligne;
	}
	
	void close() {
		scannerDeFichier.close();
	}
}
