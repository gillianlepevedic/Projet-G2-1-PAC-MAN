package fr.jeux.niveau;

/**
 * 
 * @author gillian
 *
 */
public class RecordNiveau {
	private final String idJoueur;
	private String nomJoueur;
	private int meilleurScrore;
	private int meilleurTemps;

	/**
	 * Constructeur pour lecture de fichier
	 * 
	 * @param id impossible a changer apres
	 */
	public RecordNiveau(String idJoueur) {
		this.idJoueur = idJoueur;
		this.nomJoueur = "N/A";
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
	public RecordNiveau(String idJoueur, String nomJoueur, int meilleurScrore, int meilleurTemps) {
		this.idJoueur = idJoueur;
		this.nomJoueur = nomJoueur;
		this.meilleurScrore = meilleurScrore;
		this.meilleurTemps = meilleurTemps;
	}

	/**
	 * Methode pour changer le nom du joueur
	 * 
	 * @param nomJoueur
	 * @throws Exception si le nom est Null
	 */
	public void setNomJoueur(String nomJoueur) throws Exception {
		if (this.nomJoueur == null) {
			throw new Exception("Erreur : nom null");
		}

		this.nomJoueur = nomJoueur;
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
	public String getNomJoueur() {
		return nomJoueur;
	}

	/**
	 * @return the idJoueur
	 */
	public String getIdJoueur() {
		return idJoueur;
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
		result = prime * result + ((idJoueur == null) ? 0 : idJoueur.hashCode());
		result = prime * result + meilleurScrore;
		result = prime * result + meilleurTemps;
		result = prime * result + ((nomJoueur == null) ? 0 : nomJoueur.hashCode());
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
		RecordNiveau other = (RecordNiveau) obj;
		if (idJoueur == null) {
			if (other.idJoueur != null)
				return false;
		} else if (!idJoueur.equals(other.idJoueur))
			return false;
		if (meilleurScrore != other.meilleurScrore)
			return false;
		if (meilleurTemps != other.meilleurTemps)
			return false;
		if (nomJoueur == null) {
			if (other.nomJoueur != null)
				return false;
		} else if (!nomJoueur.equals(other.nomJoueur))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n		idJoueur=" + idJoueur + "\n		nomJoueur=" + nomJoueur + "\n		meilleurScrore="
				+ meilleurScrore + "\n		meilleurTemps=" + meilleurTemps + "\n		";
	}
}
