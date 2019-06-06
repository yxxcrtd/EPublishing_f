package cn.digitalpublishing.util.xml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cn.digitalpublishing.util.io.FileUtil;

public class NLMParser {

	Document document =null;
	Map<String,Object> map=null;
	public NLMParser(String filepath) throws Exception{
		if(!FileUtil.isExist(filepath)){
			throw new Exception("文件不存在");
		}
		SAXReader saxReader = new SAXReader();
		saxReader.setEntityResolver(new EntityResolver () {  
		     public InputSource resolveEntity(String publicId, String systemId)  
		       throws SAXException, IOException {  
		            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='utf-8'?>".getBytes()));  
		     }  
		});  
		document = saxReader.read(filepath);
	}
	/**
	 * 获取单个节点的Text内容
	 * @param xpath
	 * @return
	 * @throws Exception
	 */
	public void getText(String key,String xpath)throws Exception{
		Node node=document.selectSingleNode(xpath);
		map.put(key, node.getText().trim());
	}
	public void getXML(String key,String xpath)throws Exception{
		Node node=document.selectSingleNode(xpath);
		map.put(key, node.asXML().trim());
	}
	public String getText(Node node,String xpath)throws Exception{
		Node n=node.selectSingleNode(xpath);
		return n.getText().trim();
	}
	/**
	 * 用于读取多条简单字符串集合
	 * @param key
	 * @param xpath
	 * @throws Exception
	 */
	public void getArr(String key,String xpath)throws Exception{
		List<String> list=null;
		List<Node> nodes=document.selectNodes(xpath);
		if(nodes!=null && nodes.size()>0){
			list=new ArrayList<String>();
			for(Node node:nodes ){
				list.add(node.getText().trim());
			}
		}
		map.put(key, list);
	}
	public void getAuthors()throws Exception{
		Map<String,Object> info=null;
		List<Node> affNode=document.selectNodes("//article-meta/aff/node()");			
		if(affNode!=null && affNode.size()>0){	
			info=new HashMap<String,Object>();
			String tempKey=null;
			for(int i=0;i<affNode.size();i++){
				String tempString=affNode.get(i).getStringValue().trim();
				if(tempString!=null && !"".equals(tempString)){
					if(tempKey==null){
						tempKey=tempString;
					}else{
						info.put(tempKey, tempString);
						tempKey=null;
					}
				}
			}
		}
		
		List<Map<String,Object>> authors=null;
		List<Node> nodes=document.selectNodes("//article-meta/contrib-group/contrib[@contrib-type=\"author\"]");
		if(nodes!=null && nodes.size()>0){
			
			authors=new ArrayList<Map<String,Object>>();
			Map<String,Object> m=null;
			for(Node node:nodes ){
				System.out.println(node.asXML());
				m=new HashMap<String,Object>();				
				m.put("surname",getText(node, "child::name/surname/text()"));
				m.put("given-names", getText(node, "child::name/given-names/text()"));
				m.put("role", getText(node, "child::role/text()"));
				m.put("xref", getText(node, "child::xref[@ref-type=\"aff\"]/sup/italic/text()"));
				if(info!=null && m.get("xref")!=null && !"".equals(m.get("xref"))){
					m.put("aff", info.get(m.get("xref"))); 
				}
				authors.add(m);
			}
		}
		map.put("authors", authors);		

	}
	public void Parse(){
		try {
			if(document==null){
				throw new Exception("XML文件读取错误");
			}
			map=new HashMap<String,Object>();		
			getText("publisher_short_name","//journal-id[@journal-id-type='publisher']/text()");
			getText("ISSN","//issn/text()");
			getText("article_id","//article-meta/article-id[@pub-id-type=\"other\"]/text()");
			getText("pmid","//article-meta/article-id[@pub-id-type=\"pmid\"]/text()");
			getText("subject","//article-categories/subj-group/subject/text()");
			getArr("subject_group","//article-categories/subj-group/subj-group/subject/text()");
			getText("article_title","//article-title/text()");
			getAuthors();
			getXML("notes","//author-notes/fn[@fn-type='con']/p");
			getText("correspondence","//author-notes/fn/p[fn:contains(text(),'Correspondence')]");
			getText("email","//author-notes/fn/p[fn:contains(text(),'Correspondence')]/email");
			getText("pubdate_day","//pub-date[@pub-type='pub']/day");
			getText("pubdate_month","//pub-date[@pub-type='pub']/month");
			getText("pubdate_year","//pub-date[@pub-type='pub']/year");
			getText("volume","//volume");
			getText("issue","//issue");
			getText("fpage","//fpage");
			getText("lpage","//lpage");
			getText("accepted_date_day","//history/date[@date-type='accepted']/day");
			getText("accepted_date_month","//history/date[@date-type='accepted']/month");
			getText("accepted_date_year","//history/date[@date-type='accepted']/year");
			getText("received_date_day","//history/date[@date-type='received']/day");
			getText("received_date_month","//history/date[@date-type='received']/month");
			getText("received_date_year","//history/date[@date-type='received']/year");
			getXML("abstract","//article-meta/abstract");
			getArr("sections","//body/sec/title");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	public Map<String, Object> getMap() {
		return map;
	}

	public static void main(String[] args) throws Exception {
//		NLMParser xh=new NLMParser("C:/Users/lfh/Desktop/bmj.xml");			
//		xh.Parse();
//		System.out.println(xh.getMap().get("publisher_short_name"));
//		System.out.println(xh.getMap().get("ISSN"));
//		System.out.println(xh.getMap().get("article_id"));
//		System.out.println(xh.getMap().get("pmid"));
//		System.out.println(xh.getMap().get("subject"));
//		printArr(xh.getMap().get("subject_group"));
//		System.out.println(xh.getMap().get("article_title"));
//		printAuthors(xh.getMap().get("authors"));
//		System.out.println(xh.getMap().get("notes"));
//		System.out.println(xh.getMap().get("correspondence"));
//		System.out.println(xh.getMap().get("email"));
		Calendar c = Calendar.getInstance();
		System.out.print(c.get(Calendar.MONTH));
	}
	
	static void printArr(Object arr){
		List<String> list=(List<String>)arr;
		if(list!=null){
			for(String s:list){
				System.out.println(s);
			}
		}
	}
	
	static void printAuthors(Object authorsObj){
		List<Map<String,Object>> authors=(List<Map<String,Object>>)authorsObj;
		for(Map<String,Object> m:authors){
			System.out.println(m.get("surname"));
			System.out.println(m.get("given-names"));
			System.out.println(m.get("role"));
			if(m.get("xref")!=null && !"".equals(m.get("xref"))){
				System.out.println(m.get("xref"));
				System.out.println(m.get("aff"));
			}
		}
	}
}