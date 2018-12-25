package com.station.stationapp.resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class StationNotFoundException extends RuntimeException {

	private static Log log = LogFactory.getLog(StationNotFoundException.class);

	public StationNotFoundException(String exception) {
		super(exception);
	}
}
