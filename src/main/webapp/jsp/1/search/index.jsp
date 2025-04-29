<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="/jsp/1/search/common/style.css">
<title>다센21 정보검색시스템 v4.1</title>

<script langunge=JavaScript>
function select_submit(mode)
{
	document.select.action='/servlet/SearchServlet?command=total.brief';
	//document.select.submit();
}
</script>
</head>

<body bgcolor=#FFFFFF marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>
<br>
<center>

<form method="post" name="select" onSubmit=select_submit("search")>
<input type=hidden name=pageNum value=5>

<table cellspacing=0 cellpadding=0 border=0 width=600>
  <tr>
    <td colspan=3 background=/jsp/1/search/img/box_t.gif height=75 valign=top>
      <table cellpadding=0 cellspacing=0 border=0 width=100%>
	<tr>
          <td><img src=/jsp/1/search/img/box_title1.gif border=0></td>
          <td align=right valign=bottom><img src=/jsp/1/search/img/box_title2.gif></td>
	</tr>
      </table>
    </td>
  </tr>

  <tr>
    <td background=/jsp/1/search/img/line2.gif width=3>
      <img src=/jsp/1/search/img/space.gif width=3 height=1>
    </td>
    <td align=center>
      <img src=/jsp/1/search/img/space.gif width=1 height=8><br>
      <img src=/jsp/1/search/img/line3.gif width=600 height=1><br>
      <table cellpadding=0 cellspacing=0 border=0 width=600>
        <tr>
          <td background=/jsp/1/search/img/line4.gif height=30 valign=middle class=txt4 align=right>
          </td>
        </tr>
      </table>
      <br>
      <table border=0 cellspacing=0 cellpadding=0 width=80%>
        <tr>
          <td class=txt1 width=100 height=33>데이터베이스</td>
          <td class=txt2>
		<input type=checkbox name=dbNoArr value=1 checked> KTSET 논문DB 1
		 &nbsp;&nbsp;&nbsp;&nbsp;
		<input type=checkbox name=dbNoArr value=2 checked> KTSET 논문DB 2
    	  </td>
        </tr>
        <tr>
          <td class=txt1 height=33>검색식</td>
          <td>&nbsp;<input type=text name=kwStr size=40 class=input1></td>
        </tr>
        
        <tr>
          <td align=center colspan=2 height=33 valign=top>
            <img src=/jsp/1/search/img/line1.gif width=100% height=2 vspace=10><br>
            <input type=image src=/jsp/1/search/img/but_search.gif onClick=select_submit("search"); border=0 hspace=10 alt='검색'>
            <a href=javascript:document.select.reset()><img src=/jsp/1/search/img/but_reset.gif border=0 hspace=10 alt='다시쓰기'></a>
          </td>
        </tr>
      </table>
      <br>
    </td>
    <td background=/jsp/1/search/img/line2.gif width=3>
      <img src=/jsp/1/search/img/space.gif width=3 height=1>
    </td>
  </tr>

  <tr>
    <td colspan=3>
      <table cellpadding=0 cellspacing=0 border=0 width=100%>
        <tr>
          <td width=14><img src=/jsp/1/search/img/box_lb.gif></td>
          <td background=/jsp/1/search/img/box_b.gif align=right>&nbsp;</td>
          <td align=right width=14><img src=/jsp/1/search/img/box_rb.gif></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<img src=/jsp/1/search/img/space.gif width=1 height=5><br>
<span class=copy>Copyright (c) 1998-2004 WOORIN Information. All rights Reserved. </span>
<br><br>
</center>
</body>
</html>
