
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
		alert("'"+hakgi+"'�� ������� �б��Դϴ�.\n�ٸ� �б⸦ �Է��� �ּ���");
		f.pHakgiTerm.value ="";
		new Effect.Highlight("pHakgiTerm");
		f.pHakgiTerm.focus();
	}


