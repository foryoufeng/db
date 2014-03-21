package cn.yuol.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionContext;

public class FileUploadAction implements Action{
   
	private File upload;
     private String uploadFileName;
     private String uploadContentType;
     public File getUpload() {
		return upload;
	}
	public void setUpload(File upload) {
		this.upload = upload;
	}
	public String getUploadFileName() {
		return uploadFileName;
	}
	public void setUploadFileName(String uploadFileName) {
		this.uploadFileName = uploadFileName;
	}
	public String getUploadContentType() {
		return uploadContentType;
	}
	public void setUploadContentType(String uploadContentType) {
		this.uploadContentType = uploadContentType;
	}
	public String add(){
    	 return SUCCESS;
     }
     public String show(){
    	 try {
			FileUtils.copyFile(upload, new File("D:/test/"+uploadFileName));
		} catch (IOException e) {
			ActionContext.getContext().put("error", e.toString());
			 return ERROR;
		}
    	 return SUCCESS;
     }
}
