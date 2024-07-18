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
    private static final String SERVER_NAME = "localhost";
    //public static final String serverIP = "10.13.1.82";
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    
    //public static final String DB_URL = "jdbc:mysql://localhost/hris";
    public static final String DB_URL = "jdbc:mysql://"+SERVER_NAME+"/laravel";
    //  Database credentials
   static final String USER = "root";
   static final String PASS = "";
   
   static final int COMPANY_ID = 1;
   public static final String MACHINE_ID = "101";
   static final String SERVER_URL = "http://"+SERVER_NAME+"/attendance-monitoring-webapp/public";
}
