package mediopia3.lpm;
/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */

import java.io.*;
import java.sql.*;
import java.util.Vector;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.w3c.dom.*;
import mediopia3.dbm.*;
import java.net.*;

//import java.util.*;

public class LPAnalyzer {
  //=================================
  // 디버깅 변수 선언
  //=================================
  /**출력 결과를 HTML 코드로 생성하여 반환하기 위한 Flag**/
  private boolean _Deburg = true;
  /**테스트용 출력 메세지변수**/
  private String Out_Message = new String();
  private int insertCount=0;
  TimeNow UT = new TimeNow();
  public String logFilename = new String();
  public String FileDelimiter = new String();

  //=================================
  // CP Import 처리 결과 메시지 변수
  //=================================
  public String Out_Identifier = new String();
  public String Out_Schema_Version = new String();
  public int    Out_CMCount = 0;
  public int    Out_CMiCount = 0;
  public int    Out_CMfCount = 0;
  public int    Out_CMuCount = 0;
  public int    Out_ORGCount = 0;
  public Vector Out_ORGTitle = new Vector();
  public int    Out_RSCCount = 0;
  public Vector Out_RSCType = new Vector();
  public int    Out_SCOCount = 0;
  public int    Out_ASSETCount = 0;
  public int    Out_Submanifest = 0;

  public Vector itemInfoSet = new Vector();

  //=================================
  // LPAnalyzer 클래스의 전역변수 선언
  //=================================
  /**Manifest File의 절대경로+파일명**/
  private String FILE_NAME = new String("");
  /**Manifest File의 절대경로**/
  private String Base_DIR = new String("");
  /**Manifest File의 DOM Document**/
  private Document MF_Document = null;
  /** Learning Package의 모든 정보를 보유하는 클래스 **/
  private LPMain LP_Main = null;
  /** 각 테이블에 삽입할 튜플들의 리스트 **/
  private Vector CpMain = new Vector();
  private Vector CpOrgs = new Vector();
  private Vector CpRscs = new Vector();
  private Vector CpItem = new Vector();
  private Vector CpFile = new Vector();
  private Vector CpDpdc = new Vector();
  private Vector CmMain = new Vector();

  //=================================
  // DBMS 관련 전역 변수
  //=================================
  private Connection db = null;
  private Connection conn = null;
  private Statement s = null;
  private ResultSet rs = null;

  /*****************************************************************************
   * public LPAnalyzer()
   *    @description : 생성자.
   ****************************************************************************/
  public LPAnalyzer() {
  }

  public void setLogFilename(String name) {
    logFilename = name;
  } /* END_OF_setLogFilename */
  public void setFileDelimiter(String val) {
    FileDelimiter = val;
  } /* END_OF_setLogFilename */

  /*****************************************************************************
   * public String getFilename()
   *    @description : Manifest 파일명을 얻는다.
   ****************************************************************************/
  public String getFilename() {
    return FILE_NAME;
  } /* END_OF_getFilename */

  /*****************************************************************************
   * public String getOutMessage()
   *    @description : 처리 결과 메시지를 얻는다.
   ****************************************************************************/
  public String getOut_Message() {
    return Out_Message;
  } /* END_OF_getOutMessage */

  //=================================
  // CP Import 처리 결과 메시지 변수
  //  - String Out_Identifier
  //  - String Out_Schema_Version
  //  - int    Out_ORGCount
  //  - Vector Out_ORGTitle
  //  - int    Out_RSCCount
  //  - Vector Out_RSCType
  //  - int    Out_SCOCount
  //  - int    Out_ASSETCount
  //  - int    Out_Submanifest
  //=================================

  public String getOutIdentifier() {
    return Out_Identifier;
  } /* END_OF_getOutIdentifier */
  public String getOutSchema_Version() {
    return Out_Schema_Version;
  } /* END_OF_getOutSchema_Version */
  public int getOutCMCount() {
    return Out_CMCount;
  } /* END_OF_getOutCMCount */

