<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamInfoDTO" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamAnswerDTO" %>
<%@ page import ="com.edutrack.courseexam.dto.ExamContentsDTO"%>
<%@include file="../common/course_header.jsp" %>
<script language="javascript">
    function Score_Submit(){
       var f = document.Input;

	   if(!validate(f)) return;

       f.submit();
    }

    function goCancel(){
        var f = document.Input;

        var courseid = f.pCourseId.value;
        var examid = f.pExamId.value;

        var loc = "<%=CONTEXTPATH%>/ExamResult.cmd?cmd=examUserList&pCourseId="+courseid+"&pExamId="+examid;
        document.location = loc;
    }

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }
</script>
<%
    String pCourseId        = (String)model.get("pCourseId");
    String pExamId          = (String)model.get("pExamId");
    String pUserId          = (String)model.get("pUserId");
    ArrayList contentsList  = (ArrayList)model.get("contentsList");
    ExamAnswerDTO answerInfo= (ExamAnswerDTO)model.get("answerInfo");
    ExamInfoDTO examInfo    = (ExamInfoDTO)model.get("examInfo");
    String[] answer         = StringUtil.split(answerInfo.getAnswer(),CommonUtil.DELI);
    String[] inputAnswer    = StringUtil.split(answerInfo.getAnswerScore(),CommonUtil.DELI);
    ExamContentsDTO cI      = null;
    String pExamType        = "";
    String pFeedback        = StringUtil.nvl(answerInfo.getFeedBack());
    String[] width          = new String[]{"200","537"};
%>
									</td>
									<!-- // history -->
								</tr>

								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
<!--===== Form Start =========================================================-->
<form name="Input" method="post" action="<%=CONTEXTPATH%>/ExamResult.cmd?cmd=examResultRegist" >
<input type="hidden" name="pCourseId" value="<%=pCourseId%>">
<input type="hidden" name="pExamId"   value="<%=pExamId%>">
<input type="hidden" name="pUserId"   value="<%=pUserId%>">

											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01_01" colspan="2">시험명 : [<%=CommonUtil.getCourseName(SYSTEMCODE,pCourseId)%>]<%=examInfo.getSubject()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>

<%
	for (int i =0; i < contentsList.size(); i++) {
		cI = (ExamContentsDTO)contentsList.get(i);
		pExamType = cI.getExamType();
		inputAnswer[i]	=	StringUtil.nvl(inputAnswer[i], "0");

		if(i >= 1) {
%>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
<%		} %>
											<tr>
												<td class="s_tab_view01" width="135"><%=cI.getExamOrder()%>번 문제</td>
												<td class="s_tab_view02">(<%=CommonUtil.getContentsTypeName(cI.getExamType())%>) <%=cI.getScore()%>점</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view03" colspan="2">
													<%=StringUtil.ReplaceAll(cI.getContents(),"&quot;","\"")%><br><br>
<%
		if (!cI.getRfileName().equals("")) {
%>
													<a href="javascript:fileDownload('<%=cI.getRfileName()%>','<%=cI.getSfileName()%>','<%=cI.getFilePath()%>','<%=cI.getFileSize()%>');">관련 첨부파일 Download</a>
<%
		}
%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
<%
		if (!pExamType.equals("K")) {
%>
											<tr>
												<td class="s_tab_view01" width="135">정답</td>
												<td class="s_tab_view02"><%=cI.getAnswer()%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">본인이 풀이한답</td>
												<td class="s_tab_view02"><%=StringUtil.getHtmlContents(answer[i])%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">정답 풀이</td>
												<td class="s_tab_view02">
													<%=StringUtil.getHtmlContents(cI.getComment())%>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">배점</td>
												<td class="s_tab_view02">
													<input type="text" name="pScore<%=cI.getExamOrder()%>" value="<%=inputAnswer[i]%>" notNull dataType="number" minValue="0" maxValue="<%=cI.getScore()%>" dispName="<%=cI.getExamOrder()%>번 문제 배점"> / <%=cI.getScore()%>
												</td>
											</tr>
<%
		}
		else if (pExamType.equals("K")) {
%>
											<tr>
												<td class="s_tab_view01" width="135">보기</td>
												<td class="s_tab_view02">
													<% if(!cI.getExample1().equals("")){%>
					                                1.&nbsp;<%=cI.getExample1()%><br>
					                                <% } %>
					                                <% if(!cI.getExample2().equals("")){%>
					                                2.&nbsp;<%=cI.getExample2()%><br>
					                                <% } %>
					                                <% if(!cI.getExample3().equals("")){%>
					                                3.&nbsp;<%=cI.getExample3()%><br>
					                                <% } %>
					                                <% if(!cI.getExample4().equals("")){%>
					                                4.&nbsp;<%=cI.getExample4()%><br>
					                                <% } %>
					                                <% if(!cI.getExample5().equals("")){%>
					                                5.&nbsp;<%=cI.getExample5()%><br>
					                                <% } %>
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">정답</td>
												<td class="s_tab_view02"><%=cI.getMultiAnswer().replaceAll("/",",")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">본인이 풀이한답</td>
												<td class="s_tab_view02"><%=answer[i].replaceAll("/",",")%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">정답 풀이</td>
												<td class="s_tab_view02"><%=StringUtil.getHtmlContents(cI.getComment())%></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">배점</td>
												<td class="s_tab_view02">
													<input type="text" name="pScore<%=cI.getExamOrder()%>" value="<%=inputAnswer[i]%>" notNull dataType="number" minValue="0" maxValue="<%=cI.getScore()%>" dispName="<%=cI.getExamOrder()%>번 문제 배점"> / <%=cI.getScore()%>
												</td>
											</tr>
<%
		}
%>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr class="">
												<td colspan="2" height="15"></td>
											</tr>
<%
	}//end for
%>
<input type="hidden" name="pMaxOrder" value="<%=contentsList.size()%>">
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="135">피드백내용</td>
												<td class="s_tab_view03">
													<textarea name="pFeedback" cols="70" rows="7"><%=pFeedback %></textarea>
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
<script language=javascript>Button3("채점", "Score_Submit()", "");</script>
&nbsp;<script language=javascript>Button3("목록", "goCancel()", "");</script>
												</td>
											</tr>


</form>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->
<%@include file="../common/course_footer.jsp" %>