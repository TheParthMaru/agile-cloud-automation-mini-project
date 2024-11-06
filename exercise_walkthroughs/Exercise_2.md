# Exercise 2 detailed walkthrough

- The aim is to load the JSON dataset and extract trends from the dataset with the help of groovy collection.

## Loading the dataset

- The dataset has been placed in the resources folder of the project.
- We are supposed to load it using the file operation as follows:

```groovy
def datasetFile = new File("src/main/resources/StudentsPerformance.json")
```

- We had to parsed the file in a valid json format using JSON Slurper as follows:

```groovy
def jsonData = 	new JsonSlurper().parse(datasetFile)
```

## Data selection

- In the dataset file, the data is structured in the json format as an array of maps.
- After parsing, the structure will be like List<Map<String, Object>> => [ {}, {}, {}, ... ]
- In data selection, we need to either select all the data or select a single data.

### Traditional approach without using groovy collections

```groovy
def selectionData = [:]
selectionData = jsonData[0]
println "--- DATA SELECTION WITHOUT GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(selectionData))
println "\n"
```

### Solving using groovy collections

```groovy
def selectedData = jsonData.first()
println "--- DATA SELECTION WITH GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(selectedData))
println "\n"
```

## Data projection

- In data projection, we are supposed to select only a specific fields (columns) to be displayed rather than all the fields as used in selection.
- We will print only the gender and math score of the first data.

### Traditional approach without groovy collections

```groovy
def myData = jsonData[0]
def projectionData = [gender: myData.gender, mathScore: myData['math score']]
println "DATA PROJECTION WITHOUT GROOVY COLLECTION"
println "First student data"
println projectionData
println "\n"
```

### Solving using groovy collections

```groovy
def projectedData = jsonData.first().subMap(['gender', 'math score'])
println "--- DATA PROJECTION WITH GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(projectedData))
println "\n"
```

## Data filtering

- In data filtering, we filter out the data that meets certain criteria.
- For example, lets filter out data such that we only select the females that have math score > 95 with a bachelor's degree.

### Traditional approach without using groovy collections

```groovy
def filteredData1 = []

for(int i = 0; i < jsonData.size(); i++) {
	// Each iteration will be a map
	// Creating an empty map
	def studentData = [:]
	studentData = jsonData[i];
	if(studentData['math score'] > 95 && studentData['gender'] == "female" && studentData['parental level of education'] == "bachelor's degree") {
		// Applying Data Projection as well
		def currData = [gender: studentData.gender, mathScore: studentData['math score'], educationLevel: studentData['parental level of education']]
		filteredData1 << currData
	}
}
println "DATA FILTERING WITHOUT GROOVY COLLECTION"
filteredData1.eachWithIndex { student, index ->
	println "Student ${index + 1}"
	println JsonOutput.prettyPrint(JsonOutput.toJson(student))
}
println "\n"
```

### Using groovy collections

```groovy
def filteredData2 = jsonData.findAll { studentData ->
	studentData['math score'] > 95 &&
	studentData['gender'] == "female" &&
	studentData['parental level of education'] == "bachelor's degree"
}.collect { studentData ->
	// Applying Data Projection
	[gender: studentData.gender, mathScore: studentData['math score'], educationLevel: studentData['parental level of education']]
}

println "DATA FILTERING WITH GROOVY COLLECTION"
filteredData2.eachWithIndex { student, index ->
	println "Student ${index + 1}"
	println JsonOutput.prettyPrint(JsonOutput.toJson(student))
}
println "\n"
```

## Data combination

- Aggregating data to summarize the data to produce statistics.
- For example, find the average math score of all the students

### Traditional approach without using groovy collections

```groovy
def totalMathScores = 0
def count = 0

for (int i = 0; i < jsonData.size(); i++) {
	def studentData = jsonData[i]
	totalMathScores += studentData['math score']
	count++
}

def averageScore = totalMathScores / count
println "DATA COMBINATION WITHOUT GROOVY COLLECTION"
println "Average Math Score -> $averageScore"
println ""
```

### Using groovy collection

```groovy
def mathScores = jsonData.collect {it['math score']}
def totMathScores = mathScores.sum();

def avgScore = totMathScores / mathScores.size()
println "DATA COMBINATION WITH GROOVY COLLECTION"
println "Average Math Score -> $avgScore"
println ""
```

## Data grouping

- Grouping the data into categories.
- For example, find the average math score of all the students and group them by males and females.

### Traditional approach without groovy collections

```groovy
def maleScores = []
def femaleScores = []

for (int i = 0; i < jsonData.size(); i++) {
	def studentData = jsonData[i]
	def gender = studentData['gender']
	def mathScore = studentData['math score']

	if (gender == "male") {
		maleScores.add(mathScore)
	} else if (gender == "female") {
		femaleScores.add(mathScore)
	}
}

def maleTotal = 0
maleScores.each { maleTotal += it }
def maleAvg = maleScores.size() > 0 ? maleTotal / maleScores.size() : 0

def femaleTotal = 0
femaleScores.each { femaleTotal += it }
def femaleAvg = femaleScores.size() > 0 ? femaleTotal / femaleScores.size() : 0

println "DATA GROUPING WITHOUT GROOVY COLLECTION"
println "Average Math Score for female: ${femaleAvg}"
println "Average Math Score for male: ${maleAvg}"
println ""
```

### Using groovy collections

```groovy
def scoresGender = jsonData.groupBy { it['gender'] }
println "DATA GROUPING WITH GROOVY COLLECTION"
// Calculate average math score for each gender
scoresGender.each { gender, students ->
	def mathsScores = students.collect {it['math score']}
	def totalScore = mathsScores.sum()
	def avgScore1 = totalScore / students.size()
	println "Average Math Score for ${gender}: ${avgScore1}"
}
println ""
```
