package LectureFichier;

import java.io.FileInputStream;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ScannerFichier {
	private Scanner scannerDeFichier;
	
	public  ScannerFichier(String nomfic) {
		try {
			FileInputStream file = new FileInputStream(nomfic);
			scannerDeFichier = new Scanner(file);
			System.out.println("Fichier ouvert");
			
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Erreur : Fichier introuvable");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Erreur : Lecture imposible");
			System.out.println(e);
			scannerDeFichier =null;
		}
	}
	
	public String lectureLigne() throws Exception {
		String ligne =null;
		try {
			if (scannerDeFichier.hasNext()) {
					ligne = scannerDeFichier.nextLine();
			}else {
				throw new Exception("Erreur : Pas de ligne a lire");
			}
		}catch(NoSuchElementException e) {
			System.out.println("Erreur : Imposible de lire la ligne");
			System.out.println(e);
			ligne = null;
		}catch(IllegalStateException e) {
			System.out.println("Erreur : Scanner fermer");
			System.out.println(e);
			ligne = null;
		}catch(NullPointerException e) {
			System.out.println("Erreur : Scanner null");
			System.out.println(e);
			ligne = null;
		}catch(Exception e) {
			System.out.println("Erreur : Pas de ligne a lire");
			System.out.println(e);
			ligne = null;
		}
			
		
		return ligne;
	}
	
	void close() {
		try {
			scannerDeFichier.close();
		}catch(NullPointerException e) {
			System.out.println("Erreur : Scanner null");
			System.out.println(e);
		}
	}

	public Scanner getScannerDeFichier() {
		return scannerDeFichier;
	}
		
}
