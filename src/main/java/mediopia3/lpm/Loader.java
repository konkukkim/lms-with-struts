package mediopia3.lpm;
/**
 * <p>Title: 학습객체관리시스템</p>
 * <p>Description: SCORM기반의 학습객체관리시스템</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: (주)메디오피아</p>
 * @author 나재열
 * @version 1.0
 */
import java.util.*;
import java.io.*;
import java.util.zip.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.ServletInputStream;
import mediopia3.dbm.*;

public class Loader {

    TimeNow UT = new TimeNow();
    private String logFilename = new String("result.log");

    public String guid = null;
    private LPAnalyzer AB = null;
    public  ServletInputStream in;
    public  ZipFile zf;
    private static boolean _Debug = true;   //DebugIndicator.ON;
    String baseDir = new String();
    public String zipFileFolder = new String();
    public String zipFileName = new String();
    public String repLocation = new String();
    public Vector paraName = new Vector();
    public String paraCourseID = new String();
    public String courseID = new String();

    public String fileDelimiter = new String();
//    public String courseName = new String();

    public String outFilename = new String();
    public String outMessage = new String();
    public String paraOK = new String();

    public Vector itemInfoSet = new Vector();

    public Loader(ServletInputStream instream,String fn) {
        String MSG = new String();
        boolean err=false;
        this.in = instream;

        Coordinator co = new Coordinator(fn);
        MSG = co.setParameters();
        this.paraOK = MSG;
        if (MSG.equalsIgnoreCase("OK")) {
            repLocation = co.Repository;
            paraName = co.ParaName;
            fileDelimiter = co.FileDelimiter;
            paraCourseID = co.ParaCourseID;

            parseMultiForm pM = new parseMultiForm(in);
            File ff = null;
            FileOutputStream fout = null;
            try {
                int paraCnt = paraName.size();
                for (int i=0;i<paraCnt;i++) {
                    String tName = (String)((Vector)paraName.get(i)).get(1);
                    String tVal = pM.getParameter(tName);
                    ((Vector)paraName.get(i)).add(tVal);
//  System.out.println("[Loader] " + tName + "(" + tVal + ")");
                    setLog("[Loader] " + tName + "(" + tVal + ")");
                }
                courseID = getParaValue(paraCourseID);

                guid = UT.getDate() + "_" + courseID + "/";
                zipFileFolder = repLocation + guid;
                logFilename = zipFileFolder + logFilename;

                File newFile = new File(zipFileFolder);
                if (!newFile.exists()) {
                    newFile.mkdir();
                    setLog("[Loader] New CP Import (" + guid + ")");
                }
                zipFileName = zipFileFolder + pM.getFileName();
                setLog("[Loader] " + paraCourseID + "-->" + courseID);
                setLog("[Loader] zipFileName-->" + zipFileName);
                ff = new File(zipFileName);
                fout = new FileOutputStream(ff);
                if ( pM.UpFile(fout) ) fout.close();
                else err=true;
            } catch (java.io.IOException ex) {
                setLog("[Loader] Zip File Upload FAILED !!!");
            }
            if (err) setLog("[Loader] Zip File Upload FAILED !!!");
            else setZipFileName(zipFileName);
        }
    }

    public String getParaValue(String pName) {
        int paraCnt = paraName.size();
        for (int i=0;i<paraCnt;i++) {
            String tName = (String)((Vector)paraName.get(i)).get(0);
            if (tName.equalsIgnoreCase(pName)) return (String)((Vector)paraName.get(i)).get(2);
        }
        return "";
    }

    /****************************************************************************
    ** Method : 생성자
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 :
    ** 설   명 : Loader 클래스의 생성자
    *****************************************************************************/
    public void setZipFileName(String zipFileName) {
        //if ( _Debug ) System.out.println("\n버      전 ===>" + version());
        //if ( _Debug ) System.out.println("압축파일명===>" + zipFileName);
        if (extractAll(zipFileName, fileDelimiter)) {
            AB = new LPAnalyzer();
            AB.setLogFilename(logFilename);
            AB.setFileDelimiter(fileDelimiter);
            AB.setManifest(baseDir + fileDelimiter + "imsmanifest.xml");
            this.outFilename = AB.getFilename();
            this.outMessage = AB.getOut_Message();
            this.itemInfoSet = AB.itemInfoSet;
            setLog("[Loader-setZipFileName] CourseID-->" + courseID);
            setLog("[Loader-setZipFileName] item Information-->" + itemInfoSet.toString());
        }
    }
    public String getOutFilename() {
        return outFilename;
    }
    public String getOutMessage() {
        return outMessage;
    }

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

