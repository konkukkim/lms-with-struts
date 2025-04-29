<%@ page language="java"%>
<%@ page contentType="text/html; charset=euc-kr" %>
<%@ page import ="java.util.ArrayList, java.util.Map, com.edutrack.common.UserBroker, com.edutrack.common.dto.UserMemDTO "%>
<%@ page import ="com.edutrack.framework.util.*, com.edutrack.progauthor.dto.ProgMenuDTO,com.edutrack.common.dto.CodeParam, com.edutrack.common.CommonUtil "%>
<%
    String CONTEXTPATH = request.getContextPath();
	Map model = (Map)request.getAttribute("MODEL");
	UserMemDTO userMemDto = UserBroker.getUserInfo(request);
	String SYSTEMCODE = userMemDto.systemCode;


    ProgMenuDTO MenuInfo = new ProgMenuDTO(); 
    ArrayList pArrMaster = (ArrayList)model.get("pMenuMaster");
    ArrayList pMenuList  = (ArrayList)model.get("pMenuList");

    String pGUBUN  = (String)model.get("pGUBUN");
    String pWorkGubun = (String)model.get("pWorkGubun");
    String pMenuUser  = (String)model.get("pMenuUser");
    
    
    if(pMenuList.size()>0){
        MenuInfo = (ProgMenuDTO)pMenuList.get(0);
    }
%>
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/style.css">
<link rel="stylesheet" type="text/css" href="<%=CONTEXTPATH%>/css/form.css">
<Script type="text/javascript" src="<%=CONTEXTPATH%>/js/common1.js"></script>
<Script type="text/javascript" src="<%=CONTEXTPATH%>/js/common_util.js"></script>
<Script type="text/javascript" src="<%=CONTEXTPATH%>/js/common_button.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/dwr/interface/ProgMenuListWork.js"></script>
<script type="text/javascript" src="/dwr/engine.js"></script>
<script type="text/javascript" src="/dwr/util.js"></script>
<script type="text/javascript" src="<%=CONTEXTPATH%>/js/ajaxCommon.js"></script>
<Script Language="javascript">
    
	function SubmitCheck()
	{
		var f = document.Regist;

		if(!validate(f)) return;

        if(f.pWorkGubun.value == ""){
   	     alert("페이지 구분을 선택해 주세요.");
   	     f.pWorkGubun.focus();
   	     return;
   	  }
   	    
        f.callBackMethod.value = "parent.Frame1.callBackMethodAfterRegist('<%=pGUBUN %>')";
        f.action="<%=CONTEXTPATH%>/ProgMenu.cmd?cmd=progMenuRegist";
        f.target = "hiddenFrame";
		f.submit();
    }

    /** 
     */
    function chgMenuUser(){
		var vWorkGubun = DWRUtil.getValue("pWorkGubun");
		var vMenuUser  = DWRUtil.getValue("pMenuUser");
		
		ProgMenuListWork.progMenuMasterList(vWorkGubun, vMenuUser, chgMenuCallBack);
    }
    
    
    /**
     */
    function chgMenuCallBack(data){
        var rowLength = 0;
		if(data != null){
			rowLength = data.length;
		}

		var objDDL = document.forms["Regist"].elements["pMenuMaster"];
		objDDL.options.length = 0;  
    
        var option = new Option("- 상위메뉴없슴 -", "0");
        objDDL.options.add(option, objDDL.options.length);
			
    
	  	if(rowLength != 0){
            
 		  	for(i=0;i<rowLength;i++){  
    			var progMenuDto = data[i];
			    option = new Option(progMenuDto.menuName, progMenuDto.menuMaster);                           
			    objDDL.options.add(option, objDDL.options.length);  
				
			}                  
	  	}                       
    
    }

    
	 //팝업닫기
	 function closeWin() {
		 parent.popupbox1.close();
	 }
	 
    

    /** 메뉴 저장후 콜백 메소드
     */
    function callBackMethodAfterRegist(gubun){
        parent.autoReload();
        closeWin();
    } 


</Script>
							<table width="480" height="100%" align="right">
								<tr valign="top">
									<td colspan="2" class="content_top" valign="top">
										<!-- 내용 -->
										<!-- 게시판 리스트 시작 -->
										<font color=red>*</font> 표시된 항목은 필수입력사항입니다.<br>
										<div  id="menuDiv">
										<table width="470" align="center">
                                            <form name=Regist method="post" >
                                            <input type=hidden name="pGUBUN"         value="<%=pGUBUN%>">
                                            <input type=hidden name="pMenuCode"      value="<%=MenuInfo.getMenuCode()%>">
                                            <input type=hidden name="pMenuUser"      value="<%=pMenuUser %>">
                                            <input type=hidden name="pWorkGubun"     value="<%=pWorkGubun%>">
                                            <input type=hidden name="callBackMethod" value="">
											<tr class="s_tab01">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01" width="080"><font color=red>*</font> 상위메뉴</td>
												<td class="s_tab_view02">
