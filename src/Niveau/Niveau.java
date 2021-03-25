package Niveau;
import java.util.Arrays;

public class Niveau {
	private static int longueurMap=25;
	private static int largeurMap=25;
	private boolean[][] map;
	private int meilleurTempsEnSeconde;
	private int meilleurScore;
	
	
	public Niveau() {
		this.map = null;
		this.meilleurTempsEnSeconde = 0;
		this.meilleurScore = 0;
	}
	
	public static int getLongueurMap() {
		return longueurMap;
	}
	public static int getLargeurMap() {
		return largeurMap;
	}
	public boolean[][] getMap() {
		return map;
	}
	public void setMap(boolean[][] map) {
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
	@Override
	public String toString() {
		return "Niveau [map=" + Arrays.toString(map) + ", meilleurTempsEnSeconde=" + meilleurTempsEnSeconde
				+ ", meilleurScore=" + meilleurScore + "]";
	}
}
