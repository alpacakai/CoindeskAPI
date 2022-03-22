package com.api.coindesk.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.coindesk.dao.CurrencyDao;
import com.api.coindesk.entity.Currency;
import com.api.coindesk.exception.DuplicateException;
import com.api.coindesk.exception.NotExistException;
import com.api.coindesk.exception.NotFoundException;

@Service
public class CurrencyService extends BaseService{

	@Autowired
	CurrencyDao currencyDao;
	
	@Autowired
	CoindeskService coindeskService;

	public List<Currency> findAll() {
		return currencyDao.findAll();
	}

	public Currency findById( String en ) {
		return currencyDao.findById( en ).orElseThrow( () -> new NotFoundException( "Can't find product." ) );
	}

	public Currency insert( Currency currency ) {
		if ( currencyDao.existsById( currency.getEn() ) )
			throw new DuplicateException( currency.getEn() );
		Currency result = currencyDao.save( currency );
		if ( StringUtils.isNoneBlank( result.getEn(), result.getZh() ) )
			currencyMap.put( result.getEn(), result.getZh() );
		return result;
	}

	public Currency update( Currency currency ) {
		if ( !currencyDao.existsById( currency.getEn() ) )
			throw new NotExistException( currency.getEn() );
		Currency result = currencyDao.save( currency );
		if ( StringUtils.isNoneBlank( result.getEn(), result.getZh() ) )
			currencyMap.put( result.getEn(), result.getZh() );
		return result;
	}

	public void delete( String en ) {
		currencyDao.deleteById( en );
		currencyMap.remove( en );
	}
}
