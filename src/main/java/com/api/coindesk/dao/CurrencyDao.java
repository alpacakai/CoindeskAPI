package com.api.coindesk.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.api.coindesk.entity.Currency;

@Repository
@Transactional
public interface CurrencyDao extends CrudRepository<Currency, String> {

	List<Currency> findAll();

}
