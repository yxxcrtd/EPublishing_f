package cn.digitalpublishing.util.xml;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;

import org.w3c.dom.*;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import javax.xml.xpath.*;

 

public class XpathHelper {

	Document doc=null;
	XPathFactory factory=null;
	
	public XpathHelper(File filename)throws Exception{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    doc = builder.parse(filename);
	    factory=XPathFactory.newInstance();
	}
	
	public XpathHelper(String xmlString)throws Exception{
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    DocumentBuilder builder = domFactory.newDocumentBuilder();
	    doc=builder.parse( new InputSource( new StringReader( xmlString ) ) );
	    factory=XPathFactory.newInstance();
	}
	/**
	 * 根据Xpath读取单个字符串
	 * @param xpathString
	 * @return
	 * @throws XPathExpressionException
	 */
	public String getString(String xpathString) throws Exception{
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(xpathString);
		Object result = expr.evaluate(doc, XPathConstants.STRING);
		return (String)result;
	}
	public Node getNode(String xpathString) throws Exception{
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(xpathString);
		Object result = expr.evaluate(doc, XPathConstants.NODE);
		return (Node)result;
	}
	public NodeList getNodeList(String xpathString) throws Exception{
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(xpathString);
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		return (NodeList)result;
	}
	public Double getDouble(String xpathString) throws Exception{
		XPath xpath = factory.newXPath();
		XPathExpression expr = xpath.compile(xpathString);
		Object result = expr.evaluate(doc, XPathConstants.NUMBER);
		return (Double)result;
	}
	public Integer getInteger(String xpathString) throws Exception{
		Double d=getDouble(xpathString);
		return d.intValue();
	}
	
	 public static void main(String[] args) {
		try {
			XpathHelper xh=new  XpathHelper(new File("C:/Users/lfh/Desktop/test.xml"));			
			
			String node=xh.getString("//book");
	
			System.out.println(node);
		} catch (Exception e) {			
			e.printStackTrace();
		}
		 
	}
	 
//	 public static String toString(Node node) {   
//		 if (node == null) { 
//		    throw new IllegalArgumentException();   } 
//		   Transformer transformer = newTransformer();   if (transformer != null) {    try {
//			 StringWriter sw = new StringWriter();     transformer.transform(      new DOMSource(node),      new StreamResult(sw));     return sw.toString(); 
//			    } catch (TransformerException te) { 
//			     throw new RuntimeException(te.getMessage());    }   } 
//			   return errXMLString("不能生成XML信息！");  } 
//
//		 }

	 public static String toString(Node node) {
		  if (node == null) {
			  throw new IllegalArgumentException();
		  }
		  Transformer transformer = newTransformer();
		  if (transformer != null) {
		   try {
		    StringWriter sw = new StringWriter();
		    transformer.transform(
		     new DOMSource(node),
		     new StreamResult(sw));
		    return sw.toString();
		   } catch (TransformerException te) {
		    throw new RuntimeException(te.getMessage());
		   }
		  }
		  return ("不能生成XML信息！");
		 }

	 
	 public static Transformer newTransformer() {
		  try {
		   Transformer transformer =TransformerFactory.newInstance().newTransformer();
//		   Properties properties = transformer.getOutputProperties();
//		   properties.setProperty(OutputKeys.ENCODING, "gb2312");
//		   properties.setProperty(OutputKeys.METHOD, "xml");
//		   properties.setProperty(OutputKeys.VERSION, "1.0");
//		   properties.setProperty(OutputKeys.INDENT, "no");
//		   transformer.setOutputProperties(properties);
		   return transformer;
		  } catch (TransformerConfigurationException tce) {
		   throw new RuntimeException(tce.getMessage());
		  }
		 }

}