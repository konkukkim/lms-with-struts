<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="WCM_header.jsp" %>
<%
	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");
	String pWorkType = (String)model.get("pWorkType");
	String pContentsType = (String)model.get("pContentsType");
	String pContentsId = (String)model.get("pContentsId");
	String pContentsOrder = (String)model.get("pContentsOrder");
	String pContentsName = (String)model.get("pContentsName");
	String pServerPath = (String)model.get("pServerPath");
	String pServerPathDir = (String)model.get("pServerPathDir");
	String pOldFileName = StringUtil.ReplaceAll(pServerPath,pServerPathDir+"/","");
	String pShowTime = (String)model.get("pShowTime");
	String pSpeContents = (String)model.get("pSpeContents");
	String ContentsRoot = (String)model.get("ContentsRoot");
	
	if(pWorkType.equals("ADD")) pContentsName="";
	
	
	String SubmitText = "등 록";
	if(pWorkType.equals("EDIT"))	SubmitText = "수 정";
	
	String CheckPosition = "장 추가";
	if(!pContentsName.equals("")) CheckPosition = pContentsName;
	
	String checkedC = "";	String checkedF = "";
	String disabledC = "";	String disabledF = "";
	
	if(pContentsType.equals("C")) checkedC = " checked ";
	else	checkedF = " checked ";
	
	if(pContentsType.equals("C") &&(pWorkType.equals("EDIT") || pContentsId.equals(""))) 
		disabledF=" disabled ";

	if(pContentsType.equals("F") && pWorkType.equals("EDIT"))
		disabledC=" disabled ";
	
	String menu = "";
	String cmenu = "";
	int sepIndex = 0;
	ArrayList list = (ArrayList)model.get("pFileList");
	ContentsFileDTO contentsFileDto = null;
%>
<script language="javascript">
	function chkForm(f)
	{
	 	if(!validate(f)) 
        	return false;
        else
            return true;
	}
    
</script>
<script language="javascript" src = "<%=CONTEXTPATH%>/js/WCM_list.js"></script>
<table border=1 cellspacing=1 cellpadding=0  bgcolor="#D6D3CE" width=100% >
<form name='WCM' method='post' action="<%=CONTEXTPATH%>/Contents.cmd?cmd=contentsRegist" onSubmit="return chkForm(this)">
<input type='hidden' name='pCourseId' value='<%=pCourseId%>'>
<input type='hidden' name='pCourseName' value='<%=pCourseName%>'>
<input type='hidden' name='pWorkType' value='<%=pWorkType%>'>
<input type='hidden' name='pContentsType' value='<%=pContentsType%>'>
<input type='hidden' name='pContentsId' value='<%=pContentsId%>'>
<input type='hidden' name='pContentsOrder' value='<%=pContentsOrder%>'>
<input type='hidden' name='pServerPath' value='<%=pServerPathDir%>'>
<input type='hidden' name='pSpeContents' value='<%=pSpeContents%>'>
	<tr>
		<td colspan=2>
			<table border=0 cellspacing=0 cellpadding=0 bgcolor="#D6D3CE" height=28 width=100%>
				<tr>
				<td WIDTH="13" HEIGHT="28"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/ml.gif" WIDTH="13" HEIGHT="28" BORDER="0"></TD>
				<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/ms.gif" width=650>
				<b>Web Contents Manager</b> - <%=pCourseName%>
				<td WIDTH="8" HEIGHT="28"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/mr.gif" WIDTH="8" HEIGHT="28" BORDER="0" ></TD>
				<td WIDTH="13" HEIGHT="28"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/ml.gif" WIDTH="13" HEIGHT="28" BORDER="0"></TD>
				<td background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/ms.gif"  valign='middle' align=center>
				<input type=submit value='<%=SubmitText%>' >
				<td WIDTH="8" HEIGHT="28"><IMG SRC="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/WCM/mr.gif" WIDTH="8" HEIGHT="28" BORDER="0" ></TD>
				</TR>
			</TABLE>
		</TD>
	</TR> 
	<tr>
		<td colspan=2>
			<TABLE BGCOLOR="#D6D3CE" width=100% BORDER="0" cellspacing=0 cellpadding=0>
				<TR>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 선택위치 : <%=CheckPosition%> </td>
							</tr>
						</table>
					</TD>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 장/단원 구분 : 
									<INPUT TYPE="radio" NAME="pNewContentsType" value="C" <%=checkedC%> <%=disabledC%> onClick="javascript:changeType(this.form,this.value);"> 장
									<INPUT TYPE="radio" NAME="pNewContentsType" value="F" <%=checkedF%> <%=disabledF%> onClick="javascript:changeType(this.form,this.value);"> 단원
								</td>
							</tr>
						</table>
					</TD>
				</TR>
