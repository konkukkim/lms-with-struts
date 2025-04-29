package com.edutrack.framework.persist;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.sql.RowSet;
import javax.sql.RowSetMetaData;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;

/**
 * 	JSP 등에서 페이지 단위의 리스트 형태를 출력할때 사용
 *
 *	데이터 추출에 필요한 RetrieveStatement 와 현재 페이지, 현재 블록 등을 세팅한 후
 *	getListItems() 을 호출하면, 지정된 페이지에 해당하는 데이터가 String[][] 형태로
 *	리턴됨.
 *	페이지 이동 링크에는 javascript:goPage(page, block) 형태로 링크가 생성.
 *
 *	@author  saijer
 * 	@version %0%, %9%
 * 	@see     com.epiontech.persist.PersistentBroker
 */
public class ListDTO implements Serializable{
        static Configuration config = ConfigurationFactory.getInstance().getConfiguration();

        private boolean pageScript = false;

        private int iCurrentPage		= 1;
        private int iListScale			= 10; // 리스트의 갯수
        private int iPageScale			= 10; // 페이지 갯수
        private int iTotalPageCount	    = 0; // 리스트 시작 번호
        private int iTotalItemCount     = 0;
        private RowSet iItemList        = null;
        private ResultSet arrayItemList = null;
        private RowSetMetaData info = null;
        private ArrayList  arrayList = null;
        private int startPageNum = 0;

        public void setPageScript(boolean value){ this.pageScript = value; }

        public void setCurrentPage(int iCurrentPage){
             this.iCurrentPage = iCurrentPage;
        }
 	    public int getCurrentPage(){
            if(getTotalPageCount() < 1)
               return 0;
            else
 	           return this.iCurrentPage;
         }

        public void setListScale(int iListScale){ this.iListScale = iListScale;}
        public void setPageScale(int iPageScale){ this.iPageScale = iPageScale;}

        public int getListScale(){ return this.iListScale;}
        public int getPageScale(){ return this.iPageScale; }

        public void setTotalItemCount(int itemcount){this.iTotalItemCount = itemcount;}
        public int getTotalItemCount(){ return this.iTotalItemCount;}
        public int getTotalPageCount(){ return this.iTotalPageCount;}
        public int getItemCount(){
        	if(this.iTotalItemCount < this.iListScale) return this.iTotalItemCount;
        	else return this.iListScale;
        }
        public void setItemList(RowSet list){
        	this.iItemList = list;
        	if(this.iItemList != null){
        		try {
					this.info = (RowSetMetaData)(this.iItemList.getMetaData());
				} catch (SQLException e) {
					this.info = null;
					e.printStackTrace();
				}
        	}
        }

        public void setArrayItemList(ResultSet result){
            arrayItemList = result;
        }

        public ResultSet getArrayItemList(){
            return arrayItemList;
        }

        public void setArrayList(ArrayList result){
            arrayList = result;
        }

        public ArrayList getArrayList(){
            return arrayList;
        }


        public int getStartPageNum(){
            return iTotalItemCount - (iListScale*(iCurrentPage-1));
        }

        public RowSet getItemList(){ return iItemList;}

        public ListDTO(){

        }

	public ListDTO(int iCurrentPage, int totalitemcount){
		this.iListScale = config.getInt("framework.list.default_list_scale", 10);
		this.iPageScale = config.getInt("framework.list.default_page_scale", 10);

		if(iCurrentPage <= 0) iCurrentPage = 1;
		this.iCurrentPage = iCurrentPage;
		this.iTotalItemCount = totalitemcount;
		if(this.iTotalItemCount%this.iListScale == 0) this.iTotalPageCount = this.iTotalItemCount/this.iListScale;
		else this.iTotalPageCount = this.iTotalItemCount/this.iListScale+1;
	}

	public ListDTO(int iCurrentPage, int totalitemcount, int listScale){
		this.iListScale = listScale;
		this.iPageScale = config.getInt("framework.list.default_page_scale", 10);

		if(iCurrentPage <= 0) iCurrentPage = 1;
		this.iCurrentPage = iCurrentPage;
		this.iTotalItemCount = totalitemcount;
		if(this.iTotalItemCount%this.iListScale == 0) this.iTotalPageCount = this.iTotalItemCount/this.iListScale;
		else this.iTotalPageCount = this.iTotalItemCount/this.iListScale+1;

	}

