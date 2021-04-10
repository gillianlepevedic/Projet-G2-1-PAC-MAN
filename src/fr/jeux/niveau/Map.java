package fr.jeux.niveau;

import java.util.Arrays;

/**
 * 
 * @author gillian
 *
 */
public class Map {
	private final static int longueurMap = 31;
	private final static int largeurMap = 25;
	private int[][] map;

	/**
	 * Constructeur
	 */
	public Map() {
		this.map = null;
	}

	/**
	 * Constructeur
	 * 
	 * @param map
	 */
	public Map(int[][] map) {
		this.map = map;
	}

	/**
	 * Permet de modifier la map.
	 * 
	 * @param mapInt
	 * @throws Exception Si la map n'est pas valide
	 */
	public void setMap(int[][] mapInt) throws Exception {
		this.map = mapInt;

		if (!mapEstValide()) {
			this.map = null;
			throw new Exception("Erreur : changement map impossible (nouvelle map pas valide)");
		}

	}

	private boolean mapEstValide() {

		for (int i = 0; i < Map.getLargeurmap(); i++) {
			if (this.map[i][0] != 0 || this.map[i][Map.getLongueurmap() - 1] != 0) {
				return false;
			}
		}

		for (int largeur = 0; largeur <= Map.getLargeurmap(); largeur += Map.getLargeurmap() - 1) {
			for (int longeur = 0; longeur < Map.getLongueurmap(); longeur++) {
				if (this.map[largeur][longeur] != 0) {
					return false;
				}
			}
		}

		for (int i = ((Map.getLongueurmap()) / 2) - 1; i <= ((Map.getLongueurmap()) / 2) + 1; i++) {
			if (this.map[((Map.getLargeurmap()) / 2)][i] != 2) {
				return false;
			}
		}

		for (int i = ((Map.getLongueurmap()) / 2) - 2; i <= ((Map.getLongueurmap()) / 2) + 2; i++) {
			if (i != 15) {
				if (this.map[((Map.getLargeurmap()) / 2) - 1][i] != 0
						|| this.map[((Map.getLargeurmap()) / 2) + 1][i] != 0) {
					return false;
				}
			} else if (this.map[((Map.getLargeurmap()) / 2) - 1][i] != 2
					|| this.map[((Map.getLargeurmap()) / 2) + 1][i] != 0) {
				return false;
			}

		}

		if (this.map[10][15] != 1) {
			return false;
		}

		if (this.map[(Map.getLargeurmap()) / 2][(Map.getLongueurmap() / 2) - 2] != 0
				|| this.map[(Map.getLargeurmap()) / 2][(Map.getLongueurmap() / 2) + 2] != 0) {
			return false;
		}

		if (!mapEstContinue()) {
			return false;
		}

		return true;
	}

	private boolean mapEstContinue() {
		Integer[] depart = new Integer[2];
		depart[0] = 10;
		depart[1] = 15;

		successeur(depart);

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
				if (this.map[largeur][longeur] == 1) {
					return false;
				}
			}
		}

		System.out.print(this);

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
				if (this.map[largeur][longeur] == 6) {
					this.map[largeur][longeur] = 1;
				}
			}
		}

		return true;
	}

	private void successeur(Integer[] predecesseur) {
		this.map[predecesseur[0]][predecesseur[1]] = 6;

		if (this.map[predecesseur[0] + 1][predecesseur[1]] == 1) {
			Integer[] bas = new Integer[2];
			bas[0] = predecesseur[0] + 1;
			bas[1] = predecesseur[1];
			successeur(bas);
		}

		if (this.map[predecesseur[0] - 1][predecesseur[1]] == 1) {
			Integer[] haut = new Integer[2];
			haut[0] = predecesseur[0] - 1;
			haut[1] = predecesseur[1];
			successeur(haut);
		}

		if (this.map[predecesseur[0]][predecesseur[1] + 1] == 1) {
			Integer[] droite = new Integer[2];
			droite[0] = predecesseur[0];
			droite[1] = predecesseur[1] + 1;
			successeur(droite);
		}

		if (this.map[predecesseur[0]][predecesseur[1] - 1] == 1) {
			Integer[] gauche = new Integer[2];
			gauche[0] = predecesseur[0];
			gauche[1] = predecesseur[1] - 1;
			successeur(gauche);
		}

	}

	public static int getLargeurmap() {
		return largeurMap;
	}

	public static int getLongueurmap() {
		return longueurMap;
	}

	public int[][] getMap() {
		return map;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(map);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Map other = (Map) obj;
		if (!Arrays.deepEquals(map, other.map))
			return false;
		return true;
	}

	@Override
	public String toString() {
		String string = "";
		for (int j = 0; j < Map.largeurMap; j++) {
			for (int i = 0; i < Map.longueurMap; i++) {
				string = string + String.valueOf(this.map[j][i]);
			}
			string = string + "\n		";
		}
		return string;
	}
}
