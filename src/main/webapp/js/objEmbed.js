function setEmbed() 
{ 
  var objStart = new String; 
  var objEnd = new String; 
  var parameter = new String; 
  var embed = new String; 
  var html = new String; 
  var allParameter = new String; 
  var clsid = new String; 
  var codebase = new String; 
  var pluginspace = new String; 
  var embedType = new String; 
  var objType = new String;
  var src = new String; 
  var width = new String; 
  var height = new String; 
  var headerAdd = new String;

    
  this.init = function( getType , getSrc, getClassId, getCodeBase, getWidth, getHeight ) { 
      
      if ( getType == "flash") 
      { 
        clsid = "D27CDB6E-AE6D-11cf-96B8-444553540000";
        codebase = "http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"; 
        pluginspage = "http://www.macromedia.com/go/getflashplayer"; 
        embedType = "application/x-shockwave-flash"; 
        parameter += "<param name='movie' value='"+ getSrc + "'>\n";
        parameter += "<param name='quality' value='high'>\n";
      }
	  else if ( getType != 'flash' ) 
      { 
	    clsid = getClassId;
        codebase = getCodeBase; 
      }
	  objType = getType;
      src = getSrc; 
      width = getWidth; 
      height = getHeight; 
  } 

  this.setParameter = function( parm , value ) {
      parameter += "<param name='"+parm +"' value='"+ value + "'>\n";        
      allParameter += " "+parm + "='"+ value+"'"; 
  }

  this.setHeaderAdd = function( parm , value ) {
      headerAdd += " "+parm + "='"+ value+"' "; 
  }
  
  this.show = function() { 
      if( objType == "applet")
	  {
		objStart = "<applet src='" + src + "' ";
		objEnd = "</applet>\n"; 
	  }else{
		objStart = "<object ";
		objEnd = "</object>\n"; 

		if(objType == "flash")
		{
			embed = "<embed src='" + src + "' pluginspage='"+ pluginspage + "' type='"+ embedType + "' width='"+ width + "' height='"+ height +"'"+ allParameter +" "+ headerAdd +"></embed>\n"; 
		}
	  }
	  if(clsid != "")
			objStart += "classid=\"clsid:"+ clsid +"\" ";
	  if(codebase != "")
			objStart += "codebase=\""+ codebase +"\" ";
	  objStart += " width='"+ width +"' height='"+ height +"' "+headerAdd+">\n";

      html = objStart + parameter + embed + objEnd; 
	  //alert(html);
      document.write( html );  
  } 
  
} 