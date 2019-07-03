package com.arca.technicalaptitudetest.beans;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class BorderDates {
    private Date start;
    private Date end;
}