<%
	ProgMenuDTO MenuInfo1 = new ProgMenuDTO();
	if( pGUBUN.equals("write") ) {
%>
													<select name="pMenuMaster"></select>
<%	}
    else if( pGUBUN.equals("edit") ) {
	
													if(("0").equals(MenuInfo.getMenuMaster())){
													    out.println("- 상위메뉴없슴 -");
													}else{
														for(int i=0;i<pArrMaster.size();i++){
															MenuInfo1 = (ProgMenuDTO)pArrMaster.get(i);
													
													        if(MenuInfo.getMenuMaster().equals(MenuInfo1.getMenuMaster()))
													            out.println(MenuInfo1.getMenuName());
													    }
													}
	}
%>		
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01"><font color=red>*</font> 메뉴명</td>
												<td class="s_tab_view02"><input type="text" name="pMenuName" size="50" dispName="메뉴명" notNull  lenCheck=100 value="<%=MenuInfo.getMenuName()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">메뉴URL</td>
												<td class="s_tab_view02">
												    <input type="text" name="pMenuUrl" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getMenuUrl()%>" >
												    예시) /Course.cmd?cmd=courseList
												    
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">메뉴설명</td>
												<td class="s_tab_view03"><textarea name="pMenuComment" cols=50 rows=4 dispName="메뉴설명" lenCheck=4000><%=MenuInfo.getMenuComment()%></textarea></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01"><font color=red>*</font> 접근권한</td>
												<td class="s_tab_view03">
													<input type=checkbox name="pRRight" value="Y" class=no <%=(("Y").equals(StringUtil.nvl(MenuInfo.getRRight())) ? "checked": "")%>>읽기가능 &nbsp;
					                                <input type=checkbox name="pCRight" value="Y" class=no <%=(("Y").equals(StringUtil.nvl(MenuInfo.getCRight())) ? "checked": "")%>>쓰기가능 &nbsp;
					                                <input type=checkbox name="pURight" value="Y" class=no <%=(("Y").equals(StringUtil.nvl(MenuInfo.getURight())) ? "checked": "")%>>수정가능 &nbsp;
					                                <input type=checkbox name="pDRight" value="Y" class=no <%=(("Y").equals(StringUtil.nvl(MenuInfo.getDRight())) ? "checked": "")%>>삭제가능 &nbsp;
												</td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">사용여부</td>
												<td class="s_tab_view03">
													<input type=radio name="pUseYn" value="Y" class=no <%=(!("N").equals(StringUtil.nvl(MenuInfo.getUseYn())) ? "checked": "")%>>사용 &nbsp;
                               						<input type=radio name="pUseYn" value="N" class=no <%=(("N").equals(StringUtil.nvl(MenuInfo.getUseYn())) ? "checked": "")%>>사용안함 &nbsp;
												</td>
											</tr>
											<!--tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">추가 URL1</td>
												<td class="s_tab_view02"><input type="text" name="pAddUrl1" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getAddUrl1()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">추가 URL2</td>
												<td class="s_tab_view02"><input type="text" name="pAddUrl2" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getAddUrl2()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">추가 URL3</td>
												<td class="s_tab_view02"><input type="text" name="pAddUrl3" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getAddUrl3()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">추가 URL4</td>
												<td class="s_tab_view02"><input type="text" name="pAddUrl4" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getAddUrl4()%>" ></td>
											</tr>
											<tr class="s_tab03">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_tab_view01">추가 URL5</td>
												<td class="s_tab_view02"><input type="text" name="pAddUrl5" size=60 dispName="메뉴URL" lenCheck=200 value="<%=MenuInfo.getAddUrl5()%>" ></td>
											</tr-->
											<tr class="s_tab05">
												<td colspan="2"></td>
											</tr>
											<tr>
												<td class="s_list_btn" colspan="2" height="30" align="right">
                                                    <Script Language=javascript>Button3("등록", "SubmitCheck('<%=pGUBUN%>')", "");</Script>
                                                    &nbsp;<Script Language=javascript>Button3("창닫기", "closeWin()", "");</Script>
												</td>
											</tr>
										</table>
										</div>
										<!-- // 게시판 리스트  끝 -->
									</td>
								</tr>
							</table>
                            </form>
						</td>
						<!-- // 본문 -->
					</tr>
					</table>
						

<% if( pGUBUN.equals("write") ) { %>
<Script>chgMenuUser()</Script>
<%  } %>