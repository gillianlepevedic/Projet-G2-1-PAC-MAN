package lectureFichier;

public class Niveau {
	final static int longueurMap = 31;
	final static int largeurMap = 25;
	private final String nomFichier;
	private final String id;
	private final String nom;
	final boolean[][] map;
	private int meilleurScore;
	private int meilleurTempsEnSeconde;

	public Niveau(String nomFichier, String id, String nom, boolean[][] map) {
			this.nomFichier = nomFichier;
			this.id = id;
			this.nom = nom;
			this.map = map;
	
			meilleurTempsEnSeconde = 0;
			meilleurScore = 0;
	}

	public boolean mapValide(boolean[][] map) {
		boolean valide = true;

		for (int i = 0; i < Niveau.largeurMap; i++) {
			if (!map[i][0] || !map[i][Niveau.longueurMap - 1]) {
				valide = false;
			}
		}

		for (int largeur = 0; largeur <= Niveau.largeurMap; largeur += Niveau.largeurMap - 1) {
			for (int longeur = 0; longeur < Niveau.longueurMap; longeur++) {
				if (!map[largeur][longeur]) {
					valide = false;
				}
			}
		}

		for (int i = ((Niveau.longueurMap) / 2) - 1; i <= ((Niveau.longueurMap) / 2) + 1; i++) {
			if (map[((Niveau.largeurMap) / 2)][i]) {
				valide = false;
			}
		}

		for (int i = ((Niveau.longueurMap) / 2) - 2; i <= ((Niveau.longueurMap) / 2) + 2; i++) {
			if (!map[((Niveau.largeurMap) / 2) - 1][i]) {
				valide = false;
			}
			if (!map[((Niveau.largeurMap) / 2) + 1][i]) {
				valide = false;
			}
		}

		if (!map[(Niveau.largeurMap) / 2][(Niveau.longueurMap / 2) - 2]
				|| !map[(Niveau.largeurMap) / 2][(Niveau.longueurMap / 2) + 2]) {
			valide = false;
		}

		return valide;
	}

	public void setMeilleurScore(int meilleurScore) throws Exception {
		if (meilleurScore >= this.meilleurScore) {
			this.meilleurScore = meilleurScore;
		} else {

			throw new Exception("Erreur : meilleurTempsEnSeconde plus grand");
		}
	}

	public void setMeilleurTempsEnSeconde(int meilleurTempsEnSeconde) throws Exception {
		if (meilleurTempsEnSeconde >= this.meilleurTempsEnSeconde) {
			this.meilleurTempsEnSeconde = meilleurTempsEnSeconde;
		} else {
			throw new Exception("Erreur : meilleurTempsEnSeconde plus grand");
		}

	}

	public String getNomFichier() {
		return nomFichier;
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

	public int getMeilleurScore() {
		return meilleurScore;
	}

	public int getMeilleurTempsEnSeconde() {
		return meilleurTempsEnSeconde;
	}

	@Override
	public String toString() {
		return "\nNiveau \n	nomFichier=" + nomFichier + "\n	id=" + id + "\n	nom=" + nom + "\n	meilleurScore="
				+ meilleurScore + "\n	meilleurTempsEnSeconde=" + meilleurTempsEnSeconde + "\n	map=	"
				+ toStringMap();
	}

	private String toStringMap() {
		String string = "";
		for (int j = 0; j < Niveau.largeurMap; j++) {
			for (int i = 0; i < Niveau.longueurMap; i++) {
				string = string + (this.map[j][i] ? 1 : 0);
			}
			string = string + "\n		";
		}
		return string;
	}

}
