package niveau;

public class Niveau {
	final private static int longueurMap = 25;
	final private static int largeurMap = 25;
	final private String id;
	final private String nom;
	private boolean[][] map;
	private int meilleurTempsEnSeconde;
	private int meilleurScore;

	public Niveau(String id, String nom, boolean[][] map) {
		this.id = id;
		this.nom = nom;
		this.map = map;
	}

	public int getMeilleurTempsEnSeconde() {
		return meilleurTempsEnSeconde;
	}

	public void setMeilleurTempsEnSeconde(int meilleurTempsEnSeconde) {
		this.meilleurTempsEnSeconde = meilleurTempsEnSeconde;
	}

	public int getMeilleurScore() {
		return meilleurScore;
	}

	public void setMeilleurScore(int meilleurScore) {
		this.meilleurScore = meilleurScore;
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
		return "Niveau\nid=" + id + "\nnom=" + nom + "\nmap=\n" + toStringMap() + "\nmeilleurTempsEnSeconde=" + meilleurTempsEnSeconde + "\nmeilleurScore=" + meilleurScore ;
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
