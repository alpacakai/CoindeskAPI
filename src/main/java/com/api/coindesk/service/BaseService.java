package com.api.coindesk.service;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.api.coindesk.dao.CurrencyDao;
import com.api.coindesk.entity.Currency;

@Transactional
public class BaseService {
	public static Map<String, String> currencyMap = new HashMap<>();

	@Autowired
	private CurrencyDao currencyDao;

	@PostConstruct
	private void init() {
		currencyMap = currencyDao.findAll()
				.stream()
				.collect( Collectors.toMap( Currency::getEn, Currency::getZh ) );
	}

}
