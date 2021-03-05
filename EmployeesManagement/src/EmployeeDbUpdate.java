import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class EmployeeDbUpdate {
   
   private PreparedStatement updateStatement, getAllEmpIdStatement;

   public EmployeeDbUpdate(Connection connection, Employee empObj, Scanner sc) throws SQLException, NumberFormatException {
      
      updateStatement = connection.prepareStatement(EmployeeDbInfo.SQL_UPDATE_QUERY);
      
      //Statment to get all EmpId
      getAllEmpIdStatement = connection.prepareStatement(EmployeeDbInfo.SQL_GET_ALL_ID_QUERY);

      LOGGER.log(Level.INFO, "Enter Employee Id : ");
      int eId = Integer.parseInt(sc.nextLine());

      //Check if eId exists
      if( isValidEmpId(eId) == false){
         LOGGER.log(Level.SEVERE, "Employee Id doesn't exists!\n");
         return;
      }

      LOGGER.log(Level.INFO,"\nEnter employee details");

      LOGGER.log(Level.INFO,"\nFull Name     : ");
      empObj.setName(sc.nextLine());

      LOGGER.log(Level.INFO,"\nGender(M|F|O) : ");
      empObj.setGender(sc.nextLine().toUpperCase().charAt(0));

      LOGGER.log(Level.INFO,"\nSalary        : "); 
      empObj.setSalary(Long.parseLong(sc.nextLine()));

      LOGGER.log(Level.INFO,"\nDepartment    : ");
      empObj.setDepartment(sc.nextLine());


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
      updateStatement.executeUpdate();

      LOGGER.log(Level.INFO, "Record Updated\n");  
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
}
