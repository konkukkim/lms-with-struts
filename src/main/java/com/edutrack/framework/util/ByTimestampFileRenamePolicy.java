/**
* Class Name : ByTimestampFileRenamePolicy.java
* 내용 : 중복되는 파일이 존재시 이름 바꾸는 정책
* by creep (cryingj@dreamwiz.com)
* 배포 및 수정은 마음대로 하세요.
* 근데 이거 수정해서 배포해도 되는건지 몰겠네요.
* 만약 저작권문제가 있는것이면 알려주세요. 바로 삭제하겠습니다.
*/

package com.edutrack.framework.util;

import java.io.File;
import java.sql.Timestamp;

import com.oreilly.servlet.multipart.FileRenamePolicy;

public class ByTimestampFileRenamePolicy implements FileRenamePolicy {
  
  private String strCode = "";		//분리자 code명
  private String nameMode = "A";
   
  /**
   * 기본 생성자
   */
  public ByTimestampFileRenamePolicy()
  {
  	this( new String(""),"A");
  }
  
  /**
   * 생성자
   * @param v : 분리자 (String 형) 
   */
  public ByTimestampFileRenamePolicy(String v, String mode)
  {
  	if ( v.length() < 1)
  	{
  	  	this.strCode = v;
  	}else{
  	  	if(mode.equals("A"))
  			this.strCode = v + "_";
  	  	else 
  	  		this.strCode = v;
  	}
  	
  	nameMode = mode;
  }
    
  public File rename(File f) {        
   
    String fileSystemName = "";			// 실제 시스템에 저장될 이름
    String name 	= null;				// 시스템에 저장되어 있는 이름
    String ext 		= null;				// 확장자
    String body 	= null;				// 확장자 제외한 파일이름
    String tmpStr 	= "";
    Timestamp ts 	= null;				
    
    name = f.getName();
    
    int dot = name.lastIndexOf(".");
    if (dot != -1) 
    {
      ext = name.substring(dot);  // includes "."
      body = name.substring(0, dot);
    }
    else 
    {
      body = name;
      ext = "";
    }

    if(nameMode.equals("A")){    
	    try{
	    	Thread.sleep(100);
		  }catch(Exception e){
		  }
	    ts =  new java.sql.Timestamp(System.currentTimeMillis()) ;
	    tmpStr = ts.toString();
	    tmpStr =  tmpStr.substring(0, tmpStr.indexOf(" ") ).substring(5,7)
	              +tmpStr.substring( 0, tmpStr.indexOf(" ") ).substring(8)
	              + tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(0, 2)+ tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(3, 5)
	              + tmpStr.substring( (tmpStr.indexOf(" ")+1), 19 ).substring(6)+tmpStr.substring( tmpStr.length()-3, tmpStr.length() );;
	
	    // 분리 코드명 + 이전파일이름 + "_"+ 등록 년월일 시분초 + 확장자
	    fileSystemName = this.strCode + tmpStr + ext;
    }else{
    	fileSystemName = this.strCode + ext;
    }
    f = new File(f.getParent(), fileSystemName);

    return f;
  }
}

