package com.sap.cloud.samples.ariba.discovery.rfx.dtos;

import java.util.Arrays;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Events DTO.
 *
 */
@XmlRootElement
public class EventsDto {

	private String siteId;
	private EventDto[] events;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public EventDto[] getEvents() {
		return events;
	}

	public void setEvents(EventDto[] events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return "Events [siteId=" + siteId + ", events=" + Arrays.toString(events) + "]";
	}

}
