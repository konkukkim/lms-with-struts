/*******************************************************************************
	Tree 생성을 위한 스크립트

	사용법:
	1. 트리 객체를 생성한다. 
		var TREENAME = Tree(targetName, text, value, color, func, attribute);
			targetName:	트리를 추가할 대상 객체명 또는 객체 (생략할 경우 현재위치에 표시)
			text:		트리 루트 노드에 표시할 텍스트
			value:		트리 루트 노드값
			color:		트리 루트 노드 색상
			func:		트리 아이템을 선택했을때 실행할 함수, 파라메터 없는 함수.
			attribute:	트리의 속성
				opendepth:	초기에 오픈할 노드의 깊이(단계)
				icontype:	트리의 아이콘 표시형식 (생략할 경우 'item'으로 지정
					auto: 자동, 부모노드와 자식노드를 구분해서 표시,
					folder: 아이콘을 모두 폴더아이콘으로 표시, 
					icon: 기본 아이콘 표시(icon.gif)
					item: 아이콘을 모두 아이템 아이콘으로 표시(item.gif)
					foldericon: 부모노드를 폴더아이콘으로 표시하고, 자식노드는 아이콘으로 표시
				foldertype:	폴더의 표시형식(
					simple: 라인을 그리지 않고 폴더를 간단하게 표시 
					normal: 폴더의 라인을 표시, 아이템의 갯수가 많아지면 속도가 느려짐.
				rootdisplay: 루트노드의 표시여부(yes:표시, no:표시안함)
				ontitle: 타이틀 풍선도움말 표시 여부 (yes/no, 기본값은 "no")
		ex) tree1 = Tree("treeBox1", "트리1", "root", "click1()", "openDepth=1,icontype=auto,folder=simple,rootdisplay=yes,ontitle=yes");

	2. 트리 아이템을 추가한다.
		var ITEMNAME = addTreeItem(parentNode, itemText, itemValue, itemStyle, iconName)
			parentNode:	추가할 대상 객체(부모 노드, 트리 객체 또는 아이템 객체)
			itemText:	트리 아이템이 표시할 텍스트
			value:		아이템값
			color:		아이템 색상 (기본값은 검정)
			icon:		아이템에 표시할 아이콘 이미지명 (이미지는 "treeImgPath"에 지정된 경로에 있어야 하며,
						이미지명만 입력)
			selected:	아이템 기본 선택(yes|no 값이 없으면 "no"로 지정, 주의:테이블에서 1개 이하로 선택되어야 한다.)
		ex) item1 = addTreeItem(TREENAME, "제1장", "값1", "blue", "folder.gif", "no");
	
	3. 선택한 아이템값 가져오기
		getTreeItemValue(treeObj);
			treeObj:	트리 객체 (1.에서 생성한 TREENAME) 
		ex) var value = getTreeItemValue(TREENAME);
			2.의 예에서 생성한 아이템을 선택했을 경우 value는 "값1"이 된다.

	4. 선택한 아이템 텍스트 가져오기
		getTreeItemText(treeObj);
			treeObj:	트리 객체 (1.에서 생성한 TREENAME) 
		ex) var value = getTreeItemValue(TREENAME);
			2.의 예에서 생성한 아이템을 선택했을 경우 value는 "제1장"이 된다.

	5. 선택한 아이템 단계 가져오기
		getTreeItemDepth(treeObj);

	6. 선택한 아이템 상위 노드 값 가져오기
		getTreeItemParentValue(treeObj);


	Author: Kim Kyoung shil
*******************************************************************************/

// 아이콘 이미지 경로
var treeImgPath				= "/img/1/icon/tree/";

// 트리 폰트 지정
var treeFontSize			= "9pt";
var treeFontFamily			= "굴림";

// line 높이 지정
var treeLineHeight			= "18px";

// 마우스 오버시 배경색
var treeOverColor			= "lightgrey";

var treeRootImg				= new Image();					// Tree Root 아이콘
treeRootImg.src				= treeImgPath+"folder2.gif";

var treeFolderImg			= new Image();					// 폴더 아이콘
treeFolderImg.src			= treeImgPath+"folder2.gif";

var treeItemImg				= new Image();					// 아이템 아이콘
treeItemImg.src				= treeImgPath+"item2.gif";

var treeIconImg				= new Image();					// 아이콘
treeIconImg.src				= treeImgPath+"icon.gif";

var treeBlankImg			= new Image();
treeBlankImg.src			= treeImgPath+"blank.gif";

var treeFolderPlusImg		= new Image();
treeFolderPlusImg.src		= treeImgPath+"plus_simple.gif";

