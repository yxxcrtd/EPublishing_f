package cn.digitalpublishing.ep.po;

@SuppressWarnings("serial")
public class SOnsale implements java.io.Serializable {
	/**
	 * 上线时间
	 */
	private String saleDate;
	/**
	 * 上线总量
	 */
	private Integer total;
	
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}

}
