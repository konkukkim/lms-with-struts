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
import java.util.Vector;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.w3c.dom.*;

public class Coordinator {

  public String Repository = new String();
  public String ParaCourseID = new String();
  public Vector ParaName = new Vector();
  public String FileDelimiter = new String();
  public String QuatMark = new String();

  private Document Document = null;
  private Element root = null;

  private String FileName = new String();

  public Coordinator(String fn) {
      FileName = fn;
  }

  public String setParameters() {
//      String filename = System.getProperty("user.home",".") + "\\parameters.xml";
      String filename = FileName;
      DOMParser domparser = new DOMParser();
      try {
            domparser.parse(new InputSource(new FileReader(filename)));
            Document = domparser.getDocument();
            root = (Element)Document.getFirstChild();
      } catch (Exception ex) {
            return "[Coordinator-setParameters] Invalid XML Document[parameters.xml] (" + ex.getMessage() + ")";
      } // END_OF_TRY

      for (Node cNode = root.getFirstChild();
                cNode != null;
                cNode = cNode.getNextSibling()) {
          if ( cNode instanceof Element ) {
              String curNodeName = cNode.getNodeName();
              if ( curNodeName.equalsIgnoreCase("parameter"))
                  getParameter((Element)cNode);
              if ( curNodeName.equalsIgnoreCase("etc"))
                  getEtc((Element)cNode);
          }
      } // END_OF_FOR
      return "OK";
  } // END_OF_setParameters

  private void getParameter(Element root) {
      NodeList nodeList = root.getChildNodes();

      int nodeCnt = nodeList.getLength();
      if (nodeCnt==0) return;
      ParaCourseID = root.getAttribute("default");
      Node cNode = null;

      for (int i=0;i<nodeCnt;i++) {
          cNode = nodeList.item(i);
          if ( cNode instanceof Element ) {
              Vector onePair = new Vector();
              onePair.add(cNode.getNodeName());
              onePair.add(cNode.getFirstChild().getNodeValue());
//System.out.println("나재열1====> [" + cNode.getNodeName() + "]" + cNode.getFirstChild().getNodeValue());
              ParaName.add(onePair);
          }
      } /* END_OF_FOR */
  } /* END_OF_getParameter */

  private void getEtc(Element root) {
      if (root.getChildNodes().getLength()==0) return;
      for (Node cNode = root.getFirstChild();
                cNode != null;
                cNode = cNode.getNextSibling()) {
          if ( cNode instanceof Element ) {
              String curNodeName = cNode.getNodeName();
              if ( curNodeName.equalsIgnoreCase("Repository"))
                  this.Repository = cNode.getFirstChild().getNodeValue();
              if ( curNodeName.equalsIgnoreCase("FileDelimiter"))
                  this.FileDelimiter = cNode.getFirstChild().getNodeValue();
          } /* END_OF_IF */
      } /* END_OF_FOR */
  } /* END_OF_getEtc */

}