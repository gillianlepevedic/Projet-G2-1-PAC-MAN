package lectureFichier;

import java.util.ArrayList;
import java.util.List;

public class Joueur {
	final static int maxElement = 25;
	private final String nomFichier;
	private final String id;
	private final String nom;
	private List<MeilleurScoreNiveau> listeRecord;

	public Joueur(String nomFichier, String id, String nom) {
		this.nomFichier = nomFichier;
		this.id = id;
		this.nom = nom;
		listeRecord = new ArrayList<>(maxElement);
	}

	public void ajouterRecord(MeilleurScoreNiveau record) throws Exception {
		if (record != null) {
			if (this.listeRecord.size() < maxElement) {
				this.listeRecord.add(record);
			} else {
				throw new Exception("Erreur : Liste plein");
			}
		} else {
			System.out.println("Record null");
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

	public List<MeilleurScoreNiveau> getListeRecord() {
		return listeRecord;
	}

	@Override
	public String toString() {
		return "\nJoueur \n	nomFichier=" + nomFichier + "\n	id=" + id + "\n	nom=" + nom + "\n	listeRecord="
				+ listeRecord + "\n";
	}

}
