package grp23
import groovy.json.JsonOutput
import groovy.json.JsonSlurper

// Use StudentsPerformance.json file as a dataset
// Create query for each Selection, Projection, Filtering and Grouping
// Use groovy collections to perform the same things as you would be able to do with mongodb.

/**
 * Steps:
 * 1. Load the json dataset
 * 2. Implement your query in groovy (Using collection methods like find(), collect(), groupBy(), etc) 
 */

def datasetFile = new File("src/main/resources/StudentsPerformance.json")
def jsonData = 	new JsonSlurper().parse(datasetFile)

/**
 *	Data Selection:
 *	1. In the dataset file, the data is structured in the json format as an array of maps.
 *	2. After parsing, the structure will be like List<Map<String, Object>> => [ {}, {}, {}, ... ]
 *	3. In data selection, we need to either select all the data or select a single data.
 */

// Traditional approach
def selectionData = [:]
selectionData = jsonData[0]
println "--- DATA SELECTION WITHOUT GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(selectionData))
println "\n"

// Using groovy collections
def selectedData = jsonData.first()
println "--- DATA SELECTION WITH GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(selectedData))
println "\n"

/**
 * Data Projection:
 * 1. In data projection, we are supposed to select only a specific fields (columns) to be displayed rather than all the fields as used in selection
 * 2. We will print only the gender and math score of the first data
 */

// Traditional approach
def myData = jsonData[0]
def projectionData = [gender: myData.gender, mathScore: myData['math score']]
println "DATA PROJECTION WITHOUT GROOVY COLLECTION"
println "First student data"
println projectionData
println "\n"


// Using Groovy collections
def projectedData = jsonData.first().subMap(['gender', 'math score'])
println "--- DATA PROJECTION WITH GROOVY COLLECTION -- \n"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(projectedData))
println "\n"


/**
 * Data Filtering:
 * 1. In data filtering, we filter out the data that meets certain criteria
 * 2. For example, lets filter out data such that we only select the females that have math score > 95 with a bachelor's degree
 */

// Traditional Approach without using Groovy collections
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

// Using groovy collections
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

/**
 * Data Combination:
 * 1. Aggregating data to summarize the data to produce statistics
 * 2. For example, find the average math score of all the students
 */

// Using traditional approach
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

// Using groovy collection
def mathScores = jsonData.collect {it['math score']}
def totMathScores = mathScores.sum();

def avgScore = totMathScores / mathScores.size()
println "DATA COMBINATION WITH GROOVY COLLECTION"
println "Average Math Score -> $avgScore"
println ""

/**
 * Data Grouping:
 * 1. Grouping the data into categories
 * 2. For example, find the average math score of all the students and group them by males and females
 */

// Traditional approach
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

// Calculate average math score for each gender
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


// Using groovy collection
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
