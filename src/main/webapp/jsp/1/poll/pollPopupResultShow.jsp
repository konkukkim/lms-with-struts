<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.poll.dto.InternetPollDTO"%> 
<%@include file="../common/popup_header.jsp" %>
<%
	Map data = (Map)request.getAttribute("MODEL"); 
	InternetPollDTO pollView = (InternetPollDTO)model.get("pollShow");  
	String userId = (String)model.get("userId");
	CONTEXTPATH = "/lms";

	int tot_cnt = pollView.getHitCnt();	//전체 응답수
%>
<table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#DBDBDB">
<form>
  <tr>
    <td align="center" bgcolor="#FFFFFF">
      <!--둘러싸고 있는 테이블과 안의 테이프 width값 차이 4px -->
      <table width="98%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <!-- (Height ) Fix--><td height="2"></td>
        </tr>
        <tr> 
          <td height="45" align="center" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/popup/com_bg.gif"><!-- Title --><font color="#FFFFFF" size="3"><strong>투표 결과 보기</strong></font></td>
        </tr>
		<tr> 
		  <td height="5"></td>
		</tr>
		<tr> 
		  <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/line_01.gif"></td>
		</tr>
		<tr> 
		  <td height="25">&nbsp;</td>
		</tr>
        <tr> 
          <td align="center">
            <!-- Contents Start-->
            <table width="95%" border="0" cellspacing="0" cellpadding="5">
              <tr> 
                <td><!--<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/calendar/score_icon_01.gif">투표내용 :--><b><%=StringUtil.getHtmlContents(pollView.getContents())%></b></td>
              </tr>
			  <tr> 
			    <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/line_dot.gif"></td>
			  </tr>
			  <tr>
			  	<td align="center" valign=top bgcolor=#ffffff style="padding-bottom: 5px; padding-left: 5px; padding-right: 5px; padding-top: 5px"> 
					<table border=0 cellpadding=0 cellspacing=0 height=95 width="100%">
			<%
	int     img_w   =   0;    // 이미지 넓이
	int     img_w2  =   0;    // 이미지 넓이2
	int     img_h   =   10;   // 이미지 높이
	double  pcnt    =   0;    // 비율
	String	img_bar = 	"poll_color_01";

	for(int i=0; i < pollView.getExampleCnt(); i++) {
		
		if (pollView.getHit()[i] > 0) {
			pcnt		=   Math.round( ((double)pollView.getHit()[i] / tot_cnt * 100) * 10    ) / 10.0;
			img_w		=   (pollView.getHit()[i] * 100) / tot_cnt;
			img_w2		=   101 - img_w;
		}
		else {
			img_w		=   1;
			img_w2		=   100;
		}

		if((i+1)%2==0){
			img_bar	=	"poll_color_02";
		}else{
			img_bar	=	"poll_color_01";
		}

%>
                          <tr> 
                            <td><%=i+1%>. <%=pollView.getExample()[i]%></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;&nbsp;&nbsp;&nbsp;<img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/<%=img_bar%>.gif" width=<%=img_w%> height=<%=img_h%>>&nbsp;&nbsp;<%=pollView.getHit()[i]%>표 
                              (<%=pcnt%>%)</td>
                          </tr>
                          <tr> 
                            <td height="5"></td>
                          </tr>
<%
	}
%>        
              <tr> 
                <td height="11"></td>
              </tr>
              <tr> 
                <td align="center">
                <a href ="javascript:self.close();"><img style="border:0;align:absmiddle" src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/button/b_co_comfirm.gif"></a>
				</td>
              </tr>
					</table>
				 </td>
			   </tr>
            </table>
            <!--Contents End -->
          </td>
        </tr>
        <tr> 
          <!-- (Height ) Fix--><td height="20">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
</table>
<!--<table cellpadding=5 cellpadding=1 cellspacing=0 width=96%>
<tr>
	<td align='right'>
		<input type="checkbox" name="Notice" OnClick="javascript:closeWin();"  style="border:0">오늘 하루 이 창 닫기 
	</td>
</tr>
</table>-->
</form>
<%@include file="../common/popup_footer.jsp" %>
