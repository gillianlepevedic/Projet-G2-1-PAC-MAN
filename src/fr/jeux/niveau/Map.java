package fr.jeux.niveau;

import java.util.Arrays;

/**
 * 
 * @author gillian
 *
 */
public class Map {
	public final static int longueurMap = 31;
	public final static int largeurMap = 25;
	public final static int mur = 0;
	public final static int couloir = 1;
	public final static int spawn = 2;
	public final static int superPacGomme = 3;
	public final static int pacGommeFruit = 4;
	public final static int verificationMap = 6;

	private int[][] map;

	/**
	 * Constructeur
	 */
	public Map() {
		this.map = null;
	}

	/**
	 * Permet de modifier la map. si la map n'est pas valide elle sera egal a null
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

	/**
	 * Methode qui permet de savoir si une map est valide ou non. La taille, la
	 * bordure, l'ilot central, la continuité du labyrinthe.
	 * 
	 * @return true si la map est valide. false sinon.
	 */
	private boolean mapEstValide() {
		if (this.map == null) {
			return false;
		}
		

		if (this.map.length != Map.largeurMap) {
			return false;
		}

		for (int i = 0; i < Map.largeurMap; i++) {
			if (this.map[i].length != Map.longueurMap) {
				return false;
			}
		}

		for (int i = 0; i < Map.largeurMap; i++) {
			if (this.map[i][0] != Map.mur || this.map[i][Map.longueurMap - 1] != Map.mur) {
				return false;
			}
		}

		for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
			if (this.map[0][longeur] != Map.mur || this.map[Map.largeurMap - 1][longeur] != Map.mur) {
				return false;

			}
		}

		if (!mapIlotCentral()) {
			return false;
		}

		if (!mapEstContinue()) {
			return false;
		}

		return true;
	}

	private boolean mapIlotCentral() {
		for (int i = (Map.longueurMap / 2) - 1; i <= (Map.longueurMap / 2) + 1; i++) {
			if (this.map[((Map.largeurMap) / 2)][i] != Map.spawn) {
				return false;
			}
		}

		for (int i = (Map.longueurMap / 2) - 2; i <= (Map.longueurMap / 2) + 2; i++) {
			if (i != 15) {
				if (this.map[((Map.largeurMap) / 2) - 1][i] != Map.mur
						|| this.map[((Map.largeurMap) / 2) + 1][i] != Map.mur) {
					return false;
				}
			} else if (this.map[((Map.largeurMap) / 2) - 1][i] != Map.spawn
					|| this.map[((Map.largeurMap) / 2) + 1][i] != Map.mur) {
				return false;
			}

		}

		if (this.map[10][15] != 1) {
			return false;
		}

		if (this.map[(Map.largeurMap) / 2][(Map.longueurMap / 2) - 2] != Map.mur
				|| this.map[(Map.largeurMap) / 2][(Map.longueurMap / 2) + 2] != Map.mur) {
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
				if (this.map[largeur][longeur] == Map.couloir) {
					return false;
				}
			}
		}

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
				if (this.map[largeur][longeur] == Map.verificationMap) {
					this.map[largeur][longeur] = 1;
				}
			}
		}

		return true;
	}

	private void successeur(Integer[] predecesseur) {
		this.map[predecesseur[0]][predecesseur[1]] = Map.verificationMap;

		if (this.map[predecesseur[0] + 1][predecesseur[1]] == Map.couloir) {
			Integer[] bas = new Integer[2];
			bas[0] = predecesseur[0] + 1;
			bas[1] = predecesseur[1];
			successeur(bas);
		}

		if (this.map[predecesseur[0] - 1][predecesseur[1]] == Map.couloir) {
			Integer[] haut = new Integer[2];
			haut[0] = predecesseur[0] - 1;
			haut[1] = predecesseur[1];
			successeur(haut);
		}

		if (this.map[predecesseur[0]][predecesseur[1] + 1] == Map.couloir) {
			Integer[] droite = new Integer[2];
			droite[0] = predecesseur[0];
			droite[1] = predecesseur[1] + 1;
			successeur(droite);
		}

		if (this.map[predecesseur[0]][predecesseur[1] - 1] == Map.couloir) {
			Integer[] gauche = new Integer[2];
			gauche[0] = predecesseur[0];
			gauche[1] = predecesseur[1] - 1;
			successeur(gauche);
		}

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
