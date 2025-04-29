package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LORelation
 implements MConstant
{

 public LORelation(String id, String nodeAlias, int tseq, String strNodeNum, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     kindCount = 0;
     resourceCount = 0;
     resourceIdentifier = new String();
     kind = new Vector();
     resource = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(nodeAlias) + String.valueOf(strNodeNum);
     COM = new CMCommon(CMID, "cm_relation", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     default:
         break;

     case 0: // '\0'
         COM.setComFromVoca("kind", seq, val);
         kindCount++;
         break;

     case 1: // '\001'
         COM.setComFromLS("resource/description", "", seq, 0, (Vector)val.get(1));
         Vector vCEs = (Vector)val.get(2);
         int size = vCEs.size();
         for(int i = 0; i < size; i++)
         {
             Vector vCatalogentry = (Vector)vCEs.get(i);
             COM.setComFromS("resource/catalogentry/catalog", "", seq, 0, (String)vCatalogentry.get(0));
             COM.setComFromLS("resource/catalogentry/entry", "", seq, 0, (Vector)vCatalogentry.get(1));
         }

         resourceCount++;
         resourceIdentifier = (String)val.get(0);
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, kindCount, resourceCount, resourceIdentifier";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', ").append(kindCount).append(", ").append(resourceCount).append(", '").append(rmvQuatMark(resourceIdentifier)).append("')")));
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
 private int kindCount;
 private int resourceCount;
 private String resourceIdentifier;
 private Vector kind;
 private Vector resource;
 CMCommon COM;
}