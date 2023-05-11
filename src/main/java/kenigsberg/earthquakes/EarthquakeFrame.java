package kenigsberg.earthquakes;

import com.google.gson.Gson;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class EarthquakeFrame extends JFrame {
    FeatureCollection featureCollection;

    public EarthquakeFrame() throws IOException {

        setSize(800, 300);
        setTitle("Earthquake Location");
        //What happens when we hit the exit button
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        JLabel title = new JLabel("Most recent earthquake:", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setForeground(Color.BLACK);

        mainPanel.add(title, BorderLayout.NORTH);

        JLabel earthquakePlace = new JLabel();
        earthquakePlace.setFont(new Font(Font.MONOSPACED, Font.BOLD, 30));
        earthquakePlace.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(earthquakePlace, BorderLayout.CENTER);
        earthquakePlace.setHorizontalAlignment(SwingConstants.CENTER);


        setContentPane(mainPanel);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://earthquake.usgs.gov/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();


        EarthquakeService service = retrofit.create(EarthquakeService.class);

        //getLatestEarthquakes returns and observable,
        // we're creating a .subscribe on that Observable
        //.subscribe takes in 2 things:
        //a Consumer which is happy path, what should happen if successful
        //another Consumer which throws an exception - if there was an error
        Disposable disposable = service.getLatestEarthquakes()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.newThread())
                .subscribe(
                        featureCollection -> {
                            String location = featureCollection.features[0].properties.place;
                            earthquakePlace.setText(location);
                            earthquakePlace.setHorizontalAlignment(SwingConstants.CENTER);
                        },
                        Throwable::printStackTrace

                );
    }

}
