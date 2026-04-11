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
    private static MainGUI myGUI = new MainGUI();
    private static DigitalPersona dp = new DigitalPersona();
    
   public AttendanceMonitoring () {
       
   }
   
   public static void main(String[] args) throws ParseException  {
       
       
       try {
       //   Call the GUI
//       System.out.print(System.getProperty("user.dir")+"\\index");
       start();
       //Crud c= new Crud();
       //myGUI.setAnnouncements(c.getAnnouncements());
       
       
      
       } catch (Exception ex) {
           Logger.getLogger(AttendanceMonitoring.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
   
   public static void start() {
       if(pull()) {
            show();
            while(myGUI.isShowing()) {dp.DigitalPersona();}
       }
       
   }
   
   public static void end() {
       myGUI.setVisible(false);
   }
   
   public static void show() {
       myGUI.setVisible(true);
   }
   
   public static boolean pull() {
        try {
            if(!dp.createIndex())
                MainGUI.popUp("Operation encountered an Error! Check your connection and restart the application", "Error");
            return true;
        } catch(Exception e) {
            return false;
        }
   }
}
