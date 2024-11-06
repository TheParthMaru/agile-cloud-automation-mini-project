# Analysis of Students Performance in Exams

## Dataset

- [Kaggle Link](https://www.kaggle.com/datasets/spscientist/students-performance-in-exams)
- [Download CSV](https://github.com/TheParthMaru/agile-cloud-automation-mini-project/blob/main/StudentsPerformance.csv)
- We converted the dataset for the ease of use with groovy.

## Task

### Pre-requisite

- A mongodb cluster was created using mongodb atlas.
- We connected to the cluster with the help of MongoDB Compass which is a GUI tool for querying MongoDB.
- We used the connection string to connect to the cluster.
- Then, we created a database called "student_db".
- We added one collection name "student_performace".
- We used the "Import JSON" functionality provided by the Mongo Compass to load the dataset.

### Exercise 1 - Performing NoSQL queries

- Write NoSQL queries to perform the following operations:
  - Data selection
  - Data projection
  - Data filtering
  - Data combination or data grouping
- [Solution](https://github.com/TheParthMaru/agile-cloud-automation-mini-project/blob/main/exercise_walkthroughs/Exercise%201.pdf)

### Exercise 2 - Querying using groovy collection

- Load the JSON file and parse it using JSON slurper.
- Perform the operations such as data selection, projection, combination and grouping.
- [Solution](https://github.com/TheParthMaru/agile-cloud-automation-mini-project/blob/main/exercise_walkthroughs/Exercise_2.md)

### Exercise 3 - Creating pipelines in mongodb and groovy

- We need to create mongodb connection to the cluster and import the dataset.
- Then we need to perform aggregation pipelines on it.
- [Solution](https://github.com/TheParthMaru/agile-cloud-automation-mini-project/blob/main/exercise_walkthroughs/Exercise_3.md)

### Exercise 4 - Collection based solution vs cloud based solution

- Understand which method of solution is more effiecient and when to be used.

### Exercise 5 - Analyzing the need of scalability

- Scalability can be improved by implenting pagination.
- Apart from scalability, we can also perform query optimization using indexing.
- [Solution](https://github.com/TheParthMaru/agile-cloud-automation-mini-project/blob/main/exercise_walkthroughs/Exercise%205.pdf)
