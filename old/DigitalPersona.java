/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.EnumMap;
import java.util.concurrent.LinkedBlockingQueue;

import com.digitalpersona.onetouch.*;
import com.digitalpersona.onetouch.capture.DPFPCapture;
import com.digitalpersona.onetouch.capture.DPFPCapturePriority;
import com.digitalpersona.onetouch.capture.event.DPFPDataEvent;
import com.digitalpersona.onetouch.capture.event.DPFPDataListener;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusAdapter;
import com.digitalpersona.onetouch.capture.event.DPFPReaderStatusEvent;
import com.digitalpersona.onetouch.processing.DPFPEnrollment;
import com.digitalpersona.onetouch.processing.DPFPFeatureExtraction;
import com.digitalpersona.onetouch.processing.DPFPImageQualityException;
import com.digitalpersona.onetouch.readers.DPFPReaderDescription;
import com.digitalpersona.onetouch.readers.DPFPReadersCollection;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;

public class DigitalPersona {
	//static EnumMap<DPFPFingerIndex, DPFPTemplate> templates;
	static EnumMap<DPFPFingerIndex, DPFPTemplate> templates = new EnumMap<DPFPFingerIndex, DPFPTemplate>(DPFPFingerIndex.class);
        static ArrayList<String> empNum = new ArrayList<String>();
        private static File fn;
        
        public void DigitalPersona() {
                
                
		//DPFPTemplate temp = dp.getTemplate(null, 1);
		//byte[] a = temp.serialize();
                //DPFPTemplate temptemp = DPFPGlobal.getTemplateFactory().createTemplate();
		//dp.insert("000127", b);
                //temptemp.deserialize(a);
		//byte[] b = dp.get();
		
		//DPFPTemplate temp2 = DPFPGlobal.getTemplateFactory().createTemplate();
		//temp2.deserialize(b);
		//System.out.println(b);
                //System.out.println(temp2);
	
                listReaders();
                DigitalPersona dp = new DigitalPersona();
                MainGUI mgui = new MainGUI();
                //Crud c = new Crud();
                
                if(!dp.verify(null))
                    //MainGUI.infoBox("Please try again!", "Information");
                    mgui.setBox("red");
        
        }
        
	public Connection cn() {
		Connection conn = null;
		try { Class.forName(Config.JDBC_DRIVER);
		conn = DriverManager.getConnection("jdbc:mysql://localhost/hris", Config.USER, "k1tk@t");
		} catch(Exception e) { 
                    System.out.println(e); 
                }
		
		return conn;
	}
	
	/*
	public void register(String idNo) {
            DigitalPersona dp = new DigitalPersona();
            DPFPTemplate temp = dp.getTemplate(null, 1);
            byte[] a = temp.serialize();
            //DPFPTemplate temptemp = DPFPGlobal.getTemplateFactory().createTemplate();
            dp.insert(idNo, a);
                
        }
        
        public void insert(String id, byte[] digital){
		PreparedStatement st;
		try { 
			st = cn().prepareStatement("INSERT INTO employees(IDNo, FingerPrint) VALUES(?, ?)");
			st.setString(1, id);
			st.setBytes(2, digital);                        
			st.executeUpdate();
		} catch (SQLException e) {  
                    System.out.println(e.getMessage()); 
                }
              
	}
        
	public ArrayList<byte[]> get(){ 
            ArrayList<byte[]> lp = new ArrayList<byte[]>();
            lp.clear();
            empNum.clear();
                ResultSet rs;
		PreparedStatement st;
                byte[] digital = null;
		try { 
			st = cn().prepareStatement("SELECT IDNo, FingerPrint FROM employees");
			rs = st.executeQuery();
			
                       while(rs.next()) {
				digital = rs.getBytes("FingerPrint");
                                lp.add(digital);
                                empNum.add(rs.getString("IDNo"));
                       }
			 
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} 
		 
		return lp;
	} 
	*/
	
	public static void listReaders() { 
        DPFPReadersCollection readers = DPFPGlobal.getReadersFactory().getReaders();
        if (readers == null || readers.size() == 0) {
            MainGUI.infoBox("There are no readers available.","Warning");
            return; 
        } 
        System.out.printf("Available readers:\n");
        for (DPFPReaderDescription readerDescription : readers)
            System.out.println(readerDescription.getSerialNumber());
    } 