  public int getOutCMiCount() {
    return Out_CMiCount;
  } /* END_OF_getOutCMiCount */
  public int getOutCMfCount() {
    return Out_CMfCount;
  } /* END_OF_getOutCMfCount */
  public int getOutCMuCount() {
    return Out_CMuCount;
  } /* END_OF_getOutCMuCount */

  public int getOutORGCount() {
    return Out_ORGCount;
  } /* END_OF_getOutORGCount */
  public Vector getOutORGTitle() {
    return Out_ORGTitle;
  } /* END_OF_getOutORGTitle */
  public int getOutRSCCount() {
    return Out_RSCCount;
  } /* END_OF_getOutRSCCount */
  public Vector getOutRSCType() {
    return Out_RSCType;
  } /* END_OF_getOutRSCType */
  public int getOutSCOCount() {
    return Out_SCOCount;
  } /* END_OF_getOutSCOCount */
  public int getOutASSETCount() {
    return Out_ASSETCount;
  } /* END_OF_getOutASSETCount */
  public int getOutSubmanifest() {
    return Out_Submanifest;
  } /* END_OF_getOutSubmanifest */

  /*****************************************************************************
   * public void setManifest(String filename)
   *    @description : Manifest XML 파일로부터 Document Element를 생성하여,
   *                  Metadata 정보를 추출하는 setManifest 메소드 호출.
   *    @param filename : Manifest XML 파일
   ****************************************************************************/
  public void setManifest(String filename) {
      String nodeSuffix = new String("-");  // CP ID["yyyyMMdd-HHmmssSSS"+suffix+nodeID]의 suffix
      String nodeID = new String("00");     // 최초의 Mmanifest노드의 아이디는 "00"이다.
      this.FILE_NAME = filename;
      String baseDir = getBasePath(FILE_NAME);
      DOMParser domparser = new DOMParser();

      try {
            setLog("[LPAnalyzer-setManifest] " + FILE_NAME);
            domparser.parse(new InputSource(new FileReader(FILE_NAME)));
            MF_Document = domparser.getDocument();
            Element mfRoot = (Element)MF_Document.getFirstChild();
            setManifest(mfRoot, baseDir, nodeSuffix, nodeID);
      } catch (Exception ex) {
            setLog("[LPAnalyzer-setManifest] Invalid XML document");
            ex.printStackTrace(System.err);
      } // END_OF_TRY
  } // END_OF_setManifest

  /*****************************************************************************
   * public void setManifest(Element mfRoot, String baseDir, String nodeID)
   *    @description : Document Element를 입력 받아서, Metadata 정보를 추출.
   *    @param mfRoot : Document DOM Element
   *    @param baseDir : Manifest XML 파일의 경로명
   ****************************************************************************/
  public void setManifest(Element mfRoot, String baseDir, String nodeSuffix, String nodeID) {
      this.Base_DIR = baseDir;
      this.LP_Main = new LPMain(nodeSuffix, nodeID);
      setAttributes(mfRoot, LP_Main);  // manifest의 attribute(identifier,version,xml:base)를 구한다.

      int nodeNum = 0;
      for (Node cNode = mfRoot.getFirstChild();
                cNode != null;
                cNode = cNode.getNextSibling()) {
          if ( cNode instanceof Element ) {
              String curNodeName = cNode.getNodeName();
              if ( curNodeName.equalsIgnoreCase("metadata"))
                  setMetadata((Element)cNode, LP_Main, "A00");
              if ( curNodeName.equalsIgnoreCase("organizations"))
                  setOrganizations((Element)cNode);
              if ( curNodeName.equalsIgnoreCase("resources"))
                  setResources((Element)cNode);
              if ( curNodeName.equalsIgnoreCase("manifest")) {
                  setSubManifest((Element)cNode, 0, nodeNum);
                  Out_Submanifest++;
              }
              nodeNum++;
          }
      } // END_OF_FOR
      CpMain.add(LP_Main.getAllField());
      //addMessage("<br><br>##### Manifest #####<br>" + LP_Main.getStrSchema()
      //                                 + "<br>" + LP_Main.getAllField());
      setLog("[LPAnalyzer-setManifest] CP Parsing COMPLETED !!!");
  } // END_OF_setManifest

