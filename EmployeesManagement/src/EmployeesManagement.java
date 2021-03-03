import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement; 
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class to print log messages on console
class LOGGER{
   private final static Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

   //method accesible throughout the program to log messages
   public static void log(Level level, String message){
      logger.log(level,message);
   }
}



//Class to hold details of an employee
class Employee{
   private String eName,eDept;   //Employee name and deptartment
   private char eGender;   //Employee Gender : M, F or O
   private long eSalary;   //Employee Salary
   
   
   //Copy constructor
   Employee(Employee emp){
      this.eName = emp.eName;
      this.eDept = eDept;
      this.eGender = eGender;
      this.eSalary = eSalary;
      
   }


   //Parameterized Constructor
   Employee(String eName, String eDept, char eGender, long eSalary){
      this.eName = eName;
      this.eDept = eDept;
      this.eGender = eGender;
      this.eSalary = eSalary;
   }


   //return Employee name
   String getName(){ return eName; }


   //return Employee department
   String getDept(){ return eDept; }


   //return Employee gender
   char getGender(){ return eGender; }


   //return Employee salary
   long getSalary(){ return eSalary; }

}//End of class Employee



//Singleton class to execute database insert, update, delete and select queries.
class EmployeeDB{

   //Single object of EmployeeDB class
   private static EmployeeDB dbInstance = null;

   //Constants for Database Operations
   private final String DB_NAME = "EmployeesManagement";  //Database name
   private final String TABLE_NAME = "emp";   //Table(Schema) name
   private final String SQL_USERNAME= "root";     
   private final String SQL_PASSWORD= "Rohit@1997";

   //Following are the constants for database column names
   private final String SQL_EMPID = "empid";  //Employee Id 
   private final String SQL_NAME = "ename";   //Employee Full Name
   private final String SQL_DEPT = "edept";   //Employee Department
   private final String SQL_GENDER = "egender";  //Employee Gender(M, F or O[ther])
   private final String SQL_SALARY = "esalary";  //Employee Salary per annum

   //Reference of JDBC Connection
   private Connection connection;

   //Statements to execute insert, update, delete and select query
   //getAllEmpIdStatement returns all the Employee Ids
   private PreparedStatement insertStatement, updateStatement, deleteStatement, displayStatement, getAllEmpIdStatement;


   //Default constructor, cannot be called outside the class
   private EmployeeDB(){
      
      try{         
         //Registering the driver
         Class.forName("com.mysql.cj.jdbc.Driver");

        //Create connection
         connection = DriverManager.getConnection("jdbc:mysql://localhost/"+DB_NAME+"?" +
         "user="+SQL_USERNAME+"&password="+SQL_PASSWORD);

         //Statement to insert data
         insertStatement = connection.prepareStatement("INSERT INTO "+TABLE_NAME+" ("
                           +SQL_NAME+","
                           +SQL_DEPT+","
                           +SQL_GENDER+","
                           +SQL_SALARY+") values (?,?,?,?)" );

         //Statement to display all records
         displayStatement = connection.prepareStatement("SELECT * from "+TABLE_NAME);

         //Statement to update records
         updateStatement = connection.prepareStatement("update "+TABLE_NAME+" SET "
                           +SQL_NAME+"=?,"
                           +SQL_DEPT+"=?,"
                           +SQL_GENDER+"=?,"
                           +SQL_SALARY+"=? "
                           +"WHERE "
                           +SQL_EMPID+"=?");
         
         //Statement to delete a record
         deleteStatement = connection.prepareStatement("delete from "+TABLE_NAME+" where "
                           +SQL_EMPID+"=?");

         //Statment to get all EmpId
         getAllEmpIdStatement = connection.prepareStatement("select "+SQL_EMPID+" from "+TABLE_NAME);
      }
      catch (ClassNotFoundException e) {
         //ClassNotFoundException could be generated while registering the driver, i.e Class.forName()
         LOGGER.log(Level.SEVERE, "Exception : "+e);
      }
      catch(SQLException e){
         LOGGER.log(Level.SEVERE, "Exception : "+e);
      }
   }


   //Close all the connections
   void closeSQLConnections(){
      try {
         connection.close();
         insertStatement.close();
         updateStatement.close();
         deleteStatement.close();
         displayStatement.close();

      }catch(SQLException e) {
         LOGGER.log(Level.SEVERE, "SQLException : "+e);
         e.printStackTrace();
      }catch(Exception e) {
         LOGGER.log(Level.SEVERE, "Exception : "+e);
         e.printStackTrace();
      }
   }


   //method to return instance of the class.
   public static EmployeeDB getInstance(){
      if( dbInstance == null )
         dbInstance = new EmployeeDB();

      return dbInstance;   
   }


   //method to execute insert SQL query
   int executeInsertSQL(Employee empObj) throws SQLException {

      //Insert employee name
      insertStatement.setString(1, empObj.getName());

      //Insert Employee Department
      insertStatement.setString(2, empObj.getDept());

      //Insert Gender Data
      insertStatement.setString(3, empObj.getGender()+"");

      //Insert Salary Data
      insertStatement.setLong(4, empObj.getSalary());
      
      //Execute query and return the number of rows affected
      return insertStatement.executeUpdate();  
   }


   //Method to execute display SQL query(i.e. select query)
   int executeDisplaySQL() throws SQLException {
      ResultSet rs = displayStatement.executeQuery();

      //if database is empty
      if( rs.next() == false )
         return 0;
      else{
         //Display details of all the employees
         int empId;
         Employee obj;
         do{
            empId = rs.getInt(SQL_EMPID);
            obj = new Employee(rs.getString(SQL_NAME),rs.getString(SQL_DEPT),rs.getString(SQL_GENDER).charAt(0),rs.getLong(SQL_SALARY));
            displayEmployee(empId, obj);
         }
         while( rs.next() );
      }
      return 1;
   }


