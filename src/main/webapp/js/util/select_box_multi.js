function addItem() {
	var selnum = 0;
	for (var j = 0; j < document.AddForm.original.options.length; j++) {
		 if ( document.AddForm.original.options[j].selected == true) {
			selnum = selnum + 1
		 }
	}

	if (selnum > 0) {
		for (var i = 0; i <  document.AddForm.original.options.length; i++) {
			var opt =  document.AddForm.original.options[i];
			if (opt.selected == true) {
				loc = document.AddForm.add.length;
				var temp = document.AddForm.original.options[i].text;
				var temp2 = document.AddForm.original.options[i].value;
				document.AddForm.add.options[loc] =  new Option(temp,temp2);
			}
		}  // end of for
	} else {
		alert("이동할 항목이 선택되지 않았습니다.")
	}
	delOriginal();
}


function addOriginal() {
	for (var i = 0; i <  document.AddForm.add.options.length; i++) {
		var opt =  document.AddForm.add.options[i];
		if (opt.selected == true) {
			loc = document.AddForm.original.length;
			var temp = document.AddForm.add.options[i].text;
			var temp2 = document.AddForm.add.options[i].value;
			document.AddForm.original.options[loc] =  new Option(temp,temp2);
		}
	}
}


function delItem() {

	addOriginal();
	var selnum = 0;
	for (var j = 0; j < document.AddForm.add.options.length; j++) {
		 if ( document.AddForm.add.options[j].selected == true) {
			selnum = selnum + 1
		 }
	}

	if (selnum > 0) {
		for (var i = 0; i <  document.AddForm.add.options.length; i++) {
			var opt =  document.AddForm.add.options[i];
			if (opt.selected == true) {
				document.AddForm.add.options[i] = null;        
				i = i - 1;
			}
		}  // end of for

	}else {
		alert("이동할 항목이 선택되지 않았습니다.")
		return;
	}
}


function delOriginal() {

	for (var i = 0; i <  document.AddForm.original.options.length; i++) {
		var opt =  document.AddForm.original.options[i];
		if (opt.selected == true) {
			document.AddForm.original.options[i] = null;        
			i = i - 1;
		}
	}
}

