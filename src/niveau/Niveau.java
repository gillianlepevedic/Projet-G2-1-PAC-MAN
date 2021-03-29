package niveau;

import lectureFichier.GestionFichier;

public class Niveau {
	final private static int longueurMap = 25;
	final private static int largeurMap = 25;
	GestionFichier gestion;
	private String id;
	private String nom;
	private boolean[][] map;
	private int meilleurScore;
	private int meilleurTempsEnSeconde;

	public Niveau(String nomfichier) {
		gestion = new GestionFichier(nomfichier);
		id = null;
		nom = null;
		map = null;
		meilleurTempsEnSeconde = 0;
		meilleurScore = 0;
	}

	public void lectureNiveau() throws Exception {
		gestion.ouvrirFichier();
		for (int i = 0; i < 3; i++) {
			switch (gestion.lectureLigne()) {

			case "//ID":
				this.id = gestion.lectureID();
				break;
			case "//NOM":
				this.nom = gestion.lectureNom();
				break;
			case "//MAP":
				this.map = gestion.lectureMap();
				break;
			case "":
				i--;
				break;
			default:
				throw new Exception("Erreur : entete non reconu");
			}
		}

		System.out.println("Fin lecture partie obligatoire");

		if (this.id != null && this.nom != null && this.map != null) {

			try {
				for (int i = 0; i < 2; i++) {
					switch (gestion.lectureLigne()) {

					case "//SCORE":
						this.meilleurScore = gestion.lectureScore();
						break;
					case "//TEMPS":
						this.meilleurTempsEnSeconde = gestion.lectureTemps();
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
			} finally {
				System.out.println("Fin lecture");
				gestion.fermerFichier();
				System.out.println("Fichier fermer");
			}

		}
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
		gestion.vidageFichier();
		try {
			gestion.ecrireId(this.id);
			gestion.ecrireNom(this.nom);
			gestion.ecrireMap(this.map);
			gestion.ecrireScore(this.meilleurScore);
			gestion.ecrireTemp(this.meilleurTempsEnSeconde);
			System.out.println("Fin ecriture");
		} catch (Exception e) {

		}
	}

	public int getMeilleurTempsEnSeconde() {
		return meilleurTempsEnSeconde;
	}

	public int getMeilleurScore() {
		return meilleurScore;
	}

	public static int getLongueurMap() {
		return longueurMap;
	}

	public static int getLargeurMap() {
		return largeurMap;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	public boolean[][] getMap() {
		return map;
	}

	@Override
	public String toString() {
		return "Niveau\nid=" + id + "\nnom=" + nom + "\nmap=\n" + toStringMap() + "\nmeilleurScore=" + meilleurScore
				+ "\nmeilleurTempsEnSeconde=" + meilleurTempsEnSeconde;
	}

	private String toStringMap() {
		String string = "";
		for (int j = 0; j < 25; j++) {
			for (int i = 0; i < 25; i++) {
				string = string + (this.map[j][i] ? 1 : 0);
			}
			string = string + "\n";
		}
		return string;
	}
}
