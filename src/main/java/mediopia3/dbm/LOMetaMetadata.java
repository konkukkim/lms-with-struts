package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOMetaMetadata
 implements MConstant
{

 public LOMetaMetadata(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     identifier = new String();
     catalogentryCount = 0;
     contributeCount = 0;
     metadataschemeCount = 0;
     language = new String();
     catalogentry = new Vector();
     contribute = new Vector();
     metadatascheme = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_metametadata", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, String val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         identifier = val;
         break;

     case 3: // '\003'
         COM.setComFromS("metadatascheme", "", seq, 0, val);
         metadataschemeCount++;
         break;

     case 4: // '\004'
         language = val;
         break;
     }
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 1: // '\001'
         COM.setComFromS("catalogentry/catalog", "", seq, 0, (String)val.get(0));
         COM.setComFromLS("catalogentry/entry", "", seq, 0, (Vector)val.get(1));
         catalogentryCount++;
         break;

     case 2: // '\002'
         COM.setComFromContribute("contribute", seq, val);
         contributeCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, identifier, catalogentryCount, contributeCount, metadataschemeCount, language";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', '").append(rmvQuatMark(identifier)).append("', ").append(catalogentryCount).append(", ").append(contributeCount).append(", ").append(metadataschemeCount).append(", '").append(rmvQuatMark(language)).append("')")));
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
 private String identifier;
 private int catalogentryCount;
 private int contributeCount;
 private int metadataschemeCount;
 private String language;
 private Vector catalogentry;
 private Vector contribute;
 private Vector metadatascheme;
 CMCommon COM;
}
