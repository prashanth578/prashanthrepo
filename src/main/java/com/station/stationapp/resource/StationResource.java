package com.station.stationapp.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.station.stationapp.service.StationService;

import io.swagger.annotations.ApiOperation;

@RestController
public class StationResource {

	private static Log log = LogFactory.getLog(StationResource.class);

	@Autowired
	private StationService stationService;

	@GetMapping("/stations")
	public List<Station> retrieveAllStations() {
		log.info("retrieveAllStations");
		List<Station> list = stationService.findAll();
		System.out.println(list);
		return list;
	}

	@GetMapping("/stations/id/{id}")
	@ApiOperation(value = "Find Station by id", notes = "Also returns a link to retrieve all Stations with rel - all-Stations")
	public Resource<Station> retrieveStationById(@PathVariable String id) {
		log.info("retrieveStation" + id);
		Station station = stationService.findOne(id);

		if (station == null)
			throw new StationNotFoundException("id-" + id);

		Resource<Station> resource = new Resource<Station>(station);

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllStations());

		resource.add(linkTo.withRel("all-Stations"));

		return resource;
	}

	@GetMapping("/stations/name/{name}")
	@ApiOperation(value = "Find Station by name")
	public List<Station> retrieveStationByName(@PathVariable String name) {
		log.info("retrieveStation" + name);

		List<Station> stationList = stationService.findByName(name);

		if (stationList == null || stationList.size() == 0 || stationList.isEmpty())
			throw new StationNotFoundException("name-" + name);

		return stationList;
	}

	@GetMapping("/stations/hdenabled/{hdEnabled}")
	@ApiOperation(value = "Find Station by name HDEnable")
	public List<Station> retrieveHDEnabledStations(@PathVariable String hdEnabled) {
		log.info("retrieveStation" + hdEnabled);

		boolean enable = Boolean.parseBoolean(hdEnabled);

		List<Station> stationList = stationService.findByHDEnable(enable);

		if (stationList == null || stationList.size() == 0 || stationList.isEmpty())
			throw new StationNotFoundException("enable-" + enable);

		return stationList;
	}

	@DeleteMapping("/stations/{id}")
	public void deleteStation(@PathVariable String id) {
		log.info("deleteStation");
		stationService.delete(id);
	}

	@PostMapping("/stations")
	public ResponseEntity<Object> createStation(@RequestBody Station station) {
		log.info("createStation");
		Station savedVehicle = stationService.save(station);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedVehicle.getStationId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/stations/update/{id}")
	public ResponseEntity<Station> updateStation(@RequestBody Station station, @PathVariable String id) {
		log.info("updateStation");

		Station station1 = stationService.findOne(id);

		log.info("Found the Station1" + station);

		if (station1 == null)
			return ResponseEntity.notFound().build();

		station.setStationId(id);

		log.info("Before >>>>>>>>>>> station1.getStationId() value is:" + station.getStationId());

		stationService.save(station);

		log.info("After >>>>>>>>>>> station1.getStationId() value is:" + station.getStationId());

		return new ResponseEntity<Station>(HttpStatus.OK);
	}

}