  public String getIdentifier() {
    return AB.Out_Identifier;
  } /* END_OF_getIdentifier */
  public String getSchema_Version() {
    return AB.Out_Schema_Version;
  } /* END_OF_getSchema_Version */
  public int getCMCount() {
    //  System.out.println("=============>" + AB.Out_CMCount);
    return AB.Out_CMCount;
  } /* END_OF_getCMCount */
  public int getORGCount() {
    return AB.Out_ORGCount;
  } /* END_OF_getORGCount */
  public Vector getORGTitle() {
    return AB.Out_ORGTitle;
  } /* END_OF_getORGTitle */
  public int getRSCCount() {
    return AB.Out_RSCCount;
  } /* END_OF_getRSCCount */
  public Vector getRSCType() {
    return AB.Out_RSCType;
  } /* END_OF_getRSCType */
  public int getSCOCount() {
    return AB.Out_SCOCount;
  } /* END_OF_getSCOCount */
  public int getASSETCount() {
    return AB.Out_ASSETCount;
  } /* END_OF_getASSETCount */
  public int getSubmanifest() {
    return AB.Out_Submanifest;
  } /* END_OF_getSubmanifest */

    /****************************************************************************
    ** Method : extractAll(zipFilename)
    ** 입   력 : (String)사용될 zip 파일명
    **          (Vector)zip파일의 내용(파일명)리스트
    ** 출   력 : none
    ** 설   명 :  입력받은 zip파일로부터
    **           입력받은 추출될 파일을 추출해서, 입력받은 저장할 폴더에 저장한다.
    *****************************************************************************/
    private boolean extractAll( String zipFileName, String dirDelimiter) {
        String zipFile = new String();
        String addDir = new String("");
        int entryCnt=0;

        try {
            baseDir = getBasePath(zipFileName, dirDelimiter);
            zipFile = getFilename(zipFileName, dirDelimiter);
            ZipEntry entry;                               //  zip파일의 Input stream
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

            byte[] buf = new byte[1024];
            int len;
            String tmpFilename = new String();
            String filename = new String();

            for (entry=in.getNextEntry();entry!=null;entry=in.getNextEntry()) {
                tmpFilename = entry.getName();
                if (entryCnt>=0) {
                    if (entryCnt==0) addDir = getBasePath1(tmpFilename, fileDelimiter);
                    if (addDir.equalsIgnoreCase(getBasePath1(tmpFilename, fileDelimiter))) entryCnt++;
                    else entryCnt = -1;
                    if (addDir.equalsIgnoreCase("")) entryCnt = -2;
                }
                //System.out.println("tmpFilename(F)" + addDir + "[" + entryCnt + "]===>" + tmpFilename);
                if ( !entry.isDirectory() ) {
                    filename = baseDir + fileDelimiter + convertNotation(tmpFilename, baseDir);
                    OutputStream out = new FileOutputStream(filename);
                    while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
                    out.close();
                }
            }
            in.close();
        } catch (IOException e) {
            setLog("[Loader-extractAll] CP UnCompressing Error !!! (" + zipFileName + ")");
            return false;
        }
        if (entryCnt>=0 ) baseDir = baseDir + fileDelimiter + addDir;
        return true;
    }

    /****************************************************************************
    ** Method : convertNotation()
    ** 입   력 :
    ** 출   력 :
    ** 설   명 :
    *****************************************************************************/
    private String convertNotation(String strFile, String strDir) {
        String strBase = new String();
        String strBefore = new String();
        String strAfter = new String();

        int idx = strFile.indexOf(fileDelimiter);
        if (idx<0) return strFile;
        strBefore = strFile.substring(0,idx);
        strAfter = strFile.substring(idx+1);
        strBase = strDir + fileDelimiter + strBefore;

        File newFile = new File(strBase);
        if (!newFile.exists()) {
            newFile.mkdir();
            setLog("[Loader-convertNotation] New Folder Created [" + strBase + "] ==> SUCCESS");
        }
        return strBefore + fileDelimiter + convertNotation(strAfter, strBase);
    }

