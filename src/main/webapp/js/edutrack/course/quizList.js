	// 과목리스트
	var CONTEXTPATH = "";
	var SYSTEMCODE = "1";
	var COURSE_ID = "";
	var CONTENTS_ID = "";

	function init(systemCode,contextPath,courseId,contentsId) {
		this.SYSTEMCODE = systemCode;
		this.CONTEXTPATH = contextPath;
		this.COURSE_ID = courseId;
		this.CONTENTS_ID = contentsId;
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
		QuizWork.quizConfigContentsWrite(COURSE_ID, CONTENTS_ID,{
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
			QuizWork.quizConfigContentsRegist(COURSE_ID, CONTENTS_ID,quizCount,quizPoint,{
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
		document.location.href = CONTEXTPATH+"/Contents.cmd?cmd=contentsList&pMode=MyPage&pCourseId="+COURSE_ID;
	}
	// 문제추가
	function goAdd(){
		document.location.href = CONTEXTPATH+"/Quiz.cmd?cmd=quizWrite&pMode=MyPage&pCourseId="+COURSE_ID+"&pContentsId="+CONTENTS_ID+"&pQuizType=K";
	}

	//오류처리 함수
	function errorHandler(errMsg) {
	    alert(errMsg);
	}