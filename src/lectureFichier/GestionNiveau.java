package LectureFichier;

import java.io.FileWriter;
import java.io.IOException;
import Niveau.Niveau;

/**
 * Class technique
 * La classe gestion de niveau permet d'ouvrir et fermer des
 * fichiers de niveau. Le fichier peut être dans le désordre (les parties //MAP
 * //TEMPS //SCORE) dans l'ordre que l'on veut. Dans cette classe on peut ouvrir
 * un niveau grâce au nom du fichier et/ou l'enregistre grâce à son nom de
 * fichier et le niveau à sauvegarder.
 * 
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

	/**
	 * Cette méthode permet de vérifier si le fichier ouvert correspond au critère
	 * du jeu. Les entêtes, la taille de la map, la cohérence des valeurs, etc. Il
	 * faut au minimum un map complet pour que la fonction renvoi un "niveau" non
	 * null. Le score et le temps ne sont pas obligatoires dans le fichier. Si le
	 * Score et le temps ne sont pas dans le fichier ma valeur 0 serat affecter aux
	 * variables.
	 * 
	 * @return return le niveau lu dans le fichier qui porte le nom "nomFichier"
	 *         declarer dans le constructeur.
	 */
	public Niveau ouvrirNiveau() {
		niveau = new Niveau();

		try {
			scannerDeFichier = new ScannerFichier(nomFichier);
			scannerDeFichier.getScannerDeFichier();
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
					throw new Exception("Erreur : entete non reconu");
				}

			}
			System.out.println("Fin lecture niveau");

		} catch (Exception e) {
			System.out.println(e);
			if (niveau.getMap() == null) {
				niveau = null;
			}
		}

		scannerDeFichier.close();
		return niveau;
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
	private boolean[][] lectureMap() throws Exception {
		boolean[][] map = new boolean[Niveau.getLargeurMap()][Niveau.getLongueurMap()];
		String ligneString;

		for (int largeur = 0; largeur < Niveau.getLargeurMap(); largeur++) {
			ligneString = scannerDeFichier.lectureLigne();

			if (ligneString == null) {
				throw new Exception("Erreur : Ligne vide");
			}

			if (ligneString.length() != Niveau.getLongueurMap()) {
				throw new Exception("Erreur : La ligne " + ligneString + " n'est pas a la bonne taille");
			}

			for (int longueur = 0; longueur < Niveau.getLongueurMap(); longueur++) {
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
	 * Cette méthode est appelée si on rencontre les mots "//TEMPS".
	 * 
	 * @return retourne le temps lu par le fichier sinon retourne 0
	 */
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

	/**
	 * Cette méthode est appelée si on rencontre les mots "//SCORE".
	 * 
	 * @return retourne le score lu par le fichier sinon retourne 0
	 */
	private int lectureScore() {
		int score;
		try {
			score = Integer.parseInt(scannerDeFichier.lectureLigne());

		} catch (NullPointerException e) {
			System.out.println("Erreur : pas de Score");
			System.out.println(e);
			score = 0;
		} catch (Exception e) {
			System.out.println("Erreur : lecture Score impossible");
			System.out.println(e);
			score = 0;
		}
		return score;
	}

	/**
	 * Cette méthode permet de sauvegarder le niveau passé en paramètre dans le
	 * fichier déclarer dans le constructeur. Le fichier est d'abord vidé. Puis
	 * remplis avec la structure //MAP //TEMPS //SCRORE
	 * 
	 * @param niveau il serat envoyé dans le fichier "nomfichier"
	 */
	public void sauvegarderNiveau(Niveau niveau) {
		System.out.println("Debut ecriture");
		viderFichier();
		ecrireMap(niveau.getMap());
		ecrireTemp(niveau.getMeilleurTempsEnSeconde());
		ecrireScore(niveau.getMeilleurScore());
		System.out.println("Fin ecriture");
	}

	/**
	 * vide le fichier portant le nom "nomFichier"
	 */
	private void viderFichier() {
		try (FileWriter lefichier = new FileWriter(nomFichier);) {
			lefichier.write("");
		} catch (IOException e) {
			System.out.println("Erreur : Vidage impossible");
		}
	}

	/**
	 * Remplie le ficher avec la map sous forme de tableau. Avec un entête //MAP
	 * 
	 * @param map
	 */
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

	/**
	 * Remplie le ficher avec la map sous forme de tableau. Avec un entête //TEMPS
	 * 
	 * @param temps
	 */
	private void ecrireTemp(int temps) {
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			lefichier.write("//TEMPS\n");
			lefichier.write(String.valueOf(temps) + "\n");

		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Temps Imposible");
		}

	}

	/**
	 * Remplie le ficher avec la map sous forme de tableau. Avec un entête //SCORE
	 * 
	 * @param score
	 */
	private void ecrireScore(int score) {
		try (FileWriter lefichier = new FileWriter(nomFichier, true);) {
			lefichier.write("//SCORE\n");
			lefichier.write(String.valueOf(score));

		} catch (IOException e) {
			System.out.println("Erreur : Ecriture Score Imposible");
		}
	}
}