        public ListDTO(int iCurrentPage, int totalitemcount, int listScale, int pageScale){
			this.iListScale = listScale;
			this.iPageScale = pageScale;

			if(iCurrentPage <= 0) iCurrentPage = 1;
			this.iCurrentPage = iCurrentPage;
			this.iTotalItemCount = totalitemcount;
			if(this.iTotalItemCount%this.iListScale == 0) this.iTotalPageCount = this.iTotalItemCount/this.iListScale;
			else this.iTotalPageCount = this.iTotalItemCount/this.iListScale+1;

        }


       public int getRownumAll(){
           return this.iCurrentPage * this.iListScale;
       }

       public int getRownumStart(){
           return ((this.iCurrentPage - 1) * this.iListScale + 1);
       }

       public int getRownumEnd(){
          return this.iCurrentPage * this.iListScale;
       }

        /**
         *	페이지 이동시 사용할 자바스크립트 생성
         *
         *	@param frm 검색 폼 이름 (ex: parent.frames[0].CODE_SEARCH)
         *	@param cur_page 현재 페이지 번호를 갖고 있는 폼 이름 (ex: CUR_PAGE)
         *	@return goPage() 스크립트
         *
         */
       public String getPageScript(String frm, String cur_page, String func_name){
           if(func_name == null || "".equals(func_name)) func_name = "goPage";

           StringBuffer result = new StringBuffer();

//			result.append("<div id=\"_progressbar\" style=\"position:absolute;margin-top:10%;display:none;margin-left: 20%\">\n");
//			result.append("	<table width=\"240\" height=\"50\" cellpadding=\"0\" cellspacing=\"1\" bgcolor=\"#a9a9a9\">\n");
//			result.append("	<tr>\n");
//			result.append("		<td>\n");
//			result.append("		<table width=\"100%\" height=\"100%\" bgcolor=\"#ffe79f\" cellpadding=\"4\">\n");
//			result.append("		<tr>\n");
//			result.append("			<td align=\"right\" valign=\"middle\">☞&nbsp;</td>\n");
//			result.append("			<td align=\"left\" valign=\"middle\"><span id=\"msg\" style=\"font-size:10pt\">잠시만 기다려 주세요!</span></td>\n");
//			result.append("		</tr>\n");
//			result.append("		<tr>\n");
//			result.append("			<td valign=\"middle\" colspan=\"2\"><marquee direction=\"right\"><font color=\"blue\">***************************************</font></marquee></td>\n");
//			result.append("		</tr>\n");
//			result.append("		</table>\n");
//			result.append("		</td>\n");
//			result.append("	</tr>\n");
//			result.append("	</table>\n");
//			result.append("</div>\n");

           result.append("\n<script language=\"javascript\">\n");
           result.append("function "+func_name+"(page,type){\n");
           result.append("\tif(page == null || page <= 0) return;\n");
           result.append("\tdocument.forms[\""+frm+"\"].elements[\""+cur_page+"\"].value = page;\n");
//           result.append("if(type==2) _show_progressbar();\n");
           result.append("\tdocument.forms[\""+frm+"\"].submit();\n");
           result.append("}\n");

//			result.append("<!-- 모래시계 스크립트 시작 -->\n");
//			result.append("function _hidden_progressbar() {\n");
//			result.append("try {\n");
//			result.append("_progressbar.style.display = \"none\";\n");
//			result.append("} catch(e) {}\n");
//			result.append("}\n");
//			result.append("function _show_progressbar() {\n");
//			result.append("_progressbar.style.top = document.body.scrollTop\n");
//			result.append("_progressbar.style.display = \"\";\n");
//			result.append("setTimeout(_change_msg, 3000);\n");
//			result.append("}\n");
//			result.append("function _change_msg() {\n");
//			result.append("try {\n");
//			result.append("msg.innerHTML = '잠시만 기다려 주세요!<br>지금 데이터를 처리하고 있습니다.\";\n");
//			result.append("} catch(e) {}\n");
//			result.append("}	\n");
//			result.append("<!-- 모래시계 스크립트 끝 -->");


           result.append("</script>\n");

//	this.pageScript = true;
           return result.toString();
   }

	public String getPagging(String systemcode, String func_name){
		return getPagging(systemcode,func_name, config.getString("global.context_path", "img/"));
	}

