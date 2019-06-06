package cn.digitalpublishing.springmvc.form.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.ep.po.CAccount;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class OrgUserForm extends BaseForm{
	/**
	 * 发送邮件
	 */
	public final static Integer PWD_SEND = 1;
	/**
	 * 不发送邮件
	 */
	public final static Integer PWD_NO_SEND = 2;
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
	private String[]typePropIds;
	
	//用于存储类型属性的值
	private String[] propsValue;
	
	private Map<String,String> values = new HashMap<String,String>();
	
	//存储机构类别名称：( Library：1/Enterprise:2/government:3) 
	private HashMap<String, String> orgTypeMap = new HashMap<String, String>();
	//存在机构信息
	private List<BInstitution> institutionList = new ArrayList<BInstitution>();
	
	//存储国家名称
	private HashMap<String, String> countryMap = new HashMap<String, String>();
	//省份名称
	private HashMap<String, String> provinceMap =new HashMap<String, String>();
	
	private Integer sendpwd = PWD_SEND;
	
	
	public HashMap<String, String> getProvinceMap() {
		return provinceMap;
	}
	public void setProvinceMap(HashMap<String, String> provinceMap) {
		this.provinceMap = provinceMap;
	}
	/**
	 * 账户名（用于管理页面查询）
	 */
	private String uid;
	/**
	 * 机构名称
	 */
	private String institutionName;
	
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
		this.getCondition().put("userName", userName!=null?userName.trim():"");
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
	public Map<String, String> getValues() {
		return values;
	}
	public void setValues(Map<String, String> values) {
		this.values = values;
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
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.getCondition().put("uid", uid!=null?uid.trim():"");
		this.uid = uid;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		if(!"".equals(status)){
			this.getCondition().put("status",Integer.valueOf(status));
		}
		this.status = status;
	}
	public HashMap<String, String> getCountryMap() {
		return countryMap;
	}
	public void setCountryMap(HashMap<String, String> countryMap) {
		this.countryMap = countryMap;
	}
	public String getInstitutionName() {
		return institutionName;
	}
	public void setInstitutionName(String institutionName) {
		this.institutionName = institutionName;
	}
	public Integer getSendpwd() {
		return sendpwd;
	}
	public void setSendpwd(Integer sendpwd) {
		this.sendpwd = sendpwd;
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