	public static final EnumMap<DPFPFingerIndex, String> fingerNames;
    static { 
    	fingerNames = new EnumMap<DPFPFingerIndex, String>(DPFPFingerIndex.class);
    	fingerNames.put(DPFPFingerIndex.LEFT_PINKY,	  "left pinky");
    	fingerNames.put(DPFPFingerIndex.LEFT_RING,    "left ring");
    	fingerNames.put(DPFPFingerIndex.LEFT_MIDDLE,  "left middle");
    	fingerNames.put(DPFPFingerIndex.LEFT_INDEX,   "left index");
    	fingerNames.put(DPFPFingerIndex.LEFT_THUMB,   "left thumb");
    	fingerNames.put(DPFPFingerIndex.RIGHT_PINKY,  "right pinky");
    	fingerNames.put(DPFPFingerIndex.RIGHT_RING,   "right ring");
    	fingerNames.put(DPFPFingerIndex.RIGHT_MIDDLE, "right middle");
    	fingerNames.put(DPFPFingerIndex.RIGHT_INDEX,  "right index");
    	fingerNames.put(DPFPFingerIndex.RIGHT_THUMB,  "right thumb");
    } 
    
	public DPFPTemplate getTemplate(String activeReader, int nFinger) {
        System.out.printf("Performing fingerprint enrollment...\n");
         
        DPFPTemplate template = null;
         
        try { 
            DPFPFingerIndex finger = DPFPFingerIndex.values()[nFinger];
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPEnrollment enrollment = DPFPGlobal.getEnrollmentFactory().createEnrollment();
             
            while (enrollment.getFeaturesNeeded() > 0)
            { 
                DPFPSample sample = getSample(activeReader, 
                	String.format("Scan your %s finger (%d remaining)\n", fingerName(finger), enrollment.getFeaturesNeeded()));
                if (sample == null)
                    continue; 
 
 
                DPFPFeatureSet featureSet;
                try { 
                    featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_ENROLLMENT);
                } catch (DPFPImageQualityException e) {
                    System.out.printf("Bad image quality: \"%s\". Try again. \n", e.getCaptureFeedback().toString());
                    continue; 
                } 
 
 
                enrollment.addFeatures(featureSet);
            } 
            template = enrollment.getTemplate();
            System.out.printf("The %s was enrolled.\n", fingerprintName(finger));
        } catch (DPFPImageQualityException e) {
            System.out.printf("Failed to enroll the finger.\n");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } 
         
