	// ��������Ʈ
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = 0;
	var CURRI_TERM = 0;	
	var COURSE_ID = "";	
	var FORUM_ID = "";	

	// global attribute
    var MANAGE_COURSE_ID = "";
    var MANAGE_FORUM_ID = "";
    
	function init(systemCode, contextPath) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		
		var f = document.f;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
		this.COURSE_ID = f.pCourseId.value;
		this.FORUM_ID = f.pForumId.value;
		this.PGUBUN = f.pGubun.value;
		
		autoReload();
	}
	
	//  ��й� �Խù� ����Ʈ ��ƿ���
	function autoReload() {
		viewProgress('courseForumContentsList','block',CONTEXTPATH,SYSTEMCODE,'Y');

        var curPage = DWRUtil.getValue("curPage");
        var pSearchKey = DWRUtil.getValue("pSearchKey");
        var pKeyWord = DWRUtil.getValue("pKeyWord");
		
		PublishCurriSubWork.courseForumContentsAutoList(curPage, CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, FORUM_ID, pSearchKey, pKeyWord, autoReloadCallback);
	}

	// ����¡ó��
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}
	
	// ��й� �Խù� ����Ʈ �Ѹ���
	function autoReloadCallback(data) {
		var rowLength = 0;
	  	var paggingStr = data.pagging;
	  	var dataList = data.dataList;
		if(dataList != null) rowLength = dataList.length;
		     
	  	//viewAutoButton('C',rowLength);
	  	var forumListObj = $("courseForumContentsList");
	  	var paggingObj = $("getPagging"); 
	  	var objStr = "<table width='670' border='0' align='center'>";
	  	var No = 0;
	  	
	  	// ����¡ ó��..
	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class='s_tab04_cen'>�� ��ϵ� �Խñ��� �����ϴ�. </td></tr>"
			       + "<tr class='s_tab05'><td colspan='20'></td></tr>";
	  	}else{
		  	//var no = 0;
            var totalCount = data.totalDataCount;
 			
 			var depthSpace = "";
			var commCnt = 0;
			var commStr = "";
			var bbsHot = "";
			var bbsNew = "";
			var depthSpace = "";
			var hitNo = 0;
			var regDate = "";
			var depthNo = 0;
			
			var currentDate = document.f.currentDate.value;
			           
		  	for(i=0;i<rowLength;i++){  
    			var courseForumContentsDto = dataList[i];
    			
		    	var forumId      = courseForumContentsDto.forumId;   
		    	var seqNo        = courseForumContentsDto.seqNo;   
		    	var subject      = courseForumContentsDto.subject;   
		    	var contents     = courseForumContentsDto.contents;   
		    	var contentsType = courseForumContentsDto.contentsType;
		    	var regId        = courseForumContentsDto.regId;   
		    	var regName      = courseForumContentsDto.regName;   
		    	var regDate      = courseForumContentsDto.regDate;
		    	var hitNo        = courseForumContentsDto.hitNo;
		    	var dispLine	 = courseForumContentsDto.dispLine;
	    	    var viewNo  = 0;
	    	    depthNo = courseForumContentsDto.depthNo;					// �� ���� ��
				commCnt = courseForumContentsDto.commCnt;						// ���� �ڸ�Ʈ ��
				var regDateEtc	=	courseForumContentsDto.regDateEtc;

                // �������ϰ�� ��ȣ���� * �� �����ش�..
                if(contentsType=="N")  viewNo = "*";
                else  viewNo = (totalCount--);

				if(parseFloat(regDateEtc) == parseFloat(currentDate)) bbsNew = "&nbsp;<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_new.gif'>";
				if(depthNo > 0) depthSpace = "<img src='"+CONTEXTPATH+"/img/"+SYSTEMCODE+"/bbs/icon_re.gif'>";
				for(j=0; j<depthNo;j++) depthSpace = "&nbsp;"+depthSpace;
				if(commCnt > 0) commStr = "<span style='font-size:7pt;'>["+commCnt+"]</span>";
                	
                if(No > 0 && No != dispLine) objStr += "\n<tr class=\"s_tab03\"><td colspan=\"13\"></td></tr>";
											
				objStr += " \n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">"
                        + " \n<td width=070 class='s_tab04_cen'>"+viewNo+"</td>"
    			        + " \n<td width=1 ></td>"
    					+ " \n<td width=330 class='s_tab04'>"+depthSpace+"<a href=\"javascript:goShow('"+seqNo+"')\">"+subject+"</a>"+commStr+bbsNew+bbsHot+"</td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=100 class='s_tab04_cen'>"+regName+"</td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=100 class='s_tab04_cen'>"+regDate+"</td>"
    					+ " \n<td width=1 ></td>"
    					+ " \n<td width=070 class='s_tab04_cen'>"+hitNo+"</td>"
            			+ " \n</tr>";
  				
  				
  				No++;
  				bbsNew = "";
  				depthSpace = "";
  				commStr = "";
			} // end of for
			
		}
		objStr += "\n</table>";	
		forumListObj.innerHTML = objStr;
		forumListObj.style.display = "block";
	}

    // searching
	function goSearchList() {
		DWRUtil.setValue("curPage","1");
		autoReload()
	}

    // ��ȭ������ �̵�..
	function goShow(seqNo) {
		var curPage = DWRUtil.getValue("curPage");
		var param1 = "&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
		var param2 = "&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&pSeqNo="+seqNo+"&curPage="+curPage+"&pGubun="+PGUBUN;
		document.location = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsShow&MENUNO=1&pMode=Home"+param1+param2;
	}
	
    // ���ȭ������ �̵�..
	function goAdd() {
		var	param1	=	"&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM;
		var param2	=	"&pCourseId="+COURSE_ID+"&pForumId="+FORUM_ID+"&MENUNO=1&pMode=Home&pGubun="+PGUBUN;
		document.location = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsWrite"+param1+param2;
	}
	
	// ��������Ʈ�� ���ư���	
	function goCurriList() {
		document.location = CONTEXTPATH+"/PublishCurriSub.cmd?cmd=publishCurriSubPageList&MENUNO=1&pPareCode1=99999&pPareCode2=00002&pMode=Home&pGubun="+PGUBUN;
	}
	
	// ���� ����Ʈ�� �̵�
	function goContents() {
		var	param	=	"&pCurriCode="+CURRI_CODE+"&curriYear="+CURRI_YEAR+"&curriTerm="+CURRI_TERM+"&pGubun="+PGUBUN;
		var	goUrl	=	CONTEXTPATH+"/CurriContents.cmd?cmd=lecContentsList&MENUNO=1&pMode=Home";
		document.location = goUrl + param;
	}
	
	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}