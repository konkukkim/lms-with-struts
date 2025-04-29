package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon, CMTaxonPath

public class LOClassification
 implements MConstant
{

 public LOClassification(String id, String nodeAlias, int tseq, String strNodeNum, CMAnalyzer cm)
 {
     CMID = new String();
     nodeID = new String();
     purposeCount = 0;
     taxonpathCount = 0;
     descriptionCount = 0;
     keywordCount = 0;
     purpose = new Vector();
     taxonpath = new Vector();
     description = new Vector();
     keyword = new Vector();
     COM = null;
     tpCOM = null;
     TP = null;
     CM = null;
     CMID = id;
     nodeID = String.valueOf(nodeAlias) + String.valueOf(strNodeNum);
     TSeq = tseq;
     CM = cm;
     COM = new CMCommon(CMID, "cm_classification", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         COM.setComFromVoca("purpose", seq, val);
         purposeCount++;
         break;

     case 1: // '\001'
         tpCOM = new CMCommon(CMID, "cm_taxon_path", seq, CM.CmCommon);
         TP = new CMTaxonPath(CMID, seq, CM.CmTaxonPath, tpCOM, CM.getTaxonValue(), val);
         taxonpathCount++;
         break;

     case 2: // '\002'
         COM.setComFromLS("description", "", seq, 0, val);
         descriptionCount++;
         break;

     case 3: // '\003'
         COM.setComFromLS("keyword", "", seq, 0, val);
         keywordCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, purposeCount, taxonpathCount, descriptionCount, keywordCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(purposeCount).append(", ").append(taxonpathCount).append(", ").append(descriptionCount).append(", ").append(keywordCount).append(")")));
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
 private int purposeCount;
 private int taxonpathCount;
 private int descriptionCount;
 private int keywordCount;
 private Vector purpose;
 private Vector taxonpath;
 private Vector description;
 private Vector keyword;
 private int TSeq;
 CMCommon COM;
 CMCommon tpCOM;
 CMTaxonPath TP;
 CMAnalyzer CM;
}