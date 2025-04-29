<!--

function chng(ImgName,ImgSrc)
	{
		eval('document.images.'+ImgName+'.src = '+ImgSrc+'.src;');			
	}

function chng2(ImgName,ImgSrc)
{
	eval('document.images.'+ImgName+'.src = '+ImgSrc+'.src;');			
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

//-->


<!--

function chng11(ImgName,ImgSrc)
	{
		eval('document.images.'+ImgName+'.src = '+ImgSrc+'.src;');			
	}

function chng22(ImgName,ImgSrc)
{
	eval('document.images.'+ImgName+'.src = '+ImgSrc+'.src;');			
}

function MM_findObj(n, d) { //v3.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document); return x;
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

//-->



<!--
function changeMenuLayer(n, i) {
    for(var j=1; j<=n; j++) {
        el = document.getElementById("menu" + j);
        el.src = el.src.replace("_on","_off");
        el.oSrc = el.src;
        document.getElementById("menuLayer" + j).style.display = "none";
    }
    var el = document.getElementById("menu" + i);
    el.src = el.src.replace("_off", "_on");
    el.oSrc = el.src;
    document.getElementById("menuLayer" + i).style.display = "";
}
//-->