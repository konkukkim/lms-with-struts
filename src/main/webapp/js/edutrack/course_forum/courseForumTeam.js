	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = "";
	var CURRI_TERM = "";
	var COURSE_ID = "";
	var COURSE_NAME = "";
    var FORUM_ID = "";
    var FORUM_TYPE = "";

	function init(systemCode, contextPath, curriCode, curriYear, curriTerm, courseId, pForumId, pForumType ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;
        this.CURRI_CODE  = curriCode;
        this.CURRI_YEAR  = curriYear;
        this.CURRI_TERM  = curriTerm;
		this.COURSE_ID   = courseId;
		this.FORUM_ID    = pForumId;
		this.FORUM_TYPE  = pForumType;

		autoTeamReload();
	}

    // �� ����Ʈ �ʱ�ȭ
    function autoTeamReload(){
        viewProgress('forumTeamListDiv','block',CONTEXTPATH, SYSTEMCODE,'Y');

    	CourseForumTeamWork.courseForumTeamListAuto(COURSE_ID, FORUM_ID, courseForumTeamList);
    }



    // �� ��� ǥ��
    function courseForumTeamList(data){

    	var rowLength = 0;
    	var allItemCnt = 0;
    	if(data != null){
    		rowLength = data.length;
    		allItemCnt = data.length;
    	}

    	var forumListObj = $("forumTeamListDiv");
    	var addTeamBut     = $("addTeamButton");
    	var addTeamAutoBut = $("addTeamAutoButton");
    	var manageTeamBut  = $("manageTeamButton");
    	var objStr = "";

    	// ����ڵ������Ŀ� comment div �� �����..
    	divConComment("none");

    	if(rowLength == 0){

    	    addTeamAutoBut.style.display = "";
    	    manageTeamBut .style.display = "none";

		  	objStr += "<table width='670' align='center' valign=top>"
		  	        + "<tr class=s_tab01><td colspan=2></td></tr>"
                    + "<tr><td class='s_tab_view02' align=center>�� ������ ��� ���� �����ϴ�. </td></tr>"
			        + "<tr class='s_tab03'><td colspan='20'></td></tr>"
			        + "</table>";
	  	}else{

    	    addTeamAutoBut.style.display = "none";
    	    manageTeamBut .style.display = "";

	  	    for(i=0;i<rowLength;i++){

    			var courseForumInfoDTO = data[i];

                var sForumId       = courseForumInfoDTO.forumId;
                var sParentForumId = courseForumInfoDTO.parentForumId;
                var sSubject       = courseForumInfoDTO.subject;
                var sSubTeamNo     = courseForumInfoDTO.subTeamNo;
                var sSubTeamName   = courseForumInfoDTO.subTeamName;
                var sContents      = courseForumInfoDTO.contents;
                var sSfileName     = courseForumInfoDTO.sfileName;
                var sRfileName     = courseForumInfoDTO.rfileName;
                var sFilePath      = courseForumInfoDTO.filePath;
                var sFileSize      = courseForumInfoDTO.fileSize;
                var sTeamUserCnt      = courseForumInfoDTO.teamUserCnt;
                var sForumType     = courseForumInfoDTO.forumType;


        objStr +=  "\n <div id='forumInfoShowDiv' style='width:100%;display:block'> "
                +  "\n <input type=hidden name=pForumId"+i +" value="+sForumId+"> "
                +  "\n <input type=hidden name=pParentForumId"+i +" value="+sParentForumId+">"
                +  "\n <input type=hidden name=pForumType"+i +" value="+sForumType+">"
                +  "\n <table width=670 align=center>"
                +  "\n <tr class=s_tab05><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n 	<td class=s_tab_view01 width=150>�������</td>"
                +  "\n     <td class=s_tab_view02>"+sSubTeamName +"</td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>����</td>"
                +  "\n     <td class=s_tab_view02>"
                +  "\n         <font color='606060'><strong>"+sSubject +"</a></strong></font>&nbsp;&nbsp;"
                +  "\n     </td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>������</td>"
                +  "\n     <td class=s_tab_view02><a href=\"javascript:manageTeam('"+i +"');\">"+sTeamUserCnt+" ��</td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>÷������</td>"
                +  "\n     <td class=s_tab_view03><a href=\"javascript:fileDownload('"+sSfileName +"','"+sSfileName +"','"+sFilePath +"','"+sFileSize +"')\">"+sRfileName +"</td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>��а���</td>"
                +  "\n     <td class=s_tab_view03>"+sContents +"</td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab05><td colspan=2></td></tr>"
                +  "\n <tr height=25>"
                +  "\n      <td colspan=2 align=right>"
                +  "\n      <a href=\"javascript:manageInfo('Edit','"+i +"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/bttn_edit.gif' border=0 align=absmiddle alt=����></a>"
                +  "\n      <a href=\"javascript:manageTeamDel('"+i +"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/bttn_delete.gif' border=0 align=absmiddle alt=����></a>"
                +  "\n      </td>"
                +  "\n </tr>"
                +  "\n </table>"
                +  "\n </div><br>";
                // write form
        objStr +=  "\n <div id=forumInfoEditDiv style='width:100%;display:none'>"
                +  "\n <table width=670 align=center>"
                +  "\n <tr class=s_tab05><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n 	<td class=s_tab_view01 width=150>�������</td>"
                +  "\n     <td class=s_tab_view02>"
                +  "\n 		<input type=text name=pSubTeamName"+i +" value=\""+sSubTeamName+"\" size=60 maxlength=100 dispName='�������'  notNull  lenCheck='100'>"
                +  "\n     </td>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>����</td>"
                +  "\n     <td class=s_tab_view02>"
                +  "\n         <input type=text name=pSubject"+i +" value="+sSubject+" size=60 maxlength=100 dispName='����'  notNull  lenCheck='100'>"
                +  "\n     </td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>÷������</td>"
                +  "\n     <td class=s_tab_view03>";

        if (sSfileName!="") {
        objStr +=  "\n     	    <a href=\"javascript:fileDownload('"+sRfileName+"','"+sSfileName+"','"+sFilePath+"','"+sFileSize+"');\">"+sRfileName+"</a>"
                +  "\n     	    &nbsp;&nbsp;<a href='#' onClick=\"javascript:registInfo('FileDel','"+i+"')\">[�������ϻ���]</a>"
                +  "\n     	    <input type=hidden name=pOldRfileName"+i +" value="+sRfileName+"> "
                +  "\n     	    <input type=hidden name=pOldSfileName"+i +" value="+sSfileName+">"
                +  "\n     	    <input type=hidden name=pOldFilePath"+i +"  value="+sFilePath+"> "
                +  "\n     	    <input type=hidden name=pOldFileSize"+i +"  value="+sFileSize+">"
                +  "\n     		<br>";
         }

        objStr +=  "\n         <input type=file name=pFileName"+i+" size=60>"
                +  "\n     </td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab03><td colspan=2></td></tr>"
                +  "\n <tr>"
                +  "\n     <td class=s_tab_view01>��а���</td>"
                +  "\n     <td class=s_tab_view03><textarea name=pContents"+i+" rows=5 cols='60' dispName='��а���' notNull>"+sContents +"</textarea></td>"
                +  "\n </tr>"
                +  "\n <tr class=s_tab05><td colspan=2></td></tr>"
                +  "\n <tr height=25>"
                +  "\n      <td colspan=2 align=right>"
                +  "\n      <a href=\"javascript:registInfo('Edit','"+i+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/bttn_edit.gif' border=0 align=absmiddle></a>"
                +  "\n      <a href=\"javascript:closeInfoWrite('"+i+"');\"><img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/button/bttn_cancel.gif' border=0 align=absmiddle></a>"
                +  "\n      </td>"
                +  "\n </tr>"
                +  "\n </table>"
                +  "\n </div><br>";



            }

	  	}

		forumListObj.innerHTML = objStr;
		forumListObj.style.display = "block";

     }


    ///////////////////
	// �� ����..
	function manageTeam(vSeq){
	    popupbox1.clear();
		showPopupBox(popupbox1);

		var forumId         = DWRUtil.getValue("pForumId"+vSeq);
		var parentForumId   = DWRUtil.getValue("pParentForumId"+vSeq);
		var vUrl = CONTEXTPATH+"/CourseForumTeam.cmd?cmd=courseForumTeamMemList&pCourseId="+COURSE_ID+"&pForumId="+forumId+"&pParentForumId="+parentForumId;
	    var frame = "<iframe name='Frame1' width='100%' height='100%' frameborder='0' src='"+vUrl+"'></iframe>";
        popupbox1.addContents(frame);
    }


	 //�˾��ݱ�
	 function closeWin() {
	 	//if(reloadCnt > 0)
		// 	parent.autoTeamReload();
		 parent.popupbox1.close();
	 }
	 ////////////


	 ////////////

	// �� �ڵ����� ����....
	function registForumTeamAuto(){
		var courseId  = DWRUtil.getValue("pCourseId");
		var forumId   = DWRUtil.getValue("pForumId");

        f = document.Input ;
	    //document.location = "/CourseForumTeam.cmd?cmd=courseForumTeamAutoRegist&pCourseId="+courseId+"&pForumId="+forumId;
		f.target = "hiddenFrame";
        f.action = CONTEXTPATH+ "/CourseForumTeam.cmd?cmd=courseForumTeamAutoRegist&pCourseId="+courseId+"&pForumId="+forumId;
        f.submit();


    }

     //�� �ڵ�������.. �˾��ݱ�
     function divConComment(val) {
    	 $("forumInfoWriteDiv").style.display = "none";
    	 
    	 var commentObj = $("addTeamAutoComment");
    	 commentObj.style.display=val;
     }


	 ////////////


	// �� ����/����
	function manageInfo(regMode, vSeq){

		if(document.all.forumInfoEditDiv != null){
		    editLen = document.all.forumInfoEditDiv.length;

		    if(editLen==null){
		        $("forumInfoEditDiv").style.display = "none";
                $("forumInfoShowDiv").style.display = "block";
		    }
		    else
		    {
                for(i=0;i<editLen;i++){
                    if( $(eval("forumInfoEditDiv["+i+"]")).style.display == "none") continue;

                    $(eval("forumInfoEditDiv["+i+"]")).style.display = "none";
                    $(eval("forumInfoShowDiv["+i+"]")).style.display = "block";
                }
            }
        }

        if( $("forumInfoWriteDiv").style.display == "block")
            $("forumInfoWriteDiv").style.display = "none" ;

		if(regMode == "Add"){
			$("addTeamAutoComment").style.display = "none";
			Effect.Appear("forumInfoWriteDiv");

		}else if(regMode == "Edit"){
			$(eval("forumInfoShowDiv["+vSeq+"]")).style.display = "none";
			Effect.Appear(eval("forumInfoEditDiv["+vSeq+"]"));
		}

	}


	// �� ������..
	function manageTeamDel(vSeq){

		var forumId         = DWRUtil.getValue("pForumId"+vSeq);
		var parentForumId   = DWRUtil.getValue("pParentForumId"+vSeq);

		if(!confirm("��й��� ������ ���� ȸ�������� ��аԽ��� \n\n�׸��� �ڸ�Ʈ �� ��ΰ� �����˴ϴ�. \n\n�׷��� �����Ͻðڽ��ϳ�?")) return;

        f = document.Input ;
        f.target = "hiddenFrame";
        f.action = CONTEXTPATH+ "/CourseForumInfo.cmd?cmd=courseForumInfoDelete&pCourseId="+COURSE_ID+"&pForumId="+forumId+"&pParentForumId="+parentForumId+"&pForumType="+FORUM_TYPE+"&pCallGubun=Ajax";
        f.submit();

	}


	// ���� ��й� ����
	function registInfo(pRegMode, vSeq){
		var tmpCourseId = DWRUtil.getValue("pCourseId");

		DWRUtil.setValue("pSeq",vSeq);

		var pSubTeamNo  = DWRUtil.getValue("pSubTeamNo"+vSeq);
	    var pForumId    = DWRUtil.getValue("pForumId"+vSeq);

        if(pRegMode=="FileDel"){
            // ÷�����ϸ� ����
           go_Url = CONTEXTPATH+ "/CourseForumInfo.cmd?cmd=courseForumInfoFileDelete&pRegMode="+pRegMode+"&pCallJsp=Y&pCourseId="+COURSE_ID+"&pForumId="+pForumId;
        }else { // Add, Edit
            go_Url = CONTEXTPATH+ "/CourseForumTeam.cmd?cmd=courseForumTeamInfoRegist&pRegMode="+pRegMode+"&pForumId="+pForumId;
        }

		f = document.Input ;
        f.encoding ="multipart/form-data";
        f.target = "hiddenFrame";
        f.action = go_Url;
		f.submit();

	}


    // ���� ��й� ������ �ݹ� �޼ҵ�..
	function autoReloadPopupCallback(){
	   autoTeamReload();
	}


   // ��� ������� �̵�..
   function goList(){
      var curPage    = DWRUtil.getValue("curPage");
      document.location = CONTEXTPATH+ "/CourseForumInfo.cmd?cmd=courseForumInfoPagingList&curPage="+curPage+"&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID;
   }
   function goForum(pForumId){
      //var f = document.Input;
      //var pCourseId	= f.pCourseId.value;
      document.location = CONTEXTPATH+ "/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pConnectYN=Y&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID;
   }
   function goScore(pForumId){
      //var f = document.Input;
      //var pCourseId	= f.pCourseId.value;
      document.location = CONTEXTPATH+ "/CourseForumInfo.cmd?cmd=courseForumScoreWrite&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID;
   }

   // ���� �ٿ�ޱ�..
   function fileDownload(rfilename, sfilename, filepath, filesize){
       var loc=CONTEXTPATH+ "/jsp/"+SYSTEMCODE+"/common/fileDownLoad.jsp?rFileName="+rfilename+"&sFileName="+sfilename+"&filePath="+filepath+"&fileSize="+filesize;
       hiddenFrame.document.location = loc;
    }



    // on cancel
    function closeInfoWrite(vSeq){

        if(vSeq==""){    // ���߰����� ��ҽ�
    		$("forumInfoWriteDiv").style.display = "none";
    	} else { // ���������� ��ҽ�
    		$(eval("forumInfoEditDiv["+vSeq+"]")).style.display = "none";
    		Effect.Appear(eval("forumInfoShowDiv["+vSeq+"]"));
    	}
    }


	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
