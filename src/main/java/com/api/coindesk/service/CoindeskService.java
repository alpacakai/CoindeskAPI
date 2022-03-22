package com.api.coindesk.service;

import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.api.coindesk.entity.Coindesk;
import com.api.coindesk.entity.RespCoindesk;
import com.api.coindesk.entity.RespCoindesk.Detail;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class CoindeskService extends BaseService {

	@Autowired
	private ObjectMapper objectMapper;

	@Value( "${coindesk.url}" )
	private String coindeskUrl;

	public String getOrgData() {
		String result = null;
		try {
			RestTemplate restTemplate = new RestTemplate();
			result = restTemplate.getForObject( coindeskUrl, String.class );
		} catch ( Exception e ) {
			log.error( e.getMessage(), e );
		}
		return result;
	}

	public RespCoindesk getNewData() {
		RespCoindesk respCoindesk = new RespCoindesk();
		try {
			Coindesk coindesk = objectMapper.readValue( getOrgData(), Coindesk.class );
			log.info( coindesk.toString() );

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy/MM/dd HH:mm:ss" );
			ZonedDateTime updateTime = OffsetDateTime.parse( coindesk.getTime().getUpdatedISO() ).atZoneSameInstant( ZoneId.systemDefault() );
			respCoindesk.setUpdate( formatter.format( updateTime ) );

			coindesk.getBpi().forEach( ( k, v ) -> {
				Detail detail = respCoindesk.getDetails().computeIfAbsent( k, rk -> new Detail() );
				detail.setEn( v.getCode() );
				detail.setZh( currencyMap.getOrDefault( v.getCode(), v.getCode() ) );
				detail.setRate( v.getRate_float() );
			} );

			return respCoindesk;
		} catch ( Exception e ) {
			log.error( e.getMessage(), e );
		}
		return null;
	}

}
