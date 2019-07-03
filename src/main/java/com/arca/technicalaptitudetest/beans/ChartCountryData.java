package com.arca.technicalaptitudetest.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class ChartCountryData {
    private long value;
    private Date date;
}
