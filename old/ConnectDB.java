package attendancemonitoring;

import java.sql.*;

/**
 *
 * @author Benoe
 */
public class ConnectDB  {
    
    public Connection connect() {
      Connection conn = null;
      Config myConfig = new Config(); 
       
   try{
      //STEP 2: Register JDBC driver
      Class.forName(myConfig.JDBC_DRIVER);
      //STEP 3: Open a connection
      System.out.println("Connecting to database...");
      conn = DriverManager.getConnection(myConfig.DB_URL,myConfig.USER,myConfig.PASS);
    
   }catch(Exception se){
      //Handle errors for JDBC
      se.printStackTrace();
   }
   
   return conn;
  } //end of connect()
    
}
