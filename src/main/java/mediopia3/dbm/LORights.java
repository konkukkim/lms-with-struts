package mediopia3.dbm;

import java.util.Vector;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LORights
{

 public LORights(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     costCount = 0;
     copyrightandotherrestrictionsCount = 0;
     descriptionCount = 0;
     cost = new Vector();
     copyrightandotherrestrictions = new Vector();
     description = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_rights", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         COM.setComFromVoca("cost", seq, val);
         costCount++;
         break;

     case 1: // '\001'
         COM.setComFromVoca("copyrightandotherrestrictions", seq, val);
         copyrightandotherrestrictionsCount++;
         break;

     case 2: // '\002'
         COM.setComFromLS("description", "", seq, 0, val);
         descriptionCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, costCount, copyrightandotherrestrictionsCount, descriptionCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(costCount).append(", ").append(copyrightandotherrestrictionsCount).append(", ").append(descriptionCount).append(")")));
 }

 private String CMID;
 private String nodeID;
 private int costCount;
 private int copyrightandotherrestrictionsCount;
 private int descriptionCount;
 private Vector cost;
 private Vector copyrightandotherrestrictions;
 private Vector description;
 CMCommon COM;
}