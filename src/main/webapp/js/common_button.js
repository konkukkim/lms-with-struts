/*******************************************************************************
	버튼 생성 스크립트

	사용법:
		Button(value, func, icon);
			value: 버튼 텍스트
			func: 버튼을 클릭했을때 호출할 함수
			icon: 버튼에 표시할 아이콘 이미지명
				이미지는 반드시 지정된 경로(lmsIconPath)에 있어야 한다.)
			target: 타이틀
		ex) Button("확 인", "function1('1234')", "bttn_ok.gif", "타이틀");

	Author: Kim Kyoung shil
*******************************************************************************/

// 버튼 이미지 경로
var buttonImgPath		= "../img/1/button_img/";

// 아이콘 이미지 경로
var lmsIconPath			= "../img/1/button_icon/";



// 버튼 폰트 지정
var buttonFontSize		= "9pt";
var buttonFontFamily	= "돋움";

// 버튼3 폰트 지정
var buttonFontSize3		= "9pt";
var buttonFontColor3	= "#606060";

// 버튼5 폰트 지정
var buttonFontSize5		= "8pt";
var buttonFontColor5	= "#606060";

var buttonLeftImg1		= new Image();
var buttonLeftImg2		= new Image();
var buttonCenterImg1	= new Image();
var buttonCenterImg2	= new Image();
var buttonRightImg1		= new Image();
var buttonRightImg2		= new Image();
buttonLeftImg1.src		= buttonImgPath+"btn_bg_left.gif";
buttonLeftImg2.src		= buttonImgPath+"btn_bg_left2.gif";
buttonCenterImg1.src	= buttonImgPath+"btn_bg_center.gif";
buttonCenterImg2.src	= buttonImgPath+"btn_bg_center2.gif";
buttonRightImg1.src		= buttonImgPath+"btn_bg_right.gif";
buttonRightImg2.src		= buttonImgPath+"btn_bg_right2.gif";

var button2LeftImg		= new Image();
var button2CenterImg	= new Image();
var button2RightImg		= new Image();
var button2CheckImg1	= new Image();
var button2CheckImg2	= new Image();
button2LeftImg.src		= buttonImgPath+"btn_bg2_left.gif";
button2CenterImg.src	= buttonImgPath+"btn_bg2_center.gif";
button2RightImg.src		= buttonImgPath+"btn_bg2_right.gif";
button2CheckImg1.src	= buttonImgPath+"btn_bg2_check.gif";
button2CheckImg2.src	= buttonImgPath+"btn_bg2_check2.gif";

var button3LeftImg		= new Image();
var button3CenterImg	= new Image();
var button3RightImg		= new Image();
button3LeftImg.src		= buttonImgPath+"btn_bg3_left.gif";
button3CenterImg.src	= buttonImgPath+"btn_bg3_center.gif";
button3RightImg.src		= buttonImgPath+"btn_bg3_right.gif";

var button5LeftImg		= new Image();
var button5CenterImg	= new Image();
var button5RightImg		= new Image();
button5LeftImg.src		= buttonImgPath+"btn_bg5_left.gif";
button5CenterImg.src	= buttonImgPath+"btn_bg5_center.gif";
button5RightImg.src		= buttonImgPath+"btn_bg5_right.gif";


var buttonAddCount = 0;


/**
 * 버튼 생성
 */
