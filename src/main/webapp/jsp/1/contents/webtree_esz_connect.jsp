<%
/******************************************************************************
	프로그램 : webtree_esz_connect.jsp
	모 듈 명 : 강의 컨텐츠 서버 업로드
	설    명 : 강의 컨텐츠를  Web, Vod 서버에 업로드
	테 이 블 : contents

	작 성 자 : 김용대
	작 성 일 : 2003-12-31
	수 정 일 : 2003-12-31
	수정사항 :
	미결사항 :
******************************************************************************/
%>
<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.common.CommonUtil,java.io.File,javax.sql.RowSet"%> 
<%@include file="../common/header.jsp" %>
<%
	String	wk_bright		=	"<img src=\""+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet04.gif\" align=absmiddle>&nbsp";
	String	pSYSTEM_CODE	=	SYSTEMCODE;
	String	pCURRI_CODE		=	StringUtil.nvl(model.get("pCurriCode"));
	String	pYEAR			=	StringUtil.nvl(model.get("pCurriYear"));
	String	pTERM			=	StringUtil.nvl(model.get("pCurriTerm"));
	String	pCOURSE_ID		=	StringUtil.nvl(model.get("pCourseId"));
	String	pCOURSE_NAME	=	StringUtil.nvl(model.get("pCourseName"));
	String	pCOMAIN_ID		=	pCOURSE_ID;

	String	config_type		=	"";		// 설정대코드
	String	config_code		=	"";		// 설정소코드
	String	config_value	=	"";		// 설정값
	String	subtype_name	=	"";		// 설정내용
	
	String	ftp_sv			=	"";
	String	ftp_id			=	"";
	String	ftp_pw			=	"";
	String	ftp_po			=	"";
	String	ftp_dr			=	"";
	String	vod_sv			=	"";
	String	vod_id			=	"";
	String	vod_pw			=	"";
	String	vod_po			=	"";
	String	vod_dr			=	"";
	String	vod_dr2			=	"";
	String	vod_pro			=	"";
	String	sql1			=	"";	
	
	RowSet	rs	=	(RowSet)model.get("ConfigList");
	while(rs.next()) {
		
		config_code		=	StringUtil.nvl(rs.getString("config_code"));
		config_value	=	StringUtil.nvl(rs.getString("config_value"));
		
		if(config_code.equals("036")){
			ftp_sv		=	config_value;
		}
		else if(config_code.equals("032")){
			ftp_id		=	config_value;
		}
		else if(config_code.equals("033")){
			ftp_pw		=	config_value;
		}
		else if(config_code.equals("034")){
			ftp_dr		=	config_value;
		}
		else if(config_code.equals("035")){
			ftp_po		=	config_value;
		}
		else if(config_code.equals("016")){
			vod_sv		=	config_value;
		}
		else if(config_code.equals("012")){
			vod_id		=	config_value;
		}
		else if(config_code.equals("013")){
			vod_pw		=	config_value;
		}
		else if(config_code.equals("015")){
			vod_po		=	config_value;
		}
		else if(config_code.equals("014")){
			vod_dr		=	config_value;
		}
		else if(config_code.equals("018")){
			vod_pro		=	config_value;
		}
	}
		
	//String	web_dir			=	ftp_dr + "data/contents/"+ SYSTEMCODE+"/"+ pCOMAIN_ID;
	String	web_dir			=	ftp_dr+"/"+ pCOMAIN_ID;
	File	temp			=	new File(web_dir);
	if (!temp.isDirectory()){
		temp.mkdirs();
	}

	String	vod_dir1		=	vod_dr + "/" + pCOMAIN_ID;
	String	vod_dir2		=	vod_pro + "://" + vod_sv + vod_dr + "/" + pCOMAIN_ID;
