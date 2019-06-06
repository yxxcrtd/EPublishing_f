package cn.digitalpublishing.springmvc.form.index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.BSubject;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class IndexForm extends BaseForm {
	//产品包
	PCollection pubCollection = new PCollection();
	//最新
	PPublications newest = new PPublications();
	//精选
	PPublications selected = new PPublications();
	//特惠
	PPublications special = new PPublications();
	//搜索值
	private String searchValue;
	//搜索类型=====0-全文;1-标题;2-作者..默认0
	private Integer searchsType;
	//排序 按照createdOn --desc、asc
	//	private String order ;
	//资源类型
	//	private String pubType;
	//出版商
	//	private String publisher;
	//出版年份
	//	private String pubDate;
	//分类
	//	private String taxonomy;
	//英文
	//	private String taxonomyEn;
	//关键字条件
	private Integer keywordCondition;
	//标题
	private String title;
	private String isCn;
	//作者
	private String author;
	//ISBN/ISSN
	private String code;
	//出版社
	private String publisher;
	//不包含的关键字
	private String notKeywords;
	//出版年份时间段
	private String pubDateStart;
	private String pubDateEnd;
	//标题首字母
	private String prefixWord;
	//分类列表
	private List<BSubject> subList = new ArrayList<BSubject>();
	//是否在订阅内查询
	//	private Integer lcense;
	//是否精确查找
	private Integer isAccurate;
	private Map<String, String> keyMap = new HashMap<String, String>();

	private BInstitution ins;
	// 本地书刊-查询使用的标识
	private String fullText;
	// nochinese-查询时使用的标识
	private String nochinese;
	// 1、 非本地化 2、本地化
	private String local;
	// 非language处理
	private String notLanguage;
	// 判断是否需要按照createOn排序
	private String sortFlag;
	//高级搜索定位问题
	private String selectflag;

	//	private String pCode;
	//	private String publisherId;
	//	private String subParentId;
	//	private String parentTaxonomy;
	//	private String parentTaxonomyEn;

	public BInstitution getIns() {
		return ins;
	}

	public String getSelectflag() {
		return selectflag;
	}

	public void setSelectflag(String selectflag) {
		this.selectflag = selectflag;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public void setIns(BInstitution ins) {
		this.ins = ins;
	}

	private String subFlag;

	private String newFlag;

	public String getNewFlag() {
		return newFlag;
	}

	public void setNewFlag(String newFlag) {
		this.newFlag = newFlag;
	}

	public String getSubFlag() {
		return subFlag;
	}

	public void setSubFlag(String subFlag) {
		this.subFlag = subFlag;
	}

	public PCollection getPubCollection() {
		return pubCollection;
	}

	public void setPubCollection(PCollection pubCollection) {
		this.pubCollection = pubCollection;
	}

	public PPublications getNewest() {
		return newest;
	}

	public void setNewest(PPublications newest) {
		this.newest = newest;
	}

	public PPublications getSelected() {
		return selected;
	}

	public void setSelected(PPublications selected) {
		this.selected = selected;
	}

	public PPublications getSpecial() {
		return special;
	}

	public void setSpecial(PPublications special) {
		this.special = special;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public Integer getSearchsType() {
		return searchsType;
	}

	public void setSearchsType(Integer searchsType) {
		this.searchsType = searchsType;
	}

	public Integer getKeywordCondition() {
		return keywordCondition;
	}

	public void setKeywordCondition(Integer keywordCondition) {
		this.keywordCondition = keywordCondition;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsCn() {
		return isCn;
	}

	public void setIsCn(String isCn) {
		this.isCn = isCn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getNotKeywords() {
		return notKeywords;
	}

	public void setNotKeywords(String notKeywords) {
		this.notKeywords = notKeywords;
	}

	public String getPubDateStart() {
		return pubDateStart;
	}

	public void setPubDateStart(String pubDateStart) {
		this.pubDateStart = pubDateStart;
	}

	public String getPubDateEnd() {
		return pubDateEnd;
	}

	public void setPubDateEnd(String pubDateEnd) {
		this.pubDateEnd = pubDateEnd;
	}

	public List<BSubject> getSubList() {
		return subList;
	}

	public void setSubList(List<BSubject> subList) {
		this.subList = subList;
	}

	public Integer getIsAccurate() {
		return isAccurate;
	}

	public void setIsAccurate(Integer isAccurate) {
		this.isAccurate = isAccurate;
	}

	public Map<String, String> getKeyMap() {
		return keyMap;
	}

	public void setKeyMap(Map<String, String> keyMap) {
		this.keyMap = keyMap;
	}

	public String getPrefixWord() {
		return prefixWord;
	}

	public void setPrefixWord(String prefixWord) {
		this.prefixWord = prefixWord;
	}

	public String getFullText() {
		return fullText;
	}

	public void setFullText(String fullText) {
		this.fullText = fullText;
	}

	public String getNochinese() {
		return nochinese;
	}

	public void setNochinese(String nochinese) {
		this.nochinese = nochinese;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}

	public String getNotLanguage() {
		return notLanguage;
	}

	public void setNotLanguage(String notLanguage) {
		this.notLanguage = notLanguage;
	}

	public String getSortFlag() {
		return sortFlag;
	}

	public void setSortFlag(String sortFlag) {
		this.sortFlag = sortFlag;
	}

}
