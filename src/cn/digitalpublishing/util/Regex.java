package cn.digitalpublishing.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {
	public static void main(String[] args) throws Exception {
		String s=Regex.replace("<sd-a>dsfsdf</d>sdfs0",Regex.HTML_TAG, "");
		System.out.println(s);
	}
	/**
	 * 按逗号，分号；回车\r 换行\n 切割字符串
	 */
	public static final String SPLIT_BY_SYMBOL="\\s*(\\r+|\\n+|;+|,+)+\\s*";
	/**
	 * 判断是否年份
	 */
	public static final String IS_DATE="^\\d{4}(\\d{2}){0,2}$";
	/**
	 * 匹配任意HTML标签
	 */
	public static final String HTML_TAG="(?i)</?[a-z][a-z0-9\\-]*[^<>]*>";
	public static String getSingleStr(String regex,String input)throws Exception{
		Pattern p=Pattern.compile(regex);
		Matcher m= p.matcher(input);
		if(m.find()){
			return m.group();
		}
		return null;
	}
	
	public static boolean isMatch(String str,String regex)throws Exception{
		boolean result=false;
		try{
			result=Pattern.matches(regex, str);
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * 拆分字符串
	 * @param str 待拆分的字符串内容
	 * @param regex 正则表达式
	 * @return
	 * @throws Exception
	 */
	public static String[] split(String str,String regex)throws Exception{
		String[]  result=null;
		try{
			Pattern p = Pattern.compile(regex);
			result=p.split(str);
		}catch(Exception e){
			throw e;
		}
		return result;
	}
	/**
	 * 拆分字符串(拆分结果中去除首尾的空白字符，并忽略掉纯空白字符的内容)
	 * @param str 待拆分的字符串内容
	 * @param regex 正则表达式
	 * @param trimResult
	 * @return
	 * @throws Exception
	 */
	public static String[] split(String str,String regex,Boolean trimResult)throws Exception{
		if(trimResult){
			String[] result=split(str,regex);
			List<String> list=new ArrayList<String>();
			for(String r:result){
				r=r.trim();
				if(!"".equals(r)){
					list.add(r);
				}
			}
			return list.toArray(new String[0]);
		}else{
			return split(str,regex);
		}
	}
	/**
	 * 替换字符串
	 * @param input 原内容
	 * @param regex 表达式
	 * @param replacement 替换的内容
	 * @return
	 * @throws Exception
	 */
	public static String replace(String input,String regex,String replacement)throws Exception{
		Pattern p=Pattern.compile(regex);
		Matcher m= p.matcher(input);
		if(m.find()){
			return m.replaceAll(replacement);
		}
		return input;
	}
}
