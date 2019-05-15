package meli.tmr.solarsystem.daos.implementations;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import meli.tmr.solarsystem.AppFirebase;
import meli.tmr.solarsystem.daos.interfaces.WeatherReportDAO;
import meli.tmr.solarsystem.models.WeatherReport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Component
public class WeatherReportDAOImpl implements WeatherReportDAO {
    private static final Logger LOGGER = LoggerFactory.getLogger(WeatherReportDAOImpl.class);

    private static final String COLLECTION_NAME = "reports";
    private static final String DOCUMENT_NAME = "report";

    @Override
    public WeatherReport get() {
        LOGGER.info("Obtener reporte de la base de datos");
        WeatherReport weatherReport = new WeatherReport();
        try {
            CollectionReference reports = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = reports.limit(20);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            querySnapshot.get().getDocuments().forEach(document -> { // Debería haber un único valor
                weatherReport.setDiasDeSequia(Integer.parseInt(Objects.requireNonNull(document.get("dias-de-sequia")).toString()));
                weatherReport.setDiasOptimos(Integer.parseInt(Objects.requireNonNull(document.get("dias-optimos")).toString()));
                weatherReport.setDiasDeLluvia(Integer.parseInt(Objects.requireNonNull(document.get("dias-lluviosos")).toString()));
                weatherReport.setDiaDeMayorLluvia(Integer.parseInt(Objects.requireNonNull(document.get("dia-lluvia-maxima")).toString()));
                weatherReport.setMaximoPerimetroDiaDeLluvia(Double.parseDouble(Objects.requireNonNull(document.get("maximo-perimetro-lluvia")).toString()));
            });
        } catch (InterruptedException | ExecutionException e) {
            LOGGER.error(e.getMessage());
            e.printStackTrace();
        }
        return weatherReport;
    }

    @Override
    public void save(WeatherReport weatherReport) {
        LOGGER.info("Almacenar reporte en la base de datos: " + weatherReport);
        Map<String, Object> data = new HashMap<>();
        data.put("dias-de-sequia", weatherReport.getDiasDeSequia());
        data.put("dias-optimos", weatherReport.getDiasOptimos());
        data.put("dias-lluviosos", weatherReport.getDiasDeLluvia());
        data.put("dia-lluvia-maxima", weatherReport.getDiaDeMayorLluvia());
        data.put("maximo-perimetro-lluvia", weatherReport.getMaximoPerimetroDiaDeLluvia());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME).set(data);
    }
}
