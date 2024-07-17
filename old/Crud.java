package attendancemonitoring;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
/**
 *
 * @author Benoe
 */
public class Crud {

    //   Connect to mySQL db
    static ConnectDB connDB = new ConnectDB();
    //  Global variables needed
    PreparedStatement st;
    static String sql = "";
    // employee info
    public String IDNo = "";
    public String name = "";
    private String position = "";
    private String dept = "";
    public String amInDate = "";
    public String tiAM = null;
    public String toAM = null;
    public String tiPM = null;
    public String toPM = null;
    //public String amin = null;
    //public String amout = null;
    //public String pmin = null;
    //public String pmout = null;
    public String yesterday = null;
    //public String dt = getDate();
    public String timeNow = null;
    
    public void Crud() {
        MainGUI.lblTime.setText("");
    }
    
    public void newAttendance(String id, String n, String p, String d) throws SQLException, ParseException {
       
       //String []empDTR = new String[4];
       IDNo = id;
       name = n;
       position = p;
       dept = d;
       timeNow = getTimeNow(1);
       
       /*
       if(MainGUI.timeType==1) { 
           inorout = "Out";
           //checks employee attendance yesterday
           /*checkYesterday(eid); 
           if(schedOutNextDay!=null&&(tiAM==null||toPM==null))
               dt = yesterday; //sets the date to yesterday
           else
               schedOutNextDay=null; */
       //}
       
       try {
                    sql = "SELECT DATE_FORMAT(timein_am,'%h:%i %p') AS amin, "
                            + "DATE_FORMAT(timeout_am,'%h:%i %p') AS amout, " 
                            + "DATE_FORMAT(timein_pm,'%h:%i %p') AS pmin, "
                            + "DATE_FORMAT(timeout_pm,'%h:%i %p') AS pmout, "
                            + "timein_am,timeout_am,timein_pm,timeout_pm FROM tbl_employee_dtr "
                            + "WHERE emp_id ='"+id+"' AND dtr_date =DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
                    st = connDB.connect().prepareStatement(sql);
                    //  retrieve employee attendance info 
                    try (ResultSet r = st.executeQuery()) {
                        //  retrieve data by column name
                        if(r.next()) {
                            
                            if(r.getString("amin")!=null)
                                tiAM = r.getString("amin");
                            if(r.getString("amout")!=null)
                                toAM = r.getString("amout");
                            if(r.getString("pmin")!=null)
                                tiPM = r.getString("pmin");
                            if(r.getString("pmout")!=null)
                                toPM = r.getString("pmout");
                          
                            //tiAM = empDTR[0] = r.getString("timein_am");
                            //toAM = empDTR[1] = r.getString("timeout_am");
                            //tiPM = empDTR[2] = r.getString("timein_pm");
                            //toPM = empDTR[3] = r.getString("timeout_pm");
                        }
                    }
                }catch(SQLException se) {
                    //Handle errors for JDBC
                    se.printStackTrace();
                }
       
           /* }   catch (SQLException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        } */
       
    String attnTimeType = setAMorPM(); //set attendance field to save to 
         
    //System.out.println(attnTimeType);
    
    /*
    if(insert(attnTimeType,id,empDTR)==0)
        setEmpCard("");
    }
    */
    if(insert(id,attnTimeType)==0)
        setEmpCard("");
    }  
    public int insert(String id, String f) throws SQLException, ParseException{
       
        //String[] x = getDate().split("-");
        //String[] y = getTimeNow(1).split(":");
        //String idx = id+x[1]+x[2]+x[0]+y[0]+y[1]+y[2];
        
        try{  
            //update attendance info if employee has attendance
            //otherwise inser new attendance info
            //incorrect outcomes will return 0 and go back the first step
        if(checkIfAlreadyIn(id)) {
            
            if(f.equalsIgnoreCase("timein_am")) {
                MainGUI.infoBox("You already have an AM TIME IN.","Warning!");
                return 0;
            } else if(f.equalsIgnoreCase("timeout_am")) {
                if(tiAM==null) {
                    MainGUI.infoBox("You don't have any AM TIME IN yet.","Warning!");
                    return 0;
                } else if(toAM!=null) {
                    MainGUI.infoBox("You already have an AM TIME OUT.","Warning!");
                    return 0;
                }
            } else if(f.equalsIgnoreCase("timein_pm")) {
                if(tiPM!=null) {
                    MainGUI.infoBox("You already have a PM TIME IN.","Warning!");
                    return 0;
                } else if(toAM==null) {
                    MainGUI.infoBox("You don't have any AM TIME OUT yet.","Warning!");
                    return 0;
                } 
            } else if(f.equalsIgnoreCase("timeout_pm")) {
                if(toPM!=null) {
                    MainGUI.infoBox("You already have a PM TIME OUT","Warning!");
                    return 0;
                }
                else if(tiPM==null&&tiAM==null) {
                    MainGUI.infoBox("You don't have any TIME IN yet.","Warning!");
                    return 0;
                }
            }
            
            //sql = "UPDATE tbl_employee_dtr SET "+f+"='"+timeNow+"' WHERE emp_id='"+id+"' AND dtr_date='"+dt+"'";
            sql = "UPDATE tbl_employee_dtr SET "+f+"=CURTIME() WHERE emp_id='"+id+"' AND dtr_date=DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
        }
        else {
              /*  if(schedOutNextDay==null || f.equalsIgnoreCase("TimeInAM")) {
                    if(f.equalsIgnoreCase("TimeOutPM") || f.equalsIgnoreCase("TimeOutAM")) {
                        MainGUI.infoBox("You don't have an AM TIME IN yet","Warning!");
                        return 0;
                    } 
                    sql = "INSERT INTO empattendance(IDNo,"+f+","+deductField+",DateOf) VALUES('"+IDNo+"','"+timeNow+"','"+ded+"','"+dt+"')";
                }
                else
                    sql = "UPDATE empattendance SET "+f+"='"+timeNow+"',"+deductField+"='"+ded+"' WHERE IDNo='"+IDNo+"' AND DateOf='"+yesterday+"'";
              */
              if(f.equalsIgnoreCase("timeout_pm")) {
              
                if(toPM!=null) {
                    if(verifyYesterday(id))
                        sql = "UPDATE tbl_employee_dtr SET timeout_pm=CURTIME() WHERE emp_id='"+id+"' AND dtr_date=DATE_ADD(CURDATE(), INTERVAL -1 day)";
                    else
                        sql = "UPDATE tbl_employee_dtr SET "+f+"=CURTIME() WHERE emp_id='"+id+"' AND dtr_date=DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
                    }
                else {
                    MainGUI.infoBox("You already have a PM TIME OUT","Warning!");
                    return 0;
                }
              }
              else
                sql = "INSERT INTO tbl_employee_dtr(emp_id,"+f+",dtr_date,machine_id) VALUES ('"+id+"',CURTIME(),DATE_FORMAT(CURDATE(),'%Y-%m-%d'),'"+Config.MACHINE_ID+"')";
        
        }
        System.out.println(sql);
        //  connect DB
        st = connDB.connect().prepareStatement(sql);
        //  run SQL query
        st.executeUpdate();
        
        }catch(SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            
            MainGUI.infoBox("Sorry Information not Found","Error!");
            return 0;
        }
        
        //prompts dialog box and updates employee
        //attendance card display
        //if process encountered no error
        //return 1 and is ready again for another process
        if(MainGUI.infoBox("Thank you "+name,"Verified"))
            setEmpCard(f);

        return 1;
    }
    
