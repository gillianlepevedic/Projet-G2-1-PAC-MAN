package lectureFichier;

import java.util.ArrayList;
import java.util.List;

public class FichierCharger {
	final static int maxElement = 25;
	private List<Niveau> niveauCharger;
	private Joueur joueurConecter;

	public FichierCharger() {
		niveauCharger = new ArrayList<>();
		this.joueurConecter = null;
	}

	public Niveau ajouterNiveau(String nomfichier) throws Exception {
		Niveau niveau = GestionFichierXML.lireNiveau(nomfichier);
		if (niveau != null) {
			if (this.niveauCharger.size() < maxElement) {
				this.niveauCharger.add(niveau);
			} else {
				throw new Exception("Erreur : Liste plein");
			}
		} else {
			throw new Exception("Erreur : Niveau null");
		}

		return niveau;
	}

	public Niveau sauvegardeNiveau(String id) throws Exception {
		boolean sauvegarder = false;
		Niveau retourneNiveau = null;

		for (Niveau niveau : niveauCharger) {
			if (id.equals(niveau.getId())) {
				GestionFichierXML.ecrireNiveau(niveau);
				retourneNiveau = niveau;
				sauvegarder = true;
			}
		}

		if (sauvegarder) {
			System.out.println("Sauvegarde reussi");
		} else {
			throw new Exception("Erreur : niveau pas trouver");
		}

		return retourneNiveau;
	}

	public Niveau dechargerNiveau(String id) throws Exception {
		Niveau niveausup = sauvegardeNiveau(id);

		if (this.niveauCharger.contains(niveausup)) {
			this.niveauCharger.remove(niveausup);
		} else {
			throw new Exception("Erreur : niveau pas trouve");
		}
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
		return this.joueurConecter;
	}

	public Joueur dechargerJoueur() throws Exception {
		Joueur joueursupp = sauvegarderJoueur();
		this.joueurConecter = null;
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
		return "FichierCharger \nNiveauCharger\n" + niveauCharger + "\n" + joueurConecter + "\n";
	}
}
