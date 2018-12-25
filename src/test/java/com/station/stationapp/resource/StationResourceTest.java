package com.station.stationapp.resource;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.station.stationapp.service.StationService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StationResource.class, secure = false)
public class StationResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private StationService stationService;

	@Test
	public void retrieveAllStationsTest() throws Exception {
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

		Mockito.when(stationService.findAll()).thenReturn(stationList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stations").accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{stationId:ST1,name:A,hdEnabled:true,callSign:CSA},{stationId:ST2,name:B,hdEnabled:false,callSign:CSB},{stationId:ST3,name:C,hdEnabled:true,callSign:CSC},{stationId:ST4,name:D,hdEnabled:false,callSign:CSD},{stationId:ST5,name:E,hdEnabled:true,callSign:CSE},{stationId:ST6,name:A,hdEnabled:true,callSign:CSAAA}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveDetailsForStationByStationIDTest() throws Exception {

		Station station = new Station("ST1", "A", true, "CSA");

		Mockito.when(stationService.findOne(Mockito.anyString())).thenReturn(station);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stations/id/ST1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "{stationId:ST1,name:A,hdEnabled:true,callSign:CSA}";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test(expected = org.springframework.web.util.NestedServletException.class)
	public void retrieveDetailsForStationByStationIDExceptionTest() throws Exception {

		Station station = null;
		Mockito.when(stationService.findOne(Mockito.anyString())).thenReturn(station);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stations/id/ST1")
				.accept(MediaType.APPLICATION_JSON);

		mockMvc.perform(requestBuilder);
	}

	@Test
	public void retrieveDetailsForStationByStationNameTest() throws Exception {

		List<Station> stationList = new ArrayList<Station>();

		Station station1 = new Station("ST1", "A", true, "CSA");
		Station station2 = new Station("ST6", "A", true, "CSAAA");
		stationList.add(station1);
		stationList.add(station2);

		Mockito.when(stationService.findByName(Mockito.anyString())).thenReturn(stationList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stations/name/A")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		String expected = "[{stationId:ST1,name:A,hdEnabled:true,callSign:CSA},{stationId:ST6,name:A,hdEnabled:true,callSign:CSAAA}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), false);
	}

	@Test
	public void retrieveHDEnabledStationsTest() throws Exception {

		List<Station> stationList = new ArrayList<Station>();

		Station station1 = new Station("ST1", "A", true, "CSA");
		Station station2 = new Station("ST3", "C", true, "CSC");
		Station station3 = new Station("ST5", "E", true, "CSE");
		Station station4 = new Station("ST6", "A", true, "CSAAA");

		stationList.add(station1);
		stationList.add(station2);
		stationList.add(station3);
		stationList.add(station4);

		Mockito.when(stationService.findByHDEnable(true)).thenReturn(stationList);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/stations/hdenabled/true")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse());

		System.out.println("response body is :" + result.getResponse().getContentAsString());

		String expected = "[{stationId:ST1,name:A,hdEnabled:true,callSign:CSA},{stationId:ST3,name:C,hdEnabled:true,callSign:CSC},{stationId:ST5,name:E,hdEnabled:true,callSign:CSE},{stationId:ST6,name:A,hdEnabled:true,callSign:CSAAA}]";

		JSONAssert.assertEquals(expected, result.getResponse().getContentAsString(), true);
	}

	@Test
	public void deleteStationTest() throws Exception {

		stationService.delete("ST1");

		verify(stationService).delete(any(String.class));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/stations/ST1")
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		System.out.println(result.getResponse().getStatus());

		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
	}

	@Test
	public void createStationTest() throws Exception {

		Station station = new Station("ST9", "A", true, "CS9999");

		Mockito.when(stationService.save(Mockito.any(Station.class))).thenReturn(station);

		String exampleJson = "{\"stationId\": \"ST9\",\"name\": \"A\",\"hdEnabled\": true,\"callSign\": \"CSA\"}";

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/stations").accept(MediaType.APPLICATION_JSON)
				.content(exampleJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());

		System.out.println("response.getHeader(HttpHeaders.LOCATION) is:" + response.getHeader(HttpHeaders.LOCATION));

		assertEquals("http://localhost/stations/ST9", response.getHeader(HttpHeaders.LOCATION));
	}

	@Test
	public void updateStationTest() throws Exception {

		Station station = new Station("ST1", "A", true, "CSA");

		Mockito.when(stationService.findOne(Mockito.anyString())).thenReturn(station);

		String exampleJson = "{\"stationId\": \"ST1\",\"name\": \"AAA\",\"hdEnabled\": true,\"callSign\": \"CSA\"}";

		// Send course as body to /students/Student1/courses
		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/stations/update/ST1")
				.accept(MediaType.APPLICATION_JSON).content(exampleJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result = mockMvc.perform(requestBuilder).andReturn();

		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());

	}

	@Test(expected = StationNotFoundException.class)
	public void StationNotFoundExceptionTest() throws Exception {
		Mockito.when(stationService.findOne(Mockito.anyString()))
				.thenThrow(new StationNotFoundException("Station was not found"));
	}

}
