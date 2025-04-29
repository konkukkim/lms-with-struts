<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@include file="../common/popup_header.jsp" %>
<%
	String pSERVER  = (String)model.get("pSERVER");
	String os_type  = (String)model.get("os_type");
	String year  = (String)model.get("year");
	String term  = (String)model.get("term");
	String pCourseId  = (String)model.get("pCourseId");

	String serImg = "ftp_img08.gif";
	if(pSERVER.equals("0")) serImg = "ftp_img03.gif";
	
	if(os_type.equals("window")) os_type="0";
%>
<script language="javascript">
		function CreateLocalDirectory() {
			EduWebTree.CreateLocalDirectory() ;
		}
		function CreateRemoteDirectory() {
			EduWebTree.CreateRemoteDirectory() ;
		}
		function FtpConnect() {
			EduWebTree.FtpConnect() ;
		}
</script>
<center>
<table width="100%" border="0" cellspacing="1" cellpadding="0" bgcolor="#B7B6B6">
	<tr>
		<td height="24" width="50%" bgcolor="#FFFFFF">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#EAE8E6">
				<tr align="center">
					<td height="23" width="50%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img01.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img02.gif" width="115" height="23" ></td>
					<td height="23" width="50%" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img01.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/<%=serImg%>" width="123" height="23" ></td>
				</tr>
				<tr>
					<td colspan="2" height="5" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img04.gif"></td>
				</tr>
				<tr>
					<td colspan="2">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td rowspan="3" width="10" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img06.gif">&nbsp;</td>
								<td height="30">
									<table width="100%" border="0" cellspacing="0" cellpadding="0" align=center>
										<tr>
											<td width="50%" height="30"><a href ="javascript:CreateLocalDirectory()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_bttn01.gif" width="82" height="21" border=0 ></a></td>
											<td width="25%" height="30"><a href ="javascript:CreateRemoteDirectory()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_bttn01.gif" width="82" height="21" hspace="4" border=0></a></td>
											<td width="25%" height="30" align=right><!--<a href ="javascript:FtpConnect()"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_bttn02.gif" hspace="4" border=0 ></a>--></td>
										</tr>
									</table>
								</td>
								<td rowspan="3" width="10" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img07.gif">&nbsp;</td>
							</tr>
							<tr>
								<td height="10" align=center>
									<object
									ID = "EduWebTree"
									classid="clsid:A88CEC2B-DEB0-47A7-87F0-DB7A3892D053"
									codebase="/WebFtp/AxEduWebTree.ocx#version=0,0,2,9"
									width="720" height="400">
									<param name="WindowWidth" value="720">
									<param name="WindowHeight" value="400">
									
									<param name="SystemCODE" value="<%=SYSTEMCODE%>">
									<param name="year" value="<%=year%>">
									<param name="Term" value="<%=term%>">
									<param name="CourseID" value="<%=pCourseId%>">
									
									<param name="OSType" value="<%=os_type%>">
									<param name="VODSever" value="<%=pSERVER%>">
									
									<param name="DirectoryName" value="data/contents/<%=SYSTEMCODE%>/<%=pCourseId%>">
									<param name="VDirectoryName" value="<%=pCourseId%>">
									<!--<param name="cmd" value="webFtpConfigTest">-->
									<param name="UpdateSrc" value="<%=CONTEXTPATH%>/ConfigSub.cmd?cmd=webFtpConfigTest">
									
									<param name="VodName" value="asf#wmv#wma">
									<param name="VodUse" value="1">
									</object>
								</td>
							</tr>
							<tr>
								<td>&nbsp;</td>
							</tr>									
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2" height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/webFtp/ftp_img05.gif"></td>
				</tr>
			 </table>
		</td>
	</tr>
	<tr height="30" align='center'>
		<td colspan=3><input type=button onClick="javascript:self.close()" value="종료"></td>
	</tr>
</table>
</center>
<%@include file="../common/popup_footer.jsp" %>