package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppFirebase;
import meli.tmr.sistemasolar.AppInitializator;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.models.DayWeather;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class DayWeatherDAOImpl implements DayWeatherDAO  {

    public static final String COLLECTION_NAME = "weathers";
    public static final String DOCUMENT_NAME = "weather";

    @Override
    public DayWeather getByDay(Integer day) {
        DayWeather dayWeather = new DayWeather();
        try {
            CollectionReference climas = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = climas.whereEqualTo("day", day);
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
        DocumentReference docRef = AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME+dayWeather.getDia());
        Map<String, Object> data = new HashMap<>();
        data.put("day", dayWeather.getDia());
        data.put("weather", dayWeather.getClima());
        docRef.set(data);
    }
}
