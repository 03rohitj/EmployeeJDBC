
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

//Class to print log messages on console
class LOGGER{
   
   //method accesible throughout the package to log messages
   public static void log(Level level, String message){
      Logger.getLogger(Logger.GLOBAL_LOGGER_NAME).log(level,message);
   }
}


//Driver Class
public class EmployeesManagement{

   //Reference of JDBC Connection
   private Connection connection;

   private Employee employee;
   private Scanner sc;

   //flag to break loop
   boolean exitFlag = false;

   private EmployeesManagement(){

      try{  
         employee = new Employee();
         sc = new Scanner(System.in);

         //Registering the driver
         Class.forName(EmployeeDbInfo.JDBC_DRIVER_URL);

        //Create connection
         connection = DriverManager.getConnection(EmployeeDbInfo.JDBC_CONNECTION_URL);

         while(!exitFlag){
            LOGGER.log(Level.INFO,"\n1.Insert \t 2.Display All \t 3.Update \t 4.Delete \t 5.Exit \n Choice : ");
            int choice = Integer.parseInt(sc.nextLine());
            
            //This nested try block prevents the termination of program in case exception is thrown.
            try{
               //Insert Employee details into database
               if( choice == 1){
                  new EmployeeDbInsert(connection, employee, sc);
               }
   
               //To display all the records
               else if( choice == 2){
                  new EmployeeDbDisplay(connection, employee);
               }
   
               //To update a record
               else if( choice == 3){
                  new EmployeeDbUpdate(connection, employee, sc);
               }
   
               //To delete a record
               else if(choice == 4){
                  new EmployeeDbDelete(connection, sc);
               }
               //Exit the program
               else if(choice == 5){
                  //Close all the SQL connections before exiting
                  exitFlag = true;
               }
            }
            catch(SQLException e){
               LOGGER.log(Level.SEVERE, "Exception => "+e.getMessage());
               e.printStackTrace();
            }
            
         }//End of while      
      
      }
      catch (ClassNotFoundException e) {
         //ClassNotFoundException could be generated while registering the driver, i.e Class.forName()
         LOGGER.log(Level.SEVERE, "CNFException : "+e.getMessage());
      }
      catch(SQLException e){
         LOGGER.log(Level.SEVERE, "SQLException : "+e.getMessage());
      }
      catch(NumberFormatException e){
         LOGGER.log(Level.SEVERE, "NumberFormatException : "+e.getMessage());
      }
      finally{
         //Close connection and scanner 
         try {
            connection.close();
            sc.close();
         } 
         catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "SQLException : "+e.getMessage());
         }
      }
   }
   
   //main method
   public static void main(String[] args) {
       
      new EmployeesManagement();
      
   }
}//End of Driver class