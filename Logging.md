# Logging #

Since 1.2 `dummyjdbc` uses logback (http://logback.qos.ch/) and slf4j (http://www.slf4j.org/) for logging. AspectJ is used to easily log EVERY call on public methods. The library logs as following:
  * `TRACE`: All method calls, parameters and return values
  * `INFO`: Tables without a CSV file
  * `ERROR`: Errors like IOExceptions

It is recommended to start with `TRACE` level to understand how the library works. Then you can switch to `INFO` or `ERROR`. To configure the logging add a `logback.xml` to your classpath (`res`-folder) (or extend your existing one).

## Example configuration of `logback.xml` with level=`TRACE` ##
```
<configuration>
   <appender name="STDOUT" class="ch.qos.logback.core.ConsoleAppender">
      <encoder>
         <pattern>%d{HH:mm:ss.SSS} [%-5level] %logger{36} - %msg%n</pattern>
      </encoder>
   </appender>

   <logger name="com.googlecode.dummyjdbc" level="TRACE" />

   <root level="debug">
      <appender-ref ref="STDOUT" />
   </root>
</configuration>
```

## Example of output with level=`TRACE` ##
```
10:43:26.789 [main] TRACE c.g.dummyjdbc.DummyJdbcDriver - Call com.googlecode.dummyjdbc.DummyJdbcDriver.connect:
10:43:26.792 [main] TRACE c.g.dummyjdbc.DummyJdbcDriver - url: java.lang.String = DB_URL
10:43:26.792 [main] TRACE c.g.dummyjdbc.DummyJdbcDriver - info: java.util.Properties = {user=USER, password=PASS}
10:43:26.797 [main] TRACE c.g.dummyjdbc.DummyJdbcDriver - -> Returning: com.googlecode.dummyjdbc.connection.impl.DummyConnection@333903d0
10:43:26.797 [main] TRACE c.g.d.c.impl.DummyConnection - Call com.googlecode.dummyjdbc.connection.impl.DummyConnection.createStatement:
10:43:26.801 [main] TRACE c.g.d.c.impl.DummyConnection - -> Returning: com.googlecode.dummyjdbc.statement.impl.CsvStatement@2416cef9
10:43:26.802 [main] TRACE c.g.d.statement.impl.CsvStatement - Call com.googlecode.dummyjdbc.statement.impl.CsvStatement.executeQuery:
10:43:26.802 [main] TRACE c.g.d.statement.impl.CsvStatement - sql: java.lang.String = SELECT id, first, last, age FROM Employees
10:43:26.802 [main] INFO c.g.d.statement.impl.CsvStatement - No table definition found for 'Employees', using DummyResultSet.
10:43:26.813 [main] TRACE c.g.d.statement.impl.CsvStatement - -> Returning: com.googlecode.dummyjdbc.resultset.DummyResultSet@1009946e
10:43:26.813 [main] TRACE c.g.d.resultset.DummyResultSet - Call com.googlecode.dummyjdbc.resultset.DummyResultSet.getInt:
10:43:26.813 [main] TRACE c.g.d.resultset.DummyResultSet - columnLabel: java.lang.String = id
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - -> Returning: 0
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - Call com.googlecode.dummyjdbc.resultset.DummyResultSet.getString:
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - columnLabel: java.lang.String = first
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - -> Returning: null
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - Call com.googlecode.dummyjdbc.resultset.DummyResultSet.getString:
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - columnLabel: java.lang.String = last
10:43:26.814 [main] TRACE c.g.d.resultset.DummyResultSet - -> Returning: null
10:43:26.815 [main] TRACE c.g.d.resultset.DummyResultSet - Call com.googlecode.dummyjdbc.resultset.DummyResultSet.getInt:
10:43:26.815 [main] TRACE c.g.d.resultset.DummyResultSet - columnLabel: java.lang.String = age
10:43:26.815 [main] TRACE c.g.d.resultset.DummyResultSet - -> Returning: 0
```

## Example of output with level=`INFO` ##
```
10:37:11.050 [main] INFO c.g.d.statement.impl.CsvStatement - No table definition found for 'Employees', using DummyResultSet.
```

## For the curious, this is the class which produced the log output: ##
```
public class TestClass {
   public static void main(String[] args) throws ClassNotFoundException, SQLException {
      Class.forName("com.googlecode.dummyjdbc.DummyJdbcDriver");
      Connection connection = DriverManager.getConnection("DB_URL", "USER", "PASS");
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT id, first, last, age FROM Employees");
      int id = resultSet.getInt("id");
      String first = resultSet.getString("first");
      String last = resultSet.getString("last");
      int age = resultSet.getInt("age");
   }
}
```