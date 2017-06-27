package com.sap.cloud.samples.ariba.discovery.rfx.dtos;

/**
 * Opportunity amount end DTO.
 *
 */
public class OpportunityAmtEndDto {

	private String currency;
	private long amount;
	private String symbol;

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
}
