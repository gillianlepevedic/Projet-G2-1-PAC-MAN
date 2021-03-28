import lectureFichier.GestionNiveau;

public class Main {
	private static GestionNiveau gestionNiveau1;

	public static void main(String[] args) {
		gestionNiveau1 = new GestionNiveau("./fic/niveau1.txt");
		gestionNiveau1.LectureNiveau();
		
		if (gestionNiveau1.getNiveau() != null) {
			System.out.println(gestionNiveau1.getNiveau().getId());
			System.out.println(gestionNiveau1.getNiveau().getNom());
			for (int j = 0; j < 25; j++) {
				for (int i = 0; i < 25; i++) {
					System.out.print(gestionNiveau1.getNiveau().getMap()[j][i] ? 1 : 0);
				}
				System.out.println();
			}
			System.out.println(gestionNiveau1.getNiveau().getMeilleurTempsEnSeconde());
			System.out.println(gestionNiveau1.getNiveau().getMeilleurScore());
			//gestionNiveau1.sauvegarderNiveau();
		}
		
		
	}

}
