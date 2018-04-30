package admin.modelo;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class ConexionMongo {
    private static final ConexionMongo INSTANCE = new ConexionMongo();
    private static MongoDatabase database;
    
    private ConexionMongo(){
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        database = mongoClient.getDatabase("autorizacion");
    }
    
    public static ConexionMongo getInstance(){
        return INSTANCE;
    }
    
    public MongoDatabase getDatabase(){
        return database;
    }
    
}
