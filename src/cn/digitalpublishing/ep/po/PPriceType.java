package cn.digitalpublishing.ep.po;

import java.util.HashSet;
import java.util.Set;

/**
 * EpubPPriceType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

@SuppressWarnings("serial")
public class PPriceType implements java.io.Serializable {

	// Fields
	/**
	 * 主键
	 */
	private String id;
	/**
	 * 价格类型编号
	 */
	private String code;
	/**
	 * 价格类型名称
	 */
	private String name;
	/**
	 * 价格
	 */
	private Set<PPrice> prices = new HashSet<PPrice>();

	// Constructors

	/** default constructor */
	public PPriceType() {
	}

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

	public Set<PPrice> getPrices() {
		return prices;
	}

	public void setPrices(Set<PPrice> prices) {
		this.prices = prices;
	}
}