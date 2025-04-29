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

public class LPOrgs {
  /** LP의 아이디 구축을 위해 현재시각을 구하기 위한 클래스 **/
  private String iLog = new String();

  private String id = new String();
  private String nodeID = new String();
  private String identifier = new String();
  private String structure = new String();
  private String title = new String();
  private int itemCount=0;
  private String schema = new String();
  private String schemaversion = new String();
  private String CM = new String();
  private String _QuatMark = "'";

  public LPOrgs(String id) {
      this.id = id;
      iLog = "<br>ORG_id==>" + id ;
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
      else if (name.equalsIgnoreCase("structure")) this.structure = val;
      iLog = iLog + "<br>" + name + "==>" + val;
  }

  public void setTitle(String val) {
      iLog = iLog + "<br>title==>" + val;
      this.title = val;
  }
  public void setItemCount(String val) {
      Integer temp = new Integer(val);
      iLog = iLog + "<br>itemCount==>" + temp.intValue();
      this.itemCount = temp.intValue();
  }
  public void setItemCount(int val) {
      iLog = iLog + "<br>itemCount==>" + val;
      this.itemCount = val;
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
//##############################################################################
  public String getID() {
      return id;
  }
  public String getStrSchema() {
    return "id, nodeID, identifier, structure, title, itemCount, schema, " +
            "schemaversion, CM";
  }
  public String getAllField() {
    return "('" + id + "', '" + nodeID + "', '" + rmvQuatMark(identifier) + "', '"
            + rmvQuatMark(structure) + "', '" + rmvQuatMark(title) + "', "
            + itemCount + ", '" + rmvQuatMark(schema) + "', '"
            + schemaversion + "', '" + rmvQuatMark(CM) + "')";
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