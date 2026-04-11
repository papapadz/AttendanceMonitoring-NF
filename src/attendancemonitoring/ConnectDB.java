package attendancemonitoring;

import java.sql.*;

/**
 *
 * @author Benoe
 */
public class ConnectDB  {
    
    public Connection connect() {
      Connection conn = null;
       
   try{
      //STEP 2: Register JDBC driver
      Class.forName(Config.JDBC_DRIVER);
      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(Config.DB_URL,Config.USER,Config.PASS);
    
   }catch(ClassNotFoundException | SQLException se){
      //Handle errors for JDBC
      se.printStackTrace();
      System.out.println("Error connecting to database...");
   } finally {
       System.out.println("Sucess! Connected to database!");
   }
   
   return conn;
  } //end of connect()
    
}
