package fr.mirage.api.data.mongo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import fr.mirage.api.data.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface MongoManager extends Database {

    Logger log = LoggerFactory.getLogger(MongoManager.class);

    MongoCredentials getCredentials();

    MongoClient getClient();
    MongoDatabase getDatabase();

}