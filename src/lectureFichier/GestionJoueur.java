package lectureFichier;

import java.io.FileNotFoundException;

import joueur.Joueur;
import joueur.MeilleurScoreNiveau;

public class GestionJoueur extends GestionFichier {
	private Joueur joueur;

	protected GestionJoueur(String nomFichier) {
		super(nomFichier);
	}

	public Joueur getJoueur() {
		return joueur;
	}
	
	public Joueur LectureNiveau() {
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
		} catch (Exception e) {
			System.out.println("Erreur : Partie obligatoire illisible");
			System.out.println(e);
			return null;
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
				System.out.println("Fin lecture partie optionnel");

			} catch (Exception e) {
				System.out.println("Erreur : Partie optionnel illisible");
				System.out.println(e);
			}
			System.out.println("Fin lecture");
			super.fermerFichier();
			System.out.println("Fichier fermer");
		}
		return this.joueur;
	}
	
	public MeilleurScoreNiveau lectureRecord() {
		MeilleurScoreNiveau record = null;
		try {
			record = new MeilleurScoreNiveau(super.lectureID());
			record.setNomNiveau(super.lectureNom());
			record.setMeilleurScrore(super.lectureScore());
			record.setMeilleurTemps(super.lectureTemps());
		}catch(Exception e) {
			System.out.println("Erreur : Lecture record illisible");
		}
		
		return record;
	}
	

}
