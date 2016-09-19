package main;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import com.opencsv.*;

import main.csvCoordinateFinder.simpleCoordinate;

public class csvCoordinateFinder {

	public String getCSVString(String[] str) {
		String tempStr = "";
		for (String x : str) {
			tempStr += "\"" + x + "\",";
		}
		tempStr = tempStr.substring(0, tempStr.length() - 1);
		return tempStr;
	}

	public void appendRegion(String inputFileName, String outputFileName, Vector<countryObject> countries,String longitudeAlias,String latitudeAlias) {
		try {
			int idIndex = -1, longitudeIndex = -1, latitudeIndex = -1, regionCodeIndex = -1;
			CSVReader reader = new CSVReader(new FileReader(inputFileName));
			BufferedWriter br = new BufferedWriter(new FileWriter(outputFileName));
			StringBuilder sb = new StringBuilder();

			String[] nextLine;
			if ((nextLine = reader.readNext()) == null) {
				System.out.println("Empty file");
				br.close();
				return;
			} else {
				String t = getCSVString(nextLine) + ",regionCode\n";
				System.out.println(t);
				sb.append(t);
				for (int i = 0; i < nextLine.length; i++) {
					if (nextLine[i].contains("id"))
						idIndex = i;
					if (nextLine[i].contains(longitudeAlias))
						longitudeIndex = i;
					if (nextLine[i].contains(latitudeAlias))
						latitudeIndex = i;
				}
			}
			if (idIndex == -1 || longitudeIndex == -1 || latitudeIndex == -1) {
				System.out.println("Can't find id/longitude/latitude column");
				br.close();
				return;
			}

			while ((nextLine = reader.readNext()) != null) {
				String tempRegion = "Unknown";
				for (countryObject y : countries) {
					if (y.pointInCountry(Float.parseFloat(nextLine[longitudeIndex]),
							Float.parseFloat(nextLine[latitudeIndex]))) {
						tempRegion = y.name;
						break;
					}
				}
				String t = getCSVString(nextLine) + "," + tempRegion + "\n";
				System.out.println(nextLine[longitudeIndex]+","+nextLine[latitudeIndex]+","+tempRegion);
				sb.append(t);
			}

			br.write(sb.toString());
			br.close();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public class simpleCoordinate {
		public String id;
		public String longitude, latitude;
		public String regionCode;

		simpleCoordinate(String id, String longitude, String latitude) {
			this.id = id;
			this.longitude = longitude;
			this.latitude = latitude;
			regionCode = null;
		}

		public void setRegionCode(String code) {
			this.regionCode = code;
		}
	}

	// public void writeCoordinates(Vector<simpleCoordinate>
	// simpleCoordinates,String fileName)
	// {
	//
	// try {
	// BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
	// StringBuilder sb = new StringBuilder();
	//
	// sb.append("id,latitude,longitude,regionCode\n");
	// for (simpleCoordinate x : simpleCoordinates) {
	// sb.append(x.id+",");
	// sb.append(x.latitude+",");
	// sb.append(x.longitude+",");
	// sb.append(x.regionCode+",");
	// sb.append("\n");
	// }
	//
	// br.write(sb.toString());
	// br.close();
	// } catch (Exception e) {
	// System.out.println(e.toString());
	// }
	// }

	// public Vector<simpleCoordinate> loadCoordinates(String fileName){
	//
	// try {
	// int idIndex=-1,longitudeIndex=-1,latitudeIndex=-1,regionCodeIndex=-1;
	// CSVReader reader;
	// reader = new CSVReader(new FileReader(fileName));
	// String [] nextLine;
	// if ((nextLine = reader.readNext()) == null)
	// return null;
	// else
	// for(int i=0;i<nextLine.length;i++)
	// {
	// if(nextLine[i].contains("id"))
	// idIndex=i;
	// if(nextLine[i].contains("longitude"))
	// longitudeIndex=i;
	// if(nextLine[i].contains("latitude"))
	// latitudeIndex=i;
	// }
	// if(idIndex==-1||longitudeIndex==-1||latitudeIndex==-1)
	// {
	// System.out.println("can't find id/longitude/latitude column");
	// return null;
	// }
	//
	// Vector<simpleCoordinate> simpleCoordinates = new Vector();
	// while ((nextLine = reader.readNext()) != null) {
	// simpleCoordinates.add(new
	// simpleCoordinate(nextLine[idIndex],nextLine[longitudeIndex],nextLine[latitudeIndex]));
	// }
	// return simpleCoordinates;
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// return null;
	// }
	// }

}
