/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.LinkedBlockingQueue;

import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPSample;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPDataPurpose;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
//import com.digitalpersona.onetouch.verification.DPFPVerification;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;

public class DigitalPersona {
	//static EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
//	static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
//        static ArrayList<String> empNum = new ArrayList<String>();
    static Crud c = new Crud();
        
        public void DigitalPersona() {
                
                
            try {
                DigitalPersona dp = new DigitalPersona();
                
                if(!dp.verify(null))
                    MainGUI.infoBox("Please try again!", "Information");
                
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(DigitalPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        }
	
	public static void listReaders() { 
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();
        if (readers == null || readers.isEmpty()) {
            try {
                MainGUI.infoBox("There are no readers available.","Warning"); 
                return;
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
                Logger.getLogger(DigitalPersona.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
        System.out.printf("Available readers:\n");
        readers.forEach((readerDescription) -> {
            System.out.println(readerDescription.getSerialNumber());
            });
    } 

	public boolean verify(String activeReader) {
	
        Boolean returnValue = false;
        
        try { 
            DPFPSample sample = getSample(activeReader, "Scan your finger\n");

            if (sample == null)
                throw new Exception();
               
            long startTime1 = System.currentTimeMillis();
            
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
			 
            int recordCount = 0;
            JSONArray empArray = new JSONArray();
            BufferedReader br = null; 
            try {

                File indexDir = new File(System.getProperty("user.dir")+"\\index"); //default index file save location
                for (final File filename : indexDir.listFiles()) {

                    br = new BufferedReader(new FileReader(filename)); //read JSON format array in the index file
                    String thisLine = br.readLine();

                    JSONObject jo = new JSONObject(thisLine);
                    empArray = new JSONArray(jo.get("Employees").toString());
                    recordCount = jo.getInt("Count");
                }
            } catch (IOException e) {
                System.out.println(e);
            } finally {
                try {
                    if (br != null)br.close();
                } catch (IOException ex) {
                }
            }
            
            int coreCount = Runtime.getRuntime().availableProcessors();
            System.out.println("coreCount : "+coreCount);
            System.out.println("recordCount : "+recordCount);
            int batchCount = recordCount/coreCount;
            System.out.println("batchCount : "+batchCount);
            int start = 0;
            int end = 0;
     
            ExecutorService verifierExecutor = Executors.newWorkStealingPool();
            List<Future<JSONObject>> callableList = new ArrayList<>();
            
            for(int i=0; i<coreCount; i++){

                start = end;
                if (i == coreCount-1)
                    end = recordCount;
                else
                    end += batchCount;
                System.out.println("Start : "+start+", End : "+end);
                Callable<JSONObject> callableVerifier = new CallableVerifier(featureSet, "Process : "+i, start, end, empArray);
                Future<JSONObject> verifierObject = verifierExecutor.submit(callableVerifier);
                callableList.add(verifierObject);
            }
//            int FirstSet = batchCount;
//            int SecondSet = FirstSet + batchCount;
//            int ThirdSet = SecondSet + batchCount;
//            int FinalSet = recordCount;
            //Create MyCallable instance
//            DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
//            matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
//            DPFPVerification matcher2 = DPFPGlobal.getVerificationFactory().createVerification();
//            matcher2.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
//            DPFPVerification matcher3 = DPFPGlobal.getVerificationFactory().createVerification();
//            matcher3.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
//            DPFPVerification matcher4 = DPFPGlobal.getVerificationFactory().createVerification();
//            matcher4.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
//            Callable<JSONObject> callable1 = new CallableVerifier(matcher, featureSet, "External - 1", 0, FirstSet, empArray);
//            Callable<JSONObject> callable2 = new CallableVerifier(matcher2, featureSet, "External - 2", FirstSet, SecondSet, empArray);
//            Callable<JSONObject> callable3 = new CallableVerifier(matcher3, featureSet, "External - 3", SecondSet, ThirdSet, empArray);
//            Callable<JSONObject> callable4 = new CallableVerifier(matcher4, featureSet, "External - 4", ThirdSet, FinalSet, empArray);
//            Future<JSONObject> future1 = verifierExecutor.submit(callable1);
//            Future<JSONObject> future2 = verifierExecutor.submit(callable2);
//            Future<JSONObject> future3 = verifierExecutor.submit(callable3);
//            Future<JSONObject> future4 = verifierExecutor.submit(callable4);
//            callableList.add(future1);
//            callableList.add(future2);
//            callableList.add(future3);
//            callableList.add(future4);

            for(Future<JSONObject> futureVerifier : callableList){
                try {
                    JSONObject futureObject = futureVerifier.get();
                    if(futureObject.has("emp_id")){
                        returnValue = true;
                        System.out.println("Forcefully Shutting Down Executor");
                        verifierExecutor.shutdownNow();
                        c.newAttendance(futureObject.getString("emp_id"),futureObject.getString("name"),futureObject.getString("position"),futureObject.getString("dept"),futureObject.getString("birthdate"));
                        
                    }
                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Exception : "+e);
                }
            }
            
            if(!verifierExecutor.isShutdown()){
                System.out.println("Shutting Down Executor");
                verifierExecutor.shutdown();
            }
            
//            if(!com.mysql.jdbc.AbandonedConnectionCleanupThread.interrupted())
//                com.mysql.jdbc.AbandonedConnectionCleanupThread.shutdown();
            
            long endTime1 = System.currentTimeMillis();
            System.out.println("Verification took " + (endTime1 - startTime1) + " milliseconds");
        } catch (Exception e) {
            
            System.out.println(e+"Failed to perform verification.");
        }
        return returnValue; 
    } 
    
    public boolean createIndex() {
	
        try {
            
            ConnectDB cn = new ConnectDB();
            
            //query fingerprints
//            PreparedStatement st = cn.connect().prepareStatement("SELECT birthdate,lastname,firstname,position_title,tbl_department.department,tbl_employee.emp_id,fingerprint_1,fingerprint_2  FROM tbl_employee INNER JOIN tbl_position INNER JOIN tbl_department "+
//                    " ON `tbl_employee`.`position_id` = `tbl_position`.`position_id` AND `tbl_employee`.`department_id` = `tbl_department`.`department_id`" +
//                    " WHERE `tbl_employee`.`fingerprint_1` != '' AND `tbl_employee`.`is_active` = 'Y'");
            PreparedStatement st = cn.connect().prepareStatement(
                    "SELECT employee_id,first_name,middle_name,last_name,title,name,fingerprint_1,fingerprint_2,birth_date "
                            + "FROM person_affiliations inner join `nf`.`positions` on `positions`.`id` = `person_affiliations`.`position_id` "
                            + "INNER JOIN `people` ON `person_affiliations`.`person_id` = `people`.`id` "
                            + "INNER JOIN `company_areas` ON `person_affiliations`.`company_area_id` = `company_areas`.`id`"
                            + "WHERE fingerprint_1 != ''");
            ResultSet rs = st.executeQuery();
            
            JSONArray arremp = new JSONArray();
            JSONObject emplist = new JSONObject();
            
            int ctr = 0;
            
            while(rs.next()) {
                
                JSONObject empinfo = new JSONObject();
            
                //save fingerprint and corresponding ID number to variables
                String digital = DatatypeConverter.printBase64Binary(rs.getBytes("fingerprint_1"));
                String digital2 = DatatypeConverter.printBase64Binary(rs.getBytes("fingerprint_2"));
                
                String name = rs.getString("last_name")+", "+rs.getString("first_name");
                String position = rs.getString("title");
                String dept = rs.getString("name");
                String eid = rs.getString("employee_id");
                String birthdate = rs.getString("birth_date");
                empinfo.put("emp_id",eid);
                empinfo.put("name",name);
                empinfo.put("position",position);
                empinfo.put("dept",dept);
                empinfo.put("fp1",digital);
                empinfo.put("fp2",digital2);
                empinfo.put("birthdate",birthdate);
                arremp.put(empinfo);
                ctr++;
                //System.out.println(empinfo.getString("fp1").getBytes());
            }
            Date now = new Date();
            emplist.put("Employees",arremp);
            emplist.put("Count",ctr);
            emplist.put("Date",now);
            writeFile(emplist);
            return true;
            
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    } 
    
    private static void writeFile(JSONObject emp) throws Exception{
	String dir = System.getProperty("user.dir");
        File file = new File(dir+"\\index\\Employees.txt"); // create a new file
		
	try (FileWriter fw = new FileWriter(file)) {
		fw.write(emp.toString()); // write the JSON format into file
	}		
    }
    
    public DPFPSample getSample(String activeReader, String prompt)
	throws InterruptedException
	{ 
	    final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<>();
	    DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
	    capture.setReaderSerialNumber(activeReader);
	    capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
	    capture.addDataListener((DPFPDataEvent e) -> {
                if (e != null && e.getSample() != null) {
                    try {
                        samples.put(e.getSample());
                    } catch (InterruptedException e1) {
                    }
                }
            }); 
	    capture.addReaderStatusListener(new DPFPReaderStatusAdapter()
	    { 
	    	int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
                    @Override
			public void readerConnected(DPFPReaderStatusEvent e) {
                            if (lastStatus != e.getReaderStatus())
                                MainGUI.popUp("Reader is connected","Information");
                            lastStatus = e.getReaderStatus();
			} 
                    @Override
			public void readerDisconnected(DPFPReaderStatusEvent e) {
                            if (lastStatus != e.getReaderStatus())
                                MainGUI.popUp("Reader is disconnected","Warning");
                            lastStatus = e.getReaderStatus();
                        } 
	    	 
	    }); 
            
	    try { 
	        capture.startCapture();
	        System.out.print(prompt);
	        return samples.take();
	    } catch (RuntimeException e) {
	        MainGUI.popUp(e+"Failed to start capture. Check that reader is not used by another application.\n","Error");
                System.exit(1);
                throw e;
	    } finally { 
	        capture.stopCapture();
	    } 
	} 
}