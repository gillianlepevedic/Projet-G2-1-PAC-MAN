package lectureFichier;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import niveau.Niveau;

/**
 * Class technique La classe gestion de niveau permet de lire et/ou d'enregistre
 * le fichier grâce à son nom de fichier. Elle verifie que le fichier corespond
 * au norme.
 * 
 * @author gillian
 *
 */
public class GestionNiveau extends GestionFichier {
	private Niveau niveau;

	/**
	 * Constructeur
	 * 
	 * @param nomFichier
	 */
	public GestionNiveau(String nomFichier) {
		super(nomFichier);
	}

	public Niveau getNiveau() {
		return niveau;
	}

	/**
	 * Cette méthode appelle la bonne methode en fonction de l'entete lu. Si un
	 * partie obligatoire est ilisible la lecture est abandonée. Si une partie
	 * optionnel est ilisible elle sera remplacer pas un zerp.
	 * 
	 * @return return le Niveau lu dans le fichier qui porte le nom "nomFichier"
	 */
	public Niveau LectureNiveau() {
		String id = null;
		String nom = null;
		boolean[][] map = null;

		try {
			super.ouvrirFichier();
			for (int i = 0; i < 3; i++) {
				switch (super.lectureLigne()) {

				case "//ID":
					id = super.lectureID();
					break;
				case "//NOM":
					nom = super.lectureNom();
					break;
				case "//MAP":
					map = lectureMap();
					break;
				case "":
					i--;
					break;
				default:
					throw new Exception("Erreur : entete non reconu");
				}

			}
			System.out.println("Fin lecture partie obligatoire");

		} catch (FileNotFoundException e) {
			System.out.println("Erreur : impossible d'ouvrir le fichier");
			System.out.println(e);
		} catch (Exception e) {
			System.out.println("Erreur : Partie obligatoire illisible");
			System.out.println(e);
			return null;
		}

		if (id != null && nom != null && map != null) {
			this.niveau = new Niveau(id, nom, map);

			try {

				for (int i = 0; i < 2; i++) {
					switch (super.lectureLigne()) {

					case "//SCORE":
						this.niveau.setMeilleurScore(super.lectureScore());
						break;
					case "//TEMPS":
						this.niveau.setMeilleurTempsEnSeconde(super.lectureTemps());
						break;
					case "":
						i--;
						break;
					default:
						throw new Exception("Erreur : entete non reconu");
					}

				}
				System.out.println("Fin lecture partie optionnel");

			} catch (Exception e) {
				System.out.println("Erreur : Partie optionnel illisible");
				System.out.println(e);
			}
			System.out.println("Fin lecture");
			super.fermerFichier();
			System.out.println("Fichier fermer");
		}
		return this.niveau;
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
			ligneString = super.lectureLigne();

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
	 * Cette méthode permet de sauvegarder le niveau passé en paramètre dans le
	 * fichier déclarer dans le constructeur. Le fichier est d'abord vidé. Puis
	 * remplis avec la structure //MAP //TEMPS //SCRORE
	 * 
	 * @param niveau il serat envoyé dans le fichier "nomfichier"
	 */
	public void sauvegarderNiveau() {
		System.out.println("Debut ecriture");
		viderFichier();
		try {
			ecrireId(this.niveau.getId());
			ecrireNom(this.niveau.getNom());
			ecrireMap(this.niveau.getMap());
			ecrireScore(this.niveau.getMeilleurScore());
			ecrireTemp(this.niveau.getMeilleurTempsEnSeconde());
			System.out.println("Fin ecriture");
		} catch (Exception e) {

		}
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
	 * Remplie le ficher avec l'id passer en parametre. Et ecrit une entête //ID
	 * 
	 * @param id qui sera ecrit dans le fichier
	 * @throws IOException
	 */
	private void ecrireId(String id) throws IOException {
		System.out.println("Ecriture id");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
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
	private void ecrireNom(String nom) throws IOException {
		System.out.println("Ecriture nom");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
		lefichier.write("//NOM\n");
		lefichier.write(nom + "\n");
		lefichier.close();
	}

	/**
	 * Remplie le ficher avec la map sous forme de tableau. Avec un entête //MAP
	 * 
	 * @param map
	 * @throws IOException
	 */
	private void ecrireMap(boolean[][] map) throws IOException {
		System.out.println("Ecriture map");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
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
		lefichier.close();
	}

	/**
	 * Remplie le ficher avec le score passer en parametre. Et ecrit une entête
	 * //SCORE
	 * 
	 * @param score
	 * @throws IOException
	 */
	private void ecrireScore(int score) throws IOException {
		System.out.println("Ecriture score");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
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
	private void ecrireTemp(int temps) throws IOException {
		System.out.println("Ecriture temps");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
		lefichier.write("//TEMPS\n");
		lefichier.write(String.valueOf(temps) + "\n");
		lefichier.close();

	}
}