/*
 * Created on 2004. 9. 21.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.common.dto;

/**
 * @author chorongjang
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class EditorParam {
	protected int width=520;						//-- 에디터 가로폭
	protected int height=300;						//-- 에디터 세로높이
	protected String emailMode="false";				//-- 이메일 모드로 동작하기
	protected String showFlag="true";				//-- 위스 구동 유무  이 값을 false로 설정하면 위스가 나타나지 않음
	protected String linkPopupFlag="true";			//-- 링크 자동팝업 설정하기 target _blank
	protected String fileNumberingFlag="true";		//-- 파일 개수 전송하기 VBN_FORM_MediaFileCount라는 변수로 본문에 새로 삽입된 멀티미디어 파일 개수를 전송함(없으면 0을 전송)
	protected String fileLocalPathSendFlag="true";	//-- 파일 로컬경로 전송받기 유무 설정(디폴트는 false)
	protected String menualFlag="false";			//-- 리스트에서 본문 미리보기
	protected String addInfoSendFlag="true";		//-- 부가적인 정보(미리보기, 멀티미디어 개수, 멀티페이지 개수 등)를 서버에 전송할지를 설정
	protected int maxPreviewChars=500;				//-- 본문내용의 앞 부분을 VBN_FORM_Preview란 이름의 폼 변수로 사용자 서버로 부가 전송함
	protected int maxImageSize=500;					//-- 최대 삽입 가능한 이미지 파일의 용량 제한(단위: KByte)
	protected int maxImageCount=2;					//-- 최대 삽입 가능한 이미지 파일의 개수 설정
	protected int maxVideoCount=2;					//-- 최대 삽입 가능한 비디오 파일의 개수 설정
	protected int maxAudioCount=2;					//-- 최대 삽입 가능한 오디오 파일의 개수 설정
	protected int maxFlashCount=2;					//-- 최대 삽입 가능한 플래시 파일의 개수 설정
	protected int maxFileCount=10;					//-- 최대 삽입 가능한(업로딩될) 총 파일의 개수 설정, 다른 개수 제한(이미지,비디오등)보다 우선함
	protected String countAllFileAtModify="true";	//-- 수정시 기 삽입된(쓰기시) 파일을 포함하여 업로딩 파일의 개수를 카운트(제한)함(수정시에만 의미가 있음)
	protected int lineHeight=110;					//-- line height를 더 넓힐 때 사용.
	protected String scrollbarFlag="true";			//-- 지정 사이트 보다 클 경우 가로스크롤 생성
	protected String readWidth="550";				//-- 읽기시 폭(width)을 위스의 width와 다르게 설정할 수 있음.
	protected String replyMode="false";					//-- 답변시 원글 보이기 답변시에만 true로 설정할것
	protected String replyHeader="";				//-- 답변시 헤더 설정하기
	protected String toolBarHidden="attachFile";	//-- 툴바 아이콘을 숨김으로써 기능을 제한할 수 있는 옵션(구분자는콤마,)
		// 	fontFamily,fontSize,newPage,deleteAllPage,redoUndo,	bold,italic,underline,textColor,textBGColor
		//	background,align,indent,lineHeight,list

		//	multipage,image,video,audio,flash,table,tableEdit,figure,link,imageLink,mediaLink,htmlEdit,preview,help


	/**
	 *
	 */
	public EditorParam() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return Returns the emailMode.
	 */
	public String getEmailMode() {
		return emailMode;
	}
	/**
	 * @param emailMode The emailMode to set.
	 */
	public void setEmailMode(String emailMode) {
		this.emailMode = emailMode;
	}

	/**
	 * @return Returns the addInfoSendFlag.
	 */
	public String getAddInfoSendFlag() {
		return addInfoSendFlag;
	}
	/**
	 * @param addInfoSendFlag The addInfoSendFlag to set.
	 */
	public void setAddInfoSendFlag(String addInfoSendFlag) {
		this.addInfoSendFlag = addInfoSendFlag;
	}
	/**
	 * @return Returns the countAllFileAtModify.
	 */
	public String getCountAllFileAtModify() {
		return countAllFileAtModify;
	}
	/**
	 * @param countAllFileAtModify The countAllFileAtModify to set.
	 */
	public void setCountAllFileAtModify(String countAllFileAtModify) {
		this.countAllFileAtModify = countAllFileAtModify;
	}
	/**
	 * @return Returns the fileLocalPathSendFlag.
	 */
	public String getFileLocalPathSendFlag() {
		return fileLocalPathSendFlag;
	}
	/**
	 * @param fileLocalPathSendFlag The fileLocalPathSendFlag to set.
	 */
	public void setFileLocalPathSendFlag(String fileLocalPathSendFlag) {
		this.fileLocalPathSendFlag = fileLocalPathSendFlag;
	}
	/**
	 * @return Returns the fileNumberingFlag.
	 */
	public String getFileNumberingFlag() {
		return fileNumberingFlag;
	}
	/**
	 * @param fileNumberingFlag The fileNumberingFlag to set.
	 */
	public void setFileNumberingFlag(String fileNumberingFlag) {
		this.fileNumberingFlag = fileNumberingFlag;
	}
	/**
	 * @return Returns the height.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param height The height to set.
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * @return Returns the lineHeight.
	 */
	public int getLineHeight() {
		return lineHeight;
	}
	/**
	 * @param lineHeight The lineHeight to set.
	 */
	public void setLineHeight(int lineHeight) {
		this.lineHeight = lineHeight;
	}
	/**
	 * @return Returns the linkPopupFlag.
	 */
	public String getLinkPopupFlag() {
		return linkPopupFlag;
	}
	/**
	 * @param linkPopupFlag The linkPopupFlag to set.
	 */
	public void setLinkPopupFlag(String linkPopupFlag) {
		this.linkPopupFlag = linkPopupFlag;
	}
	/**
	 * @return Returns the maxAudioCount.
	 */
	public int getMaxAudioCount() {
		return maxAudioCount;
	}
	/**
	 * @param maxAudioCount The maxAudioCount to set.
	 */
	public void setMaxAudioCount(int maxAudioCount) {
		this.maxAudioCount = maxAudioCount;
	}
	/**
	 * @return Returns the maxFileCount.
	 */
	public int getMaxFileCount() {
		return maxFileCount;
	}
	/**
	 * @param maxFileCount The maxFileCount to set.
	 */
	public void setMaxFileCount(int maxFileCount) {
		this.maxFileCount = maxFileCount;
	}
	/**
	 * @return Returns the maxFlashCount.
	 */
	public int getMaxFlashCount() {
		return maxFlashCount;
	}
	/**
	 * @param maxFlashCount The maxFlashCount to set.
	 */
	public void setMaxFlashCount(int maxFlashCount) {
		this.maxFlashCount = maxFlashCount;
	}
	/**
	 * @return Returns the maxImageCount.
	 */
	public int getMaxImageCount() {
		return maxImageCount;
	}
	/**
	 * @param maxImageCount The maxImageCount to set.
	 */
	public void setMaxImageCount(int maxImageCount) {
		this.maxImageCount = maxImageCount;
	}
	/**
	 * @return Returns the maxImageSize.
	 */
	public int getMaxImageSize() {
		return maxImageSize;
	}
	/**
	 * @param maxImageSize The maxImageSize to set.
	 */
	public void setMaxImageSize(int maxImageSize) {
		this.maxImageSize = maxImageSize;
	}
	/**
	 * @return Returns the maxPreviewChars.
	 */
	public int getMaxPreviewChars() {
		return maxPreviewChars;
	}
	/**
	 * @param maxPreviewChars The maxPreviewChars to set.
	 */
	public void setMaxPreviewChars(int maxPreviewChars) {
		this.maxPreviewChars = maxPreviewChars;
	}
	/**
	 * @return Returns the maxVideoCount.
	 */
	public int getMaxVideoCount() {
		return maxVideoCount;
	}
	/**
	 * @param maxVideoCount The maxVideoCount to set.
	 */
	public void setMaxVideoCount(int maxVideoCount) {
		this.maxVideoCount = maxVideoCount;
	}
	/**
	 * @return Returns the menualFlag.
	 */
	public String getMenualFlag() {
		return menualFlag;
	}
	/**
	 * @param menualFlag The menualFlag to set.
	 */
	public void setMenualFlag(String menualFlag) {
		this.menualFlag = menualFlag;
	}
	/**
	 * @return Returns the readWidth.
	 */
	public String getReadWidth() {
		return readWidth;
	}
	/**
	 * @param readWidth The readWidth to set.
	 */
	public void setReadWidth(String readWidth) {
		this.readWidth = readWidth;
	}
	/**
	 * @return Returns the replyHeader.
	 */
	public String getReplyHeader() {
		return replyHeader;
	}
	/**
	 * @param replyHeader The replyHeader to set.
	 */
	public void setReplyHeader(String replyHeader) {
		this.replyHeader = replyHeader;
	}
	/**
	 * @return Returns the replyMode.
	 */
	public String getReplyMode() {
		return replyMode;
	}
	/**
	 * @param replyMode The replyMode to set.
	 */
	public void setReplyMode(String replyMode) {
		this.replyMode = replyMode;
	}
	/**
	 * @return Returns the scrollbarFlag.
	 */
	public String getScrollbarFlag() {
		return scrollbarFlag;
	}
	/**
	 * @param scrollbarFlag The scrollbarFlag to set.
	 */
	public void setScrollbarFlag(String scrollbarFlag) {
		this.scrollbarFlag = scrollbarFlag;
	}
	/**
	 * @return Returns the width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width The width to set.
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return Returns the toolBarHidden.
	 */
	public String getToolBarHidden() {
		return toolBarHidden;
	}
	/**
	 * @param toolBarHidden The toolBarHidden to set.
	 */
	public void setToolBarHidden(String toolBarHidden) {
		this.toolBarHidden = toolBarHidden;
	}
	/**
	 * @return Returns the showFlag.
	 */
	public String getShowFlag() {
		return showFlag;
	}
	/**
	 * @param showFlag The showFlag to set.
	 */
	public void setShowFlag(String showFlag) {
		this.showFlag = showFlag;
	}
}
