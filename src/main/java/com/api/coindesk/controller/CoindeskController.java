package com.api.coindesk.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.coindesk.service.CoindeskService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping( value = "/coindesk", produces = MediaType.APPLICATION_JSON_VALUE )
public class CoindeskController extends BaseController {

	@Autowired
	CoindeskService coindeskService;

	@GetMapping( "/orgApi" )
	@ResponseBody
	public String getOrgApi() {
		log.info( "Call Original Coindesk API" );
		return coindeskService.getOrgData();
	}

	@GetMapping( "/newApi" )
	@ResponseBody
	public String getNewApi() {
		log.info( "Call New Coindesk API" );
		return response( coindeskService.getNewData() );
	}

}
