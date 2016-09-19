package main;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import main.csvCoordinateFinder.simpleCoordinate;

import javax.xml.parsers.*;
import java.io.*;
import java.util.Vector;

public class main {

	public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
		//Get column names for longitude and latitude, get working directory path
		String longitudeAlias, latitudeAlias, dirName;
		
		if (args.length == 0) {
			longitudeAlias = "longitude";
			latitudeAlias = "latitude";
			dirName = System.getProperty("user.dir");
		} else if (args.length == 2) {
			longitudeAlias = args[0];
			latitudeAlias = args[1];
			dirName = System.getProperty("user.dir");
		} else if (args.length == 3) {
			longitudeAlias = args[0];
			latitudeAlias = args[1];
			dirName = args[2];
		} else {
			System.out.println("Invalid argument");
			System.out.println("Expected format: -Java abc");
			System.out.println("Expected format: -Java abc longitudeAlias latitudeAlias");
			System.out.println("Expected format: -Java abc longitudeAlias latitudeAlias directory");
			return;
		}
		System.out.println("Longitude as:" + longitudeAlias);
		System.out.println("Latitude as:" + latitudeAlias);
		System.out.println("Working directory:" + dirName);
		System.out.println("Press enter to continue...");
		System.in.read();

		//Initialize variable
		File myDirectory = new File(dirName);
		String[] fileNames = myDirectory.list();
		csvCoordinateFinder coordinateLoader = new csvCoordinateFinder();

		//Load country objects
		String kmlFileName=null;
		for (String fname : fileNames) {
			if (fname.contains(".kml")) {
				kmlFileName=fname;
				break;
			}
		}
		if(kmlFileName==null){
			System.out.println("Unable to find KML file");
			return;
		}
		Vector<countryObject> countries = KMLParser.getCountries(myDirectory + "/" + kmlFileName);

		//Main loop to append region code
		Vector<simpleCoordinate> simpleCo;
		for (String fname : fileNames) {
			if (fname.contains(".csv")) {
				System.out.println("loading file:" + fname);
				String outputDir = myDirectory + "/output";
				new File(outputDir).mkdir();
				coordinateLoader.appendRegion(myDirectory + "/" + fname, outputDir + "/QRegion_" + fname, countries,longitudeAlias,latitudeAlias);

			}
		}
		System.out.println("end!");
	}
}
