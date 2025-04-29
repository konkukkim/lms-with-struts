package com.edutrack.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.RowSet;

import org.apache.regexp.RE;

import com.edutrack.common.dao.CommonDAO;
import com.edutrack.common.dto.CodeDTO;
import com.edutrack.common.dto.CodeParam;
import com.edutrack.common.dto.DateParam;
import com.edutrack.common.dto.EditorParam;
import com.edutrack.community.dao.CommBbsInfoDAO;
import com.edutrack.community.dao.CommInfoDAO;
import com.edutrack.courseexam.dao.ExamBankDAO;
import com.edutrack.coursereport.dao.ReportBankDAO;
import com.edutrack.curriresearch.dao.ResearchBankDAO;
import com.edutrack.currisub.dao.CurriSubDAO;
import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.logging.Log;
import com.edutrack.framework.logging.LogFactory;
import com.edutrack.framework.util.DateTimeUtil;
import com.edutrack.framework.util.MailUtil;
import com.edutrack.framework.util.StringUtil;
import com.edutrack.progauthor.dao.ProgAuthorDAO;
import com.edutrack.progauthor.dao.ProgMenuDAO;
import com.edutrack.user.dao.UserDAO;
import com.edutrack.user.dto.UsersDTO;
import com.oroinc.net.ftp.FTP;
import com.oroinc.net.ftp.FTPClient;
import com.oroinc.net.ftp.FTPReply;

/*********************************************************
 * 프로그램 : CommonUtil.java
 * 모 듈 명  : 프로젝트내에서 사용하는 공통 유틸 정의
 * 설    명   :
 * 테 이 블  :
 * 작 성 자  : chorongjang
 * 작 성 일  : 2004. 7. 15.
 * 수 정 일  :
 * 수정사항 :
 *********************************************************/
public class CommonUtil {
    private static Configuration config = ConfigurationFactory.getInstance().getConfiguration();
    public static final String SERVERTYPE = config.getString("framework.system.server_type");
    public static final String SERVERURL = config.getString("framework.system.server_url");
    public static final String DBTYPE = config.getString("framework.persist.defaultPersistBroker");
    public static final int CONCHECKTIME = config.getInt("framework.system.connect_check_time");
    public static final String DELI = "@$";
    private static final String[] SYSTEMCODELIST = new String[]{config.getString("framework.system.code1",""),config.getString("framework.system.code2",""),config.getString("framework.system.code3","")};
    private static CommonUtil commonUtilIns = null;
	public static RE REGION[] = new RE[]{new RE("^1\\d{2}"),new RE("^40\\d"),new RE("^4[1-9]\\d"),new RE("^6[01]\\d"),
            new RE("^68\\d"),new RE("^6[2-7]\\d"),new RE("^7(0\\d|11)"),new RE("^7([2-9]\\d|1[2-9])"),
			new RE("^50\\d"),new RE("^5[1-5]\\d"),new RE("^5[6-9]\\d"),new RE("^30\\d"),new RE("^3[1-5]\\d"),
			new RE("^3[6-9]\\d"),new RE("^2\\d{2}"),new RE("^69\\d")};

    public static final String EDITORDOMAIN = config.getString("framework.webeditor.license_domain");
    public static final String EDITORKEY = config.getString("framework.webeditor.license_key");
    public static final String EDITORNUMBER = config.getString("framework.webeditor.license_number");
    public static final String EDITORFILEURL = config.getString("framework.webeditor.savefile_url");
    public static final String EDITORRELATIVEURL = config.getString("framework.webeditor.relative_url");
    public static final String AJAX_VALIDKEY = config.getString("framework.ajax.validkey");
    protected static String defaultPagePath = config.getString("framework.system.jsp_path");
	/**
	 * 이니시스 결재 시스템  플러그인 설치 경로
	 * Default Install Path  C:/INIpay41_JAVA
	 */
	public static String InipayHome = config.getString("framework.InipayHome");

	/**
	 * DRM Server Domain
	 */
	public static String DigCapDRMServerDoamin = config.getString("framework.DigCapDRMServerDoamin");
	/**
	 * 통합검색관련 다센21 검색엔진 DBID & Server Info
	 */
	public static String WoorinDasenCurriDbId = config.getString("framework.WoorinDasenCurriDbId");
	public static String WoorinDasenEbookDbId = config.getString("framework.WoorinDasenEbookDbId");
	public static String WoorinDasenServerIp = config.getString("framework.WoorinDasenServerIp");
	public static String WoorinDasenServerPort = config.getString("framework.WoorinDasenServerPort");
	public static String WoorinDasenSearchPort = config.getString("framework.WoorinDasenSearchPort");
	public static String WoorinDasenSearchTimeout = config.getString("framework.WoorinDasenSearchTimeout");

	/**
	 * VOD 서버로 파일을 업로드 하기 위해서....
	 */
	public Log log = LogFactory.getLog(this.getClass());
	private static final String sServer = "61.107.122.68"; //서버 아이피
	private static final int iPort = 21;
	private static final String sId = "junnodae"; //사용자 아이디
	private static final String sPassword = "cyber"; //비밀번호
	private static final String sUpDir = config.getString("framework.fileupload.abspath");	//웹서버에 파일이 업로드되는 위치

	//-- 커뮤니티
	public static String getCommCategory(CodeParam param){
		CommInfoDAO commDao = new CommInfoDAO();
	   	ArrayList codeList = null;

		try{
			codeList = commDao.getMakeCateInfo(param.getSystemcode());
	   	}catch(Exception e){

		}
	   	return getCodeBox(param, codeList);
	}


