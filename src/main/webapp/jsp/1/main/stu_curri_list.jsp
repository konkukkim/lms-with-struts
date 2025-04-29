<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.curritop.dto.CurriCategoryDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String pGubun = StringUtil.nvl(request.getParameter("pGubun"),"1");

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/main/stuCurriList.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/MainWork.js"></script>


 										<table width="670" align="center">
<%	if(pGubun.equals("1") && CurriDateAction.getDateFlag("1", "cancel_start", "cancel_end", CommonUtil.getCurrentDate()).equals("Y")) {	%>
 											<tr>
												<td colspan="11" class="s_btn01" height="30" align="right" valign="middle"><script language=javascript>Button5("수강신청 정정하기", "EnrollEdit()", "");</script></td>
											</tr>
<%
	}
	
	
	ArrayList cateList= (ArrayList)model.get("cateList");
	CurriCategoryDTO curriCategoryDto = new CurriCategoryDTO();
	int	rowLength = StringUtil.nvl(model.get("rowLength"), 0);	
	if(rowLength == 0) {
%>
											<tr>
												<td><div id="ZeroInfoList" style="width:100%;display:none"></div></td>
											</tr>
<%	}

    for(int i=0;i<cateList.size();i++){

        curriCategoryDto = (CurriCategoryDTO)cateList.get(i);
%>
											<tr>
												<td><div id="InfoName<%=curriCategoryDto.getCateCode()%>" style="width:100%;display:none"></div></td>
											</tr>
											<tr>
                      							<td>
                      								<!-- 진행중인 과정 리스트 -->
                      								<div id="InfoList<%=curriCategoryDto.getCateCode() %>" style="width:100%;display:none"></div>
                      								<!-- 진행중인 과정 리스트 -->
                      							</td>
											</tr>
<%
    }//end for
%>
										</table>
										<!-- // 수강과정 끝 -->
										<br><br>
<%	if(pGubun.equals("1")) {	%>
										<!-- 수강신청 하지 않은 강좌 리스트 -->
										<table width="670" align="center">
											<tr>
												<td colspan="7" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><b>전체강의과목</b> (학점과 상관없이 자유 수강 가능한 과목)</td>
											</tr>
											<tr class="s_tab01">
												<td colspan="7"></td>
											</tr>
											<tr class="s_tab02">
												<td width="100">과목</td>
												<td class="s_tablien"></td>
												<td width="157">강좌</td>
												<td class="s_tablien"></td>
												<td width="330">강의주제</td>
												<td class="s_tablien"></td>
												<td width="80">교수</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="7"></td>
											</tr>
<%
		RowSet	rs	=	(RowSet)model.get("NoCurriList");
	    int No = 0;
		String	curriCode = "";
		String	curriYear = "";
		String	curriTerm = "";
		String	curriType = "";
		String	curriName = "";
		
		if(rs != null) {
			while(rs.next()) {
				curriCode = StringUtil.nvl(rs.getString("curri_code"));
				curriYear = StringUtil.nvl(rs.getString("curri_year"));
				curriTerm = StringUtil.nvl(rs.getString("curri_term"));
				curriType = StringUtil.nvl(rs.getString("curri_type"));
				curriName = StringUtil.nvl(rs.getString("curri_name"));

		        No++;
                if(No>1){					
%>

											<tr class="s_tab03">
												<td colspan="7"></td>
											</tr>
<%              } // end if  %>											
											<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
												<td class="s_tab04_cen"><%=StringUtil.nvl(rs.getString("cate_name"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(rs.getString("curri_name"))%></td>
												<td></td>
												<td class="s_tab04"><%=StringUtil.nvl(rs.getString("curri_info"))%>&nbsp;&nbsp;<a href="javascript:course_popup('<%=curriCode%>','<%=curriYear%>','<%=curriTerm%>')" class="lg_notice01"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button_img/btn_lecture01.gif" align="absmiddle"></a></td>
												<td></td>
												<td class="s_tab04_cen"><%=StringUtil.nvl(rs.getString("prof_name"))%></td>
											</tr>
<%	
			}
			rs.close();
		}
%>
											<tr class="s_tab05">
												<td colspan="7"></td>
											</tr>
										</table>
<%	}	%>
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>
<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pGubun %>');
</Script>