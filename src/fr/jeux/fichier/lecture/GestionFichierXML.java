package fr.jeux.fichier.lecture;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Classe technique Lit et ecrit des fichiers .pac en XML Les fichiers doivent
 * correspond aux caractéristiques des fichiers niveau ou joueur.
 * 
 * @author gillian
 *
 */
public class GestionFichierXML {
	/**
	 * Lit des fichiers niveau. Si le fichier répond aux contraintes obligatoires
	 * (ID NOM MAP). Si il sont absent ou illisible une exception est crée. Le
	 * RECORD est optionnel. Si il est ilisible un record N/A est ajouter.
	 * 
	 * @param Il faut le nom/chemin pour lire le fichier en paramatres.
	 *           (./fic/nomfichier.pac)
	 * @return retourne Niveau qui est lu dans le fichier.
	 * @throws Exception si la partie obligatoire est ilisible
	 */
	public static Niveau lireNiveau(String nomFichier) throws Exception {
		Niveau niveau = null;
		String id = null;
		String nom = null;
		Map map = new Map();

		File inputFile = new File(nomFichier);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		if (doc.getDocumentElement().getNodeName() != "Niveau") {
			throw new Exception("Erreur : pas le bon document " + doc.getDocumentElement().getNodeName());
		}

		id = lectureID(doc.getElementsByTagName("ID"));

		if (id.charAt(0) != 'N') {
			throw new Exception("Erreur : id niveau commence pas par N");
		}

		nom = lectureNom(doc.getElementsByTagName("NOM"));

		niveau = new Niveau(nomFichier, id, nom);

		map.setMap(lireMap(doc.getElementsByTagName("MAP")));
		niveau.setMap(map);

		try {
			NodeList nodeList = doc.getElementsByTagName("RECORD");
			Record record = lireRecord(nodeList.item(0));

			if (record.getId().charAt(0) != 'J') {
				throw new Exception("Erreur : id niveau record commence pas par N");
			}

			niveau.setRecordNiveau(record);

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Imposible de lire partie optionnel");
			niveau.setRecordNiveau(new Record("NA"));
		}

		return niveau;
	}

	/**
	 * Lit des fichiers joueur. Si le fichier répond aux contraintes obligatoires
	 * (ID NOM MAP). Si il sont absent ou illisible une exception est crée. Les
	 * RECORD sont optionnels si il ne sont pas lu aucun record n'est ajouter.
	 * 
	 * @param Il faut le nom/chemin pour lire le fichier en paramatres.
	 *           (./fic/nomfichier.pac)
	 * @return retourne Joueur qui est lu dans le fichier.
	 * @throws Exception si la partie obligatoire est ilisible.
	 */
	public static Joueur lireJoueur(String nomFichier) throws Exception {
		Joueur joueur = null;
		String id = null;
		String nom = null;
		Record record = null;

		File inputFile = new File(nomFichier);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		if (doc.getDocumentElement().getNodeName() != "Joueur") {
			throw new Exception("Erreur : pas le bon document : " + doc.getDocumentElement().getNodeName());
		}

		id = lectureID(doc.getElementsByTagName("ID"));

		if (id.charAt(0) != 'J') {
			throw new Exception("Erreur : id niveau commence pas par N");
		}

		nom = lectureNom(doc.getElementsByTagName("NOM"));
		joueur = new Joueur(nomFichier, id, nom);

		NodeList nodeList = doc.getElementsByTagName("RECORD");
		if (nodeList.getLength() > 0) {
			for (int numRecord = 0; numRecord < nodeList.getLength(); numRecord++) {
				try {

					record = lireRecord(nodeList.item(numRecord));

					if (record.getId().charAt(0) != 'N') {
						throw new Exception("Erreur : id niveau record commence pas par N");
					}

					joueur.ajouterRecord(record);
				} catch (Exception e) {
					System.out.println(e);
					System.out.println("Imposible de lire partie optionnel");
				}
			}

		} else {
			System.out.println("Pas de Record a lire");
		}

		return joueur;
	}

	/**
	 * Ecrit un Niveau sous forme XML. Il ecrit dans le fichier portant le nom
	 * "nomfichier". Il ecrit dans l'orde ID , NOM, MAP, RECORD.
	 * 
	 * @param demande en parametres le Niveau que l'on veut ecrire.
	 */
	public static void ecrireNiveau(Niveau niveau) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			Element rootElement = document.createElementNS("https://iut-lannion.univ-rennes1.fr/", "Niveau");

