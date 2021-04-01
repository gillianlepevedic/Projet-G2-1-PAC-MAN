package Niveau;

/**
 * 
 * @author gillian
 *
 */
public class Niveau {
	private final String nomFichier;
	private final String id;
	private final String nom;
	private Map map;
	private int meilleurScore;
	private int meilleurTempsEnSeconde;

	public Niveau(String nomFichier, String id, String nom) {
		this.nomFichier = nomFichier;
		this.id = id;
		this.nom = nom;
		this.map = new Map();

		meilleurTempsEnSeconde = 0;
		meilleurScore = 0;
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

	public void setMap(Map map) throws Exception {
		this.map.setMap(map.getMap());
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

	public int getMeilleurScore() {
		return meilleurScore;
	}

	public int getMeilleurTempsEnSeconde() {
		return meilleurTempsEnSeconde;
	}

	public boolean[][] getMap() {
		return map.getMap();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
		result = prime * result + meilleurScore;
		result = prime * result + meilleurTempsEnSeconde;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((nomFichier == null) ? 0 : nomFichier.hashCode());
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
		Niveau other = (Niveau) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (map == null) {
			if (other.map != null)
				return false;
		} else if (!map.equals(other.map))
			return false;
		if (meilleurScore != other.meilleurScore)
			return false;
		if (meilleurTempsEnSeconde != other.meilleurTempsEnSeconde)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (nomFichier == null) {
			if (other.nomFichier != null)
				return false;
		} else if (!nomFichier.equals(other.nomFichier))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\nNiveau \n	nomFichier=" + nomFichier + "\n	id=" + id + "\n	nom=" + nom + "\n	meilleurScore="
				+ meilleurScore + "\n	meilleurTempsEnSeconde=" + meilleurTempsEnSeconde + "\n	map=	" + map;
	}

}
