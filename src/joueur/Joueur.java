package joueur;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	final static int maxElement =25;
	final private String id;
	final private String nom;
	private List<MeilleurScoreNiveau> listeRecord;

	public Joueur(String id, String nom) {
		this.id = id;
		this.nom = nom;
		listeRecord = new ArrayList<>(maxElement);
	}
	
	public void ajouterRecord(MeilleurScoreNiveau record) throws Exception {
		if (listeRecord.size() < maxElement) {
			listeRecord.add(record);
		}else {
			throw new Exception("Erreur : Liste plein");
		}
	}

	public static int getMaxelement() {
		return maxElement;
	}

	public List<MeilleurScoreNiveau> getListeRecord() {
		return listeRecord;
	}

	public String getId() {
		return id;
	}

	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return "Joueur [id=" + id + ", nom=" + nom + ", listeRecord=" + listeRecord + "]";
	}

}