var treeFolderMinusImg		= new Image();
treeFolderMinusImg.src		= treeImgPath+"minus_simple.gif";

var treePlusNodeImg			= new Image();
treePlusNodeImg.src			= treeImgPath+"plus_node.gif";

var treePlusEndImg			= new Image();
treePlusEndImg.src			= treeImgPath+"plus_end.gif";

var treeMinusNodeImg		= new Image();
treeMinusNodeImg.src		= treeImgPath+"minus_node.gif";

var treeMinusEndImg			= new Image();
treeMinusEndImg.src			= treeImgPath+"minus_end.gif";

var treeLineNodeImg			= new Image();
treeLineNodeImg.src			= treeImgPath+"line_node.gif";

var treeLineEndImg			= new Image();
treeLineEndImg.src			= treeImgPath+"line_end.gif";

var treeLineImg				= new Image();
treeLineImg.src				= treeImgPath+"line.gif";

var treeAddCount			= 0;


/**
 * 트리객체를 생성한다.
 *
 * @param targetName	트리를 삽입할 대상 객체명 또는 객체 (빈값인 경우 현재위치에 생성된다.)
 * @param text			트리의 루트노드 텍스트
 * @param value			루트 아이템 값
 * @param color			루트 아이템 색상
 * @param func			아이템을 클릭했을 경우 실행할 함수
 * @param attribute		트리 속성
 * 			opendepth	초기에 펼칠 Tree의 단계수
 * 			icontype	트리의 아이템 아이콘 형식(folder:폴더, item:아이템, auto:자동)
 * 			foldertype	폴더 형식 (simple:간단하게(기본값), normal:보통)
 * 			rootdisplay	루트 노드 표시 여부(yes: 표시, no:표시안함, 빈값은 yes)
 *			ontitle		타이틀 풍선도움말 표시 여부 (yes/no, 기본값은 "no")
 * @return newTree		생성된 트리 객체
 */
function Tree(targetName, text, value, color, func, contextmenu, attribute) {
	var openDepth		= getAttributeValue(attribute, "opendepth");
	var iconType		= getAttributeValue(attribute, "icontype");
	var folderType		= getAttributeValue(attribute, "foldertype");
	var rootDisplay		= getAttributeValue(attribute, "rootdisplay");
	var ontitle			= getAttributeValue(attribute, "ontitle");
	
	if (openDepth === "") {
		openDepth = "1";
	}

	if (iconType === "") {
		iconType = "auto";
	}

	if (folderType === "") {
		folderType = "simple";
	}

	if (rootDisplay === "") {
		rootDisplay = "yes";
	}
	
	if (nullToEmpty(color) === "") {
		color = "black";
	}
	
	if (ontitle === "") {
		ontitle = "no";
	}
	ontitle = ontitle.toLowerCase();
	if (ontitle == "yes" || ontitle == "true" || ontitle == "y") {
		ontitle = "yes";
	}
	
	treeAddCount += 1;

	// 새로운 트리객체 생성
	var newTree = null;
	var treeId = "TREE"+treeAddCount;	
	if (targetName !== null && targetName !== "")	{
		newTree = document.createElement("div");
		newTree.id	= treeId;
		
		if (!targetName.id) {
			targetName = getObject(targetName);
		}
		
		targetName.appendChild(newTree);
	}
	else {
		document.write("<div id=\""+treeId+"\"></div>");
		newTree = getObject(treeId);
	}

	newTree.treeId		= treeId;
	newTree.depth		= 0;
	newTree.child		= 1;
	newTree.openDepth	= openDepth;
	newTree.iconType	= iconType;
	newTree.folderType	= folderType;
	newTree.endNodeId	= "";
	newTree.itemNo		= 0;
	newTree.func		= nullToEmpty(func);
	newTree.contextmenu	= nullToEmpty(contextmenu);
	newTree.curItemId	= "";
	newTree.value		= value;
	newTree.ontitle		= ontitle;
	newTree.onselectstart  = function() {return false;};
	newTree.ondragstart    = function() {return false;};
	
	var rootDisplayStyle = "block";
	if (rootDisplay == "no") {
		rootDisplayStyle = "none";
	}

	var onTitle = "";
	if (ontitle == "yes") {
		onTitle = "title=\""+text+"\"";
	}

	var treeContent = ""
		+ "<table border=0 cellpadding=0 cellspacing=0 style='display:"+rootDisplayStyle+"'><tr>"
		+ "<td><img id='"+treeId+"_icon' src='"+treeRootImg.src+"'></td>"
		+ "<td id='"+treeId+"_text' style='white-space:nowrap;cursor:default;padding-top:2px;"
		+ "padding-left:3px;padding-right:3px;font-size:"+treeFontSize+";font-family:"+treeFontFamily+";"
		+ "color:"+color+";cursor:pointer;' "+onTitle+">"+text+"</td>"
		+ "</tr></table>";
	
	newTree.innerHTML = treeContent;

	var newTreeText = getObject(treeId+"_text");
	newTreeText.onmousedown		= selectTreeItem;
	newTreeText.onmouseover		= onTreeItemColor;
	newTreeText.onmouseout		= offTreeItemColor;
	newTreeText.treeId 			= treeId;
	newTreeText.itemId 			= treeId;
	newTreeText.color			= color;

	var childNodeSet	= document.createElement("div");
	childNodeSet.id		= treeId+"_sub";
	childNodeSet.style.display = "block";
	newTree.appendChild(childNodeSet);

	return newTree;
}


