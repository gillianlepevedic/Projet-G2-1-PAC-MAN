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
		Niveau niveau=lireNiveau();
		System.out.println(niveau);
		niveau.setMeilleurScore(12540);
		ecrireNiveau(niveau);
		
	}
	
	private static void ecrireNiveau(Niveau niveau) {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.newDocument();
			Element rootElement = doc.createElementNS("https://iut-lannion.univ-rennes1.fr/", "Niveau");
			doc.appendChild(rootElement);

			rootElement.appendChild(formaterLigneMap(doc, rootElement, "ID", niveau.getId()));
			rootElement.appendChild(formaterLigneMap(doc, rootElement, "NOM",niveau.getNom()));
			rootElement.appendChild(formaterMap(doc, niveau.getMap()));
			rootElement.appendChild(formaterLigneMap(doc, rootElement, "MeilleurScore",
					String.valueOf(niveau.getMeilleurScore())));
			rootElement.appendChild(formaterLigneMap(doc, rootElement, "MeilleurTemps",
					String.valueOf(niveau.getMeilleurTempsEnSeconde())));

			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();

			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			DOMSource source = new DOMSource(doc);

			StreamResult file = new StreamResult(new File("./fic/test2XML"));

			transformer.transform(source, file);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private static Niveau lireNiveau() {
		String id = null;
		String nom = null;
		boolean[][] map = new boolean[Niveau.largeurMap][Niveau.longueurMap];
		
		try {
			File inputFile = new File("./fic/test2XML");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			
			id = doc.getElementsByTagName("ID").item(0).getTextContent();
			nom = doc.getElementsByTagName("NOM").item(0).getTextContent();
			
			
			NodeList nList = doc.getElementsByTagName("MAP");
			Node nNode = nList.item(0);
			Element eElement = (Element) nNode;
			
			
			String ligneString;

			for (int largeur =0; largeur<Niveau.largeurMap; largeur++) {
				ligneString = eElement.getElementsByTagName("Ligne" + String.format("%02d", largeur)).item(0).getTextContent();

				if (ligneString == null) {
					throw new Exception("Erreur : Ligne vide");
				}

				if (ligneString.length() != Niveau.longueurMap) {
					throw new Exception("Erreur : La ligne " + ligneString + " n'est pas a la bonne taille");
				}

				for (int longueur = 0; longueur < Niveau.longueurMap; longueur++) {
					if (ligneString.charAt(longueur) == '0' || ligneString.charAt(longueur) == '1') {
						map[largeur][longueur] = ligneString.charAt(longueur) == '1';
					} else {
						throw new Exception(
								"Erreur : le caractere " + ligneString.charAt(longueur) + " est interdit dans une map");
					}

				}
			}
			
			
			/*for (int i =0; i<Niveau.largeurMap; i++) {
				System.out.println("Ligne"+String.format("%02d", i)+" : " + eElement.getElementsByTagName("Ligne" + String.format("%02d", i)).item(0).getTextContent());
			}*/
			
			//System.out.println("MeilleurScore : " + doc.getElementsByTagName("MeilleurScore").item(0).getTextContent());
			//System.out.println("MeilleurTemps : " + doc.getElementsByTagName("MeilleurTemps").item(0).getTextContent());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new Niveau(id, nom, map);
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
			mapNode.appendChild(formaterLigneMap(fichier, mapNode, ("Ligne" + String.format("%02d", largeur)), tmp));
		}

		return mapNode;
	}

	private static Node formaterLigneMap(Document doc, Element element, String name, String value) {
		Element node = doc.createElement(name);
		node.appendChild(doc.createTextNode(value));
		return node;
	}

}