	public static ArrayList getCommBbsList(String systemcode, String commid){
		ArrayList bbsList = null;
	   	CommBbsInfoDAO commDao = new CommBbsInfoDAO();

		try{
			bbsList = commDao.getCommBbsInfoList(systemcode, commid);
	   	}catch(Exception e){

	   	}
	   	return bbsList;
	}


	public static String getCodeSoName(String systemcode, String daecode, String socode) {
		CommonDAO common = new CommonDAO();
		String soName = null;
		try {
			soName = common.getSoCodeName(systemcode, daecode, socode);
		}
		catch (Exception e) {}
		return soName;
	}


    /**
     *
     */
    public CommonUtil() {
        super();
        // TODO Auto-generated constructor stub
    }

    public static CommonUtil getInstance(){
	    if(commonUtilIns == null) {
	        commonUtilIns = new CommonUtil();
	     }

	     return commonUtilIns;
    }

    public static String getSystemCode(String url){
    	String systemCode = "";
    	for(int i =0; i < SYSTEMCODELIST.length; i++){
      	   if(SYSTEMCODELIST[i].indexOf(url) > -1) {
      	   	systemCode = ""+(i+1);
      	   	break;
      	   }
    	}
//    	return systemCode;
    	return "1"; //-- 무조건 1로 만들기
    }

    /**
     *  DB현재 날짜를 가져온다.
     * @return
     */
    public static String getCurrentDate(){
        String curDate = "";
        CommonDAO cd = new CommonDAO();
        try{
        	curDate = cd.getCurrentDate();
        }catch(Exception e){
            e.printStackTrace();
        }

        return curDate;
    }

    /**
     * 달력 입력 창을 생성해 준다.
     * @param param
     * @param dateobj
     * @return
     */
    public static String getCalendar(DateParam param, DateSetter dateobj){
	    StringBuffer out = new StringBuffer();
	    String curyear1= "", curmonth1="",curyear2= "", curmonth2="";
	    String strReadonly = " readonly ";
    	if (dateobj.getYear1().equals("")){
    		curyear1=DateTimeUtil.getYear();
		}else{
			curyear1=dateobj.getYear1();
		}
	    if (dateobj.getMonth1().equals("")){
            curmonth1=DateTimeUtil.getMonth();
		}else{
			curmonth1=dateobj.getMonth1();
		}

	    if(param.getReadonly().equals("false"))
	    	strReadonly = "";
		out.append("<input type=text name=\""+param.getYear()+"1\" size=4 value=\""+dateobj.getYear1()+"\" "+strReadonly+" "+ param.getCssNm()+">년 ");
		if (param.getDateType() < 3){
			out.append("<input type=text name=\""+param.getMonth()+"1\" size=2 value=\""+dateobj.getMonth1()+"\" "+strReadonly+" "+ param.getCssNm()+">월 ");
		}else{
		    out.append("<input type=hidden name=\""+param.getMonth()+"1\" size=2 value=\""+dateobj.getMonth1()+"\" "+strReadonly+">");
		}
		if (param.getDateType() < 2){
			out.append("<input type=text name=\""+param.getDay()+"1\" size=2 value=\""+dateobj.getDay1()+"\" "+strReadonly+" "+ param.getCssNm()+">일 ");
		}else{
			out.append("<input type=hidden name=\""+param.getDay()+"1\" size=2 value=\""+dateobj.getDay1()+"\" "+strReadonly+">");
		}
	    if (param.getDateType() < 1){
	    	out.append("<input type=text name=\""+param.getHour()+"1\" size=2 value=\""+dateobj.getHour1()+"\" "+ param.getCssNm()+">시 ");
	    	out.append("<input type=text name=\""+param.getMinute()+"1\" size=2 value=\""+dateobj.getMin1()+"\" "+ param.getCssNm()+">분 ");
	    }else{
	    	out.append("<input type=hidden name=\""+param.getHour()+"1\" size=2 value=\""+dateobj.getHour1()+"\">");
	    	out.append("<input type=hidden name=\""+param.getMinute()+"1\" size=2 value=\""+dateobj.getMin1()+"\">");
	    }
		out.append("<input type=hidden name=\""+param.getDate()+"1\" value=\""+dateobj.getYear1()+dateobj.getMonth1()+dateobj.getDay1()+dateobj.getHour1()+dateobj.getMin1()+dateobj.getSec1()+"\">");
	    out.append("<input type=hidden name=\""+param.getDate()+"2\" value=\""+dateobj.getYear2()+dateobj.getMonth2()+dateobj.getDay2()+dateobj.getHour2()+dateobj.getMin2()+dateobj.getSec2()+"\">");
	    //out.append(" [<a href=\"javascript:date_select('"+param.getForm()+"','"+param.getYear()+"','"+param.getMonth()+"','"+param.getDay()+"','"+param.getHour()+"','"+param.getMinute()+"',"+curyear1+","+curmonth1+",'"+param.getDate()+"','1');\">달력</a>] \n");
	    out.append("<input type=button value=\"\"  class=\"cal_btn\" align=absmiddle onClick=\"new CalendarFrame.Calendar(this,'"+param.getYear()+"','"+param.getMonth()+"','"+param.getDay()+"','"+param.getHour()+"','"+param.getMinute()+"','"+param.getDate()+"','1',"+param.getPosX()+","+param.getPosY()+"); \">");
	    //out.append("<input type=image src='/img/1/button/dateselect.gif' value='' align=absmiddle onClick=\"new CalendarFrame.Calendar(this,'"+param.getYear()+"','"+param.getMonth()+"','"+param.getDay()+"','"+param.getHour()+"','"+param.getMinute()+"','"+param.getDate()+"','1',"+param.getPosX()+","+param.getPosY()+"); \">");

		if (param.getCount() > 1){
			out.append(" ~ ");
		    if(dateobj.getYear2().equals("")){
		    	curyear2=DateTimeUtil.getYear();
		    }else{
		    	curyear2=dateobj.getYear2();
		    }
		    if (dateobj.getMonth2().equals("")){
		    	curmonth2=DateTimeUtil.getMonth();
		    }else{
		    	curmonth2=dateobj.getMonth2();
		    }
			out.append("<input type=text name=\""+param.getYear()+"2\" size=4 value=\""+dateobj.getYear2()+"\" "+strReadonly+"  "+ param.getCssNm()+">년");
			if (param.getDateType() < 3){
			     out.append("<input type=text name=\""+param.getMonth()+"2\" size=2 value=\""+dateobj.getMonth2()+"\" "+strReadonly+"  "+ param.getCssNm()+">월");
			}else{
			    out.append("<input type=hidden name=\""+param.getMonth()+"2\" size=2 value=\""+dateobj.getMonth2()+"\" "+strReadonly+">");
			}
			if (param.getDateType() < 2){
				out.append("<input type=text name=\""+param.getDay()+"2\" size=2 value=\""+dateobj.getDay2()+"\" "+strReadonly+"  "+ param.getCssNm()+">일 ");
			}else{
				out.append("<input type=hidden name=\""+param.getDay()+"2\" size=2 value=\""+dateobj.getDay2()+"\" "+strReadonly+">");
			}
		    if (param.getDateType() < 1){
		    	out.append("<input type=text name=\""+param.getHour()+"2\" size=2 value=\""+dateobj.getHour2()+"\"  "+ param.getCssNm()+">시 ");
		    	out.append("<input type=text name=\""+param.getMinute()+"2\" size=2 value=\""+dateobj.getMin2()+"\"  "+ param.getCssNm()+">분 ");
		    }else{
		    	out.append("<input type=hidden name=\""+param.getHour()+"2\" size=2 value=\""+dateobj.getHour2()+"\">");
		    	out.append("<input type=hidden name=\""+param.getMinute()+"2\" size=2 value=\""+dateobj.getMin2()+"\">");
		    }
		    out.append("<input type=button value=\"\"  class=\"cal_btn\" onClick=\"new CalendarFrame.Calendar(this,'"+param.getYear()+"','"+param.getMonth()+"','"+param.getDay()+"','"+param.getHour()+"','"+param.getMinute()+"','"+param.getDate()+"','2',"+param.getPosX()+","+param.getPosY()+")\">");
		    //out.append("[<a href=\"javascript:date_select('"+param.getForm()+"','"+param.getYear()+"','"+param.getMonth()+"','"+param.getDay()+"','"+param.getHour()+"','"+param.getMinute()+"',"+curyear2+","+curmonth2+",'"+param.getDate()+"','2');\">달력</a>] \n");
		}

		return StringUtil.outDataConverter(out.toString());
    }

