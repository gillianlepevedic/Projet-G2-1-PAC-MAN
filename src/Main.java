import lectureFichier.Joueur;
import lectureFichier.Niveau;

public class Main {
	private static Niveau niveau1;
	private static Joueur joueur1;

	public static void main(String[] args) {
		niveau1 = new Niveau("./fic/niveau1.txt");
		try {
			niveau1.lectureNiveau();
			niveau1.sauvegarderNiveau();
		}catch(Exception e){
			System.out.println(e);
			niveau1 = null;
		}
		
		joueur1 = new Joueur("./fic/joueur1.txt");
		try {
			joueur1.lectureJoueur();
			joueur1.sauvegarderJoueur();
		}catch(Exception e){
			System.out.println(e);
			niveau1 = null;
		}
		
		System.out.println(niveau1);
		System.out.println(joueur1);
	}

}
