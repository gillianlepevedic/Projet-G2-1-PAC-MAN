package Joueur;

/**
 * 
 * @author gillian
 *
 */
public class MeilleurScoreNiveau {
	final private String id;
	private String nomNiveau;
	private int meilleurScrore;
	private int meilleurTemps;

	public MeilleurScoreNiveau(String id) {
		this.id = id;
		this.nomNiveau = "N/A";
		this.meilleurScrore = 0;
		this.meilleurTemps = 0;
	}

	public MeilleurScoreNiveau(String id, String nomNiveau, int meilleurScrore, int meilleurTemps) {
		this.id = id;
		this.nomNiveau = nomNiveau;
		this.meilleurScrore = meilleurScrore;
		this.meilleurTemps = meilleurTemps;
	}

	public void setNomNiveau(String nomNiveau) throws Exception {
		if (this.nomNiveau == null) {
			throw new Exception("Erreur : nom null");
		}

		this.nomNiveau = nomNiveau;
	}

	public void setMeilleurScrore(int meilleurScrore) throws Exception {
		if (meilleurScrore < 0) {
			throw new Exception("Erreur : meilleurScore negatif");
		}

		if (this.meilleurScrore > meilleurScrore) {
			throw new Exception("Erreur : meilleurScore plus petit");
		}

		this.meilleurScrore = meilleurScrore;
	}

	public void setMeilleurTemps(int meilleurTemps) throws Exception {
		if (meilleurTemps < 0) {
			throw new Exception("Erreur : MeilleurTemps negatif");
		}

		this.meilleurTemps = meilleurTemps;
	}

	public int getMeilleurTemps() {
		return meilleurTemps;
	}

	public String getId() {
		return id;
	}

	public String getNomNiveau() {
		return nomNiveau;
	}

	public int getMeilleurScrore() {
		return meilleurScrore;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + meilleurScrore;
		result = prime * result + meilleurTemps;
		result = prime * result + ((nomNiveau == null) ? 0 : nomNiveau.hashCode());
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
		MeilleurScoreNiveau other = (MeilleurScoreNiveau) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (meilleurScrore != other.meilleurScrore)
			return false;
		if (meilleurTemps != other.meilleurTemps)
			return false;
		if (nomNiveau == null) {
			if (other.nomNiveau != null)
				return false;
		} else if (!nomNiveau.equals(other.nomNiveau))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "\n		id=" + id + "\n		nomNiveau=" + nomNiveau + "\n		meilleurScrore=" + meilleurScrore
				+ "\n		meilleurTemps=" + meilleurTemps + "\n		";
	}
}
