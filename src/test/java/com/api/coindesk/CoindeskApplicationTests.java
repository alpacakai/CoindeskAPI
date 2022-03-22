package com.api.coindesk;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.coindesk.entity.Currency;
import com.api.coindesk.service.CurrencyService;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder( OrderAnnotation.class )
class CoindeskApplicationTests {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private CurrencyService currencyService;

	@Value( "${coindesk.url}" )
	private String coindeskUrl;

	public static HttpHeaders httpHeaders;

	@BeforeEach
	void init() {
		httpHeaders = new HttpHeaders();
		httpHeaders.add( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE );
	}

	// 1. 測試呼叫查詢幣別對應表資料 API，並顯示其內容。
	@Test
	@SneakyThrows
	@Order( 1 )
	void testQuery() {
		// 讓 testQuery 查的到資料, 以及後面的 testNewCoindeskApi 有資料可以轉換
		currencyService.insert( new Currency( "USD", "美元" ) );
		
		String findAll = mockMvc.perform( get( "/currency" ).headers( httpHeaders ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.status" ).value( "SUCCESS" ) )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Test - find all currency : {}", findAll );

		String findUSD = mockMvc.perform( get( "/currency/USD" ).headers( httpHeaders ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.status" ).value( "SUCCESS" ) )
				.andExpect( jsonPath( "$.data.en" ).value( "USD" ) )
				.andExpect( jsonPath( "$.data.zh" ).value( "美元" ) )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Test - find currency USD : {}", findUSD );
	}

	// 2. 測試呼叫新增幣別對應表資料 API。
	@Test
	@SneakyThrows
	@Order( 2 )
	void testInsert() {
		Currency currency = new Currency( "TWD", "台幣" );
		String request = objectMapper.writeValueAsString( currency );

		String insert = mockMvc.perform( post( "/currency" ).headers( httpHeaders ).content( request ) )
				.andExpect( status().isCreated() )
				.andExpect( jsonPath( "$.status" ).value( "SUCCESS" ) )
				.andExpect( jsonPath( "$.data.en" ).value( currency.getEn() ) )
				.andExpect( jsonPath( "$.data.zh" ).value( currency.getZh() ) )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Test - insert currency TWD : {}", insert );
	}

	// 3. 測試呼叫更新幣別對應表資料 API，並顯示其內容。
	@Test
	@SneakyThrows
	@Order( 3 )
	void testUpdate() {
		Currency currency = new Currency( "TWD", "新台幣" );
		String request = objectMapper.writeValueAsString( currency );

		String update = mockMvc.perform( put( "/currency" ).headers( httpHeaders ).content( request ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.status" ).value( "SUCCESS" ) )
				.andExpect( jsonPath( "$.data.en" ).value( currency.getEn() ) )
				.andExpect( jsonPath( "$.data.zh" ).value( currency.getZh() ) )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Test - update currency TWD : {}", update );
	}

	// 4. 測試呼叫刪除幣別對應表資料 API。
	@Test
	@SneakyThrows
	@Order( 4 )
	void testDelete() {
		String delete = mockMvc.perform( delete( "/currency/TWD" ).headers( httpHeaders ) )
				.andExpect( status().isNoContent() )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Test - delete currency TWD : {}", delete );
	}

	// 5. 測試呼叫 coindesk API，並顯示其內容。
	@Test
	@SneakyThrows
	@Order( 5 )
	void testOrgCoindeskApi() {
		String orgApi = mockMvc.perform( get( "/coindesk/orgApi" ).headers( httpHeaders ) )
				.andExpect( status().isOk() )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "Original Coindesk API : {}", orgApi );
	}

	// 6. 測試呼叫資料轉換的 API，並顯示其內容。
	@Test
	@SneakyThrows
	@Order( 6 )
	void testNewCoindeskApi() {
		String newApi = mockMvc.perform( get( "/coindesk/newApi" ).headers( httpHeaders ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.status" ).value( "SUCCESS" ) )
				.andReturn().getResponse().getContentAsString( StandardCharsets.UTF_8 );
		log.info( "New Coindesk API : {}", newApi );
	}

}
