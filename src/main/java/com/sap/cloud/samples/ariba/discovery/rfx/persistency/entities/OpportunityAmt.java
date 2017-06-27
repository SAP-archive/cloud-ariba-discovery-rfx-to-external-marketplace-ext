package com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

/**
 * Opportunity amount entity.
 *
 */
@Entity
public class OpportunityAmt implements Serializable {

	private static final long serialVersionUID = 4937823377025500097L;

	@Id
	@GeneratedValue
	private String id;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "LOWER_END_ID")
	private OpportunityAmtEnd lowerEnd;

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "UPPER_END_ID")
	private OpportunityAmtEnd upperEnd;

	/**
	 * Returns entity id
	 *
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * Returns lower end
	 *
	 * @return lowerEnd
	 */
	public OpportunityAmtEnd getLowerEnd() {
		return lowerEnd;
	}

	/**
	 * Sets lower end
	 * 
	 * @param lowerEnd
	 */
	public void setLowerEnd(OpportunityAmtEnd lowerEnd) {
		this.lowerEnd = lowerEnd;
	}

	/**
	 * Returns upper end
	 *
	 * @return upperEnd
	 */
	public OpportunityAmtEnd getUpperEnd() {
		return upperEnd;
	}

	/**
	 * Sets upper end
	 * 
	 * @param upperEnd
	 */
	public void setUpperEnd(OpportunityAmtEnd upperEnd) {
		this.upperEnd = upperEnd;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "OpportunityAmt [id=" + id + ", lowerEnd=" + lowerEnd + ", upperEnd=" + upperEnd + "]";
	}

}
