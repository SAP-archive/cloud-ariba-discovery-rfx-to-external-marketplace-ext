package com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Product category entity.
 *
 */
@Entity
public class ProductCategory {

	@Id
	@GeneratedValue
	private String id;

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

	@Override
	public String toString() {
		return "ProductCategory [commodityType=" + commodityType + ", commodityCode=" + commodityCode + ", name=" + name
				+ "]";
	}
}
