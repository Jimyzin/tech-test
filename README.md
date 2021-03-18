## tech-test

# Problem Statement

Please implement a simple REST API in spring boot that accepts a list of Names and Postcodes in the HTTP request body, and persist the data in a H2 database. 
The API should also have an endpoint that receives postcode range and returns in the response body the list of names belonging to that postcode range, sorted alphabetically as well as the total number of characters of all names combined.
The implementation should use Java streams in some way. 
We will be specifically looking for clean, well-structured code.  There should be at least one unit test implemented.

# Assumptions
- Due to the use of SimpleJpaRepository implementation of CrudRespository interface, duplicate data entries (with duplicate key values) are merged to existing data. No duplicate key values are recreated or treated as errors.
- Application database is in-memory. That means, everytime the application is restarted, the old/previous data is lost.
- If the requested data is not found in the database, it returns an empty list and does not throw an exception.
- Postcodes cannot be negative or zero.
- Starting postcode of a range must always be lesser than the ending postcode.
- Application and JUnit test cases run on different H2 database instances. Test database ceases to exist after completing the running of test cases.
- Java 11 is used to build and test this application.

# Running the application
Just like any SpringBoot application, this application can be run by executing the `main` methd in `com.powerledger.techtest` package after properly sorting out its dependencies.

# Manual test results
Manual test was conducted using Postman and test evidence is included in `manual_test_snapshots` folder in the source code.

# Coverage
Test cases cover 100% of classes and 97% of lines. The evidence is in `coverage_snapshot` folder in the source code.
