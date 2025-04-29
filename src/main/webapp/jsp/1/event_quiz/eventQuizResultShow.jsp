<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.eventquiz.dto.EventQuizInfoDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	EventQuizInfoDTO infoView = (EventQuizInfoDTO)model.get("quizresultShow"); 
	int tot_cnt = 0 ;
	for(int i=0; i < infoView.getExampleCnt(); i++) {
		tot_cnt +=  infoView.getAnswerCnt()[i];
	}
%>
<table width="370" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
  <tr>
    <td align="center" bgcolor="#FFFFFF"><table width="360" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/quiz_tit.gif" width="360" height="60"></td>
        </tr>
		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="3" background="./img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr>
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="right">
            <!--Answer Total Start -->
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="12"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_box_01.gif" width="12" height="20"></td>
                <td width="120" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_box_02.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/icon_arrow.gif" width="13" height="12">응답자 
                  수 : <%=tot_cnt%> 명</td>
                <td width="12"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_box_03.gif" width="12" height="20"></td>
              </tr>
            </table>
            <!--Answer Total End -->
          </td>
        </tr>
        <tr> 
          <td height="15"></td>
        </tr>
        <tr> 
        		<td><strong>제목 : <%=infoView.getSubject()%></strong></td>
        </tr>
        <tr> 
        		<td height="5"></td>
        </tr>
        <tr> 
        		<td><%=StringUtil.getHtmlContents(infoView.getContents())%></td>
        </tr>
        <tr> 
        		<td height="5"></td>
        </tr>
        <tr> 
          <td align="center">
            <!-- Contents Start -->
            <table width="330" border="0" cellspacing="0" cellpadding="0">
<%
	int     	img_w   	=   0;    // 이미지 넓이
	int     	img_w2  	=   0;    // 이미지 넓이2
	int     	img_h   	=   10;   // 이미지 높이
	double  	pcnt    	=   0;    // 비율
	
	String	img_bar 	= 	"score_color_01.gif";
	String	img_icon	=	"score_icon_01.gif";

	for(int i=0; i < infoView.getExampleCnt(); i++) {
		
		if (infoView.getAnswerCnt()[i] > 0) {
			pcnt		=   Math.round( ((double)infoView.getAnswerCnt()[i] / tot_cnt * 100) * 10    ) / 10.0;
			img_w		=   (infoView.getAnswerCnt()[i] * 250) / tot_cnt;
			img_w2		=   101 - img_w;
		}
		else {
			pcnt		=	0;
			img_w		=  1;
			img_w2	=  250;
		}
		
		if( (i+1) % 2 == 0 ){
			img_bar	=	"score_color_02.gif";
			img_icon	=	"score_icon_02.gif";
		}else{
			img_bar	=	"score_color_01.gif";
			img_icon	=	"score_icon_01.gif";
		}
		
		 
%>            
              <tr> 
                <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/calendar/<%=img_icon%>" width="10" height="12"><strong><%=i+1%>. <%=infoView.getExample()[i]%></strong></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
              <tr> 
                <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/calendar/<%=img_bar%>" width=<%=img_w%> height=<%=img_h%>>&nbsp;&nbsp;(<%=infoView.getAnswerCnt()[i]%>명, <%=pcnt%>%)</td>
              </tr>
              <tr> 
                <td height="10"></td>
              </tr>
    	<% if( i < infoView.getExampleCnt() - 1 ){ %>          
              <tr> 
                <td height="1" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_dot.gif"></td>
              </tr>
              <tr> 
                <td height="15"></td>
              </tr>
              
		<%	}	%>              
<%
	}
%>              
             
            </table> 
            <!--Contents End -->
          </td>
        </tr>
        <tr> 
          <td height="20">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<table>
  <tr>
	<td height="30" align="center"><a href="javascript:window.close();"><img src="./img/<%=SYSTEMCODE%>/button/b_co_close.gif"></a></td>
  </tr>
</table>
<%@include file="../common/popup_footer.jsp" %>