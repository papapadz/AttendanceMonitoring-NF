/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;

import java.awt.Color;

/**
 *
 * @author User
 */
public class boxTimer extends MainGUI implements Runnable {
        Thread timerunner;
        javax.swing.JPanel panelboi1;
        javax.swing.JPanel panelboi2;
        javax.swing.JPanel panelboi3;
        public boxTimer(javax.swing.JPanel receive1,javax.swing.JPanel receive2,javax.swing.JPanel receive3) {
            panelboi1 = receive1;
            panelboi2 = receive2;
            panelboi3 = receive3;
            start();
            
        }
       
         public void start()
        {
            if(timerunner == null) timerunner = new Thread(this);
            timerunner.start();
                                                             //method to start thread
        }
        
        @Override
        public void run() {
            
            try
             {
                Thread.sleep(1500);
             }
              catch(InterruptedException e)
                  {
                    System.out.println("Thread failed");
                  }
            
            panelboi1.setBackground(Color.white);
            panelboi2.setBackground(Color.white);
            panelboi3.setBackground(Color.white);
                
        }
        
    }
