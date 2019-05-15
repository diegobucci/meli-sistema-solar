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
    public static final String DIAS_DE_SEQUIA = "dias-de-sequia";
    public static final String DIAS_OPTIMOS = "dias-optimos";
    public static final String DIAS_LLUVIOSOS = "dias-lluviosos";
    public static final String DIA_LLUVIA_MAXIMA = "dia-lluvia-maxima";

    @Override
    public WeatherReport get() {
        LOGGER.info("Obtener reporte de la base de datos");
        WeatherReport weatherReport = new WeatherReport();
        try {
            CollectionReference reports = AppFirebase.getDB().collection(COLLECTION_NAME);
            Query query = reports.limit(20);
            ApiFuture<QuerySnapshot> querySnapshot = query.get();
            querySnapshot.get().getDocuments().forEach(document -> { // un único valor
                weatherReport.setDiasDeSequia(Integer.parseInt(Objects.requireNonNull(document.get(DIAS_DE_SEQUIA)).toString()));
                weatherReport.setDiasOptimos(Integer.parseInt(Objects.requireNonNull(document.get(DIAS_OPTIMOS)).toString()));
                weatherReport.setDiasDeLluvia(Integer.parseInt(Objects.requireNonNull(document.get(DIAS_LLUVIOSOS)).toString()));
                weatherReport.setDiaDeMayorLluvia(Integer.parseInt(Objects.requireNonNull(document.get(DIA_LLUVIA_MAXIMA)).toString()));
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
        data.put(DIAS_DE_SEQUIA, weatherReport.getDiasDeSequia());
        data.put(DIAS_OPTIMOS, weatherReport.getDiasOptimos());
        data.put(DIAS_LLUVIOSOS, weatherReport.getDiasDeLluvia());
        data.put(DIA_LLUVIA_MAXIMA, weatherReport.getDiaDeMayorLluvia());
        AppFirebase.getDB().collection(COLLECTION_NAME).document(DOCUMENT_NAME).set(data);
    }
}
