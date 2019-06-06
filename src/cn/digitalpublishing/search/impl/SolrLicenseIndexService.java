package cn.digitalpublishing.search.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.analysis.tokenattributes.TypeAttribute;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.client.solrj.util.ClientUtils;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.wltea.analyzer.lucene.IKAnalyzer;

import cn.com.daxtech.framework.model.Param;
import cn.digitalpublishing.ep.po.BPublisher;
import cn.digitalpublishing.ep.po.LLicense;
import cn.digitalpublishing.search.LicenseIndexService;
import cn.digitalpublishing.search.util.PageUtil;
import cn.digitalpublishing.service.factory.ServiceFactory;
import cn.digitalpublishing.service.factory.impl.ServiceFactoryImpl;
import cn.digitalpublishing.util.CharUtil;

public class SolrLicenseIndexService implements LicenseIndexService {
	private ServiceFactory serviceFactory;

	public SolrLicenseIndexService() {
		this.serviceFactory = (ServiceFactory) new ServiceFactoryImpl();
	}

	private SolrServer solrClient;

	public SolrServer getSolrClient() {
		return solrClient;
	}

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
		query.setFields("id,score");// 仅返回ID字段,得分
		SolrDocumentList list = null;
		List<String> ids = null;
		List<Map<String, String>> infos = null;