/**
 * 트리에 아이템을 추가하는 함수
 *
 * @param parentNode	추가할 부모노드 객체
 * @param text			표시할 아이템 텍스트
 * @param value			아이템 값
 * @param color			아이템 텍스트의 색상 (값이 없으면 기본값 적용)
 * @param icon			아이템 아이콘명 (값이 없으면 기본값 적용)
 * @param selected		아이템 기본 선택(yes/no 값이 없으면 "no"로 지정)
 * @return newItem		생성된 아이템 객체
 */
function addTreeItem(parentNode, text, value, color, icon, selected) {
	value				= nullToEmpty(value);
	color				= nullToEmpty(color);
	icon				= nullToEmpty(icon);
	selected			= nullToEmpty(selected).toLowerCase();

	if (color == "") {
		color = "black";
	}
	
	if (selected == "") {
		selected = "no";
	}

	var parentId		= parentNode.id;
	var treeId			= parentNode.treeId;
	var depth			= parentNode.depth;
	var child			= parentNode.child;
	var prevEndNodeId	= parentNode.endNodeId;
	var ontitle			= parentNode.ontitle;

	var curTreeObj		= getObject(treeId);	// 현재 Tree 객체
	var openDepth		= curTreeObj.openDepth;	// 처음에 펼칠 Tree의 단계수
	var iconType		= curTreeObj.iconType;	// 아이콘 형식
	var folderType		= curTreeObj.folderType;
	var itemNo			= curTreeObj.itemNo + 1;
	var childNodeSet	= null;
	curTreeObj.itemNo	= itemNo;

	if (depth != null) {
		depth = depth + 1;
	}

	// Child 노드셋이 없을 경우 생성
	if (child == 0) {
		childNodeSet = document.createElement("div");
		childNodeSet.id		= parentId+"_sub";

		var childNodeSetImg = "";

		if (depth <= parseInt(openDepth)) { 
			childNodeSet.style.display = "block";

			if (folderType == "normal") {
				childNodeSetImg = treeMinusEndImg.src;				
			}
			else {
				childNodeSetImg = treeFolderMinusImg.src;
			}
		}
		else {
			childNodeSet.style.display = "none";

			if (folderType == "normal") {
				childNodeSetImg = treePlusEndImg.src;
			}
			else {
				childNodeSetImg = treeFolderPlusImg.src;
			}
		}

		parentNode.appendChild(childNodeSet);
		parentNode.child = 1;

		var parentChildObj = getObject(parentId+"_child");
		var childImgContent = "<img id='"+parentId+"_childimg' src='"+childNodeSetImg+"' "
							+ "onClick=\"changeTreeChildView(this, '"+parentId+"', '"+folderType+"')\">";
		parentChildObj.innerHTML = childImgContent;

		// Parent Node의 아이콘 변경
		if (iconType == "auto" || iconType == "foldericon") {
			var parentIconName = parentNode.icon;
			if (parentIconName == "") {
				var parentIcon = getObject(parentId+"_icon");
				parentIcon.src = treeFolderImg.src;
			}
		}
	}
	else {
		childNodeSet = getObject(parentId+"_sub");
	}

	// 아이템 객체 생성
	var newItem = document.createElement("div");
	var itemId = treeId+"_"+itemNo;
	newItem.id				= itemId;
	newItem.treeId			= treeId;
	newItem.depth			= depth;
	newItem.child			= 0;
	newItem.endNodeId		= "";
	newItem.icon			= icon;
	newItem.value			= value;
	newItem.ontitle			= ontitle;
	newItem.parentId		= parentId;
	parentNode.endNodeId	= itemId;
	parentNode.child 		= child + 1;
	
	// depth에 따라 공백 또는 line 이미지 계산
	var depthImg = "";
	for (var i=1; i<depth; i+=1) {
		depthImg += "<img id='"+itemId+"_blank_"+i+"' src='"+treeBlankImg.src+"'>";
	}

	var itemImgSrc = "";
	// 아이템에 별도의 아이콘을 지정했을 경우
	if (icon != "") {
		itemImgSrc	= treeImgPath + icon;
	}
	// 아이템에 별도의 아이콘이 지정되지 않았을 경우 기본 아이콘으로 지정
	else {
		// 트리의 아이콘 형식을 "folder"로 지정했을 경우
		if (iconType == "folder") {
			itemImgSrc = treeFolderImg.src;
		}
		// 트리의 아이콘 형식을 "icon"으로 지정했을 경우
		else if (iconType == "icon" || iconType == "foldericon") {
			itemImgSrc = treeIconImg.src;
		}
		// 트리의 아이콘 형식을 "item"으로 지정했을 경우
		else {
			itemImgSrc = treeItemImg.src;
		}
	}

	var haveChildImg = treeBlankImg;

	// 트리에 line을 표시할 경우 이미지 표시 계산
	if (folderType == "normal") {
		if (prevEndNodeId != "") {
			var prevItem			= getObject(prevEndNodeId);
			var prevItemChildImg	= getObject(prevEndNodeId+"_childimg");
			var prevChildValue		= prevItem.child;

			if (prevChildValue == 0) {
				prevItemChildImg.src = treeLineNodeImg.src;
			}
		}
		haveChildImg = treeLineEndImg.src;
	}
	// 트리를 간단하게 표시
	else {
		haveChildImg = treeBlankImg.src;
	}

	var onTitle = "";
	if (ontitle == "yes") {
		onTitle = "title=\""+text+"\"";
	}
	
	var itemStyle = ""
		+ "white-space:nowrap;"
		+ "font-size:"+treeFontSize+";"
		+ "font-family:"+treeFontFamily+";"
		+ "height:"+treeLineHeight+";"
		+ "padding-top:2px;"
		+ "padding-left:3px;"
		+ "padding-right:3px;"
		+ "vertical-align:middle;"
		+ "overflow:hidden;"
		+ "cursor:default;"
		+ "color:"+color+";"
		+ "cursor:pointer;";

	var endNodeId	= parentNode.endNodeId;

	var itemContent = ""
		+ "<table border=0 cellpadding=0 cellspacing=0><tr>"
		+ "<td>"+depthImg+"</td>"
		+ "<td id='"+itemId+"_child'><img id='"+itemId+"_childimg' src='"+haveChildImg+"'></td>"
		+ "<td><img id='"+itemId+"_icon' src='"+itemImgSrc+"'></td>"
		+ "<td id='"+itemId+"_text' style='"+itemStyle+"' "+onTitle+">"+text+"</td>"
		+ "</tr></table>";

	newItem.innerHTML = itemContent;
	childNodeSet.appendChild(newItem);

	var newItemText = getObject(itemId+"_text");
	newItemText.onmousedown = selectTreeItem;
	newItemText.onmouseover	= onTreeItemColor;
	newItemText.onmouseout	= offTreeItemColor;	
	newItemText.treeId 		= treeId;
	newItemText.itemId 		= itemId;
	newItemText.color		= color;

	// line 그리기 호출
	if (folderType == "normal") {
		var prevSiblingObj	= newItem.previousSibling;

		if (prevSiblingObj != null){
			drawTreeLine(prevSiblingObj, depth);
		}	
	}

	// 아이템이 미리 선택되어 있을 경우 처리
	if (selected == "yes" || selected == "y" || selected == "true") {
		if (depth > openDepth) {
			changeSelectedTreeItem(parentId)
		}
		
		curTreeObj.curItemId = itemId;
		onSelectColor(newItemText);
		newItemText.overcolor = "on";	
	}

	return newItem;
}


