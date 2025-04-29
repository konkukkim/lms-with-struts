<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="com.edutrack.resultcourse.dto.ResultScoreDTO"%>
<%@include file="../common/popup_header.jsp" %>
<%
	int tot_cnt = 0;
	ArrayList list = (ArrayList)model.get("deptresultList");
	ResultScoreDTO info1 = null;
	//참여자수
	for(int i=0; i < list.size(); i++){
		info1 = (ResultScoreDTO)list.get(i);
		tot_cnt += info1.getUserCnt();
	}
%>
<table width="370" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
  <tr>
    <td align="center" bgcolor="#FFFFFF"><table width="360" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_tit.gif" width="360" height="60"></td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td align="right">
            <!--Answer Total Start -->
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="12"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_box_01.gif" width="12" height="20"></td>
                <td width="120" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/score_box_02.gif"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/icon_arrow.gif" width="13" height="12">학습자 
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
          <td align="center">
            <!-- Contents Start -->
            <table width="330" border="0" cellspacing="0" cellpadding="0">
<%
	int     	img_w   	=   0;    // 이미지 넓이
	
	String	img_bar 	= 	"score_color_01.gif";
	String	img_icon	=	"score_icon_01.gif";

	ResultScoreDTO info = null;
	for(int i=0; i < list.size(); i++){
		info = (ResultScoreDTO)list.get(i);
		
		if((i+1)%2 == 0 ){
			img_bar	=	"score_color_02.gif";
			img_icon	=	"score_icon_02.gif";
		}else{
			img_bar	=	"score_color_01.gif";
			img_icon	=	"score_icon_01.gif";
		}
		
		if(info.getDeptScore() > 0) {
			img_w = info.getDeptScore() * 250 /100;
		}else{
			img_w = 1;
		}	
		 
%>            
              <tr> 
                <td><img src="./img/<%=SYSTEMCODE%>/popup/<%=img_icon%>" width="10" height="12"><strong><%=info.getDeptName()%></strong></td>
              </tr>
              <tr> 
                <td height="5"></td>
              </tr>
              <tr> 
                <td><img src="./img/<%=SYSTEMCODE%>/popup/<%=img_bar%>" width="<%=img_w%>" height="10">&nbsp;&nbsp;(<%=info.getDeptScore()%>점)</td>
              </tr>
              <tr> 
                <td height="10"></td>
              </tr>
    	<% if( i < list.size() - 1 ){ %>          
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