    public String setAMorPM() throws ParseException {
        
        //format of the string (eg: 8:00:02) 
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
 
        //parse string for time calculations
        Date t = dateFormat.parse(timeNow);
        
        Date time1 = dateFormat.parse("8:00:00");
        Date time2 = dateFormat.parse("12:00:00");
        Date time3 = dateFormat.parse("13:00:00");
        
        if(MainGUI.timeType==0) {
            if(t.compareTo(time1)==0||(t.after(time1)&&t.before(time2))||t.before(time1))
                return "timein_am";
            else 
                return "timein_pm";
        } else
            if(t.compareTo(time2)==0||(t.after(time1)&&t.before(time3)))
                return "timeout_am";
       
        return "timeout_pm";
    }
       
    public int setTimeConvention() throws ParseException {
        
        //format of the string (eg: 8:00:02) 
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
 
        //parse string for time calculations
        Date t = dateFormat.parse(timeNow);
        
        Date time1 = dateFormat.parse("8:00:00");
        Date time2 = dateFormat.parse("12:00:00");
        Date time3 = dateFormat.parse("13:00:00");
        
        if(MainGUI.timeType==0) {
            if(t.compareTo(time1)==0||(t.after(time1)&&t.before(time2)))
                return 0;
            else 
                return 1;
        } else
            if(t.compareTo(time2)==0||(t.after(time1)&&t.before(time3)))
                return 0;
        
        return 1;
    }
       
