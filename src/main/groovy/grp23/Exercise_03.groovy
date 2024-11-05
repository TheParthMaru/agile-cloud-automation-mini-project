package grp23

@Grab(group='org.mongodb', module='mongodb-driver-sync', version='4.10.1')
import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.bson.Document
import static com.mongodb.client.model.Filters.*
import static com.mongodb.client.model.Aggregates.*
import static com.mongodb.client.model.Accumulators.*
import static com.mongodb.client.model.Sorts.*
import static com.mongodb.client.model.Projections.*
import groovy.json.JsonOutput

// Load credentials from src/main/resources/mongodb.properties
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}

def connctionString = "mongodb+srv://$properties.username:$properties.password@cluster0.tmfox.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
def mongoClient = MongoClients.create(connctionString)
def db = mongoClient.getDatabase(properties.database)
def collection = db.getCollection("student_performance")

// Data selection and projection
def resultList1 = collection.aggregate([
	project(fields(include('gender', 'math score', 'reading score', 'writing score'),
		excludeId()
	)),
	limit(1)
])
println "Pipeline for data selection and projection"
println JsonOutput.prettyPrint(JsonOutput.toJson(resultList1))


// Aggregation pipeline where we use data filtering and projection
def resultList2 = collection.aggregate([
    group('$gender',               
        avg('avgMathScore', '$math score'),      
        avg('avgReadingScore', '$reading score'), 
        avg('avgWritingScore', '$writing score')
    ),
    project(fields(
        include('avgMathScore', 'avgReadingScore', 'avgWritingScore'), // Include averages
        include('_id') // Include the _id field (which contains the gender)
    )),
    sort(descending('avgMathScore')) // Sort by average math score in descending order
])

// Rename the _id field to gender in the result because during grouping, gender was considered and the field value is lost
// So we rename id to gender
def finalResults = resultList2.map { doc ->
    doc.put('gender', doc.remove('_id'))
    return doc
}

println "Pipeline for data combination and grouping"
println JsonOutput.prettyPrint(JsonOutput.toJson(finalResults))