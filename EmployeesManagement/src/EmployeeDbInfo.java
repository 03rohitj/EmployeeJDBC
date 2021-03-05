
//EmployeeDbInfo class cannot be instantiated because it is declared final.
//This class provides constants for Database operations.

public final class EmployeeDbInfo {

   //Constants for Database Operations. They are public static because they are required in other classes also
   
   public static final String TABLE_NAME = "emp";   //Table(Schema) name

   //Following are the constants for database column names
   public static final String SQL_EMPID = "empid";  //Employee Id 
   public static final String SQL_NAME = "ename";   //Employee Full Name
   public static final String SQL_DEPT = "edept";   //Employee Department
   public static final String SQL_GENDER = "egender";  //Employee Gender(M, F or O[ther])
   public static final String SQL_SALARY = "esalary";  //Employee Salary per annum

   //Constants require for registering JDBC driver and establishing Database connection
   private static final String DB_NAME = "EmployeesManagement";  //Database name
   private static final String SQL_USERNAME= "root";     
   private static final String SQL_PASSWORD= "Rohit@1997";
   
   //SQL URLs to register driver and establish connection
   public static final String JDBC_DRIVER_URL = "com.mysql.cj.jdbc.Driver";
   public static final String JDBC_CONNECTION_URL = "jdbc:mysql://localhost/"+DB_NAME+"?" +
   "user="+SQL_USERNAME+"&password="+SQL_PASSWORD;

   //SQL Query to insert a record
   public static final String SQL_INSERT_QUERY = "INSERT INTO "
         +TABLE_NAME+" ("
         +SQL_NAME+","
         +SQL_DEPT+","
         +SQL_GENDER+","
         +SQL_SALARY+") values (?,?,?,?)";

   //SQL Query to update a record
   public static final String SQL_UPDATE_QUERY = "update "+TABLE_NAME+" SET "
         +SQL_NAME+"=?,"
         +SQL_DEPT+"=?,"
         +SQL_GENDER+"=?,"
         +SQL_SALARY+"=? "
         +"WHERE "
         +SQL_EMPID+"=?";

   //SQL select query to fetch all Employee IDs
   public static final String SQL_GET_ALL_ID_QUERY = "select "+SQL_EMPID+" from "+TABLE_NAME;

   //SQL select query to fetch all the employee records along with details
   public static final String SQL_DISPLAY_QUERY = "SELECT * from "+TABLE_NAME;

   //SQL Query to delete an employee
   public static final String SQL_DELETE_QUERY = "delete from "+TABLE_NAME+" where "+SQL_EMPID+"=?";
}
