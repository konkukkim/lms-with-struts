<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.currisub.dto.CurriSubDTO"%>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CurriSubWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/curri_sub/completeMypageList.js"></script>
<script language=javascript>
	function SampleWin(url){
		var frameWin = url;
		var winOption = "left=0,top=0,toolbar=0,location=0,directories=0,status=0,menubar=0,scrollbars=auto,resizable=yes,width=1024,height=768";
		lecWin = window.open(frameWin, "lecWin", winOption);
		lecWin.focus();
	}
</script>

										<!-- 내용 -->
										<!-- 정규과정 시작 -->
										<table width="670" align="center">
										<form>
										<input type=hidden name=pProperty1 value="<%=(String) model.get("pProperty1") %>">
											<!--tr>
												<td colspan="5" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle">강의종료과정</td>
											</tr-->
											<tr>
												<td colspan="5" class="s_tabtit">
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
											</tr>
										</table>
										<table width="670" align="center">
											<tr class="s_tab01">
												<td colspan="5"></td>
											</tr>
											<tr class="s_tab02">
												<td width="348">과정명</td>
												<td class="s_tablien"></td>
												<td width="160">신청기간</td>
												<td class="s_tablien"></td>
												<td width="160">수강기간</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="5"></td>
											</tr>
											<td colspan="11">
												<!-- 리스트 -->
											  	<div id="listDiv" style="width:100%;display:none"></div>
												<!-- 리스트 -->
											</td>
											<tr class="s_tab05">
												<td colspan="5"></td>
											</tr>
											<tr>
												<td colspan="5" height="30"></td>
											</tr>
										</table>
										<!-- // 정규과정 끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
								</form>
							</table>
						
<%@include file="../common/footer.jsp" %>

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>