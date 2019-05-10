package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppInitializator;
import meli.tmr.sistemasolar.daos.interfaces.DayWeatherDAO;
import meli.tmr.sistemasolar.models.DayWeather;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class DayWeatherDAOImpl implements DayWeatherDAO  {

    @Override
    public DayWeather getByDay(Integer day) {
        DayWeather dayWeather = new DayWeather();
        try {
            CollectionReference climas = AppInitializator.getDB().collection("climas");
            Query query = climas.whereEqualTo("dia", day);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) { // Debería haber un único valor
                dayWeather = new DayWeather(Integer.parseInt(document.get("dia").toString()), document.get("clima").toString());
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
        DocumentReference docRef = AppInitializator.getDB().collection("climas").document("dia-"+dayWeather.getDia());
        Map<String, Object> data = new HashMap<>();
        data.put("dia", dayWeather.getClima());
        data.put("clima", dayWeather.getClima());
        docRef.set(data);
    }
}
