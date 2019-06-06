/**
 * 
 */
package cn.digitalpublishing.search.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.search.PublicationsIndexService;
import cn.digitalpublishing.search.util.PageUtil;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.CharUtil;

/**
 * @author Paul Zhang
 * 
 */
public class SolrPublicationsIndexService implements PublicationsIndexService {
	private SolrServer solrClient;
	private ServiceFactory serviceFactory;
	private String baseUrl = null;

	public SolrPublicationsIndexService() {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
	}

	/**
	 * @param solrClient
	 *            the solrClient to set
	 */
	public void setSolrClient(SolrServer solrClient) {
		this.solrClient = solrClient;
	}

	private Map<String, Object> searchIds(SolrQuery query, int pn, int size) {
		Map<String, Object> result = new HashMap<String, Object>(2);

		int start = PageUtil.getPageStart(pn, size);
		// if (start > 0) {
		// query.setStart(start);
		// }
		query.setStart(pn * size);
		query.setRows(size);
		query.setFields("id,score");// 仅返回ID字段,score得分
		SolrDocumentList list = null;
		List<String> ids = null;
		List<Map<String, String>> infos = null;

		try {
			query.setHighlight(true);
			query.setHighlightSimplePre("<font style='background-color:#FFFF00;' color='#336699'><b><i>");// 标记，高亮关键字前缀
			query.setHighlightSimplePost("</i></b></font>");// 后缀
			query.setHighlightFragsize(10000);// 每个分片的最大长度，默认为100

			if (query.getHighlightFields() == null || query.getHighlightFields().length == 0) {
				// query.setHighlight(true).setHighlightSnippets(1000);// 开启高亮组件
				query.addHighlightField("title");// 高亮字段
				query.addHighlightField("titleCn");// 高亮字段
				query.addHighlightField("isbn");// 高亮字段
				query.addHighlightField("author");// 高亮字段
				query.addHighlightField("authorCn");// 高亮字段
				query.addHighlightField("remark");// 高亮字段
				query.addHighlightField("remarkCn");// 高亮字段
				query.addHighlightField("copyPublisher");// 高亮字段
				query.addHighlightField("copyPublisherCn");// 高亮字段
			}
			// query.addHighlightField("fullText");// 高亮字段

			// query.setHighlight(true).setHighlightSnippets(1000); //结果分片数，默认为1

			QueryResponse response = solrClient.query(query);
			list = response.getResults();
			Map<String, Map<String, List<String>>> highlightmap = response.getHighlighting();
			List<FacetField> facetFields = response.getFacetFields();
			if (facetFields != null && facetFields.size() > 0) {
				result.put("facet", facetFields);
			}

			if (list != null && list.size() > 0) {
				long count = list.getNumFound();
				result.put("count", count);

				ids = new ArrayList<String>();
				infos = new ArrayList<Map<String, String>>();
				for (SolrDocument solrDocument : list) {
					ids.add((String) solrDocument.getFieldValue("id"));
					Map<String, String> map = new HashMap<String, String>();
					map.put("id", (String) solrDocument.getFieldValue("id"));
					map.put("isbn", highlightmap.get(solrDocument.getFieldValue("id")).get("isbn") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("isbn").get(0) : null);
					map.put("title", highlightmap.get(solrDocument.getFieldValue("id")).get("title") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("title").get(0) : null);
					if (map.get("title") == null || "".equals(map.get("title"))) {
						map.put("title", highlightmap.get(solrDocument.getFieldValue("id")).get("titleCn") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("titleCn").get(0) : null);
					}
					map.put("copyPublisher", highlightmap.get(solrDocument.getFieldValue("id")).get("copyPublisher") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("copyPublisher").get(0) : null);
					if (map.get("copyPublisher") == null || "".equals(map.get("copyPublisher"))) {
						map.put("copyPublisher", highlightmap.get(solrDocument.getFieldValue("id")).get("copyPublisherCn") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("copyPublisherCn").get(0) : null);
					}
					map.put("author", highlightmap.get(solrDocument.getFieldValue("id")).get("author") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("author").get(0) : null);
					if (map.get("author") == null || "".equals(map.get("author"))) {
						map.put("author", highlightmap.get(solrDocument.getFieldValue("id")).get("authorCn") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("authorCn").get(0) : null);
					}
					String remark = "";
					List<String> remarks = highlightmap.get(solrDocument.getFieldValue("id")).get("remark");
					if (remarks == null || remarks.size() <= 0) {
						remarks = highlightmap.get(solrDocument.getFieldValue("id")).get("remarkCn");
					}
					if (remarks != null && !remarks.isEmpty()) {
						for (int i = 0; i < remarks.size(); i++) {
							remark += (i + 1) + "." + remarks.get(i) + "...";
							if (i < remarks.size() - 1) {
								remark += "<br/>";
							}

						}
					}
					map.put("remark", !"".equals(remark) ? remark : null);
					if (solrDocument.containsKey("score")) {
						map.put("score", solrDocument.getFieldValue("score").toString());
					}
					infos.add(map);
				}

				result.put("result", infos);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;

	}

	public String buildKeywordStr(SolrInputDocument document) {
		StringBuffer buff = new StringBuffer();

		if (document.containsKey("keywords") && document.getField("keywords") != null && document.getField("keywords").toString().length() > 0) {
			buff.append(document.getFieldValue("keywords"));
			buff.append(",");// 分隔符
		}
		if (document.containsKey("publisher") && document.getField("publisher") != null && document.getField("publisher").toString().length() > 0) {
			buff.append(document.getFieldValue("publisher"));
			buff.append(",");// 分隔符
		}
		if (document.containsKey("taxonomy") && document.getField("taxonomy") != null && document.getField("taxonomy").toString().length() > 0) {
			buff.append(document.getFieldValue("taxonomy").toString().trim().replaceFirst("^[A-Z][^ ]* ", ""));// 去掉分类编号
			buff.append(",");// 分隔符
		}
		if (document.containsKey("taxonomyEn") && document.getField("taxonomyEn") != null && document.getField("taxonomyEn").toString().length() > 0) {
			buff.append(document.getFieldValue("taxonomyEn").toString().trim().replaceFirst("^[A-Z][^ ]* ", ""));// 去掉分类编号
			buff.append(",");// 分隔符
		}
		return buff.toString();
	}

	@Override
	public int indexPublications(PPublications publications) throws Exception {
		if (publications == null) {
			throw new NullPointerException("publications can't be null");
		}

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", publications.getId());
		document.addField("title", publications.getTitle());
		document.addField("chinesetitle", publications.getTitle());
		document.addField("author", publications.getAuthor() == null ? "" : publications.getAuthor());
		document.addField("isbn", publications.getCode());
		document.addField("remark", publications.getRemark() == null ? "" : publications.getRemark());
		if (publications.getPublisher() != null) {
			document.addField("publisher", publications.getPublisher().getName());
			document.addField("copyPublisher", publications.getPublisher().getName());
		} else {
			document.addField("publisher", "");
			document.addField("copyPublisher", "");
		}
		/**** 2014-03-17 by ruixue.cheng Start ****/
		if (publications.getSubTitle() != null && !"".equals(publications.getSubTitle().trim())) {
			document.addField("subtitle", publications.getSubTitle());
		}
		if (publications.getSeries() != null && !"".equals(publications.getSeries().trim())) {
			document.addField("series", publications.getSeries());
		}
		if (publications.getEdition() != null && !"".equals(publications.getEdition().trim())) {
			document.addField("edition", publications.getEdition());
		}
		/*
		 * if(publications.getPissn()!=null&&!"".equals(publications.getPissn().
		 * trim ())){//新加的 pissn 添加完sorl 字段之后解开屏蔽 document.addField("pissn",
		 * publications.getPissn()); }
		 */
		if (publications.getEissn() != null && !"".equals(publications.getEissn().trim())) {
			document.addField("eissn", publications.getEissn());
		}
		if (publications.getHisbn() != null && !"".equals(publications.getHisbn().trim())) {
			document.addField("hisbn", publications.getHisbn());
		}
		if (publications.getSisbn() != null && !"".equals(publications.getSisbn().trim())) {
			document.addField("pisbn", publications.getSisbn());
		}
		String keywords = publications.getKeywords();
		if (keywords != null && !"".equals(keywords.trim())) {

			String[] keywordArray = keywords.split(",");

			for (String keyword : keywordArray) {
				if (!"".equals(keyword.trim())) {
					document.addField("keywords", keyword.trim());
				}
			}
		}
		document.addField("publishDate", publications.getPubDate() == null ? "" : publications.getPubDate());
		String year = "";
		if (publications.getPubDate() != null) {
			year = publications.getPubDate().length() > 4 ? publications.getPubDate().substring(0, 4) : publications.getPubDate();
		} else if (publications.getYear() != null) {
			year = publications.getYear();
		} else {
			year = "";
		}
		document.addField("year", year);
		if (publications.getStartPage() != null) {
			document.addField("startpage", publications.getStartPage());
		}
		if (publications.getEndPage() != null) {
			document.addField("endpage", publications.getEndPage());
		}
		if (publications.getDoi() != null && !"".equals(publications.getDoi().trim())) {
			document.addField("doi", publications.getDoi());
		}
		if (publications.getVolumeCode() != null && !"".equals(publications.getVolumeCode().trim())) {
			document.addField("volume", publications.getVolumeCode());
		}
		if (publications.getIssueCode() != null && !"".equals(publications.getIssueCode().trim())) {
			document.addField("issue", publications.getIssueCode());
		}
		String languages = publications.getLang();
		if (languages != null && !"".equals(languages.trim())) {
			String[] languageArray = languages.split(",");
			for (String language : languageArray) {
				if (!"".equals(language.trim())) {
					document.addField("language", language);
				}
			}
		}
		/**** 2014-03-17 by ruixue.cheng End ****/
		document.addField("type", publications.getType());
		document.addField("createOn", publications.getCreateOn());
		document.addField("pubDate", publications.getPubDate() == null ? "" : publications.getPubDate());

		String pubSubject = publications.getPubSubject();
		if (pubSubject != null) {
			String repS1 = pubSubject.replace("][", ";");
			String repS2 = repS1.replace("[", "");
			String repS3 = repS2.replace("]", "");

			String[] taxonomyArray = repS3.split(";");

			for (String taxonomy : taxonomyArray) {
				document.addField("taxonomy", taxonomy.trim());
			}
		}

		String pubSubjectEn = publications.getPubSubjectEn();
		if (pubSubjectEn != null) {
			String repS1En = pubSubjectEn.replace("][", ";");
			String repS2En = repS1En.replace("[", "");
			String repS3En = repS2En.replace("]", "");

			String[] taxonomyEnArray = repS3En.split(";");

			for (String taxonomyEn : taxonomyEnArray) {
				document.addField("taxonomyEn", taxonomyEn.trim());
			}
		}

		if (publications.getFullText() != null && !"".equals(publications.getFullText().trim())) {
			document.addField("fullText", publications.getFullText());// pdf全文
		}
		document.addField("suggestion", this.buildKeywordStr(document));
		int status = 500;

		try {
			solrClient.add(document);
			UpdateResponse response = solrClient.commit();
			status = response.getStatus();
		} catch (SolrServerException e) {
			solrClient.rollback();
			// e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			// e.printStackTrace();
		} catch (Exception e) {
			solrClient.rollback();
		}

		return status;
	}

	public Map<String, Object> searchByTitle(String title, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(title)) {
			throw new IllegalArgumentException("document title can't be null");
		}
		Boolean isCn = CharUtil.isChinese(title);
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		title = myEscapeQueryChars(title);

		/*
		 * if((queryParam.get("taxonomy")!=null&&!"".equals(queryParam.get(
		 * "taxonomy"
		 * )))||(queryParam.get("taxonomyEn")!=null&&!"".equals(queryParam
		 * .get("taxonomyEn")))){ sb.append(isCn?"(titleCn:":"(title:");
		 * sb.append(title); sb.append(")"); }else{
		 */
		sb.append("(");
		// sb.append(isCn?"titleCn:":"title:");
		sb.append("titleCn:");
		sb.append(title);
		sb.append(" OR ");
		sb.append("title:");
		sb.append(title);
		/*
		 * sb.append(" OR "); sb.append(isCn?"text:":"fullText:");
		 * sb.append(title);
		 */
		sb.append(")");
		/* } */
		query.addHighlightField("title");
		query.addHighlightField("titleCn");
		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			sb.append(" AND taxonomy:");
			sb.append(queryParam.get("taxonomy"));
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			sb.append(" AND taxonomyEn:");
			sb.append(queryParam.get("taxonomyEn"));
		}

		query.setQuery(sb.toString());

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}
		if ((queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) || (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn")))) {
			queryParam.put("taxonomy", null);
			queryParam.put("taxonomyEn", null);
		}
		setFacetQuery(query, queryParam);
		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchByAuthor(String author, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(author)) {
			throw new IllegalArgumentException("document author can't be null");
		}
		Boolean isCn = CharUtil.isChinese(author);
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		author = myEscapeQueryChars(author);
		/*
		 * if((queryParam.get("taxonomy")!=null&&!"".equals(queryParam.get(
		 * "taxonomy"
		 * )))||(queryParam.get("taxonomyEn")!=null&&!"".equals(queryParam
		 * .get("taxonomyEn")))){ sb.append(isCn?"(authorCn:":"(author:");
		 * sb.append(author); sb.append(")"); }else{
		 */
		sb.append("(");
		// sb.append(isCn?"authorCn:":"author:");
		sb.append("authorCn:");
		sb.append(author);
		sb.append(" OR ");
		sb.append("author:");
		sb.append(author);
		/*
		 * sb.append(" OR "); sb.append(isCn?"text:":"fullText:");
		 * sb.append(author);
		 */
		sb.append(")");
		query.addHighlightField("author");
		query.addHighlightField("authorCn");
		/* } */
		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			sb.append(" AND taxonomy:");
			sb.append(queryParam.get("taxonomy"));
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			sb.append(" AND taxonomyEn:");
			sb.append(queryParam.get("taxonomyEn"));
		}
		query.setQuery(sb.toString());

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}
		if ((queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) || (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn")))) {
			queryParam.put("taxonomy", null);
			queryParam.put("taxonomyEn", null);
		}
		setFacetQuery(query, queryParam);
		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchByISBN(String ISBN, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(ISBN)) {
			throw new IllegalArgumentException("document ISBN can't be null");
		}

		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		ISBN = myEscapeQueryChars(ISBN);
		/*
		 * if((queryParam.get("taxonomy")!=null&&!"".equals(queryParam.get(
		 * "taxonomy"
		 * )))||(queryParam.get("taxonomyEn")!=null&&!"".equals(queryParam
		 * .get("taxonomyEn")))){ sb.append("(isbn:"); sb.append(ISBN);
		 * sb.append(" OR "); sb.append("pisbn:"); sb.append(ISBN); sb.append(
		 * " OR "); sb.append("hisbn:"); sb.append(ISBN); sb.append(" OR ");
		 * sb.append("eissn:"); sb.append(ISBN); sb.append(")"); }else{
		 */
		sb.append("(");
		sb.append("isbn:");
		sb.append(ISBN);
		sb.append(" OR ");
		sb.append("pisbn:");
		sb.append(ISBN);
		sb.append(" OR ");
		sb.append("hisbn:");
		sb.append(ISBN);
		sb.append(" OR ");
		sb.append("eissn:");
		sb.append(ISBN);
		sb.append(")");
		query.addHighlightField("isbn");
		query.addHighlightField("pisbn");
		query.addHighlightField("hisbn");
		query.addHighlightField("eissn");
		/* } */
		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			sb.append(" AND taxonomy:");
			sb.append(queryParam.get("taxonomy"));
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			sb.append(" AND taxonomyEn:");
			sb.append(queryParam.get("taxonomyEn"));
		}

		query.setQuery(sb.toString());

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}
		if ((queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) || (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn")))) {
			queryParam.put("taxonomy", null);
			queryParam.put("taxonomyEn", null);
		}
		setFacetQuery(query, queryParam);
		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchByPublisher(String publisher, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(publisher)) {
			throw new IllegalArgumentException("document publisher name can't be null");
		}
		Boolean isCn = CharUtil.isChinese(publisher);
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		publisher = myEscapeQueryChars(publisher);

		/*
		 * if((queryParam.get("taxonomy")!=null&&!"".equals(queryParam.get(
		 * "taxonomy"
		 * )))||(queryParam.get("taxonomyEn")!=null&&!"".equals(queryParam
		 * .get("taxonomyEn")))){
		 * sb.append(isCn?"(copyPublisherCn:":"(copyPublisher:");
		 * sb.append(publisher); sb.append(")"); }else{
		 */
		sb.append("(");
		// sb.append(isCn?"copyPublisherCn:":"copyPublisher:");
		sb.append("copyPublisher:");
		sb.append(publisher);
		sb.append(" OR ");
		sb.append("copyPublisherCn:");
		sb.append(publisher);
		/*
		 * sb.append(" OR "); sb.append(isCn?"text:":"fullText:");
		 * sb.append(publisher);
		 */
		sb.append(")");
		/* } */
		query.addHighlightField("copyPublisher");
		query.addHighlightField("copyPublisherCn");
		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			sb.append(" AND taxonomy:");
			sb.append(queryParam.get("taxonomy"));
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			sb.append(" AND taxonomyEn:");
			sb.append(queryParam.get("taxonomyEn"));
		}

		query.setQuery(sb.toString());

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}

		if ((queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) || (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn")))) {
			queryParam.put("taxonomy", null);
			queryParam.put("taxonomyEn", null);
		}
		setFacetQuery(query, queryParam);
		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchByAllFullText(String keywords, int pn, int size, Map<String, String> queryParam, String order) throws Exception {

		if (StringUtils.isBlank(keywords)) {
			throw new IllegalArgumentException("keywords can't be null");
		}
		Boolean isCn = CharUtil.isChinese(keywords);
		keywords = myEscapeQueryChars(keywords);
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		/*
		 * if((queryParam.get("taxonomy")!=null&&!"".equals(queryParam.get(
		 * "taxonomy"
		 * )))||(queryParam.get("taxonomyEn")!=null&&!"".equals(queryParam
		 * .get("taxonomyEn")))){
		 */
		// sb.append(isCn?"(titleCn:":"(title:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(isCn?"remarkCn:":"remark:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(isCn?"authorCn:":"author:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(isCn?"copyPublisher:":"copyPublisher:");

		sb.append("(titleCn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("title:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("remarkCn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("remark:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("authorCn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("author:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("copyPublisher:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("copyPublisherCn:");

		sb.append(keywords);
		sb.append(" OR ");
		sb.append("isbn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("pisbn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("hisbn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append(isCn ? "text:" : "fullText:");// 全文内容中没有进行拼音索引，保持原逻辑
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("eissn:");
		sb.append(keywords);
		sb.append(")");

		/*
		 * }else{ // StringBuffer sb = new
		 * StringBuffer(isCn?"titleCn:":"title:");
		 * sb.append(isCn?"titleCn:":"title:"); sb.append(keywords); sb.append(
		 * " "); sb.append(isCn?"remarkCn:":"remark:"); sb.append(keywords);
		 * sb.append(" "); sb.append(isCn?"authorCn:":"author:");
		 * sb.append(keywords); sb.append(" ");
		 * sb.append(isCn?"copyPublisherCn:":"copyPublisher:");
		 * sb.append(keywords); sb.append(" "); sb.append("isbn:");
		 * sb.append(keywords); sb.append(" "); sb.append("pisbn:");
		 * sb.append(keywords); sb.append(" "); sb.append("hisbn:");
		 * sb.append(keywords); sb.append(" "); sb.append("eissn:");
		 * sb.append(keywords);
		 * 
		 * 
		 * 
		 * }
		 */

		/*** yangheqing 2014-05-27 ***/
		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			sb.append(" AND taxonomy:");
			sb.append(queryParam.get("taxonomy"));
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			sb.append(" AND taxonomyEn:");
			sb.append(queryParam.get("taxonomyEn"));
		}

		// 加入<1>非本地资源 <2>本地资源 条件
		/*
		 * if (null != queryParam.get("local") &&
		 * !"".equals(queryParam.get("local"))) { sb.append(" AND local:");
		 * sb.append(queryParam.get("local")); }
		 */

		if (queryParam.get("nochinese") != null && !"".equals(queryParam.get("nochinese"))) {
			sb.append(" AND nochinese:" + queryParam.get("nochinese"));
		}

		/*
		 * // 非语言查询 if (null != queryParam.get("notLanguage") &&
		 * !"".equals(queryParam.get("notLanguage"))) { sb.append(" AND " +
		 * queryParam.get("notLanguage")); }
		 */

		query.setQuery(sb.toString());
		query.setRequestHandler("/full");

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}
		// queryParam 的 中英文分类 回写 null ,不进行setFacetQuery 方法
		if ((queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) || (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn")))) {
			queryParam.put("taxonomy", null);
			queryParam.put("taxonomyEn", null);
		}

		setFacetQuery(query, queryParam);
		return searchIds(query, pn, size);
	}

	@Override
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
			System.out.println("#############SolrDeleteFailPrompt SolrServerException##############");
			e.printStackTrace();
			throw e;
		} catch (IOException e) {
			solrClient.rollback();
			System.out.println("#############SolrDeleteFailPrompt IOException##############");
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			throw e;
		}

		return status;
	}

	@Override
	public void clearAllIndex() throws Exception {
		try {
			solrClient.deleteByQuery("*:*");
			solrClient.commit();
		} catch (SolrServerException e) {
			solrClient.rollback();
			e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			e.printStackTrace();
		}
	}

	private SolrQuery setFacetQuery(SolrQuery query, Map<String, String> queryParam) {
		query.setFacet(true);
		// query.setFacetSort("pubDate");
		// query.setFacetSort(true);//分组是否排序
		query.setFacetLimit(-1);
		query.addFacetField("type", "publisher", "pubDate", "taxonomy", "taxonomyEn", "language");
		if (queryParam != null) {
			if (queryParam.get("type") != null && !"".equals(queryParam.get("type")) && Integer.parseInt(queryParam.get("type").toString()) == 2) {
				query.addFilterQuery("{!tag=type}type:" + queryParam.get("type") + " OR type:7");
			} else if (queryParam.get("type") != null && !"".equals(queryParam.get("type"))) {
				query.addFilterQuery("{!tag=type}type:" + queryParam.get("type"));
			}
			if (queryParam.get("publisher") != null && !"".equals(queryParam.get("publisher"))) {
				query.addFilterQuery("{!tag=publisher}publisher:" + queryParam.get("publisher"));
			}
			if (queryParam.get("pubDate") != null && !"".equals(queryParam.get("pubDate"))) {
				query.addFilterQuery("{!tag=pubDate}pubDate:" + queryParam.get("pubDate"));
			}
			if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
				query.addFilterQuery("{!tag=taxonomy}taxonomy:" + queryParam.get("taxonomy") + "*");

			}
			if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
				query.addFilterQuery("{!tag=taxonomyEn}taxonomyEn:" + queryParam.get("taxonomyEn"));
			}
			if (queryParam.get("language") != null && !"".equals(queryParam.get("language"))) {
				query.addFilterQuery("{!tag=language}language:" + queryParam.get("language"));
			}
			if (queryParam.get("fullText") != null && !"".equals(queryParam.get("fullText"))) {
				query.addFilterQuery("{!tag=fullText}fullText:" + queryParam.get("fullText"));
			}
			if (queryParam.get("local") != null && !"".equals(queryParam.get("local"))) {
				query.addFilterQuery("local:" + queryParam.get("local"));
			}
			if (queryParam.get("notLanguage") != null && !"".equals(queryParam.get("notLanguage"))) {
				query.addFilterQuery(queryParam.get("notLanguage"));
			}
		}
		return query;
	}

	public Map<String, Object> advancedSearch(int curpage, int pageCount, Map<String, String> param, String order) throws Exception {
		return advancedSearch(curpage, pageCount, param, order, null);
	}

	@Override
	public Map<String, Object> advancedSearch(int curpage, int pageCount, Map<String, String> param, String order, String isChinese) throws Exception {
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();

		int flag = 0;

		String keywords = param.get("searchValue") == null ? "" : param.get("searchValue");
		keywords = ClientUtils.escapeQueryChars(keywords);
		if ("1".equals(param.get("keywordCondition"))) {
			keywords = "\"" + keywords + "\"";
		} /*
			 * else if("2".equals(param.get("keywordCondition"))){
			 * keywords=param.get("searchValue"); }
			 */
		keywords = "(" + keywords + ")";
		// 关键字
		if (param.get("searchValue") != null && !"".equals(param.get("searchValue"))) {
			Boolean isCn = CharUtil.isChinese(keywords);

			sb.append(isCn ? " (titleCn:" : " (title:");
			sb.append(keywords);
			sb.append(" ");
			sb.append(isCn ? "remarkCn:" : "remark:");
			sb.append(keywords);
			sb.append(" ");
			sb.append(isCn ? "authorCn:" : "author:");
			sb.append(keywords);
			sb.append(" ");
			sb.append(isCn ? "copyPublisherCn:" : "copyPublisher:");
			sb.append(keywords);
			sb.append(" ");
			sb.append("isbn:");
			sb.append(keywords);
			sb.append(" ");
			sb.append("pisbn:");
			sb.append(keywords);
			sb.append(" ");
			sb.append("hisbn:");
			sb.append(keywords);
			sb.append(" ");
			sb.append("eissn:");
			sb.append(keywords);
			sb.append(")");
			flag = 1;

			// 不包含关键字
			if (param.get("notKeywords") != null && !"".equals(param.get("notKeywords"))) {
				sb.append(" NOT remark:");
				sb.append("\"" + param.get("notKeywords") + "\"");
			}
		}
		if (isChinese != null && !"".equals(isChinese)) {
			String n = isChinese.equals("true") ? "" : " NOT ";
			String a = flag == 0 ? "" : " AND ";
			sb.append(a + n + " (language:chs OR language:CHS OR language:chn OR language:CHN OR language:CHT OR language:cht) ");
			flag = 1;
		}
		// 标题
		if (param.get("title") != null && !"".equals(param.get("title"))) {
			Boolean isCn = CharUtil.isChinese(param.get("title"));
			if (flag == 0) {
				sb.append(isCn ? " titleCn:" : " title:");
				flag = 1;
			} else {
				sb.append(isCn ? " AND titleCn:" : " AND title:");
			}
			sb.append(param.get("title"));
			query.addHighlightField("title");
			query.addHighlightField("titleCn");
		}
		// 作者
		if (param.get("author") != null && !"".equals(param.get("author"))) {
			Boolean isCn = CharUtil.isChinese(param.get("author"));
			if (flag == 0) {
				sb.append(isCn ? " authorCn:" : " author:");
				flag = 1;
			} else {
				sb.append(isCn ? " AND authorCn:" : " AND author:");
			}
			sb.append(param.get("author"));
			query.addHighlightField("author");
			query.addHighlightField("authorCn");
		}
		// ISBN/ISSN
		if (param.get("code") != null && !"".equals(param.get("code"))) {
			if (flag == 0) {
				flag = 1;
			} else {
				sb.append(" AND");
			}
			sb.append(" (");
			sb.append("isbn:");
			sb.append(param.get("code"));
			sb.append(" ");
			sb.append("pisbn:");
			sb.append(param.get("code"));
			sb.append(" ");
			sb.append("hisbn:");
			sb.append(param.get("code"));
			sb.append(" ");
			sb.append("eissn:");
			sb.append(param.get("code"));
			sb.append(") ");
			query.addHighlightField("isbn");
		}
		// 分类
		if (param.get("taxonomy") != null && !"".equals(param.get("taxonomy"))) {
			String taxStr = param.get("taxonomy").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			for (int j = 0; j < taxArr.length; j++) {
				if (flag == 0) {
					sb.append(" taxonomy:");
					flag = 1;
				} else {
					sb.append(" AND taxonomy:");
				}
				sb.append("(" + taxArr[j].split(" ")[0] + "*)");
			}
		}
		// 英文分类
		if (param.get("taxonomyEn") != null && !"".equals(param.get("taxonomyEn"))) {
			String taxStr = param.get("taxonomyEn").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			for (int j = 0; j < taxArr.length; j++) {
				if (flag == 0) {
					sb.append(" taxonomyEn:");
					flag = 1;
				} else {
					sb.append(" AND taxonomyEn:");
				}
				sb.append("(" + taxArr[j].split(" ")[0] + "*)");
			}

		}
		// // 类型
		// if (param.get("pubType") != null && !"".equals(param.get("pubType")))
		// {
		// sb.append("(");
		// if (flag == 0) {
		// sb.append("type:");
		// flag = 1;
		// } else {
		// sb.append(" AND type:");
		// }
		// sb.append(param.get("pubType") + " OR type:7");
		// query.addHighlightField("type");
		// sb.append(")");
		// } else if (param.get("pubType") != null &&
		// !"".equals(param.get("pubType"))) {
		// if (flag == 0) {
		// sb.append("type:");
		// flag = 1;
		// } else {
		// sb.append(" AND type:");
		// }
		// sb.append(param.get("pubType"));
		// query.addHighlightField("type");
		// }

		// if (param.get("pubType") != null && !"".equals(param.get("pubType")))
		// {
		// param.put("type", param.get("pubType"));
		// }
		// 类型
		if (param.get("pubType") != null && !"".equals(param.get("pubType"))) {
			if (flag == 0) {
				sb.append("type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("pubType"));
		}

		// 出版年份 开始时间不为空
		if ((param.get("pubDateStart") != null && !"".equals(param.get("pubDateStart"))) || (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd")))) {
			if (flag == 0) {
				sb.append("pubDate:");
				flag = 1;
			} else {
				sb.append(" AND pubDate:");
			}
			sb.append("[");
			if (param.get("pubDateStart") != null & !"".equals(param.get("pubDateStart"))) {
				sb.append(param.get("pubDateStart") + "0101");
			} else {
				sb.append("17900101");
			}
			sb.append(" TO ");
			if (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd"))) {
				sb.append(param.get("pubDateEnd") + "1231");
			} else {
				sb.append("99991231");
			}
			sb.append("]");
			query.addHighlightField("pubDate");
		}
		// 出版社
		if (param.get("publisher") != null && !"".equals(param.get("publisher"))) {

			String publisher = param.get("publisher");
			if (flag == 0) {
				flag = 1;
			} else {
				sb.append(" AND ");
			}
			// 左侧链接点击的出版社
			if (publisher.startsWith("_")) {
				sb.append("publisher:" + "\"" + publisher.substring(1) + "\"");
			} else {
				Boolean isCn = CharUtil.isChinese(publisher);
				sb.append(isCn ? "copyPublisherCn:" : "copyPublisher:");
				sb.append("\"" + publisher + "\"");
			}

			query.addHighlightField("publisher");
			query.addHighlightField("copyPublisher");
			query.addHighlightField("copyPublisherCn");
		}
		// 出版时间
		if (param.get("pubDate") != null && !"".equals(param.get("pubDate"))) {
			if (flag == 0) {
				sb.append("pubDate:");
				flag = 1;
			} else {
				sb.append(" AND pubDate:");
			}
			sb.append(param.get("pubDate"));
		}
		// 类型
		/*
		 * if(param.get("type")!=null&&!"".equals(param.get("type"))){
		 * if(flag==0){ sb.append("type:"); flag = 1; }else{ sb.append(
		 * " AND type:"); } sb.append(param.get("type")); }
		 */
		if (param.get("type") != null && !"".equals(param.get("type")) && Integer.parseInt(param.get("type")) == 2) {// 这个是查，如果是期刊的类型，我们就把期刊和期要同时查询
			if (flag == 0) {
				sb.append("type:");
				flag = 1;
			} else {
				sb.append(" AND ( type:");
			}
			sb.append(param.get("type") + " OR type:7");
			sb.append(")");
		} else if (param.get("type") != null && !"".equals(param.get("type"))) {
			if (flag == 0) {
				sb.append("type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("type"));
		}
		// 语种
		if (param.get("language") != null && !"".equals(param.get("language"))) {
			if (flag == 0) {
				sb.append("language:");
				flag = 1;
			} else {
				sb.append(" AND language:");
			}
			sb.append(param.get("language"));
		}
		// 首字母
		if (param.get("prefixWord") != null && !"".equals(param.get("prefixWord"))) {
			if (flag == 0) {
				sb.append("prefixWord:");
				flag = 1;
			} else {
				sb.append(" AND prefixWord:");
			}
			sb.append(param.get("prefixWord"));
			sb.append(" AND ( type:1 OR type:2)");
		}

		// 加入<1>非本地资源 <2>本地资源 条件
		if (null != param.get("local") && !"".equals(param.get("local"))) {
			if (flag == 0) {
				sb.append("local:");
				flag = 1;
			} else {
				sb.append(" AND local:");
			}
			sb.append(param.get("local"));
		}

		// 非语言查询
		if (null != param.get("notLanguage") && !"".equals(param.get("notLanguage"))) {
			if (flag == 0) {
				sb.append(" " + param.get("notLanguage"));
				flag = 1;
			} else {
				sb.append(" AND " + param.get("notLanguage"));
			}
		}

		// 排序
		// if (!StringUtils.isBlank(order)) {
		// query.setSortField("createOn", ORDER.valueOf(order));
		// }
		/** 没有分类法查询所有分类 yangheqing 2014-05-27 **/
		if ("".equals(sb.toString().trim())) {
			sb.append("*:*");
		}

		query.setQuery(sb.toString());
		query.setRequestHandler("/full");

		if (!StringUtils.isBlank(order)) {
			query.setSortField("pubDate", ORDER.valueOf(order));
		}

		if (null != param.get("sortFlag") && !"".equals(param.get("sortFlag"))) {
			if (!"desc".equals(param.get("sortFlag")) && !"asc".equals(param.get("sortFlag"))) {
				query.setSortField("createOn", ORDER.valueOf("desc"));
			} else {
				query.setSortField("createOn", ORDER.valueOf(param.get("sortFlag")));
			}
		}

		/*
		 * if (!StringUtils.isBlank(order)) { query.setSortField("pubDate",
		 * ORDER.valueOf(order)); }
		 */

		setFacetQuery(query, null);
		return searchIds(query, curpage, pageCount);
	}

	@Override
	public String[] IKKewword(String field, String keyword) throws Exception {
		String[] result = null;
		// 构建IK分词器，使用smart分词模式
		Analyzer analyzer = new IKAnalyzer(true);
		// 获取Lucene的TokenStream对象
		org.apache.lucene.analysis.TokenStream ts = null;
		try {
			ts = analyzer.tokenStream(field, new StringReader(keyword));
			// 获取词元位置属性
			// OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 获取词元文本属性
			// TypeAttribute type = ts.addAttribute(TypeAttribute.class);

			// 重置TokenStream（重置StringReader）
			ts.reset();
			StringBuffer sb = new StringBuffer();
			// 迭代获取分词结果
			while (ts.incrementToken()) {
				sb.append(term.toString() + ";");
				// System.out.println(offset.startOffset() + " - " +
				// offset.endOffset() + " : " + term.toString() + " | " +
				// type.type());
			}
			// 关闭TokenStream（关闭StringReader）
			ts.end(); // Perform end-of-stream operations, e.g. set the final
						// offset.
			result = sb.toString().split(";");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 释放TokenStream的所有资源
			if (ts != null) {
				try {
					ts.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public Map<String, Object> searchNewPubs(int pn, int size) throws Exception {

		SolrQuery query = new SolrQuery();

		query.setQuery("type:1 OR type:2");
		query.setRequestHandler("/full");

		query.setSortField("createOn", ORDER.valueOf("desc"));

		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchByQueryString(String queryString, int pn, int size, Map<String, String> queryParam, String order) throws Exception {

		SolrQuery query = new SolrQuery();

		query.setQuery(queryString);
		query.setRequestHandler("/full");

		setFacetQuery(query, queryParam);

		return searchIds(query, pn, size);
	}

	/**
	 * 当关键词外面有双引号时，先去掉引号，转义处理后再重新加上双引号， 否则直接转义
	 * 
	 * @param keywords
	 * @return
	 */
	private String myEscapeQueryChars(String keywords) {
		String kw = keywords;
		if (kw.startsWith("\"") && kw.endsWith("\"")) {
			kw = kw.replaceAll("^\\\"|\\\"$", "");
			kw = ClientUtils.escapeQueryChars(kw);
			kw = "\"" + kw + "\"";
		} else {
			kw = ClientUtils.escapeQueryChars(kw);
		}
		return kw;
	}

	@Override
	public Map<String, Object> searchNewBooks(int pn, int size) throws Exception {

		SolrQuery query = new SolrQuery();

		query.setQuery("type:1 AND language:chs");
		query.setRequestHandler("/full");

		query.setSortField("createOn", ORDER.valueOf("desc"));

		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchNewJournalCn(int pn, int size) throws Exception {

		SolrQuery query = new SolrQuery();

		query.setQuery("type:2 AND language:chs");
		query.setRequestHandler("/full");

		query.setSortField("createOn", ORDER.valueOf("desc"));

		return searchIds(query, pn, size);
	}

	@Override
	public Map<String, Object> searchNewBooksEn(int pn, int size) throws Exception {

		SolrQuery query = new SolrQuery();

		query.setQuery("type:1 AND language:eng");
		query.setRequestHandler("/full");

		query.setSortField("createOn", ORDER.valueOf("desc"));

		return searchIds(query, pn, size);
	}

	@Override
	public String suggest(String q) throws Exception {
		HttpClient client = new HttpClient();
		if (baseUrl == null) {
			HttpSolrServer s = (HttpSolrServer) solrClient;
			baseUrl = s.getBaseURL();
		}
		GetMethod gm = new GetMethod(baseUrl + "/suggest?wt=json&q=" + q);
		int i = client.executeMethod(gm);
		if (i == 200) {
			InputStream ins = gm.getResponseBodyAsStream();
			StringBuffer sb = new StringBuffer();
			byte[] b = new byte[1024];
			int r_len = 0;
			while ((r_len = ins.read(b)) > 0) {
				sb.append(new String(b, 0, r_len, gm.getResponseCharSet()));
			}
			return sb.toString();
		}
		return "{}";
	}
}
