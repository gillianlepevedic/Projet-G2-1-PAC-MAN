package fr.jeux.fichier.lecture;

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
	public final static int spawn = 1;
	public final static int couloir = 2;
	public final static int superPacGomme = 3;
	public final static int pacGommeFruit = 4;
	public final static int verificationMap = -1;

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
		int[][] save = new int[Map.largeurMap][Map.longueurMap];

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
				save[largeur][longeur] = mapInt[largeur][longeur];
			}
		}

		if (!mapEstValide(mapInt)) {
			throw new Exception("Erreur : changement map impossible (nouvelle map pas valide)");
		}

		this.map = save;
	}

	/**
	 * Methode qui permet de savoir si une map est valide ou non. La taille, la
	 * bordure, l'ilot central, la continuité du labyrinthe.
	 * 
	 * @return true si la map est valide. false sinon.
	 */
	private boolean mapEstValide(int[][] mapInt) {
		if (mapInt == null) {
			return false;
		}

		if (mapInt.length != Map.largeurMap) {
			return false;
		}

		for (int i = 0; i < Map.largeurMap; i++) {
			if (mapInt[i].length != Map.longueurMap) {
				return false;
			}
		}

		for (int i = 0; i < Map.largeurMap; i++) {
			if (mapInt[i][0] != Map.mur || mapInt[i][Map.longueurMap - 1] != Map.mur) {
				return false;
			}
		}

		for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
			if (mapInt[0][longeur] != Map.mur || mapInt[Map.largeurMap - 1][longeur] != Map.mur) {
				return false;

			}
		}

		if (!mapIlotCentral(mapInt)) {
			return false;
		}

		if (!mapEstContinue(mapInt)) {
			return false;
		}

		return true;
	}

	private boolean mapIlotCentral(int[][] mapInt) {
		for (int i = (Map.longueurMap / 2) - 1; i <= (Map.longueurMap / 2) + 1; i++) {
			if (mapInt[((Map.largeurMap) / 2)][i] != Map.spawn) {
				return false;
			}
		}

		for (int i = (Map.longueurMap / 2) - 2; i <= (Map.longueurMap / 2) + 2; i++) {
			if (i != 15) {
				if (mapInt[((Map.largeurMap) / 2) - 1][i] != Map.mur
						|| mapInt[((Map.largeurMap) / 2) + 1][i] != Map.mur) {
					return false;
				}
			} else if (mapInt[((Map.largeurMap) / 2) - 1][i] != Map.spawn
					|| mapInt[((Map.largeurMap) / 2) + 1][i] != Map.mur) {
				return false;
			}

		}

		if (mapInt[Map.largeurMap / 2 - 2][Map.longueurMap / 2] != Map.couloir) {
			return false;
		}

		if (mapInt[(Map.largeurMap) / 2][(Map.longueurMap / 2) - 2] != Map.mur
				|| mapInt[(Map.largeurMap) / 2][(Map.longueurMap / 2) + 2] != Map.mur) {
			return false;
		}

		return true;
	}

	private boolean mapEstContinue(int[][] mapInt) {
		Integer[] depart = new Integer[2];
		depart[0] = 10;
		depart[1] = 15;

		successeur(depart, mapInt);

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			for (int longeur = 0; longeur < Map.longueurMap; longeur++) {
				if (mapInt[largeur][longeur] >= Map.couloir) {
					return false;
				}
			}
		}
		return true;
		
	}

	private void successeur(Integer[] predecesseur, int[][] mapInt) {
		mapInt[predecesseur[0]][predecesseur[1]] = Map.verificationMap;

		if (mapInt[predecesseur[0] + 1][predecesseur[1]] >= Map.couloir) {
			Integer[] bas = new Integer[2];
			bas[0] = predecesseur[0] + 1;
			bas[1] = predecesseur[1];
			successeur(bas, mapInt);
		}

		if (mapInt[predecesseur[0] - 1][predecesseur[1]] >= Map.couloir) {
			Integer[] haut = new Integer[2];
			haut[0] = predecesseur[0] - 1;
			haut[1] = predecesseur[1];
			successeur(haut, mapInt);
		}

		if (mapInt[predecesseur[0]][predecesseur[1] + 1] >= Map.couloir) {
			Integer[] droite = new Integer[2];
			droite[0] = predecesseur[0];
			droite[1] = predecesseur[1] + 1;
			successeur(droite, mapInt);
		}

		if (mapInt[predecesseur[0]][predecesseur[1] - 1] >= Map.couloir) {
			Integer[] gauche = new Integer[2];
			gauche[0] = predecesseur[0];
			gauche[1] = predecesseur[1] - 1;
			successeur(gauche, mapInt);
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
