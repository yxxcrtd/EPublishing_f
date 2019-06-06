package cn.digitalpublishing.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@SuppressWarnings("serial")
public class CommonUpload extends HttpServlet {
	
	private String uploadPath = "/upload/"; // 上传文件的目录
	
    private String tempPath = "/temp/buffer/"; // 临时文件目录
    
    File tempPathFile;
    
    public void init() throws ServletException {
    }
 

    public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
    	try{
    		String webRoot = request.getSession().getServletContext().getRealPath("");
    		File tempPathFile = new File(webRoot+tempPath);
            if (!tempPathFile.exists()) {
               tempPathFile.mkdirs();
            }
            File uploadFile = new File(webRoot+uploadPath);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
            String saveDir = request.getParameter("saveDir");
            uploadFile = new File(webRoot+uploadPath+saveDir);
            if (!uploadFile.exists()) {
                uploadFile.mkdirs();
            }
    		// Create a factory for disk-based file items
    		DiskFileItemFactory factory = new DiskFileItemFactory();
    		// Set factory constraints
    		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
    		factory.setRepository(tempPathFile);// 设置缓冲区目录
    		// Create a new file upload handler
    		ServletFileUpload upload = new ServletFileUpload(factory);
    		// Set overall request size constraint
    		upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
    		List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
    		Iterator<FileItem> i = items.iterator();
    		while (i.hasNext()){
    			FileItem fi = (FileItem) i.next();
    			String fileName = fi.getName();
    			if (fileName != null){
    				File fullFile = new File(fi.getName());
    				File savedFile = new File(webRoot+uploadPath+saveDir, fullFile.getName());
    				fi.write(savedFile);
    				System.out.println("XXXXXXXXXXXXXXXXXXX File Name XXXXXXXXXXXXXXXXXX:"+fullFile.getName());
    				System.out.println("XXXXXXXXXXXXXXXXXXX File Save XXXXXXXXXXXXXXXXXX:"+webRoot+uploadPath+saveDir+fullFile.getName());
    			}
    		}
    		System.out.print("upload succeed");
    	}catch (Exception e){
    		System.out.println(e.getMessage());
    		// 可以跳转出错页面
    		//e.printStackTrace();
    	}
}


}