  /*****************************************************************************
   * private void setMetadata(Element mdRoot, Object obj, String cmIDSuffix)
   *    @description : Manifest XML 파일로부터 Metadata 정보를 추출한다.
   *    @param mdRoot : metadata DOM Element
   *    @param obj : 본 메타데이터는 이 객체의 메타데이터이다.
   *    @param cmIDSuffix :
   *      = 구분자 + 노드 아이디
   *        > 구분자 (A, B, C, D, a, b, c, d, ...)
   *        > A:manifest, B:organization, C:resource, D:file
   *        > a: level 0 item, b:level 1 item , ...
   *          (level 0부터 25까지의 item metadata 표현가능)
   ****************************************************************************/
  private void setMetadata(Element mdRoot, Object obj, String cmIDSuffix) {
      if (mdRoot.getChildNodes().getLength()==0) return;
      String className = obj.getClass().getName();
      String schema = new String();
      String schemaversion = new String();
      String strNode = null;
      Node CM = null;
      String xmlBase = new String();

      for (Node cNode = mdRoot.getFirstChild();
                cNode != null;
                cNode = cNode.getNextSibling()) {
          if ( cNode instanceof Element ) {
              if ( cNode.getNodeName().equalsIgnoreCase("schema") ) {
                  schema = cNode.getFirstChild().getNodeValue();
              } else if ( cNode.getNodeName().equalsIgnoreCase("schemaversion") ) {
                  schemaversion = cNode.getFirstChild().getNodeValue();
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:location") ) {
                  if (cNode.hasChildNodes()) strNode = cNode.getFirstChild().getNodeValue();
                  else strNode = "";
                  if (strNode.startsWith("http://")) {
                      Out_CMCount++;
                      Out_CMuCount++;
                  } else {
                      Out_CMCount++;
                      Out_CMfCount++;
                  }
              } else {
                  strNode = "INTERNAL";
                  Out_CMCount++;
                  Out_CMiCount++;
              }
          } /* END_OF_IF */
      } /* END_OF_FOR */
      if (className.equalsIgnoreCase("mediopia3.dbm.LPMain")) {
          ((LPMain)obj).setSchema(schema);
          ((LPMain)obj).setSchemaversion(schemaversion);
          ((LPMain)obj).setContentModel(strNode);
          Out_Schema_Version = schema + " " + schemaversion;
          setLog("[LPAnalyzer-setMetadata]  CP metadata Parsing COMPLETED !!!");
      } else if (className.equalsIgnoreCase("mediopia3.dbm.LPOrgs")) {
          ((LPOrgs)obj).setSchema(schema);
          ((LPOrgs)obj).setSchemaversion(schemaversion);
          ((LPOrgs)obj).setContentModel(strNode);
      } else if (className.equalsIgnoreCase("mediopia3.dbm.LPRscs")) {
          ((LPRscs)obj).setSchema(schema);
          ((LPRscs)obj).setSchemaversion(schemaversion);
          ((LPRscs)obj).setContentModel(strNode);
      } else if (className.equalsIgnoreCase("mediopia3.dbm.LPItem")) {
          ((LPItem)obj).setSchema(schema);
          ((LPItem)obj).setSchemaversion(schemaversion);
          ((LPItem)obj).setContentModel(strNode);
      } else if (className.equalsIgnoreCase("mediopia3.dbm.LPFile")) {
          ((LPFile)obj).setSchema(schema);
          ((LPFile)obj).setSchemaversion(schemaversion);
          ((LPFile)obj).setContentModel(strNode);
      }
  } /* END_OF_setMetadata */

