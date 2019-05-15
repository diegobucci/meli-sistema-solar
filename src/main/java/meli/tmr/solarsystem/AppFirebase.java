package meli.tmr.solarsystem;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class AppFirebase {
    private static final Logger LOGGER = LoggerFactory.getLogger(AppFirebase.class);
    private static Firestore db;

    public void initBD() throws IOException {
        LOGGER.info("Inicializando conexión con la base de datos");
        InputStream serviceAccount = getClass().getClassLoader().getResourceAsStream("sistema-solar-240720-64fa05b0d108.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .setProjectId("sistema-solar-240720")
                .build();
        if(FirebaseApp.getApps().size() == 0) { // This fix error "Firebase App named '[DEFAULT]' already exists" at <mvn install>
            FirebaseApp.initializeApp(options);
        }
        db = FirestoreClient.getFirestore();
        LOGGER.info("Conexión exitosa");
    }

    public static Firestore getDB(){
        return db;
    }


}
