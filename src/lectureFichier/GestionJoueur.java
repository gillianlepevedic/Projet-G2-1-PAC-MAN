package lectureFichier;

import java.io.FileNotFoundException;
import java.io.FileWriter;

import joueur.Joueur;
import joueur.MeilleurScoreNiveau;

public class GestionJoueur extends GestionFichier {
	private Joueur joueur;

	public GestionJoueur(String nomFichier) {
		super(nomFichier);
		this.joueur = null;
	}

	public Joueur getJoueur() {
		return joueur;
	}
	
	public Joueur LectureJoueur() {
		String id = null;
		String nom = null;
		

		try {
			super.ouvrirFichier();
			for (int i = 0; i < 2; i++) {
				switch (super.lectureLigne()) {

				case "//ID":
					id = lectureID();
					break;
				case "//NOM":
					nom = lectureNom();
					break;
				case "":
					i--;
					break;
				default:
					throw new Exception("Erreur : entete non reconu");
				}

			}
			System.out.println("Fin lecture partie obligatoire");

		} catch (FileNotFoundException e) {
			System.out.println("Erreur : impossible d'ouvrir le fichier");
			System.out.println(e);
			this.joueur=null;
		} catch (Exception e) {
			System.out.println("Erreur : Partie obligatoire illisible");
			System.out.println(e);
			this.joueur=null;
		}

		if (id != null && nom != null) {
			this.joueur = new Joueur(id, nom);

			try {

				for (int i = 0; i < Joueur.getMaxelement(); i++) {
					switch (super.lectureLigne()) {

					case "//RECORD":
						this.joueur.ajouterRecord(lectureRecord());
						break;
					case "":
						i--;
						break;
					default:
						throw new Exception("Erreur : entete non reconu");
					}

				}

			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println("Fin lecture partie optionnel");
			System.out.println("Fin lecture");
			super.fermerFichier();
			System.out.println("Fichier fermer");
		}
		return this.joueur;
	}
	
	public MeilleurScoreNiveau lectureRecord() throws Exception {
		MeilleurScoreNiveau record = null;
		int i=3;
		try {
			record = new MeilleurScoreNiveau(super.lectureID());
			i--;
			record.setNomNiveau(super.lectureNom());
			i--;
			record.setMeilleurScrore(super.lectureScore());
			i--;
			record.setMeilleurTemps(super.lectureTemps());
		}catch(Exception e) {
			System.out.println("Erreur : Lecture record illisible");
			while (i>0) {
				super.lectureLigne();
				i--;
			}
			record = null;
		}
		
		return record;
	}
	
	public void sauvegarderJoueur() {
		System.out.println("Debut ecriture");
		super.vidageFichier();
		try {
			super.ecrireId(this.joueur.getId());
			super.ecrireNom(this.joueur.getNom());
			for (int i=0 ; i < this.joueur.getListeRecord().size();i++) {
				ecrireRecord(this.joueur.getListeRecord().get(i));
			}
			
			System.out.println("Fin ecriture");
		} catch (Exception e) {

		}
	}
	
	private void ecrireRecord(MeilleurScoreNiveau record) throws Exception {
		System.out.println("Ecriture record");
		FileWriter lefichier = new FileWriter(super.nomFichier, true);
		lefichier.write("//RECORD\n");
		lefichier.write(record.getId()+"\n");
		lefichier.write(record.getNomNiveau()+"\n");
		lefichier.write(record.getMeilleurScrore()+"\n");
		lefichier.write(record.getMeilleurTemps()+"\n");
		lefichier.close();
	}

	@Override
	public String toString() {
		return this.joueur.toString();
	}
	

}
