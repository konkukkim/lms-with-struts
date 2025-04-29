<%@ page language="java"%> 
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="javax.sql.RowSet,com.edutrack.framework.persist.ListDTO,com.edutrack.common.dto.CodeParam"%> 
<%@include file="../common/header.jsp" %>
<script language="JavaScript"> 
function winopen(url, name, features) 
{
window.open(url, name, features)
} 
</script> 
  <table width="538" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td height="10"></td>
        </tr>
        <tr> 
          <td> 
            <!-- title imgae -->
            <img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/title/le_teach.gif" width="250" height="19"></td>
        </tr>
        <tr> 
          <td height="5"></td>
        </tr>
        <tr> 
          <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/line_01.gif"></td>
        </tr>
        <tr> 
          <td height="25">&nbsp;</td>
        </tr>
        <tr>
          <td><table width="538" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_01.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_01.gif" width="105" height="148" border="0"></a></td>
                <td width="39">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_02.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_02.gif" width="105" height="148" border="0"></a></td>
                <td width="39">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_03.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_03.gif" width="105" height="148" border="0"></a></td>
                <td width="35">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_04.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_04.gif" width="105" height="148" border="0"></a></td>
              </tr>
              <tr> 
                <td width="105" height="5"></td>
                <td width="39" height="5"></td>
                <td width="105" height="5"></td>
                <td width="39" height="5"></td>
                <td width="105" height="5"></td>
                <td width="35" height="5"></td>
                <td width="105" height="5"></td>
              </tr>
              <tr align="center"> 
                <td width="105">하수처리장<br>
                  운영실무 과정</td>
                <td width="39">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
                <td width="39">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
                <td width="35">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="25">&nbsp;</td>
        </tr>
        <tr> 
          <td height="2" background="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/line_dot.gif"></td>
        </tr>
        <tr> 
          <td height="25">&nbsp;</td>
        </tr>
        <tr> 
          <td><table width="538" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_05.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_05.gif" width="105" height="148" border="0"></a></td>
                <td width="39">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_06.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_06.gif" width="105" height="148" border="0"></a></td>
                <td width="39">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_07.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_07.gif" width="105" height="148" border="0"></a></td>
                <td width="35">&nbsp;</td>
                <td width="105"><a href="JavaScript:winopen('/html/lecture_teach_08.html', '', 'width=514, height=454, scrollbars=yes');"><img src="<%=CONTEXTPATH%>/img/<%=SYSTEMCODE%>/common/le_teach_08.gif" width="105" height="148" border="0"></a></td>
              </tr>
              <tr> 
                <td width="105" height="5"></td>
                <td width="39" height="5"></td>
                <td width="105" height="5"></td>
                <td width="39" height="5"></td>
                <td width="105" height="5"></td>
                <td width="35" height="5"></td>
                <td width="105" height="5"></td>
              </tr>
              <tr align="center"> 
                <td width="105">하수처리<br>기술 과정
                  </td>
                <td width="39">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
                <td width="39">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
                <td width="35">&nbsp;</td>
                <td width="105">하수처리<br>기술 과정
                  </td>
              </tr>
            </table></td>
        </tr>
        <tr> 
          <td height="50">&nbsp;</td>
        </tr>
      </table> 
      <!-- contents end -->
<%@include file="../common/footer.jsp" %>