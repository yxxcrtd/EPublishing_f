package cn.digitalpublishing.util.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.daxtech.framework.model.Param;
import  com.bertrams.AESEncryptionUtil;

public class dawsonEncryption {	
	
		public static String getEncryptOnlineReadUrl(String isbn,String lang) throws Exception{
			String url=null;
			try{
				String newurl=Param.getParam("dawson.config").get("onlineRead").replace("-",":");	
				String key=Param.getParam("dawson.config").get("apiKey");	
				String iv=AESEncryptionUtil.generateHexEncodedIV();
				newurl+=AESEncryptionUtil.encrypt(key, iv, isbn) + "?lang=" + lang + "&v=" + iv;
				url=newurl;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
			return url;
		}
	
		public static String getEncryptDownloadUrl(String isbn,String lang,Integer day) throws Exception{
			String url=null;
			try{
				String newurl=Param.getParam("dawson.config").get("directDownload").replace("-",":");	
				String key=Param.getParam("dawson.config").get("apiKey");	
				String iv=AESEncryptionUtil.generateHexEncodedIV();
				newurl+=AESEncryptionUtil.encrypt(key, iv, isbn) + "/S" + day + "?lang=" + lang + "&v=" + iv;
				url=newurl;
			}catch(Exception e){
				e.printStackTrace();
				throw e;
			}
			return url;
		}
	
		public static void main(String[] args) {
			try {			
				String url="http://qa.dawsonera.com/depp/reader/protected/api/external/EBookView/S";	
				String apiKey="bb9105c51de2ca4a2ebfaf36cc09964f";	
				String iv=AESEncryptionUtil.generateHexEncodedIV();
				url+=AESEncryptionUtil.encrypt(apiKey, iv, "9781845930912") + "?lang=zh&v=" + iv;

				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
}