  /*****************************************************************************
   * private void setOrganizations(Element orgRoot)
   *    @description : organizations DOM Element로부터 Organizations 정보를 추출.
   *    @param orgRoot : DOM Element
   ****************************************************************************/
  private void setOrganizations(Element orgRoot) {
      setAttributes(orgRoot, LP_Main);   // organizations의 attribute(default)를 구한다.
      NodeList orgsNodelist = orgRoot.getElementsByTagName("organization");
      int orgCnt = orgsNodelist.getLength();
      LP_Main.setOrgsCount(orgCnt);
      Out_ORGCount = orgCnt;
      for (int i=0; i<orgCnt; i++) {
          Element orgNode = (Element)orgsNodelist.item(i);
          if (orgNode != null) {
            LPOrgs LP_Orgs = new LPOrgs(LP_Main.getID());
            LP_Orgs.setNodeID(i+1);
            setAttributes(orgNode, LP_Orgs);    // organizations의 attribute(identifier, structure)를 구한다.
            int itemNum = 0;
            int itemOrder = 0;
            for (Node cNode = orgNode.getFirstChild();
                      cNode != null;
                      cNode = cNode.getNextSibling()) {
                if ( cNode instanceof Element ) {
                  if ( cNode.getNodeName().equalsIgnoreCase("title") ) {
                      LP_Orgs.setTitle(cNode.getFirstChild().getNodeValue());
                      Out_ORGTitle.add(cNode.getFirstChild().getNodeValue());
                  } else if ( cNode.getNodeName().equalsIgnoreCase("item") ) {
                      setItem((Element)cNode, 0, "", ++itemOrder);
                  } else if ( cNode.getNodeName().equalsIgnoreCase("metadata") ) {
                      setMetadata((Element)cNode, LP_Orgs, "B"+getStr(i+1));
                  }
                  itemNum++;
                }
            } // END_OF_FOR
            CpOrgs.add(LP_Orgs.getAllField());
          } else {
            setLog("[LPAnalyzer-setOrganizations] No organization Information");
          }
      } /* END_OF_FOR */
      setLog("[LPAnalyzer-setOrganizations] CP organizations Parsing COMPLETED !!!");
  } /* END_OF_setOrganizations */

  /*****************************************************************************
   * private void setResources(Element rscRoot)
   *    @description : resources DOM Element로부터 Resources 정보를 추출.
   *    @param rscRoot : DOM Element
   ****************************************************************************/
  private void setResources(Element rscRoot) {
      setAttributes(rscRoot, LP_Main);    //xml:base attribute
      NodeList rscNodelist = rscRoot.getElementsByTagName("resource");
      int rscCnt = rscNodelist.getLength();
      Out_RSCCount = rscCnt;
      LP_Main.setRscsCount(rscCnt);
      for (int i=0; i<rscCnt; i++) {
          Element rscNode = (Element)rscNodelist.item(i);
          if (rscNode != null) {
              LPRscs lpRscs = new LPRscs(LP_Main.getID());
              lpRscs.setNodeID(i+1);
              setAttributes(rscNode, lpRscs);
              setLog("[LPAnalyzer-setResources] SCORM Type of " + lpRscs.identifier + "-->" + lpRscs.scormtype );
              Out_RSCType.add(lpRscs.scormtype);
              if (lpRscs.scormtype.equalsIgnoreCase("sco")) Out_SCOCount++;
              else if (lpRscs.scormtype.equalsIgnoreCase("asset")) Out_ASSETCount++;

              int itemCnt = itemInfoSet.size();
              for (int k=0;k<itemCnt;k++) {
                  String refID = (String)((Vector)itemInfoSet.get(k)).get(3);
                  if (lpRscs.identifier.equalsIgnoreCase(refID)) {
                      ((Vector)itemInfoSet.get(k)).set(3,lpRscs.href);
                  }
              }
              int fileCnt = 0;
              int dpdcCnt = 0;
              for (Node cNode = rscNode.getFirstChild();
                        cNode != null;
                        cNode = cNode.getNextSibling()) {
                  if ( cNode instanceof Element ) {
                    if ( cNode.getNodeName().equalsIgnoreCase("metadata") ) {
                        setMetadata((Element)cNode, lpRscs, "C"+getStr(i+1));
                    } else if ( cNode.getNodeName().equalsIgnoreCase("file") ) {
                          LPFile lpFile = new LPFile(LP_Main.getID());
                          lpFile.setNodeID(getStr(i+1) + "F" + getStr(fileCnt++));
                          setAttributes((Element)cNode, lpFile);
                          setMetadata((Element)cNode, lpFile, "C"+getStr(i+1));
                          CpFile.add(lpFile.getAllField());
                    } else if ( cNode.getNodeName().equalsIgnoreCase("dependency") ) {
                          LPDependency lpDependency = new LPDependency(LP_Main.getID());
                          lpDependency.setNodeID(getStr(i+1) + "D" + getStr(dpdcCnt++));
                          setAttributes((Element)cNode, lpDependency);
                          CpDpdc.add(lpDependency.getAllField());
                    }
                  }
              } // END_OF_FOR
              CpRscs.add(lpRscs.getAllField());
          } else {
              setLog("[LPAnalyzer-setResources] No resource Information");
          } // END_OF_IF
      } // END_OF_FOR
      setLog("[LPAnalyzer-setResources] CP resources Parsing COMPLETED !!!");
  } // END_OF_setResources

