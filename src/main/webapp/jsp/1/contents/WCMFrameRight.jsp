<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="WCM_header.jsp" %>
<%
	String systemCode = (String)model.get("systemCode");
	String pPathDir = (String)model.get("pPathDir");
	ArrayList fileList = (ArrayList)model.get("pFileList");
	ArrayList dirList = (ArrayList)model.get("pDirList");
	ContentsFileDTO fileDto = null;
	ContentsFileDTO dirDto = null;
%>
<SCRIPT LANGUAGE="JavaScript">
compatibility=false; 
if(parseInt(navigator.appVersion)>=3.0){compatibility=true}
if(compatibility)
{
 IMG_htmlv_on     = new Image;		IMG_htmlv_on.src      = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/htmlview_.gif";
 IMG_htmlv_off    = new Image;		IMG_htmlv_off.src    = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/htmlview.gif";
 IMG_normalv_on     = new Image;		IMG_normalv_on.src      = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/normalview_.gif";
 IMG_normalv_off    = new Image;		IMG_normalv_off.src    = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/normalview.gif";
 IMG_normale_on     = new Image;		IMG_normale_on.src     = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/normaledit_.gif";
 IMG_normale_off    = new Image;		IMG_normale_off.src    = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/normaledit.gif";
 IMG_hnormale_on     = new Image;		IMG_hnormale_on.src     = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/hnormaledit_.gif";
 IMG_hnormale_off    = new Image;		IMG_hnormale_off.src    = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/hnormaledit.gif";
 IMG_delete_on    = new Image;		IMG_delete_on.src    = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/delete_.gif";
 IMG_delete_off   = new Image;		IMG_delete_off.src   = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/delete.gif";
 IMG_ren_on       = new Image;		IMG_ren_on.src       = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/rename_.gif";
 IMG_ren_off      = new Image;		IMG_ren_off.src      = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/rename.gif";
 IMG_move_on      = new Image;		IMG_move_on.src      = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/move_.gif";
 IMG_move_off     = new Image;		IMG_move_off.src     = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/move.gif";
 IMG_copy_on      = new Image;		IMG_copy_on.src      = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/copy_.gif";
 IMG_copy_off     = new Image;		IMG_copy_off.src     = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/copy.gif";
 IMG_download_on  = new Image;		IMG_download_on.src  = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/download_.gif";
 IMG_download_off = new Image;		IMG_download_off.src = "<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/download.gif";
} 

function change(x,y) { 
 if(compatibility) {
  document.images[x].src=eval(y+'.src');
 }
}
function radioDisabled(val){
	var f = document.WCM;
	var len = f.pFilePath.length;
	if(len >0){
		for(i=0;i<len;i++){
			f.pFilePath[i].disabled=val;
		}
	}else{
		f.pFilePath.disabled=val;
	}
}
function radioChecked(val){
	var f = document.WCM;
	var len = f.pFilePath.length;
	if(len >0){
		for(i=0;i<len;i++){
			f.pFilePath[i].checked=val;
		}
	}else{
		f.pFilePath.checked=val;
	}
}


function cellColor1(cell,color) {
    cell.style.backgroundColor = color;
}
function cellColor0(cell) {
    cell.style.backgroundColor = "";
}
function openerReload(){
	var page="<%=CONTEXTPATH%>/bin/WebContentsManager/index.php?pMode=lecture&pCourseId=test0002&ClickNode=";
}

