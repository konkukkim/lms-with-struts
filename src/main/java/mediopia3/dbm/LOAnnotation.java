
package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOAnnotation
 implements MConstant
{

 public LOAnnotation(String id, String nodeAlias, int tseq, String strNodeNum, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     person = new String();
     dateCount = 0;
     dateDatetime = new String();
     descriptionCount = 0;
     date = new Vector();
     description = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(nodeAlias) + String.valueOf(strNodeNum);
     COM = new CMCommon(CMID, "cm_annotation", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, String val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         person = val;
         break;
     }
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 1: // '\001'
         COM.setComFromLS("dateDate/description", "", seq, 0, (Vector)val.get(1));
         dateCount++;
         dateDatetime = (String)val.get(0);
         break;

     case 2: // '\002'
         COM.setComFromLS("description", "", seq, 0, val);
         descriptionCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, person, dateCount, dateDatetime, descriptionCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', '").append(rmvQuatMark(person)).append("', ").append(dateCount).append(", '").append(rmvQuatMark(dateDatetime)).append("', ").append(descriptionCount).append(")")));
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
 private String person;
 private int dateCount;
 private String dateDatetime;
 private int descriptionCount;
 private Vector date;
 private Vector description;
 CMCommon COM;
}