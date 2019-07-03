package com.arca.technicalaptitudetest.repositories;

import com.arca.technicalaptitudetest.beans.TableCountryData;
import com.arca.technicalaptitudetest.entities.CountryDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface CountryDataRepository extends CrudRepository<CountryDataEntity, Long> {

    @Query(value = "select min(date) from country_data", nativeQuery = true)
    Stream<Date> getMinDate();

    @Query(value = "select max(date) from country_data", nativeQuery = true)
    Stream<Date> getMaxDate();

    @Query(value = "SELECT distinct c.country as country from country_data c group by country order by country", nativeQuery = true)
    Stream<String> findAllCountries();

    @Query(value = "SELECT distinct c.country as country from country_data c where upper(c.country) like ?1 group by country order by country limit 5", nativeQuery = true)
    Stream<String> searchCountries(String q);

    @Query("SELECT distinct new com.arca.technicalaptitudetest.beans.TableCountryData(sum(c.value), country) " +
            "from com.arca.technicalaptitudetest.entities.CountryDataEntity c " +
            "where c.date between :start and :end group by c.country order by c.country")
    Set<TableCountryData> findTableData(@Param("start") Date start, @Param("end") Date end);


    @Query(value = "SELECT date_trunc('DAY', c.date), sum(c.value) from country_data c where c.date between :start and :end group by date_trunc('DAY', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataWithAllCountriesByDay(@Param("start") Date start, @Param("end") Date end);
    @Query(value = "SELECT date_trunc('WEEK', c.date), sum(c.value) from country_data c where c.date between :start and :end group by date_trunc('WEEK', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataWithAllCountriesByWeek(@Param("start") Date start, @Param("end") Date end);
    @Query(value = "SELECT date_trunc('MONTH', c.date), sum(c.value) from country_data c where c.date between :start and :end group by date_trunc('MONTH', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataWithAllCountriesByMonth(@Param("start") Date start, @Param("end") Date end);
    @Query(value = "SELECT date_trunc('YEAR', c.date), sum(c.value) from country_data c where c.date between :start and :end group by date_trunc('YEAR', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataWithAllCountriesByYear(@Param("start") Date start, @Param("end") Date end);

    @Query(value = "SELECT date_trunc('DAY', c.date), sum(c.value) from country_data c where c.country=:country and c.date between :start and :end group by date_trunc('DAY', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataByCountryByDay(@Param("start") Date start, @Param("end") Date end, @Param("country") String country);
    @Query(value = "SELECT date_trunc('WEEK', c.date), sum(c.value) from country_data c where c.country=:country and c.date between :start and :end group by date_trunc('WEEK', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataByCountryByWeek(@Param("start") Date start, @Param("end") Date end, @Param("country") String country);
    @Query(value = "SELECT date_trunc('MONTH', c.date), sum(c.value) from country_data c where c.country=:country and c.date between :start and :end group by date_trunc('MONTH', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataByCountryByMonth(@Param("start") Date start, @Param("end") Date end, @Param("country") String country);
    @Query(value = "SELECT date_trunc('YEAR', c.date), sum(c.value) from country_data c where c.country=:country and c.date between :start and :end group by date_trunc('YEAR', c.date)", nativeQuery = true)
    Stream<Object[]> findChartCountryDataByCountryByYear(@Param("start") Date start, @Param("end") Date end, @Param("country") String country);

}

