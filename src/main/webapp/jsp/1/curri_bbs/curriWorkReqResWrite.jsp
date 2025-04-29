<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet, com.edutrack.curribbs.dto.CurriWorkReqResDTO, com.edutrack.common.dto.EditorParam"%>
<%@include file="../common/course_header.jsp" %>
<%
	String pRegType = (String)model.get("regType");
	String pUserType = (String)model.get("userType");
	String pUserId = (String)model.get("userId");
	String pUserName = (String)model.get("userName");
	String pSeqNo  = (String)model.get("seqNo");
	String pMethodType  = (String)model.get("methodType");
	String pSystemCode  = (String)model.get("pSystemCode");
	String  pWhere	= (String)model.get("pWhere");

	CurriWorkReqResDTO curriworkreqresDto = null;
%>
<script language="javascript">
	function submitCheck()
	{
		var f = document.Input;

		if(window.VBN_prepareSubmit != null){
			if(!VBN_prepareSubmit())
				return false;
		}
<%
	if(pUserType.equals("S")){
%>
		if(f.pCurriCourseProfId[f.pCurriCourseProfId.selectedIndex].value == ""){
			alert("교수를 선택하세요");
			f.pCurriCourseProfId.focus();
			return false;
		}
<%
	}

	if(pRegType.equals("Regist")){
%>
		if(isEmpty(f.pSubject.value))
		{
			alert("제목을 입력하세요");
			f.pSubject.focus();
			return false;
		}
<%
	}
%>
		if(isEmpty(f.pContents.value))
		{
			alert("내용을 입력하세요");
			return false;
		}

		f.pFILE_NEW1_ori.value = f.pFILE_NEW1.value;

//		return true;
		f.submit();
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }

	function goList() {
		document.location="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=workReqResList&pWhere=<%=pWhere%>";
	}