	public String getPaggingAjax(String systemcode, String func_name){
		return getPaggingAjax(systemcode,func_name, config.getString("global.context_path", "img/"));
	}

	public String getPaggingAjax(String systemcode, String func_name, String contextPath){
		int iTemp=0;
        contextPath = contextPath + systemcode+"/button_img";

		if(getItemCount() < 1)
			return "";

        if(func_name == null || "".equals(func_name))
			func_name = "goPage";

		StringBuffer page = new StringBuffer();
        int intStartPage =((int)Math.ceil((double)iCurrentPage/(double)iPageScale)-1)*iPageScale+1;

        if (intStartPage>1) {
			page.append("<a href='javascript:"+func_name+"("+(intStartPage-iPageScale)+",2)' ><img src='"+contextPath+"/btn_start.gif' border=0 align=absmiddle></a>&nbsp;");
        } else {
           page.append("<img src='"+contextPath+"/btn_start.gif' border=0 align=absmiddle>&nbsp;");
        }

		if (iCurrentPage > 1) {
		   page.append("<a href='javascript:"+func_name+"("+(iCurrentPage-1)+",2)' > <img src='"+contextPath+"/btn_prev.gif' border=0 title='Prev Page' align=absmiddle></a>&nbsp;");
		} else {
		   page.append("<img src='"+contextPath+"/btn_prev.gif' border=0 title='Prev Page' align=absmiddle>&nbsp;");
		}

        for (int i=intStartPage; i<intStartPage + iPageScale ; i++) {
			iTemp=i;

			if (i<=iTotalPageCount) {
				if (i !=iCurrentPage) {
					page.append("<font class=\"s_text05\"><a href='javascript:"+func_name+"("+i+",2)'> "+i+" </a></font>");
                } else {
					page.append("<font class=\"s_text05\"><b> "+ i +" </b></font>");
                }
            } else {
				break;
            }
		}

        if (iCurrentPage < iTotalPageCount) {
           page.append("&nbsp;<a href='javascript:"+func_name+"("+(iCurrentPage+1)+",2)' > <img src='"+contextPath+"/btn_next.gif' border=0 title='Next Page' align=absmiddle></a>");
        } else {
           page.append("&nbsp;<img src='"+contextPath+"/btn_next.gif' border=0 title='Next Page' align=absmiddle>");
        }
//                </td> </tr> </table>
		if (iTemp+1<= iTotalPageCount) {
		  page.append("&nbsp;<a href='javascript:"+func_name+"("+(iTemp+1)+",2)'  ><img src='"+contextPath+"/btn_end.gif' border=0 align=absmiddle></a>");
		}else{
		  page.append("&nbsp;<img src='"+contextPath+"/btn_end.gif' border=0 align=absmiddle>");
		}
//	  page.append("</td><td align=right width=125>
		try{
			if(iItemList != null) iItemList.close();
		}catch(Exception e){

		}

		return page.toString();
    }

