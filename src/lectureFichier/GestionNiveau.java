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
		this.niveau = null;
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
			this.niveau = null;
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
		super.vidageFichier();
		try {
			super.ecrireId(this.niveau.getId());
			super.ecrireNom(this.niveau.getNom());
			ecrireMap(this.niveau.getMap());
			super.ecrireScore(this.niveau.getMeilleurScore());
			super.ecrireTemp(this.niveau.getMeilleurTempsEnSeconde());
			System.out.println("Fin ecriture");
		} catch (Exception e) {

		}
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

	@Override
	public String toString() {
		return this.niveau.toString();
	}
}