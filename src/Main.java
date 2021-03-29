import lectureFichier.FichierCharger;

public class Main {
	private static FichierCharger jeux;

	public static void main(String[] args) {
		jeux = new FichierCharger();
		
		jeux.chargerNiveau("./fic/niveau1.txt");
		jeux.chargerJoueur("./fic/joueur1.txt");
		
		System.out.println(jeux);
	}

}
