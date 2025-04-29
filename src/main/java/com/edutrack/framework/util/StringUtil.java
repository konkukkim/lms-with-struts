/*
 * Created on 2003-04-15
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package com.edutrack.framework.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.NumberFormat;
import java.text.StringCharacterIterator;

/**
 * @author 장철웅
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class StringUtil {
    private static StringUtil stringUtilins = null;

	/**
	 * String.substring(int start, int end) 대체
	 * NullPointException 방지
	 */
	public static String substring(String src, int start, int end){
		if(src == null || "".equals(src) || start > src.length() || start > end || start < 0) return "";
		if(end > src.length()) end = src.length();

		return src.substring(start, end);
	}

	/**
	 *	파라미터 스트링이 null 이 아니고, "" 이 아니면 true, 아니면 false
	 *
	 *	@param param		검사 문자열
	 *
	 *	@return 검사결과
	 */
	public static boolean isNotNull(String param){
		if(param != null && "".equals(param) == false) return true;
		else return false;
	}

	//-----------------------------------------------------------------------------
	//	ASCII을 한글캐릭터셋으로 변환
	//-----------------------------------------------------------------------------
	public static String	ksc2asc(String str) {
		String	result				=	"";
		if (str != null && !str.equals("null") && !str.equals("")) {
			str						=	str.trim();

			try {
				result				=	new String(str.getBytes("KSC5601"),"8859_1");
			//	result				=	new String(str.getBytes("8859_1"),"KSC5601");
			}
			catch (UnsupportedEncodingException e) {

				result				=	str;
			}
		}
		else {
			result					=	"";
		}
		return	result;
	}

	public static String	asc2ksc(String str) {
		String	result				=	"";
		if (str != null && !str.equals("null") && !str.equals("")) {
			str						=	str.trim();

			try {
				result				=	new String(str.getBytes("8859_1"),"KSC5601");
			}
			catch (UnsupportedEncodingException e) {

				result				=	str;
			}
		}
		else {
			result					=	"";
		}
		return	result;
	}

	/**
	 *	스트링 치환 함수
	 *
	 *	주어진 문자열(buffer)에서 특정문자열('src')를 찾아 특정문자열('dst')로 치환
	 *
	 */
	public static String ReplaceAll(String buffer, String src, String dst){
		if(buffer == null) return null;
		if(buffer.indexOf(src) < 0) return buffer;

		int bufLen = buffer.length();
		int srcLen = src.length();
		StringBuffer result = new StringBuffer();

		int i = 0;
		int j = 0;
		for(; i < bufLen; ){
			j = buffer.indexOf(src, j);
			if(j >= 0) {
				result.append(buffer.substring(i, j));
				result.append(dst);

				j += srcLen;
				i = j;
			}else break;
		}
		result.append(buffer.substring(i));
		return result.toString();
	}

	/**
	 *	파라미터 스트링이 null or "" 이면 true, 아니면 false
	 *
	 *	@param param		검사 문자열
	 *
	 *	@return 검사결과
	 */
	public static boolean NVL(String param){
		if(param == null || "".equals(param) ) return true;
		else return false;
	}

	/**
	 * 문자열을 Form의 Input Text에 삽입할때 문자 변환
	 * @param str
	 * @return
	 */
    public static String	fn_input_text (String str) {
		if (str == null || str.equals("")) {
			return	"";
		}
		else {
			char	schr1			=	'\'';
			char	schr2			=	'\"';
			StringBuffer	sb		=	new StringBuffer(str);
			int		idx_str			=	0;
			int		edx_str			=	0;

			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr1, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&#39;").toString();
				edx_str				=	idx_str + 5;
			}

			sb						=	new StringBuffer(str);
			idx_str					=	0;
			edx_str					=	0;
			while (idx_str >= 0) {
				idx_str				=	str.indexOf(schr2, edx_str);
				if (idx_str < 0) {
					break;
				}
				str					=	sb.replace(idx_str, idx_str+1, "&quot;").toString();
				edx_str				=	idx_str + 6;
			}

			return	str;
		}
	}

    /**
     * 데이터를 디비에 넣을 시 작업을 해준다.
     * @param str
     * @return
     */
	public static String inDataConverter(String str) {
        String result = "";
		if (str != null && !str.equals("null") && !str.equals("")) {
			str						=	str.trim();

			try {
//				result				=	new String(str.getBytes("8859_1"),"KSC5601");
				result = str;
			}
			//catch (UnsupportedEncodingException e) {
			catch (Exception e) {
				result				=	str;
			}
		}
		else {
			result					=	"";
		}
		return	result;
	}

	/**
	 * 데이터를 디비에서 가져올 시 작업을 해준다.
	 * @param str
	 * @return
	 */
	public static String outDataConverter(String str) {
        String result = "";
		if (str != null && !str.equals("null") && !str.equals("")) {
			str						=	str.trim();

			try {
				result				=	new String(str.getBytes("KSC5601"),"8859_1");
				result = str;
			}
			//catch (UnsupportedEncodingException e) {
			catch (Exception e) {

				result				=	str;
			}
		}
		else {
			result					=	"";
		}
		return	result;
	}

    /**
     * null인 경우 ""를 return
     * @param value
     * @return
     */
	public static String nvl(String value) {
		return nvl(value, "");
	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String nvl(String value, String defaultValue) {
		if (value == null || value.equals(""))
			return defaultValue;
		else
			return value;
	}

	/**
	 * value가 null인 경우 defalult값을 return
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static int nvl(String value, int defaultValue) {
		if (value == null || value.equals(""))
			return defaultValue;
		else
			return Integer.parseInt(value);
	}

    public static String nvl(Object value)
    {
        return nvl(value, "");
    }

    public static String nvl(Object value, String defaultValue)
    {
        if(value == null)
            return defaultValue;
        if(value.toString().equals(""))
            return defaultValue;
        else
            return value.toString();
    }

    public static int nvl(Object value, int defaultValue)
    {
        if(value == null)
            return defaultValue;
        if(value.toString().equals(""))
            return defaultValue;
        else
            return Integer.parseInt(value.toString());
    }

	/**
	 * Number 타입인지를 체크 한다.
	 * @param paramName
	 * @return
	 */
	public static boolean isNumber(String paramName) {
		paramName = nvl(paramName);
		try {
			Long.parseLong(paramName);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

    public static String getHtmlContents(String src)
    {
        src = ReplaceAll(src, "<", "&lt;");
   		src = ReplaceAll(src, ">", "&gt;");
        src = ReplaceAll(src, "\n", "<br>");
        src = ReplaceAll(src, "&quot;", "\"");
        src = ReplaceAll(src, " ", "&nbsp;");

        return src;
    }

    public static String getHtmlText(String src)
    {
        src = ReplaceAll(src, "<", "&lt;");
   		src = ReplaceAll(src, ">", "&gt;");
        src = ReplaceAll(src, "\n", "<br>");
        src = ReplaceAll(src, "&quot;", "\"");
        src = ReplaceAll(src, " ", "&nbsp;");

        return src;
    }

    /**
     * 데이타를 구분자로 나누어 배열로 리턴
     * @param str
     * @param sepe_str
     * @return
     */
	public static String[] split(String str, String sepe_str) {
		int		index				=	0;
		String[] result				=	new String[search(str,sepe_str)+1];
		String	strCheck			=	new String(str);
		while (strCheck.length() != 0) {
			int		begin			=	strCheck.indexOf(sepe_str);
			if (begin == -1) {
				result[index]		=	strCheck;
				break;
			}
			else {
				int	end				=	begin + sepe_str.length();
				if(true) {
					result[index++]	=	strCheck.substring(0, begin);
				}
				strCheck			=	strCheck.substring(end);
				if(strCheck.length()==0 && true) {
					result[index]	=	strCheck;
					break;
				}
			}
		}
		return result;
	}

	public static int search(String strTarget, String strSearch) {
		int		result				=	0;
		String	strCheck			=	new String(strTarget);
		for(int i = 0; i < strTarget.length();) {
			int		loc				=	strCheck.indexOf(strSearch);
			if(loc == -1) {
				break;
			}
			else {
				result++;
				i					=	loc + strSearch.length();
				strCheck			=	strCheck.substring(i);
			}
		}
		return result;
	}

	/**
	 * 인자값으로 받은 Integre 만큼  문자를 자른후 나머지 문자는 .. 으로 표시한다
	 * @param str
	 * @param maxLength
	 * @return
	 */
    public static String setMaxLength(String str, int maxLength) {
        if (str == null) {
            return    "";
        }
        if ( str.length() <= maxLength ) return str;
        if ( maxLength < 3 ) return str.substring(0, 2);

        StringBuffer returnString = new StringBuffer();
        str = str.trim();

        returnString.append(str.substring(0, maxLength-1)).append("..");

        return    returnString.toString();
    }

	public static String cropByte(String str, int cut) {

        if (str == null) {
            return    "";
        }
        if ( str.length() <= cut ) return str;
        if ( cut < 3 ) return str.substring(0, 2);

		StringCharacterIterator iter = new StringCharacterIterator(str);
        int check = 0;
		int type = Character.getType(iter.last());
		if(type == Character.OTHER_SYMBOL)
		  cut --;
		else check++;

		if(check < 1){
		    //재검사
			iter.setText(str.substring(0,cut));
			type = Character.getType(iter.last());
			if(type == Character.OTHER_SYMBOL)
			  cut += 2;
		}

		//문자를 다시 잘라 리턴
	    return str.substring(0,cut)+"...";
	}
	
	 public static int realLength(String s) {
        return s.getBytes().length;
    }
	 
	 
	/**
	 * 파일 확장자를 리턴한다.
	 * @param szTemp
	 * @return
	 */
	public static String getExt(String szTemp)
	{
		if(szTemp == null) return "";

		String fname = "";
		if (szTemp.indexOf(".") != -1) {
			fname = szTemp.substring(szTemp.lastIndexOf("."));
			return fname;
		} else {
			return "";
		}
	}
	
	
	/**
	 * 천단위 콤마 찍힌 숫자를 리턴한다.
	 * @param str
	 * @return
	 */
	public static String getMoneyType(int str)
	{
		NumberFormat nf = NumberFormat.getNumberInstance();
		String r_str = nf.format(str);
		return r_str;
	}
	
	
	
	//--	게시판 도배글 방지 관련으로 추가함 [2007.11.13]		--//
	
	/**
	 * 반복되는 제목 걸러내기
	 */
	public static boolean smartSimilar(String a, String b, double minSimilarity) { 
	    return similar(a, b, minSimilarity); 
	}

	public static boolean similar(String a, String b, double minSimilarity) { 
	    if (a == b) return true; 
	    if (a.length() == b.length() && a.equals(b)) return true; 
	 
	    String longer = a.length() > b.length() ? a : b; 
	    String shorter = a.length() > b.length() ? b : a; 
	 
	    // largely diff len 
	    if ((double) shorter.length() / (double) longer.length() < minSimilarity) return false; 
	 
	    // if all fails, do editDistance() 
	    double diff = (double) editDistance(a, b) / (double) longer.length(); 
	    return 1.0d - diff >= minSimilarity; 
	} 

	private static final int[] pregeneratedCosts; 
	static { 
	    pregeneratedCosts = new int[655350]; 
	    for (int i = 0; i < pregeneratedCosts.length; i++) 
	        pregeneratedCosts[i] = i; 
	} 


	public static int editDistance(String str1, String str2) { 
	    char[] carr1 = str1.toCharArray(); 
	    char[] carr2 = str2.toCharArray(); 
	    int len1 = carr1.length; 
	    int len2 = carr2.length; 
	    if (len1 == 0) return len2; 
	    else if (len2 == 0) return len1; 
	 
	    int prevCosts[] = new int[len1 + 1]; 
	    int costs[] = new int[len1 + 1]; 
	    int placeholder[]; 
	 
	    int i1; 
	    int i2; 
	    char c; 
	    int cost; 
	 
	    System.arraycopy(pregeneratedCosts, 0, prevCosts, 0, prevCosts.length); 
	 
	    for (i2 = 1; i2 <= len2; i2++) { 
	        c = carr2[i2 - 1]; 
	        costs[0] = i2; 
	        for (i1 = 1; i1 <= len1; i1++) { 
	            cost = carr1[i1 - 1] == c ? 0 : 1; 
	            costs[i1] = Math.min(Math.min(costs[i1 - 1] + 1, 
	                    prevCosts[i1] + 1), prevCosts[i1 - 1] + cost); 
	        } 
	        placeholder = prevCosts; 
	        prevCosts = costs; 
	        costs = placeholder; 
	    } 
	    return prevCosts[len1]; 
	} 

}
