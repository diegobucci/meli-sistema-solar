package meli.tmr.solarsystem;

import meli.tmr.solarsystem.models.Planet;
import meli.tmr.solarsystem.models.SolarSystem;
import meli.tmr.solarsystem.services.WeatherService;
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

    @Autowired
    private WeatherService weatherService;
    @Autowired
    private AppFirebase appFirebase;

    @PostConstruct
    private void init() throws IOException {
        LOGGER.info("App init");
        appFirebase.initBD();
        LOGGER.info("Ejecutando prediccion del clima para los proximos 10 años");
        SolarSystem solarSystem = new SolarSystem(buildPlanetList());
        LOGGER.info("Sistema solar creado con los planetas: " + getAllPlanets(solarSystem));
        weatherService.getWeatherReport(solarSystem,10);
        LOGGER.info("Predicción del clima terminada");
    }

    private String getAllPlanets(SolarSystem solarSystem) {
        return solarSystem.getPlanets().stream().map(Planet::getCivilizationName).collect(Collectors.joining(", "));
    }


    public static List<Planet> buildPlanetList(){
        Planet ferengi = new Planet("Ferengi", 1, 500 , true);
        Planet betasoide = new Planet("Betasoide", 3, 2000 , true );
        Planet vulcano = new Planet("Vulcano", 5, 1000, false);
        return Arrays.asList(ferengi, betasoide, vulcano);
    }

}
