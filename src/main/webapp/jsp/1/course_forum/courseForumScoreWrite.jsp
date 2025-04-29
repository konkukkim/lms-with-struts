<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO"%> 
<%@ page import ="com.edutrack.common.DateSetter,com.edutrack.common.dto.DateParam,com.edutrack.common.dto.CodeParam,com.edutrack.common.dto.EditorParam"%> 
<%@ page import ="com.edutrack.courseforum.dto.CourseForumInfoDTO"%> 
<%@include file="../common/course_header.jsp" %>
<%
	CommonUtil commUtil	= new CommonUtil();
   
   String pCourseId = (String)model.get("pCourseId");
   String pForumId  = (String)model.get("pForumId");
   int	pMySubForumId = 0;
     
   CourseForumInfoDTO courseForumInfo = (CourseForumInfoDTO)model.get("courseForumInfo"); 
   String pForumType        = courseForumInfo.getForumType();
   int 	 pParentForumId     = courseForumInfo.getParentForumId();
   //int 	 pSubTeamNo         = courseForumInfo.getSubTeamNo();
   String pSubTeamName      = courseForumInfo.getSubTeamName();
   String pSubject          = courseForumInfo.getSubject();
   String pContents         = courseForumInfo.getContents();
   String pRfileName        = courseForumInfo.getRfileName();
   String pSfileName        = courseForumInfo.getSfileName();
   String pFilePath         = courseForumInfo.getFilePath();
   String pFileSize         = courseForumInfo.getFileSize();
   int  	 pForumScore    = courseForumInfo.getForumScore();
   String pStartDate        = courseForumInfo.getStartDate();
   String pEndDate          = courseForumInfo.getEndDate();
   String pOpenYn           = courseForumInfo.getOpenYn();
   String sForumType        = pForumType.equals("A")? " 전체토론" : "조별토론";
   String sOpenYn           = pOpenYn.equals("Y")? " 공개 "	:	" 비공개 ";

