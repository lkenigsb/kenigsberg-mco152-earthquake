package kenigsberg.earthquakes;

import com.google.gson.Gson;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EarthquakeFrame extends JFrame
{
    FeatureCollection featureCollection;
    public EarthquakeFrame() throws IOException {

        URL url = new URL("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/significant_month.geojson");
        URLConnection connection = url.openConnection();
        //read data from an input stream
        InputStream inputStream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        Gson gson = new Gson();
        //This will tell gson: take this JSon and take this class and
        // create classes of this configuration of JSon
        featureCollection = gson.fromJson(reader, FeatureCollection.class);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        JLabel title = new JLabel("Most recent earthquake:", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        mainPanel.add(title, BorderLayout.NORTH);

        JLabel weatherInfo = new JLabel(featureCollection.features[0].properties.place
                , SwingConstants.CENTER);
        weatherInfo.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        weatherInfo.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(weatherInfo, BorderLayout.CENTER);



        setContentPane(mainPanel);
        setSize(500, 300);
        setTitle("Earthquake Location");
        //What happens when we hit the exit button
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //System.out.println(featureCollection.features[0].properties.place);
    }

}
