package cn.digitalpublishing.util.web;


import java.awt.Color;
import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class PdfHelper {
	public static void main(String[] args) throws Exception {  
		//copyPdf("L:/9786000000004.pdf","L:/9786000000004_part.pdf","1-2");
		textMark("清华大学  192.168.1.108","D:/tmp/9780000000019_110.pdf","D:/tmp/9780000000019_110_water.pdf",Color.GRAY,"STSong-Light","UniGB-UCS2-H",BaseFont.EMBEDDED);
		String path = "D:/metadata/9781613501306_water.pdf";
		System.out.println(path.substring(0, path.lastIndexOf("/")));
	}
	/**
	 * Generate a pdf with text watermark
	 * @param waterText Watermark content
	 * @param sourceFile sourceFile Path of original pdf file
	 * @param targetFile Path of new pdf file
	 * @param color Font color
	 * @param y y coordinate of the watermark
	 * @param x x coordinate of the watermark
	 * @param fontName Font name
	 * @param encoding Font encoding
	 * @param embedded Whether to be embedded
	 * @throws Exception
	 */
	public static void textMark(String watermark,String sourceFile ,String targetFile,Color color,String fontName,String encoding,boolean embedded)throws Exception{
		try{
			// 待加水印的文件  
			PdfReader reader = new PdfReader(sourceFile);
			// 加完水印的文件  
			PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(targetFile));  
			
			int total = reader.getNumberOfPages() + 1;  
			
			PdfContentByte content;  
			// 设置字体  
			BaseFont base = BaseFont.createFont(fontName,encoding,embedded);  
			// 水印文字 
			float high = 0;// 高度
			float width = 0;//宽度


			// 循环对每页插入水印  
			for (int i = 1; i < total; i++) {
				Document document = new Document(reader.getPageSize(i));
				// 水印的起始  
				high = document.getPageSize().getHeight()/2;
				width = document.getPageSize().getWidth()/2;
				content = stamper.getOverContent(i);
				PdfGState gs = new PdfGState();  
				gs.setFillOpacity(0.2f);//设置透明度为0.2  
				content.setGState(gs);  

				//content.addImage(jpeg);
				// 开始  
				content.beginText();  
				// 设置颜色  
				content.setColorFill(color);  
				// 设置字体及字号  
				content.setFontAndSize(base, 40);  
				content.showTextAligned(Element.ALIGN_CENTER, watermark, width, high, 35);//水印文字成35度角倾斜 
				content.endText();
				content.saveState();
			}  
			stamper.close();
		}catch(Exception e){
			throw e;
		}
	}

	/** 
	 * According to the original pdf file to create a new pdf file, the new pdf can be part of the original pdf file
	 * @param sourceFile Path of original pdf file 
	 * @param targetFile Path of new pdf file 
	 * @param ranges   A range of pages, example 1-20  
	 */ 
	public static void copyPdf(String sourceFile ,String targetFile, String ranges)throws Exception{
		try{
			PdfReader pdfReader = new PdfReader(sourceFile);
			PdfStamper pdfStamper = new PdfStamper(pdfReader , new FileOutputStream(targetFile)); 
			pdfReader.selectPages(ranges); 
			pdfStamper.close();
		}catch(Exception e){
			throw e;
		} 
	}
}
