// Flash Activating Script

// s: source url
// d: flash id
// w: source width
// h: source height
// t: wmode ("" for none, transparent, opaque ...)
function mf(s,d,w,h,t){
        return "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" id="+d+" width="+w+" height="+h+" wmode="+t+" ><param name=movie value="+s+" /><param name=quality value=high /><param name=wmode value="+t+" ><embed src="+s+" quality=high wmode="+t+" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?p1_prod_version=shockwaveflash\" width="+w+" height="+h+"></embed></object>";
}

function lg(s,d,w,h,t){
        return "<object classid=\"clsid:d27cdb6e-ae6d-11cf-96b8-444553540000\" codebase=\"http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,0,0\" id="+d+" width="+w+" height="+h+" wmode="+t+" ><param name=movie value="+s+" /><param name=quality value=high /><param name=wmode value="+t+" ><embed src="+s+" quality=high wmode="+t+" type=\"application/x-shockwave-flash\" pluginspage=\"http://www.macromedia.com/shockwave/download/index.cgi?p1_prod_version=shockwaveflash\" width="+w+" height="+h+"></embed></object>";
}

// write document contents
function documentwrite(src){
        document.write(src);
}

// assign code innerHTML
function setcode(target, code){
        target.innerHTML = code;
}

//메뉴 현재위치표시
IEx = navigator.appName.indexOf("Microsoft") != -1; 

function trace(n) 
{ 
    flashObj = IEx ? Fmovie : document.Fmovie; 
    flashObj.TGotoFrame("_root",n) 
	
} 
