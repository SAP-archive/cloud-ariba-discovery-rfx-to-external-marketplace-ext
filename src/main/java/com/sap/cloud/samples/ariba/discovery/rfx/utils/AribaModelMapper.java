package com.sap.cloud.samples.ariba.discovery.rfx.utils;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;

import com.sap.cloud.samples.ariba.discovery.rfx.dtos.EventDto;
import com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities.Event;

/**
 * Model mapper from
 * {@link com.sap.cloud.samples.ariba.discovery.rfx.dtos.EventDto} to
 * {@link com.sap.cloud.samples.ariba.discovery.rfx.persistency.entities.Event}
 *
 */
public class AribaModelMapper {

	private static ModelMapper modelMapper;

	static {
		AribaModelMapper.modelMapper = new ModelMapper();
		AribaModelMapper.modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public static Event map(EventDto eventDto) {
		return modelMapper.map(eventDto, Event.class);
	}

	public static List<EventDto> map(List<Event> events) {
		return modelMapper.map(events, new TypeToken<List<EventDto>>() {
		}.getType());
	}
}