	public String getPagging(String systemcode, String func_name, String contextPath){
		int iTemp=0;
        contextPath = contextPath + systemcode+"/button_img";

		if(getItemCount() < 1)
			return "";

        if(func_name == null || "".equals(func_name))
			func_name = "goPage";

		StringBuffer page = new StringBuffer();
        int intStartPage =((int)Math.ceil((double)iCurrentPage/(double)iPageScale)-1)*iPageScale+1;

        if (intStartPage>1) {
			page.append("<a href=\"javascript:"+func_name+"("+(intStartPage-iPageScale)+",2)\" onMouseOver=\"window.status='Previous Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\""+contextPath+"/btn_start.gif\" border=0 align=absmiddle></a>&nbsp;\n");
        } else {
           page.append("<img src=\""+contextPath+"/btn_start.gif\" border=0 align=absmiddle>&nbsp;\n");
        }

		if (iCurrentPage > 1) {
		   page.append("<a href=\"javascript:"+func_name+"("+(iCurrentPage-1)+",2)\" onMouseOver=\"window.status='First Page'; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\""+contextPath+"/btn_prev.gif\" border=0 title='Prev Page' align=absmiddle></a>&nbsp;\n");
		} else {
		   page.append("<img src=\""+contextPath+"/btn_prev.gif\" border=0 title=\"Prev Page\" align=absmiddle>&nbsp;\n");
		}

        for (int i=intStartPage; i<intStartPage + iPageScale ; i++) {
			iTemp=i;

			if (i<=iTotalPageCount) {
				if (i !=iCurrentPage) {
					page.append("<font class=\"s_text05\"><a href=\"javascript:"+func_name+"("+i+",2)\" onMouseOver=\"window.status='page " + i + "'; return true\" onMouseOut=\"window.status=''; return true\">"+i+"</a></font>\n");
                } else {
					page.append("<font class=\"s_text05\"><b>"+ i +"</b></font>\n");
                }
            } else {
				break;
            }
		}

        if (iCurrentPage < iTotalPageCount) {
           page.append("&nbsp;<a href=\"javascript:"+func_name+"("+(iCurrentPage+1)+",2)\" onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\""+contextPath+"/btn_next.gif\" border=0 title='Next Page' align=absmiddle></a>\n");
        } else {
           page.append("&nbsp;<img src=\""+contextPath+"/btn_next.gif\" border=0 title='Next Page' align=absmiddle>\n");
        }
//                </td> </tr> </table>
		if (iTemp+1<= iTotalPageCount) {
		  page.append("&nbsp;<a href=\"javascript:"+func_name+"("+(iTemp+1)+",2)\"  onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\""+contextPath+"/btn_end.gif\" border=0 align=absmiddle></a>\n");
		}else{
		  page.append("&nbsp;<img src=\""+contextPath+"/btn_end.gif\" border=0 align=absmiddle>\n");
		}
//	  page.append("</td><td align=right width=125>
		try{
			if(iItemList != null) iItemList.close();
		}catch(Exception e){

		}
		return page.toString();
    }

	public String getPagging2(String func_name, String imgPath){
	  int iTemp=0;

      if(getItemCount() < 1) return "";

	  if(func_name == null || "".equals(func_name)) func_name = "goPage";
	  StringBuffer page = new StringBuffer();
	  int intStartPage =((int)Math.ceil((double)iCurrentPage/(double)iPageScale)-1)*iPageScale+1;

	  if (intStartPage>1) {
		 page.append("<a href=\"javascript:"+func_name+"("+(intStartPage-iPageScale)+",1)\" onMouseOver=\"window.status='Previous Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\""+imgPath+"/double_left_page.gif\" border=0 align=absmiddle></a>\n");
	  }else{
		 page.append("<img src=\""+imgPath+"/double_left_page.gif\" border=0 align=absmiddle>\n");
	  }
	  if (iCurrentPage > 1) {
		 page.append("<a href=\"javascript:"+func_name+"("+(iCurrentPage-1)+",1)\" onMouseOver=\"window.status='First Page'; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\""+imgPath+"/left_page.gif\" border=0 title='Prev Page' align=absmiddle></a>\n");
	  }
	  else {
		 page.append("<img src=\""+imgPath+"/left_page.gif\" border=0 title=\"Prev Page\" align=absmiddle>\n");
	  }

	  for (int i=intStartPage; i<intStartPage + iPageScale ; i++) {
			  iTemp=i;
			  if (i<=iTotalPageCount) {
				   if (i !=iCurrentPage) {
				  page.append("<a href=\"javascript:"+func_name+"("+i+",1)\" onMouseOver=\"window.status='page " + i + "'; return true\" onMouseOut=\"window.status=''; return true\"><font color=black>"+i+"</font></a>\n");
			  } else {
					page.append("<font color=red>"+ i +"</font>\n");
					   }
			  } else	{
					  break;
			  }
	   }

	   if (iCurrentPage < iTotalPageCount) {
		  page.append("<a href=\"javascript:"+func_name+"("+(iCurrentPage+1)+",1)\" onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\""+imgPath+"/right_page.gif\" border=0 title='Next Page' align=absmiddle></a>\n");
	   }
	   else {
		  page.append("<img src=\""+imgPath+"/right_page.gif\" border=0 title='Next Page' align=absmiddle>\n");
	   }
//				 </td> </tr> </table>
	   if (iTemp+1<= iTotalPageCount) {
		 page.append("<a href=\"javascript:"+func_name+"("+(iTemp+1)+",1)\"  onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\""+imgPath+"/double_right_page.gif\" border=0 align=absmiddle></a>\n");
	   }else{
		 page.append("<img src=\""+imgPath+"/double_right_page.gif\" border=0 align=absmiddle>\n");
	   }
//	 page.append("</td><td align=right width=125>

	  return page.toString();
   }


}
