package lectureFichier;

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

public class FichierXml {

	public static void main(String[] args) throws Exception {
		Niveau niveau = lireNiveau("./fic/test2XML");
		System.out.println(niveau);
		// ecrireNiveau(niveau);

		Joueur joueur = lireJoueur("./fic/testXML3");
		System.out.println(joueur);
		// ecrireJoueur(joueur);
	}

	public static Niveau lireNiveau(String nomFichier) throws Exception {
		Niveau niveau = null;
		String id = null;
		String nom = null;
		boolean[][] map = null;

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

		map = lireMap(doc.getElementsByTagName("MAP"));

		niveau = new Niveau(nomFichier, id, nom, map);

		try {
			niveau.setMeilleurScore(lectureScore(doc.getElementsByTagName("MeilleurScore")));
			niveau.setMeilleurTempsEnSeconde(lectureTemps(doc.getElementsByTagName("MeilleurTemps")));

		} catch (Exception e) {
			System.out.println(e);
			System.out.println("Imposible de lire partie optionnel");
		}

		return niveau;
	}

	public static Joueur lireJoueur(String nomFichier) throws Exception {
		Joueur joueur = null;
		String id = null;
		String nom = null;

		File inputFile = new File(nomFichier);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(inputFile);
		doc.getDocumentElement().normalize();
		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

		id = lectureID(doc.getElementsByTagName("ID"));
		nom = lectureNom(doc.getElementsByTagName("NOM"));

		joueur = new Joueur(nomFichier, id, nom);
		NodeList nodeList = doc.getElementsByTagName("Record");
		if (nodeList.getLength() > 0) {
			try {
				for (int numRecord = 0; numRecord < nodeList.getLength(); numRecord++) {
					joueur.ajouterRecord(lireRecord(doc.getElementsByTagName("Record").item(numRecord)));
				}

			} catch (Exception e) {
				System.out.println(e);
				joueur = null;
			}
		} else {
			System.out.println("Pas de Recors a lire");
		}

		return joueur;
	}

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

	private static Node formaterMap(Document fichier, boolean[][] mapBool) {
		Element mapNode = fichier.createElement("MAP");
		String tmp;

		for (int largeur = 0; largeur < Niveau.largeurMap; largeur++) {
			tmp = "";
			for (int longueur = 0; longueur < Niveau.longueurMap; longueur++) {
				if (mapBool[largeur][longueur]) {
					tmp += "1";
				} else {
					tmp += "0";
				}
			}
			mapNode.appendChild(formaterValeur(fichier, mapNode, ("Ligne" + String.format("%02d", largeur)), tmp));
		}

		return mapNode;
	}

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

	private static Node formaterValeur(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

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

	private static String lectureNom(NodeList nomNode) throws Exception {
		String nomString = nomNode.item(0).getTextContent();
		String autorisation = "^[0-9a-zA-Z]{1,20}$";

		if (!nomString.matches(autorisation)) {
			throw new Exception("Erreur : nom avec des caractere interdit");
		}

		return nomString;
	}

	private static boolean[][] lireMap(NodeList mapNode) throws Exception {
		boolean[][] map = new boolean[Niveau.largeurMap][Niveau.longueurMap];
		String ligneMapString = null;
		Element mapElement = (Element) mapNode.item(0);

		if (mapElement.getChildNodes().getLength() == (Niveau.largeurMap * 2 + 1)) {

			for (int largeur = 0; largeur < Niveau.largeurMap; largeur++) {

				ligneMapString = mapElement.getElementsByTagName("Ligne" + String.format("%02d", largeur)).item(0)
						.getTextContent();

				if (ligneMapString == null) {
					throw new Exception("Erreur : Ligne vide");
				}

				if (ligneMapString.length() != Niveau.longueurMap) {
					throw new Exception("Erreur : La ligne " + ligneMapString + " n'est pas a la bonne taille");
				}

				for (int longueur = 0; longueur < Niveau.longueurMap; longueur++) {
					if (ligneMapString.charAt(longueur) == '0' || ligneMapString.charAt(longueur) == '1') {
						map[largeur][longueur] = ligneMapString.charAt(longueur) == '1';
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

	private static MeilleurScoreNiveau lireRecord(Node recordNode) {
		String id = null;
		Element recordElement = (Element) recordNode;
		id = recordElement.getElementsByTagName("ID_MAP").item(0).getTextContent();

		MeilleurScoreNiveau record = new MeilleurScoreNiveau(id);
		record.setNomNiveau(recordElement.getElementsByTagName("NOM_MAP").item(0).getTextContent());
		record.setMeilleurScrore(
				Integer.parseInt(recordElement.getElementsByTagName("MeilleurScroreMap").item(0).getTextContent()));
		record.setMeilleurTemps(
				Integer.parseInt(recordElement.getElementsByTagName("MeilleurTempsMap").item(0).getTextContent()));

		return record;
	}

	private static int lectureScore(NodeList scoreNode) {
		String scoreString = scoreNode.item(0).getTextContent();
		return Integer.parseInt(scoreString);
	}

	private static int lectureTemps(NodeList tempsNode) {
		String tempsString = tempsNode.item(0).getTextContent();
		return Integer.parseInt(tempsString);
	}
}