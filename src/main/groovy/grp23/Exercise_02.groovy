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
//def selectionData = [:]
//selectionData = jsonData[0]
//println "DATA SELECTION"
//println "First student data"
//println JsonOutput.prettyPrint(JsonOutput.toJson(selectionData))

// Using groovy collections
def selectedData = jsonData.first()
println "DATA SELECTION"
println "First student data"
println JsonOutput.prettyPrint(JsonOutput.toJson(selectedData))

/**
 * Data Projection:
 * 1. In data projection, we are supposed to select only a specific fields (columns) to be displayed rather than all the fields as used in selection
 * 2. We will print only the gender and math score of the first data
 */

// Traditional approach
def myData = jsonData[0]
def projectionData = [gender: myData.gender, mathScore: myData['math score']]
println "DATA PROJECTION"
println "First student data"
println projectionData
println ""


/**
 * Data Filtering:
 * 1. In data filtering, we filter out the data that meets certain criteria
 * 2. For example, lets filter out data such that we only select the females that have math score > 95 with a bachelor's degree
 */

// Traditional Approach without using groovy collections
//def filteredData = []
//
//for(int i = 0; i < jsonData.size(); i++) {
//	// Each iteration will be a map
//	// Creating an empty map
//	def studentData = [:]
//	studentData = jsonData[i];
//	if(studentData['math score'] > 95 && studentData['gender'] == "female" && studentData['parental level of education'] == "bachelor's degree") {
//		// Applying Data Projection as well
//		def currData = [gender: studentData.gender, mathScore: studentData['math score'], educationLevel: studentData['parental level of education']]
//		filteredData << currData
//	}
//}
//println "DATA FILTERING"
//filteredData.eachWithIndex { student, index ->
//	println "Student ${index + 1}"
//	println JsonOutput.prettyPrint(JsonOutput.toJson(student))
//}
//println ""

// Using groovy collections
def filteredData = jsonData.findAll { studentData ->
	studentData['math score'] > 95 &&
	studentData['gender'] == "female" &&
	studentData['parental level of education'] == "bachelor's degree"
}.collect { studentData ->
	// Applying Data Projection
	[gender: studentData.gender, mathScore: studentData['math score'], educationLevel: studentData['parental level of education']]
}

println "DATA FILTERING"
filteredData.eachWithIndex { student, index ->
	println "Student ${index + 1}"
	println JsonOutput.prettyPrint(JsonOutput.toJson(student))
}
println ""

/**
 * Data Combination:
 * 1. Aggregating data to summarize the data to produce statistics
 * 2. For example, find the average math score of all the students
 */

def mathScores = jsonData.collect {it['math score']}
def totMathScores = mathScores.sum();

def avgScore = totMathScores / mathScores.size()
println "DATA COMBINATION"
println "Average Math Score -> $avgScore"
println ""

/**
 * Data Grouping:
 * 1. Grouping the data into categories
 * 2. For example, find the average math score of all the students and group them by males and females
 */

def scoresGender = jsonData.groupBy { it['gender'] }
println "DATA GROUPING"
// Calculate average math score for each gender
scoresGender.each { gender, students ->
	def mathsScores = students.collect {it['math score']}
	def totalScore = mathsScores.sum()
	def averageScore = totalScore / students.size()
	println "Average Math Score for ${gender}: ${averageScore}"
}
println ""