/**
 * 트리에 라인을 그리는 함수
 */
function drawTreeLine(obj, depth) {
	var list = obj.childNodes;
	var imglist = "";

	if (list != null) {
		for (var i=0; i<list.length; i+=1) {
			if (list.item(i).tagName == "DIV") {
				var itemSubObj = list.item(i);
				var objId = itemSubObj.id;
				var idx = objId.indexOf("_sub");
				var itemId = objId.substring(0, idx)

				if (idx > -1) {
					var itemObj = getObject(itemId);
					var haveChildImg = getObject(itemId+"_childimg");

					if (itemSubObj.style.display == "block") {
						haveChildImg.src = treeMinusNodeImg.src;
					}
					else {
						haveChildImg.src = treePlusNodeImg.src;
					}

					drawTreeLine(itemSubObj, depth);
				}
				else if (objId.indexOf("_value") > -1) {

				}
				else {
					var treeBlankImg = getObject(objId+"_blank_"+depth);
					treeBlankImg.src = treeLineImg.src;

					var subObj = getObject(objId+"_sub");
					if (subObj != null) {
						drawTreeLine(subObj, depth);
					}
				}
			}
		}
	}
}


/**
 * 아이템이 미리 선택되어 있을 경우 처리
 */
function changeSelectedTreeItem(itemId) {
	var itemObj 	= getObject(itemId);
	var depth		= itemObj.depth
	var parentId 	= itemObj.parentId;
	var treeObj		= getObject(itemObj.treeId);
	
	if (depth > 0) {
		var curImg		= getObject(itemId+"_childimg");
		changeTreeChildView(curImg, itemId, treeObj.folderType);
		changeSelectedTreeItem(parentId);
	}
}