    /**
     * 코드 박스를 생성해 준다.
     * @param param
     * @param list
     * @return
     */
    public static String getCodeBox(CodeParam param, ArrayList list){
    	StringBuffer out = new StringBuffer();
    	CodeDTO code = null;
	    if (param.getType().equals("select")){
			out.append("<select name=\""+param.getName()+"\" ");
			if (param.getEvent() != null && !param.getEvent().equals("")) out.append("onChange=\""+param.getEvent()+"\" ");
			    out.append(" > \n ");
				if (param.getFirst() != null && !param.getFirst().equals("")) out.append("<option value=\"\">"+param.getFirst()+"</option> \n");
				if (list != null && list.size() > 0)
				    for(int i = 0; i < list.size(); i++){
				    	code = (CodeDTO)list.get(i);
				    	out.append("<option value=\""+code.getCode()+"\" ");
				    	if (code.getCode().equals(param.getSelected())) out.append("selected");
				    	out.append(" >"+code.getName()+"</option>\n");
				    }
			out.append("</select>\n");
	    }else if(param.getType().equals("check")){
				if (list != null && list.size() > 0)
					for(int i = 0; i < list.size(); i++){
				    	code = (CodeDTO)list.get(i);
						out.append("<INPUT type=checkbox name=\""+param.getName()+"\" class=no value=\""+code.getCode()+"\"");
						if (param.getSelected().indexOf(code.getCode()) > -1) out.append(" checked ");
						if (param.getEvent() != null && !param.getEvent().equals("")) out.append(" onclick=\""+param.getEvent()+"\" ");
						out.append(">"+code.getName()+"&nbsp; \n");
					}
	    }else if(param.getType().equals("radio")) {
				if (list != null && list.size() > 0)
					for(int i = 0; i < list.size(); i++){
						code = (CodeDTO)list.get(i);
						out.append("<INPUT type=radio name=\""+param.getName()+"\" class=no value=\""+code.getCode()+"\" ");
						if (param.getSelected().indexOf(code.getCode()) > -1) out.append(" checked ");
						if (param.getEvent() != null && !param.getEvent().equals("")) out.append(" onclick=\""+param.getEvent()+"\" ");
						out.append(">"+code.getName()+"&nbsp;&nbsp;\n");
					}
	    }

	    return out.toString();
   }

