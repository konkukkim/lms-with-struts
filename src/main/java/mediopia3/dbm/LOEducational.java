package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOEducational
 implements MConstant
{

 public LOEducational(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     interactivitytypeCount = 0;
     learningresourcetypeCount = 0;
     interactivitylevelCount = 0;
     semanticdensityCount = 0;
     intendedenduserroleCount = 0;
     contextCount = 0;
     typicalagerangeCount = 0;
     difficultyCount = 0;
     typicallearnintimeCount = 0;
     typicallearnintimeDatetime = new String();
     descriptionCount = 0;
     languageCount = 0;
     interactivitytype = new Vector();
     learningresourcetype = new Vector();
     interactivitylevel = new Vector();
     semanticdensity = new Vector();
     intendedenduserrole = new Vector();
     context = new Vector();
     typicalagerange = new Vector();
     difficulty = new Vector();
     typicallearnintime = new Vector();
     description = new Vector();
     language = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_educational", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, String val)
 {
     switch(nodeAlias)
     {
     case 10: // '\n'
         COM.setComFromS("language", "", seq, 0, val);
         System.out.println(String.valueOf(String.valueOf((new StringBuffer("[cm_common(")).append(seq).append(")]language===>").append(val))));
         languageCount++;
         break;
     }
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         COM.setComFromVoca("interactivitytype", seq, val);
         interactivitytypeCount++;
         break;

     case 1: // '\001'
         COM.setComFromVoca("learningresourcetype", seq, val);
         learningresourcetypeCount++;
         break;

     case 2: // '\002'
         COM.setComFromVoca("interactivitylevel", seq, val);
         interactivitylevelCount++;
         break;

     case 3: // '\003'
         COM.setComFromVoca("semanticdensity", seq, val);
         semanticdensityCount++;
         break;

     case 4: // '\004'
         COM.setComFromVoca("intendedenduserrole", seq, val);
         intendedenduserroleCount++;
         break;

     case 5: // '\005'
         COM.setComFromVoca("context", seq, val);
         contextCount++;
         break;

     case 6: // '\006'
         COM.setComFromLS("typicalagerange", "", seq, 0, val);
         typicalagerangeCount++;
         break;

     case 7: // '\007'
         COM.setComFromVoca("difficulty", seq, val);
         difficultyCount++;
         break;

     case 8: // '\b'
         COM.setComFromLS("typicallearnintime/description", "", seq, 0, (Vector)val.get(1));
         typicallearnintimeCount++;
         typicallearnintimeDatetime = (String)val.get(0);
         break;

     case 9: // '\t'
         COM.setComFromLS("description", "", seq, 0, val);
         descriptionCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, interactivitytypeCount, learningresourcetypeCount, interactivitylevelCount, semanticdensityCount, intendedenduserroleCount, contextCount, typicalagerangeCount, difficultyCount, typicallearnintimeCount, typicallearnintimeDatetime, descriptionCount, languageCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(interactivitytypeCount).append(", ").append(learningresourcetypeCount).append(", ").append(interactivitylevelCount).append(", ").append(semanticdensityCount).append(", ").append(intendedenduserroleCount).append(", ").append(contextCount).append(", ").append(typicalagerangeCount).append(", ").append(difficultyCount).append(", ").append(typicallearnintimeCount).append(", '").append(rmvQuatMark(typicallearnintimeDatetime)).append("', ").append(descriptionCount).append(", ").append(languageCount).append(")")));
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
 private int interactivitytypeCount;
 private int learningresourcetypeCount;
 private int interactivitylevelCount;
 private int semanticdensityCount;
 private int intendedenduserroleCount;
 private int contextCount;
 private int typicalagerangeCount;
 private int difficultyCount;
 private int typicallearnintimeCount;
 private String typicallearnintimeDatetime;
 private int descriptionCount;
 private int languageCount;
 private Vector interactivitytype;
 private Vector learningresourcetype;
 private Vector interactivitylevel;
 private Vector semanticdensity;
 private Vector intendedenduserrole;
 private Vector context;
 private Vector typicalagerange;
 private Vector difficulty;
 private Vector typicallearnintime;
 private Vector description;
 private Vector language;
 CMCommon COM;
}