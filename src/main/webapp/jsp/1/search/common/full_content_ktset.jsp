<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import="javaclientapi.*" %>
<%@ page errorPage="/jsp/1/search/error/error_jsp.jsp" %>

       <table width=620 border=0 cellspacing=2 cellpading=4 class='table1'>

<%
	JavaExternalAPI japi = new JavaExternalAPI();
	DS_Handle hptr = (DS_Handle) request.getAttribute("HPTR");
	ResultSet_Doc docInfo = null;
	ResultSet_Field fieldInfo = null;
    int ret;

	// 변수 선언
    String	AC, TI, AU, AF, AB, LA, JO, IS, YE, VO;
    String	NU, PA, ET, EA, EB, CL, KE, NO = "";

    /* 현재 결과셋에 속한 맨 처음 문서로 이동 */
    ret = japi.DS_SetDocPos(hptr, japi.DS_FIRST);

	AC = TI = AU = AF = AB = LA = JO = IS = YE = VO = "";
	NU = PA = ET = EA = EB = CL = KE = NO = "";

	// 현재 문서 정보 읽음
	docInfo = japi.DS_GetDocInfo(hptr);
	if(docInfo == null)
	{
		out.print("문서정보를 읽지 못하였습니다.");
		return;
	 }

	 //### 현재 문서에 속한 맨 처음 필드로 이동 ###//
	 ret = japi.DS_SetFieldPos(hptr, japi.DS_FIRST);
	 while(ret >= 0)
	 {
		//### 현재 필드 정보 읽음 ###//
		fieldInfo = japi.DS_GetFieldInfo(hptr);

		if(fieldInfo.getFieldid().startsWith("AC"))
		{
			AC = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("TI"))
		{
			TI = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("AU"))
		{
			AU = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("AF"))
		{
			AF = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("AB"))
		{
			AB = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("LA"))
		{
			LA = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("JO"))
		{
			JO = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("IS"))
		{
			IS = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("YE"))
		{
			YE = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("VO"))
		{
			VO = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("NU"))
		{
			NU = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("PA"))
		{
			PA = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("ET"))
		{
			ET = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("EA"))
		{
			EA = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("EB"))
		{
			EB = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("CL"))
		{
			CL = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("KE"))
		{
			KE = fieldInfo.getFieldcontent();
		}
		else if(fieldInfo.getFieldid().startsWith("NO"))
		{
			NO = fieldInfo.getFieldcontent();
		}

		 //### 다음 필드로 이동 ###//
		ret = japi.DS_SetFieldPos(hptr, japi.DS_NEXT);
	} // Field While
%>
		<tr align=center>
            <td class=td3 width=20%>한글제목</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=TI%></td>
		</tr>
		<tr align=center>
            <td class=td3 width=20%>영문제목</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=ET%></td>
		</tr>

		<tr align=center>
			<td class=td3 width=20%>한글저자</td>
        	<td class=td2 witdh=80% align=left>&nbsp;<%=AU%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>영문저자</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=EA%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>소 속</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=AF%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>언 어</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=LA%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>게재지</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=JO%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>ISSN</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=IS%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>연도</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=YE%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>볼 륨</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=VO%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>번 호</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=NU%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>페이지</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=PA%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>분류</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=CL%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>키워드</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=KE%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>NOTES</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=NO%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>한글초록</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=AB%></td>
		</tr>

		<tr align=center>
            <td class=td3 width=20%>영문초록</td>
			<td class=td2 witdh=80% align=left>&nbsp;<%=EB%></td>
		</tr>
        </table>
