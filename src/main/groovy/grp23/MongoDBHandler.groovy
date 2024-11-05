package grp23
@Grab(group='org.mongodb', module='mongodb-driver-sync', version='4.10.1')
import com.mongodb.MongoClientSettings
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import com.mongodb.client.MongoCollection
import org.bson.Document

class MongoDBHandler{
	def client
	MongoDatabase database

	// Constructor to initialize the MongoDB connection
	MongoDBHandler(String connectionString, String dbName) {
		client = MongoClients.create(connectionString)
		database = client.getDatabase(dbName)
		println "Connected to MongoDB database: $dbName"
	}

	// Method to create a collection
	void createCollection(String collectionName) {
		database.createCollection(collectionName)
		println "Collection created: $collectionName"
	}

	
	
	// Method to close the MongoDB connection
	void close() {
		client?.close()
		println "MongoDB connection closed"
	}
}
