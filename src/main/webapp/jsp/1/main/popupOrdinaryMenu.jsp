<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="java.lang.*,java.util.Map"%>
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.framework.util.StringUtil, com.edutrack.common.CommonUtil"%>
<%
	String CONTEXTPATH = request.getContextPath();
	String SYSTEMCODE = UserBroker.getSystemCode(request);
	Map		model		=	(Map)request.getAttribute("MODEL");
	String	pCurriCode	=	StringUtil.nvl(model.get("pCurriCode"));
	int		pCurriYear	=	StringUtil.nvl(model.get("pCurriYear"), 2007);
	int		pCurriTerm	=	StringUtil.nvl(model.get("pCurriTerm"), 1);

	String	pCurriName	=	CommonUtil.getCurriSubName(SYSTEMCODE, pCurriCode, pCurriYear, pCurriTerm);
%>
<HTML>
<HEAD>
<TITLE>   </TITLE>
<LINK href="<%=CONTEXTPATH%>/css/style.css" type=text/css rel=stylesheet>
<LINK href="<%=CONTEXTPATH%>/css/chtree.css" type=text/css rel=StyleSheet>
<SCRIPT src="<%=CONTEXTPATH%>/js/chtree.js" type=text/javascript></SCRIPT>
<script language=javascript src="<%=CONTEXTPATH%>/js/common.js"></script>
<script language=javascript src="<%=CONTEXTPATH%>/js/new_window.js"></script>
<Script Language=javascript>
	function doFileDown(filePath){
	   go_url = "<%=CONTEXTPATH%>/fileDownServlet?rFileName="+filePath+"&sFileName="+filePath+"&filePath=&fileSize=";
	   window.open(go_url,'down','width=5,height=5');
	}
</Script>
</HEAD>

<BODY>
	<table width="100%" border="0" cellpadding="0" cellspacing="0">
		<!--tr>
			<td>
				[<A href="javascript:ch_tree.open_all();">Open all</A>|<A href="javascript:ch_tree.close_all();">Close all</A>]
			</td>
		</tr  -->
		<tr>
			<td>
				<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td><br>
							<DIV class=chtree>
							<SCRIPT type="text/javascript">
								ch_tree = new ch_folder_tree('ch_tree', '/img/1/common','contents_main');
								ch_tree.add(0,-1,  '<font color=#000000><%=pCurriName%></font>', '');
<%
	RowSet	rs	=	(RowSet)model.get("ContentsList");
	String	new_courseId	=	"";
	String	back_courseId	=	"";
	String	contents_type	=	"";
	String	view_link		=	"";
	int		listCnt		=	0;
	int		initNo		=	0;
	int		menuNo		=	0;
	String textFilePath, asfFilePath, textFilePathLink ="";

	// DEUBGGED 2012-05-30  마지막 첨부파일 안나오는 버그 수정
	// 첨부 파일을 하위폴더 구조로 만든다
	// 글 목록(pid = parent id) 이 500 개이상 넘지 않는다고 가정해서,  501 부터 자식아이디(subID) 를 시작한다
	int  subID = 501;

	while(rs.next()) {

		new_courseId	=	StringUtil.nvl(rs.getString("course_id"));
		contents_type	=	StringUtil.nvl(rs.getString("contents_type"));
		
		textFilePathLink ="";

// 2012-05-29  DEBUGged 첫번째 첨부파일 안나오는 버그 수정 
		//if(!new_courseId.equals(back_courseId)) 
		if(false){
			// CANNOT REACH HERE (commented )
			listCnt	=	0;
			initNo	=	initNo + 30;
			listCnt++;
			view_link	=	CONTEXTPATH+"/Main.cmd?cmd=popupOrdinaryMain&pServerPath="+rs.getString("server_path");
%>
			ch_tree.add(<%=initNo%>,0,  "<%=rs.getString("course_name")%>", "");
			ch_tree.add(<%=listCnt%>,<%=initNo%>, "<%=rs.getString("contents_name")%>", "<%=view_link%>");
<%
		} else {
			listCnt++;
			view_link	=	CONTEXTPATH+"/Main.cmd?cmd=popupOrdinaryMain&pServerPath="+rs.getString("server_path");
			textFilePath = StringUtil.nvl(rs.getString("file_path")); 
			asfFilePath  = StringUtil.nvl(rs.getString("asffile_path")); 
			textFilePathLink  = (!("").equals(textFilePath)) ? "<a href=javascript:doFileDown('"+textFilePath+"')><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/tree/item.gif'  align=absmiddle alt=강의교재다운></a>" : "";
			textFilePathLink += (!("").equals(asfFilePath))  ? "<a href=javascript:doFileDown('"+asfFilePath+"') ><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/icon/tree/fmedia.gif'  align=absmiddle alt=동영상다운></a>" : "";
%>
			ch_tree.add(<%=listCnt%>,<%=initNo%>, "<%=rs.getString("contents_name")%>", "<%=view_link%>");
			
			<% if(!("").equals(textFilePathLink)) { %>
			ch_tree.add(<%=subID%>,<%=listCnt%>, "<%=textFilePathLink %>", "");
			<% } %>		
<%
		} 
%>

<%
		back_courseId	=	StringUtil.nvl(rs.getString("course_id"));
		view_link		=	"";
		subID ++;
	}
%>

								document.write(ch_tree);
							</script>
							</DIV>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
<script language=javascript>
	ch_tree.open_all();
</script>
</BODY>
</HTML>