    public static String getEditorScript(EditorParam param){
	   	StringBuffer out = new StringBuffer();

	   	out.append("<!-- WEAS 삽입 스크립트1(시작) --> \n");
	   	out.append("<VentureBrain width=\""+param.getWidth()+"\" height=\""+param.getHeight()+"\" id=VBN_id_WEAS> \n");
	   	out.append("<!--(주의) VBN_LICENSE_로 시작하는 태그값을 변경하시면 KEY를 재발급받으셔야 합니다.--> \n");
	   	out.append("<param name=VBN_LICENSE_DOMAIN value=\""+EDITORDOMAIN+"\"> \n");
	   	out.append("<param name=VBN_LICENSE_KEY value=\""+EDITORKEY+"\"> \n");
	   	out.append("<param name=VBN_LICENSE_NUMBER value=\""+EDITORNUMBER+"\"> \n");
	   	out.append("<param name=VBN_OPTION_version value=\"Standard Multi\"> \n");
	   	out.append("<param name=VBN_OPTION_baseSaveFileURL value=\""+EDITORFILEURL+"\"> \n");
	   	out.append("<param name=VBN_OPTION_relativeBaseURL value=\"http://"+EDITORRELATIVEURL+"\"> \n");
	   	out.append("<param name=VBN_OPTION_scriptIncludeFlag value=\"false\"> \n");
	   	out.append("<!-- 글 전송시 본문 내용 이외에 CSS, 멀티페이지 스크립트 포함 여부 설정 --> \n");
	   	out.append("<param name=VBN_OPTION_showFlag value=\""+param.getShowFlag()+"\">   <!--위스 구동 유무  이 값을 false로 설정하면 위스가 나타나지 않음--> \n");
	   	out.append("<param name=VBN_OPTION_linkPopupFlag value=\""+param.getLinkPopupFlag()+"\"> <!--링크 자동팝업 설정하기 target _blank--> \n");
	   	out.append("<param name=VBN_OPTION_fileNumberingFlag value=\""+param.getFileNumberingFlag()+"\"> \n");
	   	out.append("<param name=VBN_OPTION_fileLocalPathSendFlag value=\""+param.getFileLocalPathSendFlag()+"\"> \n");
	   	out.append("<param name=VBN_OPTION_unicodeFileFlag value=\"true\"><!-- 파일명이 한글인 파일 삽입안하기 --> \n");
	   	out.append("<param name=VBN_OPTION_menualFlag value=\""+param.getMenualFlag()+"\"> <!-- 리스트에서 본문 미리보기  --> \n");
	   	out.append("<param name=VBN_OPTION_addInfoSendFlag value=\""+param.getAddInfoSendFlag()+"\"> \n");
	   	out.append("<param name=VBN_OPTION_maxPreviewChars value="+param.getMaxPreviewChars()+">  \n");
	   	out.append("<!-- 본문내용의 앞 부분을 VBN_FORM_Preview란 이름의 폼 변수로 사용자 서버로 부가 전송함 --> \n");
	   	out.append("<param name=VBN_OPTION_toolBarHidden value=\""+param.getToolBarHidden()+"\">   <!--툴바 아이콘 숨기기 설정 --> \n");
	   	out.append("<param name= VBN_OPTION_maxImageSize value="+param.getMaxImageSize()+">  <!-- 이미지 삽입 용량 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_maxImageCount value="+param.getMaxImageCount()+">  <!-- 이미지 삽입 갯수 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_maxVideoCount value="+param.getMaxVideoCount()+"> <!-- 비디오 삽입 갯수 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_maxAudioCount value="+param.getMaxAudioCount()+"> <!-- 오디오 삽입 갯수 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_maxFlashCount value="+param.getMaxFlashCount()+"> <!-- 플래시 삽입 갯수 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_maxFileCount value="+param.getMaxFileCount()+"> <!-- 총 삽입 갯수 제한 --> \n");
	   	out.append("<param name=VBN_OPTION_countAllFileAtModify value=\""+param.getCountAllFileAtModify()+"\">	<!-- 수정시 기존파일 포함 카운트하기 --> \n");
	   	out.append("<param name=VBN_OPTION_lineHeight value=\""+param.getLineHeight()+"%\"><!-- 줄간격 설정하기 default=2 , 2~5--> \n");
	   	out.append("<param name=VBN_OPTION_scrollbarFlag value=\""+param.getScrollbarFlag()+"\"> <!-- 지정 사이트 보다 클 경우 가로스크롤 생성 --> \n");
	   	out.append("<param name=VBN_OPTION_readWidth value=\""+param.getReadWidth()+"\"> <!--  지정 가로 폭 --> \n");
	   	out.append("<param name=VBN_OPTION_replyMode value=\""+param.getReplyMode()+"\"><!-- 답변시 원글 보이기 답변시에만 true로 설정할것--> \n");
	   	out.append("<param name=VBN_OPTION_replyHeader value=\""+param.getReplyHeader()+"\"> <!-- 답변시 헤더 설정하기 --> \n");
	   	out.append("<param name=VBN_OPTION_emailMode value=\""+param.getEmailMode()+"\"> <!-- 답변시 헤더 설정하기 --> \n");
	   	out.append("<param name=VBN_OPTION_LIF value=\"false\"><!-- 위스 로고 안보이게 하기 --> \n");
	   	out.append("<param name=VBN_OPTION_scriptIncludeURL value = \"http://"+EDITORRELATIVEURL+"/VBN_WEAS/weas/js/VBN_WEAS.js\"> \n");
	   	out.append("<!-- VBN_WEAS.js URL 설정하기 --> \n");
	   	out.append("</VentureBrain> \n");
	   	out.append("<script language=\"javascript\" id=VBN_JSID_init src=\"http://"+EDITORRELATIVEURL+"/VBN_WEAS/weas/js/VBN_WEAS_000.js\"></script> \n");
	   	out.append("<!-- WEAS 삽입 스크립트1(끝) --> \n");

        return out.toString();
   }

