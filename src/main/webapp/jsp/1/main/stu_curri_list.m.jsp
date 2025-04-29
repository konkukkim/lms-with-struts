<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet"%>
<%@ page import ="com.edutrack.curritop.dto.CurriCategoryDTO"%>
<%@include file="../common/header.m.jsp" %>
<%
	String pGubun = StringUtil.nvl(request.getParameter("pGubun"),"1");

%>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/edutrack/main/stuCurriList.m.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/MainWork.js"></script>


 										<table width="670" align="center">
 											<tr>
												<td colspan="11" class="s_btn01" height="30" align="right" valign="middle"><font class="lg_name"> <%= UserBroker.getUserName(request)%>님</font> </td>
											</tr>
<%
	
	
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
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.m.jsp" %>
<Script Language="javascript">
	init('<%=SYSTEMCODE%>','<%=CONTEXTPATH%>','<%=pGubun %>');
</Script>
