package com.sap.cloud.samples.ariba.discovery.rfx.dtos;

import java.net.URL;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Event DTO.
 *
 */
@XmlRootElement
public class EventDto {

	private String eventId;
	private String postingId;
	private String postingTitle;
	private List<String> serviceLocations;
	private List<ProductCategoryDto> productCategories;
	private OpportunityAmountDto opportunityAmt;
	private String responseDeadline;
	private String description;
	private String buyerName;
	private String buyerANID;
	private String startDate;
	private URL discoveryURL;
	private String awardDate;
	private String statusCheckId;

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getPostingId() {
		return postingId;
	}

	public void setPostingId(String postingId) {
		this.postingId = postingId;
	}

	public String getPostingTitle() {
		return postingTitle;
	}

	public void setPostingTitle(String postingTitle) {
		this.postingTitle = postingTitle;
	}

	public List<String> getServiceLocations() {
		return serviceLocations;
	}

	public void setServiceLocations(List<String> serviceLocations) {
		this.serviceLocations = serviceLocations;
	}

	public List<ProductCategoryDto> getProductCategories() {
		return productCategories;
	}

	public void setProductCategories(List<ProductCategoryDto> productCategories) {
		this.productCategories = productCategories;
	}

	public String getResponseDeadline() {
		return responseDeadline;
	}

	public void setResponseDeadline(String responseDeadline) {
		this.responseDeadline = responseDeadline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getBuyerName() {
		return buyerName;
	}
	
	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerANID() {
		return buyerANID;
	}

	public void setBuyerANID(String buyerANID) {
		this.buyerANID = buyerANID;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public URL getDiscoveryURL() {
		return discoveryURL;
	}

	public void setDiscoveryURL(URL discoveryURL) {
		this.discoveryURL = discoveryURL;
	}

	public OpportunityAmountDto getOpportunityAmt() {
		return opportunityAmt;
	}

	public void setOpportunityAmt(OpportunityAmountDto opportunityAmt) {
		this.opportunityAmt = opportunityAmt;
	}

	public String getAwardDate() {
		return awardDate;
	}

	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}

	public String getStatusCheckId() {
		return statusCheckId;
	}
	
	public void setStatusCheckId(String statusCheckId) {
		this.statusCheckId = statusCheckId;
	}

	@Override
	public String toString() {
		return "[eventId = " + eventId + "]";
	}

}