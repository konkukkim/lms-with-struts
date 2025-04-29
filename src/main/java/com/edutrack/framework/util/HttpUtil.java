/*
 * Created on 2004. 7. 12.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLDecoder;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ssari.servlet.MultipartRequest;

/*********************************************************
 * 프로그램 : HttpUtil.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 7. 12.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class HttpUtil {

    /**
     * 
     */
    public HttpUtil() {
        super();
        // TODO Auto-generated constructor stub
    }
    
	public static Map parseSingleQueryString( String string ) {
		Map parsed = new HashMap();
		
		StringTokenizer nameAndValue = new StringTokenizer( string, "&" );
		while( nameAndValue.hasMoreTokens() ) {
			StringTokenizer st = new StringTokenizer( nameAndValue.nextToken(), "=" );
			String name = st.nextToken();
			String value = "";
			if( st.hasMoreTokens() ) {
				value = st.nextToken();
				value = URLDecoder.decode(value);
			}
			
			parsed.put( name, value ); 
		}
		
		return parsed;
	}
	
	public static String getLocalIp() {
		String localIp = "127.0.0.1";
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			localIp = localHost.getHostAddress();
		} catch (UnknownHostException e) {		
		}
		return localIp;
	}
	
	public static int sendHttpRequest( String urlString ) throws IOException {
		HttpURLConnection conn = null;
		try {
			URL url = new URL( urlString );
			conn = (HttpURLConnection)url.openConnection();
			return conn.getResponseCode();
		}
		finally {
			if( conn != null ) conn.disconnect();
		}
	}

	public static String getLinkUrl(MultipartRequest req) {
		
		Enumeration e = req.getParameterNames();
	
		StringBuffer linkUrl = new StringBuffer();
		linkUrl.append("&");
		
		while (e.hasMoreElements()) {
			String name = (String) e.nextElement();
			if (!isEmpty(name, req)) {
				linkUrl.append(name);
				linkUrl.append("=");
				linkUrl.append(req.getParameter(name));
				if( e.hasMoreElements() )
					linkUrl.append("&");
			}
		}
		return linkUrl.toString();
	}

	private static boolean isEmpty(String name, MultipartRequest req) {
		String paramValue = req.getParameter(name);
		return (paramValue == null) || (paramValue.equals(""));
	}

	public static String getHtmlPage( String urlPath ) {
		String html = null;
		InputStream in = null;
		ByteArrayOutputStream out = null;
		try {
			URL url = new URL( urlPath );
			in = url.openStream();
			out = new ByteArrayOutputStream();
			
			int len;
			byte [] buf = new byte [1024];
			while( (len=in.read(buf)) != -1 ) out.write( buf, 0, len );
			out.flush();
			html = out.toString();
		}
		catch( Exception ignored ) {}
		finally {
			if( in != null ) try { in.close(); } catch( Exception ignored ) {}
			if( out != null ) try { out.close(); } catch( Exception ignored ) {}
		}
		return html;
	}
	
	/**
	 * Method getCookie.
	 * @param request
	 * @param cookieName
	 * @return String
	 * @throws Exception
	 */
	 public static String getCookie(HttpServletRequest request, String cookieName) throws Exception {
    	Cookie [] cookies = request.getCookies();
    	if (cookies==null) return "";
    	String value = "";
    	for(int i=0;i<cookies.length;i++) {
    		if(cookieName.equals(cookies[i].getName())) {
    			value = java.net.URLDecoder.decode(cookies[i].getValue(),"MS949");
    			break;
    		}
    	}
    	return value;
    }
	 
	 /**
		 * Method setCookie.
		 * @param response
		 * @param name
		 * @param value
		 * @param iMinute
		 * @throws UnsupportedEncodingException
		 */
		public static void setCookie(HttpServletResponse response,
	                                 String name,
	                                 String value,
	                                 int iMinute)
	                                 throws java.io.UnsupportedEncodingException {
	    	value = java.net.URLEncoder.encode(value, "8859_1");
	    	Cookie cookie = new Cookie(name, value);
	    	cookie.setMaxAge(60 * iMinute);
	    	cookie.setPath("/");
	    	response.addCookie(cookie);
	    }

	    /**
		 * Method setCookie.
		 * @param response
		 * @param name
		 * @param value
		 * @throws UnsupportedEncodingException
		 */
		public static void setCookie(HttpServletResponse response,
	                                 String name,
	                                 String value)
	                                 throws java.io.UnsupportedEncodingException {
	        setCookie(response, name, value, 60*24*15);
	    }
	

}
