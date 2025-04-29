<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.lang.*,java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.StringUtil"%>
<%@ page import ="com.edutrack.curritop.dto.ContentsFileDTO"%> 
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String SYSTEMCODE = UserBroker.getSystemCode(request);
%>
<html>
<head>
<title>Edutrack</title>
<link href="<%=CONTEXTPATH%>/css/style.css"" rel="stylesheet" type="text/css">
<script language="JavaScript" src = "<%=CONTEXTPATH%>/js/common1.js"></script>
<SCRIPT LANGUAGE="JavaScript">


function cellColor1(cell,color) {
    cell.style.backgroundColor = color;
}
function cellColor0(cell) {
    cell.style.backgroundColor = "";
}

function fileDownload(rfilename, sfilename, filepath, filesize){
   var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
   hiddenFrame.document.location = loc;
}
</SCRIPT>
</head>
<body topmargin=0 leftmargin=0 marginwidth=0 marginheight=0>
<%
	String pPathDir = (String)model.get("pPathDir");
	ArrayList fileList = (ArrayList)model.get("pFileList");
	ArrayList dirList = (ArrayList)model.get("pDirList");
	ContentsFileDTO fileDto = null;
	ContentsFileDTO dirDto = null;
%>

<table border=0 cellspacing=0 cellpadding=0  bgcolor="#D6D3CE" width=100% height=100%>
	<tr>
		<td valign=top bgcolor=white width=100% align=left>
			<TABLE BGCOLOR='#F5F8ED' width=100% BORDER="0" cellspacing=0 cellpadding=0>
				<TR>
					<TD  ALIGN="left">
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr>
								<td><b>Current Path : <font color='red'><%=StringUtil.outDataConverter(pPathDir.replaceAll("contents/"+SYSTEMCODE+"/",""))%></font></b></td>
							</tr>
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width=100%  BORDER="0" CELLPADDING="0" CELLSPACING="0">
				<TR>
					<TD BGCOLOR="white">
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>&nbsp;</td></tr>
						</table>
					</TD>
					<TD BGCOLOR="white" ALIGN="center">
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Name</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="white" ALIGN="center">
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Size</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="white" ALIGN="center">
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Date</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="white" ALIGN="center"'>
						<table border=0 width=100% height=100% cellspacing=0 cellpadding=1><tr><td>Download</TD></TR>
						</TABLE>
					</TD>
				</TR>
				<tr><td colspan='7' height='1' class='b_td04'></td></tr>				
<%
int dirCnt = 0;
for(int i=0; i < dirList.size(); i++)
{
	dirDto = (ContentsFileDTO)dirList.get(i);
	dirCnt++;
%>
				<TR>
					<TD ALIGN="center"><A href="<%=CONTEXTPATH%>/Contents.cmd?cmd=WCMFrameRight&pPathDir=<%=dirDto.getFileDir()+"/"+StringUtil.outDataConverter(dirDto.getFileName())%>" target=_self><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/folder.gif" BORDER="0"></A></TD>
					<TD ALIGN="left"  ><A href="<%=CONTEXTPATH%>/Contents.cmd?cmd=WCMFrameRight&pPathDir=<%=dirDto.getFileDir()+"/"+StringUtil.outDataConverter(dirDto.getFileName())%>" target=_self><%=StringUtil.outDataConverter(dirDto.getFileName())%></A></TD>
					<TD ALIGN="left" ><%=dirDto.getFileSize()%></TD>
					<TD ALIGN="left" ><%=dirDto.getFileTime()%></TD>
				</TR>
<%
}%>
<%
int fileCnt = 0;
String serverPath = "";
for(int i=0; i < fileList.size(); i++){
	fileDto = (ContentsFileDTO)fileList.get(i);
	fileCnt++;
	
	serverPath = StringUtil.outDataConverter(fileDto.getFileDir())+"/"+StringUtil.outDataConverter(fileDto.getFileName());
%>
				<TR onMouseOver="cellColor1(this,'#dddddd')" onMouseOut="cellColor0(this)">
					<TD ALIGN="center"><IMG SRC ="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/<%=fileDto.getFileExtImg()%>" BORDER="0"></TD>
					<TD ALIGN="left" ><a href="javascript:parent.setFileName('<%=serverPath%>')"><%=StringUtil.outDataConverter(fileDto.getFileName())%></a></TD>
					<TD ALIGN="left" ><%=fileDto.getFileSize()%></TD>
					<TD ALIGN="left" ><%=fileDto.getFileTime()%></TD>
					<TD ALIGN="center"><a href="javascript:fileDownload('<%=StringUtil.outDataConverter(fileDto.getFileName())%>','<%=StringUtil.outDataConverter(fileDto.getFileName())%>','<%=fileDto.getFileDir()%>','<%=fileDto.getFileSize()%>');"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/download.gif"  NAME="IMG_download" BORDER="0" ALT="Download"></A></TD>
				</TR>
<%} %> 				
			</table>
		</td>
	</tr>
</table>

<%@include file="../common/popup_footer.jsp" %>