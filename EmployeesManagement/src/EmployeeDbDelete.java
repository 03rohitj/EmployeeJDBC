import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;

public class EmployeeDbDelete {
   private PreparedStatement deleteStatement;

   public EmployeeDbDelete(Connection connection, Scanner sc) throws SQLException, NumberFormatException {
      deleteStatement = connection.prepareStatement(EmployeeDbInfo.SQL_DELETE_QUERY);

      LOGGER.log(Level.INFO, "Enter Employee Id : ");
      int eId = Integer.parseInt(sc.nextLine());

      deleteStatement.setInt(1, eId);
      int res = deleteStatement.executeUpdate();

      if(res == 1)
         LOGGER.log(Level.SEVERE, "Employee Record Deleted\n");
      else
         LOGGER.log(Level.SEVERE, "Invalid Employee Id\n");

   }
}
