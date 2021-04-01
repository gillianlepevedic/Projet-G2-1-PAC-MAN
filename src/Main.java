import lectureFichier.FichierCharger;

public class Main {
	private static FichierCharger jeux;

	public static void main(String[] args) {
		jeux = new FichierCharger();
		try {
			jeux.ajouterNiveau("./fic/niveau1.pac");
			jeux.chargerJoueur("./fic/joueur1.pac");
			//jeux.ajouterNiveau("./fic/niveau2.pac");
			//jeux.dechargerJoueur();
			//jeux.chargerJoueur("./fic/joueur2.pac");
			System.out.println(jeux);

			//jeux.dechargerNiveau("0000000019");
			//jeux.dechargerNiveau("0000000018");
			//jeux.dechargerJoueur();
			
			
			System.out.println(jeux.getNiveauCharger().get(0).mapValide(jeux.getNiveauCharger().get(0).getMap()));
		} catch (Exception e) {
			System.out.println(e);
		}

	}

}
