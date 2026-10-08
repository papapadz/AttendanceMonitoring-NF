package attendancemonitoring;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
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
    //public String amInDate = "";
    private String did,tiAM,toAM,tiPM,toPM;
    //public String amin = null;
    //public String amout = null;
    //public String pmin = null;
    //public String pmout = null;
    public String yesterday = null;
    //public String dt = getDate();
    public String timeNow = null;
    public static String timeToDisplay = null;
    //public int yestertime = 0;
    DateFormat dateFormatParser = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
    public void Crud() {
        MainGUI.lblTime.setText("");
    }
    
    public boolean isBirthday(String birthdate) throws IOException, ParseException {
        LocalDate today = LocalDate.now();
        LocalDate birthday = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        return today.getMonthValue() == birthday.getMonthValue() &&
               today.getDayOfMonth() == birthday.getDayOfMonth();
        
    }
    
    public void newAttendance(String id, String n, String p, String d, String bdate) throws SQLException, ParseException, IOException {
       
       //String []empDTR = new String[4];
       IDNo = id;
       name = n;
       position = p;
       dept = d;
       timeNow = getTimeNow(1);
       //yestertime = 0;
       did = null;
       tiAM = null;
       toAM = null;
       tiPM = null;
       toPM = null;
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
       
                    sql = "SELECT id, DATE_FORMAT(timein_am,'%h:%i %p') AS amin, "
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
                            
                            did = r.getString("id");
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
        if(insert(id,attnTimeType,bdate)==0)
            setEmpCard("");
    
    }
    
    public int insert(String id, String f, String birthdate) throws SQLException, ParseException{
       
        try{
            
            try{
                //update attendance info if employee has attendance
                //otherwise insert new attendance info
                //incorrect outcomes will return 0 and go back the first step

                //  run SQL query
                
                if(did!=null) {
                    
                    if(f.equalsIgnoreCase("timein_am")) {
                        if(verifyYesterdayOut(id)) {
                            MainGUI.infoBox("You don't have a TIME OUT from yesterday yet.","Warning!");
                            return 0;
                        } else if(tiAM!=null) {
                            MainGUI.infoBox("You already have an AM TIME IN.","Warning!");
                            return 0;
                        } 
                    } else if(f.equalsIgnoreCase("timeout_am")) {
                        if(toAM!=null) {
                            MainGUI.infoBox("You already have an AM TIME OUT.","Warning!");
                            return 0;
                        } else if(tiAM==null) {
                            MainGUI.infoBox("You don't have any AM TIME IN yet.","Warning!");
                            return 0;
                        } 
                    } else if(f.equalsIgnoreCase("timein_pm")) {
                        if(tiPM!=null) {
                            MainGUI.infoBox("You already have a PM TIME IN.","Warning!");
                            return 0;
                        } else if(toAM==null && toPM==null) {
                            MainGUI.infoBox("You don't have any AM TIME OUT yet.","Warning!");
                            return 0;
                        }
                    } else if(f.equalsIgnoreCase("timeout_pm")) {
                        if(toPM!=null) {
                            MainGUI.infoBox("You already have a PM TIME OUT","Warning!");
                            return 0;
                        } else if(tiPM==null&&tiAM==null) {
                            MainGUI.infoBox("You don't have any TIME IN yet.","Warning!");
                            return 0;
                        } else if(tiAM==null&&toAM==null&&tiPM==null) {
                            MainGUI.infoBox("You don't have a any TIME IN yet.","Warning!");
                            return 0;
                        } else if(tiAM!=null&&toAM!=null&&tiPM==null) {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm a");
                            Date time1 = dateFormat.parse(tiAM);
                            Date time2 = dateFormat.parse(toAM);
                            
                            if(time1.after(time2)) {
                                MainGUI.infoBox("You don't have a PM TIME IN yet.","Warning!");
                                return 0;
                            }
                        }
                    }
                    
                    //sql = "UPDATE tbl_employee_dtr SET "+f+"='"+timeNow+"' WHERE emp_id='"+id+"' AND dtr_date='"+dt+"'";
                    //sql = "UPDATE tbl_employee_dtr SET "+f+"=CURTIME() WHERE emp_id='"+id+"' AND dtr_date=DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
                    sql = "UPDATE tbl_employee_dtr SET "+f+"='"+timeNow+"', sync=false WHERE id='"+did+"'";
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
                    if(f.equalsIgnoreCase("timein_am")) {
                        
                        if(verifyYesterdayOut(id)) {
                            MainGUI.infoBox("You don't have a TIME OUT from your last TIME IN yet.","Warning!");
                            return 0;
                        } else
                            sql = "INSERT INTO tbl_employee_dtr(emp_id,"+f+",dtr_date,machine_id) VALUES ('"+id+"','"+timeNow+"',DATE_FORMAT(CURDATE(),'%Y-%m-%d'),'"+Config.MACHINE_ID+"')";
                    } else if(f.equalsIgnoreCase("timeout_pm")||f.equalsIgnoreCase("timeout_am")) {
                            /*
                            if(verifyYesterday(id))
                                sql = "UPDATE tbl_employee_dtr SET timeout_nextday=CURRENT_TIMESTAMP WHERE id='"+did+"';";
                                
                            else
                                //sql = "UPDATE tbl_employee_dtr SET "+f+"=CURTIME() WHERE emp_id='"+id+"' AND dtr_date=DATE_FORMAT(CURDATE(),'%Y-%m-%d')";
                                sql = "UPDATE tbl_employee_dtr SET "+f+"=CURTIME() WHERE id='"+did+"'";
                            */
                            if(verifyYesterday(id)) {
                                String query = "UPDATE tbl_employee_dtr SET timeout_nextday=CURRENT_TIMESTAMP, sync=false WHERE id='"+did+"'";
                                st = connDB.connect().prepareStatement(query);
                                st.executeUpdate();
                                sql = "INSERT INTO tbl_employee_dtr(emp_id,"+f+",dtr_date,machine_id) VALUES ('"+id+"','"+timeNow+"',DATE_FORMAT(CURDATE(),'%Y-%m-%d'),'"+Config.MACHINE_ID+"')";
                            }
                            else {
                                MainGUI.infoBox("You don't have any TIME IN yet.","Warning!");
                                return 0;
                            }
                                //sql = "INSERT INTO tbl_employee_dtr(emp_id,"+f+",dtr_date,machine_id) VALUES ('"+id+"',CURTIME(),DATE_FORMAT(CURDATE(),'%Y-%m-%d'),'"+Config.MACHINE_ID+"')";
                    } else
                        sql = "INSERT INTO tbl_employee_dtr(emp_id,"+f+",dtr_date,machine_id) VALUES ('"+id+"','"+timeNow+"',DATE_FORMAT(CURDATE(),'%Y-%m-%d'),'"+Config.MACHINE_ID+"')";
                    
                }
                //System.out.println(sql);
                //  connect DB
                st = connDB.connect().prepareStatement(sql);
                //  run SQL query
                st.executeUpdate();
                
            }catch(SQLException se) {
                try {
                    
                    //Handle errors for JDBC
                    //System.out.println(se);
                    MainGUI.infoBox("Sorry Information not Found","Error!");
                    return 0;
                } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                    Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
            }

            //prompts dialog box and updates employee
            //attendance card display
            //if process encountered no error
            //return 1 and is ready again for another process
            if(MainGUI.infoBox("Thank you "+name,"Verified"))
                setEmpCard(f);
            
//            if(id.equals("001172")) {
//                MainGUI mg = new MainGUI();
//                mg.congrats("Congratulations doc! Thank you for all your services, wishing you all the best! See you around.");
//            }
    
            
//            if(isBirthday(birthdate)) {
//                MainGUI mg = new MainGUI();
//                mg.happyBirthday();
//            }
//                
            return 1;
        }catch(UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            Logger.getLogger(Crud.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public String setAMorPM() throws ParseException {
        
        //format of the string (eg: 8:00:02) 
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
 
        //parse string for time calculations
        Date t = dateFormat.parse(timeNow);
        
        Date time1 = dateFormat.parse("8:00:00");
        Date time2 = dateFormat.parse("12:00:00");
        Date time3 = dateFormat.parse("13:00:00");
        Date time4 = dateFormat.parse("00:00:00");
        
        if(MainGUI.timeType==0) {
            if(tiAM!=null&&toAM!=null)
                return "timein_pm";
            else if(t.compareTo(time1)==0||(t.after(time1)&&t.before(time2))||t.before(time1))  
                return "timein_am";
            else
                return "timein_pm";
        } else
            if(t.compareTo(time2)==0||(t.after(time4)&&t.before(time3)))
                return "timeout_am";
       
        return "timeout_pm";
    }
       
    /*
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
    */
    /*   
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
    */
    
    public boolean verifyYesterdayOut(String id) throws SQLException, ParseException {
        
        sql = "SELECT id, DATE_FORMAT(timein_am,'%h:%i %p') AS amin, "
                            + "DATE_FORMAT(timeout_am,'%h:%i %p') AS amout, " 
                            + "DATE_FORMAT(timein_pm,'%h:%i %p') AS pmin, "
                            + "DATE_FORMAT(timeout_pm,'%h:%i %p') AS pmout, "
                            + "timeout_nextday "
                            + "FROM tbl_employee_dtr "
                            + "WHERE emp_id ='"+id+"' ORDER BY dtr_date DESC LIMIT 1";
        //System.out.println(sql);
        st = connDB.connect().prepareStatement(sql);
        ResultSet r = st.executeQuery();
        
        if(r.next()) {
            
                String amin = r.getString("amin");
                String pmin = r.getString("pmin");
                String amout = r.getString("amout");
                String pmout = r.getString("pmout");
                String ytout = r.getString("timeout_nextday");

                if(amin!=null&&ytout!=null)
                    return false;
                else if(pmin!=null&&ytout!=null)
                    return false;
                else if(amin!=null&&amout!=null)
                    return false;
                else if(pmin!=null&&pmout!=null)
                    return false;
                else if(amout!=null&&ytout==null)
                    return false;
                else if(pmout!=null&&ytout==null)
                    return false;
                else
                    return true;
            
        } 
        return false;
    }
    
    public boolean verifyYesterday(String id) throws SQLException, ParseException {
    
        String amin,pmin,amout,pmout;
        //sql = "SELECT id,timein_am,timeout_am,timein_pm,timeout_pm FROM tbl_employee_dtr WHERE emp_id='"+id+"' AND dtr_date=DATE_ADD(CURDATE(), INTERVAL -1 day)";
        
        sql = "SELECT id, DATE_FORMAT(timein_am,'%h:%i %p') AS amin, "
                            + "DATE_FORMAT(timeout_am,'%h:%i %p') AS amout, " 
                            + "DATE_FORMAT(timein_pm,'%h:%i %p') AS pmin, "
                            + "DATE_FORMAT(timeout_pm,'%h:%i %p') AS pmout "
                            + "FROM tbl_employee_dtr "
                            + "WHERE emp_id ='"+id+"' ORDER BY dtr_date DESC LIMIT 1";
        //System.out.println(sql);
        st = connDB.connect().prepareStatement(sql);
        ResultSet r = st.executeQuery();
            if(r.next() != false) {
             amin = r.getString("amin");
             pmin = r.getString("pmin");
             amout = r.getString("amout");
             pmout = r.getString("pmout");
             //System.out.println("Get Yesterday: " + sql);
             if(amin==null&&pmin==null)
                return false;
             else if(amout!=null&&pmout!=null)
                 return false;
             else if(amin!=null&&amout!=null)
                 return false;
             else if(pmin!=null&&pmout!=null)
                 return false;
             else if(amin!=null&&pmout!=null)
                 return false;
             else {
                 did = r.getString("id");
                 tiAM = amin;
                 toAM = amout;
                 tiPM = pmin;
                 toPM = pmout;
                 //yestertime = 1;
                  return true;
                }
            } else
                return false;
    }
    
    /*
    public String getDate() {
        //returns the system date now in a string format
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
	//get current date time with Date()
	Date date = new Date();
	String dtt = dateFormat.format(date);
        return dtt;
    }
    */
    
    public String getTimeNow(int flag) throws IOException, ParseException {
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        //returns the system time now in a string format
        //DateFormat df = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        
        Date date = dateFormatParser.parse(getServerDate()); 
        timeToDisplay = dateFormat.format(date);
        switch (flag) {
            case 1:
                dateFormat = new SimpleDateFormat("HH:mm:ss");
                break;
            case 0:
                dateFormat = new SimpleDateFormat("hh:mm:ss a");
                break;
            case 4:
                dateFormat = new SimpleDateFormat("hh:mm a E");
                break;
            default:
                dateFormat = new SimpleDateFormat("hh:mm a");
                break;
        }
        //get current date time with Date()
	String tm = dateFormat.format(date);
        
        return tm;
    }
    
    public String getServerDate() throws UnknownHostException, IOException {
//        InetAddress addr = InetAddress.getByName(Config.SERVERIP);
//        Socket soc=new Socket(addr.getHostAddress(),5217);        
//        BufferedReader in=new BufferedReader(new InputStreamReader(soc.getInputStream()));
//        return in.readLine();

        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy");
        format.setTimeZone(TimeZone.getTimeZone("Asia/Singapore"));
        return format.format( new Date()   );
    }
    
    public void setEmpCard(String attnType) throws ParseException, IOException {
        
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
        
        if (attnType.equalsIgnoreCase("timein_am"))
            MainGUI.lblAMin.setText(timeToDisplay);
        else if (attnType.equalsIgnoreCase("timeout_am"))
            MainGUI.lblAMout.setText(timeToDisplay);
        else if (attnType.equalsIgnoreCase("timein_pm"))
            MainGUI.lblPMin.setText(timeToDisplay);
        else if (attnType.equalsIgnoreCase("timeout_pm"))
            MainGUI.lblPMout.setText(timeToDisplay);
        
    }
    
    public HashMap<String, String> getAnnouncements() throws SQLException, IOException, ParseException {
        HashMap<String, String> hashMap = new HashMap<>();
        Date date = dateFormatParser.parse(getServerDate());
        DateFormat dateFormatYear = new SimpleDateFormat("YYYY");
        DateFormat dateFormatMonth = new SimpleDateFormat("MM");
        DateFormat dateFormat2 = new SimpleDateFormat("E, dd MMM yyyy");
        //String firstDay = dateFormatYear.format(date)+"-"+dateFormatMonth.format(date)+"-01";
        //String lastDay = dateFormatYear.format(date)+"-12-31";
        
        ConnectDB cnx = new ConnectDB();
        //String sqlx = "SELECT * from tbl_bulletins where date between '"+firstDay+"' and '"+lastDay+"' order by date";
        
        //PreparedStatement stx = cnx.connect().prepareStatement(sqlx);
        //ResultSet rs = stx.executeQuery();
//                
//        while(rs.next()) {
//            String title = rs.getString("title");
//            String dateText = dateFormat2.format(rs.getDate("date"));
//            String subject = dateText+"\n"+rs.getString("subject");
//            hashMap.put(title, subject);
//        }
            
        
        return hashMap;
    }
}