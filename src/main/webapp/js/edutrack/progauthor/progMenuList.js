	// ��������Ʈ
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
 
    // ����Ʈ �ʱ�ȭ
    function autoReload(){
        viewProgress('menuListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

        var vWorkGubun = DWRUtil.getValue("pWorkGubun");
        var vMenuUser = DWRUtil.getValue("pMenuUser");
        
    	ProgMenuListWork.progMenuListAuto(vWorkGubun, vMenuUser, autoReloadList);
    }

	    
    	
    // ��� ǥ��
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
            objStr += "\n�� ��ϵ� �޴��� �����ϴ�. <a href=\"javascript:Copy_Menu()\"><font color=blue>[�޴�����]</font></a>";
            objStr += "\n</td>";
            objStr += "\n</tr>";
            objStr += "\n<tr>";
            objStr += "\n<td class=s_tab04_cen colspan=13 height=25>";
            objStr += "\n<div id=copyMenuUserDiv Style='display:none'>";
            objStr += "\n<select name='pFMenuUser'></select>";
            objStr += "\n�����ϰ��� �ϴ� ����ڸ� ������ <a href=\"javascript:StartCopyMenu()\"><font color=blue>�������</font></a>�� Ŭ���ϼ���.";
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
                objStr += "\n					<td width=100 align=left><!--a href=\"javascript:Show_MenuInfo('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','subadd')\">[�����޴��߰�]</a--><a href=\"javascript:doDelete('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','top')\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button_img/btn_del.gif' width=36 height=18 align=absmiddle></a></td>";
                //�����޴��� �������
                if(cnt_sub_menu[i] == 0){ 
                    objStr += "\n					<td width=055>"+(r_right[i] == "Y"  ? "( �б�O": "( �б�X")+"</td>";  
                    objStr += "\n					<td width=045>"+(c_right[i] == "Y"  ? "����O": "����X"  )+"</td>";  
                    objStr += "\n					<td width=045>"+(u_right[i] == "Y"  ? "����O": "����X"  )+"</td>";  
                    objStr += "\n					<td width=290>"+(d_right[i] == "Y"  ? "����O )": "����X )")+"</td>";  
                } else {
                    objStr += "\n					<td width=055>&nbsp;</td>";
                    objStr += "\n					<td width=045>&nbsp;</td>";
                    objStr += "\n					<td width=045>&nbsp;</td>";
                    objStr += "\n					<td width=290>&nbsp;</td>";
                } 
                objStr += "\n				</tr>";
                // �����޴��� �������, �����޴��� ǥ���Ѵ�.
                if(cnt_sub_menu[i] > 0){
                    objStr += "\n				<tr>";
                    objStr += "\n					<td height=5></td>";
                    objStr += "\n				</tr>";
                               
                     // ���� �޴��� ������ ���..
                     // ���� �޴��� ��� display �ϰ� next �� �Ѿ...
                     var tmpVal = i ;
                     for(k=0; k<cnt_sub_menu[tmpVal];k++){
                         i++;
                
                        objStr += "\n				<tr height=18>";
                        objStr += "\n					<td width=45 style='padding:10 0 0 0'></td>";
                        objStr += "\n					<td width=110 align='left'><a href=\"javascript:Show_MenuInfo('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','edit')\">"+menu_name[i]+"</a></td>";
                        objStr += "\n					<td width=060 align='left'><a href=\"javascript:doDelete('"+work_gubun[i]+"','"+menu_user[i] +"','"+menu_code[i]+"','sub')\">[����]</a></td>";
                        objStr += "\n					<td width=055>"+(r_right[i] == "Y"  ? "( �б�O": "( �б�X")+"</td>";
                        objStr += "\n					<td width=045>"+(c_right[i] == "Y"  ? "����O": "����X"  )+"</td>";
                        objStr += "\n					<td width=045>"+(u_right[i] == "Y"  ? "����O": "����X"  )+"</td>";
                        objStr += "\n					<td width=290>"+(d_right[i] == "Y"  ? "����O )": "����X )")+"</td>";
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
	 

    /** ����� ����, �޴� Ÿ���� ����Ǿ������...
     */
   function chgMenuUser(){
   	 autoReload();
   }

    /** ��� 
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

   /** �޴� ����ȸ, ����..
    */
   function Show_MenuInfo(workgubun, pMenuUser, pMenuCode, pGubun){
	    popupbox1.clear();
		showPopupBox(popupbox1);

        var vUrl= CONTEXTPATH+"/ProgMenu.cmd?cmd=progMenuWrite&pGUBUN="+pGubun+"&pWorkGubun="+workgubun+"&pMenuUser="+pMenuUser+"&pMenuCode="+pMenuCode;
        
        var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+vUrl+"' Style='z-index:5'></iframe>";
        popupbox1.addContents(frame);

    }

    
    /** �޴� ���� �����ϱ�..
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
    

    /** �޴� ���� ������ �ݹ� �޼ҵ�
     */
    function callBackMethod(msg){
        alert(msg);
    }
    
    
    /** �޴��� �������
     */
    function StartCopyMenu(){

   	    var f = document.menuList;
   	    
        var vWorkGubun = DWRUtil.getValue("pWorkGubun");
        var vTMenuUser = DWRUtil.getValue("pMenuUser");  // target
        var vFMenuUser = DWRUtil.getValue("pFMenuUser"); // from
        var vWorkGubunTxt = f.pWorkGubun[f.pWorkGubun.selectedIndex].text ;
        
        var msg = "["+  f.pFMenuUser[f.pFMenuUser.selectedIndex].text + ">"+  vWorkGubunTxt+ "]�� �޴��� \n\n" 
                + "["+ f.pMenuUser[f.pMenuUser.selectedIndex].text +  ">"+  vWorkGubunTxt+ "]�� �����Ͻðڽ��ϱ�?";
        
        if(!confirm(msg)) return;
        
        ProgMenuListWork.progMenuCopy(vWorkGubun, vFMenuUser, vTMenuUser, callBackAfterWork);
   }



    /** �޴��� ���縦 ���� ����� ������ �����´�..
     */
    function Copy_Menu(){
        $("copyMenuUserDiv").style.display = "block";
	    CodeSoWork.codeSoListAuto("1","","","101", callBackCopyMenuUser);
   }

    /** ����� ���� ����
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
                          
    
    
    /** �޴� ����
     */
    function doDelete(vWorkGubun, vMenuUser, vMenuCode, vGubun){
        var f = document.Regist;
        var msg = "";
        
        if(vGubun=="top"){
            msg = "���� �޴��� �����Ͻø� ���� �޴����� ������ �˴ϴ�. \n\n";
        } 
        
        msg += "���� �Ŀ��� ������ �Ұ����մϴ�. \n\n������ �޴��� �����Ͻðڽ��ϱ�?";
        
        
        if(confirm(msg) == false ) return ;

        ProgMenuListWork.progMenuDelete(vWorkGubun, vMenuUser, vMenuCode, callBackAfterWork);
    }
    
    /** �޴� ����, ������ �ݹ�..
     */
    function callBackAfterWork(msg){
        alert(msg);
        autoReload();
    }
    
    

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}	
