package com.station.stationapp.resource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, String> {

	@Query("SELECT s FROM Station s WHERE s.name = ?1")
	public List<Station> findByName(@Param("name") String name);

	@Query("SELECT s FROM Station s WHERE s.hdEnabled = ?1")
	public List<Station> findByHDEnable(@Param("hdEnabled") boolean hdEnabled);

}
