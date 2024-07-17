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
public class setClockTime implements Runnable {
        Thread runner;
        Crud getT = new Crud();
        
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
            MainGUI.lblTime.setText(getT.getTimeNow(0));
            
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
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
        
    }
