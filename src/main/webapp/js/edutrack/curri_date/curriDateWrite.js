
	function checkHakgiTerm() {
		var f = document.Input;
		var hakgi = f.pHakgiTerm.value;

		if(hakgi !=null && hakgi !='') {
			CurriDateWriteWork.checkHakgiTerm(hakgi, {
				callback:function(data) {
					var hakgiCnt = data;
					if(hakgiCnt != '0') {
						viewMessage();
					} else {
						return;
					}
				}	//end function(data)
			});
		} else {
			return;
		}
	}

	function viewMessage() {
		var f = document.Input;
	    var hakgi = f.pHakgiTerm.value;
		alert("'"+hakgi+"'는 사용중인 학기입니다.\n다른 학기를 입력해 주세요");
		f.pHakgiTerm.value ="";
		new Effect.Highlight("pHakgiTerm");
		f.pHakgiTerm.focus();
	}


