<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.faq.dto.FaqContentsDTO,com.edutrack.faq.dto.FaqCategoryDTO"%>
<%@include file="../common/header.jsp" %>
<%
	String pCateId  = (String)model.get("pCateId");
%>
<Script Language="javascript">
<!--
	function goSearch() {
		var f =	document.search;

		//폼체크 후 이상 있으면
		if(!validate(f)) return;

		f.action="<%=CONTEXTPATH%>/Faq.cmd?cmd=homeFaqList&pMode=Help";
		f.submit();
	}

    function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc="<%=CONTEXTPATH%>/jsp/<%=SYSTEMCODE%>/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }


    /** 컨텐츠를 클릭했을경우 상세 내용을 보여준다.
     */
    function viewContents(id, len)
    {
        for(i=1 ; i<=len ; i++)
        {
            subjectNm  = eval('document.all.subject' +i+'');
            contentsNm = eval('document.all.contents'+i+'');

            subjectNm.bgColor  = "#FFFFFF";  //#D1DEEA
            contentsNm.style.display = "none";

        } //end for

        subjectNm  = eval('document.all.subject' +id+'');
        contentsNm = eval('document.all.contents'+id+'');

        subjectNm.bgColor  = "#FFFFFF";
        contentsNm.style.display = "";

    }

	function viewtext(textid)
	{
		if (textid.style.display == "none")
		{
			textid.style.display = "";
		}
		else
		{
			textid.style.display = "none";
		}
	}
//-->
</Script>

										<!-- 내용 -->
										<table width="670" align="center">
											<!-- faq 리스트 -->
											<tr>
												<td valign=top>
													<table width="650" height=69 border=0 align="center" class="faq_tbg">
														<tr>
															<td colspan="2" class="faq_ttop"></td>
														</tr>
														<tr>
															<td width="130" align="center"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/faq_img.gif"></td>
															<td width="520" align="center">
																<table width="490" height="49" border=0 cellpadding=3 cellspacing=0>
																	<tr>
<%
	ArrayList list = (ArrayList)model.get("faqcategoryList");
	FaqCategoryDTO category = null;
    String cateTitleName = "";

	for (int i=0; i < list.size(); i++) {
    	category = (FaqCategoryDTO)list.get(i);

    	if ((i+1) % 3 == 1) {
        	out.println("<tr> ");
    	}
%>
																		<td>
																			<img height=19 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet02.gif" width=13 align="absmiddle">
																			<a href="<%=CONTEXTPATH%>/Faq.cmd?cmd=homeFaqList&pCateId=<%=category.getCateId()%>&pMode=Help">
<%		if (pCateId.equals(String.valueOf(category.getCateId()))) {
        	out.println("<b>"+ category.getCateName() +"(" + category.getConCnt() +")</b>" );
        	cateTitleName = category.getCateName();
    	} else {
        	out.println(category.getCateName() + "(" +category.getConCnt() +")" );
    	}

    	out.println("</a></td>");

    	if ((i+1) % 3 == 0) {
        	out.println("</tr> ");
        	out.println("<tr> ") ;
    	}
    }	%>
																</table>
															</td>
														</tr>
														<tr>
															<td colspan="2" class="faq_tbottom"></td>
														</tr>
													</table>
												</td>
											</tr>
											<!-- // faq 리스트 -->
											<!-- faq 게시판 시작  -->
											<tr>
												<td colspan="2" height="15"></td>
											</tr>
<%	if (pCateId != null && !pCateId.equals("")) {	%>
											<tr>
												<td valign="top" width="670">
													<table width=100% align="center">
														<tr>
															<td colspan="2" class="s_tabtit"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blet01.gif" width="18" height="16" align="absmiddle"><%=cateTitleName %></td>
														</tr>
														<tr class="s_tab01">
															<td colspan="2"></td>
														</tr>
													</table>
<%	
    	FaqCategoryDTO categoryView = (FaqCategoryDTO)model.get("faqcategoryView");
    	int num = 0;
    	ArrayList list1 = (ArrayList)model.get("faqcontentsList");
    	FaqContentsDTO contents1 = null;

    	if(list1 != null) {
	    	for (int i=0; i < list1.size(); i++) {
	        	contents1 = (FaqContentsDTO)list1.get(i);
	        	num++;
%>
													<!-- 질문 1 -->
													<a name=linkqnatext<%=num%>></a>
													<table width="100%" align="center">
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr class="faq_tab01">
															<td class="s_tab02" width="38" height="30"><img height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_q.gif" width="18"></td>
															<td width="632" ><a href="javascript:viewtext(document.all.qnatext<%=num%>)"><%=num%>.&nbsp;<%=StringUtil.setMaxLength(StringUtil.getHtmlContents(contents1.getSubject()),50)%></a></td>
														</tr>
														<tr class="s_tab03">
															<td colspan="2"></td>
														</tr>
														<tr>
															<td colspan=2 height="10"></td>
														</tr>
													</table>
													<div id=qnatext<%=num%> style="display: none">
													<table cellspacing=0 cellpadding=0 width=100% align=center border=0>
														<tr>
															<td valign=top align=middle height=10 width=38><img height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_a.gif" width=18></td>
															<td  class="faq_tab02" valign=top width=632>
																<%=StringUtil.ReplaceAll(contents1.getContents(),"&quot;","\"")%>
															</td>
														</tr>
<%				if (!contents1.getRfileName().equals("") && !contents1.getFilePath().equals("")) {	%>
														<tr>
															<td colspan=2 align="right">
																첨부화일 : <b><a href="javascript:fileDownload('<%=contents1.getRfileName()%>','<%=contents1.getSfileName()%>','<%=contents1.getFilePath()%>','<%=contents1.getFileSize()%>');"><%=contents1.getRfileName()%></a>
															</td>
														</tr>
<%				}	//첨부파일 end	%>
													</table>
													</div>
<%
			}	//for end
		}	//if end
    	if (num == 0) { 	%>
													<div id=qnatext1 style="display: none">
													<table cellspacing=0 cellpadding=0 width=100% align=center border=0>
														<tr>
															<td valign=top align=middle height=10 width=38><img height=15 src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/bbs/icon_a.gif" width=18></td>
															<td  class="faq_tab02" valign=top width=632>등록된 자료가 없습니다.</td>
														</tr>
													</table>
													</div>
<%		}	%>

												</td>
											</tr>
											<!-- // faq 게시판 끝  -->
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
<%	}	%>
										</table>
										<!-- // 내용 -->
									</td>
								</tr>
							</table>
						
<%@include file="../common/footer.jsp" %>