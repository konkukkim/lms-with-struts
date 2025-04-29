package com.edutrack.framework.persist.util;
/*
 * Created on 2004. 7. 7.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TableInfo {
	private String name = "";
	private String tableMap = "";
	private String dtoPackage = "";
	private String suffix = "";
	
	public TableInfo() {
	}
	/**
	 * 
	 */
	public TableInfo(String name, String map, String dp, String suffix) {
        this.name = name;
        this.tableMap = map;
        this.dtoPackage = dp;
        this.suffix = suffix;
	} 

	/**
	 * @return Returns the dtoPackage.
	 */
	public String getDtoPackage() {
		return dtoPackage;
	}
	/**
	 * @param dtoPackage The dtoPackage to set.
	 */
	public void setDtoPackage(String dtoPackage) {
		this.dtoPackage = dtoPackage;
	}
	/**
	 * @return Returns the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name The name to set.
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return Returns the suffix.
	 */
	public String getSuffix() {
		return suffix;
	}
	/**
	 * @param suffix The suffix to set.
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	/**
	 * @return Returns the tableMap.
	 */
	public String getTableMap() {
		return tableMap;
	}
	/**
	 * @param tableMap The tableMap to set.
	 */
	public void setTableMap(String tableMap) {
		this.tableMap = tableMap;
	}
}