   /**
    * 기본 메일 폼을 가지고 메일을 전송해준다.
    * @param subject
    * @param addr
    * @param username
    * @param msg
    */
   public static void defaultMailSend(String subject, String addr, String username,String msg){
	StringBuffer sb = new StringBuffer();
	sb.append("<html>");
	sb.append("<head>");
	sb.append("<title>Mediopia</title>");
	sb.append("</head>");
	sb.append("<body>");
	sb.append("<TABLE id=\"Table1\" cellSpacing=\"1\" cellPadding=\"1\" width=\"300\" border=\"1\">");
	sb.append("<TR>");
	sb.append("<TD style=\"HEIGHT: 60px;BACKGROUND-COLOR: #ccffcc\">");
	sb.append("<P align=\"center\"><FONT face=\"굴림\"><STRONG>"+subject+"</STRONG> </FONT>");
	sb.append("</P>");
	sb.append("</TD>");
	sb.append("</TR>");
	sb.append("<TR>");
	sb.append("<TD style=\"HEIGHT: 47px\"><FONT face=\"굴림\">"+msg+"</FONT></TD>");
	sb.append("</TR>");
	sb.append("</TABLE>");
	sb.append("</body>");
	sb.append("</html>");

	MailUtil.sendDirectMail(addr, username, subject, sb.toString());
  }
   /**
    * 사용자 타입별 리스트 가져오기
    * @param param
    * @param userType
    * @return
    */
   public static String getUserList(CodeParam param, String userType){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getUserList(param.getSystemcode(),userType);
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }

   /**
    * 소코드 리스트를 가져온다.
    * @param param
    * @param daecode
    * @return
    */
   public static String getSoCode(CodeParam param, String daecode){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getSoCode(param.getSystemcode(),daecode);
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }
   /**
    * 지정 소코드의 값(so_name)을 가져온다.
    * @param param
    * @param daecode
    * @param socode
    * @return
    */
   public static String getSoName(String systemcode, String daecode, String socode){
   	CommonDAO common = new CommonDAO();
   	String soName = "";
   	try{
   		soName = common.getSoName(systemcode,daecode,socode);
   	}catch(Exception e){
   	}

   	return soName;
   }

   /**
    * 소속코드 대코드를 가져온다.
    * @param param
    * @return
    */
   public static String getDeptDaeCode(CodeParam param){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getDeptDaeCode(param.getSystemcode());
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }

   /**
    * 소속코드 대코드를 가져온다.
    * @author HerSunJa
    * @since  2005.07.29
    * @param param
    * @param  flag  : (String) 2 (소속기관)
    * @return
    */
   public static String getDeptDaeCode(CodeParam param, String flag){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getDeptDaeCode(param.getSystemcode(), flag );
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }


   /**
    * 소속코드 소코드를 가져온다.
    * @param param
    * @param daecode
    * @return
    */
   public static String getDeptSoCode(CodeParam param, String daecode){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getDeptSoCode(param.getSystemcode(),daecode);
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }

   /**
    * 개설과정에 포함된 과목의 리스트를 가져온다.
    * @param param
    * @param curriCode
    * @param curriYear
    * @param curriTerm
    * @return
    */
   public static String getCurriCourse(CodeParam param, String curriCode, int curriYear, int curriTerm, String profId){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getCurriCourse(param.getSystemcode(),curriCode,curriYear,curriTerm,profId);
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }
   /**
    * 현재 진행중인 개설과정 리스트를 가져온다. enroll_start ~ enroll_end or enroll_start ~ service_end
    * @param param
    * @param CateCode
    * @param Property1
    * @return
    */
   public static String getCurrentCurriList(CodeParam param, String CateCode, String Property1){
   	CommonDAO common = new CommonDAO();
   	ArrayList codeList = null;
   	try{
   		codeList = common.getCurrentCurriList(param.getSystemcode(),CateCode,Property1);
   	}catch(Exception e){
   	}

   	return getCodeBox(param, codeList);
   }

   /**
    * 현재날짜를 나타내는 디비 쿼리 리턴
    * @return String
    */
   public static String getDbCurrentDate(){
	String retVal = "CAST(DATE_FORMAT(NOW(),'%Y%m%d%H%i%s') AS CHAR)";
   	return retVal;
   }
   


