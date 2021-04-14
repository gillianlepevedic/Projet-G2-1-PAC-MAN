package fr.jeux.fichier.lecture;

/**
 * 
 * @author gillian
 *
 */
public class Record {
	private final String id;
	private String nom;
	private int meilleurScrore;
	private int meilleurTemps;

	/**
	 * Constructeur pour lecture de fichier
	 * 
	 * @param id impossible a changer apres
	 */
	public Record(String id) {
		this.id = id;
		this.nom = "NA";
		this.meilleurScrore = 0;
		this.meilleurTemps = 0;
	}

	/**
	 * Constructeur complet
	 * 
	 * @param idJoueur
	 * @param nomJoueur
	 * @param meilleurScrore
	 * @param meilleurTemps
	 */
	public Record(String id, String nom, int meilleurScrore, int meilleurTemps) {
		this.id = id;
		this.nom = nom;
		this.meilleurScrore = meilleurScrore;
		this.meilleurTemps = meilleurTemps;
	}

	/**
	 * Methode pour changer le nom du joueur
	 * 
	 * @param nomJoueur
	 * @throws Exception si le nom est Null
	 */
	public void setNom(String nom) throws Exception {
		if (nom == null) {
			throw new Exception("Erreur : nom null");
		}

		this.nom = nom;
	}

	/**
	 * Pour changer le Meilleur score
	 * 
	 * @param meilleurScrore
	 * @throws Exception Si le score est negatif ou plus petit que l ancien
	 */
	public void setMeilleurScrore(int meilleurScrore) throws Exception {
		if (meilleurScrore < 0) {
			throw new Exception("Erreur : meilleurScore negatif");
		}

		if (this.meilleurScrore > meilleurScrore) {
			throw new Exception("Erreur : meilleurScore plus petit");
		}

		this.meilleurScrore = meilleurScrore;
	}

	/**
	 * Pour changer le temps
	 * 
	 * @param meilleurTemps
	 * @throws Exception si le Temps en negatif
	 */
	public void setMeilleurTemps(int meilleurTemps) throws Exception {
		if (meilleurTemps < 0) {
			throw new Exception("Erreur : MeilleurTemps negatif");
		}

		this.meilleurTemps = meilleurTemps;
	}

	/**
	 * @return the nomJoueur
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @return the idJoueur
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the meilleurScrore
	 */
	public int getMeilleurScrore() {
		return meilleurScrore;
	}

	/**
	 * @return the meilleurTemps
	 */
	public int getMeilleurTemps() {
		return meilleurTemps;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + meilleurScrore;
		result = prime * result + meilleurTemps;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		Record other = (Record) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (meilleurScrore != other.meilleurScrore)
			return false;
		if (meilleurTemps != other.meilleurTemps)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n		idJoueur=" + id + "\n		nomJoueur=" + nom + "\n		meilleurScrore=" + meilleurScrore
				+ "\n		meilleurTemps=" + meilleurTemps + "\n		";
	}
}
