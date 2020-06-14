package me.devwckd.prestigerankup.database;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.Getter;
import org.bson.Document;
import org.bson.UuidRepresentation;
import org.bson.codecs.UuidCodec;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

/*
 * author: SaiintBrisson
 */
public class MongoDataProvider {

	private final String database;

	@Getter
	private final MongoClient client;
	
	private final CodecRegistry codec;
	
	public MongoDataProvider(
		String host,
		Integer port,
		boolean srv,
		String user,
		String password,
		String database,
		Level loggerLevel
	) {

		this.database = database;

		if (host == null) {
			throw new NullPointerException("Host should not be null");
		}
		
		StringBuilder string = new StringBuilder("mongodb" + (srv ? "+srv" : "") + "://");
		
		if (user != null && password != null && database != null) {
			string.append(user).append(":").append(password).append("@");
		}
		
		string.append(host);
		
		if (port != null && !srv) {
			string.append(":").append(port);
		}
		
		if (user != null && password != null && database != null) {
			string.append("/").append(database);
		}
		
		CodecRegistry pojoCodecRegistry = fromRegistries(
				MongoClientSettings.getDefaultCodecRegistry(),
				CodecRegistries.fromProviders(
						PojoCodecProvider.builder()
								.automatic(true)
								.build()
				)
		);
		
		MongoClientSettings settings = MongoClientSettings.builder()
			.codecRegistry(pojoCodecRegistry)
			.uuidRepresentation(UuidRepresentation.STANDARD)
			.applyConnectionString(new ConnectionString(string.toString()))
			.build();
		
		this.codec = pojoCodecRegistry;
		
		if (loggerLevel != null) {
			Logger.getLogger("org.mongodb.driver").setLevel(loggerLevel);
		}
		
		client = MongoClients.create(settings);
	}
	
	
	public void connect() throws Exception {
		if (client == null)
			throw new NullPointerException("Could not instantiate MongoClient");
		
		client.startSession();
	}
	
	public MongoDatabase getDatabase(String database) {
		return client.getDatabase(database);
	}
	
	public MongoCollection<Document> getCollection(String database, String collection) {
		return getDatabase(database).getCollection(collection);
	}
	
	public <T> MongoCollection<T> getCollection(String database, String collection, Class<T> clazz) {
		MongoDatabase db = getDatabase(database);
		return db.getCollection(collection, clazz).withCodecRegistry(codec);
	}

	public <T> MongoCollection<T> getCollection(String collection, Class<T> clazz) {
		return getCollection(database, collection, clazz);
	}

}