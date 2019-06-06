package cn.digitalpublishing.util.io;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IOHandler {
	
	public static String creatFolder(String folderName,String basePath){
		String path="";
		File dirFile  = null ;
        try  {
           dirFile  =   new  File(basePath+"/"+folderName);
            if (!(dirFile.exists())&&!(dirFile.isDirectory()))  {
                dirFile.mkdirs();
            }
            path=basePath+"/"+folderName;
        }catch (Exception e)  {
           e.printStackTrace();
           System.out.println(e);
        }
        return path;
	}
	
	public static String createFile(String fileName,String prefix,String basePath,String content,String charset) throws IOException{
		String path="";
		File file  = null ;
		FileOutputStream fio=null;
        try  {
           file  =   new  File(basePath+"/"+fileName+"."+prefix);
            if (!(file.exists())&&!(file.isFile())){
            	path=basePath+"/"+fileName+"."+prefix;
        		fio = new FileOutputStream(file);   
        		//String s = gbToUtf8(content);   
        		fio.write(content.getBytes(charset));   
        		fio.close();
            } 
        }catch (IOException e)  {
           e.printStackTrace();
           System.out.println(e);
        }finally { 
			if (fio != null) { 
				fio.close(); 
			} 
		} 
		return path;
	}
	
	public static void ioCopy(String src,String des)throws IOException{
	     File file = new File(src);
	     File file1 = new File(des);
	     if(!file.exists()){
	    	 System.out.println(file.getName()+"不存在");
	     }else{
	    	 System.out.println(file.getName()+"存在");
	     }
	     if(file.isFile()){
	    	 byte[] b = new byte[(int) file.length()];
	    	 FileInputStream is=null;
	    	 FileOutputStream ps = null;
		     try {
		    	 is= new FileInputStream(file);
		    	 ps= new FileOutputStream(file1);
		    	 is.read(b);
		    	 ps.write(b);
		     }catch(IOException e) {
		    	 e.printStackTrace();
		     }finally{
		    	 if(is!=null){
		    		 is.close();
		    	 }
		    	 if(ps!=null){
		    		 ps.close();
		    	 }
		     }
	     }else if(file.isDirectory()){
	    	 File[] array = file.listFiles();
	    	 if(array!=null&&array.length>0){
	    		 for(int i=0;i<array.length;i++){  
	    			 ioCopy(src+"/"+array[i].getName(),des+"/"+array[i].getName());
	    		 }
	    	 }
	    }   
	}
	
	public static boolean deleteFile(String fileName,String prefix,String basePath){
		boolean isSuccess=false;
		File file  = null ;
        try  {
           file  =   new  File(basePath+"/"+fileName+"."+prefix);
            if (file.exists()&&file.isFile()){
            	isSuccess= file.delete();
            }else{
            	isSuccess=true;
            }
        }catch (Exception e)  {
           e.printStackTrace();
           System.out.println(e);
        }
		return isSuccess;
	}
	
	public static boolean writeFile (String content,String path)throws IOException { 
		// 先读取原有文件内容，然后进行写入操作 
		boolean flag = false; 
		String filein = content; 
		String temp = ""; 

		FileInputStream fis = null; 
		InputStreamReader isr = null; 
		BufferedReader br = null; 

		FileOutputStream fos = null; 
		PrintWriter pw = null; 
		try { 
			// 文件路径 
			File file = new File(path); 
			// 将文件读入输入流 
			fis = new FileInputStream(file); 
			isr = new InputStreamReader(fis); 
			br = new BufferedReader(isr); 
			StringBuffer buf = new StringBuffer(); 

			// 保存该文件原有的内容 
			for (int j = 1; (temp = br.readLine()) != null; j++) { 
				buf = buf.append(temp); 
				// System.getProperty("line.separator") 
				// 行与行之间的分隔符 相当于“\n” 
				buf = buf.append(System.getProperty("line.separator")); 
			} 
			buf.append(filein); 
			fos = new FileOutputStream(file); 
			pw = new PrintWriter(fos); 
			pw.write(buf.toString().toCharArray()); 
			pw.flush(); 
			flag = true; 
		}catch (IOException e1){ 
			throw e1; 
		}finally { 
			if (pw != null){ 
				pw.close(); 
			} 
			if (fos != null) { 
				fos.close(); 
			} 
			if (br != null) { 
				br.close(); 
			} 
			if (isr != null) { 
				isr.close(); 
			} 
			if (fis != null) { 
				fis.close(); 
			} 
		} 
		return flag; 
	} 



	public static String gbToUtf8(String str) throws UnsupportedEncodingException {   
		StringBuffer sb = new StringBuffer();   
		for (int i = 0; i < str.length(); i++) {   
			String s = str.substring(i, i + 1);   
			if (s.charAt(0) > 0x80) {   
				byte[] bytes = s.getBytes("Unicode");   
				String binaryStr = "";   
				for (int j = 2; j < bytes.length; j += 2) {   
					// the first byte   
					String hexStr = getHexString(bytes[j + 1]);   
					String binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
					binaryStr += binStr;   
					// the second byte   
					hexStr = getHexString(bytes[j]);   
					binStr = getBinaryString(Integer.valueOf(hexStr, 16));   
					binaryStr += binStr;   
				}   
				// convert unicode to utf-8   
				String s1 = "1110" + binaryStr.substring(0, 4);   
				String s2 = "10" + binaryStr.substring(4, 10);   
				String s3 = "10" + binaryStr.substring(10, 16);   
				byte[] bs = new byte[3];   
				bs[0] = Integer.valueOf(s1, 2).byteValue();   
				bs[1] = Integer.valueOf(s2, 2).byteValue();   
				bs[2] = Integer.valueOf(s3, 2).byteValue();   
				String ss = new String(bs, "UTF-8");   
				sb.append(ss);   
			} else {   
				sb.append(s);   
			}   
		}   
		return sb.toString();   
	}   


	private static String getHexString(byte b) {   
		String hexStr = Integer.toHexString(b);   
		int m = hexStr.length();   
		if (m < 2) {   
			hexStr = "0" + hexStr;   
		} else {   
			hexStr = hexStr.substring(m - 2);   
		}   
		return hexStr;   
	}   
	
	private static String getBinaryString(int i) {   
		String binaryStr = Integer.toBinaryString(i);   
		int length = binaryStr.length();   
		for (int l = 0; l < 8 - length; l++) {   
			binaryStr = "0" + binaryStr;   
		}   
		return binaryStr;   
	}
	
	/**
	 * 文件 下载
	 * @throws IOException 
	 */
	 public static void download(String file, String realName, HttpServletRequest request, HttpServletResponse response) throws IOException {
		 
		BufferedInputStream buf = null;
		ServletOutputStream myOut = null;
		
		try {

			myOut = response.getOutputStream();
			File myfile = new File(file);

			//set response headers
			response.setContentType("text/html");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(realName,"UTF-8").replace("+"," "));
			response.setContentLength((int) myfile.length());

			FileInputStream input = new FileInputStream(myfile);
			buf = new BufferedInputStream(input);
			int readBytes = 0;

			//read from the file; write to the ServletOutputStream
			while ((readBytes = buf.read()) != -1)
				myOut.write(readBytes);
			
		}catch (Exception e) {
		
			if (myOut != null)
				myOut.close();
			if (buf != null)
				buf.close();
				
		} finally {
			//close the input/output streams
			if (myOut != null)
				myOut.close();
			if (buf != null)
				buf.close();
		}
	}

}
