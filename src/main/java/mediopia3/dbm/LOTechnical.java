package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOTechnical
 implements MConstant
{

 public LOTechnical(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     formatCount = 0;
     size = new String();
     locationCount = 0;
     requirementCount = 0;
     installationremarksCount = 0;
     otherplatformrequirementsCount = 0;
     durationCount = 0;
     durationDatetime = new String();
     format = new Vector();
     location = new Vector();
     requirement = new Vector();
     installationremarks = new Vector();
     otherplatformrequirements = new Vector();
     duration = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_technical", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, String val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         COM.setComFromS("format", "", seq, 0, val);
         formatCount++;
         break;

     case 1: // '\001'
         size = val;
         break;
     }
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 2: // '\002'
         COM.setComFromS("location/type", "", seq, 0, (String)val.get(0));
         COM.setComFromS("location", "", seq, 0, (String)val.get(0));
         locationCount++;
         break;

     case 3: // '\003'
         COM.setComFromVoca("requirement/type", seq, (Vector)val.get(0));
         COM.setComFromVoca("requirement/name", seq, (Vector)val.get(1));
         COM.setComFromS("requirement/minimumversion", "", seq, 0, (String)val.get(2));
         COM.setComFromS("requirement/maximimversion", "", seq, 0, (String)val.get(3));
         requirementCount++;
         break;

     case 4: // '\004'
         COM.setComFromLS("installationremarks", "", seq, 0, val);
         installationremarksCount++;
         break;

     case 5: // '\005'
         COM.setComFromLS("otherplatformrequirements", "", seq, 0, val);
         otherplatformrequirementsCount++;
         break;

     case 6: // '\006'
         COM.setComFromLS("duration/description", "", seq, 0, (Vector)val.get(1));
         durationCount++;
         durationDatetime = (String)val.get(0);
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, formatCount, size, locationCount, requirementCount, installationremarksCount, otherplatformrequirementsCount, durationCount, durationDatetime";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(formatCount).append(", '").append(rmvQuatMark(size)).append("', ").append(locationCount).append(", ").append(requirementCount).append(", ").append(installationremarksCount).append(", ").append(otherplatformrequirementsCount).append(", ").append(durationCount).append(", '").append(rmvQuatMark(durationDatetime)).append("')")));
 }

 public String rmvQuatMark(String str)
 {
     try
     {
         for(int intPos = str.indexOf("'"); intPos != -1; intPos = str.indexOf("'", intPos + 2))
             str = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(str.substring(0, intPos))))).append("'").append("'").append(str.substring(intPos + 1))));

     }
     catch(Exception e)
     {
         System.out.println("rmvQuatMark=====>".concat(String.valueOf(String.valueOf(e.getMessage()))));
     }
     return str;
 }

 private String CMID;
 private String nodeID;
 private int formatCount;
 private String size;
 private int locationCount;
 private int requirementCount;
 private int installationremarksCount;
 private int otherplatformrequirementsCount;
 private int durationCount;
 private String durationDatetime;
 private Vector format;
 private Vector location;
 private Vector requirement;
 private Vector installationremarks;
 private Vector otherplatformrequirements;
 private Vector duration;
 CMCommon COM;
}