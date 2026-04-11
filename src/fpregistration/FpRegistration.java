/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fpregistration;

import attendancemonitoring.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Benoe
 */
public class FpRegistration extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public String sql="";
    
    public static HashMap<Integer, String> hashMapPositions = new HashMap<>();
    public static HashMap<Integer, String> hashMapDepartments = new HashMap<>();
    public static HashMap<Integer, String> hashMapEmpStat = new HashMap<>();
    public static String[] arrCivilStat = {"Single","Married","Widowed","Separated"};
    public static String[] arrSex = {"Male","Female"};
    public static String[] arrEmpStat = {"Regular","Casual","Contract of Service","Project Based"};
    DefaultListModel<String> listModelPositions = new DefaultListModel<>();
    DefaultListModel<String> listModelDepartments = new DefaultListModel<>();
    //public int sqlType = 0;
    public static boolean isNewEmployee = false;
    
    public static int getHashMapValue(String value, String table) {
        int r = 0;
        
        if(table.equalsIgnoreCase("positions")) {
            for (Map.Entry<Integer, String> pos : hashMapPositions.entrySet()) {
                if(pos.getValue().equalsIgnoreCase(value)) {
                    r = pos.getKey();
                    break;
                }
            }
        } else if(table.equalsIgnoreCase("departments")) {
            for (Map.Entry<Integer, String> dept : hashMapDepartments.entrySet()) {
                if(dept.getValue().equalsIgnoreCase(value)) {
                    r = dept.getKey();
                    break;
                }
            }
        } else {
            for (Map.Entry<Integer, String> stat : hashMapEmpStat.entrySet()) {
                if(stat.getValue().equalsIgnoreCase(value)) {
                    r = stat.getKey();
                    break;
                }
            }
        }
        return r;
    }
    
    public static boolean callAPI(String empID) {
            
        try {
                    /** Call an API to Pull data from server */
            // 1. Create a URL object
            URL url = new URL(Config.SERVER_URL+"/api/local-set/server/get-data/"+empID);

            // 2. Open a connection to the URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // 3. Set the request method to GET
            conn.setRequestMethod("GET");
            
            // 4. Get the response code (e.g., 200 for success)
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            
            // 5. Read the response body if the request was successful
            return (responseCode == HttpURLConnection.HTTP_OK);
                
            
        } catch (MalformedURLException ex) {
            Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public FpRegistration() {
        initComponents();
        
        try {
            ButtonImageIcon.fitImageToButton(btnQRCode, System.getProperty("user.dir")+"\\img\\qr-code.png", WIDTH);
            ButtonImageIcon.fitImageToButton(btnFPIcon, System.getProperty("user.dir")+"\\img\\fp0.png", WIDTH);    
        } catch (IOException ex) {
                Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        try {
            
            ConnectDB cn = new ConnectDB();
//            DigitalPersona conn = new DigitalPersona();

            PreparedStatement st = cn.connect().prepareStatement("Select * from positions order by title");
            ResultSet rs = st.executeQuery();
            while(rs.next())                    
                hashMapPositions.put(rs.getInt("id"), rs.getString("title"));
            
            PreparedStatement st2 = cn.connect().prepareStatement("Select * from company_areas where company_id='"+Config.COMPANY_ID+"' order by name");
            ResultSet rs2 = st2.executeQuery();
            while(rs2.next())                    
                hashMapDepartments.put(rs2.getInt("id"), rs2.getString("name"));

//            for (Map.Entry<Integer, String> pos : hashMapPositions.entrySet()) {
//                jListPos.addItem(pos.getValue());
//            } 
//            for (Map.Entry<Integer, String> dept : hashMapDepartments.entrySet()) {
//                jListDept.addItem(dept.getValue());
//            }
//            
//            for (int i = 0; i < arrCivilStat.length; i++) {
//                jListCivilStat.addItem(arrCivilStat[i]);
//            }
//            
//            for (int i = 0; i < arrSex.length; i++) {
//                jListSex.addItem(arrSex[i]);
//            }
//            
//            for (int i = 0; i < arrEmpStat.length; i++) {
//                jListEmpStat.addItem(arrEmpStat[i]);
//                hashMapEmpStat.put(i+1,arrEmpStat[i]);
//            }
                   
        } catch (SQLException e) {
            System.out.print(e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jOptionPane1 = new javax.swing.JOptionPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldFname = new javax.swing.JTextField();
        jButtonNext = new javax.swing.JButton();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jTextFieldMname = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextFieldLname = new javax.swing.JTextField();
        jButtonNext2 = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanelScanfp = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jButtonCancel2 = new javax.swing.JButton();
        jButtonScan = new javax.swing.JButton();
        btnFPIcon = new javax.swing.JButton();
        lblFPMessage = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnQRCode = new javax.swing.JButton();
        btnExitRegistration = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Fingerprint Registration Module");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel4.setBackground(new java.awt.Color(0, 153, 153));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("2. Enter/Update Employee Details");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jTextFieldFname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextFieldFname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldFname.setEnabled(false);

        jButtonNext.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonNext.setText("Next");
        jButtonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNextActionPerformed(evt);
            }
        });

        jPanel6.setBackground(new java.awt.Color(0, 153, 153));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("1. Enter Employee ID number");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jTextFieldMname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextFieldMname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldMname.setEnabled(false);
        jTextFieldMname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldMnameActionPerformed(evt);
            }
        });

        jTextField3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextField3.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });

        jTextFieldLname.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jTextFieldLname.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldLname.setEnabled(false);

        jButtonNext2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonNext2.setText("Next");
        jButtonNext2.setEnabled(false);
        jButtonNext2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNext2ActionPerformed(evt);
            }
        });

        jButtonCancel.setBackground(new java.awt.Color(255, 0, 0));
        jButtonCancel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jButtonCancel.setText("Cancel");
        jButtonCancel.setEnabled(false);
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel5.setText("First Name:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel6.setText("Middle Name:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel7.setText("Last Name:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel6))
                                .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldMname, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jTextFieldLname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldFname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNext, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldFname)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldMname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonNext2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelScanfp.setBackground(new java.awt.Color(255, 255, 255));
        jPanelScanfp.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBackground(new java.awt.Color(51, 153, 0));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("3. Scan Fingerprint");

        jButtonCancel2.setBackground(new java.awt.Color(204, 0, 0));
        jButtonCancel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButtonCancel2.setText("X");
        jButtonCancel2.setEnabled(false);
        jButtonCancel2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancel2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonCancel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        jButtonScan.setBackground(new java.awt.Color(153, 255, 0));
        jButtonScan.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jButtonScan.setText("Start Scan");
        jButtonScan.setEnabled(false);
        jButtonScan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonScanActionPerformed(evt);
            }
        });

        btnFPIcon.setBackground(new java.awt.Color(255, 255, 255));
        btnFPIcon.setBorderPainted(false);
        btnFPIcon.setEnabled(false);
        btnFPIcon.setMaximumSize(new java.awt.Dimension(200, 200));
        btnFPIcon.setMinimumSize(new java.awt.Dimension(200, 200));
        btnFPIcon.setPreferredSize(new java.awt.Dimension(200, 200));

        lblFPMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblFPMessage.setText("...");

        javax.swing.GroupLayout jPanelScanfpLayout = new javax.swing.GroupLayout(jPanelScanfp);
        jPanelScanfp.setLayout(jPanelScanfpLayout);
        jPanelScanfpLayout.setHorizontalGroup(
            jPanelScanfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelScanfpLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(jPanelScanfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonScan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFPIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblFPMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37))
        );
        jPanelScanfpLayout.setVerticalGroup(
            jPanelScanfpLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelScanfpLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonScan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnFPIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblFPMessage)
                .addGap(18, 18, 18))
        );

        jPanel1.setBackground(new java.awt.Color(0, 0, 153));

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Fingerprint Registration");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        btnQRCode.setBackground(new java.awt.Color(255, 255, 255));
        btnQRCode.setBorderPainted(false);
        btnQRCode.setMaximumSize(new java.awt.Dimension(150, 150));
        btnQRCode.setMinimumSize(new java.awt.Dimension(150, 150));
        btnQRCode.setPreferredSize(new java.awt.Dimension(150, 150));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQRCode, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        btnExitRegistration.setBackground(new java.awt.Color(255, 0, 0));
        btnExitRegistration.setForeground(new java.awt.Color(255, 255, 255));
        btnExitRegistration.setText("EXIT");
        btnExitRegistration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExitRegistrationActionPerformed(evt);
            }
        });

        jLabel8.setText("Scan the QR Code and enter");

        jLabel9.setText("Employee Details before ");

        jLabel10.setText("registering the fingerprint ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExitRegistration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jLabel8)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelScanfp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanelScanfp, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel9)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnExitRegistration))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonScanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonScanActionPerformed
        // TODO add your handling code here:
//      jTextArea1.append("Starting fingerprint reading...");
      try {
          
        lblFPMessage.setText("Scanning...");
        ButtonImageIcon.fitImageToButton(FpRegistration.btnFPIcon, System.getProperty("user.dir")+"\\img\\fp4.png", WIDTH);
        DigitalPersona dp = new DigitalPersona();
        dp.DigitalPersona(isNewEmployee);
//        dp.DigitalPersona();
        FpRegistration.infoBox("Fingerprint Registered to Employee ID No. "+jTextField3.getText(), "Information");
        //jTextFieldFname.setText("");
//        jTextArea1.setText("");
        
      jButtonScan.setEnabled(false);
      jButtonCancel2.setEnabled(false);
      setButtonsEnable(false);
      jButtonNext.setEnabled(true);
      jTextField3.setEnabled(true);
      clearAll();
      jTextFieldFname.setEnabled(false);
                jTextFieldMname.setEnabled(false);
                jTextFieldLname.setEnabled(false);
//                jListPos.setEnabled(false);
//                jListDept.setEnabled(false);
//                txtBirthdate.setEnabled(false);
//                jListCivilStat.setEnabled(false);
//                jListSex.setEnabled(false);
//                txtStartDate.setEnabled(false);
//                jListEmpStat.setEnabled(false);
        
      } catch (IOException e) {
        FpRegistration.infoBox("Fingerprint Registration caught an error!", "Error");
        jButtonScan.setEnabled(false);
        setButtonsEnable(true);
      }
    }//GEN-LAST:event_jButtonScanActionPerformed

    private void jButtonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNextActionPerformed
        // TODO add your handling code here:
        final String empID = jTextField3.getText();
        
        if(empID.length()>0) {
            
            jButtonNext.setEnabled(false);
            jTextField3.setEnabled(false);
            
            Boolean b = callAPI(empID);
            
            setButtonsEnable(true);
            
            if(b) {
                try {
                    if(checkEmpID()) {
                        jTextFieldFname.setEnabled(true);
                        jTextFieldMname.setEnabled(true);
                        jTextFieldLname.setEnabled(true);
                    } else {
                        infoBox("No Employee ID number found. Please verify if you have already registered online!","Warning");
                        jButtonNext.setEnabled(true);
                        jTextField3.setEnabled(true);
                        setButtonsEnable(false);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else 
                infoBox("No Employee ID number found. Please verify if you have already registered online!","Warning");
        }
        else
            infoBox("Invalid ID number: Try Again!","Warning");
    }//GEN-LAST:event_jButtonNextActionPerformed

    private void jButtonNext2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNext2ActionPerformed
        // TODO add your handling code here:
         if(jTextFieldFname.getText().length()>0 && jTextFieldLname.getText().length()>0) {
//             if(isValidDate(txtBirthdate.getText()) && isValidDate(txtStartDate.getText())) {
                setButtonsEnable(false);
                jButtonScan.setEnabled(true);
                jButtonCancel2.setEnabled(true);
                btnFPIcon.setEnabled(true);
//             } else
//                 infoBox("Please check date format, "+DATE_FORMAT+"!","Warning");
        }
        else
            infoBox("Please fill up all fields!","Warning");
    }//GEN-LAST:event_jButtonNext2ActionPerformed

    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
        try {
            // TODO add your handling code here:
            setButtonsEnable(false);
            jButtonNext.setEnabled(true);
            jTextField3.setEnabled(true);
            clearAll();
            jTextFieldFname.setEnabled(false);
            jTextFieldMname.setEnabled(false);
            jTextFieldLname.setEnabled(false);
//                jListPos.setEnabled(false);
//                jListDept.setEnabled(false);
//                txtBirthdate.setEnabled(false);
//                jListCivilStat.setEnabled(false);
//                jListSex.setEnabled(false);
//                txtStartDate.setEnabled(false);
//                jListEmpStat.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(FpRegistration.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButtonCancelActionPerformed

    private void jButtonCancel2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancel2ActionPerformed
        // TODO add your handling code here:
        setButtonsEnable(true);
        jButtonScan.setEnabled(false);
        jButtonCancel2.setEnabled(false);
    }//GEN-LAST:event_jButtonCancel2ActionPerformed

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jTextFieldMnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldMnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldMnameActionPerformed

    private void btnExitRegistrationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExitRegistrationActionPerformed
        // TODO add your handling code here:
        this.restart();
    }//GEN-LAST:event_btnExitRegistrationActionPerformed

    public static void restart() {
        try {
            // 1. Get the path to the current Java runtime executable
            String javaBin = System.getProperty("java.home") + File.separator + "bin" + File.separator + "java";

            // 2. Figure out how the application was originally launched
            String commandProp = System.getProperty("sun.java.command");
            
            List<String> command = new ArrayList<>();
            command.add(javaBin);

            if (commandProp != null && commandProp.endsWith(".jar")) {
                // Application was started via java -jar
                command.add("-jar");
                command.add(new File(commandProp).getPath());
            } else {
                // Application was started via java -cp (classpath)
                command.add("-cp");
                command.add(System.getProperty("java.class.path"));
                // The first word in sun.java.command is usually the main class
                command.add(commandProp != null ? commandProp.split(" ")[0] : "YourMainClassNameHere"); 
            }

            // 3. Build and start the new process
            ProcessBuilder builder = new ProcessBuilder(command);
            builder.start();

            System.out.println("New instance started. Shutting down current instance...");

            // 4. Kill the current JVM
            System.exit(0);

        } catch (IOException e) {
            System.err.println("Failed to restart the application.");
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FpRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FpRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FpRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FpRegistration.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FpRegistration().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnExitRegistration;
    public static javax.swing.JButton btnFPIcon;
    private javax.swing.JButton btnQRCode;
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonCancel2;
    private javax.swing.JButton jButtonNext;
    private javax.swing.JButton jButtonNext2;
    private javax.swing.JButton jButtonScan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    public static javax.swing.JOptionPane jOptionPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelScanfp;
    public static javax.swing.JTextField jTextField3;
    public static javax.swing.JTextField jTextFieldFname;
    public static javax.swing.JTextField jTextFieldLname;
    public static javax.swing.JTextField jTextFieldMname;
    private javax.swing.JLabel lblFPMessage;
    // End of variables declaration//GEN-END:variables

   public static void infoBox(String infoMessage, String titleBar)
    {
        jOptionPane1.showMessageDialog(null, infoMessage, titleBar, jOptionPane1.INFORMATION_MESSAGE);
        
    }
   
   public static void setFPImageProgress(int n) throws IOException {
       
       String imgPath = "\\fp0.png";
       switch(n){
           case 3: imgPath = "\\fp1.png"; break;
           case 2: imgPath = "\\fp2.png"; break;
           case 1: imgPath = "\\fp3.png"; break;
           case 0: imgPath = "\\fp4.png"; break;
       }
       ButtonImageIcon.fitImageToButton(FpRegistration.btnFPIcon, System.getProperty("user.dir")+"\\img"+imgPath, WIDTH);
   }

public void setButtonsEnable(boolean b){
    jButtonNext2.setEnabled(b);
    jButtonCancel.setEnabled(b);
    //jTextFieldFname.setEnabled(b);
    //jTextFieldMname.setEnabled(b);
    //jTextFieldLname.setEnabled(b);
    }

private boolean checkEmpID() throws SQLException {
    //   Connect to mySQL db
//    DigitalPersona conn = new DigitalPersona();
    //  Global variables needed
    try {
        PreparedStatement st;
    
        ConnectDB cn = new ConnectDB();
//          
//	st = conn.cn().prepareStatement("SELECT firstname,middlename,lastname,position_title,department FROM tbl_employee INNER JOIN `tbl_position` INNER JOIN `tbl_department` WHERE `tbl_employee`.`position_id` = `tbl_position`.`position_id` AND `tbl_employee`.`department_id` = `tbl_department`.`department_id` AND `emp_id`='"+jTextField3.getText()+"'");
	st = cn.connect().prepareStatement("SELECT first_name,middle_name,last_name,title,name,birth_date,gender,civil_status,start_date,status "
                + "FROM person_affiliations inner join `nf`.`positions` on `positions`.`id` = `person_affiliations`.`position_id`  "
                + "INNER JOIN `people` ON `person_affiliations`.`person_id` = `people`.`id` "
                + "INNER JOIN `employment_status` ON `person_affiliations`.`employment_status_id` = `employment_status`.`id` "
                + "INNER JOIN `company_areas` ON `person_affiliations`.`company_area_id` = `company_areas`.`id` "
                + "WHERE `people`.`employee_id`='"+jTextField3.getText()+"' LIMIT 1");
	
        ResultSet rs = st.executeQuery();
            //  retrieve data by column name
            if(rs.next()) {          
                isNewEmployee=false;
                jTextFieldFname.setText(rs.getString("first_name"));
                jTextFieldMname.setText(rs.getString("middle_name"));    
                jTextFieldLname.setText(rs.getString("last_name"));            
                //jTextFieldPosition.setText(rs.getString("title"));
                //jTextFieldDept.setText(rs.getString("name"));
//                txtBirthdate.setText(rs.getString("birth_date"));
//                txtStartDate.setText(rs.getString("start_date"));
//                jListPos.setSelectedItem(rs.getString("title"));
//                jListDept.setSelectedItem(rs.getString("name"));
//                jListCivilStat.setSelectedItem(rs.getString("civil_status"));
//                jListSex.setSelectedItem(rs.getString("gender"));
//                jListEmpStat.setSelectedItem(rs.getString("status"));

            return true;
            } 
//            else {
//                isNewEmployee=true;
//                jTextFieldFname.setText("");
//                jTextFieldMname.setText("");
//                jTextFieldLname.setText("");
                //jTextFieldPosition.setText("");
                //jTextFieldDept.setText("");
//                txtBirthdate.setText(DATE_FORMAT);
//                txtStartDate.setText(DATE_FORMAT);
//                jTextFieldFname.setEnabled(true);
//                jTextFieldMname.setEnabled(true);
//                jTextFieldLname.setEnabled(true);
//                jListPos.setEnabled(true);
//                jListDept.setEnabled(true);
//                txtBirthdate.setEnabled(true);
//                jListCivilStat.setEnabled(true);
//                jListSex.setEnabled(true);
//                jListEmpStat.setEnabled(true);
//                txtStartDate.setEnabled(true);

                
//            }
            
        return false;
        
    } catch (SQLException e) {  
        infoBox(e.getMessage(),"Error"); 
        return false;
    }
}

    public void clearAll() throws IOException {
        jTextFieldFname.setText("");
        jTextFieldMname.setText("");
        jTextFieldLname.setText("");
        jTextField3.setText("");
//        jTextArea1.setText("");
        btnFPIcon.setEnabled(false);
        lblFPMessage.setText("...");
        ButtonImageIcon.fitImageToButton(FpRegistration.btnFPIcon, System.getProperty("user.dir")+"\\img\\fp0.png", WIDTH);
        //jTextFieldPosition.setText("");
        //jTextFieldDept.setText("");

    }
    
    private static final String DATE_FORMAT = "yyyy-MM-dd";

        public static boolean isValidDate(String input) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
                LocalDate.parse(input, formatter);
                return true;
            } catch (DateTimeParseException e) {
                return false;
            }
    }
}
