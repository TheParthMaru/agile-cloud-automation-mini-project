package grp23

@Grab(group='org.mongodb', module='mongodb-driver-sync', version='4.10.1')
import com.mongodb.client.MongoClients

// Load credentials from src/main/resources/mongodb.properties
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

def connctionString = "mongodb+srv://$properties.username:$properties.password@cluster0.tmfox.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
def mongoClient = new MongoDBHandler(connctionString, properties.database)
def db = mongoClient.getDatabase(properties.database)
def collection = db.getCollection("student_performace")

// DATA SELECTION PIPELINE
//def pipeline_1 = 