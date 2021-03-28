package joueur;

public class MeilleurScoreNiveau {
	final private String id;
	private String nomNiveau;
	private int meilleurScrore;
	private int meilleurTemps;
	
	
	public MeilleurScoreNiveau(String id) {
		this.id = id;
		this.meilleurScrore = 0;
		this.meilleurTemps = 0;
	}
	
	public MeilleurScoreNiveau(String id, int meilleurScrore, int meilleurTemps) {
		this.id = id;
		this.meilleurScrore = meilleurScrore;
		this.meilleurTemps = meilleurTemps;
	}

	public int getMeilleurScrore() {
		return meilleurScrore;
	}

	public void setMeilleurScrore(int meilleurScrore) {
		this.meilleurScrore = meilleurScrore;
	}

	public int getMeilleurTemps() {
		return meilleurTemps;
	}

	public void setMeilleurTemps(int meilleurTemps) {
		this.meilleurTemps = meilleurTemps;
	}

	public String getId() {
		return id;
	}

	public String getNomNiveau() {
		return nomNiveau;
	}

	public void setNomNiveau(String nomNiveau) {
		this.nomNiveau = nomNiveau;
	}

	@Override
	public String toString() {
		return "	id=" + id + "\n	nomNiveau=" + nomNiveau + "\n	meilleurScrore=" + meilleurScrore
				+ "\n	meilleurTemps=" + meilleurTemps + "\n";
	}
}
