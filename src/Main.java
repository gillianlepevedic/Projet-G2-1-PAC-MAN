import LectureFichier.GestionFichierMap;

public class Main {
	private static GestionFichierMap leFichier = new GestionFichierMap("./fic/niveau1.txt");
	private static boolean[][] map;

	public static void main(String[] args) throws Exception {
		map = leFichier.lireMap();
		if (map != null) {
			for (int j = 0; j < 25; j++) {
				for (int i = 0; i < 25; i++) {
					System.out.print(map[j][i] ? 1 : 0);
				}
				System.out.println();
			}
			//leFichier.ecrireMap(map);
		}

	}

}