<%
	if(pContentsType.equals("C") && pContentsId.equals("") && pWorkType.equals("ADD")){//-- 최초 장 등록시
%>				
				<TR>
					<TD  ALIGN="left" colspan=2>
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 목&nbsp;차 &nbsp;명 : <input type='text' name='pContentsName' size='50' class='inputtext' value="<%=pContentsName%>" dispName="목차명" notNull> </td>
							</tr>
						</table>
					</TD>
				</TR>
<%}%>
<%
	if(pContentsType.equals("C") && !pContentsId.equals("") && pWorkType.equals("EDIT")){//-- 장 수정시
%>				
				<TR>
					<TD  ALIGN="left" colspan=2>
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 목&nbsp;차 &nbsp;명 : <input type='text' name='pContentsName' size='50' class='inputtext' value="<%=pContentsName%>" dispName="목차명" notNull> </td>
							</tr>
						</table>
					</TD>
				</TR>
<%}%>
<%
if(!pContentsId.equals("") && pWorkType.equals("ADD")){//-- 최초 단원 등록시
%>
				<TR>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 목 차 명 : <input type='text' name='pContentsName' size='50' class='inputtext' value="<%=pContentsName%>" dispName="목차명" notNull> </td>
							</tr>
						</table>
					</TD>
					
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 수강기준시간 : <input type='text' name='pShowTime' class='inputtext' value='0' size=3 style="text-align:right;"  dispName="수강기준시간" dataType="number" notNull> 분</td>
							</tr>
						</table>
					</TD>
					
				</TR>
				<TR>
					<TD  ALIGN="left">
					</TD>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 선택파일 : <input type='text' name='pSelectFile' class='inputtext' value='' size=45 readonly dispName="선택된 파일" notNull></td>
							</tr>
						</table>
					</TD>
				</TR>
<%}%>
<%if(pContentsType.equals("F") && pWorkType.equals("EDIT") && !pContentsId.equals("")){%>

				<TR>
					<TD  ALIGN="left" >
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 목 차 명 : <input type='text' name='pContentsName' size='50' class='inputtext' value="<%=pContentsName%>" dispName="목차명" notNull> </td>
							</tr>
						</table>
					</TD>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 수강기준시간 : <input type='text' name='pShowTime' class='inputtext' value='<%=pShowTime%>' size=3  style="text-align:right;"  dispName="수강기준시간" dataType="number" notNull> 분</td>
							</tr>
						</table>
					</TD>
				</TR>
	<%if(pSpeContents.equals("N")){%>
				<TR>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 현재파일 : <input type='text' name='pOldFile' class='inputtext' value='<%=pOldFileName%>' size=45 readonly></td>
							</tr>
						</table>
					</TD>
					<TD  ALIGN="left">
						<table border=1 width=100% height=27 cellspacing=0 cellpadding=1>
							<tr>
								<td> ◈ 선택파일 : <input type='text' name='pSelectFile' class='inputtext' value='<%=pOldFileName%>' size=45 readonly dispName="선택된 파일" notNull></td>
							</tr>
						</table>
					</TD>
				</TR>
	<%}%>
<%}%>				
			</TABLE>
		</TD>
	</TR>
<%
	if(pContentsType.equals("C") &&(pWorkType.equals("EDIT") || pContentsId.equals(""))) {
%>	
	<tr height="360">
		<td valign=middle bgcolor=white width=100% align=center>
			장 이름을 입력해 주세요
		</td>
	</tr>
<%}else if(pContentsType.equals("F") &&(pSpeContents.equals("Y") && !pContentsId.equals(""))){%>
	<tr height="360">
		<td valign=middle bgcolor=white width=100% align=center>
			목차명과 수강 기준시간을 입력해 주세요
		</td>
	</tr>
<%}else{%>
	<tr height="360">
		<td valign=top bgcolor=white width=230 align=left>
			<table  border=0 cellspacing=0 cellpadding=0 align=left valign=top STYLE=" clear: left">
				<tr valign=top >
					<td height=100% valign=top align=left>
						<Script Language="JavaScript">
						net_yPos = 8;
						menu = gFldr("<font class=t2><%=pCourseId%></font>","<%=ContentsRoot%>");
	<%
	for(int i=0; i < list.size(); i++){
		menu = "";
		contentsFileDto = (ContentsFileDTO)list.get(i);
		menu = "menu_"+contentsFileDto.getDepth();
		sepIndex = menu.lastIndexOf("_");
	    cmenu = menu.substring(0,sepIndex);
	%>
						<%=menu%> = insFldr(<%=cmenu%>,gFldr('<%=StringUtil.outDataConverter(contentsFileDto.getFileName())%>','<%=contentsFileDto.getFileDir()+"/"+StringUtil.outDataConverter(contentsFileDto.getFileName())%>'));
	<% } %> 
						initializeDocument();
						</script>					
					</td>
				</tr>
			</table>
		</td>
		<td valign=top bgcolor=white width=493 align=left>
			<IFRAME ID="WCMRight" NAME="WCMRight" SRC="<%=CONTEXTPATH%>/Contents.cmd?cmd=WCMFrameRight&pCourseId=<%=pCourseId%>&pServerPath=<%=pServerPathDir%>" WIDTH="100%" HEIGHT="100%" frameborder="0" scrolling="Yes" noresize></IFRAME>
		</td>
	</tr>
	<tr>
		<td  colspan=2><input type=text name="pDirCnt" value="" class="tbox"> Directory 
		/ 
		<input type=text name="pFileCnt" value="" size=4 class="tbox"> File  
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<font color=brown><b>※ 폴더, 파일명에 공백또는 특수 문자를 사용하지 말아주세요</b></font> </TD>
	</TR>
<%}%>
</form>
</table>
<SCRIPT LANGUAGE="JavaScript">
	function radioDisabled(val){
		var f = document.WCMRight.WCM;
		var len = f.pFilePath.length;
		if(len >0){
			for(i=0;i<len;i++){
				f.pFilePath[i].disabled=val;
			}
		}else{
			f.pFilePath.disabled=val;
		}
	}
	function changeType(f,type){
	<% //if(pContentsType.equals("F") && pWorkType.equals("EDIT")) {%>
		f.pContentsType.value=type;
		//alert(f.pContentsType.value);
		if(type == 'C'){
			f.pSelectFile.value="";
			f.pServerPath.value="";
			f.pShowTime.value = 0;
			f.pShowTime.disabled=true;
			f.pSelectFile.disabled=true;
			radioDisabled(true);
		}else{
			f.pShowTime.disabled=false;
			f.pSelectFile.disabled=false;
			radioDisabled(false);
		}
		<% //} %>
	}
	
</script>
</body>
</html>