%>
<Script Language="javascript">
   function goList(){
   	var f = document.Input;
      var pCourseId	= f.pCourseId.value; 
      var pForumId	= f.pForumId.value; 
      document.location = "<%=CONTEXTPATH%>/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }
   function goForum(pForumId){
   	var f = document.Input;
      var pCourseId	= f.pCourseId.value; 
      document.location = "<%=CONTEXTPATH%>/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pConnectYN=Y&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }


    // 점수 등록..
   function goScoreWrite(pGubun, pUserId){   
   	var f = document.Input;
   	
   	if(!validate(f)) return ;
   	
   	f.pOneUserId.value = pUserId;
   	f.pGubun.value = pGubun ;// 개별 or 일괄..
   	
   	f.target="hiddenFrame";
    f.action="<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumUserScoreRegist";
   	f.submit();
   }
   

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;        
       hiddenFrame.document.location = loc;
    }

</Script>


								<tr valign="top"> 
									<!-- classroom title -->
									<td height="34" width="346" class="c_stit_title" valign="bottom"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/classroom/class_centitle.gif" align="absmiddle">&nbsp;<font face='돋움' size="3"><b><%=CommonUtil.getPageTitle(request,"토론방")%></b></font></td>
									<!-- // classroom title -->
									<!-- history -->
									<td class="c_stit_history" valign="bottom" align="right" width="327"> 
									<!-- // history -->
<%
	String NAVIGATION = "";
	if (model != null) NAVIGATION = (String)model.get("site_navigation");
	if (PMODE.equals("Search")) NAVIGATION = "홈 > 통합검색";
	if (NAVIGATION != "") {
		out.println(NAVIGATION) ;
	} // end if
%>
									</td>
									<!-- // history -->
								</tr>
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
                						<!-- form start -->
                                        <form name="Input" method="post" action="<%=CONTEXTPATH%>/CourseForumUser.cmd?cmd=courseForumUserScoreRegist">
                                        <input type="hidden" name="pCourseId" value="<%=pCourseId%>">	
                                        <input type="hidden" name="pForumId" value="<%=pForumId%>">
                                        <input type="hidden" name="pParentForumId" value="<%=pParentForumId%>">
                                        <input type="hidden" name="pOneUserId" >
    									<input type="hidden" name="pGubun" value="">
    									<table width="670" align="center">
										<tr class="s_tab01">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">주제</td>
											<td class="s_tab_view02" width="570" colspan=3><%=StringUtil.getHtmlContents(pSubject)%></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">배점</td>
											<td class="s_tab_view02" width="235"><%=pForumScore%> 점</td>
											<td class="s_tab_view01" width="100">첨부파일</td>
											<td class="s_tab_view02" width="235">
											<%	if (!StringUtil.nvl(pRfileName).equals(""))	{	%>
                    						    <a href="javascript:fileDownload('<%=pRfileName %>','<%=pSfileName %>','<%=pFilePath %>','<%=pFileSize %>');"><%=pRfileName %></a></td>
                                            <%	}	%>
											</td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="100">토론종류</td>
											<td class="s_tab_view02" width="235"><%=sForumType %></td>
											<td class="s_tab_view01" width="100">공개여부</td>
											<td class="s_tab_view02" width="235"><%=sOpenYn %></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="120">토론기간</td>
											<td class="s_tab_view02" width="550" colspan=3><%=(DateTimeUtil.getDateType(1,pStartDate) + " - " + DateTimeUtil.getDateType(1,pEndDate)) %></td>
										</tr>
										<tr class="s_tab03">
											<td colspan="4"></td>
										</tr>
										<tr>
											<td class="s_tab_view01" width="120">토론개요</td>
											<td class="s_tab_view02" width="550" colspan=3><%=pContents%></td>
										</tr>
										<tr class="s_tab05">
											<td colspan="4"></td>
										</tr>
										</table>
										<br>	
										<!-- 내용 -->

                                        <% if(("T").equals(pForumType)){ %>
										<table width="670" align="center">
										<tr>
											<td class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"> <%=pSubTeamName %></td>
										</tr>
										</table>
										<% } %>
										<!-- 게시판 리스트 시작 -->
										<table width="670" align="center">
										<tr class="s_tab01">
											<td colspan="20"></td>
										</tr>
										<tr class="s_tab02">
											<td width="070">번호</td>
											<td class="s_tablien"></td>
											<td width="100" >이름</td>
											<td class="s_tablien"></td>
											<td width="110" >아이디</td>
											<td class="s_tablien"></td>
											<td width="100" >게시물 수</td>
											<td class="s_tablien"></td>
											<td width="100" >코멘트 수</td>
											<td class="s_tablien"></td>
											<td width="100" >점수</td>
											<td class="s_tablien"></td>
											<td width="090" >개별적용</td>
										</tr>
<%	ListDTO listObj = (ListDTO)model.get("userList"); 
 	int iTotCnt = listObj.getTotalItemCount();
 	int iCurPage = listObj.getCurrentPage();
 	int iDispLine = listObj.getListScale();	%> 
<%= listObj.getPageScript("f", "curPage", "goPage") %>
<%	int No = listObj.getStartPageNum();
	int	i	=	0;
	RowSet ForumUserList= listObj.getItemList();	
	String depthSpace = "";
   	
   	if (listObj.getItemCount() > 0) {
		while (ForumUserList.next()) {
			i++;
%>
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
										<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
											<td class=s_tab04_cen><%=No%></td>
											<td width=1 ></td>
											<td class=s_tab04_cen><%=commUtil.getUserName(SYSTEMCODE, StringUtil.nvl(ForumUserList.getString("user_id")))%></td>
											<td width=1 ></td>
											<td class=s_tab04_cen><%=StringUtil.nvl(ForumUserList.getString("user_id"))%></td>
											<td width=1 ></td>
											<td class=s_tab04_cen><%=ForumUserList.getInt("contents_cnt")%></td>
											<td width=1 ></td>
											<td class=s_tab04_cen><%=ForumUserList.getInt("comment_cnt")%></td>
											<td width=1 ></td>
											<td class=s_tab04_cen>
											    <input type="hidden" name="pUserId" value="<%=ForumUserList.getString("user_id")%>">
											    <input type="text"   name="pScore" size="3" style="text-align:right" value=<%=ForumUserList.getInt("score")%>  dispName="점수" notNull dataType="number" maxValue='<%=pForumScore%>'>
											</td>
											<td width=1 ></td>
											<td class=s_tab04_cen><Script Language=Javascript>Button5("개별적용","goScoreWrite('','<%=StringUtil.nvl(ForumUserList.getString("user_id"))%>')","")</Script></td>
									    </tr>
<%			No = No - 1;
		} 
	} else {	%>	
										<tr class="s_tab03">
											<td colspan="20"></td>
										</tr>
										<tr onMouseOver="this.className='tab_over'" onMouseOut="this.className='tab_out'">
											<td class='s_tab04_cen' colspan="20">※ 채점할 학습자가 없습니다. </td>
									    </tr>
<%	}%>
										<tr class="s_tab05">
											<td colspan="20"></td>
										</tr>
										</table>
										
										
                        			  	<table width="670" align="center">
										<tr>
                        			  	    <td class="s_list_btn" colspan="9" height="30" align="right">
                        			  	        <Script Language=Javascript>Button3('토론목록','goList()','')</Script>
                        			  	        <Script Language=Javascript>Button3("점수일괄적용","goScoreWrite('ALL','')","")</Script>
											</td>
										</tr>										
										</table>

									<!-- // 내용 -->
								</td>
							</tr>
							</form>
						</table>
					</td>
					<!-- // 본문 -->

				
					
<%@include file="../common/course_footer.jsp" %>