%>
<style>
  td { font-size:10pt; }
  input { border: #545353; border-style: solid; border-top-width: 1px; border-right-width: 1px; border-bottom-width: 1px; border-left-width: 1px; }
  .inputtext { width: 424px; }
  .inputtext2 { width: 320px; }
  .inputbutton { width: 100px; }
  .title { font-family: "Arial", "Helvetica", "sans-serif"; font-size: 12pt; font-weight: bold; color: white; }
  .copyright { font-family: "돋움, 굴림"; font-size: 9pt; color: navy; line-height:20pt; }
</style>
<script src="<%=CONTEXTPATH%>/js/EszUpploadPlus4.js" language="javascript"></script>
<script language="javascript">
<!--
var gbConnected = false;
var gnConnIndex = 0;
var gnVersion;

function Connect(idx)
{
	if (gbConnected) return true;

	var StrIP = f1.StrServerName[idx].value;
	var StrID = f1.StrUserName[idx].value;
	var StrPW = f1.StrPassword[idx].value;
	var IntPort = f1.IntPortNumber[idx].value;
	var sPath = f1.StrSourcePath.value;
	var tPath = f1.StrTargetPath[idx].value;

	if (StrIP == '') return false;
	if (StrIP.substr(0, 6).toLowerCase() == 'ftp://') StrIP = StrIP.substr(6);
	if (typeof("gnVersion") == "undefined")
	{
		if (mOCX.HzOpenESZ(sPath) == 0)
			gnVersion = mOCX.HzGetESZInfo("version");
		else
			gnVersion = 3;
	}

	var o = new FtpObject(StrIP, StrID, StrPW, sPath, tPath, IntPort);
	o.init();
	gbConnected = o.connect();
	if (gbConnected) gnConnIndex = parseInt(idx+1, 10);
	ChangeConnState();

	return true;
}

function Disconnect()
{
	if (!gbConnected) return false;

	gFtpConn.disconnect();
	gbConnected = false;
	ChangeConnState();
	return true;
}

function ChangeConnState()
{
	if (gnConnIndex > 0) {
		var f1 = document.f1;
		f1.Conn[gnConnIndex-1].disabled = gbConnected;//f1.Disconn.disabled = !gbConnected;
	}
}

function GetResult ( bRes )
{
	if ( bRes )
		alert("Success!!!");
	else
		alert("Fail!!!");
}

function UploadHTMLData()
{
	if (gnConnIndex != 1) {
		Disconnect();
	}

	Connect(0);
	if (!gbConnected) return false;
	var ArrList = GetWebDataArray();
	var res = Upload(ArrList);
	GetResult ( res );
	Disconnect();
}

function UploadAsfData()
{
	if (gnConnIndex != 2) {
		Disconnect();
	}

	Connect(1);
	if (!gbConnected) return false;
	var ArrList = GetMediaDataArray();
	var res = Upload(ArrList);
	GetResult ( res );
	Disconnect();
}

function UploadViewer()
{
	if (gnConnIndex != 1) {
		Disconnect();
	}

	Connect(0);
	if (!gbConnected) return false;

	var ArrList = GetWebViewerArray();
	var res = Upload(ArrList);
	GetResult(res);
	Disconnect();
}

function UploadAllData()
{
	Disconnect();

	var WebConn = Connect(0);
	if ( WebConn ) {
		var ArrList = new Array();
		ArrList = ArrList.concat(GetWebDataArray(), GetWebViewerArray());
		var res = Upload(ArrList);
		alert ( "WEB Upload : " + res );
		Disconnect();
	}

	var MediaConn = Connect(1);
	if ( MediaConn ) {
		var ArrList = GetMediaDataArray();
		var res = Upload(ArrList);
		alert ( "Media Upload : " + res );
		Disconnect();
	}
}

function RemoveAllData()
{
	if (confirm("정말로 삭제하시겠습니까? 삭제 후에는 데이터를 복구할 수 없습니다.")) {
		Disconnect();

		var WebConn = Connect(0);
		if ( WebConn ) {
			var res = true;
			var tPath = document.f1.StrTargetPath[0].value;
			var res = xFTP.DeletePath(tPath, 'MD');
			alert ( "WEB Delete : " + res );
			Disconnect();
		}

		var MediaConn = Connect(1);
		if ( MediaConn ) {
			var tPath = document.f1.StrTargetPath[1].value;
			var res = xFTP.DeletePath(tPath, 'MD');
			alert ( "Media Delete : " + res );
			Disconnect();
		}
	}
}

function UnZam()
{
	var fname = document.f1.StrEszPath.value;
	var fext = fname.replace(/(.*)\.([^\.]*)$/i, "$2");
	if (fext.toLowerCase() != "esz") return false;

	document.body.style.cursor = "wait";
	var sTemp = getTemp();
	Zam.SetZamFileName(fname);
	Zam.UnZamFile(sTemp);
	document.body.style.cursor = "auto";

	document.f1.StrSourcePath.value = sTemp;
	if ( mOCX.HzOpenESZ(sTemp) == 0)
	{
		GetContentInfo();
	}
}

function GetContentInfo()
{
    var frm = document.f1;
    var sPath = frm.StrSourcePath.value ;

    strVer    = mOCX.HzGetESZInfo("version");
	gnVersion = strVer;

    strAuthor = mOCX.HzGetESZInfo("author");
    strTitle  = mOCX.HzGetESZInfo("title");
    strDate   = mOCX.HzGetESZInfo("date");
    strOutline= mOCX.HzGetESZInfo("comments");
    if (strVer == "3")
    	strVideo = sPath + "/output.asf";
    else
    	strVideo = sPath + "/media/output.asf";
    strTime   = mOCX.HZGetDuration(strVideo);

    frm.L_Writer.value = Trim(strAuthor);
    frm.L_Name.value = Trim(strTitle);
    frm.L_LecDate.value = Trim(strDate).replace(/\//g, '-');
    frm.L_LecTime.value = Math.ceil(Number(Trim(strTime) / 60000));
    frm.L_Intro.value = Trim(strOutline).replace(/(\||)/g, "\n")

    //강의동영상 파일 크기 (byte)
    var Fsize = mOCX.HZGetFileSize(strVideo);
    //보조동영상 파일들 크기 (byte)
    var FSubSize = mOCX.GetFolderSize(sPath + "\\HTML\\AsfFiles\\");

    //전체동영상 크기
    Fsize += FSubSize;
    var tmp = (Number(Fsize) / (1024 * 1024)) * 10;
    frm.L_FileSize.value = Math.round(tmp) / 10;

    //DateChk(f1);
    //_isupload = true;
}

function getTemp()
{
	var sPath = mOCX.GetTempFolder();
	if (sPath.match(/(.)$/)[1] != '\\') sPath += '\\';
	sPath += 'eStreamManager';
	mOCX.EmptyFolder(sPath);
	mOCX.CreateFolder(sPath);
	return sPath;
}

function showdlg(vField)
{
	mOCX.ShowFileDlg();
	var fname = mOCX.getfilename();
	if ( fname != '') eval('document.f1.'+vField).value = fname;
	UnZam();
}

function upVideoUrl()
{
	var sPath = document.f1.StrSourcePath.value;
	var sUrl = document.f1.strVideoUrl.value;
	if (sPath == '' || sUrl == '' || sUrl == 'mms://') return false;
	var res;
	if ( mOCX.HzOpenESZ(sPath) == 0)
	{
		res = mOCX.HzSetVideoUrl(sUrl);
	}
	GetResult( res==0 );
}

function Trim(vStr) {
    var tmp = vStr;
    var atChar;

    if (tmp.length > 0) atChar = tmp.charAt(0);
    while (atChar == ' ' || atChar == '\t') {
      tmp = tmp.substring(1, tmp.length);
      atChar = tmp.charAt(0);
    }
    if (tmp.length > 0) atChar = tmp.charAt(tmp.length - 1);
    while (atChar == ' ' || atChar == '\t') {
      tmp = tmp.substring(0, tmp.length - 1);
      atChar = tmp.charAt(tmp.length - 1);
    }
    return tmp;
}

function goList() {
	document.location = "<%=CONTEXTPATH%>/Contents.cmd?cmd=contentsList&pMode=MyPage&pCourseId=<%=pCOURSE_ID%>&pCourseName=<%=pCOURSE_NAME%>";
}
//-->
</script>
<script language=javascript>
function setTargetValue() {
	document.f1.StrTargetPath[0].value = document.f1.StrTargetPathOld[0].value + "/" + document.f1.UploadFolderName.value;
	document.f1.StrTargetPath[1].value = document.f1.StrTargetPathOld[1].value + "/" + document.f1.UploadFolderName.value;
	if (mOCX.HzGetESZInfo("version") == "3") {
		document.f1.strVideoUrl.value = document.f1.strVideoUrlOld.value + "/" + document.f1.UploadFolderName.value + "/output.asf";
	}
	else {
		document.f1.strVideoUrl.value = document.f1.strVideoUrlOld.value + "/" + document.f1.UploadFolderName.value + "/media/output.asf";
	}
}
</script>



<form name="f1" method="post" action="">
<table width="670" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td>
			<table border=0 cellspacing=0 cellpadding=0 width=100%>
				<tr height=30>
					<td width=20>&nbsp;</td>
					<td align=left><b>※ ESZ UPLOAD( E-Stream 저작도구로 제작한 컨텐츠를 업로드합니다. )</b></td>
					<td width=20>&nbsp;</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td height=3></td>
	</tr>
</table>

<table width="670" border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td width=20>&nbsp;</td>
		<td class=bgline2>
		  <table border=0 cellspacing=1 cellpadding=3 width=100% height=100%>
		    <tr valign=middle>
				<td colspan=8 align="right"><script language=javascript>Button5("목록", "goList()", "");</script></td>
			</tr>
		    <tr valign=middle>
				<td class="s_tab01" colspan=8></td>
			</tr>
		    <tr>
		      <td rowspan=8 class="s_tab_view01">컨텐츠<br>정보</td>
		      <td class="s_tab_view01"><%=wk_bright%> ESZ File</td>
		      <td class="s_tab_view02">
		        <input type="text" name="StrEszPath" class="inputtext2">
		        <input type="button" value="File..." name="UpFileBtn" onClick="javascript:showdlg('StrEszPath');" class="inputbutton">
		      </td>
		    </tr>
		    <tr>
		      <td class="s_tab_view01"><%=wk_bright%> Source Path</td>
		      <td class="s_tab_view02">
		        <input type="text" name="StrSourcePath" class="inputtext">
		      </td>
		    </tr>
		    <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 저작자</td>
		      <td class="s_tab_view02">
		        <input type="text" name="L_Writer" class="inputtext" disabled>
		      </td>
		    </tr>
		     <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 제목</td>
		      <td class="s_tab_view02">
		        <input type="text" name="L_Name" class="inputtext" disabled>
		      </td>
		    </tr>
		     <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 저작일자</td>
		      <td class="s_tab_view02">
		        <input type="text" name="L_LecDate" class="inputtext" disabled>
		      </td>
		    </tr>
		     <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 총시간(분)</td>
		      <td class="s_tab_view02">
		        <input type="text" name="L_LecTime" class="inputtext" disabled>
		      </td>
		    </tr>
		     <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 동영상크기</td>
		      <td class="s_tab_view02">
		        <input type="text" name="L_FileSize" class="inputtext2" disabled>(Mbyte)
		      </td>
		    </tr>
		     <tr>
		      <td class="s_tab_view01"><%=wk_bright%> 개요</td>
		      <td class="s_tab_view02">
		        <textarea name="L_Intro" rows="3" cols="57" style="border:1 solid #003300;back-color:gray;" disabled></textarea>
		      </td>
		    </tr>
		    <tr>
		      <td class="s_tab_view01" colspan="2"><b>업로드 폴더명</b></td>
		      <td class="s_tab_view02">
		        <input type="text" name="UploadFolderName" class="inputtext2" onkeyup="javascript:setTargetValue();">
		      </td>
		    </tr>
<input type="hidden" name="StrServerName" value="<%=ftp_sv%>">
<input type="hidden" name="StrUserName" value="<%=ftp_id%>">
<input type="hidden" name="StrPassword" value="<%=ftp_pw%>">
<input type="hidden" name="IntPortNumber" value="<%=ftp_po%>">
<input type="hidden" name="StrTargetPath" value="<%=web_dir%>">
<input type="hidden" name="StrTargetPathOld" value="<%=web_dir%>">
		    <tr>
		      <td class="s_tab_view01" colspan="2">WEB Server</td>
		      <td class="s_tab_view02">
		        <input type="button" value="connect" name="Conn" onClick="javscript:Connect(0);" class="inputbutton"> <input type="button" value="disconnect" name="Disconn" onClick="javascript:Disconnect();" class="inputbutton">
		      </td>
		    </tr>
<input type="hidden" name="StrServerName" value="<%=vod_sv%>">
<input type="hidden" name="StrUserName" value="<%=vod_id%>">
<input type="hidden" name="StrPassword" value="<%=vod_pw%>">
<input type="hidden" name="IntPortNumber" value="<%=vod_po%>">
<input type="hidden" name="StrTargetPath" value="<%=vod_dir1%>">
<input type="hidden" name="StrTargetPathOld" value="<%=vod_dir1%>">
		    <tr>
		      <td class="s_tab_view01" colspan="2">VOD Server</td>
		      <td class="s_tab_view02">
		        <input type="button" value="connect" name="Conn" onClick="javscript:Connect(1);" class="inputbutton"> <input type="button" value="disconnect" name="Disconn" onClick="javascript:Disconnect();" class="inputbutton">
		      </td>
		    </tr>
		    <tr>
		      <td colspan="2" class="s_tab_view01"><%=wk_bright%> Video URL</td>
		      <td class="s_tab_view02">
		        <div align="left">
		          <input type="text" name="strVideoUrl" class="inputtext" style="width:320px" value="<%=vod_dir2%>">&nbsp;
		          <input type="button" value="SetVideoUrl" onClick="upVideoUrl();" class="inputbutton">
		        </div>
		      </td>
		    </tr>
<input type="hidden" name="strVideoUrlOld" class="inputtext" value="<%=vod_dir2%>">
		    <tr>
		      <td colspan="2" class="s_tab_view01"><%=wk_bright%> Upload</td>
		      <td class="s_tab_view02">
		        <input type="button" value="HTML Data" name="HTMLData" onClick="javscript:UploadHTMLData();" class="inputbutton">
		        <input type="button" value="Viewer" name="ViewerData" onClick="javscript:UploadViewer();" class="inputbutton"> |
		        <input type="button" value="ASF Data" name="ASFData" onClick="javscript:UploadAsfData();" class="inputbutton"> |
		        <input type="button" value="Upload All" name="AllData" onClick="javscript:UploadAllData();" class="inputbutton">
		      </td>
		    </tr>
		    <!--tr>
		      <td colspan="2" class="s_tab_view01"><%=wk_bright%> Remove</td>
		      <td class="s_tab_view02"><input type="button" value="Remove" name="RemoveData" onClick="javscript:RemoveAllData();" class="inputbutton"></td>
		    </tr-->
			</form>

			<tr>
				<td colspan="2" class="s_tab_view01">진행상태</td>
				<td class="s_tab_view02">
				<OBJECT id='xFTP' classid='clsid:9365D806-0A8D-11D6-82E5-0050DA8D82D4' style="LEFT: 0px; TOP: 0px; width:430px; height:50px" CODEBASE="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/contents/eStreamManager.cab#version=1,0,0,14">
					<PARAM NAME="_Version" VALUE="65536">
					<PARAM NAME="_ExtentX" VALUE="2646">
					<PARAM NAME="_ExtentY" VALUE="1323">
					<PARAM NAME="_StockProps" VALUE="0">
					<PARAM NAME="GraphColor" VALUE="483D8B">
				</OBJECT>
				<OBJECT id='mOCX' WIDTH=0 HEIGHT=0 classid='clsid:48E94CA6-1706-11D6-82E5-0050DA8D82D4' CODEBASE="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/contents/eStreamManager.cab#version=1,0,0,14">
					<PARAM NAME="_Version" VALUE="65536">
					<PARAM NAME="_ExtentX" VALUE="2646">
					<PARAM NAME="_ExtentY" VALUE="1323">
					<PARAM NAME="_StockProps" VALUE="0">
					<PARAM NAME="extensions" value="ESZ file(*.esz)|*.esz|모든 파일 (*.*)|*.*||">
				</OBJECT>
				<OBJECT ID="Zam" WIDTH=0 HEIGHT=0 CLASSID="CLSID:09B5EEC6-A43F-11D4-BC8D-0001027EFE3C" CODEBASE="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/contents/eStreamManager.cab#version=2,0,0,5">
					<PARAM NAME="_Version" VALUE="65536">
					<PARAM NAME="_ExtentX" VALUE="2646">
					<PARAM NAME="_ExtentY" VALUE="1323">
					<PARAM NAME="_StockProps" VALUE="0">
				</OBJECT>
				</td>
			</tr>
			<tr valign=middle>
				<td class="s_tab05" colspan=8></td>
			</tr>
			</table>
		</td>
		<td width=20>&nbsp;</td>
	</tr>
</table>

<table class=base border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td width=20>&nbsp;</td>
		<td >
			<table border=0 cellspacing=0 cellpadding=3 width=100% height=100%>
				<tr>
					<td width=10>&nbsp;</td>
					<td>
						<font color="red"><B>※ 다음은 실행방법에 대한 안내입니다.
						   아래의 실행순서대로 실행하시기 바랍니다.</b></font><br><br>

						1. <b>ESZ 파일 선택</b><br>
						    &nbsp;&nbsp;&nbsp;(1). 현재 페이지는 esz 파일 업로드 페이지 입니다. 컨텐츠를 esz 파일로 퍼블리싱 합니다.<br>
						    &nbsp;&nbsp;&nbsp;(2). 상단의 [컨텐츠 정보] ▶ [ESZ File] 에서 [File...] 버튼을 눌러서 퍼블리싱이 완료된 esz 파일을 선택합니다.<br>
						    &nbsp;&nbsp;&nbsp;(3). 파일을 선택하면 컨텐츠 정보의 나머지란이 자동으로 입력됩니다.<br><br>

						2. <b>업로드 폴더명 입력</b><br>

						    &nbsp;&nbsp;&nbsp;(1). 강의를 업로드 하고자 하는 컨텐츠 및 VOD 서버의 업로드 폴더명을 입력합니다.<br>
						    &nbsp;&nbsp;&nbsp;(2). 업로드 폴더명은 영문 또는 숫자로 입력해 주세요.(띄워쓰기 불가)<br>
						    &nbsp;&nbsp;&nbsp;(3). 입력예시) lesson1 또는 lesson/01<br>
						    &nbsp;&nbsp;&nbsp;(4). 업로드 폴더명에 입력한 폴더 구조대로 컨텐츠 서버 및 VOD 서버에 업로드 됩니다.<br><br>

						3. <b>Video URL 확인</b><br>
						    &nbsp;&nbsp;&nbsp;(1). 업로드 폴더명에 입력한 대로 기본적으로 Video URL이 만들어 집니다.<br>
						    &nbsp;&nbsp;&nbsp;(2). 이 경로는 실제 사용될 VOD의 MMS URL입니다. output.asf의 위치가 정확한지 확인해 주세요.<br>
						    &nbsp;&nbsp;&nbsp;(3). eStream 3.0의 경우에는 media 폴더가 생략될 수 있습니다.<br>
						    &nbsp;&nbsp;&nbsp;(4). <font color="red"><b>URL정보가 이상이 없으면 반드시 SetVideoUrl 버튼을 클릭하셔야 합니다.</b></font><br><br>

						4. <b>컨텐츠 업로드</b><br>
						    &nbsp;&nbsp;&nbsp;(1). Upload All : 전체 컨텐츠 및 동영상을 업로드 합니다.<br>
						    &nbsp;&nbsp;&nbsp;(2). HTML Data : 컨텐츠 서버로 컨텐츠 중 HTML 파일 및 이미지 파일만 업로드 합니다.<br>
						    &nbsp;&nbsp;&nbsp;(3). Viewer : 컨텐츠 서버로 컨텐츠 중 Viewer OCX 및 Java script를 업로드 합니다.<br>
						    &nbsp;&nbsp;&nbsp;(4). ASF Data : VOD 서버로 동영상을 업로드 합니다.<br>
					</td>
				</tr>
				 <tr valign=middle>
					<td class=th1 colspan=8></td>
				</tr>
				 <tr valign=middle>
					<td width=20>&nbsp;</td>
				</tr>
			</table>
		</td>
		<td width=20>&nbsp;</td>
	</tr>
</table>
</td>
</tr>
</table>
<%@include file="../common/footer.jsp" %>