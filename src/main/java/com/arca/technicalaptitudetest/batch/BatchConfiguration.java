package com.arca.technicalaptitudetest.batch;

import com.arca.technicalaptitudetest.beans.CountryDataRaw;
import com.arca.technicalaptitudetest.entities.CountryDataEntity;
import com.arca.technicalaptitudetest.repositories.CountryDataRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

@Configuration
@Slf4j
@Service
@AllArgsConstructor
@EnableBatchProcessing
public class BatchConfiguration {

    private final CountryDataRepository countryDataRepository;

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public FlatFileItemReader<CountryDataRaw> reader() {

        return new FlatFileItemReaderBuilder<CountryDataRaw>()
                .name("countryDataReader")
                .resource(new ClassPathResource("data.txt"))
                .delimited()
                .names(new String[]{"timestamp", "value", "country"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<CountryDataRaw>() {{
                    setTargetType(CountryDataRaw.class);
                }})
                .build();
    }

    @Bean
    public BatchProcessor processor() {
        return new BatchProcessor();
    }

    @Bean
    public ItemWriter<CountryDataEntity> writer() {
        return new BatchWriter(countryDataRepository);
    }

    @Bean
    public Job importDataJob(JobExecutionListener listener, Step step1) {
        return jobBuilderFactory.get("importCountryDataJob")
                //.preventRestart()
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1(ItemWriter<CountryDataEntity> writer) {
        return stepBuilderFactory.get("step1")
                .<CountryDataRaw, CountryDataEntity>chunk(10000)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }
}

