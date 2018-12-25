package com.station.stationapp.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import com.station.stationapp.resource.Station;
import com.station.stationapp.resource.StationRepository;

@RunWith(SpringRunner.class)
public class StationServiceTest {

	@Mock
	private static StationRepository stationRepository;

	@InjectMocks
	private static StationService stationService;

	@Test
	public void testFindAllStations() {
		List<Station> stationList = new ArrayList<Station>();

		Station station1 = new Station("ST1", "A", true, "CSA");
		Station station2 = new Station("ST2", "B", false, "CSB");
		Station station3 = new Station("ST3", "C", true, "CSC");
		Station station4 = new Station("ST4", "D", false, "CSD");
		Station station5 = new Station("ST5", "E", true, "CSE");
		Station station6 = new Station("ST6", "A", true, "CSAAA");
		stationList.add(station1);
		stationList.add(station2);
		stationList.add(station3);
		stationList.add(station4);
		stationList.add(station5);
		stationList.add(station6);

		Mockito.when(stationRepository.findAll()).thenReturn(stationList);

		stationService.findAll();
	}

	@Test
	public void testFindStationByID() {
		Station station = new Station("ST1", "A", true, "CSA");
		Mockito.when(stationRepository.findOne(Mockito.anyString())).thenReturn(station);
		stationService.findOne("ST1");
		Assert.assertEquals("ST1", station.getStationId());
	}

	@Test
	public void testFindStationByName() {
		List<Station> stationList = new ArrayList<Station>();
		Station station1 = new Station("ST1", "A", true, "CSA");
		Station station2 = new Station("ST6", "A", true, "CSAAA");
		stationList.add(station1);
		stationList.add(station2);
		Mockito.when(stationRepository.findByName(Mockito.anyString())).thenReturn(stationList);
		stationService.findByName("A");
		Assert.assertEquals(2, stationList.size());
	}

	@Test
	public void testHDEnabledStationsTest() {
		List<Station> stationList = new ArrayList<Station>();

		Station station1 = new Station("ST1", "A", true, "CSA");
		Station station2 = new Station("ST3", "C", true, "CSC");
		Station station3 = new Station("ST5", "E", true, "CSE");
		Station station4 = new Station("ST6", "A", true, "CSAAA");

		stationList.add(station1);
		stationList.add(station2);
		stationList.add(station3);
		stationList.add(station4);

		Mockito.when(stationRepository.findByHDEnable(true)).thenReturn(stationList);
		stationService.findByHDEnable(true);
		Assert.assertEquals(4, stationList.size());
	}

	@Test
	public void deleteStationTest() throws Exception {
		stationRepository.delete("ST1");
		verify(stationRepository).delete(any(String.class));
		stationService.delete("ST1");
	}

	@Test
	public void createStationTest() throws Exception {
		Station station = new Station("ST9", "A", true, "CS9999");
		Mockito.when(stationRepository.save(any(Station.class))).thenReturn(station);
		stationService.save(station);
		Assert.assertEquals("ST9", station.getStationId());
		Assert.assertEquals("A", station.getName());
	}

}
