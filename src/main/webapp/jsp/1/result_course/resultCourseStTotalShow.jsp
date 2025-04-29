<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.resultcourse.dto.ResultScoreDTO,java.text.DecimalFormat,com.edutrack.currisub.dto.CurriContentsDTO"%>
<%@include file="../common/header.jsp" %>
<%
	//---- 소수점 형태 선언
	DecimalFormat decimal = new DecimalFormat("#.##");

	String pCourseId = (String)model.get("pCourseId");
	String pCourseName = (String)model.get("pCourseName");

	
%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ResultCourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/result_course/resultCourseStTotalShow.js"></script>
<script language="javascript">
	function change_code(courseId)
	{
		var selId = courseId.value;
		document.location = "<%=CONTEXTPATH%>/ResultCourse.cmd?cmd=resultCourseStShow&pCourseId="+selId;
	}
</script>


										<!-- 게시판 리스트 시작 -->
										<table width="670" border=0 cellspacing=0 align="center">
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td width="50%"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" vspace=0 align="absmiddle">
															    년도/학기
																<select name="pCurriYearTerm" onChange="javascript:autoReload()">
																<%
																//-- 수강생이 수강한 년도학기..
																RowSet rs = (RowSet)model.get("rs");

																while(rs.next()){
																%>
																<option value="<%=rs.getString("curri_year")%>!<%=rs.getString("hakgi_term")%>"><%=rs.getString("curri_year")%>년도 <%=rs.getString("hakgi_term")%>학기</option>
																<%
																}// end while
																rs.close();
																%>
																</select>
															</td>
															<td align=right width="50%" height=30></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 리스트 -->
												  	<div id="listDiv" style="width:100%;display:none"></div>
													<!-- 리스트 -->
												</td>
											</tr>
											<tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11" class="s_tab04_cen" colspan="50"> ※ 성적정정 신청은 강의실 개인면담 게시판을 통해서 할 수 있도록 한다. 평가 세부내용은 학기가 마감된 다음에 볼 수 있다. </td>
											</tr>
										</table>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>

<%@include file="../common/footer.jsp" %>

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
