package com.arca.technicalaptitudetest.services;

import com.arca.technicalaptitudetest.beans.BorderDates;
import com.arca.technicalaptitudetest.beans.SearchDataQuery;
import com.arca.technicalaptitudetest.beans.TableCountryData;
import com.arca.technicalaptitudetest.enums.ErrorCodes;
import com.arca.technicalaptitudetest.enums.Granularity;
import com.arca.technicalaptitudetest.repositories.CountryDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class CountryDataService {

    private final CountryDataRepository countryDataRepository;

    @Transactional
    public BorderDates getBorderDates() {

        BorderDates borderDates = new BorderDates();

        borderDates.setStart(countryDataRepository.getMinDate().collect(Collectors.toList()).get(0));
        borderDates.setEnd(countryDataRepository.getMaxDate().collect(Collectors.toList()).get(0));

        return borderDates;
    }

    @Transactional
    public List<String> getAllCountries() {
        return countryDataRepository.findAllCountries().collect(Collectors.toList());
    }

    @Transactional
    public List<String> searchCountries(String q) {
        return countryDataRepository.searchCountries(q.toUpperCase()).collect(Collectors.toList());
    }


    public List<TableCountryData> getTableCountryData(SearchDataQuery searchDataQuery) {
        return new ArrayList<>(countryDataRepository.findTableData(searchDataQuery.getStart(), searchDataQuery.getEnd()));
    }

    @Transactional
    public List<Object[]> getChartCountryData(SearchDataQuery search) {
        if (search.getGranularity() == null) {
            search.setGranularity(Granularity.DAY);
        }

        if (search.getCountry() == null) {
            if (Granularity.DAY.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataWithAllCountriesByDay(search.getStart(), search.getEnd()).collect(Collectors.toList());
            } else if (Granularity.WEEK.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataWithAllCountriesByWeek(search.getStart(), search.getEnd()).collect(Collectors.toList());
            } else if (Granularity.MONTH.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataWithAllCountriesByMonth(search.getStart(), search.getEnd()).collect(Collectors.toList());
            } else if (Granularity.YEAR.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataWithAllCountriesByYear(search.getStart(), search.getEnd()).collect(Collectors.toList());
            }
        } else {
            if (Granularity.DAY.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataByCountryByDay(search.getStart(), search.getEnd(), search.getCountry()).collect(Collectors.toList());
            } else if (Granularity.WEEK.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataByCountryByWeek(search.getStart(), search.getEnd(), search.getCountry()).collect(Collectors.toList());
            } else if (Granularity.MONTH.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataByCountryByMonth(search.getStart(), search.getEnd(), search.getCountry()).collect(Collectors.toList());
            } else if (Granularity.YEAR.equals(search.getGranularity())) {
                return countryDataRepository.findChartCountryDataByCountryByYear(search.getStart(), search.getEnd(), search.getCountry()).collect(Collectors.toList());
            }
        }
        return new ArrayList<>();
    }

    public SearchDataQuery queryHandling(Date start, Date end) throws Exception {
        if (start == null || end == null) {
            throw new Exception(ErrorCodes.START_DATE_AND_END_DATE_ARE_MANDATORY.name());
        }
        if (end.before(start)) {
            throw new Exception(ErrorCodes.START_DATE_MUST_BE_BEFORE_END_DATE.name());
        }

        SearchDataQuery search = new SearchDataQuery();
        search.setStart(start);
        search.setEnd(end);
        return search;
    }
}
