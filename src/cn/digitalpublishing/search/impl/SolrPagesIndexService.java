package cn.digitalpublishing.search.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;

import cn.digitalpublishing.ep.po.PPage;
import cn.digitalpublishing.search.PagesIndexService;
import cn.digitalpublishing.util.CharUtil;

public class SolrPagesIndexService implements PagesIndexService {

	private SolrServer solrClient;
	/**
	 * @param solrClient
	 *            the solrClient to set
	 */
	public void setSolrClient(SolrServer solrClient) {
		this.solrClient = solrClient;
	}
	
	private Map<String,Object> searchIds(SolrQuery query) {
		Map<String,Object> resultList = new HashMap<String,Object>();
		SolrDocumentList list = null;
		List<Map<String,Object>> lm = new ArrayList<Map<String,Object>>();
		try {			
			QueryResponse response = solrClient.query(query);
			list = response.getResults();
			Map<String, Map<String, List<String>>> highLightMap =response.getHighlighting();
			if (list != null && list.size() > 0) {
				long count = list.getNumFound();
				resultList.put("count",count);
				for (SolrDocument solrDocument : list) {
					Map<String, Object> result = new HashMap<String, Object>(3);
					Object id=solrDocument.getFieldValue("id");
					result.put("id",id);
					result.put("pageNumber",solrDocument.getFieldValue("pageNumber"));
					result.put("hlMap", highLightMap.get(id).containsKey("fullText")?highLightMap.get(id).get("fullText"):highLightMap.get(id).get("text"));
					lm.add(result);
				}
			}
			resultList.put("result", lm);
		} catch (SolrServerException e) {
			//e.printStackTrace();
		}
		return resultList;
	}
	
	public int indexPages(PPage page) throws Exception {
		if (page == null) {
			throw new NullPointerException("Pages can't be null");
		}

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", page.getId());
		document.addField("sourceId",page.getPublications().getId());//原文ID
		document.addField("pageNumber", page.getNumber());//当前页
		document.addField("fullText", page.getFullText());//pdf全文

		int status = 500;

		try {
			solrClient.add(document);
			UpdateResponse response = solrClient.commit();
			status = response.getStatus();
		} catch (SolrServerException e) {
			solrClient.rollback();
			//e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			//e.printStackTrace();
		}

		return status;
	}

	public Map<String,Object> selectPagesByFullText(String sourceId,String fulltext,Integer page,Integer pageSize) throws Exception {
		if (StringUtils.isBlank(fulltext)) {
			throw new IllegalArgumentException("document fulltext can't be null");
		}
		Boolean isCn=CharUtil.isChinese(fulltext);
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer(isCn?"text:":"fullText:").append("\"").append(fulltext).append("\"");
		sb.append(" AND ");
		sb.append("sourceId:").append(sourceId);
		query.setStart(page*pageSize);
		query.setRows(pageSize);
		query.setSortField("pageNumber", ORDER.asc);
		query.setQuery(sb.toString());
		query.addField("id,pageNumber");
		query.setHighlight(true);
		query.addHighlightField(isCn?"text":"fullText");
		query.setHighlightSnippets(1);//仅取一处命中内容
		query.setHighlightFragsize(25);//取25个上下文内容字符
		query.setHighlightSimplePre("<font style='background-color:#FFFF00;' color='#336699'><b><i>");//标记，高亮关键字前缀
        query.setHighlightSimplePost("</i></b></font>");//后缀

		return searchIds(query);
	}

	public int deleteIndexById(String id) throws Exception {
		if (StringUtils.isBlank(id)) {
			throw new IllegalArgumentException("id can't be null or empty");
		}

		int status = 500;
		try {
			solrClient.deleteById(id);
			UpdateResponse response = solrClient.commit();
			status = response.getStatus();
		} catch (SolrServerException e) {
			solrClient.rollback();
			//e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			//e.printStackTrace();
		}
		return status;
	}

	public void clearAllIndex() throws Exception {
		try {
			solrClient.deleteByQuery("*:*");
			solrClient.commit();
		} catch (SolrServerException e) {
			solrClient.rollback();
			//e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			//e.printStackTrace();
		}
	}

	public void deleteIndexByCondition(String fieidName, String value)
			throws Exception {
		try {
			solrClient.deleteByQuery(fieidName+":"+value);
			solrClient.commit();
		} catch (SolrServerException e) {
			solrClient.rollback();
			//e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			//e.printStackTrace();
		}
	}

}