   /**
    * 날짜의 형태를 쿼리로 변형
    * @param field
    * @param mode
    * @return
    */
   public static String getDbDateFormat(String Field){
   	String retVal = getDbDateFormat(Field,"1");
   	return retVal;
   }
   public static String getDbDateFormat(String Field, String Mode){
   	String retVal = "";
   	if(Mode.equals("1")) {
   		//retVal = "SUBSTRING("+Field+",1,4)||'.'||SUBSTRING("+Field+",5,2)||'.'||SUBSTRING("+Field+",7,2)";
   		retVal = "getDateText("+Field+", null)";
   	} else if (Mode.equals("2")) {
   		//retVal = "SUBSTRING("+Field+",1,4)||'.'||SUBSTRING("+Field+",5,2)||'.'||SUBSTRING("+Field+",7,2)||' '||SUBSTRING("+Field+",9,2)||':'||SUBSTRING("+Field+",11,2)||':'||SUBSTRING("+Field+",13,2)";
   		//retVal = "concat(getDateText(substring("+Field+",1,8), null),' ',getDateText(substring("+Field+",9,6), null))";
   		//retVal = "concat(getDateText(substring("+Field+",1,8), null),' ',substring("+Field+",9,2),':',substring("+Field+",11,2),':',substring("+Field+",13,2))";
   		retVal = "concat(substring("+Field+",1,4),'.',substring("+Field+",5,2),'.',substring("+Field+",7,2), null),' ',substring("+Field+",9,2),':',substring("+Field+",11,2),':',substring("+Field+",13,2))";
   	}
   	return retVal;
   }

   /**
    * 강좌명을 가져온다.
    * @param systemcode
    * @param courseid
    * @return
    */
   public static String getCourseName(String systemcode, String courseid){
     String courseName = "";
     CommonDAO commonDao = new CommonDAO();
     courseName = commonDao.getCourseName(systemcode, courseid);

     return courseName;
   }

   public static String getCurriSubName(String systemcode, String curriCode, int curriYear, int curriTerm){
	     String curriSubName = "";
	     CommonDAO commonDao = new CommonDAO();
	     curriSubName = commonDao.getCurriSubName(systemcode, curriCode, curriYear, curriTerm);

	     return curriSubName;
   }

   /**
    * 시험문제 타입명을 가져온다.
    * @param type
    * @return
    */
   public static String getContentsTypeName(String type){
   	   String contentsType = "";
	   if(type.equals("J")) contentsType	=	"서술형";
	   else if(type.equals("K")) contentsType	=	"선택형";
	   else if(type.equals("D")) contentsType	=	"단답형";
	   else if(type.equals("S")) contentsType	=	"OX형";
	   else if(type.equals("F")) contentsType	=	"파일형";

	   return  StringUtil.outDataConverter(contentsType);
   }

   /**
    *  사용자명을 가져온다.
    * @param systemcode
    * @param courseid
    * @return
    */
   public static String getUserName(String systemcode, String userid) {
       UserDAO userDao = new UserDAO();
       UsersDTO userDto = null;
       String userName = "";
       try{
           userDto = userDao.getUserInfo(systemcode,userid);

           if( userDto == null) return "";

           userName = userDto.getUserName();
       }catch(Exception e){}

       return  userName;
   }
   /**
    * 사용자의 성별을 반환한다 1 : 남자 , 2: 여자
    * @param systemcode
    * @param userid
    * @return
    */
   public static String getUserSex(String systemcode, String userid) {
    UserDAO userDao = new UserDAO();
    UsersDTO userDto = null;
    String sexType = "1";
    try{
        userDto = userDao.getUserInfo(systemcode,userid);

        if( userDto == null) return "";

        sexType = userDto.getSexType();
    }catch(Exception e){}

    return  sexType;
   }
   /**
    * 사용자의 이메일주소를 반환한다.
    * @param systemcode
    * @param userid
    * @return
    */
   public static String getUserEmail(String systemcode, String userid) {
    UserDAO userDao = new UserDAO();
    UsersDTO userDto = null;
    String sexType = "1";
    try{
        userDto = userDao.getUserInfo(systemcode,userid);

        if( userDto == null) return "";

        sexType = userDto.getEmail();
    }catch(Exception e){}

    return  sexType;
   }
   /**
    * 사용자의 나이를 반환 한다.
    * @param systemcode
    * @param userid
    * @return
    */
   public static String getUserAge(String systemcode, String userid) {
    UserDAO userDao = new UserDAO();
    String age = "";
    try{
    	age = userDao.getUserAge(systemcode,userid);
    }catch(Exception e){}

    return  age;
   }

   public static String getBankInfoList(CodeParam param, String systemcode, String courseid){
	   ExamBankDAO bankDao = new ExamBankDAO();

    ArrayList codeList = null;
    try{
    //	   codeList = bankDao.getExamBankInfoCodeList(systemcode, courseid);
    }catch(Exception e){}

    return getCodeBox(param, codeList);
  }

   public static String getReportBankInfoList(CodeParam param, String systemcode, String courseid){
   	ReportBankDAO bankDao = new ReportBankDAO();

	ArrayList codeList = null;
	try{
	//	codeList = bankDao.getReportBankInfoCodeList(systemcode, courseid);
	}catch(Exception e){}

	return getCodeBox(param, codeList);
   }

   public static String getResearchBankInfoList(CodeParam param, String systemcode,String userid, String share){
	   ResearchBankDAO bankDao = new ResearchBankDAO();

	 ArrayList codeList = null;
	 try{
	 //	   codeList = bankDao.getResearchBankInfoCodeList(systemcode,userid, share);
	 }catch(Exception e){}

	 return getCodeBox(param, codeList);
}

