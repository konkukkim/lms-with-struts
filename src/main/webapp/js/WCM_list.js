    //constructor
    function Folder(folderDescription, setValue) {
        //constant data;
        this.desc = folderDescription;
        //this.hreference = hreference;
        this.id = -1;
        this.navObj = 0;
        this.iconImg = 0;
        this.nodeImg = 0;
        this.isLastNode = 1;
        // dynamic data
        this.isOpen = true;
        this.iconSrc = "/img/1/contents/folder_closed.gif";
        this.children = new Array;
        this.nChildren = 0;
        // methods
        this.initialize = initializeFolder;
        this.setState = setStateFolder;
        this.addChild = addChild;
        this.createIndex = createEntryIndex;
        this.hide = hideFolder;
        this.display = display;
        this.renderOb = drawFolder;
        this.totalHeight = totalHeight;
        this.subEntries = folderSubEntries;
        this.outputLink = outputFolderLink;
		//this.wk_link = wk_link;
		this.setValue = setValue;
    }


    function setStateFolder(isOpen) {
        var subEntries;
        var totalHeight;
        var fIt = 0;
        var i = 0;
        if(isOpen == this.isOpen) {
            return;
        }
        if(browserVersion == 2) {
            totalHeight = 0;
            for(i = 0; i < this.nChildren; i++) {
                totalHeight = totalHeight + this.children[i].navObj.clip.height;
            }
            subEntries = this.subEntries();
            if(this.isOpen) {
                totalHeight = 0 - totalHeight;
            }
            for(fIt = this.id + subEntries + 1; fIt < nEntries; fIt++) {
                indexOfEntries[fIt].navObj.moveBy(0, totalHeight);
            }
        }
        this.isOpen = isOpen;
        propagateChangesInState(this);
    }

    function propagateChangesInState(folder) {
        var i=0
        if (folder.isOpen) {
            if (folder.nodeImg) {
                if (folder.isLastNode) {
                    folder.nodeImg.src = "/img/1/contents/minus_end.gif";
                }
                else {
                    folder.nodeImg.src = "/img/1/contents/minus_node.gif";
                }
            }
            folder.iconImg.src = "/img/1/contents/folder_open.gif";
            for (i=0; i<folder.nChildren; i++) {
                    folder.children[i].display();
            }
        }
        else {
            if (folder.nodeImg) {
                if (folder.isLastNode) {
                    folder.nodeImg.src = "/img/1/contents/plus_end.gif";
                }
                else {
                    folder.nodeImg.src = "/img/1/contents/plus_node.gif";
                }
            }
            folder.iconImg.src = "/img/1/contents/folder_closed.gif";
            for (i=0; i<folder.nChildren; i++) {
                folder.children[i].hide();
            }
        }
    }


    function hideFolder() {
        if(browserVersion == 1 || browserVersion == 3) {
            if(this.navObj.style.display == "none") {
                return;
            }
            this.navObj.style.display = "none";
        }
        else {
            if(this.navObj.visibility == "hiden") {
                return;
            }
            this.navObj.visibility = "hiden";
        }
        this.setState(0);
    }


    function initializeFolder(level, lastNode, leftSide) {
        var i = 0;
        var j = 0;
        var numberOfFolders;
        var numberOfDocs;
        var nc;
        nc = this.nChildren;
        this.createIndex();
        var auxEv = "";
        if(browserVersion > 0) {
            auxEv = "<a href='javascript:clickOnNode("+this.id+",\"\")'>";
        }
        else {
            auxEv = "<a>";
        }
        if(level > 0) {
            if(lastNode) { //the last 'brother' in the children array
               this.renderOb(leftSide + auxEv + "<img name='nodeIcon"+this.id+"' id='nodeIcon"+this.id+"' src='/img/1/contents/line_end.gif' width=16 height=22 border=0></a>");
               leftSide = leftSide + "<img src='/img/1/contents/blank.gif' width=16 height=22>";
               this.isLastNode = 1;
            }
            else {
                this.renderOb(leftSide + auxEv + "<img name='nodeIcon"+this.id+"' id='nodeIcon"+this.id+"' src='/img/1/contents/minus_node.gif' width=16 height=22 border=0></a>");
                leftSide = leftSide + "<img src='/img/1/contents/line.gif' width=16 height=22>";
                this.isLastNode = 0;
            }
        }
        else {
            this.renderOb("");
        }
        if(nc > 0) {
            level = level + 1;
            for(i = 0; i < this.nChildren; i++) {
                if(i == this.nChildren-1) {
                    this.children[i].initialize(level, 1, leftSide);
                }
                else {
                    this.children[i].initialize(level, 0, leftSide);
                }
            }
        }
    }

    function drawFolder(leftSide) {
		//-- FolderType
        if (browserVersion == 2) {
            if (!doc.yPos) {
                if (!net_yPos) {
                     doc.yPos=8;
                }
                else {
                    doc.yPos = net_yPos;
                }
            }
            doc.write("<layer id='folder" + this.id + "' top=" + doc.yPos + " visibility=hiden>");
        }

        doc.write("<table");

        if (browserVersion == 1 || browserVersion == 3) {
            doc.write(" id='folder"+this.id+"' style='position:block;' ");
        }

        doc.write(" border=0 cellspacing=0 cellpadding=0>");
        doc.write("<tr><td>");
        doc.write(leftSide);
        this.outputLink();
        doc.write("<img id='folderIcon"+this.id+"' name='folderIcon"+this.id+"' ");
        doc.write("src='" + this.iconSrc+"' border=0></a>");
        doc.write("</td><td nowrap>");
        doc.write("<DIV CLASS=\"fldrroot\">");
		doc.write("<a class="+this.fclass+" href=/Contents.cmd?cmd=WCMFrameRight&pPathDir=" + this.setValue + " target=WCMRight>"+ this.desc + "</a>");
        doc.write("</DIV>");
        doc.write("</td>");
        doc.write("</table>");

        if (browserVersion == 2) {
            doc.write("</layer>");
        }

        if (browserVersion == 1) {
            this.navObj  = doc.all["folder"+this.id];
            this.iconImg = doc.all["folderIcon"+this.id];
            this.nodeImg = doc.all["nodeIcon"+this.id];
        }
        else if (browserVersion == 2) {
            this.navObj  = doc.layers["folder"+this.id];
            this.iconImg = this.navObj.document.images["folderIcon"+this.id];
            this.nodeImg = this.navObj.document.images["nodeIcon"+this.id];
            doc.yPos     = doc.yPos+this.navObj.clip.height;
        }
        else if (browserVersion == 3) {
            this.navObj  = doc.getElementById("folder"+this.id);
            this.iconImg = doc.getElementById("folderIcon"+this.id);
            this.nodeImg = doc.getElementById("nodeIcon"+this.id);
        }
    }


    function outputFolderLink() {
		/*
        if (this.hreference) {
            var sta = "onMouseOver=\"window.status=\'View Contents...\'; return true;\" "
                    + "onMouseOut=\"window.status=\'\'; return true\"";
			var courseId=document.Input.pCourseId.value;
			var contentsOrder= str[2].split(".");
			var contentsTopOrder = contentsOrder[0];
			var str = this.setValue.split("|");
            doc.write("<a href=\'javascript:lec_win(\"/bin/lecture/lecture_main_frame.php?pCourseId=" +courseId+ "&pTopOrder=" +contentsTopOrder+ "\")\' "+sta+ " ");
            doc.write(">");
        }
		*/
    }

    function addChild(childNode) {
        this.children[this.nChildren] = childNode;
        this.nChildren++;
        return childNode;
    }


    function folderSubEntries() {
        var i = 0;
        var se = this.nChildren;
        for (i=0; i < this.nChildren; i++){
            if (this.children[i].children) { //is a folder
                se = se + this.children[i].subEntries();
            }
        }
        return se;
    }


    // Definition of class Item (a document or link inside a Folder)
    // *************************************************************
    function Item(itemDescription, itemLink, itemImg, fontClass,setValue) {   // Constructor
        // constant data
        this.desc            = itemDescription;
        this.link            = itemLink;
        this.id              = -1;
        this.navObj          = 0;
        this.iconImg         = 0;
        this.iconSrc         = "/img/1/contents/";
        this.initialize      = initializeItem;
        this.createIndex     = createEntryIndex;
        this.hide            = hideItem;
        this.display         = display;
        this.renderOb        = drawItem;
        this.totalHeight     = totalHeight;
		this.setValue		 = setValue;
        if (itemImg == "FY") {
            this.iconSrc += "page_check.gif";
        }
        else if (itemImg == "FN") {
            this.iconSrc += "page_ncheck.gif";
        }
        else if (itemImg == "QY") {
            this.iconSrc += "q_pass.gif";
        }
        else if (itemImg == "QN") {
            this.iconSrc += "q_npass.gif";
        }
        this.fclass = fontClass;
    }


    function hideItem() {
        if (browserVersion == 1 || browserVersion == 3) {
            if (this.navObj.style.display == "none") {
                return;
            }
            this.navObj.style.display = "none";
        }
        else {
            if (this.navObj.visibility == "hiden") {
                return;
            }
            this.navObj.visibility = "hiden";
        }
    }


    function initializeItem(level, lastNode, leftSide) {
        this.createIndex()
        if (level>0) {
            if (lastNode) { //the last 'brother' in the children array
                this.renderOb(leftSide + "<img src='/img/1/contents/line_end.gif' width=16 height=22>");
                leftSide = leftSide + "<img src='/img/1/contents/blank.gif' width=16 height=22>";
            }
            else {
                this.renderOb(leftSide + "<img src='/img/1/contents/line_node.gif' width=16 height=22>");
                leftSide = leftSide + "<img src='/img/1/contents/line.gif' width=16 height=22>";
            }
        }
        else {
            this.renderOb("");
        }
    }


    function drawItem(leftSide) {
		//-- File Type write
        if (browserVersion == 2) {
            doc.write("<layer id='item"+this.id+"' top="+doc.yPos+" visibility=hiden>");
        }

        doc.write("<table ");
        if (browserVersion == 1 || browserVersion == 3) {
            doc.write(" id='item"+this.id+"' style='position:block;' ");
        }

        doc.write(" border=0 cellspacing=0 cellpadding=0>");
        doc.write("<tr><td>");
        doc.write(leftSide);


        var sta = "onMouseOver=\"window.status='View Contents...'; return true;\" onMouseOut=\"window.status=''; return true\"";

        // icon click --> goto url
        if(this.link != "") {
            doc.write("<a href=" + this.link + ">");
        }

        // icon display
        if(this.iconSrc != "") {
            doc.write("<img id='itemIcon"+this.id+"' name='iname"+this.id+"' ");
            doc.write("src='"+this.iconSrc+"' alt='"+this.desc+"' border=0>")
        }

        if(this.link != "") {
            doc.write("</a>");
        }
        doc.write("</td><td nowrap>");
        doc.write("<DIV CLASS=\"fldritem\">");
		
        doc.write("<a class="+this.fclass+" href=" + this.setValue + ">"+ this.desc + "</a>");

        doc.write("</DIV>");
        doc.write("</table>");

        if (browserVersion == 2) {
            doc.write("</layer>");
        }
        if (browserVersion == 1) {
            this.navObj = doc.all["item"+this.id];
            this.iconImg = doc.all["itemIcon"+this.id];
        }
        else if (browserVersion == 2) {
            this.navObj = doc.layers["item"+this.id];
            this.iconImg = this.navObj.document.images["itemIcon"+this.id];
            doc.yPos=doc.yPos+this.navObj.clip.height;
        }
        else if (browserVersion == 3) {
            this.navObj = doc.getElementById("item"+this.id);
            this.iconImg = doc.getElementById("itemIcon"+this.id);
        }
    }


    // Methods common to both objects (pseudo-inheritance)
    // ********************************************************
    function display() {
        if (browserVersion == 1 || browserVersion == 3) {
            this.navObj.style.display = "block";
        }
        else {
            this.navObj.visibility = "show";
        }
    }


    function createEntryIndex() {
        this.id = nEntries;
        indexOfEntries[nEntries] = this;
        nEntries++;
    }


    // total height of subEntries open
    function totalHeight() { //used with browserVersion == 2
        var h = this.navObj.clip.height;
        var i = 0;
        if (this.isOpen) { //is a folder and _is_ open
            for (i=0 ; i < this.nChildren; i++) {
                h = h + this.children[i].totalHeight();
            }
        }
        return h;
    }


    // Events
    // *********************************************************
    function clickOnFolder(folderId) {
        var clicked = indexOfEntries[folderId];
        if (!clicked.isOpen) {
            clickOnNode(folderId);
            return;
        }
        if (clicked.isOpen == true) {
            clickOnNode(folderId);
            return;
        }
        if (clicked.isSelected) {
            return;
        }
    }


    function clickOnNode(folderId, ref) {
        var clickedFolder = 0
        var state = 0
        clickedFolder = indexOfEntries[folderId]
        state = clickedFolder.isOpen
		if (!state && ref) {
			parent.mainFrame.location = ref;
		}
        clickedFolder.setState(!state) //open<->close
    }


    function initializeDocument() {
        menu.initialize(0, 1, "");
        menu.display();
        if(browserVersion > 0) {
            doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>");
            // close the whole tree
            clickOnNode(0);
            // open the root folder
            clickOnNode(0);
        }
    }

    // for scorm
    function initializeDocument2() {
        menu.initialize(0, 1, "");
        menu.display();
        if(browserVersion > 0) {
            doc.write("<layer top="+indexOfEntries[nEntries-1].navObj.top+">&nbsp;</layer>");
            clickOnNode(0);
            clickOnNode(0);
            clickOnNode(1);
        }
    }

    // Auxiliary Functions for Folder-Treee backward compatibility
    // *********************************************************
    function gFldr(description, setValue) {
        folder = new Folder(description, setValue);
        return folder;
    }

    function gLnk(target, description, linkData, itemImg, fontClass,setValue) {
        fullLink = "";
        var sta = "onMouseOver=\"window.status=\'View Contents...\'; return true;\" onMouseOut=\"window.status=\'\'; return true\"";
		var str = setValue.split("|");
		//lec_win(Page)
		//lec_win('/bin/lecture/lecture_main_frame.php?pCourseId=&pTopOrder=
		var courseId=document.Input.pCourseId.value;
		var contentsOrder= str[2].split(".");
		var contentsTopOrder = contentsOrder[0];
        if (target != "") {
            if(linkData != "") {
                fullLink = "'javascript:lec_win(\"/bin/lecture/lecture_main_frame.php?pCourseId=" +courseId+ "&pTopOrder=" +contentsTopOrder+ "\")' " +sta;
            }
            else {
                fullLink = "";
            }
        }
        else {
            if(linkData != "") {
                fullLink = "'javascript:lec_win(\"/bin/lecture/lecture_main_frame.php?pCourseId=" +courseId+ "&pTopOrder=" +contentsTopOrder+ "\")' " +sta;
            }
            else {
                fullLink = "";
            }
        }
        linkItem = new Item(description, fullLink, itemImg, fontClass,setValue)
        return linkItem
    }

    function insFldr(parentFolder, childFolder, m_num) {
        return parentFolder.addChild(childFolder);
    }

    function insDoc(parentFolder, document) {
        parentFolder.addChild(document);
    }

    // Global variables
    // ****************
    USETEXTLINKS = 1;
    indexOfEntries = new Array;
    nEntries = 0;
    doc = document;
    browserVersion = 0;
    selectedFolder=0;

    // Browser Version check
    if(doc.all) {
        browserVersion = 1; //IE4
    }
    else if(doc.layers) {
        browserVersion = 2; //NS4
    }
    else if (document.getElementById) {
        browserVersion = 3; //NS7
    }
    else {
        browserVersion = 0; //other
    }
