package com.edutrack.framework.util;

import java.util.ArrayList;

import com.oreilly.servlet.MultipartRequest;

/*********************************************************
 * 프로그램 : UploadFiles.java
 * 모 듈 명 : 
 * 설    명 : 
 * 테 이 블 :
 * 작 성 자 : chorongjang
 * 작 성 일 : 2004. 7. 16.
 * 수 정 일 :
 * 수정사항 : 
 *********************************************************/
public class UploadFiles {
    private ArrayList files = null;
    private String status = "S"; // 파일이 없을 경우 : N, 파일 용량을 초과 했을 경우 : O
    private MultipartRequest multipart = null;
    private String uploadPath = "";
    /**
     * 
     */
    public UploadFiles() {
        files = new ArrayList();
    }

    /**
     * @return Returns the multipart.
     */
    public MultipartRequest getMultipart() {
        return multipart;
    }
    /**
     * @param multipart The multipart to set.
     */
    public void setMultipart(MultipartRequest multipart) {
        this.multipart = multipart;
    }
    /**
     * 업로드된 파일을 리스트에 추가한다.
     * @param upfile
     */
    public void addFile(UploadFile upfile){
        files.add(upfile);
    }
    
    /**
     *  현재 추가된 파일의 갯수를 돌려준다.
     * @return
     */
    public int getUploadFileSize(){
        return files.size();
    }
    /**
     * @return Returns the files.
     */
    public ArrayList getFiles() {
        return files;
    }
    /**
     * @param files The files to set.
     */
    public void setFiles(ArrayList files) {
        this.files = files;
    }
    /**
     * @return Returns the status.
     */
    public String getStatus() {
        return status;
    }
    /**
     * @param status The status to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    
    /**
     * @return Returns the uploadPath.
     */
    public String getUploadPath() {
        return uploadPath;
    }
    /**
     * @param uploadPath The uploadPath to set.
     */
    public void setUploadPath(String uploadPath) {
        this.uploadPath = uploadPath;
    }
}   
    
 

