package cn.digitalpublishing.springmvc.form.configure;

import org.springframework.web.multipart.commons.CommonsMultipartFile;

import cn.digitalpublishing.ep.po.BInstitution;
import cn.digitalpublishing.springmvc.form.BaseForm;

public class BInstitutionForm extends BaseForm {
	
	public final static String PARAM_NAME="upload.directory.config";
	
	public final static String PARAM_KEY="institution";
	
	private CommonsMultipartFile file = null;
	
	private String format = "jpg|gif|png|bmp";
	
	private String uploadPath;
	
		private BInstitution obj=new BInstitution();
		/**
		 * 主键
		 */
		private String id;
		/**
		 * 编号  唯一的
		 */
		private String code;
		/**
		 * 机构名称
		 */
		private String name;
		/**
		 * 机构LOGO
		 */
		private String logo;
		/**
		 * LOGO链接
		 */
		private String logoUrl;
		/**
		 * LOGO标致
		 */
		private String logoNote;
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getLogo() {
			return logo;
		}
		public void setLogo(String logo) {
			this.logo = logo;
		}
		public String getLogoUrl() {
			return logoUrl;
		}
		public void setLogoUrl(String logoUrl) {
			this.logoUrl = logoUrl;
		}
		public String getLogoNote() {
			return logoNote;
		}
		public void setLogoNote(String logoNote) {
			this.logoNote = logoNote;
		}
		public BInstitution getObj() {
			return obj;
		}
		public void setObj(BInstitution obj) {
			this.obj = obj;
		}
		public String getUploadPath() {
			return uploadPath;
		}

		public void setUploadPath(String uploadPath) {
			this.uploadPath = uploadPath;
		}

		public CommonsMultipartFile getFile() {
			return file;
		}

		public void setFile(CommonsMultipartFile file) {
			this.file = file;
		}

		public String getFormat() {
			return format;
		}

		public void setFormat(String format) {
			this.format = format;
		}

		
	
}