		try {
			query.setHighlight(true);
			query.setHighlightSimplePre("<font style='background-color:#FFFF00;' color='#336699'><b><i>");// 标记，高亮关键字前缀
			query.setHighlightSimplePost("</i></b></font>");// 后缀
			query.setHighlightFragsize(10000);// 每个分片的最大长度，默认为100
			// query.setHighlight(true).setHighlightSnippets(1000); //结果分片数，默认为1
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
					map.put("publisher", highlightmap.get(solrDocument.getFieldValue("id")).get("publisher") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("publisher").get(0) : null);
					if (map.get("publisher") == null || "".equals(map.get("publisher"))) {
						map.put("publisher", highlightmap.get(solrDocument.getFieldValue("id")).get("publisherCn") != null ? highlightmap.get(solrDocument.getFieldValue("id")).get("publisherCn").get(0) : null);
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
			} else {
				result.put("count", 0);
			}
		} catch (SolrServerException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public int indexLicenses(LLicense license) throws Exception {
		if (license == null) {
			throw new NullPointerException("license can't be null");
		}

		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", license.getId());
		document.addField("title", license.getPublications().getTitle());
		document.addField("local", license.getPublications().getLocal());// 武大限制使用功能需要此字段
		document.addField("titleCn", license.getPublications().getChineseTitle() == null ? "" : license.getPublications().getChineseTitle());
		document.addField("chinesetitle", license.getPublications().getChineseTitle() == null ? "" : license.getPublications().getChineseTitle());
		document.addField("author", license.getPublications().getAuthor() == null ? "" : license.getPublications().getAuthor());
		document.addField("isbn", license.getPublications().getCode());
		document.addField("remark", license.getPublications().getRemark() == null ? "" : license.getPublications().getRemark());
		BPublisher publisher = license.getPublications().getPublisher();
		if (publisher == null && license.getPublications().getPublications() != null) {
			publisher = license.getPublications().getPublications().getPublisher();
		}
		document.addField("publisher", publisher.getName() != null ? publisher.getName() : "");
		document.addField("copyPublisher", publisher.getName() != null ? publisher.getName() : "");
		/**** 2014-03-17 by ruixue.cheng Start ****/
		if (license.getPublications().getSubTitle() != null && !"".equals(license.getPublications().getSubTitle().trim())) {
			document.addField("subtitle", license.getPublications().getSubTitle());
		}
		if (license.getPublications().getSeries() != null && !"".equals(license.getPublications().getSeries().trim())) {
			document.addField("series", license.getPublications().getSeries());
		}
		if (license.getPublications().getEdition() != null && !"".equals(license.getPublications().getEdition().trim())) {
			document.addField("edition", license.getPublications().getEdition());
		}
		if (license.getPublications().getEissn() != null && !"".equals(license.getPublications().getEissn().trim())) {
			document.addField("eissn", license.getPublications().getEissn());
		}
		if (license.getPublications().getHisbn() != null && !"".equals(license.getPublications().getHisbn().trim())) {
			document.addField("hisbn", license.getPublications().getHisbn());
		}
		if (license.getPublications().getSisbn() != null && !"".equals(license.getPublications().getSisbn().trim())) {
			document.addField("pisbn", license.getPublications().getSisbn());
		}
		String keywords = license.getPublications().getKeywords() != null ? license.getPublications().getKeywords() : "";
		if (keywords != null && !"".equals(keywords.trim())) {
			String[] keywordArray = keywords.split(",");
			for (String keyword : keywordArray) {
				if (!"".equals(keyword.trim())) {
					document.addField("keywords", keyword.trim());
				}
			}
		}
		document.addField("publishDate", license.getPublications().getPubDate() == null ? "" : license.getPublications().getPubDate());
		String year = "";
		if (license.getPublications().getPubDate() != null) {
			year = license.getPublications().getPubDate().length() > 4 ? license.getPublications().getPubDate().substring(0, 4) : license.getPublications().getPubDate();
		} else if (license.getPublications().getYear() != null) {
			year = license.getPublications().getYear();
		} else {
			year = "";
		}
		document.addField("year", year);
		if (license.getPublications().getStartPage() != null && !"".equals(license.getPublications().getStartPage().toString())) {
			document.addField("startpage", license.getPublications().getStartPage());
		}
		if (license.getPublications().getEndPage() != null && !"".equals(license.getPublications().getEndPage().toString())) {
			document.addField("endpage", license.getPublications().getEndPage());
		}
		if (license.getPublications().getDoi() != null && !"".equals(license.getPublications().getDoi().trim())) {
			document.addField("doi", license.getPublications().getDoi());
		}
		if (license.getPublications().getVolumeCode() != null && !"".equals(license.getPublications().getVolumeCode().trim())) {
			document.addField("volume", license.getPublications().getVolumeCode());
		}
		if (license.getPublications().getIssueCode() != null && !"".equals(license.getPublications().getIssueCode().trim())) {
			document.addField("issue", license.getPublications().getIssueCode());
		}
		String languages = license.getPublications().getLang() != null ? license.getPublications().getLang() : "";
		if (languages != null && !"".equals(languages.trim())) {
			String[] languageArray = languages.split(",");
			for (String language : languageArray) {
				if (!"".equals(language.trim())) {
					document.addField("language", language.trim());
				}
			}
		}
		/**** 2014-03-17 by ruixue.cheng End ****/
		document.addField("type", license.getPublications().getType());
		Date createOn = license.getPublications().getCreateOn();
		if (createOn == null && license.getPublications().getType() == 3 && license.getPublications().getPublications() != null) {
			createOn = license.getPublications().getPublications().getCreateOn();
		}
		document.addField("createOn", createOn == null ? "" : createOn);
		document.addField("pubDate", license.getPublications().getPubDate());
		// 所属用户
		document.addField("cover", license.getUser().getId());

		if (license.getPublications().getFullText() != null && !"".equals(license.getPublications().getFullText().trim())) {
			document.addField("fullText", license.getPublications().getFullText());
		}

		if (license.getPublications().getPubSubject() != null && !"".equals(license.getPublications().getPubSubject().toString())) {
			String pubSubject = license.getPublications().getPubSubject();
			String repS1 = pubSubject.replace("][", ";");
			String repS2 = repS1.replace("[", "");
			String repS3 = repS2.replace("]", "");

			String[] taxonomyArray = repS3.split(";");
			for (String taxonomy : taxonomyArray) {
				document.addField("taxonomy", taxonomy.trim());
			}
		}

		if (license.getPublications().getPubSubjectEn() != null && !"".equals(license.getPublications().getPubSubjectEn().toString())) {
			String pubSubjectEn = license.getPublications().getPubSubjectEn();
			String repS1En = pubSubjectEn.replace("][", ";");
			String repS2En = repS1En.replace("[", "");
			String repS3En = repS2En.replace("]", "");

			String[] taxonomyEnArray = repS3En.split(";");

			for (String taxonomyEn : taxonomyEnArray) {
				document.addField("taxonomyEn", taxonomyEn.trim());
			}
		}
		int status = 500;

		try {
			solrClient.add(document);
			UpdateResponse response = solrClient.commit();
			status = response.getStatus();
		} catch (SolrServerException e) {
			solrClient.rollback();
			e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			e.printStackTrace();
		}

		return status;
	}

	public Map<String, Object> searchByTitle(String title, String userId, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(title)) {
			throw new IllegalArgumentException("document title can't be null");
		}
		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("document userId can't be null");
		}
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		// sb.append(CharUtil.isChinese(title)?"titleCn:":"title:");
		sb.append("(title:");
		sb.append(title);
		sb.append(" OR ");
		sb.append("titleCn:");
		title = myEscapeQueryChars(title);
		sb.append(title);
		sb.append(")");
		sb.append(" AND ");
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append(" ( ");
		sb.append(cover);
		sb.append(" ) ");
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
	public Map<String, Object> searchByAuthor(String author, String userId, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(author)) {
			throw new IllegalArgumentException("document author can't be null");
		}

		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("document userId can't be null");
		}

		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		sb.append("(author:");
		sb.append(author);
		sb.append(" OR ");
		sb.append("authorCn:");
		author = myEscapeQueryChars(author);
		sb.append(author);
		sb.append(")");
		// sb.append(CharUtil.isChinese(author)?"authorCn:":"author:");
		// author = myEscapeQueryChars(author);
		// sb.append(author);
		sb.append(" AND ");
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append(" ( ");
		sb.append(cover);
		sb.append(" ) ");
		query.addHighlightField("author");
		query.addHighlightField("authorCn");
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
	public Map<String, Object> searchByISBN(String ISBN, String userId, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(ISBN)) {
			throw new IllegalArgumentException("document ISBN can't be null");
		}

		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("document userId can't be null");
		}

		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer("isbn:");
		ISBN = myEscapeQueryChars(ISBN);
		sb.append(ISBN);
		sb.append(" AND ");
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append(" ( ");
		sb.append(cover);
		sb.append(" ) ");
		query.addHighlightField("isbn");
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
	public Map<String, Object> searchByPublisher(String publisher, String userId, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(publisher)) {
			throw new IllegalArgumentException("document publisher name can't be null");
		}

		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("document userId can't be null");
		}

		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		sb.append("(copyPublisher:");
		sb.append(publisher);
		sb.append(" OR ");
		sb.append("copyPublisherCn:");
		publisher = myEscapeQueryChars(publisher);
		sb.append(publisher);
		sb.append(")");
		// sb.append(CharUtil.isChinese(publisher)?"copyPublisherCn:":"copyPublisher:");
		// publisher = myEscapeQueryChars(publisher);
		// sb.append(publisher);
		sb.append(" AND ");
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append(" ( ");
		sb.append(cover);
		sb.append(" ) ");
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
	public Map<String, Object> searchByAllFullText(String keywords, String userId, int pn, int size, Map<String, String> queryParam, String order) throws Exception {
		if (StringUtils.isBlank(keywords)) {
			throw new IllegalArgumentException("keywords can't be null");
		}

		if (StringUtils.isBlank(userId)) {
			throw new IllegalArgumentException("document userId can't be null");
		}
		Boolean flag = CharUtil.isChinese(keywords);
		keywords = "(" + myEscapeQueryChars(keywords) + ")";
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();
		// sb.append("(");
		// sb.append(flag?"titleCn:":"title:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(flag?"remarkCn:":"remark:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(flag?"authorCn:":"author:");
		// sb.append(keywords);
		// sb.append(" OR ");
		// sb.append(flag?"copyPublisherCn:":"copyPublisher:");
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
		sb.append(flag ? "text:" : "fullText:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("pisbn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("hisbn:");
		sb.append(keywords);
		sb.append(" OR ");
		sb.append("eissn:");
		sb.append(keywords);
		sb.append(")");
		sb.append(" AND ");
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append(" ( ");
		sb.append(cover);
		sb.append(" ) ");

		// 分类
		if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
			String taxStr = queryParam.get("taxonomy").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			for (int j = 0; j < taxArr.length; j++) {
				sb.append(" AND taxonomy:");
				sb.append("(" + taxArr[j].split(" ")[0] + "*) ");
			}
		}
		// 英文分类
		if (queryParam.get("taxonomyEn") != null && !"".equals(queryParam.get("taxonomyEn"))) {
			String taxStr = queryParam.get("taxonomyEn").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			for (int j = 0; j < taxArr.length; j++) {
				sb.append(" AND taxonomyEn:");
				sb.append("(" + taxArr[j].split(" ")[0] + "*) ");
			}
		}

		query.setQuery(sb.toString());
		query.setRequestHandler("/full");

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
			// e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			// e.printStackTrace();
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
			// e.printStackTrace();
		} catch (IOException e) {
			solrClient.rollback();
			// e.printStackTrace();
		}
	}

	private SolrQuery setFacetQuery(SolrQuery query, Map<String, String> queryParam) {
		query.setFacet(true);

		query.addFacetField("type", "publisher", "pubDate", "taxonomy", "taxonomyEn", "language");
		if (queryParam != null) {
			if (queryParam.get("type") != null && !"".equals(queryParam.get("type"))) {
				query.addFilterQuery("{!tag=type}type:" + queryParam.get("type"));
			}
			if (queryParam.get("publisher") != null && !"".equals(queryParam.get("publisher"))) {
				query.addFilterQuery("{!tag=pub}publisher:" + queryParam.get("publisher"));
			}
			if (queryParam.get("pubDate") != null && !"".equals(queryParam.get("pubDate"))) {
				query.addFilterQuery("{!tag=pubDate}pubDate:" + queryParam.get("pubDate"));
			}
			if (queryParam.get("taxonomy") != null && !"".equals(queryParam.get("taxonomy"))) {
				query.addFilterQuery("{!tag=taxonomy}taxonomy:" + queryParam.get("taxonomy"));
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
			if (null != queryParam.get("local") && !"".equals(queryParam.get("local"))) {
				query.addFilterQuery("local:" + queryParam.get("local"));
			}
			if (null != queryParam.get("notLanguage") && !"".equals(queryParam.get("notLanguage"))) {
				query.addFilterQuery(queryParam.get("notLanguage"));
			}

		}
		return query;
	}

	@Override
	public Map<String, Object> advancedSearch(String userId, int curpage, int pageCount, Map<String, String> param, String order) throws Exception {
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();

		int flag = 0;

		String keywords = param.get("searchValue") == null ? "" : param.get("searchValue");
		Boolean isChinese = CharUtil.isChinese(keywords);
		keywords = ClientUtils.escapeQueryChars(keywords);
		if ("1".equals(param.get("keywordCondition"))) {
			keywords = "\"" + keywords + "\"";
		} /*
			 * else if("2".equals(param.get("keywordCondition"))){
			 * keywords=param.get("searchValue"); }
			 */

		// 关键字
		if (param.get("searchValue") != null && !"".equals(param.get("searchValue"))) {
			if (flag == 0) {
				sb.append(isChinese ? "(titleCn:" : "(title:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "remarkCn:" : "remark:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "authorCn:" : "author:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "copyPublisherCn:" : "copyPublisher:");
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
			} else {
				sb.append(" AND ");
				sb.append(isChinese ? "(titleCn:" : "(title:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "remarkCn:" : "remark:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "authorCn:" : "author:");
				sb.append(keywords);
				sb.append(" ");
				sb.append(isChinese ? "copyPublisherCn:" : "copyPublisher:");
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
			}
			// 不包含关键字
			if (param.get("notKeywords") != null && !"".equals(param.get("notKeywords"))) {
				sb.append(" NOT remark:");
				sb.append("\"" + param.get("notKeywords") + "\"");
			}
		} /*
			 * else{ //全部 if(flag==0){ sb.append("*:*"); flag=1; }else{
			 * sb.append(" AND *:* "); } }
			 */
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
			sb.append(" OR ");
			sb.append("pisbn:");
			sb.append(param.get("code"));
			sb.append(" OR ");
			sb.append("hisbn:");
			sb.append(param.get("code"));
			sb.append(" OR ");
			sb.append("eissn:");
			sb.append(param.get("code"));
			sb.append(") ");
		}
		// 分类
		/*
		 * if(param.get("taxonomy")!=null&&!"".equals(param.get("taxonomy"))){
		 * if(flag==0){ sb.append(" taxonomy:"); flag = 1; }else{ sb.append(
		 * " AND taxonomy:"); } sb.append(param.get("taxonomy")); }
		 */
		// 分类
		if (param.get("taxonomy") != null && !"".equals(param.get("taxonomy"))) {
			String taxStr = param.get("taxonomy").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			if (flag == 0) {
				if (taxArr.length == 1) {
					sb.append("taxonomy:");
				}
				flag = 1;
			} else {
				if (taxArr.length == 1) {
					sb.append("AND taxonomy:");
				}
			}
			for (int j = 0; j < taxArr.length; j++) {
				String taxonomy = "";
				if (taxArr.length == 1) {
					if (j == 0) {
						String subCode = taxArr[0].split(" ")[0];
						taxonomy = subCode + "*";
						sb.append(taxonomy);
					}
				} else {
					if (j != 0) {
						String s = taxArr[j];
						String taxs[] = s.trim().split(" ");
						String sd = taxs[0].split(",")[0] + "*";

						sb.append("(");
						sb.append("taxonomy:");
						sb.append(sd);
						sb.append(")");
					}
				}

			}

		}
		// 英文分类
		if (param.get("taxonomyEn") != null && !"".equals(param.get("taxonomyEn"))) {
			String taxStr = param.get("taxonomyEn").toString().trim().replaceAll("\"", "");
			String[] taxArr = taxStr.trim().split(",");
			if (flag == 0) {
				if (taxArr.length == 1) {
					sb.append("taxonomyEn:");
				}
				flag = 1;
			} else {
				if (taxArr.length == 1) {
					sb.append("AND taxonomyEn:");
				}
			}
			for (int j = 0; j < taxArr.length; j++) {
				String taxonomy = "";
				if (taxArr.length == 1) {
					if (j == 0) {
						String subCode = taxArr[0].split(" ")[0];
						taxonomy = subCode + "*";
						sb.append(taxonomy);
					}
				} else {
					if (j != 0) {
						String s = taxArr[j];
						String taxs[] = s.trim().split(" ");
						String sd = taxs[0].split(",")[0] + "*";

						sb.append("(");
						sb.append("taxonomyEn:");
						sb.append(sd);
						sb.append(")");
					}
				}

			}

		}
		// 类型
		if (param.get("pubType") != null && !"".equals(param.get("pubType"))) {
			if (flag == 0) {
				sb.append(" type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("pubType"));
		}
		// 出版年份 开始时间不为空
		if ((param.get("pubDateStart") != null && !"".equals(param.get("pubDateStart"))) || (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd")))) {
			if (flag == 0) {
				sb.append(" pubDate:");
				flag = 1;
			} else {
				sb.append(" AND pubDate:");
			}
			sb.append("[");
			if (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd"))) {
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
				sb.append(" pubDate:");
				flag = 1;
			} else {
				sb.append(" AND pubDate:");
			}
			sb.append(param.get("pubDate"));
		}
		// 类型
		if (param.get("type") != null && !"".equals(param.get("type"))) {
			if (flag == 0) {
				sb.append(" type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("type"));
		}
		// 语种
		if (param.get("language") != null && !"".equals(param.get("language"))) {
			if (flag == 0) {
				sb.append(" language:");
				flag = 1;
			} else {
				sb.append(" AND language:");
			}
			sb.append(param.get("language"));
		}

		// 本地资源与非本地资源
		if (null != param.get("local") && !"".equals(param.get("local"))) {
			if (flag == 0) {
				sb.append(" local:");
				flag = 1;
			} else {
				sb.append(" AND local:");
			}
			sb.append(param.get("local"));
		}

		// 非语种
		if (param.get("notLanguage") != null && !"".equals(param.get("notLanguage"))) {
			if (flag == 0) {
				sb.append(" " + param.get("notLanguage"));
				flag = 1;
			} else {
				sb.append(" AND " + param.get("notLanguage"));
			}
		}

		// 用户
		if (flag != 0) {
			sb.append(" AND ");
		}
		String[] userIds = userId.split(",");
		String cover = "";
		for (int i = 0; i < userIds.length; i++) {
			cover += "cover:" + userIds[i];
			if (i < userIds.length - 1) {
				cover += " OR ";
			}
		}
		sb.append("(");
		sb.append(cover);
		sb.append(")");
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
		setFacetQuery(query, null);
		return searchIds(query, curpage, pageCount);
	}

	@Override
	public Map<String, Object> advancedSearch(Integer coverType, String userId, int curpage, int pageCount, Map<String, String> param, String order) throws Exception {
		SolrQuery query = new SolrQuery();
		StringBuffer sb = new StringBuffer();

		int flag = 0;

		String keywords = param.get("searchValue") == null ? "" : param.get("searchValue");
		Boolean isChinese = CharUtil.isChinese(keywords);
		keywords = ClientUtils.escapeQueryChars(keywords);
		if ("1".equals(param.get("keywordCondition"))) {
			keywords = "\"" + keywords + "\"";
		} /*
			 * else if("2".equals(param.get("keywordCondition"))){
			 * keywords=param.get("searchValue"); }
			 */
		keywords = "(" + keywords.replace("\\ ", " ") + ")";
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
			sb.append("fullText:");
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
		} /*
			 * else{ //全部 if(flag==0){ sb.append("*:*"); flag=1; }else{
			 * sb.append(" AND *:* "); } }
			 */
		// 语言分类查询分类
		if (param.get("isCn") != null && !"".equals(param.get("isCn"))) {
			String n = param.get("isCn").equals("true") ? "" : " NOT ";
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
		/*
		 * if(param.get("taxonomy")!=null&&!"".equals(param.get("taxonomy"))){
		 * if(flag==0){ sb.append(" taxonomy:"); flag = 1; }else{ sb.append(
		 * " AND taxonomy:"); } sb.append(param.get("taxonomy")); }
		 */
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
		// 类型
		if (param.get("pubType") != null && !"".equals(param.get("pubType"))) {
			if (flag == 0) {
				sb.append(" type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("pubType"));
			query.addHighlightField("pubType");
		}
		// 出版年份 开始时间不为空
		if ((param.get("pubDateStart") != null && !"".equals(param.get("pubDateStart"))) || (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd")))) {
			if (flag == 0) {
				sb.append(" pubDate:");
				flag = 1;
			} else {
				sb.append(" AND pubDate:");
			}
			sb.append("[");
			if (param.get("pubDateEnd") != null & !"".equals(param.get("pubDateEnd"))) {
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
		if (param.get("type") != null && !"".equals(param.get("type"))) {
			if (flag == 0) {
				sb.append(" type:");
				flag = 1;
			} else {
				sb.append(" AND type:");
			}
			sb.append(param.get("type"));
		}
		// 语种
		if (param.get("language") != null && !"".equals(param.get("language"))) {
			if (flag == 0) {
				sb.append(" language:");
				flag = 1;
			} else {
				sb.append(" AND language:");
			}
			sb.append(param.get("language"));
		}

		// 本地资源与非本地资源
		if (null != param.get("local") && !"".equals(param.get("local"))) {
			if (flag == 0) {
				sb.append(" local:");
				flag = 1;
			} else {
				sb.append(" AND local:");
			}
			sb.append(param.get("local"));
		}
		// 非语种
		if (param.get("notLanguage") != null && !"".equals(param.get("notLanguage"))) {
			if (flag == 0) {
				sb.append(" " + param.get("notLanguage"));
				flag = 1;
			} else {
				sb.append(" AND " + param.get("notLanguage"));
			}
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
		/*
		 * if(userId!=null&&!"".equals(userId)){ if(flag==0){
		 * sb.append("cover:"); flag = 1; }else{ sb.append(" AND cover:"); }
		 * sb.append(userId); }
		 */
		// 用户
		if (flag != 0) {
			sb.append(" AND ");
		}
		String cover = "";
		String[] userIds = userId.split(",");
		// 已订阅 查询
		if (coverType == 1) {

			String covers = "";

			List<String> list1 = new ArrayList<String>();
			String oafree = "";
			Map<String, String> oafreeMap = new HashMap<String, String>();
			oafreeMap = Param.getParam("OAFree.uid.config");
			oafree = oafreeMap.get("uid");
			for (int i = 0; i < userIds.length; i++) {
				if (!userIds[i].equals(oafree)) {
					covers = "cover:" + userIds[i];
					list1.add(covers);
					/*
					 * cover +="cover:"+ userIds[i]; if(i<userIds.length-(a+2)){
					 * cover+=" OR "; }
					 */
				}

			}
			// 已订阅
			if (list1 != null && list1.size() > 0) {
				for (int i = 0; i < list1.size(); i++) {
					if (list1.size() == (i + 1)) {
						cover += list1.get(i);
					} else {
						cover += list1.get(i) + " OR ";
					}
				}

			}
		}

		// 开源、免费
		if (coverType == 2) {

			String oaCover = "";

			// 免费、开源
			List<String> list2 = new ArrayList<String>();
			String oafree = "";
			Map<String, String> oafreeMap = new HashMap<String, String>();
			oafreeMap = Param.getParam("OAFree.uid.config");
			oafree = oafreeMap.get("uid");
			for (int i = 0; i < userIds.length; i++) {

				if (userIds[i].equals(oafree)) {
					oaCover = "cover:" + userIds[i];
					list2.add(oaCover);
				}
			}
			// 开源、免费
			if (list2 != null && list2.size() > 0) {
				for (int i = 0; i < list2.size(); i++) {
					if (list2.size() == (i + 1)) {
						cover += list2.get(i);
					} else {
						cover += list2.get(i) + " OR ";
					}
				}

			}
		}
		/*
		 * String[] userIds =userId.split(","); String cover=""; String covers =
		 * ""; String oaCover =""; List<String> list1= new ArrayList<String>();
		 * //免费、开源 List<String> list2= new ArrayList<String>(); String oafree =
		 * ""; Map<String,String> oafreeMap = new HashMap<String, String>();
		 * oafreeMap = Param.getParam("OAFree.uid.config"); oafree =
		 * oafreeMap.get("uid"); for(int i=0;i<userIds.length;i++){
		 * if(!userIds[i].equals(oafree)){ covers="cover:"+userIds[i];
		 * list1.add(covers); cover +="cover:"+ userIds[i];
		 * if(i<userIds.length-(a+2)){ cover+=" OR "; } }
		 * if(userIds[i].equals(oafree)){ oaCover = "cover:"+userIds[i];
		 * list2.add(oaCover); } } //已订阅 if(list1!=null&&list1.size()>0){
		 * for(int i=0;i<list1.size();i++){ if(list1.size()==(i+1)){
		 * cover+=list1.get(i); }else{ cover+=list1.get(i)+" OR "; } }
		 * 
		 * }
		 */

		sb.append("(");
		sb.append(cover);
		sb.append(")");
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
			OffsetAttribute offset = ts.addAttribute(OffsetAttribute.class);
			// 获取词元文本属性
			CharTermAttribute term = ts.addAttribute(CharTermAttribute.class);
			// 获取词元文本属性
			TypeAttribute type = ts.addAttribute(TypeAttribute.class);

			// 重置TokenStream（重置StringReader）
			ts.reset();
			result = new String[ts.hashCode()];
			int i = 0;
			// 迭代获取分词结果
			while (ts.incrementToken()) {
				result[i] = term.toString();
				i++;
				System.out.println(offset.startOffset() + " - " + offset.endOffset() + " : " + term.toString() + " | " + type.type());
			}
			// 关闭TokenStream（关闭StringReader）
			ts.end(); // Perform end-of-stream operations, e.g. set the final
						// offset.

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
}
