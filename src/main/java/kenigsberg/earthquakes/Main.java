package kenigsberg.earthquakes;

import com.google.gson.Gson;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) throws IOException {
        /*
        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");
        URLConnection connection = url.openConnection();
        //read data from an input stream
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Gson gson = new Gson();
        //This will tell gson: take this JSon and take this
        class and create classes of this configuration of JSon
        FeatureCollection featureCollection = gson.fromJson(reader,FeatureCollection.class);
        System.out.println(featureCollection.features[0].properties.place);

 */

        EarthquakeFrame earthquakeFrame = new EarthquakeFrame();
        earthquakeFrame.setVisible(true);

    }
}
