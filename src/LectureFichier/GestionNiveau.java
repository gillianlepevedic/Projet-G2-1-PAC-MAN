package LectureFichier;

import java.io.FileWriter;
import java.io.IOException;

import Niveau.Niveau;

/**
 * Class technique
 * la classe gestion de niveau permet d'ouvrir et fermer des fichiers de niveau.
 * Le fichier peut être dans le désordre (les parties //MAP //TEMPS //SCORE) dans l'ordre que l'on veut.
 * Dans cette classe on peut ouvrir un niveau grâce au nom du fichier et/ou l'enregistre grâce à son nom de fichier et le niveau à sauvegarder.
 * @author gillian
 *
 */
public class GestionNiveau {
	private String nomFichier;
	private Niveau niveau;
	private ScannerFichier scannerDeFichier;
	/**
	 * Constructeur 
	 * 
	 * @param nomFichier
	 */
	public GestionNiveau(String nomFichier) {
		this.nomFichier = nomFichier;
	}

	public Niveau ouvrirNiveau() {
		scannerDeFichier = new ScannerFichier(nomFichier);
		niveau = new Niveau();
		try {
			if (scannerDeFichier.getScannerDeFichier() != null) {
				for (int i = 0; i < 3; i++) {
					switch (scannerDeFichier.lectureLigne()) {

					case "//MAP":
						niveau.setMap(lectureMap());
						break;
					case "//TEMPS":
						niveau.setMeilleurTempsEnSeconde(lectureTemps());
						break;
					case "//SCORE":
						niveau.setMeilleurScore(lectureScore());
						break;
					case "":
						i--;
						break;
					default:
						System.out.println("Erreur");
					}
					
				}
				scannerDeFichier.close();
				System.out.println("Fin lecture niveau");
			}else {
				throw new Exception("Erreur : scanner fermer");
			}

		} catch (Exception e) {
			System.out.println(e);
			niveau = null;
		}

		return niveau;
	}

	private boolean[][] lectureMap() {
		boolean[][] map = new boolean[Niveau.getLargeurMap()][Niveau.getLongueurMap()];
		String ligneString;
		try {

			for (int largeur = 0; largeur < Niveau.getLargeurMap(); largeur++) {
				ligneString = scannerDeFichier.lectureLigne();

				if (ligneString.length() != Niveau.getLongueurMap()) {
					throw new Exception("Erreur : La ligne " + ligneString + " n'est pas a la bonne taille");
				}

				for (int longueur = 0; longueur < Niveau.getLongueurMap(); longueur++) {
					map[largeur][longueur] = ligneString.charAt(longueur) == '1';
				}

			}
		} catch (Exception e) {
			System.out.println("Erreur : contenue pas au norme");
			System.out.println(e);
			map = null;
		}
		return map;
	}

	private int lectureTemps() {
		int temps;
		try {
			temps = Integer.parseInt(scannerDeFichier.lectureLigne());
		} catch (Exception e) {
			System.out.println("Erreur : lecture Score impossible");
			System.out.println(e);
			temps = 0;
		}
		return temps;
	}

	private int lectureScore() {
		int score;
		try {
			score = Integer.parseInt(scannerDeFichier.lectureLigne());

		} catch (Exception e) {
			System.out.println("Erreur : lecture Score impossible");
			System.out.println(e);
			score = 0;
		}
		return score;
	}

	public void sauvegarderNiveau(Niveau niveau) {
		System.out.println("Debut ecriture");
		viderFichier();
		ecrireMap(niveau.getMap());
		ecrireTemp(niveau.getMeilleurTempsEnSeconde());
		ecrireScore(niveau.getMeilleurScore());
		System.out.println("Fin ecriture");
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
			for (int largeur = 0; largeur < Niveau.getLargeurMap(); largeur++) {
				for (int longueur = 0; longueur < Niveau.getLongueurMap(); longueur++) {
					if (map[largeur][longueur]) {
						lefichier.write("1");
					} else {
						lefichier.write("0");
					}
				}
				lefichier.write("\n");
			}

		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Map Impossible");
		}
	}

	private void ecrireTemp(int temps) {
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			lefichier.write("//TEMPS\n");
			lefichier.write(String.valueOf(temps) + "\n");

		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Temps Imposible");
		}

	}

	private void ecrireScore(int score) {

		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			lefichier.write("//SCORE\n");
			lefichier.write(String.valueOf(score));

		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Score Imposible");
		}
	}
}