package com.station.stationapp.resource;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@Entity
@ApiModel(description = "All details about the Stations. ")
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public class Station implements Serializable {

	@Id
	@Column(name = "stationid")

	private String stationId;

	@Column(name = "name")

	private String name;

	@Column(name = "hdenabled")

	private Boolean hdEnabled;

	@Column(name = "callsign")

	private String callSign;

	public Station() {
		super();
	}

	public Station(String stationId, String name, Boolean hdEnabled, String callSign) {
		super();
		this.stationId = stationId;
		this.name = name;
		this.hdEnabled = hdEnabled;
		this.callSign = callSign;
	}

	public String getStationId() {
		return stationId;
	}

	public void setStationId(String stationId) {
		this.stationId = stationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getHdEnabled() {
		return hdEnabled;
	}

	public void setHdEnabled(Boolean hdEnabled) {
		this.hdEnabled = hdEnabled;
	}

	public String getCallSign() {
		return callSign;
	}

	public void setCallSign(String callSign) {
		this.callSign = callSign;
	}

}
