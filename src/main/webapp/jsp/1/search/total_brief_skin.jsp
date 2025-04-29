<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page import="woorin.dasen.bean.*" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>
<%@ page import ="javax.sql.RowSet"%> 
<%@include file="../common/header.jsp" %>
<td colspan="4" width="100%" valign="top" align="left"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/search02.gif" width="901" height="51"></td>
               </tr>
              <!-- main 시작 -->
              <tr> 
                <td  valign="top" height="510" bgcolor="FEFEF6" colspan=4> 

<!-- 여기까지 LMS -->
<br>
<%
	boolean print_noresults = true;
%>

<TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD width=5><IMG 
                  src="/jsp/1/search/images/bg_subtit_left.gif"></TD>
                <TD class=txt_green style="PADDING-TOP: 5px" vAlign=top 
                background="/jsp/1/search/images/bg_subtit_m.gif">
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD style="PADDING-LEFT: 6px"><IMG height=9 
                        src="/jsp/1/search/images/bullet_black.gif" width=4> 
                        올드보이로 검색한 결과 </TD>
                      <TD align=right><STRONG><SELECT name=select> <OPTION 
                          value=01 selected>10개출력</OPTION> <OPTION 
                          value=02>20개출력</OPTION> <OPTION 
                        value=03>30개출력</OPTION></SELECT> 
                  </STRONG></TD></TR></TBODY></TABLE></TD>
                <TD width=5><IMG 
                  src="/jsp/1/search/images/bg_subtit_right.gif"></TD></TR>
              <TR>
                <TD colSpan=3 height=5></TD></TR></TBODY></TABLE>
            <TABLE class=box_gray cellSpacing=0 cellPadding=0 width=900 
