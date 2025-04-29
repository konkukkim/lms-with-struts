package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOLifeCycle
{

 public LOLifeCycle(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     versionCount = 0;
     statusCount = 0;
     contributeCount = 0;
     version = new Vector();
     status = new Vector();
     contribute = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_lifecycle", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         COM.setComFromLS("version", "", seq, 0, val);
         versionCount++;
         break;

     case 1: // '\001'
         COM.setComFromVoca("status", seq, val);
         statusCount++;
         break;

     case 2: // '\002'
         System.out.println(String.valueOf(String.valueOf((new StringBuffer("[cm_common(")).append(seq).append(")]contribute===>").append(val.toString()))));
         contributeCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, versionCount, statusCount, contributeCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(versionCount).append(", ").append(statusCount).append(", ").append(contributeCount).append(")")));
 }

 private String CMID;
 private String nodeID;
 private int versionCount;
 private int statusCount;
 private int contributeCount;
 private Vector version;
 private Vector status;
 private Vector contribute;
 CMCommon COM;
}