    /****************************************************************************
    ** Method : getBasePath(String zipFilename)
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (String)zip 파일이 위치한 폴더의 절대경로명.
    ** 설   명 : 사용될 zip 파일을 입력받아 zip 파일이 위치한 절대 경로를 반환한다.
    *****************************************************************************/
    private String getBasePath(String str, String dirDelimiter) {
        String strBase = new String("");
        int idx = str.lastIndexOf(dirDelimiter);
        if (idx<0) return "";
        strBase = str.substring(0,idx);
        return strBase;
    }
    private String getBasePath1(String str, String dirDelimiter) {
        String strBase = new String("");
        int idx = str.indexOf(dirDelimiter);
        if (idx<0) return "";
        strBase = str.substring(0,idx);
        return strBase;
    }

    /****************************************************************************
    ** Method : getFileCount()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : zip 파일내의 엔트리 수
    ** 설   명 :  입력받은 zip파일로부터 zip 파일내의 엔트리 수를 구해 반환한다.
    *****************************************************************************/
    private int getFileCount( String zipFileName ) {
        int fileCount = 0;
        try {
            zf = new ZipFile(zipFileName);
            fileCount = zf.size();
            zf.close();
        } catch (IOException e) {
            fileCount = -1;
            setLog("[Loader-getFileCount] CP Parsing Error !!! (" + zipFileName + ")");
        }
        return fileCount;
    }

    /****************************************************************************
    ** Method : extract()
    ** 입   력 : (String)사용될 zip 파일명
    **          (String)zip파일로부터 추출하고자하는 파일명
    **          (String)zip파일로부터 추출한 파일을 저장할 폴더
    ** 출   력 : none
    ** 설   명 :  입력받은 zip파일로부터
    **           입력받은 추출될 파일을 추출해서, 입력받은 저장할 폴더에 저장한다.
    *****************************************************************************/
    private String extract( String zipFileName, String extractedFile,
                                 String pathOfExtract) {
        String nameOfExtractedFile = new String("");
        try {
            String pathAndName = new String("");
            //  Input stream for the zip file (package)
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
            //  Cut the path off of the name of the file. (for writing the file)
            int indexOfFileBeginning = extractedFile.lastIndexOf("/") + 1;
            nameOfExtractedFile = extractedFile.substring(indexOfFileBeginning);
            pathAndName= pathOfExtract + fileDelimiter + nameOfExtractedFile;
            //  Ouput stream for the extracted file
            OutputStream out = new FileOutputStream(pathAndName);
            //OutputStream out = new FileOutputStream(nameOfExtractedFile);

            ZipEntry entry;
            byte[] buf = new byte[1024];
            int len;
            int flag = 0;

            // entry.geName이나 extractedFile의 값이 널인 경우를 점검하는 기능 추가해야혀!!!
            while (flag != 1) {
                entry = in.getNextEntry();
                if ((entry.getName()).equalsIgnoreCase(extractedFile)) {
                    flag = 1;
                }
            }
            while ((len = in.read(buf)) > 0) out.write(buf, 0, len);
            out.close();
            in.close();
        } catch (IOException e) {
            setLog("[Loader-extract] IO Exception Caught: " + e.getMessage());
        }
        return nameOfExtractedFile;
    }

