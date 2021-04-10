package fr.jeux.lectureFichier;

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

import fr.jeux.joueur.Joueur;
import fr.jeux.joueur.MeilleurScoreNiveau;
import fr.jeux.niveau.Map;
import fr.jeux.niveau.Niveau;

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
	 * (ID NOM MAP). Le SCORE et TEMPS sont optionnels
	 * 
	 * @param nomFichier et le chemin pour y acceder
	 * @return Niveau
	 * @throws Exception si la partie obligatoire est ilisible
	 */
	public static Niveau lireNiveau(String nomFichier) throws Exception {
		Niveau niveau = null;
		String id = null;
		String nom = null;
		int[][] map = null;

		File inputFile = new File(nomFichier);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		if (doc.getDocumentElement().getNodeName() != "Niveau") {
			throw new Exception("Erreur : pas le bon document " + doc.getDocumentElement().getNodeName());
		}

		id = lectureID(doc.getElementsByTagName("ID"));
		nom = lectureNom(doc.getElementsByTagName("NOM"));

		niveau = new Niveau(nomFichier, id, nom);

		map = lireMap(doc.getElementsByTagName("MAP"));
		niveau.setMap(new Map(map));

		try {
			niveau.setMeilleurScore(lectureScore(doc.getElementsByTagName("MeilleurScore")));
			niveau.setMeilleurTempsEnSeconde(lectureTemps(doc.getElementsByTagName("MeilleurTemps")));

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Imposible de lire partie optionnel");
		}

		return niveau;
	}

	/**
	 * Lit des fichiers joueur. Si le fichier répond aux contraintes obligatoires
	 * (ID NOM). Les RECORD sont optionnels
	 * 
	 * @param nomFichier et le chemin pour y acceder
	 * @return Joueur
	 * @throws Exception si la partie obligatoire est ilisible
	 */
	public static Joueur lireJoueur(String nomFichier) throws Exception {
		Joueur joueur = null;
		String id = null;
		String nom = null;

		File inputFile = new File(nomFichier);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();

		if (doc.getDocumentElement().getNodeName() != "Joueur") {
			throw new Exception("Erreur : pas le bon document : " + doc.getDocumentElement().getNodeName());
		}

		id = lectureID(doc.getElementsByTagName("ID"));
		nom = lectureNom(doc.getElementsByTagName("NOM"));
		joueur = new Joueur(nomFichier, id, nom);

		NodeList nodeList = doc.getElementsByTagName("Record");
		if (nodeList.getLength() > 0) {
			for (int numRecord = 0; numRecord < nodeList.getLength(); numRecord++) {
				try {
					joueur.ajouterRecord(lireRecord(doc.getElementsByTagName("Record").item(numRecord)));
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
	 * Ecrit un niveau sous forme XML.
	 * 
	 * @param niveau
	 */
	public static void ecrireNiveau(Niveau niveau) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			Element rootElement = document.createElementNS("https://iut-lannion.univ-rennes1.fr/", "Niveau");

			rootElement.appendChild(formaterValeur(document, rootElement, "ID", niveau.getId()));
			rootElement.appendChild(formaterValeur(document, rootElement, "NOM", niveau.getNom()));
			rootElement.appendChild(formaterMap(document, niveau.getMap()));
			rootElement.appendChild(
					formaterValeur(document, rootElement, "MeilleurScore", String.valueOf(niveau.getMeilleurScore())));
			rootElement.appendChild(formaterValeur(document, rootElement, "MeilleurTemps",
					String.valueOf(niveau.getMeilleurTempsEnSeconde())));
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
	 * Ecrit un Joueur sous forme XMl
	 * 
	 * @param joueur
	 */
	public static void ecrireJoueur(Joueur joueur) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document document = dBuilder.newDocument();
			Element rootElement = document.createElementNS("https://iut-lannion.univ-rennes1.fr/", "Joueur");
			rootElement.appendChild(formaterValeur(document, rootElement, "ID", joueur.getId()));
			rootElement.appendChild(formaterValeur(document, rootElement, "NOM", joueur.getNom()));

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
	 * Verifie si l'ID lu repond au critere
	 * 
	 * @param idNode
	 * @return String id si il est valide
	 * @throws Exception Si l'ID n'est pas de la bonne taille et contient des
	 *                   caracter interdit
	 */
	private static String lectureID(NodeList idNode) throws Exception {
		String idString = idNode.item(0).getTextContent();
		String autorisation = "^[0-9]{10}$";

		if (idString.length() != 10) {
			throw new Exception("Erreur : ID pas de la bonne taille");
		}
		if (!idString.matches(autorisation)) {
			throw new Exception("Erreur : ID avec des caractere interdit");
		}

		return idString;
	}

	/**
	 * Verifie si l'NOM lu repond au critere
	 * 
	 * @param nomNode
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
	 * Verifie si la MAP lu repond au critere
	 * 
	 * @param mapNode
	 * @return boolean[][] si la map repond au critere
	 * @throws Exception Si la map n'est pas a la bonne taille, contient des
	 *                   caracter interdit
	 */
	private static int[][] lireMap(NodeList mapNode) throws Exception {
		int[][] map = new int[Map.getLargeurmap()][Map.getLongueurmap()];
		String ligneMapString = null;
		Element mapElement = (Element) mapNode.item(0);

		if (mapElement.getChildNodes().getLength() == (Map.getLargeurmap() * 2 + 1)) {

			for (int largeur = 0; largeur < Map.getLargeurmap(); largeur++) {

				ligneMapString = mapElement.getElementsByTagName("Ligne" + String.format("%02d", largeur)).item(0)
						.getTextContent();

				if (ligneMapString == null) {
					throw new Exception("Erreur : Ligne vide");
				}

				if (ligneMapString.length() != Map.getLongueurmap()) {
					System.out.println(ligneMapString.length());
					throw new Exception("Erreur : La ligne " + ligneMapString + " n'est pas a la bonne taille");
				}

				for (int longueur = 0; longueur < Map.getLongueurmap(); longueur++) {
					if (ligneMapString.charAt(longueur) == '0' || ligneMapString.charAt(longueur) == '1' ||  ligneMapString.charAt(longueur) == '2') {
						map[largeur][longueur] = Character.getNumericValue(ligneMapString.charAt(longueur)) ;
					} else {
						throw new Exception("Erreur : le caractere " + ligneMapString.charAt(longueur)
								+ " est interdit dans une map");
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
	 * Verifie si le RECORD lu repond au critere
	 * 
	 * @param recordNode
	 * @return MeilleurScoreNiveau sinon renvoie Null
	 */
	private static MeilleurScoreNiveau lireRecord(Node recordNode) {
		MeilleurScoreNiveau record = null;

		try {
			String id = null;
			Element recordElement = (Element) recordNode;
			id = lectureID(recordElement.getElementsByTagName("ID_MAP"));

			record = new MeilleurScoreNiveau(id);

			record.setNomNiveau(lectureNom(recordElement.getElementsByTagName("NOM_MAP")));
			record.setMeilleurScrore(lectureScore(recordElement.getElementsByTagName("MeilleurScroreMap")));
			record.setMeilleurTemps(lectureTemps(recordElement.getElementsByTagName("MeilleurTempsMap")));

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("record ilisible");
			record = null;
		}

		return record;
	}

	/**
	 * Verifie si le SCORE lu repond au critere
	 * 
	 * @param scoreNode
	 * @return
	 * @throws Exception contient des caracter interdit
	 */
	private static int lectureScore(NodeList scoreNode) throws Exception {
		String scoreString = scoreNode.item(0).getTextContent();
		return Integer.parseInt(scoreString);
	}

	/**
	 * Verifie si le TEMPS lu repond au critere
	 * 
	 * @param scoreNode
	 * @return
	 * @throws Exception contient des caracter interdit
	 */
	private static int lectureTemps(NodeList tempsNode) throws Exception {
		String tempsString = tempsNode.item(0).getTextContent();
		return Integer.parseInt(tempsString);
	}

	private static Node formaterMap(Document fichier, int[][] mapBool) {
		Element mapNode = fichier.createElement("MAP");
		String tmp;

		for (int largeur = 0; largeur < Map.getLargeurmap(); largeur++) {
			tmp = "";
			for (int longueur = 0; longueur < Map.getLongueurmap(); longueur++) {
					tmp += String.valueOf(longueur);
			}
			mapNode.appendChild(formaterValeur(fichier, mapNode, ("Ligne" + String.format("%02d", largeur)), tmp));
		}

		return mapNode;
	}

	/**
	 * Creer un Node Record et y mets toute les valeurs dedans
	 * 
	 * @param document
	 * @param record
	 * @return
	 */
	private static Node formaterRecord(Document document, MeilleurScoreNiveau record) {
		Element recordNode = document.createElement("Record");

		recordNode.appendChild(formaterValeur(document, recordNode, "ID_MAP", record.getId()));
		recordNode.appendChild(formaterValeur(document, recordNode, "NOM_MAP", record.getNomNiveau()));
		recordNode.appendChild(
				formaterValeur(document, recordNode, "MeilleurScroreMap", String.valueOf(record.getMeilleurScrore())));
		recordNode.appendChild(
				formaterValeur(document, recordNode, "MeilleurTempsMap", String.valueOf(record.getMeilleurTemps())));

		return recordNode;
	}

	/**
	 * Foramt le nom et les valeur en format XML
	 * 
	 * @param doc
	 * @param element
	 * @param name
	 * @param value
	 * @return
	 */
	private static Node formaterValeur(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}
}