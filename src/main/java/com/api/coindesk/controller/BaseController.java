package com.api.coindesk.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BaseController {

	@Autowired
	private ObjectMapper objectMapper;

	public String response( Object data ) {
		Map<String, Object> map = new HashMap<>();
		map.put( "status", "SUCCESS" );
		if ( data != null )
			map.put( "data", data );
		try {
			return objectMapper.writeValueAsString( map );
		} catch ( JsonProcessingException e ) {
			log.error( e.getMessage() );
		}
		return map.toString();
	}

}
