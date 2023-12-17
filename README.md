Project Name: JDBC-Database-Operations

Description:
Welcome to the JDBC-Database-Operations repository! This project serves as a practical exploration of Java Database Connectivity (JDBC), showcasing various database operations. The project is structured into different packages, each addressing specific tasks related to querying, updating, and executing SQL commands.

Project Structure:

ca.myjava.query:

StaticSQLQuery.java: This class utilizes static SQL (Statement or PreparedStatement) to query the Country table in your Oracle database. It retrieves and displays information about countries with LifeExpectancy values within a specified range.

PreparedStatementQuery.java: Similar to StaticSQLQuery, this class also queries the Country table but uses PreparedStatement for the task.

ca.myjava.unknown:

AnySQL.java: This class allows users to input any SQL command from the console. The program then executes the provided SQL command using JDBC API and displays the query results or information on the console.
ca.myjava.update:

InsertStatement.java: This class executes an SQL insert statement on the Country table, adding new records.

UpdateStatement.java: This class executes an SQL update statement on the Country table, modifying existing records.

DeleteStatement.java: This class executes an SQL delete statement on the Country table, removing specific records.

Instructions:

Clone this repository to your local machine using the following command:

bash
Copy code
git clone https://github.com/your-username/JDBC-Database-Operations.git
Import the project into Eclipse IDE.

Ensure that you have the Oracle JDBC library (ojdbc14.jar) added to the project's library.

Explore and run the various Java classes within the respective packages to perform database operations.

Note:
Modify the JDBC connection details in the Java classes according to your Oracle database configuration before running the examples. Exercise caution while using the AnySQL class to input SQL commands from the console.

Feel free to contribute, provide feedback, or use this repository as a practical resource for JDBC database operations. Happy coding!
