package lectureFichier;

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

	/**
	 * 
	 * @throws Exception
	 */
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
				this.setMap(gestion.lectureMap());
				break;
			case "":
				i--;
				break;
			default:
				throw new Exception("Erreur : entete partie obligatoire non reconu");
			}
		}

		System.out.println("Fin lecture partie obligatoire");

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

	/**
	 * Cette m�thode permet de sauvegarder le niveau pass� en param�tre dans le
	 * fichier d�clarer dans le constructeur. Le fichier est d'abord vid�. Puis
	 * remplis avec la structure //MAP //TEMPS //SCRORE
	 * 
	 * @param niveau il serat envoy� dans le fichier "nomfichier"
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
			System.out.println("Probleme ecriture fichier");
			System.out.println(e);
		}
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
	
	public void setMap(boolean[][] map) throws Exception {
		if (map[0].length == Niveau.longueurMap && map.length == Niveau.largeurMap) {
			this.map = map;
		}else {
			throw new Exception("Erreur : map pas au norme");
		}
		
	}


	public int getMeilleurScore() {
		return meilleurScore;
	}

	public void setMeilleurScore(int meilleurScore) throws Exception {
		if (meilleurScore > this.meilleurScore) {
			this.meilleurScore = meilleurScore;
		} else {
			throw new Exception("Erreur : meilleurTempsEnSeconde plus grand");
		}
	}

	public int getMeilleurTempsEnSeconde() {
		return meilleurTempsEnSeconde;
	}

	public void setMeilleurTempsEnSeconde(int meilleurTempsEnSeconde) throws Exception {
		if (meilleurTempsEnSeconde > this.meilleurTempsEnSeconde) {
			this.meilleurTempsEnSeconde = meilleurTempsEnSeconde;
		} else {
			throw new Exception("Erreur : meilleurTempsEnSeconde plus grand");
		}

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