  /*****************************************************************************
   * private void setSubManifest(Element mfRoot)
   *    @description : manifest DOM Element로부터 Manifest 정보를 추출.
   *    @param mfRoot : DOM Element
   ****************************************************************************/
  private void setSubManifest(Element mfRoot, int level, int nodeNum) {
      String nodeSuffix = new String();
      LPAnalyzer subManifest = new LPAnalyzer();
      nodeSuffix = getIDSuffix(level);
      subManifest.setManifest(mfRoot, Base_DIR, nodeSuffix, getStr(nodeNum));
  } /* END_OF_setManifest */

  /*****************************************************************************
   * private void setItem(Element itemRoot, int level, int nodeNum)
   *    @description : DOM Element로부터 Item 정보를 추출.
   *    @param itemRoot : item DOM Element
   *    @param level : item Element의 레벨(organization의 child item의 레벨 : 0)
   *    @param nodeNum : itemRoot가 몇번째 sibling인가
   ****************************************************************************/
  private String setItem(Element itemRoot, int level, String pNodeID, int nodeNum) {
    boolean leapFlag = true;
    String retRef = new String();
    String itemSuffix = new String();
    String itemNodeID = new String();
    LPItem lpItem = new LPItem(LP_Main.getID());
    itemNodeID = getStr(nodeNum);
    lpItem.setNodeID(pNodeID + itemNodeID);
    lpItem.setNodeLevel(level);
    itemSuffix = getIDSuffix(level);
    level++;
    Integer Level = new Integer(level);
    if (itemRoot != null) {
        setAttributes(itemRoot, lpItem);  // item의 Attr(identifier, identifierref, isvisible, parameters)를 구한다.
        int itemNum = 0;
        int itemOrder = 0;
        for (Node cNode = itemRoot.getFirstChild();
                  cNode != null;
                  cNode = cNode.getNextSibling()) {
            if ( cNode instanceof Element ) {
              if ( cNode.getNodeName().equalsIgnoreCase("title") ) {
                  lpItem.setTitle(cNode.getFirstChild().getNodeValue());
              } else if ( cNode.getNodeName().equalsIgnoreCase("item") ) {
                  leapFlag = false;
                  if (retRef.equalsIgnoreCase("")) retRef = setItem((Element)cNode, level, pNodeID + itemNodeID, ++itemOrder);
                  else setItem((Element)cNode, level, pNodeID + itemNodeID, ++itemOrder);
              } else if ( cNode.getNodeName().equalsIgnoreCase("metadata") ) {
                  setMetadata((Element)cNode, lpItem, itemSuffix+getStr(nodeNum));
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:prerequisites") ) {
                  lpItem.setPreRequisites(cNode.getFirstChild().getNodeValue());
                  lpItem.setPreRequisiType(((Element)cNode).getAttribute("type"));
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:maxtimeallowed") ) {
                  lpItem.setMaxTimeAllowed(cNode.getFirstChild().getNodeValue());
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:timelimitaction") ) {
                  lpItem.setTimeLimitAction(cNode.getFirstChild().getNodeValue());
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:datafromlms") ) {
                  lpItem.setDataFromLMS(cNode.getFirstChild().getNodeValue());
              } else if ( cNode.getNodeName().equalsIgnoreCase("adlcp:masteryscore") ) {
                  lpItem.setMasteryScore(cNode.getFirstChild().getNodeValue());
              }
              itemNum++;
            }
        } // END_OF_FOR
    } else {
      setLog("[LPAnalyzer-setItem] No Item Information");
    }
    CpItem.add(lpItem.getAllField());

    Vector itemInfo = new Vector();
    itemInfo.add(0,lpItem.identifier);      // item의 identifier
    itemInfo.add(1,convertID(lpItem.nodeID));          // item의 순서 (예: 001.001.000.000.000.~~~)
    itemInfo.add(2,lpItem.title);           // item의 타이틀
//    if (lpItem.identifierref.equalsIgnoreCase(""))
//        itemInfo.add(3,retRef);            // item의 패스 (없으면 하위 item중 패스를 가지고 있는 첫번째 패스)
//    else {
        itemInfo.add(3,lpItem.identifierref);// item의 패스 (없으면 하위 item중 패스를 가지고 있는 첫번째 패스)
        retRef = lpItem.identifierref;
//    }
    if (leapFlag) itemInfo.add(4,"F");      // item의 nodetype (leap이면 F, 아니면 C)
    else          itemInfo.add(4,"C");      // item의 nodetype (leap이면 F, 아니면 C)
    itemInfo.add(5,lpItem.prerequisites);   // item의 prerequisites
    itemInfo.add(6,lpItem.prerequisitype);  // item의 prerequisitype
    itemInfo.add(7,lpItem.maxtimeallowed);  // item의 maxtimeallowed
    itemInfo.add(8,lpItem.timelimitaction); // item의 timelimitaction
    itemInfo.add(9,lpItem.datafromlms);     // item의 datafromlms
    itemInfo.add(10,lpItem.masteryscore);   // item의 masteryscore
    if (lpItem.isvisible) itemInfo.add(11,"T");      // item의 isvisible
    else                  itemInfo.add(11,"F");      // item의 isvisible
    itemInfoSet.add(itemInfo);

    return retRef;
  } /* END_OF_setItem */

