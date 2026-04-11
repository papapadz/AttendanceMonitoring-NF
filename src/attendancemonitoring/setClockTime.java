/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;
import java.awt.Color;
import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Benoe
 */
public class setClockTime implements Runnable {
        Thread runner;
        Crud getT = new Crud();
        int ctr = 0;
        
        public setClockTime() {
            start();
        }
       
         public void start()
        {
            if(runner == null) runner = new Thread(this);
            runner.start();
                                                             //method to start thread
        }
        
        @Override
        public void run() {
            
            while (runner == Thread.currentThread() )
            { 
                
                                                         
           try
           {
               
               MainGUI.lblTime.setText(getT.getTimeNow(0));
               MainGUI.refreshCalendar();
               ctr++;
               if(ctr>=6) {
                   MainGUI.resetUI();
                   ctr = 0;
               }
               
               //define thread task
               
               try
               {
                   Thread.sleep(1000);
               }
               catch(InterruptedException e)
               {
                   System.out.println("Thread failed");
               }
           }
              catch(IOException ex)
                  {
                    Logger.getLogger(setClockTime.class.getName()).log(Level.SEVERE, null, ex);
                  } catch (ParseException ex) {
                    Logger.getLogger(setClockTime.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
