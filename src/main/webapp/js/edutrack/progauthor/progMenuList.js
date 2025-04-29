	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var MENU_USER  = "";		
	var WORK_GUBUN = "";	
    var MENUCNT = "0";
    
	function init(systemCode, contextPath, vMenuUser , vWorkGubun) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;	
        this.MENU_USER   = vMenuUser;	
        this.WORK_GUBUN  = vWorkGubun;		

		autoReload();	
	}
 
    // 리스트 초기화
    function autoReload(){
        viewProgress('menuListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

        var vWorkGubun = DWRUtil.getValue("pWorkGubun");
        var vMenuUser = DWRUtil.getValue("pMenuUser");
        
    	ProgMenuListWork.progMenuListAuto(vWorkGubun, vMenuUser, autoReloadList);
    }

	    
    	
    // 목록 표시
    function autoReloadList(data){
    
    	var rowLength = 0;
    	var allItemCnt = 0;
    	if(data != null){
    		rowLength = data.length;
    		allItemCnt = data.length;
    	}
   	
    	var menuListDiv = $("menuListDiv");
    	var objStr = "";
    	
    	if(rowLength == 0){
    	    
            objStr += "\n<table width=670 align=center>";
            objStr += "\n<tr>";
            objStr += "\n<td class=s_tab04_cen colspan=13 height=25>";
            objStr += "\n※ 등록된 메뉴가 없습니다. <a href=\"javascript:Copy_Menu()\"><font color=blue>[메뉴복사]</font></a>";
            objStr += "\n</td>";
            objStr += "\n</tr>";
            objStr += "\n<tr>";
            objStr += "\n<td class=s_tab04_cen colspan=13 height=25>";
            objStr += "\n<div id=copyMenuUserDiv Style='display:none'>";
            objStr += "\n<select name='pFMenuUser'></select>";
            objStr += "\n복사하고자 하는 사용자를 선택후 <a href=\"javascript:StartCopyMenu()\"><font color=blue>복사시작</font></a>을 클릭하세요.";
            objStr += "\n</div>";
            objStr += "\n</td>";
            objStr += "\n</tr>";
            objStr += "\n</table>";

			        
	  	}else{

            var cnt_sub_menu = new Array();
            
            var menu_code  = new Array();
            var menu_order = new Array();
            var menu_name  = new Array();
            var r_right    = new Array();
            var c_right    = new Array();
            var u_right    = new Array();
            var d_right    = new Array();
            var work_gubun = new Array();
            var menu_user  = new Array();
        
            var menuDto ;   
            
            for(i=0;i<rowLength; i++){
                
                menuDto = data[i];
            
                cnt_sub_menu[i] = menuDto.cntSubMenu;
                menu_code[i]   = menuDto.menuCode;
                menu_name[i]   = menuDto.menuName;
                menu_order[i]  = menuDto.menuOrder;
                work_gubun[i]  = menuDto.workGubun;
                menu_user[i]   = menuDto.menuUser;
                
                
               
                if(cnt_sub_menu[i]==0){
                    r_right[i]     = menuDto.RRight;
                    c_right[i]     = menuDto.CRight;
                    u_right[i]     = menuDto.URight;
                    d_right[i]     = menuDto.DRight;
                }
                else {
                    r_right[i]     = "";
                    c_right[i]     = "";
                    u_right[i]     = "";
                    d_right[i]     = "";
                }

            }
    
    	  	objStr += "\n<ul id=\"list\">";
    	  	
	  	    for(i=0;i<rowLength;i++){

                
                objStr += "\n<li id='"+menu_code[i] +"_item'>";
                objStr += "\n<table width=650 border=0 align=center>";
                objStr += "\n	<tr>";
                objStr += "\n		<td class=menuper_ttop></td>";
                objStr += "\n	</tr>";
                objStr += "\n	<tr>";
                objStr += "\n		<td width='100%' align=center  class=menuper_tbg>";
                objStr += "\n			<table width='100%' height='100%' border=0 cellpadding=0 cellspacing=0>";
                objStr += "\n				<tr>";
                objStr += "\n					<td width=45 align=right><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/common/blet09.gif' align=absmiddle></td>";
                objStr += "\n					<td width=110 align=left><a href=\"javascript:Show_MenuInfo('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','edit')\" class=menuper_text01>"+menu_name[i] +"</a></td>";
                objStr += "\n					<td width=100 align=left><!--a href=\"javascript:Show_MenuInfo('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','subadd')\">[하위메뉴추가]</a--><a href=\"javascript:doDelete('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','top')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif' width=36 height=18 align=absmiddle></a></td>";
                //하위메뉴가 없을경우
                if(cnt_sub_menu[i] == 0){ 
                    objStr += "\n					<td width=055>"+(r_right[i] == "Y"  ? "( 읽기O": "( 읽기X")+"</td>";  
                    objStr += "\n					<td width=045>"+(c_right[i] == "Y"  ? "쓰기O": "쓰기X"  )+"</td>";  
                    objStr += "\n					<td width=045>"+(u_right[i] == "Y"  ? "수정O": "수정X"  )+"</td>";  
                    objStr += "\n					<td width=290>"+(d_right[i] == "Y"  ? "삭제O )": "삭제X )")+"</td>";  
                } else {
                    objStr += "\n					<td width=055>&nbsp;</td>";
                    objStr += "\n					<td width=045>&nbsp;</td>";
                    objStr += "\n					<td width=045>&nbsp;</td>";
                    objStr += "\n					<td width=290>&nbsp;</td>";
                } 
                objStr += "\n				</tr>";
                // 하위메뉴가 있을경우, 하위메뉴를 표현한다.
                if(cnt_sub_menu[i] > 0){
                    objStr += "\n				<tr>";
                    objStr += "\n					<td height=5></td>";
                    objStr += "\n				</tr>";
                               
                     // 하위 메뉴가 존재할 경우..
                     // 하위 메뉴를 모두 display 하고 next 로 넘어감...
                     var tmpVal = i ;
                     for(k=0; k<cnt_sub_menu[tmpVal];k++){
                         i++;
                
                        objStr += "\n				<tr height=18>";
                        objStr += "\n					<td width=45 style='padding:10 0 0 0'></td>";
                        objStr += "\n					<td width=110 align='left'><a href=\"javascript:Show_MenuInfo('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','edit')\">"+menu_name[i]+"</a></td>";
                        objStr += "\n					<td width=060 align='left'><a href=\"javascript:doDelete('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','sub')\">[삭제]</a></td>";
                        objStr += "\n					<td width=055>"+(r_right[i] == "Y"  ? "( 읽기O": "( 읽기X")+"</td>";
                        objStr += "\n					<td width=045>"+(c_right[i] == "Y"  ? "쓰기O": "쓰기X"  )+"</td>";
                        objStr += "\n					<td width=045>"+(u_right[i] == "Y"  ? "수정O": "수정X"  )+"</td>";
                        objStr += "\n					<td width=290>"+(d_right[i] == "Y"  ? "삭제O )": "삭제X )")+"</td>";
                        objStr += "\n				</tr>";
                        
                    } // end for
                } // end if
                
                objStr += "\n			</table>";
                objStr += "\n		</td>";
                objStr += "\n	</tr>";
                objStr += "\n	<tr>";
                objStr += "\n		<td class=menuper_tbottom></td>";
                objStr += "\n	</tr>";
                objStr += "\n	<tr>";
                objStr += "\n		<td height=10></td>";
                objStr += "\n	</tr>";
                objStr += "\n</table>";
                objStr += "\n</li>";
                
            }
                objStr += "\n</ul>";

	  	}

		menuListDiv.innerHTML = objStr;
		menuListDiv.style.display = "block";

        MENUCNT = rowLength ;
        if(rowLength > 0)  Sortable.create('list');
                   
     }
	 

    /** 사용자 유형, 메뉴 타입이 변경되었을경우...
     */
   function chgMenuUser(){
   	 autoReload();
   }

    /** 등록 
     */
   function Create_Prog(){
	    popupbox1.clear();
		showPopupBox(popupbox1);

        var vMenuUser = DWRUtil.getValue("pMenuUser");
        var vWorkGubun = DWRUtil.getValue("pWorkGubun");

        var vUrl= CONTEXTPATH+"/ProgMenu.cmd?cmd=progMenuWrite&pGUBUN=write&pWorkGubun="+vWorkGubun+"&pMenuUser="+vMenuUser;
        
        var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+vUrl+"' Style='z-index:5'></iframe>";
        popupbox1.addContents(frame);
   }

   /** 메뉴 상세조회, 수정..
    */
   function Show_MenuInfo(workgubun, pMenuUser, pMenuCode, pGubun){
	    popupbox1.clear();
		showPopupBox(popupbox1);

        var vUrl= CONTEXTPATH+"/ProgMenu.cmd?cmd=progMenuWrite&pGUBUN="+pGubun+"&pWorkGubun="+workgubun+"&pMenuUser="+pMenuUser+"&pMenuCode="+pMenuCode;
        
        var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+vUrl+"' Style='z-index:5'></iframe>";
        popupbox1.addContents(frame);

    }

    
    /** 메뉴 순서 저장하기..
     */
    function Save_Order(){
        var obj = document.getElementById('list').getElementsByTagName('li');
        var len = obj.length;
        
        var ordObj = "";
        
        for(i=0;i<len;i++){
            ordObj += obj[i].getAttribute('id');
        }
        
        var vMenuUser = DWRUtil.getValue("pMenuUser");
        var vWorkGubun = DWRUtil.getValue("pWorkGubun");
      
        ProgMenuListWork.progMenuOrderRegist(vWorkGubun, vMenuUser, ordObj, MENUCNT, callBackMethod);
    }
    

    /** 메뉴 순서 저장후 콜백 메소드
     */
    function callBackMethod(msg){
        alert(msg);
    }
    
    
    /** 메뉴를 복사시작
     */
    function StartCopyMenu(){

   	    var f = document.menuList;
   	    
        var vWorkGubun = DWRUtil.getValue("pWorkGubun");
        var vTMenuUser = DWRUtil.getValue("pMenuUser");  // target
        var vFMenuUser = DWRUtil.getValue("pFMenuUser"); // from
        var vWorkGubunTxt = f.pWorkGubun[f.pWorkGubun.selectedIndex].text ;
        
        var msg = "["+  f.pFMenuUser[f.pFMenuUser.selectedIndex].text + ">"+  vWorkGubunTxt+ "]의 메뉴를 \n\n" 
                + "["+ f.pMenuUser[f.pMenuUser.selectedIndex].text +  ">"+  vWorkGubunTxt+ "]로 복사하시겠습니까?";
        
        if(!confirm(msg)) return;
        
        ProgMenuListWork.progMenuCopy(vWorkGubun, vFMenuUser, vTMenuUser, callBackAfterWork);
   }



    /** 메뉴를 복사를 위해 사용자 유형을 가져온다..
     */
    function Copy_Menu(){
        $("copyMenuUserDiv").style.display = "block";
	    CodeSoWork.codeSoListAuto("1","","","101", callBackCopyMenuUser);
   }

    /** 사용자 유형 세팅
     */
    function callBackCopyMenuUser(data){

		var rowLength = 0;
	  	var dataList = data.dataList;
		if(dataList != null) rowLength = dataList.length;
		

        vMenuUser = DWRUtil.getValue("pMenuUser");
        
		var objDDL = document.forms["menuList"].elements["pFMenuUser"];
		objDDL.options.length = 0;  

        if(rowLength != 0){
            
 		  	for(i=0;i<rowLength;i++){  
    			var codeSoDto = dataList[i];
			    
			    if( vMenuUser ==  codeSoDto.codeSo ) continue;

			    option = new Option(codeSoDto.soName, codeSoDto.codeSo);
			    objDDL.options.add(option, objDDL.options.length);  
				
			}                  
	  	}
    }
                          
    
    
    /** 메뉴 삭제
     */
    function doDelete(vWorkGubun, vMenuUser, vMenuCode, vGubun){
        var f = document.Regist;
        var msg = "";
        
        if(vGubun=="top"){
            msg = "상위 메뉴를 삭제하시면 하위 메뉴까지 삭제가 됩니다. \n\n";
        } 
        
        msg += "삭제 후에는 복구가 불가능합니다. \n\n정말로 메뉴를 삭제하시겠습니까?";
        
        
        if(confirm(msg) == false ) return ;

        ProgMenuListWork.progMenuDelete(vWorkGubun, vMenuUser, vMenuCode, callBackAfterWork);
    }
    
    /** 메뉴 삭제, 복사후 콜백..
     */
    function callBackAfterWork(msg){
        alert(msg);
        autoReload();
    }
    
    

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	
