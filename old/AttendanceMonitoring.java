package attendancemonitoring;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Benoe
 */
public class AttendanceMonitoring {
   
    //public static JLabel jlabel = new JLabel();
    //private static JTextField txtIn = new JTextField();
    //private static JFrame jframe = new JFrame();
    
   public AttendanceMonitoring () {
          
   }
   
   public static void main(String[] args) throws ParseException  {
       
       DigitalPersona dp = new DigitalPersona();
       
       try {
       //   Call the GUI
       MainGUI myGUI = new MainGUI();
       myGUI.setVisible(true);
    
       //Crud c = new Crud();
       //c.newAttendance("000856", "BENOE PADAMADA", "CMT III", "IHOMP");
       
       while(myGUI.isShowing()) 
        dp.DigitalPersona();
      
       } catch (Exception ex) {
           Logger.getLogger(AttendanceMonitoring.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
}
