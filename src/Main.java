import lectureFichier.GestionJoueur;
import lectureFichier.GestionNiveau;

public class Main {
	private static GestionNiveau gestionNiveau1;
	private static GestionJoueur gestionJoueur1;

	public static void main(String[] args) {
		gestionNiveau1 = new GestionNiveau("./fic/niveau1.txt");
		gestionNiveau1.LectureNiveau();
		System.out.println(gestionNiveau1);
		gestionNiveau1.sauvegarderNiveau();
		
		gestionJoueur1 = new GestionJoueur("./fic/joueur1.txt");
		gestionJoueur1.LectureJoueur();
		System.out.println(gestionJoueur1);
		gestionJoueur1.sauvegarderJoueur();
	}

}
