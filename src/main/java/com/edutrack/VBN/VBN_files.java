/***********************************************************************************************

제 품 명 : 위스(WEAS)

버전 : 1.030929 (마지막 수정일 : 2003/09/29)

용도 : JAVA 파일 MultipartRequest Upload용

제품지원 : http://www.venturebrain.net

개 발 사 : (주)벤처브레인

저 작 권 : 

	1. 이 소스의 저작권은 (주)벤처브레인에 있습니다.

	2. 라이센스를 구매하지 않은 사이트에서는 사용하실 수 없습니다.
	   (무단 배포는 법적인 제재를 받습니다)

	3. 수정사항 이외의 부분을 임의로 수정하시면 안됩니다.

	4. 기타 의문사항은 webmaster@venturebrain.net을 이용해주십시오.
	
***********************************************************************************************/

package com.edutrack.VBN;

import java.io.File;

public class VBN_files { 

	
	//업로딩된 파일을 저장할 디렉토리를 미리 만들어 놓고 권한을 777로 주어야 함. 
		
	
	String mSaveDirPath = "";	//업로딩된 파일을 저장할 서버 로컬 절대 경로. 마지막 "/"는 붙이지 말 것! 예) "D:/Program Files/resin-1.2.3/doc/WEASFiles"
	
	
	String mFileSaveBaseURL = "";	//마지막 "/"는 붙이지 말 것! 예) "http://www.test.co.kr/board/WEASFiles", "./tmp"
	
	
	boolean debugMode = false; 
	//javax.servlet.jsp.JspWriter out;	//디버그용
	
