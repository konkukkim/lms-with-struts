package com.edutrack.framework.persist;

import com.edutrack.framework.configuration.Configuration;
import com.edutrack.framework.configuration.ConfigurationFactory;
import com.edutrack.framework.util.StringUtil;

public class ListStatement extends SelectStatement {
	      private Configuration config = ConfigurationFactory.getInstance().getConfiguration();
          private String select;
          private String from;
          private String where;
          private String groupby;
          private String having;
          private String orderby;
          private String alias;
          private String cutCol;
          private String totalCol;

          /**
           * 생성자
           */
          public ListStatement()
          {
                  super();
          }

		public String getSql(){
			StringBuffer s = new StringBuffer();
			s.append(SELECT).append(select);
			s.append(FROM).append(from);
			if (StringUtil.isNotNull(where)) s.append(WHERE).append(where);
			if (StringUtil.isNotNull(groupby)) s.append(GROUP_BY).append(groupby);
			if (StringUtil.isNotNull(having)) s.append(HAVING).append(having);
			if (StringUtil.isNotNull(orderby)) s.append(ORDER_BY).append(orderby);

			return s.toString();
		}

		public String getSql(int cutcnt, String cutcol){
			StringBuffer s = new StringBuffer();
			s.append(SELECT).append(" TOP "+cutcnt+" "+cutcol);
			s.append(FROM).append(from);
			if (StringUtil.isNotNull(where)) s.append(WHERE).append(where);
			if (StringUtil.isNotNull(groupby)) s.append(GROUP_BY).append(groupby);
			if (StringUtil.isNotNull(having)) s.append(HAVING).append(having);
			if (StringUtil.isNotNull(orderby)) s.append(ORDER_BY).append(orderby);

			return s.toString();
		}

		public String getListSql()
          {
                  StringBuffer s = new StringBuffer();
                  s.append(SELECT).append(alias);
                  if (StringUtil.isNotNull(groupby) || StringUtil.isNotNull(orderby) || (StringUtil.isNotNull(select) && select.trim().toLowerCase().startsWith("distinct"))){
					s.append(PART_MIDDLE).append(alias);
					s.append(PART_FROM);
                    s.append(" ( ").append(getSql()).append(" ) ");
                    s.append(WHERE);
                  }else{
					s.append(PART_MIDDLE).append(select);
					s.append(PART_FROM);
                    s.append(from);
					s.append(WHERE);
                    if(StringUtil.isNotNull(where)) s.append(where).append(" AND ");
                  }
                  s.append(" limit 1 AND limit ? ) WHERE RNUM>= ? AND RNUM<= ?");

                  return s.toString();
          } 

          public String getListSql(int linescale, int start)
          {
          		  StringBuffer s = new StringBuffer();
                  s.append(SELECT).append(" TOP "+linescale + " ").append(select);
                  s.append(FROM).append(from);
                  if(StringUtil.isNotNull(where))
                  	s.append(WHERE).append(where).append(" and (( ");
                  else 
                  	s.append(WHERE).append(" (( ");
                  
                  s.append(cutCol).append(" ) NOT IN (");
				  s.append(getSql(start, cutCol)).append(" ))");
				  if (StringUtil.isNotNull(groupby)) s.append(GROUP_BY).append(groupby);
				  if (StringUtil.isNotNull(having)) s.append(HAVING).append(having);
				  if (StringUtil.isNotNull(orderby)) s.append(ORDER_BY).append(orderby);
				  
				  return s.toString();
          } 
                    
      	public String getListSql2(int linescale, int start) 
    	{
    		StringBuffer s = new StringBuffer();
    		s.append(getSql());
    		s.append(" LIMIT "+String.valueOf(start)+", "+String.valueOf(linescale)+" ");
    		
    		return s.toString();
    	}
		
