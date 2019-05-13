package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppFirebase;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.exceptions.NoWeatherFoundException;
import meli.tmr.sistemasolar.models.DayWeather;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class DayWeatherDAOImpl implements DayWeatherDAO  {

    private static final Logger LOGGER = LoggerFactory.getLogger(DayWeatherDAOImpl.class);

    private static final String COLLECTION_NAME = "weathers";
    private static final String DOCUMENT_NAME = "weather-";
    private static final String DAY = "day";
    private static final String WEATHER = "weather";

    @Override
    public DayWeather getByDay(Integer day) {
        LOGGER.info("Lectura de la bbdd dentro de la coleccion " + COLLECTION_NAME + " para el dia: " + day);
        DayWeather dayWeather = new DayWeather();
        try {
            CollectionReference weathers = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = weathers.whereEqualTo(DAY, day);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            List<QueryDocumentSnapshot> documents = querySnapshot.get().getDocuments();
            if(documents.size() == 0) throw new NoWeatherFoundException("No se encontro ningun resultado para el dia " + day);
            documents.forEach(document -> {
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
        LOGGER.info("Guardar en la bbdd dentro de la coleccion " + COLLECTION_NAME + " el dia " + dayWeather.getDia());
        Map<String, Object> data = new HashMap<>();
        data.put(DAY, dayWeather.getDia());
        data.put(WEATHER, dayWeather.getClima());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME +dayWeather.getDia()).set(data, SetOptions.merge());
    }
}

