package fr.mirage.core.common.database.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import fr.mirage.api.data.mongo.MongoCredentials;
import fr.mirage.api.data.mongo.MongoManager;
import lombok.Getter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;

@Getter
public class MongoRepository implements MongoManager {

    private MongoClient client;
    private MongoDatabase database;

    private final MongoCredentials credentials;

    private boolean connected;

    public MongoRepository(MongoCredentials credentials) {
        // LOAD CREDENTIALS FROM JSON
        this.credentials = credentials;
        this.connected = false;
    }

    @Override
    public void connect() {
        try {
            this.client = MongoClients.create(credentials.url());
            this.database = client.getDatabase(credentials.database());
            this.connected = true;

            Configurator.setLevel("org.mongodb.driver", Level.WARN);
            log.info("Mongo client successfully connected");
        } catch (Exception exception) {
            log.error("Failed to connect to mongodb cause : ", exception);
        }
    }

    @Override
    public void disconnect() {
        this.connected = false;

        client.close();
    }

}