        return template;
    }
	
	public boolean verify(String activeReader) {
           
        DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
	
        Crud c = new Crud();
        
        try { 
            DPFPSample sample = getSample(activeReader, "Scan your finger\n");
            if (sample == null)
                throw new Exception();
 
            DPFPFeatureExtraction featureExtractor = DPFPGlobal.getFeatureExtractionFactory().createFeatureExtraction();
            DPFPFeatureSet featureSet = featureExtractor.createFeatureSet(sample, DPFPDataPurpose.DATA_PURPOSE_VERIFICATION);
			 
            DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
            matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);
            
            BufferedReader br = null; 
		
		try {
                    
                    File indexDir = new File("C:\\index"); //default index file save location
			
                    for (final File filename : indexDir.listFiles()) {
			
                        //fn = filename;
                        
                        br = new BufferedReader(new FileReader(filename)); //read JSON format array in the index file
                        String thisLine = br.readLine();
                        
                        JSONObject jo = new JSONObject(thisLine);
                        
                        JSONArray empArray = new JSONArray(jo.get("Employees").toString());
                        
                        for(int i=0; i<empArray.length();i++) {
                        
                            JSONObject empObject = empArray.getJSONObject(i);
                            
                            byte[] digital = DatatypeConverter.parseBase64Binary(empObject.getString("fp1"));
                            byte[] digital2 = DatatypeConverter.parseBase64Binary(empObject.getString("fp2"));

                            template.deserialize(digital); //deserialize fingerprint
                            DPFPVerificationResult result = matcher.verify(featureSet, template);

                            template.deserialize(digital2); //deserialize fingerprint
                            DPFPVerificationResult result2 = matcher.verify(featureSet, template);

                            if (result.isVerified()||result2.isVerified()) {

                                c.newAttendance(empObject.getString("emp_id"),empObject.getString("name"),empObject.getString("position"),empObject.getString("dept"));
                                return true;
                            }
                        } 
                    }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
                    try {
			if (br != null)br.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
		}
            /************************
            //query fingerprints
            PreparedStatement st = cn().prepareStatement("SELECT * FROM tbl_employee INNER JOIN tbl_position INNER JOIN tbl_department "+
                    " ON `tbl_employee`.`position_id` = `tbl_position`.`position_id` AND `tbl_employee`.`department_id` = `tbl_department`.`department_id`" +
                    " WHERE `tbl_employee`.`fingerprint_1` != '' AND `tbl_employee`.`is_active` = 'Y'");
            ResultSet rs = st.executeQuery();
                        
            while(rs.next()) {
                
                //save fingerprint and corresponding ID number to variables
                byte[] digital = rs.getBytes("fingerprint_1");
                byte[] digital2 = rs.getBytes("fingerprint_2");
                
                if(digital2==null)
                    continue;

                template.deserialize(digital); //deserialize fingerprint
                //for (DPFPFingerIndex finger : DPFPFingerIndex.values()) {
                    //DPFPTemplate template = user.getTemplate(finger); 
                DPFPVerificationResult result = matcher.verify(featureSet, template);
                template.deserialize(digital2); //deserialize fingerprint
                DPFPVerificationResult result2 = matcher.verify(featureSet, template);

                if (result.isVerified()||result2.isVerified()) {

                    //        System.out.printf("Matching finger: %s, FAR achieved: %g.\n",
                    //        		fingerName(finger), (double)result.getFalseAcceptRate()/DPFPVerification.PROBABILITY_ONE);        
                    String n = rs.getString("lastname")+", "+rs.getString("firstname");
                    String p = rs.getString("position_title");
                    String d = rs.getString("department");
                    c.newAttendance(rs.getString("emp_id"),n,p,d);
                    return true;
                } 
              //  } 
            }
        */ 
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("Failed to perform verification.");
        } 
        
        return false; 
    } 
    
    public boolean createIndex() {
	
        try {
            //query fingerprints
            PreparedStatement st = cn().prepareStatement("SELECT * FROM tbl_employee INNER JOIN tbl_position INNER JOIN tbl_department "+
                    " ON `tbl_employee`.`position_id` = `tbl_position`.`position_id` AND `tbl_employee`.`department_id` = `tbl_department`.`department_id`" +
                    " WHERE `tbl_employee`.`fingerprint_1` != '' AND `tbl_employee`.`is_active` = 'Y'");
            ResultSet rs = st.executeQuery();
            
            JSONArray arremp = new JSONArray();
            JSONObject emplist = new JSONObject();
            
            int ctr = 0;
            
            while(rs.next()) {
                
                JSONObject empinfo = new JSONObject();
            
                //save fingerprint and corresponding ID number to variables
                String digital = DatatypeConverter.printBase64Binary(rs.getBytes("fingerprint_1"));
                String digital2 = DatatypeConverter.printBase64Binary(rs.getBytes("fingerprint_2"));
                
                String name = rs.getString("lastname")+", "+rs.getString("firstname");
                String position = rs.getString("position_title");
                String dept = rs.getString("department");
                String eid = rs.getString("emp_id");
                
                empinfo.put("emp_id",eid);
                empinfo.put("name",name);
                empinfo.put("position",position);
                empinfo.put("dept",dept);
                empinfo.put("fp1",digital);
                empinfo.put("fp2",digital2);
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
		
        File file = new File("C:\\index\\Employees.txt"); // create a new file
		
	try (FileWriter fw = new FileWriter(file)) {
		fw.write(emp.toString()); // write the JSON format into file
	}		
    }
    
    public DPFPSample getSample(String activeReader, String prompt)
	throws InterruptedException
	{ 
	    final LinkedBlockingQueue<DPFPSample> samples = new LinkedBlockingQueue<DPFPSample>();
	    DPFPCapture capture = DPFPGlobal.getCaptureFactory().createCapture();
	    capture.setReaderSerialNumber(activeReader);
	    capture.setPriority(DPFPCapturePriority.CAPTURE_PRIORITY_LOW);
	    capture.addDataListener(new DPFPDataListener()
	    { 
	        public void dataAcquired(DPFPDataEvent e) {
	            if (e != null && e.getSample() != null) {
	                try { 
	                    samples.put(e.getSample());
	                } catch (InterruptedException e1) {
	                    e1.printStackTrace();
	                } 
	            } 
	        } 
	    }); 
	    capture.addReaderStatusListener(new DPFPReaderStatusAdapter()
	    { 
	    	int lastStatus = DPFPReaderStatusEvent.READER_CONNECTED;
			public void readerConnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					MainGUI.infoBox("Reader is connected","Information");
				lastStatus = e.getReaderStatus();
			} 
			public void readerDisconnected(DPFPReaderStatusEvent e) {
				if (lastStatus != e.getReaderStatus())
					MainGUI.infoBox("Reader is disconnected","Warning");
				lastStatus = e.getReaderStatus();
			} 
	    	 
	    }); 
	    try { 
	        capture.startCapture();
	        System.out.print(prompt);
	        return samples.take();
	    } catch (RuntimeException e) {
	        System.out.printf("Failed to start capture. Check that reader is not used by another application.\n");
	        throw e;
	    } finally { 
	        capture.stopCapture();
	    } 
	} 
 
 
    public String fingerName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger); 
    } 
    public String fingerprintName(DPFPFingerIndex finger) {
    	return fingerNames.get(finger) + " fingerprint"; 
    } 
    
}