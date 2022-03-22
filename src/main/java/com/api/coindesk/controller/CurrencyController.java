package com.api.coindesk.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.api.coindesk.entity.Currency;
import com.api.coindesk.service.CurrencyService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping( value = "/currency", produces = MediaType.APPLICATION_JSON_VALUE )
public class CurrencyController extends BaseController {

	@Autowired
	CurrencyService currencyService;

	@GetMapping
	public ResponseEntity<String> findAll() {
		log.info( "findAll" );
		return ResponseEntity.ok( response( currencyService.findAll() ) );
	}

	@GetMapping( "/{en}" )
	public ResponseEntity<String> findById( @PathVariable String en ) {
		log.info( "findById : {}", en );
		return ResponseEntity.ok( response( currencyService.findById( en ) ) );
	}

	@PostMapping
	public ResponseEntity<String> insert( @Valid @RequestBody Currency request ) {
		log.info( "insert : {}", request );
		Currency currency = currencyService.insert( request );
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path( "/{en}" ).buildAndExpand( currency.getEn() ).toUri();
		return ResponseEntity.created( location ).body( response( currency ) );
	}

	@PutMapping
	public ResponseEntity<String> update( @Valid @RequestBody Currency request ) {
		log.info( "update : {}", request );
		return ResponseEntity.ok( response( currencyService.update( request ) ) );
	}

	@DeleteMapping( "/{en}" )
	public ResponseEntity<String> delete( @PathVariable( "en" ) String en ) {
		log.info( "delete : {}", en );
		currencyService.delete( en );
		return ResponseEntity.noContent().build();
	}
}