	//호출 예, VBN_files v_objFile = new VBN_files("D:/Program Files/resin-1.2.3/doc/WEASFiles", "http://testna.gngnet.co.kr/board/", 10, false);
	public VBN_files(String saveDirPath, String fileSaveBaseURL) throws java.lang.Exception{
		try{
			mSaveDirPath = saveDirPath;
			mFileSaveBaseURL = fileSaveBaseURL;			
			
			//파일을 저장할 폴더가 있는 지 검사함. 
			//[주의!] 이 경로는 반드시 new MultipartRequest 할 때 사용한 경로와 일치하여야 함!!			
				java.io.File tmpFile;
				tmpFile = new File(saveDirPath);
				
				if(!tmpFile.isDirectory()){
					Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nThis file save path is not directory!!\\n\\n" + saveDirPath + "\\n\\nThis path must be the same path at new MultipartRequest class');</SCRIPT>");
					throw tmpErr;
				}
			
			mSaveDirPath = VBN_replace(mSaveDirPath, "\\", "/");
		}catch(Exception e){
			Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nThis file save path is not directory!!\\n\\n" + saveDirPath + "\\n\\nThis path must be the same path at new MultipartRequest class');</SCRIPT>");
			throw tmpErr;
		}
		
	}

/*
	public VBN_files(String saveDirPath, String fileSaveBaseURL, javax.servlet.jsp.JspWriter tmpOut) throws java.lang.Exception{
	//디버그용		
		try{
			mSaveDirPath = saveDirPath;
			mFileSaveBaseURL = fileSaveBaseURL;
			out = tmpOut;

			if(debugMode) out.println("<SCRIPT>alert('VBN_files() call ok!');</SCRIPT>" );
			
			//파일을 저장할 폴더가 있는 지 검사함. 
			//[주의!] 이 경로는 반드시 new MultipartRequest 할 때 사용한 경로와 일치하여야 함!!			
				java.io.File tmpFile;
				tmpFile = new File(saveDirPath);
				
				if(debugMode) out.println("<SCRIPT>alert('saveDirPath exist ok!\\n\\n" + saveDirPath + "\\n\\nThis path must be the same path at new MultipartRequest class!!');</SCRIPT>" );
				
				
				if(!tmpFile.isDirectory()){
					Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nThis file save path is not directory!!\\n\\n" + saveDirPath + "\\n\\nThis path must be the same path at new MultipartRequest class');</SCRIPT>");
					throw tmpErr;				
				}
				
			
			mSaveDirPath = VBN_replace(mSaveDirPath, "\\", "/");			
		}catch(Exception e){
			Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nThis file save path is not directory!!\\n\\n" + saveDirPath + "\\n\\nThis path must be the same path at new MultipartRequest class');</SCRIPT>");
			throw tmpErr;
		}
	}
*/

	
	// [주의] 이 함수는 DB쿼리문(insert,update) 실행 직전에 호출 할 것!
	public String VBN_uploadMultiFiles(String content, com.oreilly.servlet.MultipartRequest myRequest) throws java.lang.Exception{
		
		String formName, tmp, filePath, clientFilePath, fileName, saveFileName, saveFileURL, serverURL, content_old;
		long fileSize, fileLimitSize_byte, fileTotalSize_byte;
		int i, fileCount, deletedMediaFileCount, sIndex;
		float fileTotalSize;
		
		java.io.File tmpFile;
		
		try{
			//if(debugMode) out.println("<SCRIPT>alert('VBN_uploadMultiFiles() call ok!');</SCRIPT>" );
			
			if(myRequest == null){
				Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nMultipartRequest Object is NULL at calling function VBN_uploadMultiFiles()');history.back();</SCRIPT>");
				throw tmpErr;
			}
			
			
			
			if( new String("true").equals(myRequest.getParameter("VBN_FORM_WEAS")) == true ){
				
				//if(debugMode) out.println("<SCRIPT>alert('VBN_FORM_WEAS = true');</SCRIPT>");
				
				
				
				
				//3. 수정일 경우 삭제파일 삭제(시작)
				try{
					deletedMediaFileCount = 0;
					
					tmp = myRequest.getParameter("VBN_FORM_DeletedMediaFileCount");
					
					//if(debugMode) out.println("<SCRIPT>alert('VBN_FORM_DeletedMediaFileCount : " + tmp + "');</SCRIPT>" );
					
					if(tmp != ""){
						deletedMediaFileCount = Integer.parseInt(tmp);
					}
						
						if(deletedMediaFileCount >0){
							
							for(i = 1; i <= deletedMediaFileCount; i++){
								
								filePath = myRequest.getParameter("VBN_FORM_DeletedMediaFile_" + i);
								
								//if(debugMode) out.println("<SCRIPT>alert('Deleted filePath = " + filePath + "');</script>");
		
							      //URL을 로컬경로로 바꿔서 하드 디스크에서 파일 삭제
							      		if(filePath.indexOf(mFileSaveBaseURL) != -1){
							      			filePath = VBN_replace(filePath, mFileSaveBaseURL, mSaveDirPath);
								      		
								      		tmpFile = new File(filePath);
								      		
								      		if(tmpFile.exists()){
								      			tmpFile.delete();	//상대경로(URL)도 알아서 삭제해줌.
								      			//if(debugMode) out.println("<SCRIPT>alert('file delete ok! : " + filePath + "');</SCRIPT>");
								      		}else{
											//if(debugMode) out.println("<SCRIPT>alert('Editor Error Message : not found file for delete!\\n\\n" + filePath + "');history.back();</script>");
											//return content;	
								      		}
						   			}
							}//for
						}//if
				}catch(Exception e2){	
					//에러가 나도 그냥 작동시킨다. 중대한 게 아니니까.. 	
				}
				//3. 수정일 경우 삭제파일 삭제(끝)	
				
				tmp = myRequest.getParameter("VBN_FORM_MediaFileCount");
				
				fileCount =0;
				
				if(tmp != ""){
					fileCount = Integer.parseInt(tmp);
				}
				
				//if(debugMode) out.println("<SCRIPT>alert('VBN_FORM_MediaFileCount = " + fileCount + "');</script>");
				
				//4. 쓰기,수정,답변시 새로 업로딩된 파일 저장(시작)			
					
					int index = 0;
					
					for (i=1; i<= fileCount; i++){ 	
						
						formName = "VBN_FORM_MediaFile_" + i;
						
						clientFilePath = myRequest.getParameter(formName + "_Value"); //본문과 이것을 replace 해야 함.
						
						if(clientFilePath == null || clientFilePath == ""){
							Exception tmpErr = new Exception("<SCRIPT>alert('- Editor Error Message - \\n\\nyou need set this editor option! \\n\\n<param name=VBN_OPTION_fileLocalPathSendFlag value=\"true\">');history.back();</SCRIPT>");
							throw tmpErr;
						}
						
						saveFileName = myRequest.getFilesystemName(formName); //실제 저장된 파일명
						
						//if(debugMode) out.println("<SCRIPT>alert('clientFilePath = " + VBN_replace(clientFilePath, "\\", "\\\\") + "');</script>");
						//if(debugMode) out.println("<SCRIPT>alert('saveFileName = " + saveFileName + "');</script>");
					
								
						saveFileURL = mFileSaveBaseURL + saveFileName;
						
						//if(debugMode) out.println("<SCRIPT>alert('saveFileURL : " + saveFileURL + "');</script>");
						
						//replace 처리
						content_old = content;
						
						//4.본문의 파일경로를 서버URL로 변경									
						content = VBN_replace(content, clientFilePath, saveFileURL);       
						
						if(content_old.equals(content)){
							//if(debugMode) out.println("<SCRIPT>alert('- Editor Error Message - \\n\\ncannot replace file local path to file url at content!');history.back();</script>");
							//return content;
						}else{
							//if(debugMode) out.println("<SCRIPT>alert('replace ok!');</script>");
						}
			
					}//for
					
				//4. 쓰기,수정,답변시 새로 업로딩된 파일 저장(끝)
				
		
				
			}//위스로 작성된 글일때
			
			return content;			
		
		}catch(Exception e){
			throw e;
		}

	}
	
	
	//본문에 삽입된 멀티미디어 파일 삭제 - delete문 실행전에 본문을 인자로 넘겨서 호출할 것!
	public void VBN_deleteFiles(String content){
	
		int index, k, startIndex, endIndex, sIndex, eIndex;
		String tmpTag, tagName, filePath;
		java.io.File tmpFile;
		
		try{
		
			//if(debugMode) out.println("<SCRIPT>alert('VBN_deleteFiles() call ok!');</SCRIPT>" );
		
			if(content.indexOf(mFileSaveBaseURL) != -1){
				
				index = 0;	
				
				for(k=0; k<4; k++){
				
					startIndex = 0;
					
					if(k==0){
						tagName = "img";
					}else if(k==1){
						tagName = "IMG";
					}else if(k==2){
						tagName = "embed";
					}else{
						tagName = "EMBED";
					}
					
					//본문에서 <IMG>,<EMBED>태그 추출
					while(true){
						index = index + 1;
						
						//미디어 태그추출
							startIndex = content.indexOf("<" + tagName + " ", startIndex);
							
							if(startIndex != -1){
								endIndex = content.indexOf(">", startIndex);
								
								if(endIndex == -1) break;
							}else{
								break;
							}
							
							tmpTag = content.substring(startIndex, endIndex + 1) ;
							//if(debugMode) out.println("<Script>alert('delete file tag = " + tmpTag + "');</script>");
						
						filePath = "";
						
						//경로추출
							if(tmpTag.indexOf(" src") != -1 || tmpTag.indexOf(" SRC") != -1 ){
								
								sIndex = tmpTag.indexOf("\"" + mFileSaveBaseURL);
								
								
								//if(debugMode) out.println("<Script>alert('sIndex1 = " + sIndex + "');</script>");
								
								if(sIndex != -1){
								// ""로 쌓여져 있으면
									eIndex = tmpTag.indexOf("\"",sIndex + 1);
									
									//if(debugMode) out.println("<Script>alert('eIndex1 = " + eIndex + "');</script>");
									if(eIndex != -1){
										filePath = tmpTag.substring(sIndex + 1, eIndex) ;		// "는 제외하고
									}
								}else{
									sIndex = tmpTag.lastIndexOf(mFileSaveBaseURL);
									//if(debugMode) out.println("<Script>alert('sIndex2 = " + sIndex + "');</script>");
									
									if(sIndex != -1){
									//""없이 경로만 있을 경우 
									
										eIndex = tmpTag.indexOf(" ",sIndex + 1);
										//if(debugMode) out.println("<Script>alert('eIndex2 = " + eIndex + "');</script>");
										
										if(eIndex != -1){
										//중간에 있을경우
											filePath = tmpTag.substring(sIndex, eIndex) ;
										}else{
										//마지막에 있을 경우
											eIndex = tmpTag.indexOf(">",sIndex + 1);
											if(eIndex != -1){
												filePath = tmpTag.substring(sIndex, eIndex) ;
											}
										}
									}
								}
								
								//if(debugMode) out.println("<Script>alert('delete file path = " + filePath + "');</script>");
								
							}
						
						//파일 삭제
							if(tmpTag.indexOf(" ownFlag=") == -1 && filePath != ""){	//ownFlag="n"가 없을 때만 삭제함.
							
								filePath = VBN_replace(filePath, mFileSaveBaseURL, mSaveDirPath);
								
								tmpFile = new File(filePath);
						      		if(tmpFile.exists()){
						      			tmpFile.delete();	//상대경로(URL)로 알아서 삭제해줌.
						      			//if(debugMode) out.println("<SCRIPT>alert('delete file ok! : " + filePath + "');</SCRIPT>");
						      		}else{
									//if(debugMode) out.println("<SCRIPT>alert('Editor Error Message : not found delete file!\\n\\n" + filePath + "');</script>");
						      		}
							}else{
								//if(debugMode) out.println("<SCRIPT>alert('not delete item! : " + tmpTag + "');</SCRIPT>");
							}

						//오류대비						
						if(index > 1000){
							//if(debugMode) out.println("<SCRIPT>alert('Editor Error Message : index > 1000');</script>");
							break;
						}
						
						startIndex = endIndex;
						
					}//while
				}//for
			}		
		
		}catch(Exception e){
			//삭제 못해도 치명적이지 않기 때문에 오류는 버린다.
		}		
	}				    


    public String VBN_replace(String line, String oldString, String newString) { 
        int index = 0; 
        while ((index = line.indexOf(oldString, index)) >= 0) { 
            line = line.substring(0, index) + newString + line.substring(index + oldString.length()); 
            index += newString.length(); 
        } 
        return line; 
    } 	
	
}	