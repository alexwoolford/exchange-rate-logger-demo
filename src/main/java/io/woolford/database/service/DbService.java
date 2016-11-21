package io.woolford.database.service;

import io.woolford.database.entity.ExchangeRateRecord;
import io.woolford.database.mapper.DbMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DbService {

    @Autowired
    private DbMapper dbMapper;

    public void insertExchangeRateRecord(ExchangeRateRecord exchangeRateRecord){
        dbMapper.insertExchangeRateRecord(exchangeRateRecord);
    }

}
