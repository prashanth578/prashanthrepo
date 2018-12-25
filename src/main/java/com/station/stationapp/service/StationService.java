package com.station.stationapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.station.stationapp.resource.Station;
import com.station.stationapp.resource.StationRepository;

@Service
public class StationService {
	@Autowired
	private StationRepository stationRepository;

	public List<Station> findAll() {
		return stationRepository.findAll();
	}

	public Station findOne(String id) {
		return stationRepository.findOne(id);
	}

	public List<Station> findByName(String name) {
		return stationRepository.findByName(name);
	}

	public List<Station> findByHDEnable(boolean hdEnabled) {
		return stationRepository.findByHDEnable(hdEnabled);
	}

	public void delete(String id) {
		stationRepository.delete(id);
	}

	public Station save(Station station) {
		return stationRepository.save(station);
	}

}