border=0>
              <TBODY>
              <TR>
                <TD class=td_spec2 
                style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px">
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD class=td_spec2 
                      style="PADDING-RIGHT: 5px; PADDING-LEFT: 5px; PADDING-BOTTOM: 5px; PADDING-TOP: 5px"><A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#1">국내영화</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#2">국외영화</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#3">인명</A>:654 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#4">필름</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#5">비디오</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#6">시나리오</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#7">스틸</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#8">포스터</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#9">동영상파일</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#10">학위논문</A>:140건 
                        | <BR><A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#11">단행본</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#12">정기간행물</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#16">기사</A>:140건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#17">평론</A>:123건 
                        | <A 
                        href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#18">음반</A>:140건 
                      </TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=1></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  국내영화: 1434건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_01.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=122 bgColor=#f2f2f2><B>감독</B></TD>
                <TD align=middle width=193 bgColor=#f2f2f2><B>제작사</B></TD>
                <TD align=middle width=78 bgColor=#f2f2f2><B>제작년도</B></TD>
                <TD align=middle width=200 
                bgColor=#f2f2f2><STRONG>출현진</STRONG></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=2></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  국외영화: 1434건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_02.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=122 bgColor=#f2f2f2><B>감독</B></TD>
                <TD align=middle width=193 bgColor=#f2f2f2><B>제작사</B></TD>
                <TD align=middle width=78 bgColor=#f2f2f2><B>제작년도</B></TD>
                <TD align=middle width=200 
                bgColor=#f2f2f2><STRONG>출현진</STRONG></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/movie/md_basic.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/category_rlist_09.html">올드보이</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 10px">최민식, 유지태, 강혜정... </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=3></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  인명 : 134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_03.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=4 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>한글예명</B></TD>
                <TD width=200 bgColor=#f2f2f2><B>한글본명</B></TD>
                <TD align=middle width=200 bgColor=#f2f2f2><B>출생년도</B></TD>
                <TD align=middle width=200 bgColor=#f2f2f2><B>분야</B></TD></TR>
              <TR>
                <TD colSpan=4 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/actor/mm_basic.html">한국인</A></TD>
                <TD align=middle>한구긴</TD>
                <TD align=middle>1970</TD>
                <TD align=middle>배우/배우</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/actor/mm_basic.html">한국인</A></TD>
                <TD align=middle>한구긴</TD>
                <TD align=middle>1970</TD>
                <TD align=middle>스탭/시나리오</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/actor/mm_basic.html">한국인</A></TD>
                <TD align=middle>한구긴</TD>
                <TD align=middle>1970</TD>
                <TD align=middle>평론가/평론</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/actor/mm_basic.html">한국인</A></TD>
                <TD align=middle>한구긴</TD>
                <TD align=middle>1970</TD>
                <TD align=middle>교수/교수(학자)</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/actor/mm_basic.html">한국인</A></TD>
                <TD align=middle>한구긴</TD>
                <TD align=middle>1970</TD>
                <TD align=middle>애니매이터/작화</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=4></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  필름 : 134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_04.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=7 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=122 bgColor=#f2f2f2><B>감독</B></TD>
                <TD align=middle width=193 bgColor=#f2f2f2><B>제작사</B></TD>
                <TD align=middle width=80 bgColor=#f2f2f2><B>제작년도</B></TD>
                <TD align=middle width=80 
                bgColor=#f2f2f2><STRONG>규격</STRONG></TD>
                <TD align=middle width=80 
                bgColor=#f2f2f2><STRONG>필름구분</STRONG></TD>
                <TD align=middle bgColor=#f2f2f2><STRONG>색채</STRONG></TD></TR>
              <TR>
                <TD colSpan=7 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medfilm.html">한국단편애니메이션모음집</A></TD>
                <TD align=middle>박찬욱</TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>SD(1.33:1)</TD>
                <TD align=middle>DN</TD>
                <TD align=middle>컬러</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=7 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medfilm.html">올드보이</A></TD>
                <TD align=middle>박찬욱</TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>SD(1.33:1)</TD>
                <TD align=middle>MP</TD>
                <TD align=middle>흑백</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=7 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medfilm.html">올드보이</A></TD>
                <TD align=middle>박찬욱</TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>SD(1.33:1)</TD>
                <TD align=middle>ON</TD>
                <TD align=middle>컬러</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=7 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medfilm.html">올드보이</A></TD>
                <TD align=middle>박찬욱</TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>SD(1.33:1)</TD>
                <TD align=middle>RP</TD>
                <TD align=middle>혼합</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=7 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medfilm.html">올드보이</A></TD>
                <TD align=middle>박찬욱</TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>SD(1.33:1)</TD>
                <TD align=middle>MP</TD>
                <TD align=middle>컬러</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=7 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=7 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=5></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  비디오 : 134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_05.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=120 bgColor=#f2f2f2><B>심의번호</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>제조사</B></TD>
                <TD align=middle width=109 bgColor=#f2f2f2><B>제작년도</B></TD>
                <TD align=middle width=100 
                  bgColor=#f2f2f2><STRONG>상영시간</STRONG></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medvid.html">올드보이(영화보다 
                  재미있는 영화이야기)</A></TD>
                <TD align=middle>9906-9235</TD>
                <TD align=middle>신씨네</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>120</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medvid.html">올드보이</A></TD>
                <TD align=middle>9906-9235</TD>
                <TD align=middle>신씨네</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>120</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medvid.html">올드보이</A></TD>
                <TD align=middle>9906-9235</TD>
                <TD align=middle>신씨네</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>120</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medvid.html">올드보이</A></TD>
                <TD align=middle>9906-9235</TD>
                <TD align=middle>신씨네</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>120</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_medvid.html">올드보이</A></TD>
                <TD align=middle>9906-9235</TD>
                <TD align=middle>신씨네</TD>
                <TD align=middle>2003</TD>
                <TD align=middle>120</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=6></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  시나리오: 134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_06.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=4 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>시나리오명</B></TD>
                <TD width=150 bgColor=#f2f2f2><B>각본</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>각색</B></TD>
                <TD align=middle width=100 
                bgColor=#f2f2f2><STRONG>종류</STRONG></TD></TR>
              <TR>
                <TD colSpan=4 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">101번째 
                  프로포즈</A></TD>
                <TD align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle>오리지널</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">101번째 
                  프로포즈</A></TD>
                <TD align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle>심의대본</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">101번째 
                  프로포즈</A></TD>
                <TD align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle>copy</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">101번째 
                  프로포즈</A></TD>
                <TD align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle>심의대본</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">101번째 
                  프로포즈</A></TD>
                <TD align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docsce.html">김종성</A></TD>
                <TD align=middle>심의대본</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=4 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=7></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  <SPAN class=txt_green>스틸: </SPAN>52건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_07.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR>
              <TR bgColor=#e3ebee>
                <TD colSpan=2 height=2></TD></TR>
              <TR>
                <TD colSpan=2 height=25>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD 
                      style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px" 
                      align=middle height=24>
                        <TABLE cellSpacing=0 cellPadding=0 border=0>
                          <TBODY>
                          <TR>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_imgsti.html"><IMG 
                                src="/jsp/1/search/images/temp_004.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_imgsti.html"><IMG 
                                src="/jsp/1/search/images/temp_005.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_imgsti.html"><IMG 
                                src="/jsp/1/search/images/temp_006.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_imgsti.html"><IMG 
                                src="/jsp/1/search/images/temp_007.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_imgsti.html"><IMG 
                                src="/jsp/1/search/images/temp_008.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD></TR>
                          <TR>
                            <TD align=middle colSpan=5 
                        height=10></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=8></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  <SPAN class=txt_green>포스터: </SPAN>5건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_08.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR>
              <TR bgColor=#e3ebee>
                <TD colSpan=2 height=2></TD></TR>
              <TR>
                <TD colSpan=2 height=25>
                  <TABLE cellSpacing=0 cellPadding=0 width="100%" border=0>
                    <TBODY>
                    <TR>
                      <TD 
                      style="PADDING-RIGHT: 10px; PADDING-LEFT: 10px; PADDING-BOTTOM: 10px; PADDING-TOP: 10px" 
                      align=middle height=24>
                        <TABLE cellSpacing=0 cellPadding=0 border=0>
                          <TBODY>
                          <TR>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/com/comsearch_poster_view.html"><IMG 
                                src="/jsp/1/search/images/temp_002.jpg" 
                                width=100 
                            border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/com/comsearch_poster_view.html"><IMG 
                                src="/jsp/1/search/images/temp_003.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/com/comsearch_poster_view.html"><IMG 
                                src="/jsp/1/search/images/temp_003.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/com/comsearch_poster_view.html"><IMG 
                                src="/jsp/1/search/images/temp_003.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD>
                            <TD align=middle width=200>
                              <TABLE cellSpacing=0 cellPadding=0 border=0>
                                <TBODY>
                                <TR>
                                <TD class=box_gray 
                                style="PADDING-RIGHT: 3px; PADDING-LEFT: 3px; PADDING-BOTTOM: 3px; PADDING-TOP: 3px"><A 
                                href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/com/comsearch_poster_view.html"><IMG 
                                src="/jsp/1/search/images/temp_003.jpg" 
                                border=0></A></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A name=9></A><IMG 
                  height=9 src="/jsp/1/search/images/bullet_03.gif" width=4> 
                  동영상파일 : 134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right>예고편<IMG height=14 
                  src="/jsp/1/search/images/bt_mov_tra.gif" width=14 
                  align=absMiddle> 동영상 <IMG height=14 
                  src="/jsp/1/search/images/bt_mov_01.gif" width=14 
                  align=absMiddle> <IMG 
                  src="/jsp/1/search/images/bt_mov_02.gif" align=absMiddle> 
                  <IMG src="/jsp/1/search/images/bt_mov_03.gif" 
                  align=absMiddle> <STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_09.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=122 bgColor=#f2f2f2><B>감독</B></TD>
                <TD align=middle width=193 bgColor=#f2f2f2><B>제작사</B></TD>
                <TD align=middle width=109 bgColor=#f2f2f2><B>제작년도</B></TD>
                <TD align=middle width=100 
                bgColor=#f2f2f2><STRONG>동영상</STRONG></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25>올드보이</TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_01.gif" align=absMiddle 
                  border=0></A></TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25>올드보이</TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_01.gif" align=absMiddle 
                  border=0></A></TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25>올드보이</TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_tra.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_01.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_02.gif" align=absMiddle 
                  border=0></A></TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25>올드보이</TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_tra.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_01.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_02.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_03.gif" align=absMiddle 
                  border=0></A> </TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25>올드보이</TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#">박찬욱</A></TD>
                <TD align=middle>쇼이스트(주)/애그필름</TD>
                <TD align=middle>2003</TD>
                <TD style="PADDING-LEFT: 5px"><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_tra.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_01.gif" align=absMiddle 
                  border=0></A> <A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list.html#"><IMG 
                  src="/jsp/1/search/images/bt_mov_02.gif" align=absMiddle 
                  border=0></A></TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A 
                  name=10></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 학위논문: 134건 
                  </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_10.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>논문서명</B></TD>
                <TD width=200 bgColor=#f2f2f2><B>논문저자</B></TD>
                <TD align=middle width=80 bgColor=#f2f2f2><B>발행처</B></TD>
                <TD align=middle width=100 bgColor=#f2f2f2><B>발행년도</B></TD>
                <TD align=middle width=100 bgColor=#f2f2f2><B>발행구분</B></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docthe.html">한국영화포스터에 
                  대한 연구</A></TD>
                <TD align=middle>김경자</TD>
                <TD align=middle>단국대학교</TD>
                <TD align=middle>1983</TD>
                <TD align=middle>학위</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docthe.html">한국영화포스터에 
                  대한 연구</A></TD>
                <TD align=middle>김경자</TD>
                <TD align=middle>단국대학교</TD>
                <TD align=middle>1983</TD>
                <TD align=middle>학위</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docthe.html">한국영화포스터에 
                  대한 연구</A></TD>
                <TD align=middle>김경자</TD>
                <TD align=middle>단국대학교</TD>
                <TD align=middle>1983</TD>
                <TD align=middle>학위</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docthe.html">한국영화포스터에 
                  대한 연구</A></TD>
                <TD align=middle>김경자</TD>
                <TD align=middle>단국대학교</TD>
                <TD align=middle>1983</TD>
                <TD align=middle>학위</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docthe.html">한국영화포스터에 
                  대한 연구</A></TD>
                <TD align=middle>김경자</TD>
                <TD align=middle>단국대학교</TD>
                <TD align=middle>1983</TD>
                <TD align=middle>학위</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A 
                  name=11></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 단행본 : 134건 
                  </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_11.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=4 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>서명</B></TD>
                <TD width=200 bgColor=#f2f2f2><B>저자</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>발행년도</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>발행처</B></TD></TR>
              <TR>
                <TD colSpan=4 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docboo.html">영화, 
                  역사:영화와 새로운 과거의 만남</A></TD>
                <TD align=middle>로버트 A. 로젠스톤 (엮음)</TD>
                <TD align=middle>2002</TD>
                <TD align=middle>소나무</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docboo.html">영화, 
                  역사:영화와 새로운 과거의 만남</A></TD>
                <TD align=middle>로버트 A. 로젠스톤 (엮음)</TD>
                <TD align=middle>2002</TD>
                <TD align=middle>소나무</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docboo.html">영화, 
                  역사:영화와 새로운 과거의 만남</A></TD>
                <TD align=middle>로버트 A. 로젠스톤 (엮음)</TD>
                <TD align=middle>2002</TD>
                <TD align=middle>소나무</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docboo.html">영화, 
                  역사:영화와 새로운 과거의 만남</A></TD>
                <TD align=middle>로버트 A. 로젠스톤 (엮음)</TD>
                <TD align=middle>2002</TD>
                <TD align=middle>소나무</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docboo.html">영화, 
                  역사:영화와 새로운 과거의 만남</A></TD>
                <TD align=middle>로버트 A. 로젠스톤 (엮음)</TD>
                <TD align=middle>2002</TD>
                <TD align=middle>소나무</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=4 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A 
                  name=12></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 정기간행물 : 
                  134건 </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_12.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=150 bgColor=#f2f2f2><B>발행처</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>간행빈도</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>통권번호</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>키워드</B></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docper.html">Shall 
                  We 댄스?</A></TD>
                <TD align=middle>소나무</TD>
                <TD align=middle>격주</TD>
                <TD align=middle>B-3245</TD>
                <TD style="PADDING-LEFT: 5px" align=middle>Shall We 댄스?</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docper.html">Shall 
                  We 댄스?</A></TD>
                <TD align=middle>소나무</TD>
                <TD align=middle>격주</TD>
                <TD align=middle>B-3245</TD>
                <TD style="PADDING-LEFT: 5px" align=middle>Shall We 댄스?</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docper.html">Shall 
                  We 댄스?</A></TD>
                <TD align=middle>소나무</TD>
                <TD align=middle>격주</TD>
                <TD align=middle>B-3245</TD>
                <TD style="PADDING-LEFT: 5px" align=middle>Shall We 댄스?</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docper.html">Shall 
                  We 댄스?</A></TD>
                <TD align=middle>소나무</TD>
                <TD align=middle>격주</TD>
                <TD align=middle>B-3245</TD>
                <TD style="PADDING-LEFT: 5px" align=middle>Shall We 댄스?</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" align=middle height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_docper.html">Shall 
                  We 댄스?</A></TD>
                <TD align=middle>소나무</TD>
                <TD align=middle>격주</TD>
                <TD align=middle>B-3245</TD>
                <TD style="PADDING-LEFT: 5px" align=middle>Shall We 댄스?</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A id=16 
                  name=16></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 기사 : 134건 
                  </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_16.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=5 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>기사명</B></TD>
                <TD width=100 bgColor=#f2f2f2><STRONG>종류</STRONG></TD>
                <TD align=middle width=100 bgColor=#f2f2f2><B>구분</B></TD>
                <TD align=middle width=100 bgColor=#f2f2f2><B>발행처</B></TD>
                <TD align=middle width=100 
                bgColor=#f2f2f2><STRONG>분류</STRONG></TD></TR>
              <TR>
                <TD colSpan=5 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">Weekzine 
                  Free/김영진과 극장가기- '송환' 등 3편</A></TD>
                <TD align=middle>신문기사</TD>
                <TD align=middle>일간지</TD>
                <TD align=middle>한국일보</TD>
                <TD align=middle>극장가</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">Weekzine 
                  Free/김영진과 극장가기- '송환' 등 3편</A></TD>
                <TD align=middle>신문기사</TD>
                <TD align=middle>일간지</TD>
                <TD align=middle>한국일보</TD>
                <TD align=middle>극장가</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">Weekzine 
                  Free/김영진과 극장가기- '송환' 등 3편</A></TD>
                <TD align=middle>신문기사</TD>
                <TD align=middle>일간지</TD>
                <TD align=middle>한국일보</TD>
                <TD align=middle>극장가</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">Weekzine 
                  Free/김영진과 극장가기- '송환' 등 3편</A></TD>
                <TD align=middle>신문기사</TD>
                <TD align=middle>일간지</TD>
                <TD align=middle>한국일보</TD>
                <TD align=middle>극장가</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">Weekzine 
                  Free/김영진과 극장가기- '송환' 등 3편</A></TD>
                <TD align=middle>신문기사</TD>
                <TD align=middle>일간지</TD>
                <TD align=middle>한국일보</TD>
                <TD align=middle>극장가</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=5 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=5 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A id=16 
                  name=16></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 평론 : 134건 
                  </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_17.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=3 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>제목</B></TD>
                <TD width=150 bgColor=#f2f2f2><B>발행일</B></TD>
                <TD align=middle width=80 bgColor=#f2f2f2><B>게제지</B></TD></TR>
              <TR>
                <TD colSpan=3 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">홀쭉이와 
                  뚱뚱이-콤비 '스탠 로렐'과 '알리버 하디'</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">1971-11-28</A></TD>
                <TD align=middle>주간한국</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=3 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">홀쭉이와 
                  뚱뚱이-콤비 '스탠 로렐'과 '알리버 하디'</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">1971-11-28</A></TD>
                <TD align=middle>주간한국</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=3 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">홀쭉이와 
                  뚱뚱이-콤비 '스탠 로렐'과 '알리버 하디'</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">1971-11-28</A></TD>
                <TD align=middle>주간한국</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=3 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">홀쭉이와 
                  뚱뚱이-콤비 '스탠 로렐'과 '알리버 하디'</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">1971-11-28</A></TD>
                <TD align=middle>주간한국</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=3 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">홀쭉이와 
                  뚱뚱이-콤비 '스탠 로렐'과 '알리버 하디'</A></TD>
                <TD align=middle><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_absarteri.html">1971-11-28</A></TD>
                <TD align=middle>주간한국</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=3 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=3 height=2></TD></TR></TBODY></TABLE><BR>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR>
                <TD style="PADDING-LEFT: 10px" height=25><B><A id=17 
                  name=17></A><IMG height=9 
                  src="/jsp/1/search/images/bullet_03.gif" width=4> 음반 : 134건 
                  </B></TD>
                <TD style="PADDING-RIGHT: 10px" align=right><STRONG><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_list_18.html"><IMG 
                  src="/jsp/1/search/images/bt_more01.gif" 
                  border=0></A></STRONG></TD></TR></TBODY></TABLE>
            <TABLE cellSpacing=0 cellPadding=0 width=900 border=0>
              <TBODY>
              <TR bgColor=#5e8f9f>
                <TD colSpan=4 height=2></TD></TR>
              <TR align=middle>
                <TD align=middle bgColor=#f2f2f2 height=29><B>음반제목</B></TD>
                <TD width=200 bgColor=#f2f2f2><B>제작사</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>음반종류</B></TD>
                <TD align=middle width=150 bgColor=#f2f2f2><B>대분류</B></TD></TR>
              <TR>
                <TD colSpan=4 height=1></TD></TR><!--리스트 -->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_sourc.html">올드보이</A></TD>
                <TD align=middle>새한음반</TD>
                <TD align=middle>CD</TD>
                <TD align=middle>주제음악</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR><!--리스트 끝-->
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_sourc.html">올드보이</A></TD>
                <TD align=middle>새한음반</TD>
                <TD align=middle>CD</TD>
                <TD align=middle>주제음악</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_sourc.html">올드보이</A></TD>
                <TD align=middle>새한음반</TD>
                <TD align=middle>CD</TD>
                <TD align=middle>주제음악</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_sourc.html">올드보이</A></TD>
                <TD align=middle>새한음반</TD>
                <TD align=middle>CD</TD>
                <TD align=middle>주제음악</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR>
                <TD style="PADDING-LEFT: 5px" height=25><A 
                  href="http://kofa.koreafilm.or.kr:8000/1.시안확정-html/unions/unions_view_sourc.html">올드보이</A></TD>
                <TD align=middle>새한음반</TD>
                <TD align=middle>CD</TD>
                <TD align=middle>주제음악</TD></TR>
              <TR>
                <TD background="/jsp/1/search/images/dot_01.gif" colSpan=4 
                height=1></TD></TR>
              <TR bgColor=#cbdee4>
                <TD colSpan=4 height=2></TD></TR></TBODY></TABLE>

<!-- 여기부터 LMS -->
				</td>
				<td valign="top" width="1" bgcolor="FEFEF6" ><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/white.gif" width="1" height="26"><br>
                  <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/blank.gif" width="1" height="1"></td>
              </tr>
              <!-- main 끝 -->
<%@include file="../common/footer.jsp" %>