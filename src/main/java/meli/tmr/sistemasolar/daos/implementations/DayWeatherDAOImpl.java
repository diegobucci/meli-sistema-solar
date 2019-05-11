package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppFirebase;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.models.DayWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class DayWeatherDAOImpl implements DayWeatherDAO  {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayWeatherDAOImpl.class);

    private static final String COLLECTION_NAME = "weathers";
    private static final String DOCUMENT_NAME = "weather-";
    private static final String DAY = "day";
    private static final String WEATHER = "weather";

    @Override
    public DayWeather getByDay(Integer day) {

        LOGGER.info("Lectura de la bbdd para el dia: " + day);
        DayWeather dayWeather = new DayWeather();
        try {
            CollectionReference weathers = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = weathers.whereEqualTo(DAY, day);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            querySnapshot.get().getDocuments().forEach(document -> {
                dayWeather.setDia(Integer.parseInt(Objects.requireNonNull(document.get(DAY)).toString()));
                dayWeather.setClima(Objects.requireNonNull(document.get(WEATHER)).toString());
            });
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return dayWeather;
    }

    @Override
    public void save(DayWeather dayWeather) {
        Map<String, Object> data = new HashMap<>();
        data.put(DAY, dayWeather.getDia());
        data.put(WEATHER, dayWeather.getClima());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME +dayWeather.getDia()).set(data, SetOptions.merge());
    }
}

