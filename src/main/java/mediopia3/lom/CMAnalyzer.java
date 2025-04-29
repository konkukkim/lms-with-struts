package mediopia3.lom;

import java.util.Vector;
import mediopia3.com.MConstant;
import mediopia3.dbm.*;
import org.w3c.dom.*;

public class CMAnalyzer
 implements MConstant
{

 public CMAnalyzer(String cmID, Element cmRoot)
 {
     Message = new String();
     Out_Gidentifier = new String();
     Out_Gtitle = new String();
     Out_Gdescription = new String();
     Out_Gkeyword = new String();
     Out_Gstructure = new String();
     Out_Lversion = new String();
     Out_Lstatus = new String();
     Out_Lcontribute = new String();
     Out_Lrole = new String();
     Out_Lentity = new String();
     Out_Ldate = new String();
     Out_Mmetadataschema = new String();
     Out_Tformat = new String();
     Out_Tsize = new String();
     Out_Tlocation = new String();
     Out_Einteractivitytype = new String();
     Out_Elearningresourcetype = new String();
     Out_Einteractivitylevel = new String();
     Out_Etypicallearningtime = new String();
     Out_Rcost = new String();
     Out_Rcopyrightandotherrestrictions = new String();
     Out_Rdescription = new String();
     CMID = new String();
     CM_Main = null;
     ContentModel = new Vector();
     CmMain = new Vector();
     CmGeneral = new Vector();
     CmLifecycle = new Vector();
     CmMetametadata = new Vector();
     CmTechnical = new Vector();
     CmEducational = new Vector();
     CmRights = new Vector();
     CmRelation = new Vector();
     CmAnnotation = new Vector();
     CmClassification = new Vector();
     CmCommon = new Vector();
     CmTaxonPath = new Vector();
     CMID = cmID;
     CM_Main = new CMMain(CMID);
     analyzeCM(cmRoot);
     CmMain.add(CM_Main.getAllField());
     ContentModel.add(CmMain);
     ContentModel.add(CmGeneral);
     ContentModel.add(CmLifecycle);
     ContentModel.add(CmMetametadata);
     ContentModel.add(CmTechnical);
     ContentModel.add(CmEducational);
     ContentModel.add(CmRights);
     ContentModel.add(CmRelation);
     ContentModel.add(CmAnnotation);
     ContentModel.add(CmClassification);
     ContentModel.add(CmCommon);
     ContentModel.add(CmTaxonPath);
 }

 public void analyzeCM(Element cmRoot)
 {
     int gCnt = 0;
     int hCnt = 0;
     int iCnt = 0;
     for(Node cNode = cmRoot.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         String curNodeName = cNode.getNodeName();
         if(curNodeName.equalsIgnoreCase("general"))
         {
             setGeneral((Element)cNode, "A");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("lifecycle"))
         {
             setLifeCycle((Element)cNode, "B");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("metametadata"))
         {
             setMetaMetadata((Element)cNode, "C");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("technical"))
         {
             setTechnical((Element)cNode, "D");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("educational"))
         {
             setEducational((Element)cNode, "E");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("rights"))
         {
             setRights((Element)cNode, "F");
             continue;
         }
         if(curNodeName.equalsIgnoreCase("relation"))
         {
             setRelation((Element)cNode, "G", ++gCnt);
             continue;
         }
         if(curNodeName.equalsIgnoreCase("annotation"))
         {
             setAnnotation((Element)cNode, "H", ++hCnt);
             continue;
         }
         if(curNodeName.equalsIgnoreCase("classification"))
             setClassification((Element)cNode, "I", ++iCnt);
     }

 }

 private void setGeneral(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0, 0, 0, 0, 0, 0, 0
     };
     CM_Main.setCount('A');
     LOGeneral loGeneral = new LOGeneral(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("identifier"))
         {
             loGeneral.setElement(seq[CON00]++, CON00, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("title"))
         {
             loGeneral.setElement(seq[CON01]++, CON01, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("catalogentry"))
         {
             loGeneral.setElement(seq[CON02]++, CON02, getCatalogentry((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("language"))
         {
             loGeneral.setElement(seq[CON03]++, CON03, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("description"))
         {
             loGeneral.setElement(seq[CON04]++, CON04, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("keyword"))
         {
             loGeneral.setElement(seq[CON05]++, CON05, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("coverage"))
         {
             loGeneral.setElement(seq[CON06]++, CON06, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("structure"))
         {
             loGeneral.setElement(seq[CON07]++, CON07, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("aggregationlevel"))
             loGeneral.setElement(seq[CON08]++, CON08, getVocabulary((Element)cNode));
     }

     CmGeneral.add(loGeneral.getAllField());
 }

 private void setLifeCycle(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0
     };
     CM_Main.setCount('B');
     LOLifeCycle loLifeCycle = new LOLifeCycle(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("version"))
         {
             loLifeCycle.setElement(seq[CON00]++, CON00, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("status"))
         {
             loLifeCycle.setElement(seq[CON01]++, CON01, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("contribute"))
             loLifeCycle.setElement(seq[CON02]++, CON02, getContribute((Element)cNode));
     }

     CmLifecycle.add(loLifeCycle.getAllField());
 }

 private void setMetaMetadata(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0, 0, 0
     };
     CM_Main.setCount('C');
     LOMetaMetadata loMetaMetadata = new LOMetaMetadata(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("identifier"))
         {
             loMetaMetadata.setElement(seq[CON00]++, CON00, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("catalogentry"))
         {
             loMetaMetadata.setElement(seq[CON01]++, CON01, getCatalogentry((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("contribute"))
         {
             loMetaMetadata.setElement(seq[CON02]++, CON02, getContribute((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("metadatascheme"))
         {
             loMetaMetadata.setElement(seq[CON03]++, CON03, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("language"))
             loMetaMetadata.setElement(seq[CON04]++, CON04, cNode.getFirstChild().getNodeValue());
     }

     CmMetametadata.add(loMetaMetadata.getAllField());
 }

 private void setTechnical(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0, 0, 0, 0, 0
     };
     CM_Main.setCount('D');
     LOTechnical loTechnical = new LOTechnical(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("format"))
         {
             loTechnical.setElement(seq[CON00]++, CON00, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("size"))
         {
             loTechnical.setElement(seq[CON01]++, CON01, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(tName.equalsIgnoreCase("location"))
         {
             loTechnical.setElement(seq[CON02]++, CON02, getLocation((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("requirement"))
         {
             loTechnical.setElement(seq[CON03]++, CON03, getRequirement((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("installationremarks"))
         {
             loTechnical.setElement(seq[CON04]++, CON04, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("otherplatformrequirements"))
         {
             loTechnical.setElement(seq[CON05]++, CON05, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("duration"))
             loTechnical.setElement(seq[CON06]++, CON06, getDate((Element)cNode));
     }

     CmTechnical.add(loTechnical.getAllField());
 }

 private void setEducational(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 
         0
     };
     CM_Main.setCount('E');
     LOEducational loEducational = new LOEducational(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("interactivitytype"))
         {
             loEducational.setElement(seq[CON00]++, CON00, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("learningresourcetype"))
         {
             loEducational.setElement(seq[CON01]++, CON01, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("interactivitylevel"))
         {
             loEducational.setElement(seq[CON02]++, CON02, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("semanticdensity"))
         {
             loEducational.setElement(seq[CON03]++, CON03, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("intendedenduserrole"))
         {
             loEducational.setElement(seq[CON04]++, CON04, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("context"))
         {
             loEducational.setElement(seq[CON05]++, CON05, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("typicalagerange"))
         {
             loEducational.setElement(seq[CON06]++, CON06, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("difficulty"))
         {
             loEducational.setElement(seq[CON07]++, CON07, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("typicallearningtime"))
         {
             loEducational.setElement(seq[CON08]++, CON08, getDate((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("description"))
         {
             loEducational.setElement(seq[CON09]++, CON09, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("language"))
             loEducational.setElement(seq[CON10]++, CON10, cNode.getFirstChild().getNodeValue());
     }

     CmEducational.add(loEducational.getAllField());
 }

 private void setRights(Element root, String nodeAlias)
 {
     int seq[] = {
         0, 0, 0
     };
     CM_Main.setCount('F');
     LORights loRights = new LORights(CMID, nodeAlias, 1, this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("cost"))
         {
             loRights.setElement(seq[CON00]++, CON00, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("copyrightandotherrestrictions"))
         {
             loRights.setElement(seq[CON01]++, CON01, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("description"))
             loRights.setElement(seq[CON02]++, CON02, getLangString((Element)cNode));
     }

     CmRights.add(loRights.getAllField());
 }

 private void setRelation(Element root, String nodeAlias, int cnt)
 {
     int seq[] = {
         0, 0
     };
     CM_Main.setCount('G');
     LORelation loRelation = new LORelation(CMID, nodeAlias, cnt, getStr(cnt), this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("kind"))
         {
             loRelation.setElement(seq[CON00]++, CON00, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("resource"))
             loRelation.setElement(seq[CON01]++, CON01, getResource((Element)cNode));
     }

     CmRelation.add(loRelation.getAllField());
 }

 private void setAnnotation(Element root, String nodeAlias, int cnt)
 {
     int seq[] = {
         0, 0, 0
     };
     CM_Main.setCount('H');
     LOAnnotation loAnnotation = new LOAnnotation(CMID, nodeAlias, cnt, getStr(cnt), this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("person"))
         {
             loAnnotation.setElement(seq[CON00]++, CON00, getVCard((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("date"))
         {
             loAnnotation.setElement(seq[CON01]++, CON01, getDate((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("description"))
             loAnnotation.setElement(seq[CON02]++, CON02, getLangString((Element)cNode));
     }

     CmAnnotation.add(loAnnotation.getAllField());
 }

 private void setClassification(Element root, String nodeAlias, int cnt)
 {
     int seq[] = {
         0, 0, 0, 0
     };
     CM_Main.setCount('I');
     LOClassification loClassification = new LOClassification(CMID, nodeAlias, cnt, getStr(cnt), this);
     String tName = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         tName = cNode.getNodeName();
         if(tName.equalsIgnoreCase("purpose"))
         {
             loClassification.setElement(seq[CON00]++, CON00, getVocabulary((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("taxonpath"))
         {
             loClassification.setElement(seq[CON01]++, CON01, getTaxonPath((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("description"))
         {
             loClassification.setElement(seq[CON02]++, CON02, getLangString((Element)cNode));
             continue;
         }
         if(tName.equalsIgnoreCase("keyword"))
             loClassification.setElement(seq[CON03]++, CON03, getLangString((Element)cNode));
     }

     CmClassification.add(loClassification.getAllField());
 }

 private Vector getLocation(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(nullStr);
     retVal.set(1, root.getFirstChild().getNodeValue());
     retVal.set(0, root.getAttribute("type"));
     return retVal;
 }

 private Vector getRequirement(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(getVocaValue());
     retVal.add(getVocaValue());
     retVal.add(nullStr);
     retVal.add(nullStr);
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("type"))
         {
             retVal.set(0, getVocabulary((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("name"))
         {
             retVal.set(1, getVocabulary((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("minimumversion"))
         {
             retVal.set(2, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("maximumversion"))
             retVal.set(3, cNode.getFirstChild().getNodeValue());
     }

     return retVal;
 }

 private Vector getResource(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(getLSValue());
     retVal.add(getCEsValue());
     Vector vCEs = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("identifier"))
         {
             retVal.set(0, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("description"))
         {
             retVal.set(1, getLangString((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("catalogentry"))
             vCEs.add(getCatalogentry((Element)cNode));
     }

     if(vCEs.size() != 0)
         retVal.set(2, vCEs);
     return retVal;
 }

 private Vector getTaxonPath(Element root)
 {
     Vector retVal = new Vector();
     Vector vTX = new Vector();
     vTX.add(getTaxonValue());
     retVal.add(getLSValue());
     retVal.add(vTX);
     Vector vTXs = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("source"))
         {
             retVal.set(0, getLangString((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("taxon"))
             vTXs.add(getTaxon((Element)cNode));
     }

     if(vTXs.size() != 0)
         retVal.set(1, vTXs);
     return retVal;
 }

 private Vector getTaxon(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     Vector vTX = new Vector();
     vTX.add(getTaxonValue());
     retVal.add(nullStr);
     retVal.add(getLSValue());
     retVal.add(vTX);
     Vector vTXs = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("id"))
         {
             retVal.set(0, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("entry"))
         {
             retVal.set(1, getLangString((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("taxon"))
             vTXs.add(getTaxon((Element)cNode));
     }

     if(vTXs.size() != 0)
         retVal.set(2, vTXs);
     return retVal;
 }

 private Vector getCatalogentry0(Element root)
 {
     Vector retVal = new Vector();
     retVal.add(getVocaValue());
     retVal.add(getVocaValue());
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("catalog"))
         {
             retVal.set(0, getVocabulary((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("entry"))
             retVal.set(1, getVocabulary((Element)cNode));
     }

     return retVal;
 }

 private Vector getCatalogentry(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(getLSValue());
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("catalog"))
         {
             retVal.set(0, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("entry"))
             retVal.set(1, getLangString((Element)cNode));
     }

     return retVal;
 }

 private Vector getContribute(Element root)
 {
     Vector retVal = new Vector();
     retVal.add(getVocaValue());
     retVal.add(getCentityValue());
     retVal.add(getDateValue());
     Vector vCentity = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("role"))
         {
             retVal.set(0, getVocabulary((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("centity"))
         {
             vCentity.add(getVCard((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("date"))
             retVal.set(2, getDate((Element)cNode));
     }

     if(vCentity.size() != 0)
         retVal.set(1, vCentity);
     return retVal;
 }

 private Vector getLangString(Element root)
 {
     Vector retVal = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element) || !cNode.getNodeName().equalsIgnoreCase("langstring"))
             continue;
         String nullStr = new String();
         Vector oneString = new Vector();
         oneString.add(nullStr);
         oneString.add(nullStr);
         if(cNode.getAttributes().getLength() != 0)
             oneString.set(0, ((Element)cNode).getAttribute("xml:lang"));
         oneString.set(1, cNode.getFirstChild().getNodeValue());
         retVal.add(oneString);
     }

     return retVal;
 }

 private Vector getPVocabulary(Element root)
 {
     Vector retVal = new Vector();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
         if((cNode instanceof Element) && cNode.getNodeName().equalsIgnoreCase("vocabulary"))
             retVal.add(getVocabulary((Element)cNode));

     return retVal;
 }

 private Vector getVocabulary(Element root)
 {
     Vector retVal = new Vector();
     retVal.add(getLSValue());
     retVal.add(getLSValue());
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("source"))
         {
             retVal.set(0, getLangString((Element)cNode));
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("value"))
             retVal.set(1, getLangString((Element)cNode));
     }

     return retVal;
 }

 private String getVCard(Element root)
 {
     String retVal = new String();
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
         if((cNode instanceof Element) && cNode.getNodeName().equalsIgnoreCase("vcard"))
             retVal = cNode.getFirstChild().getNodeValue();

     return retVal;
 }

 private Vector getDate(Element root)
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(getLSValue());
     for(Node cNode = root.getFirstChild(); cNode != null; cNode = cNode.getNextSibling())
     {
         if(!(cNode instanceof Element))
             continue;
         if(cNode.getNodeName().equalsIgnoreCase("datetime"))
         {
             retVal.set(0, cNode.getFirstChild().getNodeValue());
             continue;
         }
         if(cNode.getNodeName().equalsIgnoreCase("description"))
             retVal.set(1, getLangString((Element)cNode));
     }

     return retVal;
 }

 private String getStr(int val)
 {
     Integer temp = new Integer(val);
     if(val >= 0 && val < 10)
         return "0".concat(String.valueOf(String.valueOf(temp.toString())));
     else
         return temp.toString();
 }

 private Vector getVocaValue()
 {
     Vector retVal = new Vector();
     retVal.add(getLSValue());
     retVal.add(getLSValue());
     return retVal;
 }

 private Vector getLSValue()
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     Vector oneString = new Vector();
     oneString.add(nullStr);
     oneString.add(nullStr);
     retVal.add(oneString);
     return retVal;
 }

 private Vector getCEsValue()
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     Vector vCatalogentry = new Vector();
     vCatalogentry.add(nullStr);
     vCatalogentry.add(getLSValue());
     retVal.add(vCatalogentry);
     return retVal;
 }

 public Vector getTaxonValue()
 {
     String nullStr = new String();
     Vector nullVector = new Vector();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(getLSValue());
     retVal.add(nullVector);
     return retVal;
 }

 private Vector getCentityValue()
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     Vector vCentity = new Vector();
     vCentity.add(nullStr);
     retVal.add(vCentity);
     return retVal;
 }

 private Vector getDateValue()
 {
     String nullStr = new String();
     Vector retVal = new Vector();
     retVal.add(nullStr);
     retVal.add(getLSValue());
     return retVal;
 }

 public String getGidentifier()
 {
     return Out_Gidentifier;
 }

 public String getGtitle()
 {
     return Out_Gtitle;
 }

 public String getGdescription()
 {
     return Out_Gdescription;
 }

 public String getGkeyword()
 {
     return Out_Gkeyword;
 }

 public String getGstructure()
 {
     return Out_Gstructure;
 }

 public String getLversion()
 {
     return Out_Lversion;
 }

 public String getLstatus()
 {
     return Out_Lstatus;
 }

 public String getLcontribute()
 {
     return Out_Lcontribute;
 }

 public String getLrole()
 {
     return Out_Lrole;
 }

 public String getLentity()
 {
     return Out_Lentity;
 }

 public String getLdate()
 {
     return Out_Ldate;
 }

 public String getMmetadataschema()
 {
     return Out_Mmetadataschema;
 }

 public String getTformat()
 {
     return Out_Tformat;
 }

 public String getTsize()
 {
     return Out_Tsize;
 }

 public String getTlocation()
 {
     return Out_Tlocation;
 }

 public String getEinteractivitytype()
 {
     return Out_Einteractivitytype;
 }

 public String getElearningresourcetype()
 {
     return Out_Elearningresourcetype;
 }

 public String getEinteractivitylevel()
 {
     return Out_Einteractivitylevel;
 }

 public String getEtypicallearningtime()
 {
     return Out_Etypicallearningtime;
 }

 public String getRcost()
 {
     return Out_Rcost;
 }

 public String getRcopyrightandotherrestrictions()
 {
     return Out_Rcopyrightandotherrestrictions;
 }

 public String getRdescription()
 {
     return Out_Rdescription;
 }

 public String Message;
 public String Out_Gidentifier;
 public String Out_Gtitle;
 public String Out_Gdescription;
 public String Out_Gkeyword;
 public String Out_Gstructure;
 public String Out_Lversion;
 public String Out_Lstatus;
 public String Out_Lcontribute;
 public String Out_Lrole;
 public String Out_Lentity;
 public String Out_Ldate;
 public String Out_Mmetadataschema;
 public String Out_Tformat;
 public String Out_Tsize;
 public String Out_Tlocation;
 public String Out_Einteractivitytype;
 public String Out_Elearningresourcetype;
 public String Out_Einteractivitylevel;
 public String Out_Etypicallearningtime;
 public String Out_Rcost;
 public String Out_Rcopyrightandotherrestrictions;
 public String Out_Rdescription;
 public static int CON00 = 0;
 public static int CON01 = 1;
 public static int CON02 = 2;
 public static int CON03 = 3;
 public static int CON04 = 4;
 public static int CON05 = 5;
 public static int CON06 = 6;
 public static int CON07 = 7;
 public static int CON08 = 8;
 public static int CON09 = 9;
 public static int CON10 = 10;
 public static int CON11 = 11;
 private String CMID;
 private CMMain CM_Main;
 public Vector ContentModel;
 private Vector CmMain;
 private Vector CmGeneral;
 private Vector CmLifecycle;
 private Vector CmMetametadata;
 private Vector CmTechnical;
 private Vector CmEducational;
 private Vector CmRights;
 private Vector CmRelation;
 private Vector CmAnnotation;
 private Vector CmClassification;
 public Vector CmCommon;
 public Vector CmTaxonPath;

}