function Button(value, func, icon, title) {
	buttonAddCount += 1;
	icon = nullToEmpty(icon);

	var buttonId = "BTTN"+buttonAddCount;
	var buttonStr = "<table border=0 cellspacing=0 cellpadding=0 id='"+buttonId+"_TB'>"
		+ "<tr onclick=\""+func+"\">"
		+ "<td><img id='"+buttonId+"_left' src='"+buttonLeftImg1.src+"'></td>";
	
	if (icon != "" && icon != "null") {
		buttonStr += "<td id='"+buttonId+"_icon' nowrap><img src='"+lmsIconPath+icon+"'></td>";
	}
	
	buttonStr += "<td id='"+buttonId+"_text' nowrap>"+value+"</td>"
		+ "<td><img id='"+buttonId+"_right' src='"+buttonRightImg1.src+"'></td>"
		+ "</tr></table>";

	document.write(buttonStr);
	
	var buttonTbObj = getObject(buttonId+"_TB");
	buttonTbObj.style.display	= "inline";
	buttonTbObj.style.cursor	= "pointer";
	
	var textTd		= getObject(buttonId+"_text");
	var iconTd		= getObject(buttonId+"_icon");
	var bttnLeft	= getObject(buttonId+"_left");
	var bttnRight	= getObject(buttonId+"_right");
	textTd.style.backgroundImage = "url("+buttonCenterImg1.src+")";
	if (iconTd != null) {
		iconTd.style.backgroundImage = "url("+buttonCenterImg1.src+")";
	}
	
	textTd.style.fontSize		= buttonFontSize;
	textTd.style.fontFamily		= buttonFontFamily;
	textTd.style.paddingTop		= "2px";
	textTd.style.paddingLeft	= "5px";
	textTd.style.paddingRight 	= "5px";
	
	buttonTbObj.textTd			= textTd;
	buttonTbObj.iconTd			= iconTd;
	buttonTbObj.bttnLeft		= bttnLeft;
	buttonTbObj.bttnRight		= bttnRight;
	if (nullToEmpty(title) != "") {
		buttonTbObj.title = title;
	}
	
	buttonTbObj.onmouseover		= onButtonImgChange;
	buttonTbObj.onmouseout		= offButtonImgChange;
	buttonTbObj.onselectstart	= function() {return false}
	buttonTbObj.ondragstart		= function() {return false}
	buttonTbObj.oncontextmenu	= function() {return false}	
}


/**
 * 버튼 생성 2
 */
function Button2(value, func, title) {
	buttonAddCount += 1;

	var buttonId = "BTTN"+buttonAddCount;
	var buttonStr = "<table border=0 cellspacing=0 cellpadding=0 id='"+buttonId+"_TB'>"
		+ "<tr onclick=\""+func+"\">"
		+ "<td><img id='"+buttonId+"_left' src='"+button2LeftImg.src+"'></td>";
	
	buttonStr += "<td id='"+buttonId+"_icon' nowrap><img id='"+buttonId+"_check' src='"+button2CheckImg1.src+"'></td>";
	
	buttonStr += "<td id='"+buttonId+"_text' nowrap>"+value+"</td>"
		+ "<td><img id='"+buttonId+"_right' src='"+button2RightImg.src+"'></td>"
		+ "</tr></table>";

	document.write(buttonStr);
	
	var buttonTbObj = getObject(buttonId+"_TB");
	buttonTbObj.style.display	= "inline";
	buttonTbObj.style.cursor	= "pointer";
	
	var textTd		= getObject(buttonId+"_text");
	var iconTd		= getObject(buttonId+"_icon");
	textTd.style.backgroundImage = "url("+button2CenterImg.src+")";
	iconTd.style.backgroundImage = "url("+button2CenterImg.src+")";
	
	textTd.style.fontSize		= buttonFontSize;
	textTd.style.fontFamily		= buttonFontFamily;
	textTd.style.paddingTop		= "1px";
	textTd.style.paddingLeft	= "1px";
	textTd.style.paddingRight 	= "1px";
	
	buttonTbObj.textTd			= textTd;
	buttonTbObj.iconTd			= iconTd;
	buttonTbObj.bttnCheck		= getObject(buttonId+"_check");
	if (nullToEmpty(title) != "") {
		buttonTbObj.title = title;
	}
	
	buttonTbObj.onmouseover		= onButton2ImgChange;
	buttonTbObj.onmouseout		= offButton2ImgChange;
	buttonTbObj.onselectstart	= function() {return false}
	buttonTbObj.ondragstart		= function() {return false}
	buttonTbObj.oncontextmenu	= function() {return false}	
}


/**
 * 버튼 생성 3
 */
function Button3(value, func, title) {
	buttonAddCount += 1;

	var buttonId = "BTTN"+buttonAddCount;
	var buttonStr = "<table border=0 cellspacing=0 cellpadding=0 id='"+buttonId+"_TB'>"
		+ "<tr onclick=\""+func+"\">"
		+ "<td><img id='"+buttonId+"_left' src='"+button3LeftImg.src+"'></td>"
		+ "<td background='"+button3CenterImg.src+"' align='top'><div id='"+buttonId+"_text'>"+value+"</div></td>"
		+ "<td><img id='"+buttonId+"_right' src='"+button3RightImg.src+"'></td>"
		+ "</tr></table>";

	document.write(buttonStr);
	
	var buttonTbObj = getObject(buttonId+"_TB");
	buttonTbObj.style.display	= "inline";
	buttonTbObj.style.cursor	= "pointer";
	
	var textTd		= getObject(buttonId+"_text");
	textTd.style.fontSize		= buttonFontSize3;
	textTd.style.color			= buttonFontColor3;
	textTd.style.fontFamily		= buttonFontFamily;
	textTd.style.height			= (button3LeftImg.height-2)+"px";
	textTd.style.overflow		= "hidden";
	textTd.style.paddingTop		= "5px";
	textTd.style.paddingLeft	= "2px";
	textTd.style.paddingRight 	= "2px";
	if (nullToEmpty(title) != "") {
		buttonTbObj.title = title;
	}
	
	buttonTbObj.onselectstart	= function() {return false}
	buttonTbObj.ondragstart		= function() {return false}
	buttonTbObj.oncontextmenu	= function() {return false}	
}


