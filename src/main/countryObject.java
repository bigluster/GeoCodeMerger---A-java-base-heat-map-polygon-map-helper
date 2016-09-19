package main;

import java.util.Vector;

public class countryObject {

	String name;
	Vector<polygon> polygons;

	public countryObject(String name) {
		this.name = name;
		polygons = new Vector();
	}

	public void addPoly(polygon ply) {
		polygons.add(ply);
	}

	public polygon get(int index) {
		return polygons.elementAt(index);
	}

	public boolean pointInCountry(float testx, float testy) {
		for (polygon ply : polygons) {
			if (ply.pointInPolygon(testx, testy))
				return true;
		}
		return false;
	}
}
