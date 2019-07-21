# mebank coding challenge


# Tech-Stack Used to build application:

1. Java 8
2. JUnit : Unit Test Framework 
3. Maven : Build integration


# Application Assumptions (As per problem statement)
Input file and records are all in a valid format
Transaction are recorded in order

# How to run this application
* Clone the git repo using following command

```git clone https://github.com/jyotiverma8257/mebank.git```
	
   This will create a folder mebank in your current working directory.
* Execute command:

``` cd mebank ```
* Compile code using following command

``` mvn clean install ```
* Run the application using following command

```  java -jar ./target/TransactionApp-0.0.1-SNAPSHOT-jar-with-dependencies.jar ./src/main/resources/Transaction.csv ACC334455 "20/10/2016 12:47:55" "20/10/2019 12:47:55"```

This command has following format:

```java -jar <jar_name> <csv_path> <accountId> <\"fromDate\"> <\"toDate\">```
	
* Now application is started.

## Author
* **Jyoti Verma**

