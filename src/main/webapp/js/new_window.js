//	윈도우즈 새창 오픈
	function openNewWindow(url,vWidth,vHeight,win_name) {
		var wWidth, wHeight, wTop, wLeft;

		if (vWidth) wWidth = vWidth;
		else wWidth = screen.width;

		if (vHeight) wHeight = vHeight;
		else wHeight = screen.height;

		wTop = screen.height / 2 - wHeight / 2;
		wLeft = screen.width / 2 - wWidth / 2;
		var new_windows = window.open(url,win_name,'left='+wLeft+',top='+wTop+',width='+wWidth+',height='+wHeight+',toolbar=no,scrollbars=yes,resizable=no');

	}

//	모달 다이아로그 새창 오픈
	function openModalWindow(url,vWidth,vHeight,vTop,vLeft) {
		var wWidth, wHeight, wTop, wLeft;

		if (vWidth) wWidth = vWidth;
		else wWidth = screen.width;

		if (vHeight) wHeight = vHeight;
		else wHeight = screen.height;

		if (document.layers) {
			alert("This application designed for Internet Explorer Only!");
			return false;
		}
		else {
			if(vTop=='t') {
				wTop = 0;
			}
			else if(vTop=='b') {
				wTop = screen.height - wHeight;
			}
			else if(vTop) {
				wTop = vTop;
			}
			else {
				wTop = screen.height / 2 - wHeight / 2;
			}

			if(vLeft=='l') {
				wLeft = 0;
			}
			else if(vLeft=='r') {
				wLeft = screen.width - wWidth;
			}
			else if(vLeft) {
				wLeft = vLeft;
			}
			else {
				wLeft = screen.width / 2 - wWidth / 2;
			}
		}
		showModalDialog(url,window, "dialogHeight:"+wHeight+"px; dialogWidth:"+wWidth+"px; dialogTop:"+wTop+"px; dialogLeft:"+wLeft+"px; help:no; resizable:yes; status:no; scroll:yes; center:Yes;");
	}


//	모달 다이아로그 인쇄창 오픈
	function openPrintWindow(url,vWidth,vHeight,vTop,vLeft) {
		var wWidth, wHeight, wTop, wLeft;

		if (vWidth) wWidth = vWidth;
		else wWidth = screen.width;

		if (vHeight) wHeight = vHeight;
		else wHeight = screen.height;

		if (document.layers) {
			alert("This application designed for Internet Explorer Only!");
			return false;
		}
		else {
			if(vTop=='t') {
				wTop = 0;
			}
			else if(vTop=='b') {
				wTop = screen.height - wHeight;
			}
			else if(vTop) {
				wTop = vTop;
			}
			else {
				wTop = screen.height / 2 - wHeight / 2;
			}

			if(vLeft=='l') {
				wLeft = 0;
			}
			else if(vLeft=='r') {
				wLeft = screen.width - wWidth;
			}
			else if(vLeft) {
				wLeft = vLeft;
			}
			else {
				wLeft = screen.width / 2 - wWidth / 2;
			}
		}
		showModalDialog(url,window, "dialogHeight:"+wHeight+"px; dialogWidth:"+wWidth+"px; dialogTop:"+wTop+"px; dialogLeft:"+wLeft+"px;help:no;resizable:yes;edge:raised;status:no;scroll:yes;center:yes;");
	}