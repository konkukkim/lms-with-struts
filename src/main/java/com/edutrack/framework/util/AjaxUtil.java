/*
 * Created on 2003-04-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * @author 김청현
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class AjaxUtil {

	/**
	 * AJAX 파라미터 디코딩
	 * 2007.03.21 sangsang
	 * @param str
	 * @return
	 */
	public static String ajaxDecoding(String str) {
		String	result	=	"";
		if (str != null && !str.equals("null") && !str.equals("")) {
			str	=	str.trim();

			try {
				//result = new String(str.getBytes("ISO8859_1"),"UTF-8");
				result = URLDecoder.decode(str,"utf-8");
			}
			catch (UnsupportedEncodingException e) {
				result = str;
			}
		}
		else {
			result = "";
		}
		return	result;
	}
	
	
	/**
	 * XML 형태로 리턴 - 전체 xml 형태 조합
	 * @param data
	 * @return
	 */
	public static String makeCompleteXML(Object data) 
	{ 
		
		String validCheck="success";
		if("error".equals((String)data))
			validCheck = "error";
		
		StringBuffer strXML = new StringBuffer();
		if(data != null ){
			strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
			strXML.append("<dataSet>");
			strXML.append("<validCheck>");
			strXML.append("<result><![CDATA["+validCheck+"]]></result>");
			strXML.append("</validCheck>");			
			strXML.append("<entry>");
			strXML.append("<data><![CDATA["+data+"]]></data>");
			strXML.append("</entry>");
			strXML.append("</dataSet>");
			System.out.println(strXML.toString());
		}
		return strXML.toString(); 
	}
	
	/**
	 * XML 형태로 리턴 - 데이타부분만 조합
	 * @param data
	 * @param type
	 * @return
	 */
	public static String makeXML(Object data) 
	{ 
		
		String validCheck="success";
		if("error".equals((String)data))
			validCheck = "error";
		
		StringBuffer strXML = new StringBuffer();
		if(data != null ){
			strXML.append("<?xml version=\"1.0\" encoding=\"euc-kr\" ?>");
			strXML.append("<dataSet>");
			strXML.append("<validCheck>");
			strXML.append("<result><![CDATA["+validCheck+"]]></result>");
			strXML.append("</validCheck>");					
			strXML.append(data);
			strXML.append("</dataSet>");
			System.out.println(strXML.toString());
		}
		return strXML.toString(); 
	}
}