          public String getTotalSql(int curPage, int listScale, int pageScale)
          {
			StringBuffer s = new StringBuffer();
			s.append("SELECT count(*) FROM (" );
			s.append(SELECT).append(getTotalCol());
			s.append(FROM).append(from);
			if (StringUtil.isNotNull(where)) s.append(WHERE).append(where);
			if (StringUtil.isNotNull(groupby)) s.append(GROUP_BY).append(groupby);
			if (StringUtil.isNotNull(having)) s.append(HAVING).append(having);
			if(PersistBrokerFactory.defaultBroker.equals("oracle"))
				s.append(" ) ");
			else 
				s.append(" ) DERIVEDTBL");
			
			return s.toString();
          } 

          /**
           * Having절을 돌려준다.
           */
          public String getHaving()
          {
                  return having;
          }

          /**
           * GroupBy절을 돌려준다.
           */
          public String getGroupby()
          {
                  return groupby;

          }

          /**
           * OrderBy절을 돌려준다.
           */
          public String getOrderby()
          {
                  return orderby;
          }

          /**
           * Where 절을 돌려준다.
           */
          public String getWhere()
          {
                  return where;

          }

          /**
           * From 절을 돌려준다.
           */
          public String getFrom()
          {
                  return from;
          }

          /**
           * Select절을 돌려준다.
           */
          public String getSelect()
          {
                  return select;
          }
          /**
           * AliasSelect절을 돌려준다.
           */
          public String getAliasSelect()
          {
                  return alias;
          }

          /**
           * From 절을 셋팅해준다.
           * @param string
           */
          public void setFrom(String string)
          {
				if(string == null) this.from = "";
                else from = string.trim();
          }

          /**
           * GroupBy절을 셋팅해준다.
           * @param string
           */
          public void setGroupby(String string)
          {
			if(string == null) this.groupby = "";
			else groupby = string.trim();
          }

          /**
           * Having절을 셋팅해준다.
           * @param string
           */
          public void setHaving(String string)
          {
			if(string == null) this.having = "";
			else having = string.trim();
          }

          /**
           * OrderBy절을 셋팅해준다.
           * @param string
           */
          public void setOrderby(String string)
          {
			if(string == null) this.orderby = "";
			else orderby = string.trim();
          }

          /**
           * Select절을 셋팅해준다.
           * @param string
           */
          public void setSelect(String string)
          {
			if(string == null) this.select = "";
			else select = string.trim()/*.toUpperCase()*/;   // 운영에서 왜 에러 안나는지 모르겠다 2005-04-29
          }

          /**
           * AliasSelect절을 셋팅해준다.
           * @param string
           */
          public void setAliasSelect(String string)
          {
			if(string == null) this.alias = "";
			else alias = string.trim();
          }

          /**
           * Where절을 셋팅해준다.
           * @param string
           */
          public void setWhere(String string)
          {
			if(string == null) this.where = "";
			else where = string.trim();
          }

		/**
		 * @return Returns the alias.
		 */
		public String getAlias() {
			return alias;
		}
		/**
		 * @param alias The alias to set.
		 */
		public void setAlias(String alias) {
			this.alias = alias;
		}
		/**
		 * @return Returns the totalCol.
		 */
		public String getTotalCol() {
			return totalCol;
		}
		/**
		 * @param totalCol The totalCol to set.
		 */
		public void setTotalCol(String totalCol) {
			this.totalCol = totalCol;
		}

		public void addWhere(String value){
			if(StringUtil.NVL(value) || value.trim().equals("")) return ;
			
			if(this.where == null) this.where = "";
			String addWhere = value.trim().toUpperCase();
			if(StringUtil.isNotNull(this.where) && addWhere.startsWith("AND") == false){
				value = " and " + value;
			}else if(StringUtil.NVL(this.where) && addWhere.startsWith("AND")){
				addWhere = value.toUpperCase();
				value = value.substring(addWhere.indexOf("AND")+3);
			}
			this.where = this.where + value;
		}
		
		/**
		 * @return Returns the cutCol.
		 */
		public String getCutCol() {
			return cutCol;
		}
		/**
		 * @param cutCol The cutCol to set.
		 */
		public void setCutCol(String cutCol) {
			this.cutCol = cutCol;
		}
}