package com.arca.technicalaptitudetest.controllers;

import com.arca.technicalaptitudetest.beans.BorderDates;
import com.arca.technicalaptitudetest.beans.SearchDataQuery;
import com.arca.technicalaptitudetest.beans.TableCountryData;
import com.arca.technicalaptitudetest.enums.Granularity;
import com.arca.technicalaptitudetest.services.CountryDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:8081")
public class CountryDataRestController {

    private final CountryDataService countryDataService;

    @RequestMapping("/dates")
    public ResponseEntity<BorderDates> getBorderDates() {
        return new ResponseEntity<>(countryDataService.getBorderDates(), HttpStatus.OK);
    }

    @RequestMapping("/countries/get")
    public ResponseEntity<List<String>> getCountries() {
        return new ResponseEntity<>(countryDataService.getAllCountries(), HttpStatus.OK);
    }

    @RequestMapping("/countries/search")
    public ResponseEntity<List<String>> searchCountries(@Param("q") @NotNull String q) {
        return new ResponseEntity<>(countryDataService.searchCountries(q), HttpStatus.OK);
    }

    @RequestMapping("/data/table")
    public ResponseEntity<List<TableCountryData>> getTableData(
            @Param("start") @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @Param("end") @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end) throws Exception {

        SearchDataQuery search = countryDataService.queryHandling(start, end);
        return new ResponseEntity<>(countryDataService.getTableCountryData(search), HttpStatus.OK);

    }

    @RequestMapping("/data/chart")
    public ResponseEntity<List<Object[]>> getChartData(
            @Param("start") @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date start,
            @Param("end") @Valid @NotNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date end,
            @Param("granularity") @Valid Granularity granularity,
            @Param("country") String country) throws Exception {

        SearchDataQuery search = countryDataService.queryHandling(start, end);
        search.setCountry(country);
        search.setGranularity(granularity);

        return new ResponseEntity<>(countryDataService.getChartCountryData(search), HttpStatus.OK);

    }
}
