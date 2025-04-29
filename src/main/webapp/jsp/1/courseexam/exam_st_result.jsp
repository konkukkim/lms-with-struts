<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamInfoDTO,com.edutrack.courseexam.dto.ExamAnswerDTO,com.edutrack.courseexam.dto.ExamContentsDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
    String pCourseId        = (String)model.get("pCourseId");
    String pExamId          = (String)model.get("pExamId");
    String pUserId          = (String)model.get("pUserId");
    ArrayList contentsList      = (ArrayList)model.get("contentsList");
    ExamAnswerDTO answerInfo = (ExamAnswerDTO)model.get("answerInfo");
    ExamInfoDTO examInfo = (ExamInfoDTO)model.get("examInfo");
    String[] answer = StringUtil.split(answerInfo.getAnswer(),CommonUtil.DELI);
    String[] inputAnswer = StringUtil.split(answerInfo.getAnswerScore(),CommonUtil.DELI);
    ExamContentsDTO cI = null;
    String pExamType = "";
    String pFeedback = StringUtil.nvl(answerInfo.getFeedBack());
    String[] width = new String[]{"200","400"};
    String LineBgColor = "#40B389";
%>
<script language="javascript">
    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>
	<table width="100%" height="100%" border="0">
		<tr>
			<td class="pop_topleft"></td>
			<td class="pop_topbg"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet05.gif" align="absmiddle"><font class="pop_tit">시험결과</font></td>
			<td class="pop_topright"></td>
		</tr>
		<tr>
			<td class="pop_midleft"></td>
			<td valign="top" class="pop_form">
				<!-- 내용 -->
				<table width="90%" height="100%" align="center">
					<tr height="97%">
						<td align="left" valign="top">
							<table width="100%" border="0" cellspacing="0" cellpadding="0">
								<tr class="s_tab01">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td class="s_tab_view02" align="center" colspan="2">시험명 : <font class="c_text02"><b>[<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%>]<%=examInfo.getSubject()%></b></font></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
<%	for (int i =0; i < contentsList.size(); i++) {
		cI = (ExamContentsDTO)contentsList.get(i);
		pExamType = cI.getExamType();	%>
								<tr>
									<td class="s_tab_view01" width="20%"><%=cI.getExamOrder()%>번 문제</td>
									<td class="s_tab_view02" width="80%">(<%=CommonUtil.getContentsTypeName(cI.getExamType())%>)&nbsp;<%=cI.getScore()%>점</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view03" colspan="2">&nbsp;<%=StringUtil.ReplaceAll(cI.getContents(),"&quot;","\"")%><br>
<%		if (!cI.getRfileName().equals("")) {	%>
										<a href="javascript:fileDownload('<%=cI.getRfileName()%>','<%=cI.getSfileName()%>','<%=cI.getFilePath()%>','<%=cI.getFileSize()%>');">관련 첨부파일 Download</a>
<% 		}	%>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
<% 		if (!pExamType.equals("K")) {	%>
								<tr>
									<td class="s_tab_view01">정답</td>
									<td class="s_tab_view02"><%=cI.getAnswer()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">본인이 풀이한 답</td>
									<td class="s_tab_view02"><%=answer[i]%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">정답 풀이</td>
									<td class="s_tab_view02"><%=cI.getComment()%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">배점</td>
									<td class="s_tab_view02"><%=inputAnswer[i]%>/&nbsp;<%=cI.getScore()%>점중</td>
								</tr>
<%		} else if (pExamType.equals("K")) {	%>
								<tr>
									<td class="s_tab_view01">보기</td>
									<td class="s_tab_view02">
<% 			if (!cI.getExample1().equals("")) {	%>
						1.&nbsp;<%=cI.getExample1()%><br>
<% 			}

			if(!cI.getExample2().equals("")){%>
						2.&nbsp;<%=cI.getExample2()%><br>
<% 			}

			if(!cI.getExample3().equals("")){%>
						3.&nbsp;<%=cI.getExample3()%><br>
<% 			}

			if(!cI.getExample4().equals("")){%>
						4.&nbsp;<%=cI.getExample4()%><br>
<% 			}

			if(!cI.getExample5().equals("")){%>
						5.&nbsp;<%=cI.getExample5()%><br>
<% 			}	%>
									</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">정답</td>
									<td class="s_tab_view02"><%=cI.getMultiAnswer().replaceAll("/",",")%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">본인이 풀이한 답</td>
									<td class="s_tab_view02"><%=answer[i].replaceAll("/",",")%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">정답 풀이</td>
									<td class="s_tab_view02"><%=StringUtil.getHtmlContents(cI.getComment())%></td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">배점</td>
									<td class="s_tab_view02"><%=inputAnswer[i]%>&nbsp;/&nbsp;<%=cI.getScore()%>점중</td>
								</tr>
<%		}	%>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
								<tr>
									<td height="8" colspan="2"></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>
<%
	}	%>

								<tr>
									<td class="s_tab_view01">채점 결과</td>
									<td class="s_tab_view02">총 <%=answerInfo.getTotalScore()%> 점</td>
								</tr>
								<tr>
									<td height="1" colspan="2" class="c_test_tablien01"> </td>
								</tr>
								<tr>
									<td class="s_tab_view01">피드백 내용</td>
									<td class="s_tab_view03"><%=pFeedback %></td>
								</tr>
								<tr class="s_tab05">
									<td colspan="2"></td>
								</tr>

							</table>
						</td>
					</tr>
				</table>
				<!-- // 내용 -->
			</td>
			<td class="pop_midright"></td>
		</tr>
		<tr height="6">
			<td class="pop_bottomleft"></td>
			<td class="pop_bottombg"></td>
			<td class="pop_bottomright"></td>
		</tr>
		<tr>
			<td colspan="3" bgcolor="F5F5F5" height="34" align="right" class="pop_btnclose"><a
			href="javascript:window.close();"><img
			src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_popclose.gif"></a></td>
		</tr>
	</table>
<%@include file="../common/popup_footer.jsp" %>