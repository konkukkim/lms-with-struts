package mediopia3.lom;

import java.io.*;
import java.util.Vector;
import org.apache.xerces.framework.XMLParser;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.xml.sax.InputSource;


public class CMRegister
{

 public CMRegister(String cmID, Element cmRoot)
 {
     CMID = new String();
     cmAnalyzer = null;
     CMID = cmID;
     cmAnalyzer = new CMAnalyzer(CMID, cmRoot);
 }

 public CMRegister(String cmID, String filename)
 {
     CMID = new String();
     cmAnalyzer = null;
     CMID = cmID;
     Document mfDocument = null;
     System.out.println("\nfilename=====>".concat(String.valueOf(String.valueOf(filename))));
     DOMParser domparser = new DOMParser();
     try
     {
         domparser.parse(new InputSource(new FileReader(filename)));
         mfDocument = domparser.getDocument();
         Element cmRoot = (Element)mfDocument.getFirstChild();
         cmAnalyzer = new CMAnalyzer(CMID, cmRoot);
     }
     catch(Exception ex)
     {
         System.err.println("Error: Invalid XML document");
         ex.printStackTrace(System.err);
     }
 }

 public CMRegister(String cmID, Reader rr)
 {
     CMID = new String();
     cmAnalyzer = null;
     CMID = cmID;
     Document mfDocument = null;
     System.out.println("\nrr.toString()=====>".concat(String.valueOf(String.valueOf(rr.toString()))));
     DOMParser domparser = new DOMParser();
     try
     {
         domparser.parse(new InputSource(rr));
         mfDocument = domparser.getDocument();
         Element cmRoot = (Element)mfDocument.getFirstChild();
         cmAnalyzer = new CMAnalyzer(CMID, cmRoot);
     }
     catch(Exception ex)
     {
         System.err.println("Error: Invalid XML document");
         ex.printStackTrace(System.err);
     }
 }

 public String getMessage()
 {
     return cmAnalyzer.Message;
 }

 public Vector getCMInfo()
 {
     return cmAnalyzer.ContentModel;
 }

 private String CMID;
 private CMAnalyzer cmAnalyzer;
}