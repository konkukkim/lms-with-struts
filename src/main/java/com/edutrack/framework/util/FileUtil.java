/*
 * Created on 2004. 7. 6.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.oreilly.servlet.MultipartRequest;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class FileUtil {
    // 환경설정을 가져오기 위한 Configuration 객체를 가져온다.
	private static Configuration config = ConfigurationFactory.getInstance().getConfiguration();	
	public static String UPLOAD_PATH = StringUtil.ReplaceAll(config.getString("framework.fileupload.path"),"WEB-INF/../","");
	public static String IMG_DIR = config.getString("framework.fileupload.imgdir");
	public static String CONTENTS_DIR = config.getString("framework.fileupload.contentsdir");
	public static String FILE_DIR = config.getString("framework.fileupload.filedir");
	public static String ABS_PATH = config.getString("framework.fileupload.abspath");
	public static String DATA_DIR = config.getString("framework.fileupload.datadir");
	
	private static int MAX_POST_SIZE = config.getInt("framework.fileupload.max_post_size");
	private static String ENCODING = config.getString("framework.fileupload.encoding");
	
	/**
	 * 에피루스 이북 DRM , 디지캡의 PDF DRM 파일 경로
	 */
	public static String EpyrusEbookDrmFile = config.getString("framework.EpyrusEbookDrmFile");
	public static String DigCapPdfDrmFile = config.getString("framework.DigCapPdfDrmFile");
	
	/**
	 * 
	 */
	public FileUtil() {
	}

	public static UploadFiles upload(HttpServletRequest req,String directory,String userid) throws Exception {
	    return upload(req,directory,userid,"A");
	}

	
	/**
	 * 파일 업로드 처
	 * @param req
	 * @param directory
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public static UploadFiles upload(HttpServletRequest req,String directory,String userid, String namemode) throws Exception {
	    return upload(req,directory,userid,namemode, MAX_POST_SIZE);
	}
	 
	/**
	 * 파일업로드 처리
	 */
	public static UploadFiles upload(HttpServletRequest req,String directory,String userid, String namemode,int maxsize) throws Exception {
		// set multipart request
		String uploadPath = directory;
		String realPath = UPLOAD_PATH + uploadPath;
		boolean check = setDirectory( realPath );
		
		MultipartRequest multipartRequest = null;

		// 업로드 파일에 대한 상태값과 파일 정보를 담아올 객체를 생성한다.
		UploadFiles files = new UploadFiles();
		try {
			multipartRequest = new MultipartRequest( req, realPath, maxsize*1024*1000, ENCODING, new ByTimestampFileRenamePolicy(userid,namemode));
		}
		catch( IOException e ) {
		    // 업로드 시 작업 실패했을 경우 올렸던 파일을 삭제한다. 
		    files.setStatus("E");
		    delFiles(files);
		    return files;
		}

		
		// 파일 업로드를 처리한 멀티파트 객체를 넣어준다.
		files.setMultipart(multipartRequest);
		files.setUploadPath(uploadPath);
		// get parameter via multipart request
		String fileName = "fileName";
		Enumeration attachedFiles = multipartRequest.getFileNames();
		
	    if( attachedFiles == null ) {
		    files.setStatus("N");
	        return files;
		} 
	    
	    UploadFile fileEntity = null;
	    File file = null;
	    long itemSize = 0;
	    String fileSystemName = "";
	    long paramFileSize = 0;
	    
	    
		while(attachedFiles.hasMoreElements()){		    
            fileName = (String)attachedFiles.nextElement();
            fileEntity = new UploadFile();
            fileEntity.setObjName(fileName);
            
            file = multipartRequest.getFile(fileName);
            if(file == null) continue;
            
            fileSystemName = multipartRequest.getFilesystemName(fileName);
            fileName = multipartRequest.getOriginalFileName(fileName);
            paramFileSize = file.length();
            
		    itemSize = file.length();
	
			if( itemSize > MAX_POST_SIZE*1000*1024 ) {
				// exceed item size
				file.delete();
				files.setStatus("O");
				return files;
			}else {	// 파일이 무사히 업로드 됨
			    files.setStatus("S");
	            
	            fileEntity.setRootName(fileName);
	            fileEntity.setUploadName(fileSystemName);
	            fileEntity.setSize(paramFileSize);  
	            
	            
	            
	            files.addFile(fileEntity);
			}
			
		}
		
		return files;
	}
	
	/**
	 *  서버쪽 업로드 파일 삭제 
	 * @param directory
	 * @return
	 */
	public static void	 delFiles(UploadFiles files) {
        String path = "";
	    String dir = UPLOAD_PATH+files.getUploadPath();
        ArrayList fileList = files.getFiles();
        UploadFile file = null;
        for(int i =0; i < fileList.size(); i++){
            file = (UploadFile)fileList.get(i);
            path = dir+"/"+file.getUploadName();
            delFile(path);
        }
	}

	/**
	 * 파일을 삭제한다.
	 * @param path
	 */
	public static  void	 delFile(String  path) {    
	    File cFile = new File(path); 
		if(cFile.exists()) cFile.delete();		
	}	

	/**
	 *  파일을 삭제해준다.
	 * @param systemcode
	 * @param path
	 * @param filename
	 */ 
	public static  void	 delFile(String  path, String filename ) {
	    String dir = UPLOAD_PATH +path +"/"+filename;
	    File cFile = new File(dir); 
		if(cFile.exists()) cFile.delete();		
	}	
	
	/**
	 * 업로드 디렉토리 세팅
	 */
	public static boolean setDirectory( String directory) {
		File wantedDirectory = new File(directory);
		if (wantedDirectory.isDirectory())
			return true;
	    
		return wantedDirectory.mkdirs();
	}
	
	/**
	 * 파일을  카피해준다. 
	 * sourcedir : 원본 파일이 들어가 있는 디렉토리 예) file1/lmcourse
	 * targetdir   :   새로 생성할  파일이 들어가 있는 디렉토리 예) file1/lmsystem
	 * sourcefilename : 원본 파일명 예) lmcourse_00000000.gif
	 * targetfilename : 새로 생성할  파일명 예) lmsystem_00000000.gif
	 * @param sourcedir
	 * @param targetdir
	 * @param sourefilename
	 * @param targetfilename
	 * @return
	 */
	public  static boolean fileCopy(String systemcode, String sourcedir, String targetdir, String sourefilename, String targetfilename){
	      int i, len=0;
	      InputStream in=null;
	      OutputStream out=null;
	      	
	      //String sourcePath = UPLOAD_PATH + "file"+ systemcode+ sourcedir;
	      //String targetPath = UPLOAD_PATH + "file" + systemcode + targetdir;
	      
	      String sourcePath = UPLOAD_PATH + sourcedir;
	      String targetPath = UPLOAD_PATH + targetdir;

	      if(!setDirectory(targetPath)) return false; 
	      
	      sourcePath += "/" + sourefilename;
	      targetPath += "/" + targetfilename;

	        try {

		       in = new FileInputStream(new File(sourcePath));
	
		        out = new FileOutputStream(targetPath, true);

	           while((i=in.read()) != -1) {
	              out.write(i);
	              len++;
	           }

	           in.close();
	           out.close();

	           return true;
	        } catch(IOException e1) {
	           e1.printStackTrace(); 
	        }
	    
	    return false;
	}
	
    /**
     * 파일의 확장명 검색 함수
     * @param fname
     * @return
     */	
	public static String	fn_file_ext(String fname) {
		if (!fname.equals("")) {
			int		lst_in			=	fname.lastIndexOf('.');
			String	ext				=	fname.substring(lst_in+1);
			return	ext.toLowerCase();
		}
		else {
			return	"";
		}
	}

	/**
	 * 파일 확장명에 따른 이미지 출력 함수
	 * @param fname
	 * @param fwhere
	 * @return
	 */ 
	public static String	fn_file_img(String fname, String fwhere) {
		String	ext					=	null;
		String	type_img;
		if (!fname.equals("")) {
			ext						=	fn_file_ext(fname);

			if (ext.equals("gz") || ext.equals("zip") || ext.equals("rar") || ext.equals("arj")
				|| ext.equals("lzh") || ext.equals("tar")) {
				type_img			=	"compressed";
			}
			else if (ext.equals("gif") || ext.equals("jpg") || ext.equals("bmp") || ext.equals("pcx")
				|| ext.equals("tif")) {
				type_img			=	"image";
			}
			else if (ext.equals("exe") || ext.equals("com") || ext.equals("dll")) {
				type_img			=	"exe";
			}
			else if (ext.equals("htm") || ext.equals("html")) {
				type_img			=	"html";
			}
			else if (ext.equals("hwp")) {
				type_img			=	"hwp";
			}
			else if (ext.equals("mov") || ext.equals("avi") || ext.equals("mpg") || ext.equals("mpeg")) {
				type_img			=	"movie";
			}
			else if (ext.equals("mp3")) {
				type_img			=	"mp3";
			}
			else if (ext.equals("rm") || ext.equals("ra") || ext.equals("ram")) {
				type_img			=	"ra";
			}
			else if (ext.equals("wav") || ext.equals("mod") || ext.equals("mid")) {
				type_img			=	"sound";
			}
			else if (ext.equals("txt") || ext.equals("log") || ext.equals("dat") || ext.equals("ini")) {
				type_img			=	"text";
			}
			else if (ext.equals("xls") || ext.equals("csv")) {
				type_img			=	"excel";
			}
			else if (ext.equals("doc")) {
				type_img			=	"word";
			}
			else if (ext.equals("ppt")) {
				type_img			=	"ppt";
			}
			else if (ext.equals("rlt")) {
				type_img			=	"rlt";
			}
			else {
				type_img			=	"unknown";
			}
			return	 fwhere+"common/file_type/"+ type_img + ".gif";
		}
		else {
			return	"";
		}
	}

    /**
     * 서버경로에서 파일명만 추출하는 함수
     * @param fname
     * @return
     */
	public static String	fn_file_name(String fname) {
		if (!fname.equals("")) {
			int		lst_in			=	fname.lastIndexOf('/');
			String	file_name		=	fname.substring(lst_in+1);
			return	file_name;
		}
		else {
			return	"";
		}
	}

    /**
     * 환경에 따라 서버 절대경로를 표시하는 함수
     * @return
     */
	public static String	fn_file_road() {
		String		result			=	"..";
		return		result;
	}

    /**
     * 컨텐츠 타입을 리턴하는 함수
     * @param str
     * @return
     */
	public static String	fn_file_type(String str) {
		String	file_type			=	fn_file_ext(str);
		if(file_type.equals("html") || file_type.equals("htm") || file_type.equals("HTML") || file_type.equals("HTM") || file_type.equals("jpg")|| file_type.equals("JPG") || file_type.equals("gif")|| file_type.equals("GIF") || file_type.equals("txt") || file_type.equals("TXT") || file_type.equals("yaz") || file_type.equals("YAZ")) {
			return "T";
		}
		if(file_type.equals("wmv") || file_type.equals("WMV") || file_type.equals("asf") || file_type.equals("ASF")) {
			return "V";
		}
		//--	Live Share File(추가)
		if(file_type.equals("lsa") || file_type.equals("LSA") || file_type.equals("lsv") || file_type.equals("LSV")) {
			return "L";
		}
		if(file_type.equals("ouc") || file_type.equals("OUC")) {
			return "O";
		}
		//--	ActiveTutor file(추가)
		if(file_type.equals("arf") || file_type.equals("ARF")) {
			return "A";
		}
		else {
			return "B";
		}
	}	
	
	/** 
	 * 주어진 클래스의 인스턴스를 만들어 반환
	 * 
	 * @param className
	 * @return
	 */
	public static Object newInstance(String className)
	{
		Object instance = null;

		ClassLoader loader = FileUtil.class.getClassLoader();
		try
		{
			//instance = Class.forName(className).newInstance();		
			instance = loader.loadClass(className).newInstance();
			
		} catch(ClassNotFoundException cnfe)
		{
			cnfe.printStackTrace();
			
		} catch(IllegalAccessException iae)
		{
			iae.printStackTrace();
			
		} catch(InstantiationException ie)
		{
			ie.printStackTrace();
			
		}
		
		return instance;
		
	}
	
	/**
	 * 파일 확장자를 리턴, 확장자가 없으면 "" 를 리턴
	 * 
	 * @param filename 파일명
	 * @return extention 파일명 확장자 스트링 
	 */
	public static String getFileExtention(String filename)
	{
		String extention = "";
		
		if (filename != null)
		{
			int sepIndex = filename.lastIndexOf(".");
			if (sepIndex > 0 ) 
				extention = filename.substring(sepIndex);
		}
		return extention; 
	}	

   /**
    * 파일명을 리턴, 확장자가 없으면 "" 를 리턴
    *
    * @param file 파일
    * @return filename 파일명 스트링
    */
   public static String getFileName(String file)
   {
           String filename = "";

           if (file != null)
           {
                   int sepIndex = file.lastIndexOf(".");
                   if (sepIndex > 0 )
                           filename = file.substring(0,sepIndex);
           }
           return filename;
   }
   
   /**
    * 파일전체 경로중 파일명을 제외한 경로만 가져온다.
    * @param filePath
    * @return
    */
   public static String getFileDir(String filePath)
   {
           String dirName = "";

           if (filePath != null)
           {
                   int sepIndex = filePath.lastIndexOf("/");
                   if (sepIndex > 0 )
                   	dirName = filePath.substring(0,sepIndex);
           }
           return dirName;
   }

	public static void saveToFileParameter(HttpServletRequest request, String filename){
		request.setAttribute("output", "file");
		request.setAttribute("inline", "false");
		request.setAttribute("filename", filename);
	}
	
	public static void createFile(String path, String filename, String msg){
	    OutputStream out=null; 
	    try { 
	      setDirectory(path);
	      out = new FileOutputStream(path+"/"+filename, false); 
	      out.write(msg.getBytes());
		  out.close(); 
	    } catch(Exception e) { 
	      System.out.println(e); 
	    }finally{ 
	    	if(out != null){
	    		try{
	    		   out.close();
	    		}catch(Exception e){
	    			 System.out.println(e); 
	    		}
	    	}
	    }
	}
}
