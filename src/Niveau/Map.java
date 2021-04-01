package Niveau;

import java.util.Arrays;

/**
 * 
 * @author gillian
 *
 */
public class Map {
	private final static int longueurMap = 31;
	private final static int largeurMap = 25;
	private boolean[][] map;

	public Map() {
		this.map = null;
	}

	public Map(boolean[][] map) {
		this.map = map;
	}

	public void setMap(boolean[][] mapBool) throws Exception {
		if (mapValide(mapBool)) {
			this.map = mapBool;
		} else {
			throw new Exception("Erreur : changement map impossible (nouvelle map pas valide)");
		}

	}

	private boolean mapValide(boolean[][] map) {
		for (int i = 0; i < Map.getLargeurmap(); i++) {
			if (!map[i][0] || !map[i][Map.getLongueurmap() - 1]) {
				return false;
			}
		}

		for (int largeur = 0; largeur <= Map.getLargeurmap(); largeur += Map.getLargeurmap() - 1) {
			for (int longeur = 0; longeur < Map.getLongueurmap(); longeur++) {
				if (!map[largeur][longeur]) {
					return false;
				}
			}
		}

		for (int i = ((Map.getLongueurmap()) / 2) - 1; i <= ((Map.getLongueurmap()) / 2) + 1; i++) {
			if (map[((Map.getLargeurmap()) / 2)][i]) {
				return false;
			}
		}

		for (int i = ((Map.getLongueurmap()) / 2) - 2; i <= ((Map.getLongueurmap()) / 2) + 2; i++) {
			if (!map[((Map.getLargeurmap()) / 2) - 1][i]) {
				return false;
			}
			if (!map[((Map.getLargeurmap()) / 2) + 1][i]) {
				return false;
			}
		}

		if (!map[(Map.getLargeurmap()) / 2][(Map.getLongueurmap() / 2) - 2]
				|| !map[(Map.getLargeurmap()) / 2][(Map.getLongueurmap() / 2) + 2]) {
			return false;
		}

		return true;
	}

	public static int getLargeurmap() {
		return largeurMap;
	}

	public static int getLongueurmap() {
		return longueurMap;
	}

	public boolean[][] getMap() {
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
				string = string + (this.map[j][i] ? 1 : 0);
			}
			string = string + "\n		";
		}
		return string;
	}
}