			rootElement.appendChild(formaterValeur(document, "ID", niveau.getId()));
			rootElement.appendChild(formaterValeur(document, "NOM", niveau.getNom()));
			rootElement.appendChild(formaterMap(document, niveau.getMap()));

			rootElement.appendChild(formaterRecord(document, niveau.getRecordNiveau()));

			document.appendChild(rootElement);

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(new File(niveau.getNomFichier())));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Ecrit un Joueur sous forme XML. Il ecrit dans le fichier portant le nom
	 * "nomfichier". Il ecrit dans l'orde ID , NOM, RECORD.
	 * 
	 * @param demande en parametres le joueur que le veut ecrire.
	 */
	public static void ecrireJoueur(Joueur joueur) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			Element rootElement = document.createElementNS("https://iut-lannion.univ-rennes1.fr/", "Joueur");
			rootElement.appendChild(formaterValeur(document, "ID", joueur.getId()));
			rootElement.appendChild(formaterValeur(document, "NOM", joueur.getNom()));

			for (int numRecord = 0; numRecord < joueur.getListeRecord().size(); numRecord++) {
				rootElement.appendChild(formaterRecord(document, joueur.getListeRecord().get(numRecord)));

			}

			document.appendChild(rootElement);
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(document), new StreamResult(new File(joueur.getNomFichier())));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * La methode recoit une NodeList avec le l'id a lire. Elle le convertit en
	 * string. Et verifie si l'ID lu repond au critere
	 * 
	 * @param recoit le NodeListe contenant l'ID "idNode".
	 * @return String id si il est valide
	 * @throws Exception Si l'ID n'est pas de la bonne taille ou contient des
	 *                   caracter interdit
	 */
	private static String lectureID(NodeList idNode) throws Exception {

		String idString = idNode.item(0).getTextContent();
		String autorisation = "^(N|J){1}[0-9]{10}$";

		if (idString.length() != 11) {
			throw new Exception("Erreur : ID pas de la bonne taille");
		}
		if (!idString.matches(autorisation)) {
			throw new Exception("Erreur : ID avec des caractere interdit");
		}

		return idString;
	}

	/**
	 * La methode recoit une NodeList avec le nom a lire. Elle le convertit en
	 * string. Et verifie si le NOM lu repond au critere
	 * 
	 * @param recoit le NodeListe contenant l'NOM "nomNode".
	 * @return String nom si il est valide
	 * @throws Exception si contient des caractere interdit
	 */
	private static String lectureNom(NodeList nomNode) throws Exception {
		String nomString = nomNode.item(0).getTextContent();
		String autorisation = "^[0-9a-zA-Z]{1,20}$";

		if (!nomString.matches(autorisation)) {
			throw new Exception("Erreur : nom avec des caractere interdit");
		}

		return nomString;
	}

	/**
	 * La methode recoit une NodeList avec la map a lire. Elle le convertit en
	 * tableau a 2 dimention de int. Et verifie si la map lu repond au critere
	 * 
	 * @param recoit le NodeListe contenant la map "mapNode".
	 * @return retourn int[][] si la map repond au critere
	 * @throws Exception Si la map n'est pas de la bonne taille ou contient des
	 *                   caracter interdit
	 */
	private static int[][] lireMap(NodeList mapNode) throws Exception {
		int[][] map = new int[Map.largeurMap][Map.longueurMap];
		String ligneMapString = null;
		Element mapElement = (Element) mapNode.item(0);

		if (mapElement.getChildNodes().getLength() == (Map.largeurMap * 2 + 1)) {

			for (int largeur = 0; largeur < Map.largeurMap; largeur++) {

				ligneMapString = mapElement.getElementsByTagName("Ligne" + String.format("%02d", largeur)).item(0)
						.getTextContent();

				if (ligneMapString == null) {
					throw new Exception("Erreur : Ligne vide");
				}

				if (ligneMapString.length() != Map.longueurMap) {
					System.out.println(ligneMapString.length());
					throw new Exception("Erreur : La ligne " + ligneMapString + " n'est pas a la bonne taille");
				}

				for (int longueur = 0; longueur < Map.longueurMap; longueur++) {
					int tmp = Character.getNumericValue(ligneMapString.charAt(longueur));
					if (tmp == Map.couloir || tmp == Map.mur || tmp == Map.pacGommeFruit || tmp == Map.spawn
							|| tmp == Map.superPacGomme) {
						map[largeur][longueur] = tmp;
					} else {
						throw new Exception("Erreur : le caractere " + tmp + " est interdit dans une map");
					}

				}

			}
		} else {
			throw new Exception("Erreur : Largeur de la map pas a la bonne taille"
					+ (mapElement.getChildNodes().getLength() - 1) / 2);
		}

		return map;
	}

	/**
	 * La methode recoit une NodeList avec le l'record a lire. Elle le convertit en
	 * Record. Et verifie si le record lu repond au critere
	 * 
	 * @param recoit le Node contenant lr record "recordNode".
	 * @return retourn le RECORD lu sinon renvoie Null si il est illisible
	 * @throws Exception
	 */
	private static Record lireRecord(Node recordNode) throws Exception {
		Record record = null;

		String id = null;
		Element recordElement = (Element) recordNode;
		id = lectureID(recordElement.getElementsByTagName("ID"));

		record = new Record(id);

		record.setNom(lectureNom(recordElement.getElementsByTagName("NOM")));
		record.setMeilleurScrore(lectureScore(recordElement.getElementsByTagName("MEILLEUR_SCORE")));
		record.setMeilleurTemps(lectureTemps(recordElement.getElementsByTagName("MEILLEUR_TEMPS")));

		return record;
	}

	/**
	 * La methode recoit une NodeList avec le score a lire. Elle le convertit en
	 * int. Et verifie si le scrore lu repond au critere
	 * 
	 * @param recoit le NodeListe contenant le Score "scoreNode".
	 * @return
	 * @throws Exception contient des caracter interdit
	 */
	private static int lectureScore(NodeList scoreNode) throws Exception {
		String scoreString = scoreNode.item(0).getTextContent();
		return Integer.parseInt(scoreString);
	}

	/**
	 * La methode recoit une NodeList avec le temps a lire. Elle le convertit en
	 * int. Et verifie si le temps lu repond au critere
	 * 
	 * @param recoit le NodeListe contenant le Temps "tempsNode".
	 * @return
	 * @throws Exception contient des caracter interdit
	 */
	private static int lectureTemps(NodeList tempsNode) throws Exception {
		String tempsString = tempsNode.item(0).getTextContent();
		return Integer.parseInt(tempsString);
	}

	/**
	 * Recoit une map de int et le convertit en node pour preparer a l'ecriture sur
	 * le fichier en XML. sur le "document".
	 * 
	 * @param document sur le quelle le record sera mis
	 * @param mapInt   que l'on veut ecrire
	 * @return retourn le Node avec la map ecrit dessus
	 */
	private static Node formaterMap(Document document, int[][] mapInt) {
		Element mapNode = document.createElement("MAP");
		String tmp;

		for (int largeur = 0; largeur < Map.largeurMap; largeur++) {
			tmp = "";
			for (int longueur = 0; longueur < Map.longueurMap; longueur++) {
				tmp += String.valueOf(mapInt[largeur][longueur]);
			}
			mapNode.appendChild(formaterValeur(document, ("Ligne" + String.format("%02d", largeur)), tmp));
		}

		return mapNode;
	}

	/**
	 * Recoit un record et le convertit en node pour preparer a l'ecriture sur le
	 * fichier en XML. sur le "document".
	 * 
	 * 
	 * @param document sur le quelle le record sera mis
	 * @param record   que l'on veut ecrir
	 * @return retourn le Node avec le record ecrit dessus
	 */
	private static Node formaterRecord(Document document, Record record) {
		Element recordNode = document.createElement("RECORD");

		recordNode.appendChild(formaterValeur(document, "ID", record.getId()));
		recordNode.appendChild(formaterValeur(document, "NOM", record.getNom()));
		recordNode.appendChild(formaterValeur(document, "MEILLEUR_SCORE", String.valueOf(record.getMeilleurScrore())));
		recordNode.appendChild(formaterValeur(document, "MEILLEUR_TEMPS", String.valueOf(record.getMeilleurTemps())));

		return recordNode;
	}

	/**
	 * Recoit un "valeur" et "nom" a ecrire dans le "document". Il convertie la
	 * valeur en node avec pour nom "nom". le node node est renvoyer en parametres
	 * pour etres ajouter aux fichier.
	 * 
	 * @param document sur le quelle le valeur sera mis
	 * @param element
	 * @param name
	 * @param value
	 * @return
	 */
	private static Node formaterValeur(Document doc, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}