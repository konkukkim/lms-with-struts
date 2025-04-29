/**
 * @(#)LmContentsDTO
 *
 * Copyright 2004 mediopia. All Rights Reserved.
 */

package com.edutrack.curritop.dto;


/**
 * CONTENTS File Dto 클래스.
 *
 ************************************************
 * DATE         AUTHOR      DESCRIPTION
 * ----------------------------------------------
 * 2004. 7. 7.  mediopia       Initial Release
 ************************************************
 *
 * @author      mediopia
 * @version     1.0
 * @since       2004. 7. 7.
 */
public class ContentsFileDTO {
    protected String fileName = "";
    protected String fileExt = "";
    protected String fileExtImg = "";
    protected String fileDir = "";
    protected boolean thisDir	 = false;
    protected boolean thisFile = false;
    protected String depth = "";
    protected String fileTime = "";
    protected long fileSize = 0;
    
    
    /**
     * @return Returns the fileExt.
     */
    public String getFileExt() {
        return fileExt;
    }
    /**
     * @param fileExt The fileExt to set.
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }
    /**
     * @return Returns the fileExtImg.
     */
    public String getFileExtImg() {
        return fileExtImg;
    }
    /**
     * @param fileExtImg The fileExtImg to set.
     */
    public void setFileExtImg(String fileExtImg) {
        this.fileExtImg = fileExtImg;
    }
    /**
     * @return Returns the fileTime.
     */
    public String getFileTime() {
        return fileTime;
    }
    /**
     * @param fileTime The fileTime to set.
     */
    public void setFileTime(String fileTime) {
        this.fileTime = fileTime;
    }
    /**
     * @return Returns the fileSize.
     */
    public long getFileSize() {
        return fileSize;
    }
    /**
     * @param fileTime The fileTime to set.
     */
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
    
    
    /**
     * @return Returns the depth.
     */
    public String getDepth() {
        return depth;
    }
    /**
     * @param depth The depth to set.
     */
    public void setDepth(String depth) {
        this.depth = depth;
    }
    /**
     * @return Returns the fileName.
     */
    public String getFileName() {
        return fileName;
    }
    /**
     * @param fileName The fileName to set.
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    /**
     * @return Returns the fileDir.
     */
    public String getFileDir() {
        return fileDir;
    }
    /**
     * @param fileDir The fileDir to set.
     */
    public void setFileDir(String fileDir) {
        this.fileDir = fileDir;
    }
    
    /**
     * @return Returns the thisDir.
     */
    public boolean isThisDir() {
        return thisDir;
    }
    /**
     * @param thisDir The thisDir to set.
     */
    public void setThisDir(boolean thisDir) {
        this.thisDir = thisDir;
    }
    /**
     * @return Returns the thisFile.
     */
    public boolean isThisFile() {
        return thisFile;
    }
    /**
     * @param thisFile The thisFile to set.
     */
    public void setThisFile(boolean thisFile) {
        this.thisFile = thisFile;
    }
}
