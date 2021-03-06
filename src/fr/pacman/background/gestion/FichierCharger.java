package fr.pacman.background.gestion;

import java.util.ArrayList;
import java.util.List;

import fr.pacman.zonedejeux.decor.Joueur;
import fr.pacman.zonedejeux.decor.Niveau;
import fr.pacman.zonedejeux.decor.Record;

/**
 * 
 * @author gillian
 *
 */
public class FichierCharger {
	final static int maxElement = 25;
	private List<Niveau> niveauCharger;
	private Joueur joueurConnecter;

	/**
	 * Constructeur
	 */
	public FichierCharger() {
		niveauCharger = new ArrayList<>(FichierCharger.maxElement);
		this.joueurConnecter = null;
	}

	/**
	 * Permet de charger un joueur grace a son nom de fichier
	 * 
	 * @param nomfic
	 * @return retourne le Joueur si tous va bien
	 * @throws Exception Si le fichier est ilisible
	 */
	public Joueur chargerJoueur(String nomfic) throws Exception {
		Joueur joueur = LectureFichierXML.lireJoueur(nomfic);
		if (joueur == null) {
			throw new Exception("Erreur joueur est null");
		}
		if (this.joueurConnecter != null) {
			throw new Exception("Erreur joueur dejas charger");
		}

		this.joueurConnecter = joueur;
		return joueur;
	}

	/**
	 * Pour ajouter un niveau grace a sont nom de fichier.
	 * 
	 * @param nomfichier
	 * @return retourne le niveau si tous vas bien
	 * @throws Exception Si le fichier n'est pas lisible. Si la liste de niveau est
	 *                   complete
	 */
	public Niveau chargerNiveau(String nomfichier) throws Exception {
		Niveau niveau = LectureFichierXML.lireNiveau(nomfichier);

		if (niveau == null) {
			throw new Exception("Erreur : Niveau null");
		}

		if (this.niveauCharger.size() > maxElement) {
			throw new Exception("Erreur : Liste plein");
		}

		this.niveauCharger.add(niveau);

		return niveau;
	}

	/**
	 * Sauvegade dans son fichier le Joueur connecter
	 * 
	 * @return retourne le joueur si tous va bien
	 * @throws Exception Si aucun joueur n'est connecter. Si impossible d'ecrire le
	 *                   fichier. Si sauvegarde c'est mal passe
	 */
	public Joueur sauvegarderJoueur() throws Exception {
		if (this.joueurConnecter == null) {
			throw new Exception("Pas de joueur connecte");
		}

		LectureFichierXML.ecrireJoueur(this.joueurConnecter);

		if (!this.joueurConnecter.equals(LectureFichierXML.lireJoueur(this.joueurConnecter.getNomFichier()))) {
			throw new Exception("Erreur : sauvegarde different de l'objet");
		}

		System.out.println("Sauvegarde reussi");

		return this.joueurConnecter;
	}

	/**
	 * Permet de sauvegarder un niveau dans son fichier grace a son id.
	 * 
	 * @param id composer de 10 caracteres
	 * @return retourn le niveau sauvegard
	 * @throws Exception si le niveau n'est pas toruver graces a l'id ou si la
	 *                   sauvegarde c'ect mal passe
	 */
	public Niveau sauvegardeNiveau(String id) throws Exception {
		boolean sauvegarder = false;
		Niveau niveauSauvegarder = null;

		for (Niveau niveau : niveauCharger) {
			if (id.equals(niveau.getId())) {
				niveauSauvegarder = niveau;
				LectureFichierXML.ecrireNiveau(niveauSauvegarder);
				sauvegarder = true;
			}
		}

		if (!sauvegarder) {
			throw new Exception("Erreur : niveau pas trouver");
		}

		if (!niveauSauvegarder.equals(LectureFichierXML.lireNiveau(niveauSauvegarder.getNomFichier()))) {
			throw new Exception("Erreur : sauvegarde different de l'objet");
		}

		System.out.println("Sauvegarde reussi");

		return niveauSauvegarder;
	}

	/**
	 * Suprime le joueur connecter
	 * 
	 * @return retourne le joueur supprmier si tous va bien
	 * @throws Exception Si la sauvegarde c'est mal passe. Si impossible de siprimer
	 *                   le Joueur
	 */
	public Joueur dechargerJoueur() throws Exception {
		Joueur joueursupp = sauvegarderJoueur();

		this.joueurConnecter = null;

		if (this.joueurConnecter != null) {
			throw new Exception("Erreur : impossible de decherger le joueur");
		}

		System.out.println("Dechargement reussi");

		return joueursupp;
	}

	/**
	 * Sauvegarde le fichier et le suprime de la liste apres
	 * 
	 * @param id
	 * @return retourne le fichier suprimer si tous va bien
	 * @throws Exception si la sauvegarde est impossible. Si la supression est
	 *                   impossible.
	 */
	public Niveau dechargerNiveau(String id) throws Exception {
		Niveau niveausup = sauvegardeNiveau(id);

		if (!this.niveauCharger.contains(niveausup)) {
			throw new Exception("Erreur : niveau pas trouve");
		}

		this.niveauCharger.remove(niveausup);

		if (this.niveauCharger.contains(niveausup)) {
			throw new Exception("Erreur : impossible de suprimer le fichier");
		}

		System.out.println("Supression reussi");
		return niveausup;
	}

	/**
	 * Ajout d'un record au joueur connecter
	 * 
	 * @param record
	 * @throws Exception Si le record est null. Si il est impossible d'ajouter
	 */
	public void ajouterRecordJoueur(Record record) throws Exception {
		if (record == null) {
			throw new Exception("Erreur : record null");
		}
		
		sauvegarderJoueur();
		
		this.joueurConnecter.ajouterRecord(record);
		
		sauvegarderJoueur();
	}

	/**
	 * Ajout d'un record au Niveau qui coresppend a l'ID. Si la map n'est pas
	 * charger
	 * 
	 * @param record
	 * @throws Exception Si la map n'est pas charger
	 */
	public void ajouterRecordNiveau(String idNiveau, Record record) throws Exception {
		int index;
		if (record == null) {
			throw new Exception("Erreur : record null");
		}

		Niveau niveauAjout = sauvegardeNiveau(idNiveau);

		if (!this.niveauCharger.contains(niveauAjout)) {
			throw new Exception("Erreur : niveau pas trouve");
		}

		index = this.niveauCharger.indexOf(niveauAjout);
		this.niveauCharger.get(index).setRecordNiveau(record);
		sauvegardeNiveau(idNiveau);

		this.joueurConnecter.ajouterRecord(record);
	}

	/**
	 * Suprimer un record du joueur connecter grace a l'id du record
	 * 
	 * @param idRecordsupp
	 * @return retourne le record supprimer
	 * @throws Exception si id est null. Si impossible de le supprimer
	 */
	public Record supprimerRecordJoueur(String idRecordsupp) throws Exception {
		Record recordsupp = null;
		if (idRecordsupp == null) {
			throw new Exception("Erreur : id null");
		}
		
		sauvegarderJoueur();
		recordsupp = this.joueurConnecter.suprimerRecord(idRecordsupp);
		sauvegarderJoueur();
		return recordsupp;
	}

	public List<Niveau> getNiveauCharger() {
		return niveauCharger;
	}

	public Joueur getJoueurConecter() {
		return joueurConnecter;
	}

	@Override
	public String toString() {
		return "NiveauCharger\n" + niveauCharger + "\n" + "joueurConecter" + joueurConnecter + "\n";
	}
}
