import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

public class EmployeeDbDisplay {
   private PreparedStatement displayStatement;

   public EmployeeDbDisplay(Connection connection, Employee empObj) throws SQLException {
      displayStatement = connection.prepareStatement(EmployeeDbInfo.SQL_DISPLAY_QUERY);

      ResultSet rs = displayStatement.executeQuery();

      //if database is empty
      if( rs.next() == false ){
         LOGGER.log(Level.INFO, "No Records to display\n");
      }
      else{
         //Display details of all the employees
         int empId;
         do{
            empId = rs.getInt(EmployeeDbInfo.SQL_EMPID);

            empObj.setName(rs.getString(EmployeeDbInfo.SQL_NAME));
            empObj.setDepartment(rs.getString(EmployeeDbInfo.SQL_DEPT));
            empObj.setGender(rs.getString(EmployeeDbInfo.SQL_GENDER).charAt(0));
            empObj.setSalary(rs.getLong(EmployeeDbInfo.SQL_SALARY));

            displayEmployee(empId, empObj);
         }
         while( rs.next() );
      }
   }

   //Method to display the record of a particular employee
   void displayEmployee(int empId, Employee empObj){      
      LOGGER.log(Level.INFO,"Emp Id     : "+ empId);
      LOGGER.log(Level.INFO,"Name       : "+ empObj.getName());
      LOGGER.log(Level.INFO,"Gender     : "+ empObj.getGender());
      LOGGER.log(Level.INFO,"Salary     : "+ empObj.getSalary());
      LOGGER.log(Level.INFO,"Department : "+ empObj.getDept()+ "\n\n");
   }

}
