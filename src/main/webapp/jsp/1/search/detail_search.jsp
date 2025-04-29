<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC_KR">
<link rel=stylesheet type=text/css href=type.css>
<title>다센21 정보검색시스템 v4.1</title>

<script langunge=JavaScript>
function select_submit(mode)
{
    if(mode == "search")
        document.select.action='/servlet/SearchServlet?command=total.brief';
	else if(mode == "scan")
		document.select.action='/servlet/SearchServlet?command=scan';
	document.select.submit();
}
</script>
</head>

<body bgcolor=#FFFFFF marginheight=0 marginwidth=0 leftmargin=0 topmargin=0>
<center>
<form method=post name=select>

<br>
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
            <img src=/jsp/1/search/img/icon3.gif hspace=5><a href=/index.jsp>일반검색</a> &nbsp;
          </td>
        </tr>
      </table>

      <table border=0 cellspacing=0 cellpadding=0 width=80%>
        <tr>
          <td width=100 height=30 class=txt1>데이터베이스</td>
          <td class=txt2><input type=checkbox name=dbNoArr value=1 checked>KTSET
		         &nbsp;&nbsp;&nbsp;&nbsp;
		  	 <input type=checkbox name=dbNoArr value=2> KTSET2
	  </td>
        </tr>
        <tr>
          <td height=30 class=txt1><b>검색식</b></td>
          <td class=txt2>
            <select name=field class=input1>
            <option value=전체>전체
            <option value=한글제목>한글제목
            <option value=한글저자>한글저자
            <option value=한글초록>한글초록
            <option value=한글제목+한글초록>한글제목+한글초록
            <option value=영문제목>영문제목
            <option value=영문저자>영문저자
            <option value=영문초록>영문초록
            <option value=영문제목+영문초록>영문제목+영문초록
            </select>&nbsp;
            <input type=text name=kwStr size=26 class=input1>
          </td>
        </tr>
        <tr>
          <td height=30 class=txt1>발행연도</td>
          <td class=txt2>
            <input type=text name=v_from size=4 class=input1> 부터
            <input type=text name=v_to size=4 class=input1> 까지
          </td>
        </tr>
        <tr>
          <td class=txt1 valign=top height=60>
            <img src=pic/space.gif width=1 height=5><br>검색옵션
          </td>
	  <td class=txt2>
	    <table cellpadding=0 cellspacing=0 border=0>
	      <tr>
		<td class=txt2 width=100 height=30>
	  	  <select name=searchMode class=input1>
		  <option value=0>검색방법
		  <option value=0>조건검색
		  <option value=1>가중치검색
		  </select>
		</td>
		<td class=txt2 width=100>
		  <select name=expMode class=input1>
		  <option value=0>검색어확장
		  <option value=0>확장안함
		  <option value=1>유사어확장
		  <option value=2>전방일치
		  <option value=3>후방일치
		  </select>
		</td>
		<td class=txt2>
		  <select name=sortFldArr class=input1>
		  <option value=0>결과정렬
		  <option value=1>제목
		  <option value=4>연도
		  </select>
		</td>
 	      </tr>
	      <tr>
		<td class=txt2 height=33>
		  <select name=sortMode class=input1>
		  <option value=0>정렬순서
		  <option value=0>올림차순
		  <option value=1>내림차순
		  </select>
		</td>
		<td class=txt2 colspan=2>
		  <select name=pageNum class=input1>
		  <option value=10>출력갯수
		  <option value=10>10
		  <option value=20>20
		  <option value=30>30
		  <option value=50>50
		  </select>
		</td>
              </tr>
	    </table>
          </td>
        </tr>
	<tr>
          <td class=txt1 height=33>색인어열람</td>
          <td class=txt2>
            <select name=scan_field class=input1>
            <option value=전체>전체
            <option value=한글제목>한글제목
            <option value=한글저자>한글저자
            <option value=한글초록>한글초록
            <option value=영문제목>영문제목
            <option value=영문저자>영문저자
            <option value=영문초록>영문초록
            </select>&nbsp;
            <input type=text name=scanStr size=26 class=input1></td>
        </tr>
        <tr>
          <td align=center colspan=2 height=33 valign=top>
            <img src=/jsp/1/search/img/line1.gif width=100% height=2 vspace=10><br>
            <input type=image src=/jsp/1/search/img/but_search.gif onClick=select_submit("search"); border=0 hspace=10 alt='검색'>
	    <input type=image src=/jsp/1/search/img/but_index.gif onClick=select_submit("scan"); border=0 hspace=10 alt='색인어열람'>
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
<span class=copy>Copyright (c) 1998-2003 WOORIN Information. All rights Reserved
. </span>
<br><br>
</center>
</body>
</html>
