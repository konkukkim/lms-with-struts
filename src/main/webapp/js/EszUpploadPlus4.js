var gFtpConn = null;

function FtpObject(strServer, strUserId, strUserPw, strSource, strTarget, intPort)
{
	function connect()
	{
		if (gFtpConn == null)
		{
			gFtpConn = this;//.ftpObj;
			return xFTP.ConnectToPort(this.server, this.userid, this.userpw, this.source, this.target, this.port);
		}
	}
	
	function disconnect()
	{
		if (gFtpConn != null)
		{
			xFTP.Disconnect();
		}
		gFtpConn = null;
	}
	
	function uploadfolder(sp, tp)
	{
		if (gFtpConn != null)
		{
			return xFTP.uploadfolder ( sp,  tp, true);
		}
	}
	
	function uploadfile(sf, tp)
	{
		if (gFtpConn != null)
		{
			return xFTP.uploadfile ( sf, tp );
		}
	}
	
	function init()
	{
		this.ftpObj = "FTP://"+strServer;
		this.server	= strServer;
		this.userid	= strUserId;
		this.userpw	= strUserPw;
		this.source	= strSource;
		this.target	= strTarget;
		this.port	= intPort;
	}
	
	this.server	= null;
	this.userid	= null;
	this.userpw	= null;
	this.source	= null;
	this.target	= null;
	this.port	= 21;
	this.status	= null;
	this.init	= init;
	this.connect	= connect;
	this.disconnect	= disconnect;
	this.uploadfolder	= uploadfolder;
	this.uploadfile	= uploadfile;
}

function Upload(ArrList)
{
	var _status = true;
	var sp, tp;
	sp = gFtpConn.source;
	tp = gFtpConn.target;
	var str = '';

	for (var i=0; i<ArrList.length; i++)
	{
		if (!_status) break;
		
		var ssp, stp;
		var isFolder = (ArrList[i].indexOf(".") == -1);
		if (isFolder)
		{
			ssp = sp + "\\" + ArrList[i];
			stp = tp + "/"+ ArrList[i];
			_status = _status && gFtpConn.uploadfolder(ssp, stp);
		}
		else
		{
			if (ArrList[i].indexOf("/") != -1)
			{
				ssp = sp + "\\" + ArrList[i].replace(/\//g, "\\");
				stp = tp + "/"+ ArrList[i].substr(0, ArrList[i].lastIndexOf("/"));
			}
			else
			{
				ssp = sp + "\\" + ArrList[i];
				stp = tp;
			}
			_status = _status && gFtpConn.uploadfile(ssp, stp);
		}
		
	}
	return _status;
}

var emptyArray = new Array('');
function GetWebDataArray()
{
	var o3 = new Array(
				'HTML'
				,'default.xml'
				,'estream@xinics.com.xml'
				,'imsmanifest.xml'
				,'intro.htm'
				,'list.xml'
				,'marker.txt'
				,'output.est'
				,'package.xml'
				,'slideinfo.xml'
				,'start.htm'
			);
	var o4 = new Array(
				'data'
				,'html'
				,'cscripts/intro.htm'
				,'default.xml'
				,'imsmanifest.xml'
				,'package.xml'
			);
	if (gnVersion == "3")
		return o3;
	else if (gnVersion == "4")
		return o4;
	else
		return emptyArray;
}

function GetMediaDataArray()
{
	var o3 = new Array(
				'HTML/AsfFiles'
				,'output.asf'
			);
	var o4 = new Array(
				'media'
				,'datamedia'
				,'output.asx'
			);
	if (gnVersion == "3")
		return o3;
	else if (gnVersion == "4")
		return o4;
	else
		return emptyArray;
}

function GetWebViewerArray()
{
	var o3 = new Array(
				'help'
				,'images'
				,'adlcp_rootv1p2.xsd'
				,'adlcp_v1p3.xsd'
				,'APIWrapper.js'
				,'AUFunctions.js'
				,'datatypes.dtd'
				,'default.htm'
				,'default.asp'
				,'eStreamPresto.js'
				,'eStreamPresto.vbs'
				,'ims_xml.xsd'
				,'imscp_rootv1p1p2.xsd'
				,'imsmd_rootv1p2p1.xsd'
				,'imscp_v1p1.xsd'
				,'intro.htm'
				,'introduction.htm'
				,'launch.htm'
				,'marker_list.html'
				,'player.js'
				,'ques.htm'
				,'sn.htm'
				,'sn.asp'
				,'sn.js'
				,'start.htm'
				,'StreamNote2.cab'
				,'thumbnail.gif'
				,'viewer.css'
				,'Viewer.exe'
				,'viewer.ini'
				,'ViewerOCX.exe'
				,'xml.xsd'
				,'XMLSchema.dtd'
			);
	var o4 = new Array(
				'common'
				,'cscripts'
				,'viewer'
				,'adlcp_rootv1p2.xsd'
				,'adlcp_v1p3.xsd'
				,'datatypes.dtd'
				,'default.htm'
				,'default.asp'
				,'ims_xml.xsd'
				,'imscp_rootv1p1p2.xsd'
				,'imsmd_rootv1p2p1.xsd'
				,'imscp_v1p1.xsd'
				,'index.htm'
				,'index.asp'
				,'launch.htm'
				,'launch.asp'
				,'output.asp'
				,'output.asx'
				,'Viewer.exe'
				,'viewer.ini'
				,'xml.xsd'
				,'XMLSchema.dtd'
			);
	if (gnVersion == "3")
		return o3;
	else if (gnVersion == "4")
		return o4;
	else
		return emptyArray;
				//	common/sn.htm, common/startframe.htm
}

function GetLiveWebDataArray() {
	var oo = new Array(
				'html'
				,'cscripts/intro.htm'
			);
	return oo;
}

function GetLiveMediaDataArray() {
	var oo = new Array(
				'datamedia'
			);
	return oo;
}

function GetEszVersion(sPath)
{
	if ( mOCX.HzOpenESZ(sPath) == 0 ) {
		return mOCX.HzGetESZInfo("version");
	} else {
		return gnVersion;
	}
}