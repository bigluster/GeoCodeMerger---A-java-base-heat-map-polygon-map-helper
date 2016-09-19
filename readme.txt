Why?
It is fairly easy to create map object in Qlik view and Qlik sense. Following are two awesome tutorials by Michael Tarallo on how to create map object:
https://community.qlik.com/docs/DOC-6941 (Point map)
https://community.qlik.com/docs/DOC-7354 (Polygon map)
In practice, polygon map is much harder to implement than point map. It is because with longitutude and latitude you can build a point map easily. But for polygon maps, not only an KML file is required to difine boundaries, a region field in data source is also expected to link your point with KML. This region field must be the same field with KML file.In addition, this region field must be in your KML file as well.
Take Michael's second tutorial as example(where you can download several useful KML files like US states.KML and world.KML), there is a CountryISOCode to link with world.kml. Almost all geography data comes with longitude and latitude(else it won't be a geo data),but very few data sets contain CountryISOCode.

What?
This Java programme is a demostration of using ray-casting to associate points with polygon. Base on the content of KML file, it builds several country objects and test if points are inside this "countries"; if yes, append a regionCode at the end of output CSV. It is functional, but only tested with world.kml(https://community.qlik.com/docs/DOC-7354). 
At this moment it works with only CSV files.

How?
In the repository, there is runnable JAR file call GeoCodeMerger.jar.
Download GeoCodeMerger.jar, 2003-9.csv and world.kml file to your local machine.
Open commandline console-> cd /your working directory/ -> java -jar GeoCodeMerger.jar
You will see the code running and an output folder is generated. In the output folder, You will find new csv files with one more column regionCode.
requirement:
One KML file(if there is more than one, the first is chosen)
Multiple CSV file with a longitude column and a latitude column.(You may use 
java -jar GeoCodeMerger.jar (longitudeAlias) (latitudeAlias) 
to specify new column names for longitude and latitudeAlias.For example:
java -jar GeoCodeMerger.jar col2 col5 )