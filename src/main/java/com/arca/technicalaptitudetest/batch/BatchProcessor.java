package com.arca.technicalaptitudetest.batch;

import com.arca.technicalaptitudetest.beans.CountryDataRaw;
import com.arca.technicalaptitudetest.entities.CountryDataEntity;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class BatchProcessor implements ItemProcessor<CountryDataRaw, CountryDataEntity> {

    private static final Logger log = LoggerFactory.getLogger(CountryDataRaw.class);

    @Override
    public CountryDataEntity process(final CountryDataRaw countryDataRaw) throws Exception {


        final Date date = new Date(countryDataRaw.getTimestamp());
        final Integer value = countryDataRaw.getValue();
        final String country = countryDataRaw.getCountry();

        log.info(String.format("Converting (%s)", countryDataRaw));
        return new CountryDataEntity(value, country, date);
    }

}