   //Method to display the record of a particular employee
   void displayEmployee(int empId, Employee obj){      
      LOGGER.log(Level.INFO,"Emp Id     : "+ empId);
      LOGGER.log(Level.INFO,"Name       : "+ obj.getName());
      LOGGER.log(Level.INFO,"Gender     : "+ obj.getGender());
      LOGGER.log(Level.INFO,"Salary     : "+ obj.getSalary());
      LOGGER.log(Level.INFO,"Department : "+ obj.getDept()+ "\n");
   }


   //Method to execute update SQL query
   int executeUpdateSQL(int eId, Employee empObj) throws SQLException {
      //update employee name
      updateStatement.setString(1, empObj.getName());

      //Update Employee Department
      updateStatement.setString(2, empObj.getDept());

      //Update Gender Data
      updateStatement.setString(3, empObj.getGender()+"");

      //Update Salary Data
      updateStatement.setLong(4, empObj.getSalary());

      //Specify Employee id where changes has to be made
      updateStatement.setInt(5, eId);

      //Execute the query and return the number of rows affected
      return updateStatement.executeUpdate();
   }


   //Method to check whether input eId exists( return true ) in database or not( return false)
   boolean isValidEmpId(int eId) throws SQLException {
      boolean isValidEmpId = false;

      ResultSet resEmpId = getAllEmpIdStatement.executeQuery();
      while( resEmpId.next() ){
         if( resEmpId.getInt(1) == eId){  //valid eId i.e. EmpId exists in database
            isValidEmpId = true;
            break;
         }
      }   
      return isValidEmpId;
   }


   //Method to execute delete SQL query
   int executeDeleteSQL(int eId) throws SQLException {
      deleteStatement.setInt(1, eId);
      return deleteStatement.executeUpdate();
   }

}//End of class EmployeeDB



//Driver Class
public class EmployeesManagement{

   //main method
   public static void main(String[] args) {
      //flag to break loop
      boolean exitFlag = false;

      //Instance of EmployeeDB class
      EmployeeDB dbInstance = EmployeeDB.getInstance();

      while(!exitFlag){
            
         LOGGER.log(Level.INFO,"\n1.Insert \t 2.Display All \t 3.Update \t 4.Delete \t 5.Exit \n Choice : ");
         int choice = new Scanner(System.in).nextInt();

         try{
            //Insert Employee details into database
            if( choice == 1){
               Employee obj = inputEmployeeDetails();
               int rows = dbInstance.executeInsertSQL(obj);
            if( rows > 0)
               LOGGER.log(Level.INFO, "Data inserted\n");
            else
               LOGGER.log(Level.SEVERE, "Insertion Failed\n");
            }

            //To display all the records
            else if( choice == 2){
               if( dbInstance.executeDisplaySQL() == 0 )
                  LOGGER.log(Level.WARNING, "No records to display\n");
            }

            //To update a record
            else if( choice == 3){
               LOGGER.log(Level.INFO,"Update an employee record\n");
               int eId = inputEmployeeId();

               //Check whether input eId exists or not
               if( !(dbInstance.isValidEmpId(eId)) ){
                  LOGGER.log(Level.SEVERE, "Invalid Employee Id\n");
               }
               else{
                  Employee empObj = inputEmployeeDetails();
                  int rows = dbInstance.executeUpdateSQL(eId, empObj);
                  if( rows > 0 )
                     LOGGER.log(Level.INFO, "Record Updated, "+rows+" rows affected\n");
                  else
                     LOGGER.log(Level.SEVERE, "Updation Failed\n");
               } 
            }

            //To delete a record
            else if(choice == 4){
               LOGGER.log(Level.OFF,"Delete Employee \n");
               int eId = inputEmployeeId();

               //Check whether input eId exists or not
               if( !(dbInstance.isValidEmpId(eId)) ){
                  LOGGER.log(Level.SEVERE, "Invalid Employee Id\n");
               }
               else{
                  int rows = dbInstance.executeDeleteSQL(eId);
                  if( rows > 0 )
                     LOGGER.log(Level.INFO,"Record Deleted\n");
                  else
                     LOGGER.log(Level.WARNING, "Deletion Failed...Record Not Found\n");
               }
            }
            //Exit the program
            else if(choice == 5){
               //Close all the SQL connections before exiting
               dbInstance.closeSQLConnections();
               exitFlag = true;
            }
         }
         catch(SQLException e){
            LOGGER.log(Level.SEVERE, "Exception : "+e);
         }
         
      }//End of while      
   }


   //Take EmpId input from user and return it
   static int inputEmployeeId(){
      LOGGER.log(Level.INFO,"Employee Id : ");
      int eId = new Scanner(System.in).nextInt();

      return eId;
   }


   //Method to input details of an employee through console
   private static Employee inputEmployeeDetails(){
      Scanner sc = new Scanner(System.in);
      
      LOGGER.log(Level.INFO,"\nEnter employee details\n");

      LOGGER.log(Level.INFO,"\nFull Name     : ");
      String eName = sc.nextLine();

      LOGGER.log(Level.INFO,"\nGender(M|F|O) : ");
      char eGender = sc.next().toUpperCase().charAt(0);

      LOGGER.log(Level.INFO,"\nSalary        : "); 
      long eSalary = sc.nextLong();

      sc.nextLine();    //To skip '\n' character

      LOGGER.log(Level.INFO,"\nDepartment    : ");
      String eDept = sc.nextLine();

      //Create an object of Employee with given values
       return new Employee(eName, eDept, eGender, eSalary);

   }

}//End of Driver class

