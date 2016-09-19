package main;

import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KMLParser {

	public static Vector<countryObject> getTestObject() {
		Vector<countryObject> countries = new Vector();
		countryObject x1 = new countryObject("test1");
		polygon y1 = new polygon();
		y1.addPoint((float) 190, 100);
		y1.addPoint((float) 72, 228);
		y1.addPoint((float) 266, 471);
		y1.addPoint((float) 500, 350);
		y1.addPoint((float) 363, 252);
		y1.addPoint((float) 400, 160);
		x1.addPoly(y1);
		countries.add(x1);

		return countries;
	}

	public static Vector<countryObject> getCountries(String filename)
			throws ParserConfigurationException, SAXException, IOException {
		Vector<countryObject> countries = new Vector();

		try {
			File inputFile = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

			NodeList nList = doc.getElementsByTagName("Placemark");
			int count = nList.getLength();
			System.out.println("----------------------------");

			for (int temp = 0; temp < nList.getLength(); temp++) {
				countryObject tempCountry = null;
				Node nNode = nList.item(temp);
				System.out.println("\nCurrent Element :" + nNode.getNodeName());

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					String countryName = eElement.getElementsByTagName("name").item(0).getTextContent();
					System.out.println("Country name : " + countryName);

					tempCountry = new countryObject(countryName);

					NodeList geoList = eElement.getElementsByTagName("Polygon");
					Integer polyCount = geoList.getLength();
					for (int n = 0; n < geoList.getLength(); n++) {
						Node geoNode = geoList.item(n);
						polygon tempPolygon = new polygon();

						System.out.println("Current Element :" + geoNode.getNodeName());

						if (geoNode.getNodeType() == Node.ELEMENT_NODE) {
							Element geoElement = (Element) geoNode;
							String coordinateString = geoElement.getElementsByTagName("coordinates").item(0)
									.getTextContent();
//							System.out.println("Coordinate : " + coordinateString);
							coordinateString = coordinateString.trim();
							coordinateString = coordinateString.replaceAll(" ", ",");
							coordinateString = coordinateString.replaceAll("\n", ",");
							coordinateString = coordinateString.replaceAll("0,", ",");
							coordinateString = coordinateString.replaceAll(",{1,}", ",");

//							System.out.println("Coordinate : " + coordinateString);

							String[] tabOfFloatString = coordinateString.split(",");

							for (int j = 0; j < tabOfFloatString.length / 2; j++) {
								float x = Float.parseFloat(tabOfFloatString[j * 2]);
								float y = Float.parseFloat(tabOfFloatString[j * 2 + 1]);
								tempPolygon.addPoint(x, y);
							}
						}
						tempCountry.addPoly(tempPolygon);
					}
				}
				countries.add(tempCountry);
			}
		} catch (Exception e) {
			System.out.println(e.toString());
		}

		return countries;
	}

}