function fileDownload(rfilename, sfilename, filepath, filesize){
   var loc="<%=CONTEXTPATH%>/jsp/1/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
   hiddenFrame.document.location = loc;
}
</SCRIPT>
<table border=0 cellspacing=0 cellpadding=0  bgcolor="#D6D3CE" width=100% height=100%>
<form name='WCM' method='post' >
	<tr>
		<td valign=top bgcolor=white width=100% align=left>
			<TABLE BGCOLOR="#D6D3CE" width=100% BORDER="0" cellspacing=0 cellpadding=0>
				<TR>
					<TD  ALIGN="left">
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr>
								<td>Path : <%=StringUtil.outDataConverter(pPathDir.replaceAll("contents/"+systemCode+"/",""))%></td>
							</tr>
						</table>
					</TD>
				</TR>
			</TABLE>
			<TABLE width=100%  BORDER="0" CELLPADDING="0" CELLSPACING="0">
				<TR>
					<TD BGCOLOR="#D6D3CE">
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>&nbsp;</td></tr>
						</table>
					</TD>
					<TD BGCOLOR="#D6D3CE" ALIGN="center">
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Name</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="#D6D3CE" ALIGN="center">
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Size</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="#D6D3CE" ALIGN="center">
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1>
							<tr><td>Date</TD></TR>
						</TABLE>
					</TD>
					<TD BGCOLOR="#D6D3CE" ALIGN="center" colspan='3'>
						<table border=1 width=100% height=100% cellspacing=0 cellpadding=1><tr><td>Select</TD></TR>
						</TABLE>
					</TD>
				</TR>
<%
int dirCnt = 0;
for(int i=0; i < dirList.size(); i++)
{
	dirDto = (ContentsFileDTO)dirList.get(i);
	dirCnt++;
%>
				<TR>
					<TD ALIGN="center"><A HREF=""><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/folder.gif" BORDER="0"></A></TD>
					<TD ALIGN="left"  ><A href="<%=CONTEXTPATH%>/Contents.cmd?cmd=WCMFrameRight&pPathDir=<%=dirDto.getFileDir()+"/"+StringUtil.outDataConverter(dirDto.getFileName())%>" target=_self><%=StringUtil.outDataConverter(dirDto.getFileName())%></A></TD>
					<TD ALIGN="left" ><%=dirDto.getFileSize()%></TD>
					<TD ALIGN="left" ><%=dirDto.getFileTime()%></TD>
				</TR>
<%
}%>
<%
int fileCnt = 0;
for(int i=0; i < fileList.size(); i++){
	fileDto = (ContentsFileDTO)fileList.get(i);
	fileCnt++;
%>
				<TR onMouseOver="cellColor1(this,'#dddddd')" onMouseOut="cellColor0(this)">
					<TD ALIGN="center"><IMG SRC ="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/<%=fileDto.getFileExtImg()%>" BORDER="0"></TD>
					<TD ALIGN="left"  ><%=StringUtil.outDataConverter(fileDto.getFileName())%></TD>
					<TD ALIGN="left" ><%=fileDto.getFileSize()%></TD>
					<TD ALIGN="left" ><%=fileDto.getFileTime()%></TD>
					<TD ALIGN="center"><a href="javascript:fileDownload('<%=StringUtil.outDataConverter(fileDto.getFileName())%>','<%=StringUtil.outDataConverter(fileDto.getFileName())%>','<%=fileDto.getFileDir()%>','<%=fileDto.getFileSize()%>');"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/download.gif"  NAME="IMG_download" BORDER="0" ALT="Download"></A></TD>
					<TD ALIGN="center"><INPUT TYPE="radio" NAME="pFilePath"  class="no" value="<%=StringUtil.outDataConverter(fileDto.getFileName())%>" title='<%=StringUtil.outDataConverter(fileDto.getFileName())%>' onClick='setFilePath(this.value)'></TD>
				</TR>
<%} %> 				
			</table>
		</td>
	</tr>
</form>
</table>
<SCRIPT LANGUAGE="JavaScript">

var f = top.document.WCM;
f.pDirCnt.value="<%=dirCnt%>";
f.pFileCnt.value="<%=fileCnt%>";

f.pServerPath.value="<%=StringUtil.outDataConverter(pPathDir)%>";
//f.pSelectFile.value="";

function radioDisabled(val){
	var f = document.WCM;
	var len = f.pFilePath.length;
	if(len >0){
		for(i=0;i<len;i++){
			f.pFilePath[i].disabled=val;
		}
	}else{
		f.pFilePath.disabled=val;
	}
}
if(<%=fileCnt%> > 0)
{
	if(top.document.WCM.pContentsType.value == 'C') 
		radioDisabled(true);
	else 
		radioDisabled(false);
}
function setFilePath(path){
	var f = top.document.WCM;
	f.pSelectFile.value=path;
}
</script>
<%@include file="../common/popup_footer.jsp" %>