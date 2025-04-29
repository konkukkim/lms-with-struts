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

public class LPItem {
  /** LP의 아이디 구축을 위해 현재시각을 구하기 위한 클래스 **/
  private String iLog = new String();

  private String id = new String();
  private int nodeLevel;
  public String nodeID = new String();
  public String identifier = new String();
  public String identifierref = new String();
  public boolean isvisible = true;
  private String parameters = new String();
  public String title = new String();
  private int itemCount=0;
  private String schema = new String();
  private String schemaversion = new String();
  private String CM = new String();

  public String prerequisites = new String();
  public String prerequisitype = new String();
  public String maxtimeallowed = new String();
  public String timelimitaction = new String();
  public String datafromlms = new String();
  public String masteryscore = new String();
  private String _QuatMark = "'";

  public LPItem(String id) {
      this.id = id;
      iLog = "<br>ITEM_id==>" + id ;
  }
  public void setNodeID(String val) {
      iLog = iLog + "<br>nodeID==>" + val;
      this.nodeID = val;
  }
  public void setNodeLevel(int val) {
      iLog = iLog + "<br>nodeLevel==>" + val;
      this.nodeLevel = val;
  }
  public void setAttribute(String name, String val) {
      if (name.equalsIgnoreCase("identifier")) this.identifier = val;
      else if (name.equalsIgnoreCase("identifierref")) this.identifierref = val;
      else if (name.equalsIgnoreCase("isvisible")) this.isvisible = val.equalsIgnoreCase("true");
      else if (name.equalsIgnoreCase("parameters")) this.parameters = val;
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
  public void setPreRequisites(String val) {
      iLog = iLog + "<br>prerequisites==>" + val;
      this.prerequisites = val;
  }
  public void setPreRequisiType(String val) {
      iLog = iLog + "<br>prerequisitype==>" + val;
      this.prerequisitype = val;
  }
  public void setMaxTimeAllowed(String val) {
      iLog = iLog + "<br>maxtimeallowed==>" + val;
      this.maxtimeallowed = val;
  }
  public void setTimeLimitAction(String val) {
      iLog = iLog + "<br>timelimitaction==>" + val;
      this.timelimitaction = val;
  }
  public void setDataFromLMS(String val) {
      iLog = iLog + "<br>datafromlms==>" + val;
      this.datafromlms = val;
  }
  public void setMasteryScore(String val) {
      iLog = iLog + "<br>masteryscore==>" + val;
      this.masteryscore = val;
  }

//##############################################################################
  public String getID() {
      return id;
  }
  public String getStrSchema() {
    return "id, nodeLevel, nodeID, identifier, identifierref, isvisible, " +
            "parameters, title, itemCount, schema, schemaversion, CM " +
            "prerequisites, prerequisitype, maxtimeallowed, timelimitaction, " +
            "datafromlms, masteryscore";
  }
  public String getAllField() {
    return "('" + id + "', " + nodeLevel + ", '" + nodeID + "', '"
             + rmvQuatMark(identifier) + "', '" + rmvQuatMark(identifierref) + "', '"
             + isvisible + "', '" + rmvQuatMark(parameters) + "', '"
             + rmvQuatMark(title) + "', " + itemCount + ", '"
             + rmvQuatMark(schema) + "', '" + rmvQuatMark(schemaversion) + "', '"
             + rmvQuatMark(CM) + "', '" + rmvQuatMark(prerequisites) + "', '"
             + rmvQuatMark(prerequisitype) + "', '" + rmvQuatMark(maxtimeallowed) + "', '"
             + rmvQuatMark(timelimitaction) + "', '" + rmvQuatMark(datafromlms) + "', '"
             + rmvQuatMark(masteryscore) + "')";
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