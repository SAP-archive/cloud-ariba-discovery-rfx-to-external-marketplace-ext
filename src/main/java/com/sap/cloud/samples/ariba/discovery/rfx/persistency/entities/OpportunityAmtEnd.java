package com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Opportunity amount end entity.
 *
 */
@Entity
public class OpportunityAmtEnd implements Serializable {

	private static final long serialVersionUID = -8946498741454659586L;

	@Id
	@GeneratedValue
	private String id;

	private String currency;
	private long amount;
	private String symbol;

	/**
	 * Returns entity id
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public long getAmount() {
		return amount;
	}

	public void setAmount(long amount) {
		this.amount = amount;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	@Override
	public String toString() {
		return "OpportunityAmtEnd [id=" + id + ", currency=" + currency + ", amount=" + amount + ", symbol=" + symbol
				+ "]";
	}
}