  private String convertID(String str) {
      String Str = new String("000.000.000.000.000.000.000.000.000.000");
      char [] retChar = new char[39];
      for (int i=0;i<39;i++) { retChar[i]=Str.charAt(i); }
      int size = str.length();
      int k=0;
      for (int i=0;i<size;i=i+2) {
          retChar[k*4+1] = str.charAt(k*2);
          retChar[k*4+2] = str.charAt(k*2+1);
          k++;
      }
      //retChar[1] = str.charAt(0);
      //retChar[2] = (char)((int)str.charAt(1)+1);
      return new String(retChar);
  }

  /*****************************************************************************
   * private void setAttributes(Element hiRoot, Object obj)
   *    @description : DOM Element로부터 Attributes를 추출, 해당 객체의 변수에 저장.
   *    @param hiRoot : DOM Element
   *    @param obj : DOM Element의 저장 객체
   ****************************************************************************/
  private void setAttributes(Element hiRoot, Object obj) {
      String className = obj.getClass().getName();
      int cnt= hiRoot.getAttributes().getLength();
      for (int i=0; i<cnt; i++) {
          String tmpAttrName = hiRoot.getAttributes().item(i).getNodeName();
          if (className.equalsIgnoreCase("mediopia3.dbm.LPMain")) {
              ((LPMain)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
              Out_Identifier = ((LPMain)obj).identifier;
          } else if (className.equalsIgnoreCase("mediopia3.dbm.LPOrgs")) {
              ((LPOrgs)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
          } else if (className.equalsIgnoreCase("mediopia3.dbm.LPRscs")) {
              ((LPRscs)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
          } else if (className.equalsIgnoreCase("mediopia3.dbm.LPItem")) {
              ((LPItem)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
          } else if (className.equalsIgnoreCase("mediopia3.dbm.LPFile")) {
              ((LPFile)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
          } else if (className.equalsIgnoreCase("mediopia3.dbm.LPDependency")) {
              ((LPDependency)obj).setAttribute(tmpAttrName,hiRoot.getAttribute(tmpAttrName));
          }
      }
  } /* END_OF_setAttributes */

  /*****************************************************************************
   * private String getBasePath(String filename)
   *    @description : "파일경로+파일이름"으로부터 파일 경로명만을 추출한다.
   *    @param filename : 파일경로+파일이름
   ****************************************************************************/
  private String getBasePath(String filename) {
      String strBase = new String("");
      int idx = filename.lastIndexOf(FileDelimiter);
      if (idx<0) return "";
      strBase = filename.substring(0,idx);
      return strBase;
  }

  /*****************************************************************************
   * private String convertDelimiter(String str)
   *    @description : 입력된 문자열에서 "/"를 "\\"로 치환한 후, 문자열 앞에 "\\"를 붙인다.
   *    @param str : 변경하고자 하는 문자열
   ****************************************************************************/
  private String convertDelimiter(String str) {
//      for (int ptr=str.indexOf("/");ptr>=0;ptr=str.indexOf("/"))
//                       str = str.substring(0,ptr) + "\\" + str.substring(ptr+1);
//      return "\\" + str;
      return FileDelimiter + str;
  }

  /*****************************************************************************
   * private String getStr(int val)
   *    @description : 숫자값을 입력받아 두자리 문자형으로 변환.
   *    @param val : 숫자
   ****************************************************************************/
  private String getStr(int val) {
      Integer temp = new Integer(val);
      if (val>=0 && val<10) return "0" + temp.toString();
      else return temp.toString();
  }

  /*****************************************************************************
   * private void addMessage(String msg)
   *    @description : outMessage를 추가.
   *    @param msg : 추가할 메시지
   ****************************************************************************/
  private void addMessage(String msg) {
    Out_Message = Out_Message + msg;
  } /* END_OF_addMessage */

  /*****************************************************************************
   * private String getIDSuffix(int level)
   *    @description : element의 레벨에 따른 suffix를 결정한다.
   *    @param msg : element의 레벨
   *        > 레벨 0 --> 'a', 레벨 1 --> 'b', 레벨 2 --> 'c', ...
   ****************************************************************************/
  private String getIDSuffix(int level) {
      int   suffix, asciiA=97;   // 'a' : 97
      String retVal = new String();

      suffix = asciiA + level;
      return retVal.valueOf((char)suffix);
  }

  //****************************************************************************
  // DBMS 관련 메소드
  //****************************************************************************
  private void setLog(String msg) {
    String tmpStr = new String("");
    try {
        FileReader fr = new FileReader(logFilename);
        BufferedReader br = new BufferedReader(fr);
        for (String str = br.readLine(); str != null; str = br.readLine()) {
            tmpStr = tmpStr + str + "\n";
        }
        br.close();
    } catch (java.io.IOException ex) {}

    try {
        File ff = new File(logFilename);
        FileWriter fw = new FileWriter(ff);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(tmpStr + "[" + UT.getDate() + "] " + msg);
        bw.close();
    } catch (java.io.IOException ex) {}
  }


} // END_OF_LPAnalyzer(클래스)

/*
  public void setNextCMIDsuffix() {
      int   i1, i2, minV=65, maxV=90;   // 'A' : 65 , 'Z' : 90

      if (cmIDsuffix.equalsIgnoreCase("")) cmIDsuffix = "AA";
      else {
          i1 = (int)cmIDsuffix.charAt(0);
          i2 = (int)cmIDsuffix.charAt(1) + 1;
          if (i2 > maxV) { i1++;   i2=minV;  }
          cmIDsuffix = new String(new char [] {(char)i1, (char)i2});
      }
  }
*/
