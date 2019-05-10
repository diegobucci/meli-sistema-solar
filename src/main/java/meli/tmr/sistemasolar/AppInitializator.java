package meli.tmr.sistemasolar;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.BucketInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import meli.tmr.sistemasolar.models.*;
import meli.tmr.sistemasolar.services.CalculatorUtil;
import meli.tmr.sistemasolar.services.WeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppInitializator {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppInitializator.class);
    private WeatherService weatherService;
    private AppFirebase appFirebase;

    @Autowired
    public AppInitializator(WeatherService weatherService, AppFirebase appFirebase){
        this.weatherService = weatherService;
        this.appFirebase = appFirebase;
    }

    @PostConstruct
    private void init() throws IOException {
        LOGGER.info("App init");
        appFirebase.initBD();
        LOGGER.info("Ejecutando prediccion del clima para los proximos 10 años");
        SolarSystem solarSystem = new SolarSystem(getPlanetsList());
        LOGGER.info("Sistema solar creado con los planetas: " + solarSystem.getPlanets().stream().map(p -> p.getCivilizationName()).collect(Collectors.joining(", ")));
        weatherService.getWeatherReport(solarSystem,10);
        LOGGER.info("Predicción del clima terminada");
    }


    public static List<Planet> getPlanetsList(){
        Planet ferengi = new Planet("Ferengi", 1, 500 , true);
        Planet betasoide = new Planet("Betasoide", 3, 2000 , true );
        Planet vulcano = new Planet("Vulcano", 5, 1000, false);
        return Arrays.asList(ferengi, betasoide, vulcano);
    }

}
