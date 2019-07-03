package com.arca.technicalaptitudetest.beans;

import com.arca.technicalaptitudetest.enums.Granularity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class SearchDataQuery {
    private Date start;
    private Date end;

    // For Charts only
    private String country;
    private Granularity granularity;


}
