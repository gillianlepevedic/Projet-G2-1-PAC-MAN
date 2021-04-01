package Joueur;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author gillian
 *
 */
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
		boolean trouver = false;
		if (record == null) {
			throw new Exception("Record null");
		}

		for (MeilleurScoreNiveau i : listeRecord) {
			if (i.getId().equals(record.getId())) {
				i.setMeilleurScrore(record.getMeilleurScrore());
				i.setMeilleurTemps(record.getMeilleurScrore());
				i.setNomNiveau(record.getNomNiveau());
				trouver = true;
			}
		}

		if (!trouver) {
			if (this.listeRecord.size() >= maxElement) {
				throw new Exception("Erreur : Liste plein");
			}
			this.listeRecord.add(record);
		}
	}
	
	public MeilleurScoreNiveau suprimerRecord(String id) throws Exception {
		MeilleurScoreNiveau recordSupp = null;
		for (MeilleurScoreNiveau i : listeRecord) {
			if (i.getId().equals(id)) {
				recordSupp = i;
			}
		}
		
		if (recordSupp == null) {
			throw new Exception("Erreur : record pas trouvé");
		}
		
		listeRecord.remove(recordSupp);
		
		if (listeRecord.contains(recordSupp)) {
			throw new Exception("Erreur : impossible supprimer record");
		}
		
		System.out.println("Record supprimer");
		
		return recordSupp;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((listeRecord == null) ? 0 : listeRecord.hashCode());
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
		Joueur other = (Joueur) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (listeRecord == null) {
			if (other.listeRecord != null)
				return false;
		} else if (!listeRecord.equals(other.listeRecord))
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
		return "\nJoueur \n	nomFichier=" + nomFichier + "\n	id=" + id + "\n	nom=" + nom + "\n	listeRecord="
				+ listeRecord + "\n";
	}

}
