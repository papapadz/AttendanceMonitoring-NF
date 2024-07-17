/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;

/**
 *
 * @author Benoe
 */


public class Config {
    //  jdbc and mySQL database
    //public static final String serverIP = "192.168.0.19";
    public static final String SERVERIP = "localhost";
    //public static final String serverIP = "10.13.1.82";
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    
    //public static final String DB_URL = "jdbc:mysql://localhost/hris";
    public static final String DB_URL = "jdbc:mysql://"+SERVERIP+"/nf";
    //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   public static final String MACHINE_ID = "102";
   
   
}