</script>
							<tr valign="top">
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"개인면담")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327">
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!-- form start   onSubmit="return submitCheck()"  -->
<form name=Input method="post" action="<%=CONTEXTPATH%>/CurriWorkReqRes.cmd?cmd=curriWorkReqResRegist&pWhere=<%=pWhere%>" enctype="multipart/form-data">
<input type="hidden" name="pFILE_NEW1_ori" value="">
<input type="hidden" name="pRegType" value="<%=pRegType%>">
<input type="hidden" name="pMethodType" value="<%=pMethodType%>">
<input type="hidden" name="pSeqNo" value="<%=pSeqNo%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
<%
	String	colspans	=	"4";
	if(pRegType.equals("Regist")){	// 	신규등록.....학습자가 요청내용등록...

		String 	pSubject	=	"";
		String	pContents	=	"";
		String	pReqRegDate	=	"";
		String	pReqModDate	=	"";
		String	pRfileName1	=	"";
		String	pSfileName1	=	"";
		String	pFilePath1	=	"";
		String	pFileSize1	=	"";
		String	pRegDate	=	"";

		if(!pMethodType.equals("Insert")){
			curriworkreqresDto = (CurriWorkReqResDTO)model.get("workReqResInfo");
			pSubject 	= 	curriworkreqresDto.getReqSubject();
			pContents	=	curriworkreqresDto.getReqContents();
			pReqRegDate	=	DateTimeUtil.getDateType(1,curriworkreqresDto.getReqRegDate());
			pReqModDate	=	DateTimeUtil.getDateType(1,curriworkreqresDto.getReqModDate());
			pRfileName1	=	curriworkreqresDto.getRfileName1();
			pSfileName1	=	curriworkreqresDto.getSfileName1();
			pFilePath1	=	curriworkreqresDto.getFilePath1();
			pFileSize1	=	curriworkreqresDto.getFileSize1();
			pRegDate	=	DateTimeUtil.getDateType(1, curriworkreqresDto.getReqRegDate());

			colspans	=	"1";

		}

%>
											<tr>
												<td class="s_tab_view01" width="92">교수선택</td>
												<td class="s_tab_view02"><%=model.get("curriCourseProfList")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%	if (colspans.equals("1")) {	%>
											<tr>
												<td class="s_tab_view01">요청자</td>
												<td class="s_tab_view02"><%=pUserName+"("+pUserId+")"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청일자</td>
												<td class="s_tab_view02"><%=pRegDate %></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%	} else {	%>
											<tr>
												<td class="s_tab_view01">요청자</td>
												<td class="s_tab_view02"><%=pUserName+"("+pUserId+")"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%	}	%>
											<tr>
												<td class="s_tab_view01">요청제목</td>
												<td class="s_tab_view02"><input type="text" name="pSubject" value="<%=pSubject%>" size=70 maxlength="80"></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
<%
		if(!pRfileName1.equals("")){
%>
                                   					이전 파일 : <a href="javascript:fileDownload('<%=pRfileName1%>','<%=pSfileName1%>','<%=pFilePath1%>','<%=pFileSize1%>');"><%=pRfileName1%></a>
<%
		}
%>
													<input type=file name="pFILE_NEW1" size=60>
												</td>
											</tr>
<input type="hidden" name="oldRfileName" value="<%=pRfileName1%>">
<input type="hidden" name="oldSfileName" value="<%=pSfileName1%>">
<input type="hidden" name="oldFilePath"  value="<%=pFilePath1%>">
<input type="hidden" name="oldSfileSize" value="<%=pFileSize1%>">
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea cols=90 name="pContents" rows=10 dispName="내용" notNull><%=pContents%></textarea>
<%
	 	EditorParam editerParam = new EditorParam();
	 	editerParam.setShowFlag("true");
		editerParam.setWidth(520);
		editerParam.setHeight(300);
		editerParam.setEmailMode("true");
		editerParam.setToolBarHidden("attachFile,image,video,audio,flash");
	    out.print(CommonUtil.getEditorScript(editerParam));
%>
												</td>
											</tr>
<%
	}else{

		curriworkreqresDto = (CurriWorkReqResDTO)model.get("workReqResInfo");
		if(pMethodType.equals("Update")) colspans = "1";

		String 	pLastReplyUserId = "";
		String 	pLastReplyUserName = "";
		String	pLastReplyDate	=	"";
		String	pProfId		=	curriworkreqresDto.getProfId();
		String	pProfName	=	CommonUtil.getUserName(pSystemCode, pProfId);

		if(pMethodType.equals("Update")){
			if(curriworkreqresDto.getResModId().equals("")){
				pLastReplyUserId	=	curriworkreqresDto.getResRegId();
				pLastReplyUserName	=	CommonUtil.getUserName(pSystemCode, pLastReplyUserId);
				pLastReplyDate	=	DateTimeUtil.getDateType(1,curriworkreqresDto.getResRegDate());

			}else{
				pLastReplyUserId	=	curriworkreqresDto.getResModId();
				pLastReplyUserName	=	CommonUtil.getUserName(pSystemCode, pLastReplyUserId);
				pLastReplyDate	=	DateTimeUtil.getDateType(1,curriworkreqresDto.getResModDate());
			}
		}else{
			pLastReplyUserId	=	pUserId;
			pLastReplyUserName	=	pUserName;
		}
%>
											<tr>
												<td class="s_tab_view01" width="92">교수선택</td>
												<td class="s_tab_view02"><%=pProfName + "(" + pProfId + ")"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청자</td>
												<td class="s_tab_view02"><%=CommonUtil.getUserName(pSystemCode, curriworkreqresDto.getStudentId()) +"("+ curriworkreqresDto.getStudentId() + ")"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청일자</td>
												<td class="s_tab_view02"><%=DateTimeUtil.getDateType(1, curriworkreqresDto.getReqRegDate())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">요청제목</td>
												<td class="s_tab_view02"><%=curriworkreqresDto.getReqSubject()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
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
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<%=StringUtil.ReplaceAll(curriworkreqresDto.getReqContents(),"&quot;","\"")%>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td colspan="2" height="15"></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
<%
		if (colspans.equals("1")) {
%>
											<tr>
												<td class="s_tab_view01">최종답변자</td>
												<td class="s_tab_view02"><%=pLastReplyUserName + "( "+ pLastReplyUserId + " )"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">최종답변일</td>
												<td class="s_tab_view02"><%=pLastReplyUserName + "( "+ pLastReplyUserId + " )"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		} else {
%>
											<tr>
												<td class="s_tab_view01">최종답변자</td>
												<td class="s_tab_view02"><%=pLastReplyUserName + "( "+ pLastReplyUserId + " )"%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		}
%>
											<tr>
												<td class="s_tab_view01">첨부파일</td>
												<td class="s_tab_view02">
<%
		if(!curriworkreqresDto.getRfileName2().equals("")){
%>
                               						이전 파일 : <a href="javascript:fileDownload('<%=curriworkreqresDto.getRfileName2()%>','<%=curriworkreqresDto.getSfileName2()%>','<%=curriworkreqresDto.getFilePath2()%>','<%=curriworkreqresDto.getFileSize2()%>');"><%=curriworkreqresDto.getRfileName2()%></a>
<%
		}
%>
                   									<input type=file name="pFILE_NEW1" size=70>
												</td>
											</tr>
<input type="hidden" name="oldRfileName" value="<%=curriworkreqresDto.getRfileName2()%>">
<input type="hidden" name="oldSfileName" value="<%=curriworkreqresDto.getSfileName2()%>">
<input type="hidden" name="oldFilePath" value="<%=curriworkreqresDto.getFilePath2()%>">
<input type="hidden" name="oldSfileSize" value="<%=curriworkreqresDto.getFileSize2()%>">
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">내용</td>
												<td class="s_tab_view03">
													<textarea name="pContents" cols=70 rows=10><%=curriworkreqresDto.getResContents()%></textarea>

<%
	 	EditorParam editerParam = new EditorParam();
	 	editerParam.setShowFlag("true");
		editerParam.setWidth(520);
		editerParam.setHeight(300);
		editerParam.setEmailMode("true");
		editerParam.setToolBarHidden("attachFile,image,video,audio,flash");
	    out.print(CommonUtil.getEditorScript(editerParam));
 %>
												</td>
											</tr>
<%
	}
%>




											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>

											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("목록", "goList()", "");</script>
&nbsp;<script language=javascript>Button3("등록", "submitCheck()", "");</script>
												</td>
											</tr>
</form>
<!-- form -->
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(시작) -->
<script>if(window.VBN_connectVentureBrainNetwork != null) VBN_connectVentureBrainNetwork();</script>
<!-- (주)벤처브레인 WEAS 삽입 스크립트2(끝) -->
<%@include file="../common/course_footer.jsp" %>