    public boolean checkIfAlreadyIn(String id) throws SQLException {
        //check employee if it has already
        //an existing attendance info in a
        //particular date. If exists, return
        //true otherwise return false
             sql = "SELECT emp_id FROM tbl_employee_dtr WHERE dtr_date=DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
             st = connDB.connect().prepareStatement(sql);
             
             try (ResultSet rs = st.executeQuery()) {
                while(rs.next()) {
                    if(id.equalsIgnoreCase(rs.getString("emp_id")))
                        return true;
                }
             }
             return false;
    }
    
    
    public String getYesterday() throws ParseException, SQLException {
       //checks schedule and attendance yesterday 
       //for employee time out after a day
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1); //get yesterday's date    
        String y =  dateFormat.format(cal.getTime()); //parse to format
        
        return y;
    }
    
    public boolean verifyYesterday(String id) throws SQLException, ParseException {
    
        String am,pm;
        sql = "SELECT * FROM tbl_employee_dtr WHERE emp_id='"+id+"' AND dtr_date=DATE_ADD(CURDATE(), INTERVAL -1 day)";
        st = connDB.connect().prepareStatement(sql);
        ResultSet r = st.executeQuery();
            if(r.next()) {
             am = r.getString("timein_am");
             pm = r.getString("timein_pm");
             System.out.println("Get Yesterday: " + sql);
             if(am==null&&pm==null)
                return false;
            }
            
        return true;
    }
    
    public String getDate() {
        //returns the system date now in a string format
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	//get current date time with Date()
	Date date = new Date();
	String dtt = dateFormat.format(date);
        return dtt;
    }
    
    public String getTimeNow(int flag) {
        DateFormat dateFormat = null;
        //returns the system time now in a string format
        if(flag==1)
            dateFormat = new SimpleDateFormat("HH:mm:ss");
        else if(flag==0)
            dateFormat = new SimpleDateFormat("hh:mm:ss a");
        else
            dateFormat = new SimpleDateFormat("hh:mm a");
        //get current date time with Date()
	Date date = new Date();
	String tm = dateFormat.format(date);
        return tm;
    }
    
    public void setEmpCard(String attnType) throws ParseException {
        
        //append text and update employee
        //attendance card display
        
        MainGUI.lblEmpID.setText(IDNo);
        MainGUI.lblEmpName.setText(name);
        MainGUI.lblDivision.setText(position);
        MainGUI.lblSection.setText(dept);
        
        MainGUI.lblAMin.setText(tiAM);
        MainGUI.lblAMout.setText(toAM);
        MainGUI.lblPMin.setText(tiPM);
        MainGUI.lblPMout.setText(toPM);
        
        //SimpleDateFormat df = new SimpleDateFormat("HH:mm a");
        
        if(attnType.equalsIgnoreCase("timein_am"))
            MainGUI.lblAMin.setText(getTimeNow(2));
        else 
            if(attnType.equalsIgnoreCase("timeout_am"))
                MainGUI.lblAMout.setText(getTimeNow(2));
            else 
                if(attnType.equalsIgnoreCase("timein_pm"))
                    MainGUI.lblPMin.setText(getTimeNow(2));
                else 
                    if(attnType.equalsIgnoreCase("timeout_pm"))
                        MainGUI.lblPMout.setText(getTimeNow(2));
                    
        
    }
    
}