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

import com.oreilly.servlet.multipart.FileRenamePolicy;
import java.io.*;
import java.util.Calendar;

public class ImageRenamePolicy implements FileRenamePolicy {
  
	public File rename(File upFile){
		
		String fileName = upFile.getName();	//원래 파일명
		String fileExe = "";	//원래 확장자

		String newFileNameBody = ""; 	//확장자를 제외한 새 파일명
		String newFileName = "";	//새 파일명(확장자 포함)
		
		int index = 0;	//파일명 중복시 증가할 인덱스
		int dotIndex;
		String tmpStr = "";
		
		boolean imageFlag = false;	//이미지일 경우		
		
		java.io.File tmpFile;		//파일명이 정해진 새 파일 객체 
		

		dotIndex = fileName.lastIndexOf(".");
		
		if(dotIndex != -1){
			fileExe = fileName.substring(dotIndex);		// '.'이 포함됨
			newFileNameBody = fileName.substring(0, dotIndex);		// '.'을 제외한 파일명
		}else{
			newFileNameBody = fileName;
		}
		
		//확장자가 이미지일 경우만
		tmpStr = fileExe.toLowerCase();
		
		//이미지인지 검사 
		if(tmpStr.indexOf("jpg") != -1 || tmpStr.indexOf("jpeg") != -1 || tmpStr.indexOf("gif") != -1 || tmpStr.indexOf("bmp") != -1 || tmpStr.indexOf("ico") != -1){
			imageFlag = true;
		}
		
		if(imageFlag){
		//이미지인 경우
		
			//파일명 설정(파일명을 날짜기준으로 바꿈)
			while(true){
				
				index = index + 1;
				
				newFileNameBody = "";
				
				//현재 시간을 알아옴(시작)
					Calendar objNow = Calendar.getInstance();
					
					newFileNameBody += toString(objNow.get(objNow.YEAR));		//년 
					newFileNameBody += toString(objNow.get(objNow.MONTH) + 1);	//월
					newFileNameBody += toString(objNow.get(objNow.DAY_OF_MONTH));	//일
					
					newFileNameBody += "_";	//분리자
					
					newFileNameBody += toString(objNow.get(objNow.HOUR_OF_DAY));		//시
					newFileNameBody += toString(objNow.get(objNow.MINUTE));	//분
		
					newFileNameBody += "_";	//분리자
					
					newFileNameBody += toString(objNow.get(objNow.SECOND));	//초
					newFileNameBody += "" + index;		//중복시 증가할 인덱스
				//현재 시간을 알아옴(끝)
				
				newFileName = newFileNameBody + fileExe;	//원래 확장자를 붙임
					
				tmpFile = new File(upFile.getParent(), newFileName);
				
				//중복파일 검사
				try{
					if(tmpFile.createNewFile()){
						//중복 파일이 없으면 성공이므로 나간다. 
						break;
					}//if
				}catch(Exception e){}		
				
				if(index > 10000) break;	//1초에 파일이 1만개 이상 올라올 수 없다고 가정.
				
				tmpFile = null;
				objNow = null;
			}		
		}else{
		//이미지가 아닐 경우 - 기존 메커니즘대로 함. 뒤에 숫자를 붙임.
			
			//파일명 설정(파일명을 날짜기준으로 바꿈)
			tmpStr = "";
			
			while(true){
				
				newFileName = newFileNameBody + tmpStr + fileExe;	//원래 확장자를 붙임
					
				tmpFile = new File(upFile.getParent(), newFileName);
				
				//중복파일 검사
				try{
					if(tmpFile.createNewFile()){
						//중복 파일이 없으면 성공이므로 나간다. 
						break;
					}//if
				}catch(Exception e){}		
				
				index = index + 1;
				tmpStr = "" + index;	//뒤에 숫자를 붙임.
				
				if(index > 10000) break;
				
				tmpFile = null;
			}
		}
		
		return tmpFile;
		
	}
	
	//숫자를 2자리 문자로 리턴 
	private String toString(int tmp){
		if(tmp < 10){
			return "0" + tmp;
		}else{
			return tmp + "";
		}
	}
	
}

