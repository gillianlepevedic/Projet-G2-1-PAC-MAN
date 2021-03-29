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

	public void chargerNiveau(String nomfic) {

		Niveau niveau = new Niveau(nomfic);
		try {
			niveau.lectureNiveau();
			ajouterNiveau(niveau);
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Chargement impossible");
		}
	}

	public void sauvegardeNiveau(String id) {
		for (int i = 0; i < niveauCharger.size(); i++) {
			if (id == this.niveauCharger.get(i).getId()) {
				this.niveauCharger.get(i).sauvegarderNiveau();
			}
		}
	}

	public void chargerJoueur(String nomfic) {

		Joueur joueur = new Joueur(nomfic);
		try {
			joueur.lectureJoueur();
			this.joueurConecter = joueur;
		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Chargement impossible");
		}
	}

	public void sauvegarderJoueur() {
		this.joueurConecter.sauvegarderJoueur();
	}

	private void ajouterNiveau(Niveau niveau) throws Exception {
		if (niveau != null) {
			if (this.niveauCharger.size() < maxElement) {
				this.niveauCharger.add(niveau);
			} else {
				throw new Exception("Erreur : Liste plein");
			}
		} else {
			System.out.println("Record null");
		}
	}

	public List<Niveau> getNiveauCharger() {
		return niveauCharger;
	}

	public Joueur getJoueurConecter() {
		return joueurConecter;
	}

	@Override
	public String toString() {
		return "FichierCharger [niveauCharger=" + niveauCharger + ", joueurConecter=" + joueurConecter + "]";
	}
}
