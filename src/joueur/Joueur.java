package joueur;

import java.util.ArrayList;
import java.util.List;

import lectureFichier.GestionFichier;

public class Joueur {
	final static int maxElement = 25;
	private GestionFichier gestion;
	private String id;
	private String nom;
	private List<MeilleurScoreNiveau> listeRecord;

	public Joueur(String nomfichier) {
		gestion = new GestionFichier(nomfichier);
		this.id = null;
		this.nom = null;
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

	public void lectureJoueur() throws Exception {

		gestion.ouvrirFichier();
		for (int i = 0; i < 2; i++) {
			switch (gestion.lectureLigne()) {

			case "//ID":
				this.id = gestion.lectureID();
				break;
			case "//NOM":
				this.nom = gestion.lectureNom();
				break;
			case "":
				i--;
				break;
			default:
				throw new Exception("Erreur : entete non reconu");
			}

		}
		System.out.println("Fin lecture partie obligatoire");

		if (this.id != null && this.nom != null) {
			
			try {

				for (int i = 0; i < Joueur.getMaxelement(); i++) {
					switch (gestion.lectureLigne()) {
					
					case "//RECORD":
						this.ajouterRecord(gestion.lectureRecord());
						break;
					case "":
						i--;
						break;
					default:
						throw new Exception("Erreur : entete non reconu");
					}

				}

			} catch (Exception e) {
				System.out.print(e);
				System.out.println(" dans la partie optionel");
			} finally {
				System.out.println("Fin lecture");
				gestion.fermerFichier();
				System.out.println("Fichier fermer");
			}
		}
	}

	public void sauvegarderJoueur() {
		System.out.println("Debut ecriture");
		gestion.vidageFichier();
		try {
			gestion.ecrireId(this.id);
			gestion.ecrireNom(this.nom);
			for (int i = 0; i < this.listeRecord.size(); i++) {
				gestion.ecrireRecord(this.listeRecord.get(i));
			}

			System.out.println("Fin ecriture");
		} catch (Exception e) {

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
		return "Joueur\nid=" + id + "\nnom=" + nom + "\nlisteRecord=" + listeRecord;
	}

}
