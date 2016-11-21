package io.woolford;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.woolford.database.entity.ExchangeRateRecord;
import io.woolford.database.mapper.DbMapper;
import org.hibernate.validator.internal.util.privilegedactions.GetMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Component
public class ExchangeRateLogger {

    static Logger logger = Logger.getLogger(ExchangeRateLogger.class.getName());

    @Autowired
    DbMapper dbMapper;

    @Value("${open.exchange.rates.base.url}")
    private String openExchangeRatesBaseUrl;

    @Value("${open.exchange.rates.app.id}")
    private String openExchangeRatesAppId;

    @Scheduled(cron="0 0 * * * *") // run every hour, at the top of the hour
    private void logExchangeRates() throws IOException {

        UriComponents uri = UriComponentsBuilder
                .fromHttpUrl(openExchangeRatesBaseUrl + "latest.json?app_id={app_id}")
                .buildAndExpand(openExchangeRatesAppId);

        String response = getResponse(uri.toUri().toURL());

        ObjectMapper mapper = new ObjectMapper();
        OerLatestRecord oerLatestRecord = mapper.readValue(response, OerLatestRecord.class);

        oerLatestRecord.getRates().forEach((k,v)->{
            ExchangeRateRecord exchangeRateRecord = new ExchangeRateRecord();
            exchangeRateRecord.setCurrency(k);
            exchangeRateRecord.setRate(v);

            dbMapper.insertExchangeRateRecord(exchangeRateRecord);
        });

    }

    private String getResponse(URL uri) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(uri.openStream()));
        String response = "";
        String line;
        while (null != (line = br.readLine())){
            response = response + "\n" + line;
        }
        return response;
    }

}
