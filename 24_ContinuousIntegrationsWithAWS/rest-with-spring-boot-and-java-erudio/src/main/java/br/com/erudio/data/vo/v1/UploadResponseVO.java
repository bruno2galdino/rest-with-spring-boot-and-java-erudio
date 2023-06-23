package br.com.erudio.data.vo.v1;

import java.io.Serializable;

public class UploadResponseVO implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileDownload;
    private String fileType;
    private long size;

    public UploadResponseVO(){}

    public UploadResponseVO(String fileName, String fileDownload, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownload = fileDownload;
        this.fileType = fileType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownload() {
        return fileDownload;
    }

    public void setFileDownload(String fileDownload) {
        this.fileDownload = fileDownload;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
    
}
