package cn.digitalpublishing.springmvc.form.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.ep.po.CDirectory;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class UserForm extends BaseForm {
	//账户状态 1-激活 2-未激活 3-全部 
	private String status;

	private String pwdCheck;
	//用户类型 1：个人用户；2：机构用户
	private String userType;
	//用户姓名
	private String userName;
	//用户院系
	private String userDepartment;
	//用户职称
	private String userTitle;
	//用户电话
	private String userTelephone;
	//账号信息
	private CAccount account = new CAccount();
	//用于存储类型属性Id集合（在用户注册页面将全部类型属性存储到用户属性中）
	private String[] typePropIds;

	//用于存储类型属性的值
	private String[] propsValue;
	//存储国家名称
	private HashMap<String, String> countryMap = new HashMap<String, String>();
	//身份Map
	private Map<String, String> identityMap = new HashMap<String, String>();

	private Map<String, String> values = new HashMap<String, String>();

	private Integer level;

	//存储机构类别名称：( Library：1/Enterprise:2/government:3) 
	private HashMap<String, String> orgTypeMap = new HashMap<String, String>();
	//存在机构信息
	private List<BInstitution> institutionList = new ArrayList<BInstitution>();
	// 排序
	private String order;
	//删除Id集合，以, 逗号隔开
	private String dels;

	// 账户名（用于管理页面查询）
	private String uid;
	/**
	 * 提醒类别
	 * 1：出版物提醒；2：学科主题
	 */
	private Integer alertsType;
	//订阅频率
	private Integer[] frequencys;
	//批量设置学科提醒频率
	private Integer frequencysAll;
	//学科主题Id,用于记录用户订阅那些学科主题
	private String[] deals;

	// 父分类编号,用于进行查询学科主题
	private String pCode;
	//树节点code
	private String[] treeCodes;
	//类型
	private Integer type;
	//操作类型 1、增加  2、修改  3、删除
	private Integer operType;
	//搜索保存的文件夹ID
	private String dirId;
	//搜索收藏夹
	private CDirectory dirObj = new CDirectory();
	//收藏夹名称
	private String name;
	//金额
	private Double amount;
	//搜索历史收藏夹 每行样式
	private String cssType;
	//学科提醒频率
	private Integer frequency;
	/*private CUser user;
	public CUser getUser() {
		return user;
	}
	public void setUser(CUser user) {
		this.user = user;
	}*/
	private String email;

	public Integer getFrequencysAll() {
		return frequencysAll;
	}

	public void setFrequencysAll(Integer frequencysAll) {
		this.frequencysAll = frequencysAll;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.getCondition().put("status", status != null ? status.trim() : "");
		this.status = status;
	}

	public String getPwdCheck() {
		return pwdCheck;
	}

	public void setPwdCheck(String pwdCheck) {
		this.pwdCheck = pwdCheck;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.getCondition().put("userName", userName != null ? userName.trim() : "");
		this.userName = userName;
	}

	public CAccount getAccount() {
		return account;
	}

	public void setAccount(CAccount account) {
		this.account = account;
	}

	public String[] getTypePropIds() {
		return typePropIds;
	}

	public void setTypePropIds(String[] typePropIds) {
		this.typePropIds = typePropIds;
	}

	public String[] getPropsValue() {
		return propsValue;
	}

	public void setPropsValue(String[] propsValue) {
		this.propsValue = propsValue;
	}

	public HashMap<String, String> getCountryMap() {
		return countryMap;
	}

	public void setCountryMap(HashMap<String, String> countryMap) {
		this.countryMap = countryMap;
	}

	public Map<String, String> getIdentityMap() {
		return identityMap;
	}

	public void setIdentityMap(Map<String, String> identityMap) {
		this.identityMap = identityMap;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public HashMap<String, String> getOrgTypeMap() {
		return orgTypeMap;
	}

	public void setOrgTypeMap(HashMap<String, String> orgTypeMap) {
		this.orgTypeMap = orgTypeMap;
	}

	public List<BInstitution> getInstitutionList() {
		return institutionList;
	}

	public void setInstitutionList(List<BInstitution> institutionList) {
		this.institutionList = institutionList;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getDels() {
		return dels;
	}

	public void setDels(String dels) {
		this.dels = dels;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.getCondition().put("uid", uid != null ? uid.trim() : "");
		this.uid = uid;
	}

	public Integer getAlertsType() {
		return alertsType;
	}

	public void setAlertsType(Integer alertsType) {
		this.alertsType = alertsType;
	}

	public Integer[] getFrequencys() {
		return frequencys;
	}

	public void setFrequencys(Integer[] frequencys) {
		this.frequencys = frequencys;
	}

	public String[] getDeals() {
		return deals;
	}

	public void setDeals(String[] deals) {
		this.deals = deals;
	}

	public String getpCode() {
		return pCode;
	}

	public void setpCode(String pCode) {
		this.pCode = pCode;
	}

	public String[] getTreeCodes() {
		return treeCodes;
	}

	public void setTreeCodes(String[] treeCodes) {
		this.treeCodes = treeCodes;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public String getDirId() {
		return dirId;
	}

	public void setDirId(String dirId) {
		this.dirId = dirId;
	}

	public CDirectory getDirObj() {
		return dirObj;
	}

	public void setDirObj(CDirectory dirObj) {
		this.dirObj = dirObj;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCssType() {
		return cssType;
	}

	public void setCssType(String cssType) {
		this.cssType = cssType;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getUserDepartment() {
		return userDepartment;
	}

	public void setUserDepartment(String userDepartment) {
		this.userDepartment = userDepartment;
	}

	public String getUserTitle() {
		return userTitle;
	}

	public void setUserTitle(String userTitle) {
		this.userTitle = userTitle;
	}

	public String getUserTelephone() {
		return userTelephone;
	}

	public void setUserTelephone(String userTelephone) {
		this.userTelephone = userTelephone;
	}

}
