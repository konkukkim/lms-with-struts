<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import = "com.edutrack.curridate.dto.CurriDateDTO" %>
<%@include file="../common/header.jsp" %>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/score_grade/scoreGradeList.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ScoreGradeWork.js"></script>


										<table width="670" align="center">
										    <tr valign="middle">
            									<td colspan="13">
        										    <table width="100%" align="center">
        										        <tr>
                                                        	<td height=40>
                        									    개설년도/학기 <select name="pCurriYearTerm" onChange="javascript:autoScoreGradeReload()"><option value="0!0">공통<option value="2007!1">2007년도 1학기</select>
                        									</td>
                        									<td align="right" width="368">
                        									    <% if(CommonUtil.getAuthorCheck(request,  "C"))/* 권한체크 */  { %><Script>Button5("평가등급등록","goAdd()","")</Script><% } %>
                        									</td>
                    									</tr>
                    								</table>
                    							</td>
            								</tr>
            								<tr>
												<td colspan="13">
												<!-- 평가등급 입력 박스 끝-->
                                                    <div id="infoWrite" style="width:100%;display:none">
                                                    <table width="100%" align="center">
                                                        <form method="post" name="Input" onSubmit="return false">
                                                        <input type="hidden" name="pRegMode" value="">
            											<input type="hidden" name="pGradeCode" value="">
            											<input type="hidden" name="callBackMethod" value="parent.callbackAfterRegist()">
            											<tr class="s_tab01">
            												<td colspan="4"></td>
            											</tr>
            											<tr>
            												<td class="s_tab_view01" width=130>평가등급</td>
            												<td class="s_tab_view02" width=200><INPUT name="pGradeName" value="" dispName="평가등급" notNull maxLength=2 size=3></td>
            												<td class="s_tab_view01" width=130>평점</td>
            												<td class="s_tab_view02" width=200><INPUT name="pGradePoint" value="" dispName="평점" dataTye="number" notNull maxLength=5 size=5 lenCheck=5></td>
            											</tr>
            											<tr class="s_tab03">
            												<td colspan="4"></td>
            											</tr>
            											<tr>
            												<td class="s_tab_view01">적용점수</td>
            												<td class="s_tab_view02" colspan="3">
                                            					<input type=text name=pFrPoint value="" dispName="적용점수"  dataTye="number" notNull size="6" maxlength=6 lenCheck=6>
                                            					~ <input type=text name=pToPoint value="" dispName="적용점수"  dataTye="number" notNull size="6" maxlength=6 lenCheck=6>
                                            					&nbsp;&nbsp;<font class="s_text03">* 소수점은 1자리까지만 입력 가능합니다.</font>
            												</td>
            											</tr>
            											<tr class="s_tab03">
            												<td colspan="4"></td>
            											</tr>
            												<td class="s_tab_view01">적용비율</td>
            												<td class="s_tab_view02" colspan="3">
                                            					<input type=text name=pFrRate value="" dispName="적용비율"  dataTye="number" notNull size="6" maxlength=5 lenCheck=6>
                                            					~ <input type=text name=pToRate value="" dispName="적용비율" dataTye="number"  notNull size="6" maxlength=5 lenCheck=6>
                                            					&nbsp;&nbsp;<font class="s_text03">* 소수점은 1자리까지만 입력 가능합니다.</font>
            												</td>
            											</tr>
            											<tr class="s_tab03">
            												<td colspan="4"></td>
            											</tr>
            											<tr>
            												<td class="s_tab_view01">사용여부</td>
            												<td class="s_tab_view02" colspan="3">
                                            					<input type=radio name=pUseYn value="Y" class="no" checked ><font class="s_text01">사용</font>
                                            					<input type=radio name=pUseYn value="N" class="no" ><font class="s_text02">사용안함</font>
                                            				</td>
            											</tr>
            											<tr class="s_tab05">
            												<td colspan="4"></td>
            											</tr>
            											<tr>
            												<td class="s_list_btn" colspan="4" height="30" align="right">
                                                                <script language=javascript>Button3("확인", "submitCheck()", "");</script>
                                                                &nbsp;<script language=javascript>Button3("취소", "closeWin()", "");</script>
            												</td>
            											</tr>
                                                        </form>
            										</table>
                                                    </div>
                                                    <!-- 평가등급 입력 박스 끝-->
												</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="13"></td>
											</tr>
											<tr class="s_tab02">
												<td width="050">번호</td>
												<td class="s_tablien"></td>
												<td width="080">등급</td>
												<td class="s_tablien"></td>
												<td width="080">평점</td>
												<td class="s_tablien"></td>
												<td width="150">점수</td>
												<td class="s_tablien"></td>
												<td width="150">비율(%)</td>
												<td class="s_tablien"></td>
												<td width="080">사용여부</td>
												<td class="s_tablien"></td>
												<td width="080">삭제</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13"><div id="InfoList" style="width:100%;display:none"></div></td>
											</tr>
											<tr class="s_tab05">
												<td colspan="13"></td>
											</tr>
											<tr>
												<td colspan="13" height=25>&nbsp;&nbsp;<font class="s_text03">* 평점을 클릭하시면 수정하실 수 있습니다.</font></td>
											</tr>
										</table>
										<!-- // 게시판 리스트  끝 -->
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						<!-- // 본문 -->
<%@include file="../common/footer.jsp" %>

<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>');
</Script>
