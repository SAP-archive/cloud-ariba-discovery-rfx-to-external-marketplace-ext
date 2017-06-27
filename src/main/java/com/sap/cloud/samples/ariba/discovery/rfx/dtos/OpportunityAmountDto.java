package com.sap.cloud.samples.ariba.discovery.rfx.dtos;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Opportunity amount DTO.
 *
 */
@XmlRootElement
public class OpportunityAmountDto {

	private OpportunityAmtEndDto lowerEnd;
	private OpportunityAmtEndDto upperEnd;

	public OpportunityAmtEndDto getLowerEnd() {
		return lowerEnd;
	}

	public void setLowerEnd(OpportunityAmtEndDto lowerEnd) {
		this.lowerEnd = lowerEnd;
	}

	public OpportunityAmtEndDto getUpperEnd() {
		return upperEnd;
	}

	public void setUpperEnd(OpportunityAmtEndDto upperEnd) {
		this.upperEnd = upperEnd;
	}

}
