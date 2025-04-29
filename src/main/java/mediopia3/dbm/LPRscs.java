package mediopia3.dbm;

/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */
import org.w3c.dom.*;

public class LPRscs {
  /** LP의 아이디 구축을 위해 현재시각을 구하기 위한 클래스 **/
  private String iLog = new String();

  private String id = new String();
  private String nodeID = new String();
  public String identifier = new String();
  public String type = new String();
  public String scormtype = new String();
  private String base = new String();
  public String href = new String();

  private String schema = new String();
  private String schemaversion = new String();
  private String CM = new String();

  private int fileCount=0;
  private int dependencyCount=0;
  private String _QuatMark = "'";

  public LPRscs(String id) {
      this.id = id;
      iLog = "<br>RSC_id==>" + id ;
  }
  public void setNodeID(int val) {
      Integer temp = new Integer(val);
      String strTemp = new String();
      if (val<10) strTemp = "0" + temp.toString();
      else strTemp = temp.toString();
      iLog = iLog + "<br>nodeID==>" + strTemp;
      this.nodeID = strTemp;
  }
  public void setAttribute(String name, String val) {
      if (name.equalsIgnoreCase("identifier")) this.identifier = val;
      else if (name.equalsIgnoreCase("type")) this.type = val;
      else if (name.equalsIgnoreCase("adlcp:scormtype")) this.scormtype = val;
      else if (name.equalsIgnoreCase("xml:base")) this.base = val;
      else if (name.equalsIgnoreCase("href")) this.href = val;
      iLog = iLog + "<br>" + name + "==>" + val;
  }

  public void setSchema(String val) {
      iLog = iLog + "<br>schema==>" + val;
      this.schema = val;
  }
  public void setSchemaversion(String val) {
      iLog = iLog + "<br>schemaversion==>" + val;
      this.schemaversion = val;
  }
  public void setContentModel(String val) {
      iLog = iLog + "<br>CM==>" + val;
      this.CM = val;
  }
  public void setFileCount(int val) {
      iLog = iLog + "<br>fileCount==>" + val;
      this.fileCount = val;
  }
  public void setDependencyCount(int val) {
      iLog = iLog + "<br>dependencyCount==>" + val;
      this.dependencyCount = val;
  }
//##############################################################################
  public String getBase() {
      return base;
  }
  public String getID() {
      return id;
  }
  public String getStrSchema() {
    return "id, nodeID, identifier, type, scormtype, base, href, schema," +
            "schemaversion, CM, fileCount, dependencyCount";
  }
  public String getAllField() {
    return "('" + id + "', '" + nodeID + "', '" + rmvQuatMark(identifier) + "', '"
            + rmvQuatMark(type) + "', '" + rmvQuatMark(scormtype) + "', '"
            + rmvQuatMark(base) + "', '" + rmvQuatMark(href) + "', '"
            + rmvQuatMark(schema) + "', '" + rmvQuatMark(schemaversion) + "', '"
            + rmvQuatMark(CM) + "', " + fileCount + ", " + dependencyCount + ")";
  }
  public String getILog() {
      return iLog;
  }
  public String rmvQuatMark(String str) {
      int intPos;
      try {
          intPos = str.indexOf(_QuatMark);
          while (intPos != -1) {
             str = str.substring(0,intPos) + _QuatMark + _QuatMark + str.substring(intPos+1);
             intPos = str.indexOf(_QuatMark,intPos+2);
          }
      } catch(Exception e) {
          System.out.println("rmvQuatMark=====>" + e.getMessage());
      }
      return str;
  }
}