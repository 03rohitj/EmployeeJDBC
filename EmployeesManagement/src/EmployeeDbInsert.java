import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class EmployeeDbInsert {
   private PreparedStatement insertStatement;

   public EmployeeDbInsert(Connection connection, Employee empObj, Scanner sc) throws SQLException, NumberFormatException {

      //Statement to insert data
      insertStatement = connection.prepareStatement( EmployeeDbInfo.SQL_INSERT_QUERY );

      LOGGER.log(Level.INFO,"\nEnter employee details\n");

      LOGGER.log(Level.INFO,"\nFull Name     : ");
      empObj.setName(sc.nextLine());

      LOGGER.log(Level.INFO,"\nGender(M|F|O) : ");
      empObj.setGender(sc.nextLine().toUpperCase().charAt(0));

      LOGGER.log(Level.INFO,"\nSalary        : "); 
      empObj.setSalary(Long.parseLong(sc.nextLine()));

      LOGGER.log(Level.INFO,"\nDepartment    : ");
      empObj.setDepartment(sc.nextLine());

      //Insert employee name
      insertStatement.setString(1, empObj.getName());

      //Insert Employee Department
      insertStatement.setString(2, empObj.getDept());

      //Insert Gender Data
      insertStatement.setString(3, empObj.getGender()+"");

      //Insert Salary Data
      insertStatement.setLong(4, empObj.getSalary());
      
      //Execute query and return the number of rows affected
      insertStatement.executeUpdate();
   }  
   
}
