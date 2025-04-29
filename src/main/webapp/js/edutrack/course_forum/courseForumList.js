	// 목차리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = "";
	var CURRI_TERM = "";
	var COURSE_NAME = "";
    var USERTYPE = "";
    var COURSELISTSIZE = 1;

	// global attribute
    var MANAGE_FORUM_ID = "";

	function init(systemCode, contextPath, curriCode, curriYear, curriTerm, courseId, courseName, profId, userType, courseListsize ) {
		this.SYSTEMCODE  = systemCode;
		this.CONTEXTPATH = contextPath;
        this.CURRI_CODE  = curriCode;
        this.CURRI_YEAR  = curriYear;
        this.CURRI_TERM  = curriTerm;
		this.COURSE_NAME = courseName;
		this.USERTYPE = userType;
		this.COURSELISTSIZE = courseListsize;

		autoReload();
	}

	// 컨텐츠 밸루세팅
	function setManageValue(vForumId){
		MANAGE_FORUM_ID  = vForumId;
	}

    function autoReload(){

        viewProgress('courseForumInfoList','block',CONTEXTPATH, SYSTEMCODE,'Y');

        var pCourseId = DWRUtil.getValue("pCourseId");

        CourseForumWork.courseForumInfoListAuto(f.curPage.value, CURRI_CODE, CURRI_YEAR, CURRI_TERM, pCourseId, autoReloadCallback);
	}


	// 페이징처리
	function goPage(page,type){
		if(page == null || page <= 0) return;
		document.forms["f"].elements["curPage"].value = page;
		autoReload();
	}


	// 리스트 뿌려주기
	function autoReloadCallback(data){
		var rowLength = 0;
	  	var paggingStr = data.pagging;
	  	var dataList = data.dataList;
		if(dataList != null) rowLength = dataList.length;

	  	//viewAutoButton('C',rowLength);
	  	var forumListObj = $("courseForumInfoList");
	  	var paggingObj = $("getPagging");
	  	var objStr = "<table width='670' align='center' border='0' >";


	  	// 페이징 처리..
	  	if(paggingStr != null && paggingStr !=''){
			paggingObj.innerHTML = paggingStr;
			paggingObj.style.display = "block";
		}

	  	if(rowLength == 0){
		  	objStr += "<tr><td class='s_tab04_cen'>※ 개설된 토론방이 없습니다. </td></tr>"
			       + "<tr class='s_tab03'><td colspan='20'></td></tr>";
	  	}else{

		  	//var no = 0;
            var totalCount = data.totalDataCount;

		  	for(i=0;i<rowLength;i++){
    			var courseForumDto = dataList[i];

				courseId    = courseForumDto.courseId;
		    	var curriCode   = courseForumDto.curriCode;
		    	var curriYear   = courseForumDto.curriYear;
		    	var curriTerm   = courseForumDto.curriTerm;
		    	var courseName  = courseForumDto.courseName;
		    	var forumId     = courseForumDto.forumId;
		    	var subForumId  = courseForumDto.subForumId;
		    	var subject     = courseForumDto.subject;
		    	var openYn      = (courseForumDto.openYn =="Y" ? "공개" : "비공개" );
		    	var registYn    = (courseForumDto.registYn =="Y" ? "등록" : "미등록" );
			    var forumType   = courseForumDto.forumType;
			    var startDate   = courseForumDto.startDate;
		    	var endDate     = courseForumDto.endDate;
		    	var regDate     = courseForumDto.regDate;
		    	var joinForumYN     = courseForumDto.joinForumYN;

			    var startEndDate   = startDate + "-" + endDate ;

			    //tmpForumId = (USERTYPE=="S" && forumType=="T") ? subForumId : forumId ;


			    if(forumType == "A"){
					forumType  = "전체";
				}else{
				    if(USERTYPE!="S"){
					    forumType  = "<a href=\"#\" style='cursor:pointer' onmousedown=\"setManageValue('"+forumId+"'); goTeamMange('2');\")>팀별</a>";
					}else{
					    forumType  = "팀별";
					}
				}


				objStr += "\n<tr onMouseOver=\"this.className='tab_over'\" onMouseOut=\"this.className='tab_out'\">";

				if(USERTYPE!="S"){
    				objStr += " \n<td width=050 class='s_tab04_cen'>"+(totalCount--)+"</td>"
        					+ " \n<td width=1 ></td>"
        					//+ " \n<td width=120 class='s_tab04'>"+courseName+"</td>"
        					//+ " \n<td width=1 ></td>"
        					+ " \n<td width=210 class='s_tab04_cen'><a href=\"javascript:goEdit('"+courseId+"','"+forumId+"')\">"+subject+"</a></td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=150 class='s_tab04_cen'>"+startEndDate+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=070 class='s_tab04_cen'>"+forumType+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=070 class='s_tab04_cen'>"+openYn+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=070 class='s_tab04_cen'>"+registYn+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=050 class='s_tab04_cen'><a href=\"javascript:goView('"+courseId+"','"+forumId+"')\">입장</a></td>";
    			}
    			else {

    				objStr += " \n<td width=50 class='s_tab04_cen'>"+(totalCount--)+"</td>"
        					+ " \n<td width=1 ></td>";

        					if(COURSELISTSIZE>1){
        			objStr += " \n<td width=75 class='s_tab04'>"+courseName+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=190 class='s_tab04'><a href=\"javascript:goView('"+courseId+"','"+forumId+"')\">"+subject+"</a></td>"
        					+ " \n<td width=1 ></td>";
        					} else {
        			objStr += " \n<td width=310 class='s_tab04_cen'><a href=\"javascript:goView('"+courseId+"','"+forumId+"')\">"+subject+"</a></td>"
        					+ " \n<td width=1 ></td>";
        					}
        			objStr += " \n<td width=150 class='s_tab04_cen'>"+startEndDate+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=80 class='s_tab04_cen'>"+forumType+"</td>"
        					+ " \n<td width=1 ></td>"
        					+ " \n<td width=80 class='s_tab04_cen'><a href=\"javascript:goForum('"+courseId+"','"+forumId+"','"+joinForumYN+"')\">입장</a></td>";

    			}

				objStr += "\n</tr>"
				       + "\n<tr class='s_tab03'><td colspan='20'></td></tr>";

			} // end of for

		}
		objStr += "\n</table>";
		forumListObj.innerHTML = objStr;
		forumListObj.style.display = "block";
	 }


   function goView(pCourseId, pForumId)
   {
		document.location = CONTEXTPATH+"/CourseForumInfo.cmd?cmd=courseForumInfoView&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }


	function goAdd() {
	    var pCourseId = DWRUtil.getValue("pCourseId");

		document.location = CONTEXTPATH+"/CourseForumInfo.cmd?cmd=courseForumInfoWrite&pCourseId="+pCourseId+"&pRegMode=Add";
	}

	function goEdit(pCourseId, pForumId){
		document.location = CONTEXTPATH+"/CourseForumInfo.cmd?cmd=courseForumInfoWrite&pCourseId="+pCourseId+"&pForumId="+pForumId+"&pRegMode=Edit";
	}


   function goForum(pCourseId, pForumId, pJoinForumYN){
      if(pJoinForumYN=="N"){
        alert("토론이 시작 되지 않았습니다.");
        return;
      }

      document.location = CONTEXTPATH+"/CourseForumContents.cmd?cmd=courseForumContentsPagingList&pConnectYN=Y&pCourseId="+pCourseId+"&pForumId="+pForumId;
   }


   function goResult() {
	    var pCourseId = DWRUtil.getValue("pCourseId");

        var url = CONTEXTPATH+"/CourseForumInfo.cmd?cmd=courseForumResultList&pCourseId="+pCourseId;
        var name = "";
        var features = "width=600,height=550,scrollbars=yes,top=100,left=100";
        var popupWin = window.open(url, name, features);
        centerSubWindow(popupWin, 600, 550);
        popupWin.focus();
  }


	// 파일리스트 띄우기
	function goTeamMange(){
		var vUrl = "";
	    var pCourseId = DWRUtil.getValue("pCourseId");

		vUrl = CONTEXTPATH+"/CourseForumTeam.cmd?cmd=courseForumTeamList&pCourseId="+pCourseId+"&pForumId="+MANAGE_FORUM_ID;

		location.href = vUrl ;
	}


	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}
