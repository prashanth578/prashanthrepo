package com.station.stationapp.resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.swagger.annotations.ApiOperation;

@RestController
public class VehicleResource {

	@Autowired
	private VehicleRepository vehicleRepository;

	@GetMapping("/Vehicles")
	public List<Vehicle> retrieveAllVehicles() {
		System.out.println("test");
		return vehicleRepository.findAll();
	}

	@GetMapping("/Vehicles/{id}")
	@ApiOperation(value = "Find Vehicle by id", notes = "Also returns a link to retrieve all Vehicles with rel - all-Vehicles")
	public Resource<Vehicle> retrieveVehicle(@PathVariable long id) {
		Vehicle Vehicle = vehicleRepository.findOne(id);

		if (Vehicle == null)
			throw new VehicleNotFoundException("id-" + id);

		Resource<Vehicle> resource = new Resource<Vehicle>(Vehicle);

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllVehicles());

		resource.add(linkTo.withRel("all-Vehicles"));

		return resource;
	}

	@DeleteMapping("/Vehicles/{id}")
	public void deleteVehicle(@PathVariable long id) {
		vehicleRepository.delete(id);
	}

	@PostMapping("/Vehicles")
	public ResponseEntity<Object> createVehicle(@RequestBody Vehicle Vehicle) {
		Vehicle savedVehicle = vehicleRepository.save(Vehicle);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedVehicle.getId()).toUri();

		return ResponseEntity.created(location).build();

	}

	@PutMapping("/Vehicles/{id}")
	public ResponseEntity<Object> updateVehicle(@RequestBody Vehicle Vehicle, @PathVariable long id) {

		Vehicle v = vehicleRepository.findOne(id);

		if (v != null)
			return ResponseEntity.notFound().build();

		v.setId(id);

		vehicleRepository.save(v);

		return ResponseEntity.noContent().build();
	}
}
