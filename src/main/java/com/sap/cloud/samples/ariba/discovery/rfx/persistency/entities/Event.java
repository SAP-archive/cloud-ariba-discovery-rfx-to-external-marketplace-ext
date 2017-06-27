package com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities;

import java.io.Serializable;
import java.net.URL;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 * Event entity.
 *
 */
@Entity
@NamedQueries({ @NamedQuery(name = Event.QUERY_FIND_BY_EVENT_ID, query = "SELECT e FROM Event e WHERE e.eventId = :"
		+ Event.QUERY_PARAM_FIND_BY_EVENT_ID) })
public class Event implements Serializable {

	private static final long serialVersionUID = -8189224760313859993L;

	public static final String QUERY_FIND_BY_EVENT_ID = "findById";
	public static final String QUERY_PARAM_FIND_BY_EVENT_ID = "eventId";

	@Id
	@GeneratedValue
	private String id;

	@Column(unique = true, nullable = false)
	private String eventId;

	private String postingId;
	private String postingTitle;
	private List<String> serviceLocations;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ProductCategory> productCategories;

	@OneToOne(cascade = CascadeType.ALL)
	private OpportunityAmt opportunityAmt;

	private String responseDeadline;
	private String description;
	private String buyerName;
	private String buyerAnid;
	private String awardDate;
	private String startDate;
	private String referenceNumber;
	private URL discoveryUrl;
	private String statusCheckId;

	/**
	 * Default constructor
	 */
	public Event() {
		super();
	}

	/**
	 * Returns entity id
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns event id
	 *
	 * @return eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * Sets event id
	 * 
	 * @param eventId
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * Returns posting id
	 *
	 * @return postingId
	 */
	public String getPostingId() {
		return postingId;
	}

	/**
	 * Sets posting id
	 * 
	 * @param postingId
	 */
	public void setPostingId(String postingId) {
		this.postingId = postingId;
	}

	/**
	 * Returns posting title
	 *
	 * @return postingTitle
	 */
	public String getPostingTitle() {
		return postingTitle;
	}

	/**
	 * Sets posting title
	 * 
	 * @param postingTitle
	 */
	public void setPostingTitle(String postingTitle) {
		this.postingTitle = postingTitle;
	}

	/**
	 * Returns service locations
	 *
	 * @return serviceLocations
	 */
	public List<String> getServiceLocations() {
		return serviceLocations;
	}

	/**
	 * Sets service locations
	 * 
	 * @param serviceLocations
	 */
	public void setServiceLocations(List<String> serviceLocations) {
		this.serviceLocations = serviceLocations;
	}

	/**
	 * Returns product categories
	 *
	 * @return productCategories
	 */
	public List<ProductCategory> getProductCategories() {
		return productCategories;
	}

	/**
	 * Sets product categories
	 * 
	 * @param productCategories
	 */
	public void setProductCategories(List<ProductCategory> productCategories) {
		this.productCategories = productCategories;
	}

	/**
	 * Returns opportunity amount
	 *
	 * @return opportunityAmt
	 */
	public OpportunityAmt getOpportunityAmt() {
		return opportunityAmt;
	}

	/**
	 * Sets opportunity amount
	 * 
	 * @param opportunityAmt
	 */
	public void setOpportunityAmt(OpportunityAmt opportunityAmount) {
		this.opportunityAmt = opportunityAmount;
	}

	/**
	 * Returns response deadline
	 *
	 * @return responseDeadline
	 */
	public String getResponseDeadline() {
		return responseDeadline;
	}

	/**
	 * Sets response deadline
	 * 
	 * @param responseDeadline
	 */
	public void setResponseDeadline(String responseDeadline) {
		this.responseDeadline = responseDeadline;
	}

	/**
	 * Returns description
	 *
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets description
	 * 
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	/**
	 * Returns buyer ANID
	 *
	 * @return buyerANID
	 */
	public String getBuyerAnid() {
		return buyerAnid;
	}

	/**
	 * Sets buyer ANID
	 * 
	 * @param buyerAnid
	 */
	public void setBuyerAnid(String buyerAnid) {
		this.buyerAnid = buyerAnid;
	}

	/**
	 * Returns award date
	 *
	 * @return awardDate
	 */
	public String getAwardDate() {
		return awardDate;
	}

	/**
	 * Sets award date
	 * 
	 * @param awardDate
	 */
	public void setAwardDate(String awardDate) {
		this.awardDate = awardDate;
	}

	/**
	 * Returns start date
	 *
	 * @return startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * Sets start date
	 * 
	 * @param startDate
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * Returns reference number
	 *
	 * @return referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * Sets reference number
	 * 
	 * @param referenceNumber
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * Returns discovery URL
	 *
	 * @return discoveryURL
	 */
	public URL getDiscoveryUrl() {
		return discoveryUrl;
	}

	/**
	 * Sets discovery URL
	 * 
	 * @param discoveryUrl
	 */
	public void setDiscoveryUrl(URL discoveryUrl) {
		this.discoveryUrl = discoveryUrl;
	}

	public String getStatusCheckId() {
		return statusCheckId;
	}

	public void setStatusCheckId(String statusCheckId) {
		this.statusCheckId = statusCheckId;
	}

	@Override
	public String toString() {
		return "Event [eventId=" + eventId + ", postingId=" + postingId + ", postingTitle=" + postingTitle
				+ ", serviceLocations=" + serviceLocations + ", productCategories=" + productCategories
				+ ", opportunityAmt=" + opportunityAmt + ", responseDeadline=" + responseDeadline + ", description="
				+ description + ", buyerName=" + buyerName + ", buyerAnid=" + buyerAnid + ", awardDate=" + awardDate
				+ ", startDate=" + startDate + ", referenceNumber=" + referenceNumber + ", discoveryUrl=" + discoveryUrl
				+ ", statusCheckId=" + statusCheckId + "]";
	}
}