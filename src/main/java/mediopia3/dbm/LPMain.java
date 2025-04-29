package mediopia3.dbm;
/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */
//import java.io.*;
import java.util.*;
import org.w3c.dom.*;

public class LPMain {
  /*****************************************************************************
   *************************   cpmanifest 테이블 구조   *************************
   *     필드명      |   타 입   |     필 드     설 명
   * ---------------+-----------+----------------------------------------------
   * cpid           | varchar2  | Content Package의 고유 아이디(시스템 자체 생성)
   * identifier     | varchar2  | CP의 식별자(SCORM v1.2에서 notDefined;예약되어있음)
   * version        | varchar2  | CP의 버전 (CP를 제작시 부여된 버전 번호)
   * base           | [cp_base] | Namespace 참조
   * schema         | varchar2  | CP의 schema 명
   * schemaversion  | varchar2  | CP의 schema 버전
   * cm             | [cm_main] | CP 참조 (링크 또는 Internal)
   * orgs_ref       | [cp_orgs] | organizations 테이블 참조
   * orgs_default   | varchar2  | organization들 중, default org의 identifier
   * orgs_count     | integer   | organizations에 포함된 organization의 수
   * rscs_ref       | [cp_rscs] | resources 테이블 참조
   * rscs_base      | varchar2  | 모든 resource에 공통으로 적용되는 base Path
   * rscs_count     | integer   | resources에 포함된 resource의 수
   * sub_manifest   | [cp_main] | cp_main 테이블 참조 (포함된 하위 manifest)
   * mf_count       | integer   | 포함된 하위 manifest의 수
   ****************************************************************************/
  /** LP의 아이디 구축을 위해 현재시각을 구하기 위한 클래스 **/
  TimeNow UT = new TimeNow();
  private String iLog = new String();

  private String id = new String();
  public String identifier = new String();
  private String version = new String();
  private String base = new String();
  private String schema = new String();
  private String schemaversion = new String();
  private String CM = new String();
  private String orgsDefault = new String();
  private int orgsCount=0;
  private String rscsBase = new String();
  private int rscsCount=0;
  private int mfCount=0;
  private String _QuatMark = "'";

  public LPMain(String nodeSuffix, String nodeID) {
      id = UT.getDate() + nodeSuffix + nodeID;
      iLog = "<br>CP_id==>" + id ;
  }

  public void setAttribute(String name, String val) {
      if (name.equalsIgnoreCase("identifier")) this.identifier = val;
      else if (name.equalsIgnoreCase("version")) this.version = val;
      else if (name.equalsIgnoreCase("xml:base")) this.base = val;
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
  public void setOrgsDefault(String val) {
      iLog = iLog + "<br>orgsDefault==>" + val;
      this.orgsDefault = val;
  }
  public void setOrgsCount(int val) {
      Integer temp = new Integer(val);
      iLog = iLog + "<br>orgsCount==>" + temp.toString();
      this.orgsCount = val;
  }
  public void setRscsBase(String val) {
      iLog = iLog + "<br>rscsBase==>" + val;
      this.rscsBase = val;
  }
  public void setRscsCount(int val) {
      Integer temp = new Integer(val);
      iLog = iLog + "<br>rscsCount==>" + temp.toString();
      this.rscsCount = val;
  }
  public void setMfCount(int val) {
      iLog = iLog + "<br>mfCount==>" + val;
      this.mfCount = val;
  }
//##############################################################################
  public String getID() {
      return id;
  }
  public String getStrSchema() {
    return "id, identifier, version, base, schema, schemaversion, CM, " +
            "orgsCount, orgsDefault, rscsBase, rscsCount, mfCount";
  }
  public String getAllField() {
    return "('" + id + "', '" + rmvQuatMark(identifier) + "', '"
           + rmvQuatMark(version) + "', '" + rmvQuatMark(base) + "', '"
           + rmvQuatMark(schema) + "', '" + rmvQuatMark(schemaversion) + "', '"
           + rmvQuatMark(CM) + "', " + orgsCount + ", '"
           + rmvQuatMark(orgsDefault) + "', '" + rmvQuatMark(rscsBase) + "', "
           + rscsCount + ", " + mfCount + ")";
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