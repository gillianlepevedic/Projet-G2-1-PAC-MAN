package fr.pacman.zonedejeux.decor;

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
	private Record record;

	public Niveau(String nomFichier, String id, String nom) {
		this.nomFichier = nomFichier;
		this.id = id;
		this.nom = nom;

		this.map = new Map();
		this.record = null;

	}

	public void setMap(Map map) throws Exception {
		this.map.setMap(map.getMap());
	}

	public void setRecordNiveau(Record record) throws Exception {

		if (record == null) {
			throw new Exception("Erreur : record Niveau null");
		}

		if (this.record == null) {
			this.record = record;
		} else {

			if (this.record.getMeilleurScrore() > record.getMeilleurScrore()) {
				throw new Exception("Erreur : meilleur score moins bon");
			}

			if (this.record.getId() == record.getId()) {
				this.record.setNom(record.getNom());
				this.record.setMeilleurScrore(record.getMeilleurScrore());
				this.record.setMeilleurTemps(record.getMeilleurTemps());
			} else {
				this.record = record;
			}
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

	public int[][] getMap() {
		return map.getMap();
	}

	/**
	 * @return the recordNiveau
	 */
	public Record getRecordNiveau() {
		return record;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((map == null) ? 0 : map.hashCode());
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
		return "Niveau \n	nomFichier=" + nomFichier + "\n	id=" + id + "\n	nom=" + nom + "\n	map=	" + map
				+ "\n	recordNiveau=" + record + "\n";
	}

}
