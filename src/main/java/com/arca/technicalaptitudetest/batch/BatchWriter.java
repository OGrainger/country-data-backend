package com.arca.technicalaptitudetest.batch;

import com.arca.technicalaptitudetest.entities.CountryDataEntity;
import com.arca.technicalaptitudetest.repositories.CountryDataRepository;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class BatchWriter implements ItemWriter<CountryDataEntity> {
    private final CountryDataRepository countryDataRepository;

    BatchWriter(CountryDataRepository countryDataRepository) {
        this.countryDataRepository = countryDataRepository;
    }

    @Override
    public void write(List<? extends CountryDataEntity> countryDataEntities) {
        countryDataRepository.saveAll(countryDataEntities);
    }
}
