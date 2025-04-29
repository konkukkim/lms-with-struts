/*jadclipse*/// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
//Jad home page: http://kpdus.tripod.com/jad.html
//Decompiler options: packimports(3) radix(10) lradix(10) 
//Source File Name:   LOGeneral.java

package mediopia3.dbm;

import java.io.PrintStream;
import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.lom.CMAnalyzer;

//Referenced classes of package mediopia.dbm:
//         CMCommon

public class LOGeneral
 implements MConstant
{

 public LOGeneral(String id, String nodeAlias, int tseq, CMAnalyzer CM)
 {
     CMID = new String();
     nodeID = new String();
     identifier = new String();
     titleCount = 0;
     catalogentryCount = 0;
     languageCount = 0;
     descriptionCount = 0;
     keywordCount = 0;
     coverageCount = 0;
     structureCount = 0;
     aggregationlevelCount = 0;
     title = new Vector();
     catalogentry = new Vector();
     language = new Vector();
     description = new Vector();
     keyword = new Vector();
     coverage = new Vector();
     structure = new Vector();
     aggregationlevel = new Vector();
     COM = null;
     CMID = id;
     nodeID = String.valueOf(String.valueOf(nodeAlias)).concat("00");
     COM = new CMCommon(CMID, "cm_general", tseq, CM.CmCommon);
 }

 public void setElement(int seq, int nodeAlias, String val)
 {
     switch(nodeAlias)
     {
     case 0: // '\0'
         identifier = val;
         break;

     case 3: // '\003'
         COM.setComFromS("language", "", seq, 0, val);
         languageCount++;
         break;
     }
 }

 public void setElement(int seq, int nodeAlias, Vector val)
 {
     switch(nodeAlias)
     {
     case 1: // '\001'
         COM.setComFromLS("title", "", seq, 0, val);
         titleCount++;
         break;

     case 2: // '\002'
         COM.setComFromS("catalogentry/catalog", "", seq, 0, (String)val.get(0));
         COM.setComFromLS("catalogentry/entry", "", seq, 0, (Vector)val.get(1));
         catalogentryCount++;
         break;

     case 4: // '\004'
         COM.setComFromLS("description", "", seq, 0, val);
         descriptionCount++;
         break;

     case 5: // '\005'
         COM.setComFromLS("keyword", "", seq, 0, val);
         keywordCount++;
         break;

     case 6: // '\006'
         COM.setComFromLS("coverage", "", seq, 0, val);
         coverageCount++;
         break;

     case 7: // '\007'
         COM.setComFromVoca("structure", seq, val);
         structureCount++;
         break;

     case 8: // '\b'
         COM.setComFromVoca("aggregationlevel", seq, val);
         aggregationlevelCount++;
         break;
     }
 }

 public String getStrSchema()
 {
     return "cmid, nodeID, identifier, titleCount, catalogentryCount, languageCount, descriptionCount, keywordCount, coverageCount, structureCount, aggregationlevelCount";
 }

 public String getAllField()
 {
     return String.valueOf(String.valueOf((new StringBuffer("('")).append(CMID).append("', '").append(nodeID).append("', '").append(rmvQuatMark(identifier)).append("', ").append(titleCount).append(", ").append(catalogentryCount).append(", ").append(languageCount).append(", ").append(descriptionCount).append(", ").append(keywordCount).append(", ").append(coverageCount).append(", ").append(structureCount).append(", ").append(aggregationlevelCount).append(")")));
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
 private int titleCount;
 private int catalogentryCount;
 private int languageCount;
 private int descriptionCount;
 private int keywordCount;
 private int coverageCount;
 private int structureCount;
 private int aggregationlevelCount;
 private Vector title;
 private Vector catalogentry;
 private Vector language;
 private Vector description;
 private Vector keyword;
 private Vector coverage;
 private Vector structure;
 private Vector aggregationlevel;
 CMCommon COM;
}