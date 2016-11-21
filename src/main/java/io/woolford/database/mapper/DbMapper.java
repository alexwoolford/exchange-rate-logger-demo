package io.woolford.database.mapper;


import io.woolford.database.entity.ExchangeRateRecord;
import org.apache.ibatis.annotations.Insert;

public interface DbMapper {

    @Insert("INSERT INTO oer.exchange_rate       " +
            "    (`currency`, `rate`)            " +
            "VALUES                              " +
            "    (#{currency}, #{rate})          " +
            "ON DUPLICATE KEY UPDATE rate=#{rate}")
    public void insertExchangeRateRecord(ExchangeRateRecord exchangeRateRecord);

}