/**
 * Child Node들의 표시여부 변경
 */
function changeTreeChildView(curImg, objId, folderType) {
	var subObj			= getObject(objId+"_sub");
	var displayStyle	= subObj.style.display;
	var parentObj		= getObject(objId);
	var nextSibilingObj = parentObj.nextSibling;

	if (displayStyle == "block") {
		offDisplay(subObj);

		if (folderType == "normal") {
			if (nextSibilingObj != null) {
				curImg.src = treePlusNodeImg.src;
			}
			else {
				curImg.src = treePlusEndImg.src;
			}
		}
		else {
			curImg.src = treeFolderPlusImg.src;
		}
	}
	else {
		onDisplay(subObj);
		
		if (folderType == "normal") {
			if (nextSibilingObj != null) {
				curImg.src = treeMinusNodeImg.src;
			}
			else {
				curImg.src = treeMinusEndImg.src;
			}
		}
		else {
			curImg.src = treeFolderMinusImg.src;
		}
	}
}


/**
 * 트리 ITEM 선택
 */
function selectTreeItem(evt) {
	// 마우스 버튼 체크
	var mouseButton = getMouseButton(evt);
	
	var treeObj		= getObject(this.treeId);
	var curItemId	= treeObj.curItemId;
	var func		= treeObj.func;
	var contextmenu	= treeObj.contextmenu;

	if (mouseButton == "left" || mouseButton == "right") {	
		if (curItemId != this.itemId && curItemId != "") {
			var prevItemText = getObject(curItemId+"_text");
			offSelectColor(prevItemText, prevItemText.color);
			prevItemText.overcolor = null;
		}
		treeObj.curItemId = this.itemId;
		onSelectColor(this);
		this.overcolor = "on";
	}

	// 마우스 왼쪽 버튼 클릭
	if (mouseButton == "left" && func != "") {
		eval(func);
	}
	// 마우스 오른쪽 버튼 클릭
	else if (mouseButton == "right" && contextmenu !== "") {
		showContextMenu(eval(contextmenu));
	}
}


/**
 * ITEM의 Value를 반환
 * @param treeObj	트리 객체
 */
function getTreeItemValue(treeObj) {
	var itemObj		= getObject(treeObj.curItemId);
	var value = "";
	if (itemObj) {
		value = itemObj.value;
	}
	return value;
}


/**
 * ITEM의 Text를 반환
 * @param treeObj	트리 객체
 */
function getTreeItemText(treeObj) {
	var itemTextObj	= getObject(treeObj.curItemId+"_text");
	var value = "";
	if (itemTextObj) {
		value = itemTextObj.innerHTML
	}
	return value;
}


/**
 * ITEM의 depth 반환
 * @param treeObj	트리 객체
 */
function getTreeItemDepth(treeObj) {
	var itemObj	= getObject(treeObj.curItemId);
	
	return itemObj.depth;
}


/**
 * ITEM의 상위 노드 값 반환
 * @param treeObj	트리 객체
 */
function getTreeItemParentValue(treeObj) {
	var itemObj	= getObject(treeObj.curItemId);
	var parentObj = getObject(itemObj.parentId);
	
	var value = "";
	if (itemObj) {
		value = parentObj.value;
	}
	return value;
}


/**
 * 마우스 오버시 아아템 배경색
 */
function onTreeItemColor() {
	if (this.overcolor == null) {
		this.style.backgroundColor = treeOverColor;
		this.overcolor = treeOverColor;
	}
}


/**
 * 마우스 아웃시 아이템 배경색
 */
function offTreeItemColor() {
	if (this.overcolor == treeOverColor) {
		this.style.backgroundColor = "";
		this.overcolor = null;
	}
}