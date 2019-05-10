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
import java.util.concurrent.ExecutionException;

@Service
public class DayWeatherDAOImpl implements DayWeatherDAO  {
    private static final Logger LOGGER = LoggerFactory.getLogger(DayWeatherDAOImpl.class);

    public static final String COLLECTION_NAME = "weathers";
    public static final String DOCUMENT_NAME = "weather";

    @Override
    public DayWeather getByDay(Integer day) {
        DayWeather dayWeather = new DayWeather();
        try {
            CollectionReference weathers = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = weathers.whereEqualTo("day", day);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) { // Debería haber un único valor
                dayWeather = new DayWeather(Integer.parseInt(document.get("day").toString()), document.get("weather").toString());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return dayWeather;
    }

    @Override
    public void save(DayWeather dayWeather) {
        Map<String, Object> data = new HashMap<>();
        data.put("day", dayWeather.getDia());
        data.put("weather", dayWeather.getClima());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME+dayWeather.getDia()).set(data);
    }
}
