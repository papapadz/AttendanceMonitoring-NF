    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package attendancemonitoring;
import com.digitalpersona.onetouch.DPFPFeatureSet;
import com.digitalpersona.onetouch.DPFPGlobal;
import com.digitalpersona.onetouch.DPFPTemplate;
import com.digitalpersona.onetouch.verification.DPFPVerification;
import com.digitalpersona.onetouch.verification.DPFPVerificationResult;
import java.util.concurrent.Callable;
import javax.xml.bind.DatatypeConverter;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author RVFormoso
 */
public class CallableVerifier implements Callable<JSONObject> {
    private final DPFPFeatureSet featureSet;
    private final String ProcName;
    private final int StartPosition;
    private final int EndPosition;
    private final JSONArray empArray;
    
    public CallableVerifier(DPFPFeatureSet featureSet, String ProcName, int StartPosition, int EndPosition, JSONArray empArray) {
        this.featureSet = featureSet;
        this.ProcName = ProcName;
        this.StartPosition = StartPosition;
        this.EndPosition = EndPosition;
        this.empArray = empArray;
    }
    
    @Override
    public JSONObject call() throws Exception {
        System.out.println(this.ProcName + " Started");
        
        DPFPTemplate template = DPFPGlobal.getTemplateFactory().createTemplate();
        DPFPVerification matcher = DPFPGlobal.getVerificationFactory().createVerification();
        matcher.setFARRequested(DPFPVerification.MEDIUM_SECURITY_FAR);

        JSONArray arrayQpo = this.empArray;
        JSONObject returnValue = new JSONObject();
        returnValue.put("found", false);

        for(int i = this.StartPosition; i<this.EndPosition;i++) {

            JSONObject empObject = arrayQpo.getJSONObject(i);

            byte[] digital = DatatypeConverter.parseBase64Binary(empObject.getString("fp1"));
            byte[] digital2 = DatatypeConverter.parseBase64Binary(empObject.getString("fp2"));

            template.deserialize(digital); //deserialize fingerprint
            DPFPVerificationResult result = matcher.verify(this.featureSet, template);
            template.deserialize(digital2); //deserialize fingerprint
            DPFPVerificationResult result2 = matcher.verify(this.featureSet, template);

            if (result.isVerified()||result2.isVerified()) {
//                Crud c = new Crud();
//                c.newAttendance(empObject.getString("emp_id"),empObject.getString("name"),empObject.getString("position"),empObject.getString("dept"));
                return empObject;
            }
        } 
        return returnValue;
    }
}
    

