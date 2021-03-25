package LectureFichier;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GestionFichierMap {
	private static int LongueurMap=25;
	private static int LargeurMap=25;
	private String nomFichier;
	
	public GestionFichierMap(String nomfic) {
		nomFichier = nomfic;
	}
	
	public boolean[][] lireMap() throws Exception {
		System.out.println("Debut lecture");
		boolean[][] map = new boolean[LargeurMap][LongueurMap];
		Scanner scannerDeFichier = null;
		FileInputStream file = null;

		try {
			file = new FileInputStream(nomFichier);
			scannerDeFichier = new Scanner(file);

			int j = 0;
			while (scannerDeFichier.hasNext() && j<LargeurMap) {
				map[j] = lireLigneMap(scannerDeFichier.nextLine());
				j++;
			}
			System.out.println("Fin de la lecture");

		} catch (java.util.NoSuchElementException e) {
			System.out.println("Erreur : Lecture imposible");
			System.out.println(e);
			map = null;
		} catch (java.io.FileNotFoundException e) {
			System.out.println("Erreur : Fichier introuvable");
			System.out.println(e);
			map = null;
		} catch (java.lang.Exception e) {
			System.out.println("Erreur");
			System.out.println(e);
			map = null;
		} finally {
			try {
				scannerDeFichier.close();
			} catch (java.lang.NullPointerException e) {
				System.out.println(e);
			}
		}
		return map;
	}

	private boolean[] lireLigneMap(String ligneString) throws Exception {
		boolean[] ligneBool = new boolean[LongueurMap];

		if (ligneString.length() == LongueurMap) {
			for (int i = 0; i < ligneString.length(); i++) {
				ligneBool[i] = ligneString.charAt(i) == '1';
			}
		} else {
			throw new Exception("Erreur : La ligne" + ligneString + "n'est pas aux norme");
		}

		return ligneBool;
	}

	public void ecrireMap(boolean[][] map) {
		System.out.println("Debut ecriture");
		viderFichier();
		for (int i = 0; i < LargeurMap; i++) {
			ecrireLigneMap(map[i]);
		}
		System.out.println("Fin ecriture");
	}

	private void viderFichier() {
		try (FileWriter lefichier = new FileWriter(nomFichier);) {
			lefichier.write("");
		} catch (IOException e) {
			System.out.println("Erreur : Vidage impossible");
		}
	}

	private void ecrireLigneMap(boolean[] LigneMapBool) {
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			for (int i = 0; i < LongueurMap; i++) {
				if (LigneMapBool[i]) {
					lefichier.write("1");
				} else {
					lefichier.write("0");
				}

			}

			lefichier.write("\n");
		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Impossible");
		}
	}
}
