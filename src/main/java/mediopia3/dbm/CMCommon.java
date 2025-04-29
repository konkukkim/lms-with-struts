package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;

public class CMCommon
 implements MConstant
{

 public CMCommon(String cmid, String tname, int tseq, Vector cm_common)
 {
     CMID = new String();
     tName = new String();
     CM_Common = null;
     CMID = cmid;
     tName = tname;
     tSeq = tseq;
     CM_Common = cm_common;
 }

 public void setComFromLS(String name, String cname, int seq, int cseq, Vector vLS)
 {
     String retVal = new String();
     String value = new String();
     String attr = new String();
     int size = vLS.size();
     for(int i = 0; i < size; i++)
     {
         Vector aLS = (Vector)vLS.get(i);
         value = (String)aLS.get(1);
         attr = (String)aLS.get(0);
         retVal = String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(tName).append("', '").append(name).append("', '").append(cname).append("', ").append(tSeq).append(", ").append(seq).append(", ").append(cseq).append(", ").append(i).append(", '").append(rmvQuatMark(value)).append("', '").append(rmvQuatMark(attr)).append("')")));
         CM_Common.add(retVal);
     }

 }

 public void setComFromS(String name, String cname, int seq, int cseq, String S)
 {
     String retVal = new String();
     int order = 0;
     retVal = String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(tName).append("', '").append(name).append("', '").append(cname).append("', ").append(tSeq).append(", ").append(seq).append(", ").append(cseq).append(", ").append(order).append(", '").append(rmvQuatMark(S)).append("', '").append("").append("')")));
     CM_Common.add(retVal);
 }

 public void setComFromVoca(String name, int seq, Vector vVoca)
 {
     setComFromLS(name, "source", seq, 0, (Vector)vVoca.get(0));
     setComFromLS(name, "value", seq, 0, (Vector)vVoca.get(1));
 }

 public void setComFromContribute(String name, int seq, Vector vCON)
 {
     Vector vCentity = new Vector();
     setComFromVoca("contribute/role", seq, (Vector)vCON.get(0));
     vCentity = (Vector)vCON.get(1);
     int size = vCentity.size();
     for(int i = 0; i < size; i++)
         setComFromS(name, "centity/vcard", seq, i, (String)vCentity.get(i));

     setComFromDate("contribute/date", seq, (Vector)vCON.get(2));
 }

 public void setComFromDate(String name, int seq, Vector vDate)
 {
     setComFromS(name, "datetime", seq, 0, (String)vDate.get(0));
     setComFromLS(name, "description", seq, 0, (Vector)vDate.get(1));
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
 private String tName;
 private int tSeq;
 Vector CM_Common;
}