package niveau;

import java.util.Arrays;

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
		return "Niveau [id=" + id + ", nom=" + nom + ", map=" + Arrays.toString(map) + ", meilleurTempsEnSeconde="
				+ meilleurTempsEnSeconde + ", meilleurScore=" + meilleurScore + "]";
	}
}