  public static String getRegionCode(String postcode){
  	int regionCode = 0;
    for(int i =0; i < REGION.length;i++){
    	if(REGION[i].match(postcode)) {
    		regionCode =  i+10;
    		break;
    	}
    }

    return ""+regionCode;
  }

//  public static  String getWebSiteTitle(String sitetype){
//		String title = "";
//		if(sitetype.equals("10")) title = "관련사이트";
//		else if(sitetype.equals("20")) title = "What's Up";
//
//		return title;
//  }

  public static int getCompleteStudentCnt(String systemCode, String curriCode,int curriYear, int curriTerm){
    int  cnt = 0;
    CommonDAO cd = new CommonDAO();
    try{
    	cnt = cd.getCompleteStudentCnt(systemCode, curriCode,curriYear, curriTerm);
    }catch(Exception e){
        e.printStackTrace();
    }

    return cnt;
 }

  /**
   * 권한체크
   * @param loginYn	- 로그인여부
   * @param userId	- 로그인사용자 아이디
   * @param roll	- 접근가능권한
   * @param userType	- 로그인사용자 타입
   */
  public static boolean getAuthorCheckPageLoding(HttpServletRequest request, String pMenuUrl2){

  	ProgAuthorDAO progAuthorDao = new ProgAuthorDAO();
  	String systemCode = UserBroker.getSystemCode(request);
  	String pMenuUser  = UserBroker.getUserType(request);
  	
  	boolean bResult = false;
  	String returnVal = null;
  	
  	try{
 		
  		// 게시판관리의 경우 상세 url 에 대한 체크는 각각의 화면단에서 체크되므로, 
  		// 여기서는 cmd 의 경로까지만 체크함..
  		returnVal = progAuthorDao.checkProgAuthor(systemCode, pMenuUrl2);
  		
  		if(returnVal==null) return true;  // db 에 없을때는 모든 프로그램 handling 가능토록..
  		
  		String[] arr = returnVal.split(",");

  		for(int i=0; i<arr.length;i++){
  			if(!arr[i].equals(pMenuUser)) continue;
  			
  			bResult = true;
  		}

   	}catch(Exception e){
  		e.printStackTrace();
  		bResult = false;
  	}
   	
	return bResult;
  }

  
  /**
   * 권한체크
   * @param loginYn	- 로그인여부
   * @param userId	- 로그인사용자 아이디
   * @param roll	- 접근가능권한
   * @param userType	- 로그인사용자 타입
   */
  public static boolean getAuthorCheck(HttpServletRequest request, String pRights){

  	ProgMenuDAO progMenuDao = new ProgMenuDAO();
  	HttpSession session = request.getSession();

  	String systemCode = UserBroker.getSystemCode(request);
  	//String pUserType  = StringUtil.nvl(UserBroker.getUserType(request),"G");
  	String pMenuUrl   = session.getAttribute("MENUURL").toString(); // menuUrl type : url&MENUCODE=pWorkGroup-pMenuCode
  	String pMenuUser  = UserBroker.getUserType(request);

  	boolean bResult = false;

  	try{
//		 테이블에 존재하지 않을 경우 -1  ==> true ...because of main page
//		 테이블에 존재하지만 권한이 없을경우는 0 ==> false
//   	권한이 있으면 1이상을 반환한다 ==> true

  		int cntMenu = progMenuDao.getMenuAuthor(systemCode, pMenuUrl, pMenuUser, pRights);
  	  	if(cntMenu!=0) bResult = true;

  	//  학사공지, 일반공지, 자유게시판, 학교에 바란다......(메뉴에 없기 때문 only 사이버 노동대)
  	  if(pMenuUrl.indexOf("pBbsId=17")>0 || pMenuUrl.indexOf("pBbsId=18")>0 || pMenuUrl.indexOf("pBbsId=19")>0 || pMenuUrl.indexOf("pBbsId=20")>0 || pMenuUrl.indexOf("pBbsId=21")>0) {
  			bResult = true;
  		}
  		
   	}catch(Exception e){
  		e.printStackTrace();
  		bResult = false;
  	}
   	
	return bResult;
  }
  
  
  /**
   * getAuthorCheckAjax
   * @param request
   * @param response
   * @return
   * @throws Exception
   */
	public String[] getAuthorCheckAjax(HttpServletRequest request, HttpServletResponse response) throws Exception{
	  	
	  	ProgMenuDAO progMenuDao = new ProgMenuDAO();
	  	HttpSession session = request.getSession();

	  	String systemCode = UserBroker.getSystemCode(request);
	  	String pMenuUrl   = session.getAttribute("MENUURL").toString(); // menuUrl type : url&MENUCODE=pWorkGroup-pMenuCode
	  	String pMenuUser  = UserBroker.getUserType(request);
	  	
	  	String[] dataList = new String[4];
	  	try{
//			 테이블에 존재하지 않을 경우 -1  ==> true ...because of main page
//			 테이블에 존재하지만 권한이 없을경우는 0 ==> false
//	   	권한이 있으면 1이상을 반환한다 ==> true
	  		
	  		if( progMenuDao.getMenuAuthor(systemCode, pMenuUrl, pMenuUser, "C") !=0 ) dataList[0] ="true";
	  		else dataList[0] = "false";
	  		
	  		if( progMenuDao.getMenuAuthor(systemCode, pMenuUrl, pMenuUser, "R") !=0 ) dataList[1] ="true";
	  		else dataList[1] = "false";
	  		
	  		if( progMenuDao.getMenuAuthor(systemCode, pMenuUrl, pMenuUser, "U") !=0 ) dataList[2] ="true";
	  		else dataList[2] = "false";
	  		
	  		if( progMenuDao.getMenuAuthor(systemCode, pMenuUrl, pMenuUser, "D") !=0 ) dataList[3] ="true";
	  		else dataList[3] = "false";
	  		
	  		//  학사공지, 일반공지, 자유게시판, 학교에 바란다......(메뉴에 없기 때문 only 사이버 노동대)
	  		if(pMenuUrl.indexOf("pBbsId=17")>0 || pMenuUrl.indexOf("pBbsId=18")>0 || pMenuUrl.indexOf("pBbsId=19")>0 || pMenuUrl.indexOf("pBbsId=20")>0 || pMenuUrl.indexOf("pBbsId=21")>0) {
	  			dataList[0]="true";
	  			dataList[1]="true";
	  			dataList[2]="true";
	  			dataList[3]="true";
	  		}
 		
	   	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	   	
		return dataList;
	  }
	
	
  /**
   * 네비게이션 타이틀을 가져온다.
   * @param request
   * @param pDefaltTitle
   * @return
   */
  public static String[] getNaviTitle(HttpServletRequest request){

  	ProgMenuDAO progMenuDao = new ProgMenuDAO();
  	HttpSession session = request.getSession();

  	String systemCode = UserBroker.getSystemCode(request);
  	//String pUserType  = StringUtil.nvl(UserBroker.getUserType(request),"G");
  	String pMenuUrl   = session.getAttribute("MENUURL").toString(); // menuUrl type : url&MENUCODE=pWorkGroup-pMenuCode
  	String pMenuUser  = UserBroker.getUserType(request);

  	String[] sMenuTitle = new String[2];
  	try{
//  	 향후에 pMenuUrl 이 널인 경우가 없도록 수정...2007.09.16
  		if(!("").equals(pMenuUrl)) sMenuTitle = progMenuDao.getMenuNaviTitle(systemCode, pMenuUrl, pMenuUser);
  	}catch(Exception e){
  		e.printStackTrace();
  	}

	return sMenuTitle;
  }

  
  /**
   * 
   * @param request
   * @param pMenuTitle
   * @return
   */
  public static String getPageTitle(HttpServletRequest request, String pMenuTitle){

  	ProgMenuDAO progMenuDao = new ProgMenuDAO();
  	HttpSession session = request.getSession();

  	String systemCode = UserBroker.getSystemCode(request);
  	String pMenuUrl   = session.getAttribute("MENUURL").toString(); // menuUrl type : url&MENUCODE=pWorkGroup-pMenuCode
  	String pMenuUser  = UserBroker.getUserType(request);

  	String sPageTitle = new String();
  	try{
  		sPageTitle = progMenuDao.getMenuPageTitle(systemCode, pMenuUrl, pMenuUser);
  		// 향후에 pMenuUrl 이 널인 경우가 없도록 수정...2007.09.16
  		if( ("").equals(pMenuUrl) || ("").equals(sPageTitle)) sPageTitle = pMenuTitle ;
  	}catch(Exception e){
  		e.printStackTrace();
  	}

	return sPageTitle;
  }
  
  	/**
  	 * VOD 서버로 파일을 업로드 한다.
  	 * @param FilePath
  	 * @param sFileName
  	 * @return
  	 */
	public static boolean VodFTPUpload(String FilePath, String sFileName) {
		boolean retVal		=	false;		
		//-- FTP 파일 올리기
		FTPClient	ftpClient	=	null;
		InputStream input 		= 	null;
		File 		local 		= 	null;
		
		try {
			ftpClient	= 	new FTPClient();
			ftpClient.connect(sServer, iPort);
			
			int reply;
			
			// 연결 시도후, 성공했는지 응답 코드 확인
			reply = ftpClient.getReplyCode();


			if(!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				//System.out.println("서버로부터 연결을 거부당했습니다");
			} 
			else {
				//-- 로그인
				ftpClient.login(sId, sPassword);
				ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
				ftpClient.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);

				//-- 폴더이동 || 폴더가 없다면 생성한 후 이동한다.
				try {
					if(!ftpClient.changeWorkingDirectory("/ucc")) {
						ftpClient.makeDirectory("/ucc");
					}
					ftpClient.changeWorkingDirectory("/ucc");

				} catch (IOException ioe) {
					//System.out.println("폴더를 이동하지 못했습니다");
				}
				
				//-- 업로드 파일 파라메터 세팅
				try {
					local = new File(sUpDir+FilePath, sFileName); //sUpDir
					input = new FileInputStream(local);
				}
				catch(FileNotFoundException e) {
					//System.out.println("파일 파라메터를 세팅하지 못했습니다.");
				}

				//-- 파일 업로드
				try {
					// targetName 으로 파일이 올라간다
					ftpClient.storeFile(sFileName, input);
					retVal	=	true;
				}
				catch(IOException e) {
					//System.out.println("파일을 전송하지 못했습니다");
				}

				ftpClient.logout();
			}
			ftpClient.disconnect();
		}
		catch (IOException ioe) {
			if(ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch(IOException f) {
					//System.out.println("서버로부터 연결을 거부당했습니다");
				}
			}
		}
		//-- FTP 올리기 끝
		return retVal;
	}
	
	/**
	 * 파일확장자 명을 가져온다.
	 * @param fileName
	 * @return
	 */
	public static String getFileType(String fileName) {
		String	fileType	=	"";
					
		fileType	=	fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
		
		return fileType;
	}
  
}
