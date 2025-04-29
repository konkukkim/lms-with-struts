	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var CURRI_CODE = "";
	var CURRI_YEAR = "";
	var CURRI_TERM = "";
	var COURSE_ID = "";
	var CONTENTS_ID = "";

	function init(systemCode,contextPath,courseId,contentsId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.CONTENTS_ID = contentsId;

		var f = document.Input;
		this.CURRI_CODE = f.pCurriCode.value;
		this.CURRI_YEAR = f.pCurriYear.value;
		this.CURRI_TERM = f.pCurriTerm.value;
	}

	 // 폼체크
	 function formCheck(){
		var f = document.Input;
		var quizCount = f.pQuizCount.value;
		var quizPoint = f.pQuizPoint.value;
		var quizCnt = f.pCnt.value;

		if(isNumber(quizCount)){
			if(parseInt(quizCount) > parseInt(quizCnt)){
				alert("표시문제수가 출제된 문제보다 클 수 없습니다.");
				new Effect.Highlight("pQuizCount");
				f.pQuizCount.focus();
				return false;
			}
		}else{
			alert("숫자만 입력가능 합니다.");
			f.pQuizCount.value = 0;
			new Effect.Highlight("pQuizCount");
			f.pQuizCount.focus();
			return false;
		}

		if(isNumber(quizPoint)){
			if(parseInt(quizPoint) > parseInt(quizCnt)){
				alert("통과정답수가 출제된 문제보다 클 수 없습니다.");
				new Effect.Highlight("pQuizPoint");
				f.pQuizPoint.focus();
				return false;
			}
		}else{
			alert("숫자만 입력가능 합니다.");
			f.pQuizPoint.value = 0;
			new Effect.Highlight("pQuizPoint");
			f.pQuizPoint.focus();
			return false;
		}

		if(parseInt(quizPoint) > parseInt(quizCount)){
			alert("통과정답수가 표시문제수보다 클 수 없습니다.");
			new Effect.Highlight("pQuizPoint");
			f.pQuizPoint.focus();
			return false;
		}

		return true;
	 }

	// 평가설정 입력 폼 열기
	function writeConfig(){
		CurriQuizWork.curriQuizConfigContentsWrite(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, CONTENTS_ID,{
			callback:function(data) {
				if(data != null){
					var f = document.Input;
					var contentsDTO = data;
					f.pQuizCount.value = contentsDTO.quizCount;
					f.pQuizPoint.value = contentsDTO.quizPoint;
					Effect.Appear("inputBox");
			  	}else{
			  		return;
			  	}
			}
		});
	}

	// 평가설정 등록
	function registConfig(){
		var f = document.Input;
		var quizCount = f.pQuizCount.value;
		var quizPoint = f.pQuizPoint.value;

		if(formCheck()){
			CurriQuizWork.curriQuizConfigContentsRegist(CURRI_CODE, CURRI_YEAR, CURRI_TERM, COURSE_ID, CONTENTS_ID,quizCount,quizPoint,{
				callback:function(data) {
					var result = data;
				  	if(result != '0')
						closeConfig	();
				  	else
				  		return;
				}
			});
		}else
			return;
	}

	// 평가설정 입력 폼 열기
	function closeConfig(){
		$("inputBox").style.display = "none";
	}

    // 컨텐츠 목록으로 이동
	function goContentsList(){
		document.location.href = CONTEXTPATH+"/CurriContents.cmd?cmd=curriContentsList&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID;
	}
	function goAdd(){
		document.location.href = CONTEXTPATH+"/CurriQuiz.cmd?cmd=curriQuizWrite&pCurriCode="+CURRI_CODE+"&pCurriYear="+CURRI_YEAR+"&pCurriTerm="+CURRI_TERM+"&pCourseId="+COURSE_ID+"&pContentsId="+CONTENTS_ID+"&pQuizType=K";
	}
/*
	function config_contents(){
		var page = "<%=CONTEXTPATH%>/CurriQuiz.cmd?cmd=curriQuizConfigContentsForm&pCurriCode=<%=pCurriCode%>&pCurriYear=<%=pCurriYear%>&pCurriTerm=<%=pCurriTerm%>&pCourseId=<%=pCourseId%>&pCourseName=<%=pCourseName%>&pContentsId=<%=pContentsId%>&pContentsName=<%=pContentsName%>";
		ShowConfig = window.open(page,'config','left=155,top=110,toolbar=0,directories=0,status=0,menubar=0,width=450,height=200,scrollbars=no,resizable=no');
	}
*/
	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}