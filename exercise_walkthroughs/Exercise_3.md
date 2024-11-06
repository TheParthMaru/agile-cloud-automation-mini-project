# Exercise 3 detailed walkthrough

- The aim is to write mongodb pipelines to perform data selection, projection, combination and grouping.

## Creating properties file

- Create a `.properties` file to save the important credentials such as `username`, `password`, `database name` and `server name`.
- We have added this file to `src/main/resources` folder.

```
username=grp23
password=grp23
server=tmfox
database=student_db
```

## Loading the properties file

```groovy
def properties = new Properties()
def propertiesFile = new File('src/main/resources/mongodb.properties')
propertiesFile.withInputStream {
	properties.load(it)
}
```

## Setting up mongodb client

```groovy
def connctionString = "mongodb+srv://$properties.username:$properties.password@cluster0.tmfox.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
def mongoClient = MongoClients.create(connctionString)
def db = mongoClient.getDatabase(properties.database)
def collection = db.getCollection("student_performance")
```

## Creating data selection and projection pipeline

```groovy
def resultList1 = collection.aggregate([
	project(fields(include('gender', 'math score', 'reading score', 'writing score'),
		excludeId()
	)),
	limit(1)
])
println "Pipeline for data selection and projection"
println JsonOutput.prettyPrint(JsonOutput.toJson(resultList1))
```

## Creating data combination and grouping pipeline

```groovy
def resultList2 = collection.aggregate([
    group('$gender',
        avg('avgMathScore', '$math score'),
        avg('avgReadingScore', '$reading score'),
        avg('avgWritingScore', '$writing score')
    ),
    project(fields(
        include('avgMathScore', 'avgReadingScore', 'avgWritingScore'),
        include('_id')
    )),
    sort(descending('avgMathScore'))
])

// Rename the _id field to gender in the result because during grouping,
// gender was considered and the field value is lost
// So we rename id to gender
def finalResults = resultList2.map { doc ->
    doc.put('gender', doc.remove('_id'))
    return doc
}

println "Pipeline for data combination and grouping"
println JsonOutput.prettyPrint(JsonOutput.toJson(finalResults))
```
