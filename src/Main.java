import LectureFichier.LectureNiveau;
import Niveau.Niveau;

public class Main {
	private static LectureNiveau leNiveau = new LectureNiveau("./fic/niveau1.txt");

	public static void main(String[] args) {
		
		Niveau niveau =leNiveau.lectureDuNiveau();
		
		if (niveau != null) {
			for (int j = 0; j < 25; j++) {
				for (int i = 0; i < 25; i++) {
					System.out.print(niveau.getMap()[j][i] ? 1 : 0);
				}
				System.out.println();
			}
			System.out.println(niveau.getMeilleurTempsEnSeconde());
			System.out.println(niveau.getMeilleurScore());
		}

	}

}
