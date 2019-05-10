package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppFirebase;
import meli.tmr.sistemasolar.AppInitializator;
import meli.tmr.sistemasolar.daos.interfaces.WeatherReportDAO;
import meli.tmr.sistemasolar.models.WeatherReport;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WeatherReportDAOImpl implements WeatherReportDAO {

    public static final String COLLECTION_NAME = "reports";
    public static final String DOCUMENT_NAME = "report";

    @Override
    public WeatherReport get() {
        WeatherReport weatherReport = new WeatherReport();
        try {
            CollectionReference reports = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = reports.limit(20);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            for (DocumentSnapshot document : querySnapshot.get().getDocuments()) { // Debería haber un único valor
                weatherReport.setNumberOfDroughtDays(Integer.parseInt(document.get("dias-de-sequia").toString()));
                weatherReport.setNumberOfOptimalDays(Integer.parseInt(document.get("dias-optimos").toString()));
                weatherReport.setNumberOfRainyDays(Integer.parseInt(document.get("dias-lluviosos").toString()));
                weatherReport.setDayOfGreatestRain(Integer.parseInt(document.get("dia-lluvia-maxima").toString()));
                weatherReport.setMaxPerimeterRain(Double.parseDouble(document.get("maximo-perimetro-lluvia").toString()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return weatherReport;
    }

    @Override
    public void save(WeatherReport weatherReport) {
        DocumentReference docRef = AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME);
        Map<String, Object> data = new HashMap<>();
        data.put("dias-de-sequia", weatherReport.getNumberOfDroughtDays());
        data.put("dias-optimos", weatherReport.getNumberOfOptimalDays());
        data.put("dias-lluviosos", weatherReport.getNumberOfRainyDays());
        data.put("dia-lluvia-maxima", weatherReport.getDayOfGreatestRain());
        data.put("maximo-perimetro-lluvia", weatherReport.getMaxPerimeterRain());
        docRef.set(data);
    }
}