/**
 * 버튼 생성 4 (일반적인 이미지 버튼)
 */
function Button4(func, imgName, title) {
	buttonAddCount += 1;

	var buttonId = "BTTN"+buttonAddCount;
	var buttonStr = "<table border=0 cellspacing=0 cellpadding=0 id='"+buttonId+"_TB'>"
		+ "<tr onclick=\""+func+"\">"
		+ "<td><img src='"+buttonImgPath+imgName+"'></td>"
		+ "</tr></table>";

	document.write(buttonStr);
	
	var buttonTbObj = getObject(buttonId+"_TB");
	buttonTbObj.style.display	= "inline";
	buttonTbObj.style.cursor	= "pointer";
	if (nullToEmpty(title) != "") {
		buttonTbObj.title = title;
	}
	
	buttonTbObj.onselectstart	= function() {return false}
	buttonTbObj.ondragstart		= function() {return false}
	buttonTbObj.oncontextmenu	= function() {return false}
}


/**
 * 버튼 생성 5
 */
function Button5(value, func, title) {
	buttonAddCount += 1;

	var buttonId = "BTTN"+buttonAddCount;
	var buttonStr = "<table border=0 cellspacing=0 cellpadding=0 id='"+buttonId+"_TB'>"
		+ "<tr onclick=\""+func+"\">"
		+ "<td><img id='"+buttonId+"_left' src='"+button5LeftImg.src+"'></td>"
		+ "<td background='"+button5CenterImg.src+"' align='top'><div id='"+buttonId+"_text'>"+value+"</div></td>"
		+ "<td><img id='"+buttonId+"_right' src='"+button5RightImg.src+"'></td>"
		+ "</tr></table>";

	document.write(buttonStr);
	
	var buttonTbObj = getObject(buttonId+"_TB");
	buttonTbObj.style.display	= "inline";
	buttonTbObj.style.cursor	= "pointer";
	
	var textTd		= getObject(buttonId+"_text");
	textTd.style.fontSize		= buttonFontSize5;
	textTd.style.color			= buttonFontColor5;
	textTd.style.fontFamily		= buttonFontFamily;
	textTd.style.height			= (button5LeftImg.height-2)+"px";
	textTd.style.overflow		= "hidden";
	textTd.style.paddingTop		= "4px";
	textTd.style.paddingLeft	= "2px";
	textTd.style.paddingRight 	= "2px";
	if (nullToEmpty(title) != "") {
		buttonTbObj.title = title;
	}
	
	buttonTbObj.onselectstart	= function() {return false}
	buttonTbObj.ondragstart		= function() {return false}
	buttonTbObj.oncontextmenu	= function() {return false}	
}

/**
 * 마우스 오버시 버튼 변경
 */
function onButtonImgChange() {
	this.textTd.style.backgroundImage = "url("+buttonCenterImg2.src+")";
	if (this.iconTd != null) {
		this.iconTd.style.backgroundImage = "url("+buttonCenterImg2.src+")";
	}
	this.bttnLeft.src	= buttonLeftImg2.src;
	this.bttnRight.src	= buttonRightImg2.src;
}


/**
 * 마우스 아웃시 버튼 변경
 */
function offButtonImgChange() {
	this.textTd.style.backgroundImage = "url("+buttonCenterImg1.src+")";
	if (this.iconTd != null) {
		this.iconTd.style.backgroundImage = "url("+buttonCenterImg1.src+")";
	}
	this.bttnLeft.src	= buttonLeftImg1.src;
	this.bttnRight.src	= buttonRightImg1.src;
}


/**
 * 마우스 오버시 버튼 변경 (버튼2)
 */
function onButton2ImgChange() {
	this.bttnCheck.src	= button2CheckImg2.src;
}


/**
 * 마우스 아웃시 버튼 변경 (버튼2)
 */
function offButton2ImgChange() {
	this.bttnCheck.src	= button2CheckImg1.src;
}