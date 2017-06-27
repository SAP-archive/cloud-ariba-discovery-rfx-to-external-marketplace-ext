package com.sap.cloud.samples.ariba.discovery.rfx.dtos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Product category DTO.
 *
 */
@XmlRootElement
public class ProductCategoryDto {

	private String commodityType;
	private String commodityCode;
	private String name;

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
