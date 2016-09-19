package main;

import java.util.Vector;

public class polygon {
	Vector<coordinate> coordinates;

	public polygon() {
		coordinates = new Vector();
	}

	public void addPoint(float x, float y) {
		coordinates.add(new coordinate(x, y));
	}

	public coordinate get(int index) {
		return coordinates.elementAt(index);
	}

	public boolean pointInPolygon(float testx, float testy) {
		int i, j;
		boolean c = false;
		int nvert = coordinates.size();
		for (i = 0, j = nvert - 1; i < nvert; j = i++) {
			if (((coordinates.get(i).getY() > testy) != (coordinates.get(j).getY() > testy))
					&& (testx < (coordinates.get(j).getX() - coordinates.get(i).getX())
							* (testy - coordinates.get(i).getY())
							/ (coordinates.get(j).getY() - coordinates.get(i).getY()) + coordinates.get(i).getX()))
				c = !c;
		}
		return c;
	}
}
