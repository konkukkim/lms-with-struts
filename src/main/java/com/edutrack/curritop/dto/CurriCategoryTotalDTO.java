package com.edutrack.curritop.dto;


import java.io.Serializable;


public class CurriCategoryTotalDTO implements Serializable {

	private String systemCode;
	private String pareCode1;
	private String pareCode2;
	private String cateCode;
	private String pareCode1Name;
	private String pareCode2Name;
	private String cateName;
	private String cateInfo;
	private String cateStatus;
	private int	cateDepth;
	private String cateImg1;
	private String cateImg2;
	private String cateImg3;
	private String useYn;
	private String regId;
	private String regDate;
	private String modId;
	private String modDate;
	
	public CurriCategoryTotalDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String cateName) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.cateName = cateName;
	}
	
	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String pareCode2, String cateName) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.pareCode2 = pareCode2;
		this.cateName = cateName;
	}
	
/*
	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String pareCode2, String cateName, String cateInfo) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.pareCode2 = pareCode2;
		this.cateName = cateName;
		this.cateInfo = cateInfo;
	}
*/
	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String cateName, String cateStatus, int cateDepth) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.cateName = cateName;
		this.cateStatus = cateStatus;
		this.cateDepth = cateDepth;
	}
	
	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String pareCode2, String cateCode, String cateName) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.pareCode2 = pareCode2;
		this.cateCode = cateCode;
		this.cateName = cateName;
	}
	
	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String pareCode2, String cateCode, String cateName, String cateStatus, int cateDepth, String cateImg1, String cateImg2, String cateImg3, String useYn, String regId, String regDate, String modId, String modDate) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.pareCode1 = pareCode2;
		this.pareCode1 = cateCode;
		this.cateName = cateName;
		this.cateStatus = cateStatus;
		this.cateDepth = cateDepth;
		this.cateImg1 = cateImg1;
		this.cateImg2 = cateImg2;
		this.cateImg3 = cateImg3;
		this.useYn = useYn;
		this.regId = regId;
		this.regDate = regDate;
		this.modId = modId;
		this.modDate = modDate;
	}

	public CurriCategoryTotalDTO(String systemCode, String pareCode1, String pareCode2, String cateCode, String pareCode1Name, String pareCode2Name, String cateName ) {
		super();
		// TODO Auto-generated constructor stub
		this.systemCode = systemCode;
		this.pareCode1 = pareCode1;
		this.pareCode2 = pareCode2;
		this.cateCode = cateCode;
		this.pareCode1Name = pareCode1Name;
		this.pareCode2Name = pareCode2Name;
		this.cateName = cateName;
	}
	
	
	public String getCateImg1() {
		return cateImg1;
	}

	public void setCateImg1(String cateImg1) {
		this.cateImg1 = cateImg1;
	}

	public String getCateImg2() {
		return cateImg2;
	}

	public void setCateImg2(String cateImg2) {
		this.cateImg2 = cateImg2;
	}

	public String getCateImg3() {
		return cateImg3;
	}

	public void setCateImg3(String cateImg3) {
		this.cateImg3 = cateImg3;
	}

	public String getPareCode1Name() {
		return pareCode1Name;
	}
	public void setPareCode1Name(String pareCode1Name) {
		this.pareCode1Name = pareCode1Name;
	}
	public String getPareCode2Name() {
		return pareCode2Name;
	}
	public void setPareCode2Name(String pareCode2Name) {
		this.pareCode2Name = pareCode2Name;
	}
	public String getCateName() {
		return cateName;
	}
	
	public void setCateName(String cateName) {
		this.cateName = cateName;
	}
	
	public String getCateInfo() {
		return cateInfo;
	}

	public void setCateInfo(String cateInfo) {
		this.cateInfo = cateInfo;
	}
	
	public String getCateStatus() {
		return cateStatus;
	}
	
	public void setCateStatus(String cateStatus) {
		this.cateStatus = cateStatus;
	}
	
	public String getModDate() {
		return modDate;
	}
	
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}
	
	public String getPareCode1() {
		return pareCode1;
	}

	public void setPareCode1(String pareCode1) {
		this.pareCode1 = pareCode1;
	}
	
	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	
	public String getRegId() {
		return regId;
	}
	
	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getSystemCode() {
		return systemCode;
	}
	
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}
	
	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getCateCode() {
		return cateCode;
	}

	public void setCateCode(String cateCode) {
		this.cateCode = cateCode;
	}

	public String getPareCode2() {
		return pareCode2;
	}
	
	public void setPareCode2(String pareCode2) {
		this.pareCode2 = pareCode2;
	}

	public int getCateDepth() {
		return cateDepth;
	}
	
	public void setCateDepth(int cateDepth) {
		this.cateDepth = cateDepth;
	}
}
