package cn.digitalpublishing.springmvc.form.product;

import java.util.List;

import cn.digitalpublishing.ep.po.PCcRelation;
import cn.digitalpublishing.ep.po.PCollection;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class PCollectionForm extends BaseForm {
	
	private Integer marktype;
	
	private String typeValue;
	
	private String collectionId;
	/**
	 * 分类Id
	 */
	private String subId;
	/**
	 * 产品类型 1-书籍 2-期刊
	 */
	private String type;
	/**
	 * 出版商
	 */
	private String publishersId;
	private boolean inLicense=false;//是否成功购买（后台确认）
	private boolean inOrderDetail=false;//是否在购物车中
	
	/**
	 * 针对外文文章单篇购买设定的几个查询参数
	 */
	private String publisherId;
	private String subjectId;
	private String langId;
	
	
	private PCollection obj;
	
	private List<PCcRelation> statistic;
	
	private Integer cnBook = 0;
	
	private Integer enBook = 0;
	
	private Integer jounary = 0;
	
	private Integer issue = 0;
	
	public Integer getCnBook() {
		return cnBook;
	}
	public void setCnBook(Integer cnBook) {
		this.cnBook = cnBook;
	}
	public Integer getEnBook() {
		return enBook;
	}
	public void setEnBook(Integer enBook) {
		this.enBook = enBook;
	}
	public Integer getJounary() {
		return jounary;
	}
	public void setJounary(Integer jounary) {
		this.jounary = jounary;
	}
	public Integer getIssue() {
		return issue;
	}
	public void setIssue(Integer issue) {
		this.issue = issue;
	}
	public List<PCcRelation> getStatistic() {
		return statistic;
	}
	public void setStatistic(List<PCcRelation> statistic) {
		this.statistic = statistic;
		if(statistic!=null&&!statistic.isEmpty()){
			for(PCcRelation cc:statistic){
				if(cc.getPublications().getType()==1){
					if("CHS".equals(cc.getPublications().getLang().toUpperCase())||"CHT".equals(cc.getPublications().getLang().toUpperCase())||"CHI".equals(cc.getPublications().getLang().toUpperCase())){//CHS CHT  CHI
						this.cnBook +=cc.getEffective();
					}else{
						this.enBook +=cc.getEffective();
					}
				}else if(cc.getPublications().getType()==2){
					this.jounary +=cc.getEffective();
				}else if(cc.getPublications().getType()==7){
					this.issue +=cc.getEffective();
				}
			}
		}
		
	}
	public PCollection getObj() {
		return obj;
	}
	public void setObj(PCollection obj) {
		this.obj = obj;
	}
	public String getSubId() {
		return subId;
	}
	public void setSubId(String subId) {
		this.subId = subId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getPublishersId() {
		return publishersId;
	}
	public void setPublishersId(String publishersId) {
		this.publishersId = publishersId;
	}
	public Integer getMarktype() {
		return marktype;
	}
	public void setMarktype(Integer marktype) {
		this.marktype = marktype;
	}
	public String getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(String typeValue) {
		this.typeValue = typeValue;
	}
	public String getCollectionId() {
		return collectionId;
	}
	public void setCollectionId(String collectionId) {
		this.collectionId = collectionId;
	}
	public boolean isInLicense() {
		return inLicense;
	}
	public void setInLicense(boolean inLicense) {
		this.inLicense = inLicense;
	}
	public boolean isInOrderDetail() {
		return inOrderDetail;
	}
	public void setInOrderDetail(boolean inOrderDetail) {
		this.inOrderDetail = inOrderDetail;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public String getLangId() {
		return langId;
	}
	public void setLangId(String langId) {
		this.langId = langId;
	}
	
}
