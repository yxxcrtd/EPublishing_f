package cn.digitalpublishing.springmvc.form.custom;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CUser;
import cn.digitalpublishing.ep.po.PPublications;
import cn.digitalpublishing.ep.po.RRecommend;
import cn.digitalpublishing.ep.po.RRecommendDetail;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class RRecommendForm extends BaseForm{

		private RRecommend obj=new  RRecommend();
		private RRecommendDetail detail=new  RRecommendDetail();
		private List<BInstitution> listInstitution=new ArrayList<BInstitution>();
		
		private String particulars;
		
		private String institutionId;
		
		private String pubid;
		
		private String note;
		
		private String level;
		
		private String isOrder;
		
		private String pubTitle;
		
		private String pubCode;
		
		private String orderStatus;
		
		private CUser recommendUser;//推荐人
		private Integer type;//类型
		
		private String startTime;//开始时间
		private String endTime;//结束时间
		
		public String getStartTime() {
			return startTime;
		}

		public void setStartTime(String startTime) {
			this.startTime = startTime;
		}

		public String getEndTime() {
			return endTime;
		}

		public void setEndTime(String endTime) {
			this.endTime = endTime;
		}

		private Integer sort;//排序方式 0-专家用户推荐次数倒序 1-所有用户推荐次数倒序
		
		private Map<String,String> typeMap = new HashMap<String,String>();
		
		public String getParticulars() {
			return particulars;
		}

		public void setParticulars(String particulars) {
			this.particulars = particulars;
		}

		public Map<String, String> getTypeMap() {
			return typeMap;
		}

		public void setTypeMap(Map<String, String> typeMap) {
			this.typeMap = typeMap;
		}

		public Integer getType() {
			return type;
		}

		public void setType(Integer type) {
			this.type = type;
		}

		public CUser getRecommendUser() {
			return recommendUser;
		}

		public void setRecommendUser(CUser recommendUser) {
			this.recommendUser = recommendUser;
		}

		public RRecommendForm(){
			obj.setInstitution(new BInstitution());
			obj.setPublications(new PPublications());
		}

		public RRecommend getObj() {
			return obj;
		}

		public void setObj(RRecommend obj) {
			this.obj = obj;
		}

		public RRecommendDetail getDetail() {
			return detail;
		}

		public void setDetail(RRecommendDetail detail) {
			this.detail = detail;
		}

		public String getPubid() {
			return pubid;
		}

		public void setPubid(String pubid) {
			this.pubid = pubid;
		}

		public String getNote() {
			return note;
		}

		public void setNote(String note) {
			this.note = note;
		}

		public String getLevel() {
			return level;
		}

		public void setLevel(String level) {
			this.level = level;
		}

		public String getIsOrder() {
			return isOrder;
		}

		public void setIsOrder(String isOrder) {
			this.getCondition().put("isorder",isOrder);
			this.isOrder = isOrder;
		}

		public String getPubTitle() {
			return pubTitle;
		}

		public void setPubTitle(String pubTitle) {
			this.getCondition().put("pubtitle",pubTitle.trim());
			this.pubTitle = pubTitle;
		}

		public String getPubCode() {
			return pubCode;
		}

		public void setPubCode(String pubCode) {
			this.getCondition().put("pubCode", pubCode.trim());
			this.pubCode = pubCode;
		}

		public List<BInstitution> getListInstitution() {
			return listInstitution;
		}

		public void setListInstitution(List<BInstitution> listInstitution) {
			this.listInstitution = listInstitution;
		}

		public String getInstitutionId() {
			return institutionId;
		}

		public void setInstitutionId(String institutionId) {
			this.getCondition().put("institutionid", institutionId);
			this.institutionId = institutionId;
		}

		public String getOrderStatus() {
			return orderStatus;
		}

		public void setOrderStatus(String orderStatus) {
			this.orderStatus = orderStatus;
		}

		public Integer getSort() {
			return sort;
		}

		public void setSort(Integer sort) {
			this.sort = sort;
		}				
}
