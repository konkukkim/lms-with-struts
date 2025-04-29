<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%>
<%@ page import ="com.edutrack.curristudent.dto.StudentDTO"%>
<%@ page import ="java.util.Vector"%>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/CompanyMasterCourseWork.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/companymaster/deptUsersList.js"></script>
							<table width="685" height="100%" align="right">
								<tr valign="top">
									<!-- sub title -->
									<td height="34" class="stit_blet"><div class="stit_title"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/mtit_23_01.gif" alt="타이틀" width="247" height="34"></div></td>
									<!-- // sub title -->
									<!-- history -->
									<td class="stit_history" valign="bottom" align="right" width="368">
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
											<tr>
												<td colspan="11" class="s_btn01">
													<table width="100%" align="center">
														<tr>
															<td align=left><font color="#9A8B7C">&nbsp;* 수강생 이름을 클릭하시면 해당 수강생의 수강정보를 보실 수 있습니다.</td>
                                                		    <td valign="middle" width="120" align="right">총회원수 : <b><%=(String)model.get("memberCnt") %> 명</b></font></td>
														</tr>
													</table>
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="11"></td>
											</tr>
											<tr class="s_tab02">
												<td width="40">번호</td>
												<td class="s_tablien"></td>
												<td width="227">이름 (아이디)</td>
												<td class="s_tablien"></td>
												<td width="100">가입일</td>
												<td class="s_tablien"></td>
												<td width="100">수강중</td>
												<td class="s_tablien"></td>
												<td width="100">수료</td>
												<td class="s_tablien"></td>
												<td width="100">접속수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="11"></td>
											</tr>
											<tr>
												<td colspan="11">
													<!-- 진행중인 과정 리스트 -->
                      								<div id="studentInfoList" style="width:100%;display:none"></div>
                      								<!-- 진행중인 과정 리스트 -->
												</td>
											</tr>
										    <tr class="s_tab05">
												<td colspan="11"></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						</td>
						<!-- // 본문 -->

<script language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</script>
<%@include file="../common/footer.jsp" %>
