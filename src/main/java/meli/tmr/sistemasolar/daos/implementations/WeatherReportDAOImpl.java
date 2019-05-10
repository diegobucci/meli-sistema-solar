package meli.tmr.sistemasolar.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import meli.tmr.sistemasolar.AppFirebase;
import meli.tmr.sistemasolar.daos.interfaces.WeatherReportDAO;
import meli.tmr.sistemasolar.models.WeatherReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class WeatherReportDAOImpl implements WeatherReportDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherReportDAOImpl.class);

    public static final String COLLECTION_NAME = "reports";
    public static final String DOCUMENT_NAME = "report";

    @Override
    public WeatherReport get() {
        LOGGER.info("Obtener reporte de la base de datos");
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
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        } catch (ExecutionException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return weatherReport;
    }

    @Override
    public void save(WeatherReport weatherReport) {
        LOGGER.info("Almacenar reporte en la base de datos: ", weatherReport);
        Map<String, Object> data = new HashMap<>();
        data.put("dias-de-sequia", weatherReport.getNumberOfDroughtDays());
        data.put("dias-optimos", weatherReport.getNumberOfOptimalDays());
        data.put("dias-lluviosos", weatherReport.getNumberOfRainyDays());
        data.put("dia-lluvia-maxima", weatherReport.getDayOfGreatestRain());
        data.put("maximo-perimetro-lluvia", weatherReport.getMaxPerimeterRain());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME).set(data);
    }
}
