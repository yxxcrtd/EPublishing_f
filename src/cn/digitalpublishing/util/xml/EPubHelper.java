package cn.digitalpublishing.util.xml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.digitalpublishing.util.web.Escape;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.domain.SpineReference;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.domain.TableOfContents;
import nl.siegmann.epublib.epub.EpubReader;


public class EPubHelper {

	String _filePath=null;
	public EPubHelper(String filePath){
		this._filePath=filePath;
	}
	
	/**
	 * 解压 EPUB
	 * @throws IOException 
	 */
	public List<String> unZip(String destDir){
		
		List<String> list = new ArrayList<String>();
		
		destDir = destDir.endsWith( "//" ) ? destDir : destDir + "//" ; 

		byte b[] = new byte [1024]; 

		int length; 

		ZipFile zipFile = null;
		OutputStream outputStream = null;
		try{
			zipFile = new ZipFile( new File(_filePath)); 
			Enumeration enumeration = zipFile.getEntries();
			ZipEntry zipEntry = null ; 
			while (enumeration.hasMoreElements()) { 
				zipEntry = (ZipEntry) enumeration.nextElement();
				File loadFile = new File(destDir + zipEntry.getName()); 
				if (zipEntry.isDirectory()){
					//这段都可以不要，因为每次都貌似从最底层开始遍历的 
					loadFile.mkdirs(); 
				}else{
					if(!loadFile.getParentFile().exists()) 
						loadFile.getParentFile().mkdirs(); 
					list.add(zipEntry.getName());
					outputStream = new FileOutputStream(loadFile); 
					InputStream inputStream = zipFile.getInputStream(zipEntry); 
					while ((length = inputStream.read(b)) > 0) 
						outputStream.write(b, 0, length);
				}
				if(outputStream!=null){
		    		try {
		    			outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		    	}
			} 
			System. out .println("unzip epub file success"); 
	    } catch (IOException e) { 
	    	e.printStackTrace(); 
	    }finally{
	    	if(outputStream!=null){
	    		try {
	    			outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    	if(zipFile!=null){
	    		try {
	    			zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	    	}
	    }
	    return list;
	}
	
	/**
	 * 获取全文文本信息
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public String getText() throws FileNotFoundException, IOException{
		StringBuilder s=new StringBuilder();
		EpubReader epubReader = new EpubReader(); 		
		Book book = epubReader.readEpub(new FileInputStream(_filePath)); 
		//电子书的内容 
		List<Resource> list = book.getContents(); 
		for(int i=0;i<list.size();i++){ 
			Resource  res = list.get(i); 		
			byte[] resdata = res.getData(); 
			String str = new String(resdata,res.getInputEncoding()); 
			Document doc = Jsoup.parse(str,res.getInputEncoding());	
			s.append(doc.text()); 
		} 
		return s.toString();
	}
	/**
	 * 获取章节内容（不去除html标签）
	 * @param epubPath
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<Map<String, Object>> getToc(String epubPath) throws FileNotFoundException, IOException{
		return getToc(epubPath,false);
	}
	/**
	 * 获取章节标题
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public static List<Map<String, Object>> getToc(String epubPath,Boolean isClearTag) throws FileNotFoundException, IOException{
		List<Map<String,Object>> list=null;
		EpubReader epubReader = new EpubReader(); 		
		Book book = epubReader.readEpub(new FileInputStream(epubPath)); 
		String opf=book.getOpfResource().getHref();
		if(opf.indexOf("/")!=-1){
			opf=opf.substring(0,opf.indexOf("/"));
		}
		opf=opf!=null && !"".equals(opf)?opf + "/":"";
		//封面是xhtml的无法在iframe加载，暂时不提取
		/*if(book.getCoverPage()!=null){
			list=new ArrayList<Map<String,Object>>();
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("title", "Cover");
			map.put("href", opf+book.getCoverPage().getHref());
			byte[] resdata = book.getCoverPage().getData(); 
			String str = new String(resdata,book.getCoverPage().getInputEncoding()); 
			if(isClearTag){
				Document doc = Jsoup.parse(str,book.getCoverPage().getInputEncoding());	
				str=doc.text();
			}
			map.put("pageContent", str);
			map.put("mediaType", book.getCoverPage().getMediaType());
			list.add(map);
		}*/
		TableOfContents contentTable = book.getTableOfContents();
		if(contentTable!=null && contentTable.size()>0){
			List<TOCReference>  resList = contentTable.getTocReferences(); 
			if(resList!=null && resList.size()>0){
				list=list==null ?list=new ArrayList<Map<String,Object>>():list;
				for(int i=0;i<resList.size();i++){ 
					TOCReference res=resList.get(i);
					Map<String,Object> map=new HashMap<String, Object>();
					map.put("title", res.getTitle());
					map.put("href", opf+res.getCompleteHref());
					byte[] resdata = res.getResource().getData(); 
					String str = new String(resdata,res.getResource().getInputEncoding()); 
					if(isClearTag){
						Document doc = Jsoup.parse(str,res.getResource().getInputEncoding());	
						str=doc.text();
					}
					map.put("pageContent",str);
					map.put("mediaType", res.getResource().getMediaType());
					list.add(map);
				} 
			}
		}
		return list;
	}

	public List<Map<String,Object>> Process(String unzipPath)throws Exception{
		List<Map<String, Object>> result=null;
		try{
			unZip(unzipPath);
			EpubReader epubReader = new EpubReader(); 		
			Book book = epubReader.readEpub(new FileInputStream(_filePath)); 
			String opf=book.getOpfResource().getHref();
			if(opf.indexOf("/")!=-1){
				opf=opf.substring(0,opf.indexOf("/"));
			}
			opf=opf!=null && !"".equals(opf)?opf + "/":"";
			List<SpineReference> spineList= book.getSpine().getSpineReferences();
		 
			if(spineList!=null && spineList.size()>0){
				result=new ArrayList<Map<String,Object>>();
				for(int i=0;i<spineList.size();i++){ 
					SpineReference spine=spineList.get(i);
					String href=opf+spine.getResource().getHref();
					List<Map<String,Object>> pageInfo=pageProccess(unzipPath+href,i);
					if(pageInfo!=null && !pageInfo.isEmpty()){
						result.addAll(pageInfo);
					}
				} 
			}
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	
	//预处理(加tag，加密)
	private List<Map<String,Object>> pageProccess(String filePath,Integer pos)throws Exception{
		List<Map<String, Object>> result=null;
		try{
			File filename = new File(filePath);
			InputStreamReader reader = new InputStreamReader(new FileInputStream(filename)); 
			BufferedReader br = new BufferedReader(reader); 
			String line = "";
			StringBuilder sb=new StringBuilder();
			line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine(); // 一次读入一行数据
			}
			
			Document doc= Jsoup.parse(sb.toString());
			doc.body().attr("oncopy", "return false");
			//doc.body().attr("onselectstart", "return false");--貌似火狐不支持
//          如果有直接写在body标签下的文本信息，需要先把文本信息 放进div中
//			if(doc.body().textNodes()!=null && doc.body().textNodes().size()>0){
//				for(int i=0;i<doc.body().textNodes().size();i++){
//					doc.body().textNodes().get(i).text(Escape.escapeAll(doc.body().textNodes().get(i).text().trim()));
//				}
//			}
			//--------文本信息转码--------------
			if(doc.body().children()!=null && doc.body().children().size()>0){
				result=new ArrayList<Map<String,Object>>();
				for(int i=0;i<doc.body().children().size();i++){					
					Element ele=doc.body().children().get(i);
					String tag=pos+"#tag_"+i;
					String text=ele.text();
					ele.attr("itag", tag);//仅为body的第一级子级添加标签
					if(text!=null && !"".equals(text.trim())){
						Map<String,Object> map=new HashMap<String, Object>();
						map.put("tag", tag);
						map.put("text", ele.text());//取得转码前的文本内容。
						result.add(map);
					}
					eleProccess(ele);				
				}			
			}

			String html=doc.outerHtml();//文本信息转码后	
			Pattern p=Pattern.compile("<\\!--\\?\\s*?xml[^>]+\\?-->");//xhtml经过jsoup处理后头部的被注释成这样了
			Matcher m = p.matcher(html);						 //<!--?xml version="1.0" encoding="utf-8" standalone="no"?-->
			while(m.find()){					//这里替换回来
				String dest=m.group();
				String rep=dest.replace("<!--", "<").replace("-->", ">");
				html=html.replace(dest, rep);
			}
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(html.getBytes());
			fos.close();	
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	
	private void eleProccess(Element el){
			
		if(el.text()!=null && !"".equals(el.text().trim())){
			//System.out.println(el.text().trim());//段落连续文字内容
			if(el.textNodes()!=null && el.textNodes().size()>0){
				for(Integer j=0;j<el.textNodes().size();j++){
					if(el.textNodes().get(j)!=null && !"".equals(el.textNodes().get(j).text().trim())){
						el.textNodes().get(j).text(Escape.escapeAll(el.textNodes().get(j).text().trim()));				
					}
				}					
			}
			if(el.children()!=null && el.children().size()>0){
				for(int i=0;i<el.children().size();i++){
					eleProccess(el.children().get(i));
				}
			}
		}		
	}
//	public static String getNodeText(Node node){
//		String result="";
//	}
	
	public static void main(String[] args) throws Exception {	
		
		EPubHelper eph=new  EPubHelper("d:/9783527644612.epub");
		List<Map<String,Object>> list= eph.Process("D:/tomcat6/webapps/EPublishing/epubReaderDemo/9783527644612/");

	}
}
