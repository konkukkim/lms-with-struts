package com.edutrack.framework.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author $Author: cvs $
 *
 */
public class PageList extends ArrayList
{
	protected int iCurrentPage	= 1;
	protected int iListScale		= 10;
	protected int iPageScale		= 10;
	protected int iTotalPageCount = 0;

	/**
	 * @param arg0
	 */
	public PageList(int arg0)
	{
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	public PageList()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public PageList(Collection c)
	{
		super(c);


	}
	
	public PageList(Collection c, int currentPage)
	{
		this(c);
		this.iCurrentPage = currentPage;
 	}


	public PageList(Collection c, int currentPage, int listScale)
	{
		this(c);
		this.iCurrentPage = currentPage;
		this.iListScale = listScale;
	}

	public PageList(Collection c, int currentPage, int listScale, int pageScale)
	{
		this(c);
		this.iCurrentPage = currentPage;
		this.iListScale = listScale;
		this.iPageScale = pageScale;
	}
	
	
	public Iterator iterator()
	{
		return new PageListItr();
	}


	class PageListItr implements Iterator {
		/**
		 * Index of element to be returned by subsequent call to next.
		 */
		int cursor = 0;
	
		/**
		 * Index of element returned by most recent call to next or
		 * previous.  Reset to -1 if this element is deleted by a call
		 * to remove.
		 */
		int lastRet = -1;
	
		/**
		 * The modCount value that the iterator believes that the backing
		 * List should have.  If this expectation is violated, the iterator
		 * has detected concurrent modification.
		 */
		int expectedModCount = modCount;
		
		public PageListItr()
		{
			cursor = (iCurrentPage-1)*iListScale;
		}
	
		public boolean hasNext() {
			return cursor != size() && (cursor != iCurrentPage*iListScale);
		}
	
		public Object next() {
			try {
			Object next = get(cursor);
			checkForComodification();
			lastRet = cursor++;
			return next;
			} catch(IndexOutOfBoundsException e) {
			checkForComodification();
			throw new NoSuchElementException();
			}
		}
	
		public void remove() {
//			if (lastRet == -1)
//			throw new IllegalStateException();
//				checkForComodification();
//	
//			try {
//			AbstractList.this.remove(lastRet);
//			if (lastRet < cursor)
//				cursor--;
//			lastRet = -1;
//			expectedModCount = modCount;
//			} catch(IndexOutOfBoundsException e) {
//			throw new ConcurrentModificationException();
//			}
		}
	
		final void checkForComodification() {
			if (modCount != expectedModCount)
			throw new ConcurrentModificationException();
		}
	}
	/**
	 * @return
	 */
	public int getCurrentPage()
	{
		return iCurrentPage;
	}

	/**
	 * @return
	 */
	public int getListScale()
	{
		return iListScale;
	}

	/**
	 * @return
	 */
	public int getPageScale()
	{
		return iPageScale;
	}

	/**
	 * @param i
	 */
	public void setCurrentPage(int i)
	{
		iCurrentPage = i;
	}

	/**
	 * @param i
	 */
	public void setListScale(int i)
	{
		iListScale = i;
	}

	/**
	 * @param i
	 */
	public void setPageScale(int i)
	{
		iPageScale = i;
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

			result.append("\n<script language=\"javascript\">\n");
			result.append("function "+func_name+"(page){\n");
			result.append("\tif(page == null || page <= 0) return;\n");
			result.append("\tdocument.forms[\""+frm+"\"].elements[\""+cur_page+"\"].value = page;\n");
			result.append("\tdocument.forms[\""+frm+"\"].submit();\n");
			result.append("}\n");
			result.append("</script>\n");

//	this.pageScript = true;
			return result.toString();
	}
	
	
	public String getPagging(String func_name){
	  
	  // 전체페이지수를 계산	
	  if(size()%iListScale == 0) this.iTotalPageCount = size()/this.iListScale;
	  else this.iTotalPageCount = size()/this.iListScale+1;
		
	  int iTemp=0;

	  if(func_name == null || "".equals(func_name)) func_name = "goPage";
	  StringBuffer page = new StringBuffer();
	  int intStartPage =((int)Math.ceil((double)iCurrentPage/(double)iPageScale)-1)*iPageScale+1;

	  if (intStartPage>1) {
		 page.append("[<a href=\"javascript:"+func_name+"("+(intStartPage-iPageScale)+")\" onMouseOver=\"window.status='Previous Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\"/img/i_prev2.gif\" border=0 align=absmiddle></a>\n");
	  }else{
		 page.append("[<img src=\"/img/i_prev1.gif\" border=0 align=absmiddle>\n");
	  }

	  for (int i=intStartPage; i<intStartPage + iPageScale ; i++) {
			  iTemp=i;
			  if (i<=iTotalPageCount) {
				   if (i !=iCurrentPage) {
				  page.append("<a href=\"javascript:"+func_name+"("+i+")\" onMouseOver=\"window.status='page " + i + "'; return true\" onMouseOut=\"window.status=''; return true\"><font color=black>"+i+"</font></a>\n");
			  } else {
					page.append("<font color=red>"+ i +"</font>\n");
					   }
			  } else	{
					  break;
			  }
	   }

	  if (iTemp+1<= iTotalPageCount) {
		page.append("<a href=\"javascript:"+func_name+"("+(iTemp+1)+")\"  onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"><img src=\"/img/i_next2.gif\" border=0 align=absmiddle></a>]\n");
	  }else{
		page.append("<img src=\"/img/i_next1.gif\" border=0 align=absmiddle>]\n");
	  }
//	page.append("</td><td align=right width=125>

	  if (iCurrentPage > 1) {
		 page.append("<a href=\"javascript:"+func_name+"("+(iCurrentPage-1)+")\" onMouseOver=\"window.status=First Page; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\"/img/b_mprev01.gif\" border=0 title='Prev Page' align=absmiddle></a>\n");
	  }
	  else {
		 page.append("<img src=\"/img/b_mprev02.gif\" border=0 title=\"Prev Page\" align=absmiddle>\n");
	  }

	  if (iCurrentPage < iTotalPageCount) {
		 page.append("<a href=\"javascript:"+func_name+"("+(iCurrentPage+1)+")\" onMouseOver=\"window.status='Next Page'; return true\" onMouseOut=\"window.status=''; return true\"> <img src=\"/img/b_mnext01.gif\" border=0 title='Next Page' align=absmiddle></a>\n");
	  }
	  else {
		 page.append("<img src=\"/img/b_mnext02.gif\" border=0 title='Next Page' align=absmiddle>\n");
	  }
//				</td> </tr> </table>
	 return page.toString();
  }	

}
