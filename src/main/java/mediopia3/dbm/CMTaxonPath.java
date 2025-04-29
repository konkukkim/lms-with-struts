package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class CMTaxonPath
 implements MConstant
{

 public CMTaxonPath(String cmid, int tseq, Vector cm_taxonpath, CMCommon com, Vector vTV, Vector vTP)
 {
     CMID = new String();
     CM_TaxonPath = null;
     COM = null;
     this.vTV = null;
     CMID = cmid;
     tSeq = tseq;
     CM_TaxonPath = cm_taxonpath;
     COM = com;
     this.vTV = vTV;
     setTaxonPath("taxonpath", vTP);
 }

 public void setTaxonPath(String name, Vector vTP)
 {
     String iSentence = new String();
     String nodeID = new String();
     Vector vSource = (Vector)vTP.get(0);
     COM.setComFromLS("source", "", 0, 0, vSource);
     Vector vTXs = (Vector)vTP.get(1);
     int level = -1;
     nodeID = getStr(tSeq);
     int size = setTaxon(level++, nodeID, vTXs);
     nodeID = String.valueOf(String.valueOf(nodeID)).concat("00");
     iSentence = String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', ").append(level).append(", '").append(nodeID).append("', 1, '', 0, ").append(size).append(")")));
     CM_TaxonPath.add(iSentence);
 }

 public int setTaxon(int level, String nodeId, Vector vTXs)
 {
     String nodeID = new String();
     String iSentence = new String();
     int vNum = vTXs.size();
     if(vNum != 0 && vTV.equals((Vector)vTXs.get(0)))
         return 0;
     level++;
     Vector vTX = new Vector();
     String taxonID = new String();
     Vector vEntry = new Vector();
     Vector vvTXs = new Vector();
     for(int i = 0; i < vNum; i++)
     {
         vTX = (Vector)vTXs.get(i);
         nodeID = String.valueOf(nodeId) + String.valueOf(getStr(i + 1));
         int srcCnt = -1;
         taxonID = (String)vTX.get(0);
         vEntry = (Vector)vTX.get(1);
         vvTXs = (Vector)vTX.get(2);
         int taxonEntryCount = vEntry.size();
         int taxonCountCount = setTaxon(level, nodeID, vvTXs);
         iSentence = String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', ").append(level).append(", '").append(nodeID).append("', -1, '").append(taxonID).append("', ").append(taxonEntryCount).append(", ").append(taxonCountCount).append(")")));
         CM_TaxonPath.add(iSentence);
         COM.setComFromLS("entry", "", 0, 0, vEntry);
     }

     return vNum;
 }

 private String getStr(int val)
 {
     Integer temp = new Integer(val);
     if(val >= 0 && val < 10)
         return "0".concat(String.valueOf(String.valueOf(temp.toString())));
     else
         return temp.toString();
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
 private int tSeq;
 Vector CM_TaxonPath;
 CMCommon COM;
 Vector vTV;
}