/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benoe
 */
public class ComputeAttendance {
    
    public double compute(String TimeNow, String DateTimeSched, int dedType){
        double x=0.0;
        long diff=0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD HH:mm:ss");
	//get current date time with Date()
	try {
           Date time1 = dateFormat.parse(DateTimeSched);
           Date time2 = dateFormat.parse(TimeNow);
           
           if(dedType==0)
                diff = time2.getTime()-time1.getTime();
           else 
                diff = time1.getTime()-time2.getTime();
           
           if(diff>0)
               x = (diff/1000)/60;
           
       } catch (ParseException ex) {
           Logger.getLogger(AttendanceMonitoring.class.getName()).log(Level.SEVERE, null, ex);
       }
         return x;     
    }
    
    public double computeFlexi(String TimeNow, String TiAMSched, String ToAMSched, String TiPMSched, String ToPMSched,String schedDate) {
        double x = 0.0;
        long hrs;
        long diff = 0;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
        //get current date time with Date()
	try {
            
            Date time1 = dateFormat.parse(TimeNow);
            Date time2 = dateFormat.parse(TiAMSched);
            Date time5 = dateFormat.parse(schedDate+" "+ToPMSched);
            System.out.println("AM in sched: "+time2);
            System.out.println("PM out sched: "+time5);
            if(ToAMSched==null || TiPMSched==null) {
                
                hrs = time5.getTime() - time2.getTime();
                diff = hrs - (time1.getTime() - time2.getTime());
            
            } else {
                
                Date time3 = dateFormat.parse(schedDate+" "+ToAMSched);
                Date time4 = dateFormat.parse(schedDate+" "+TiPMSched);
                
                hrs = (time3.getTime() - time2.getTime()) + (time5.getTime() - time4.getTime());
                diff = hrs - (time1.getTime() - time2.getTime());
            }
            
            if(diff>0)
               x = (diff/1000)/60;
           
       } catch (ParseException ex) {
           Logger.getLogger(AttendanceMonitoring.class.getName()).log(Level.SEVERE, null, ex);
       }
        
        return x;
    }
}
