package com.arca.technicalaptitudetest.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class TableCountryData {
    private long value;
    private String country;
}
