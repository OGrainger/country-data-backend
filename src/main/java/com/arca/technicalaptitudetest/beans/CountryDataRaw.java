package com.arca.technicalaptitudetest.beans;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Getter
@Setter
@NoArgsConstructor
public class CountryDataRaw {
    private Integer value;
    private String country;
    private Long timestamp;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("value", this.value)
                .append("country", this.country)
                .append("timestamp", this.timestamp)
                .toString();
    }
}
