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
    public static final String DB_NAME = "nf";
    public static final String SERVER_NAME = "localhost";
    public static final String DATE_SET = "2026-04-09";
    public static final String MACHINE_ID = "260001";
    
    public static final String JDBC_DRIVER = "com.mysql.jdbc.Driver"; 
    public static final String DB_URL = "jdbc:mysql://"+SERVER_NAME+"/"+DB_NAME;
    //  Database credentials
    static final String USER = "root";
    static final String PASS = "";

    public static final int COMPANY_ID = 1;
    public static final String COMPANY_NAME = "NORTHFLASH POWER AND BUILDS, INC.";
    public static final String MACHINE_LOCATION = "Paoay, Ilocos Norte";
    
//    static final String SERVER_URL = "http://"+SERVER_NAME+"/attendance-monitoring-webapp/public";
    public static final String SERVER_URL = "http://attendance-monitoring-webapp.bee";
//    public static final String SERVER_URL = "http://localhost:1234";
    
    public static final String AUDIO_SUCCESS = System.getProperty("user.dir")+"\\audio\\success.wav";
    public static final String AUDIO_ERROR = System.getProperty("user.dir")+"\\audio\\error.wav";
}
