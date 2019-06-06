package cn.digitalpublishing.util.web;

import java.util.Comparator;

import org.apache.solr.client.solrj.response.FacetField.Count;

public class ComparatorSubject implements Comparator<Object> {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s1 = "B4 start(13)";
		String s2 = "B2 end(110)";
		System.out.println("====>"+s1.compareTo(s2));

	}

	@Override
	public int compare(Object o1, Object o2) {
		Count count1 = (Count)o1;
		Count count2 = (Count)o2;
		int flag=count1.getName().compareTo(count2.getName());
		return flag;
	}

}
