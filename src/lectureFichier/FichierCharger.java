package lectureFichier;

import java.util.ArrayList;
import java.util.List;

import Joueur.Joueur;
import Niveau.Niveau;

/**
 * 
 * @author gillian
 *
 */
public class FichierCharger {
	final static int maxElement = 25;
	private List<Niveau> niveauCharger;
	private Joueur joueurConecter;

	public FichierCharger() {
		niveauCharger = new ArrayList<>(FichierCharger.maxElement);
		this.joueurConecter = null;
	}

	public Niveau ajouterNiveau(String nomfichier) throws Exception {
		Niveau niveau = GestionFichierXML.lireNiveau(nomfichier);

		if (niveau == null) {
			throw new Exception("Erreur : Niveau null");
		}

		if (this.niveauCharger.size() > maxElement) {
			throw new Exception("Erreur : Liste plein");
		}

		this.niveauCharger.add(niveau);

		return niveau;
	}

	public Niveau sauvegardeNiveau(String id) throws Exception {
		boolean sauvegarder = false;
		Niveau niveauSauvegarder = null;

		for (Niveau niveau : niveauCharger) {
			if (id.equals(niveau.getId())) {
				niveauSauvegarder = niveau;
				GestionFichierXML.ecrireNiveau(niveauSauvegarder);
				sauvegarder = true;
			}
		}

		if (!sauvegarder) {
			throw new Exception("Erreur : niveau pas trouver");
		}

		if (!niveauSauvegarder.equals(GestionFichierXML.lireNiveau(niveauSauvegarder.getNomFichier()))) {
			throw new Exception("Erreur : sauvegarde different de l'objet");
		}

		System.out.println("Sauvegarde reussi");

		return niveauSauvegarder;
	}

	public Niveau dechargerNiveau(String id) throws Exception {
		Niveau niveausup = sauvegardeNiveau(id);

		if (!this.niveauCharger.contains(niveausup)) {
			throw new Exception("Erreur : niveau pas trouve");
		}

		this.niveauCharger.remove(niveausup);
		System.out.println("Supression reussi");
		return niveausup;
	}

	public Joueur chargerJoueur(String nomfic) throws Exception {
		Joueur joueur = GestionFichierXML.lireJoueur(nomfic);
		if (joueur == null) {
			throw new Exception("Erreur joueur est null");
		}
		if (this.joueurConecter != null) {
			throw new Exception("Erreur joueur dejas charger");
		}

		this.joueurConecter = joueur;
		return joueur;
	}

	public Joueur sauvegarderJoueur() throws Exception {
		if (this.joueurConecter == null) {
			throw new Exception("Pas de joueur connecte");
		}

		GestionFichierXML.ecrireJoueur(this.joueurConecter);

		if (!this.joueurConecter.equals(GestionFichierXML.lireJoueur(this.joueurConecter.getNomFichier()))) {
			throw new Exception("Erreur : sauvegarde different de l'objet");
		}

		System.out.println("Sauvegarde reussi");

		return this.joueurConecter;
	}

	public Joueur dechargerJoueur() throws Exception {
		Joueur joueursupp = sauvegarderJoueur();
		this.joueurConecter = null;

		if (this.joueurConecter != null) {
			throw new Exception("Erreur : impossible de decherger le joueur");
		}

		System.out.println("Dechergement reussi reussi");

		return joueursupp;
	}

	public List<Niveau> getNiveauCharger() {
		return niveauCharger;
	}

	public Joueur getJoueurConecter() {
		return joueurConecter;
	}

	@Override
	public String toString() {
		return "NiveauCharger\n" + niveauCharger + "\n" + "joueurConecter" + joueurConecter + "\n";
	}
}
