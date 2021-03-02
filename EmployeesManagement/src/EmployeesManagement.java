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
      
   private int eId;  //Employee ID
   private String eName,eDept;   //Employee name and deptartment
   private char eGender;   //Employee Gender : M or F
   private long eSalary;   //Employee Salary
   
   //Default constructor.
   Employee(){
      //insert method is called to input the details of the employee
      insertEmployee();
   }

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

   //Method to input details of an employee through console
   private void insertEmployee(){
      Scanner sc = new Scanner(System.in);
      
      LOGGER.log(Level.INFO,"\nEnter employee details\n");

      LOGGER.log(Level.INFO,"\nFull Name  : ");
      eName = sc.nextLine();

      LOGGER.log(Level.INFO,"\nGender     : ");
      eGender = sc.next().toUpperCase().charAt(0);

      LOGGER.log(Level.INFO,"\nSalary     : "); 
      eSalary = sc.nextLong();

      sc.nextLine();    //To skip '\n' character

      LOGGER.log(Level.INFO,"\nDepartment : ");
      eDept = sc.nextLine();

      //sc.close();
   }

   //return Employee name
   String getName(){ return eName; }

   //return Employee department
   String getDept(){ return eDept; }

   //return Employee gender
   char getGender(){ return eGender; }

   //return Employee salary
   long getSalary(){ return eSalary; }

}


public class EmployeesManagement{

   static final String DB_NAME = "EmployeesManagement";
   static final String TABLE_NAME = "emp";
   static final String SQL_EMPID = "empid";
   static final String SQL_NAME = "ename";
   static final String SQL_DEPT = "edept";
   static final String SQL_GENDER = "egender";
   static final String SQL_SALARY = "esalary";

   //HashMap to store all the employee records
   private static HashMap<Integer,Employee> employeeList = new HashMap<>();
   
   //To generate unique empid
   private static int NEXT_EMPID;

   //Initialize static members
   static{
      NEXT_EMPID = 0;
   }

   //return next Employee Id
   private static int getNextId(){
      return ++NEXT_EMPID;
   }

   //Reference of Connection
   private static Connection connection;
   private static PreparedStatement insertStatement, updateStatement, deleteStatement, displayStatement;

   //method to execute insert SQL query
   static void executeInsertSQL() throws SQLException {

      Employee empObj = new Employee();

      //Insert employee name
      insertStatement.setString(1, empObj.getName());

      //Insert Employee Department
      insertStatement.setString(2, empObj.getDept());

      //Insert Gender Data
      insertStatement.setString(3, empObj.getGender()+"");

      //Insert Salary Data
      insertStatement.setLong(4, empObj.getSalary());
      
      if( insertStatement.executeUpdate() > 0 )
         LOGGER.log(Level.INFO, "Data inserted");
      else
         LOGGER.log(Level.SEVERE, "Insertion Failed");

   }

   //Method to execute display SQL query(i.e. select query)
   static void executeDisplaySQL() throws SQLException {

      ResultSet rs = displayStatement.executeQuery();

      //if database is empty
      if( rs.next() == false )
         LOGGER.log(Level.WARNING, "No records to display");
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

   }


   static void executeUpdateSQL() throws SQLException {

      //Update an employee
      LOGGER.log(Level.INFO,"Update an employee record\n");
      LOGGER.log(Level.INFO,"Employee Id : ");
      int eId = new Scanner(System.in).nextInt();
      
      Employee empObj = new Employee();

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
      
      int rows = updateStatement.executeUpdate();
      
      if( rows > 0 )
         LOGGER.log(Level.INFO, "Data Updated, "+rows+" affected");
      else
         LOGGER.log(Level.SEVERE, "Updation Failed");

   }

   //Display a particular employee
   static void displayEmployee(int empId, Employee obj){      
      LOGGER.log(Level.INFO,"Emp Id     : "+ empId);
      LOGGER.log(Level.INFO,"Name       : "+ obj.getName());
      LOGGER.log(Level.INFO,"Gender     : "+ obj.getGender());
      LOGGER.log(Level.INFO,"Salary     : "+ obj.getSalary());
      LOGGER.log(Level.INFO,"Department : "+ obj.getDept()+ "\n");
   }

   //Delete an employee
   static Employee deleteEmployee(int eId){      
      Employee tempObj = null;
      if( employeeList.containsKey(eId) ){
         tempObj = new Employee(employeeList.get(eId));
         employeeList.remove(eId);
      }

      return tempObj;
   }


   //To update details of Employee having given eId
   static boolean updateEmployee(int eId){
      
      
      return false;
   }

   //Driver method
   public static void main(String[] args) {
      boolean exitFlag = false;

      try{
         
         //Registering the driver
         Class.forName("com.mysql.cj.jdbc.Driver");

         //Create connection
         /*
            EmployeesManagerment : Database name
            localhost            : Server name
            root                 : Username
            Rohit@1997           : Password
         */
         connection = DriverManager.getConnection("jdbc:mysql://localhost/"+DB_NAME+"?" +
         "user=root&password=Rohit@1997");
         System.out.println("Connected successfully!!");

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
                           

         while(!exitFlag){
            
            LOGGER.log(Level.INFO,"\n1.Insert \t 2.Display All \t 3.Update \t 4.Delete \t 5.Exit \n Choice : ");
            int choice = new Scanner(System.in).nextInt();
   
            //Insert Employee details into database
            if( choice == 1){
               executeInsertSQL();
            }
   
            //To display all the records
            else if( choice == 2){
               executeDisplaySQL();
            }
   
            else if( choice == 3){
               executeUpdateSQL();
            }
   
            else if(choice == 4){
               //Delete an employee
               LOGGER.log(Level.INFO,"Delete Employee \n");
               LOGGER.log(Level.INFO,"Employee ID : ");
               int eId = new Scanner(System.in).nextInt();
               if( deleteEmployee(eId) != null )
                  LOGGER.log(Level.INFO,"Employee record deleted\n");
            }
            else if(choice == 5)
               exitFlag = true;
            
         }//End of while


         //Closing the database connection
         connection.close();
      }
      catch(SQLException e){
         LOGGER.log(Level.SEVERE, "Exception : "+e);
      } catch (ClassNotFoundException e) {
         //ClassNotFoundException could be generated while registering the driver, i.e Class.forName()
         LOGGER.log(Level.SEVERE, "Exception : "+e);
      }


      
   }
}
