<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.curribbs.dto.CurriWorkReqResDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String 	pSeqNo		= (String)model.get("pSeqNo");
	String	pSystemCode = (String)model.get("systemCode");
	String	pUserType	= (String)model.get("pUserType");
	String  pWhere		= (String)model.get("pWhere");

	CurriWorkReqResDTO curriworkreqresDto = (CurriWorkReqResDTO)model.get("curriWorkReqResInfo");
%>
<script language="javascript">
	function goUpdate()
	{
		var f = document.Input;
		f.pMethodType.value = "Update";
		f.submit();
		
	}

	function doDelete()
	{
		if(confirm("해당 요청사항을 삭제하시겠습니까?")){
			var f = document.Input;
			f.pMethodType.value = "Delete";
			f.submit();
		}
	}
	
	function givePoint(seqNo){
		var f = document.Input;
		var chk	=	false;
		var point	=	0;
		for(var i=0;i < f.pPoint.length;i++){
			if(f.pPoint[i].checked){
				chk = true;
				point = f.pPoint[i].value;
				break;
			}
		}

		if(!chk){
			alert("평가구분을 선택하세요");
			// return false;
		}	else {
 			location.href="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=insertPoint&pPoint="+point+"&pSeqNo="+seqNo+"&pWhere=<%=pWhere%>";
		}

	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

	function goList() {
		document.location="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere=<%=pWhere%>&pMode=Lecture";
	}
</Script>

										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResWrite&pWhere=<%=pWhere%>">
<input type="hidden" name="pRegType" value="Regist">
<input type="hidden" name="pMethodType" value="Update">
<input type="hidden" name="pSeqNo" value="<%=pSeqNo%>">
											<tr class="s_tab01">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="92">교수선택</td>
												<td class="s_tab_view02" width="377" colspan="3"><%=CommonUtil.getUserName(pSystemCode, curriworkreqresDto.getProfId()) +"("+ curriworkreqresDto.getProfId() + ")"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청자</td>
												<td class="s_tab_view02"><%=CommonUtil.getUserName(pSystemCode, curriworkreqresDto.getStudentId()) +"("+ curriworkreqresDto.getStudentId() + ")"%></td>
												<td class="s_tab_view01">요청일자</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1, curriworkreqresDto.getReqRegDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청제목</td>
												<td class="s_tab_view02" colspan="3"><%=curriworkreqresDto.getReqSubject()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%
	if(!curriworkreqresDto.getRfileName1().equals("")){
%>
													<a href="javascript:fileDownload('<%=curriworkreqresDto.getRfileName1()%>','<%=curriworkreqresDto.getSfileName1()%>','<%=curriworkreqresDto.getFilePath1()%>','<%=curriworkreqresDto.getFileSize1()%>');"><%=curriworkreqresDto.getRfileName1()%></a>
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03" colspan="3"><%=StringUtil.ReplaceAll(curriworkreqresDto.getReqContents(),"&quot;","\"")%></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" height="15"></td>
											</tr>
<%
	if(!curriworkreqresDto.getResRegId().equals("") ){
		String 	pLastReplyUserId = "";
		String 	pLastReplyUserName = "";
		String	pLastReplyDate	=	"";

	if(curriworkreqresDto.getResModId().equals("")){
		pLastReplyUserId = curriworkreqresDto.getResRegId();
		pLastReplyUserName = CommonUtil.getUserName(pSystemCode, pLastReplyUserId);
		pLastReplyDate	=	DateTimeUtil.getDateType(1, curriworkreqresDto.getResRegDate());

	} else {
		pLastReplyUserId = curriworkreqresDto.getResModId();
		pLastReplyUserName = CommonUtil.getUserName(pSystemCode, pLastReplyUserId);
		pLastReplyDate	=	DateTimeUtil.getDateType(1, curriworkreqresDto.getResModDate());
	}
%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">최종답변자</td>
												<td class="s_tab_view02"><%=pLastReplyUserName + "( "+ pLastReplyUserId + " )"%></td>
												<td class="s_tab_view01">요청일자</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1, curriworkreqresDto.getReqRegDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">최종답변일</td>
												<td class="s_tab_view02" colspan="3"><%=pLastReplyDate%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02" colspan="3">
<%
	if(!curriworkreqresDto.getRfileName2().equals("")){
%>
													<a href="javascript:fileDownload('<%=curriworkreqresDto.getRfileName2()%>','<%=curriworkreqresDto.getSfileName2()%>','<%=curriworkreqresDto.getFilePath2()%>','<%=curriworkreqresDto.getFileSize2()%>');"><%=curriworkreqresDto.getRfileName2()%></a>
<%
	}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">답변내용</td>
												<td class="s_tab_view03" colspan="3"><%=StringUtil.ReplaceAll(curriworkreqresDto.getResContents(),"&quot;","\"")%></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td colspan="4" height="15"></td>
											</tr>
<%
	if( pUserType.equals("S") && curriworkreqresDto.getPoint() == 0){
%>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">평가하기</td>
												<td class="s_tab_view02" colspan="3">
&nbsp;&nbsp;매우만족 <input type="radio" name="pPoint" value="5" class="no">
&nbsp;&nbsp;만 족 <input type="radio" name="pPoint" value="4" class="no">
&nbsp;&nbsp;보 통 <input type="radio" name="pPoint" value="3" class="no">
&nbsp;&nbsp;불 만 족 <input type="radio" name="pPoint" value="2" class="no">
&nbsp;&nbsp;매우불만족 <input type="radio" name="pPoint" value="1" class="no">
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="4"></td>
											</tr>
<%
	} else if (( pUserType.equals("S") || pUserType.equals("M")) && curriworkreqresDto.getPoint() != 0) {
		String pointStr = "";

		switch (curriworkreqresDto.getPoint()) {
			case 1: pointStr = "<font color=red>매우불만족";break;
			case 2: pointStr = "<font color=red>불  만  족";break;
			case 3: pointStr = "<font color=orange>보       통";break;
			case 4: pointStr = "<font color=blue>만       족";break;
			case 5: pointStr = "<font color=blue>매우 만족";break;
		}
%>
											<tr>
												<td class="s_tab_view02" colspan="4" align="center">평가완료 : <%=pointStr%></td>
											</tr>
<%
		}
	}
%>
											<tr>
												<td class="s_list_btn" colspan="4" height="30" align=right>
<script language=javascript>Button3("목록", "goList()", "");</script>
<%
	if(pUserType.equals("S") && curriworkreqresDto.getResRegId().equals("")){
%>
&nbsp;<script language=javascript>Button3("수정", "goUpdate()", "");</script>
&nbsp;<script language=javascript>Button3("삭제", "doDelete()", "");</script>
<%
	}

	if(pUserType.equals("S") && !curriworkreqresDto.getResRegId().equals("") && curriworkreqresDto.getPoint() == 0){
%>
&nbsp;<script language=javascript>Button3("평가하기", "givePoint('<%=pSeqNo%>')", "");</script>
<%
	}
%>
												</td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>