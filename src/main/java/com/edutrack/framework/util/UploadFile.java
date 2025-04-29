/*
 * Created on 2004. 7. 16.
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.edutrack.framework.util;

/*********************************************************
 * 프로그램 : UploadFile.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 7. 16.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class UploadFile {
    private String objName = "";
    private String rootName = "";
    private String uploadName = "";
    private long size = 0;

    /**
     * 
     */
    public UploadFile() {
    }

    /**
     * @return Returns the rootName.
     */
    public String getRootName() {
        return rootName;
    }
    /**
     * @param rootName The rootName to set.
     */
    public void setRootName(String rootName) {
        this.rootName = rootName;
    }
    /**
     * @return Returns the size.
     */
    public long getSize() {
        return size;
    }
    /**
     * @param size The size to set.
     */
    public void setSize(long size) {
        this.size = size;
    }
    /**
     * @return Returns the uploadName.
     */
    public String getUploadName() {
        return uploadName;
    }
    /**
     * @param uploadName The uploadName to set.
     */
    public void setUploadName(String uploadName) {
        this.uploadName = uploadName;
    }
    /**
     * @return Returns the objName.
     */
    public String getObjName() {
        return objName;
    }
    /**
     * @param objName The objName to set.
     */
    public void setObjName(String objName) {
        this.objName = objName;
    }
}
