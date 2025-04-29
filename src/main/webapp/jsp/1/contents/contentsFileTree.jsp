<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.util.Map,java.util.ArrayList"%> 
<%@ page import ="com.edutrack.common.UserBroker,com.edutrack.curritop.dto.ContentsFileDTO,com.edutrack.framework.util.StringUtil"%>
<% String CONTEXTPATH = request.getContextPath(); %>
<%
    Map model = (Map)request.getAttribute("MODEL");
    String SYSTEMCODE = UserBroker.getSystemCode(request);

	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");
	String ContentsRoot = StringUtil.nvl((String)model.get("ContentsRoot"));
	String pServerPathDir = StringUtil.nvl((String)model.get("pServerPathDir"));

	ArrayList list = (ArrayList)model.get("pFileList");
	ContentsFileDTO contentsFileDto = null;

    String pFlag = StringUtil.nvl(request.getParameter("pFlag"),"1");
%>
<html>
<head>
<title>컨텐츠파일리스트</title>

<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/common_util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/util/tree.js"></script>
<script type="text/javascript">

	function setFileName(selectedFile){
		
		if(selectedFile != ""){
			
			if("<%=pFlag %>"=="1") {
			
				if(confirm("선택된 파일을 시작파일로 사용하시겠습니까?") == true){
					parent.changeServerPath(selectedFile);
					parent.popupbox1.close();
				}else return;
			
			} else if("<%=pFlag %>"=="3") {
					
				if(confirm("선택된 파일을 강의 동영상 다운로드용으로 사용하시겠습니까?") == true){
					parent.changeAsfFilePath(selectedFile);
					parent.popupbox1.close();
				}else return;
			
			}else if("<%=pFlag %>"=="2") {
					
				if(confirm("선택된 파일을 강의교재로 사용하시겠습니까?") == true){
					parent.changeTextFilePath(selectedFile);
					parent.popupbox1.close();
				}else return;
			
			}
			
		}else return;
	}
	
		
	function getFileList(){
		var serverPath = getTreeItemValue(contentsTree);
		if(serverPath != ""){
			WCMRight.document.location.href = "/Contents.cmd?cmd=WCMFrameRight&pPathDir="+serverPath;
		}else return;
		
	}	
</script>
</head>
<body topmargin=0 leftmargin=0 marginwidth=0 marginheight=0>
	<table border=0 cellspacing=0 cellpadding=0  bgcolor="#D6D3CE" width=100% >
		<tr>
			<td valign=top bgcolor='#F5F8ED' width='100' height='330' align=left >
				<table  border=0 cellspacing=0 cellpadding=0 align=left valign=top STYLE=" clear: left">
					<tr>
						<td valign=top width='100%' height='100%' id="treeBox1">
							<script language="javascript">
								var contentsTree = Tree("treeBox1", "<%=pCourseName%>", "", "red", "getFileList()", "", "opendepth=1,icontype=auto,foldertype=simple,rootdisplay=yes,ontitle=no");
							<%
								String parentTreeDepth="1";
								String fileIcon="folder.gif";
								String serverPath = "";
								String selectedItem = "";
								for(int i=0; i < list.size(); i++){
									contentsFileDto = (ContentsFileDTO)list.get(i);
									
									if(!"".equals(contentsFileDto.getFileExtImg()))
										fileIcon = contentsFileDto.getFileExtImg();
									else
										fileIcon="folder.gif";
									
									if(contentsFileDto.isThisDir()){
										serverPath = StringUtil.outDataConverter(contentsFileDto.getFileDir())+"/"+StringUtil.outDataConverter(contentsFileDto.getFileName());
									}else{
										serverPath = "";
									}
								/*  파일리스트까지 세팅할때
									if(contentsFileDto.isThisFile()){
										serverPath = StringUtil.outDataConverter(contentsFileDto.getFileDir())+"/"+StringUtil.outDataConverter(contentsFileDto.getFileName());
									}else{
										serverPath = "";
									}
								*/
									if(ContentsRoot.equals(serverPath)){
										selectedItem = "yes";
									}else{
										selectedItem = "";
									}

									if(StringUtil.split(contentsFileDto.getDepth(),"_").length == 1){
							%>		
										var treeItem_<%=contentsFileDto.getDepth()%> = addTreeItem(contentsTree, "<%=StringUtil.outDataConverter(contentsFileDto.getFileName())%>", "<%=serverPath%>", "", "<%=fileIcon%>","<%=selectedItem%>");		
							<%			
									}else{
										parentTreeDepth = contentsFileDto.getDepth().substring(0,contentsFileDto.getDepth().lastIndexOf("_"));
							%>		
										var treeItem_<%=contentsFileDto.getDepth()%> = addTreeItem(treeItem_<%=parentTreeDepth%>, "<%=StringUtil.outDataConverter(contentsFileDto.getFileName())%>", "<%=serverPath%>", "", "<%=fileIcon%>","<%=selectedItem%>");		
							<%			
									}
								}
							%>
							</script>
						</td>					
					</tr>
				</table>
			</td>
			
			<td valign=top border='0' bgcolor=white width='380' height='330' align=left>
				<IFRAME ID="WCMRight" NAME="WCMRight" SRC="<%=CONTEXTPATH%>/Contents.cmd?cmd=WCMFrameRight&pCourseId=<%=pCourseId%>&pServerPath=<%=pServerPathDir%>" WIDTH="100%" HEIGHT="100%" frameborder="0" scrolling="Auto" noresize></IFRAME>
			</td>			
		</tr>
	</table>

</body>
</html>