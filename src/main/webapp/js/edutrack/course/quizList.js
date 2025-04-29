	// ���񸮽�Ʈ
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

	 // ��üũ
	 function formCheck(){
		var f = document.Input;
		var quizCount = f.pQuizCount.value;
		var quizPoint = f.pQuizPoint.value;
		var quizCnt = f.pCnt.value;

		if(isNumber(quizCount)){
			if(parseInt(quizCount) > parseInt(quizCnt)){
				alert("ǥ�ù������� ������ �������� Ŭ �� �����ϴ�.");
				new Effect.Highlight("pQuizCount");
				f.pQuizCount.focus();
				return false;
			}
		}else{
			alert("���ڸ� �Է°��� �մϴ�.");
			f.pQuizCount.value = 0;
			new Effect.Highlight("pQuizCount");
			f.pQuizCount.focus();
			return false;
		}

		if(isNumber(quizPoint)){
			if(parseInt(quizPoint) > parseInt(quizCnt)){
				alert("���������� ������ �������� Ŭ �� �����ϴ�.");
				new Effect.Highlight("pQuizPoint");
				f.pQuizPoint.focus();
				return false;
			}
		}else{
			alert("���ڸ� �Է°��� �մϴ�.");
			f.pQuizPoint.value = 0;
			new Effect.Highlight("pQuizPoint");
			f.pQuizPoint.focus();
			return false;
		}

		if(parseInt(quizPoint) > parseInt(quizCount)){
			alert("���������� ǥ�ù��������� Ŭ �� �����ϴ�.");
			new Effect.Highlight("pQuizPoint");
			f.pQuizPoint.focus();
			return false;
		}

		return true;
	 }

	// �򰡼��� �Է� �� ����
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

	// �򰡼��� ���
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

	// �򰡼��� �Է� �� ����
	function closeConfig(){
		$("inputBox").style.display = "none";
	}

    // ������ ������� �̵�
	function goContentsList(){
		document.location.href = CONTEXTPATH+"/Contents.cmd?cmd=contentsList&pMode=MyPage&pCourseId="+COURSE_ID;
	}
	// �����߰�
	function goAdd(){
		document.location.href = CONTEXTPATH+"/Quiz.cmd?cmd=quizWrite&pMode=MyPage&pCourseId="+COURSE_ID+"&pContentsId="+CONTENTS_ID+"&pQuizType=K";
	}

	//����ó�� �Լ�
	function errorHandler(errMsg) {
	    alert(errMsg);
	}