    /****************************************************************************
    ** Method : findManifest()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (Boolean)manifest 파일을 찾았는지의 여부.
    ** 설   명 : zip파일을 입력받아서, imsmanifest.xml 파일을 찾는다.
    *****************************************************************************/
    private boolean findManifest( String zipFileName ) {
        boolean rtn = false;
        try {
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
            ZipEntry entry;
            int flag = 0;
            while ( (flag != 1) && (in.available() != 0) )  {
                entry = in.getNextEntry();
                if (in.available() != 0) {
                    if ((entry.getName()).equalsIgnoreCase("imsmanifest.xml")) {
                        flag = 1;
                        rtn = true;
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            setLog("[Loader-findManifest] IO Exception Caught: " + e.getMessage());
        }
        return rtn;
    }

    /****************************************************************************
    ** Method : findMetadata()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (Boolean)XML 파일이 발견되었는지의 여부
    ** 설   명 : zip파일을 입력받아서, .xml확장자를 갖는 모든 파일을 위치시킨다.
    *****************************************************************************/
    private boolean findMetadata( String zipFileName ) {
        boolean rtn = false;
        String suffix = ".xml";
        try {
            //  The zip file being searched.
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
            //  An entry in the zip file
            ZipEntry entry;
            while ( (in.available() != 0) ) {
                entry = in.getNextEntry();
                if (in.available() != 0) {
                    if ( (entry.getName()).endsWith(suffix) ) {
                        rtn = true;
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            setLog("[Loader-findMetadata] IO Exception Caught: " + e.getMessage());
        }
        return rtn;
    }

    /****************************************************************************
    ** Method : locateMetadata()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (Vector)XMl 파일명의 Vector 리스트
    ** 설   명 : zip파일을 입력받아서, xml파일을 벡터 변수에 추가한다.
    *****************************************************************************/
    private Vector locateMetadata( String zipFileName ) {
        //  An array of names of xml files to be returned to ColdFusion
        Vector metaDataVector = new Vector();
        String suffix = ".xml";

        try {
            //  The zip file being searched.
            ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));
            //  An entry in the zip file
            ZipEntry entry;
            while ( (in.available() != 0) ) {
                entry = in.getNextEntry();
                if (in.available() != 0) {
                    if ( (entry.getName()).endsWith(suffix) ) {
                    if ( _Debug ) System.out.println(entry.getName());
                        metaDataVector.addElement(entry.getName());
                    }
                }
            }
            in.close();
        } catch (IOException e) {
            setLog("[Loader-locateMetadata] IO Exception Caught: " + e.getMessage());
        }
        return metaDataVector;
    }

    /****************************************************************************
    ** Method : getListOfMetadata()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (String)메타데이터 파일들의 리스트(콤마로 분리)
    ** 설   명 : zip파일을 입력받아서, xml파일명을 벡터 변수에 추가한후,
    **          콤마로 구분되는 문자열로 변환하여 반환한다.
    *****************************************************************************/
    private String getListOfMetadata( String zipFile ) {
        Vector mdVector = new Vector();
        mdVector = locateMetadata( zipFile );
        String mdString = new String();
        mdString = mdVector.toString();
        return mdString;
    }

    /****************************************************************************
    ** Method : main(String[] args)
    ** 입   력 :
    ** 출   력 :
    ** 설   명 : Loader 클래스에 대한 테스트 Main
    *****************************************************************************/
/*    public static void main(String[] args) {
        String zipFileName = new String("");
        zipFileName = "C:\\Documents and Settings\\Na JaeYeol\\My Documents\\mediopia\\dataTest\\";
        zipFileName = zipFileName + "MultipleSCOEx.zip";
        Loader ld = new Loader(zipFileName);
    }
*/
//################################################################################
    /****************************************************************************
    ** Method : version()
    ** 입   력 : none
    ** 출   력 : (String)클래스의 버전을 표시한다.
    ** 설   명 : 디버깅 툴로 사용. 클래스의 버전번호를 문자열로 반환한다.
    *****************************************************************************/
    public static String version() {
        String versionId = new String("");
        versionId = "CPM Loader Version 1.03 For mediopia LCMS";
        return versionId;
    }

    /****************************************************************************
    ** Method : getFilename(String zipFilename)
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (String)zip 파일의 파일명.
    ** 설   명 : 사용될 zip 파일을 입력받아 zip 파일의 파일명을 반환한다.
    *****************************************************************************/
    private String getFilename(String zipFileName, String dirDelimiter) {
        String filename = new String("");
        int idx = zipFileName.lastIndexOf(dirDelimiter);
        filename = zipFileName.substring(idx+1);
        return filename;
    }
    /****************************************************************************
    ** Method : getFileList()
    ** 입   력 : (String)사용될 zip 파일명
    ** 출   력 : (Vector)zip파일에 압축되어 있는 파일명들의 리스트
    ** 설   명 : zip 파일의 내용(파일명)을 Vector변수에 담아 반환한다.
    *****************************************************************************/
    private Vector getFileList(String zipFileName) {
        Vector fileList = new Vector();
        try {
            zf = new ZipFile(zipFileName);
            for (Enumeration entries = zf.entries(); entries.hasMoreElements();) {
                fileList.add( ((ZipEntry)entries.nextElement()).getName() );
            }
            zf.close();
        } catch (IOException e) {
            setLog("[Loader-getFileList] IO Exception Caught: " + e.getMessage());
        }
        return fileList;
    }

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

}