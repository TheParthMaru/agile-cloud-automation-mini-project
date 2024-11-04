package grp23;

// Imports
import com.mongodb.client.MongoClients


// Load credentials from src/main/resources/mongodb.properties
// This file contains all the neccessary authentication data such as credentials, server name and database name
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

// Create connection: replace <YOUR-CONNECTION-STRING> with the corresponding part of your connection string
def username = properties.username
def password = properties.password
def connectionString = "mongodb+srv://$username:$password@cluster0.tmfox.mongodb.net/"
def mongoClient = MongoClients.create(connectionString)

def db = mongoClient.getDatabase(properties.database)
def collection = db.getCollection("student_performance")

// Pipeline 1
def query = collection.find().first();
query.each { println it }