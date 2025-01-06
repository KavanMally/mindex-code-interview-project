package com.mindex.challenge.data;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Compensation {

    BigDecimal salary;
    String effectiveDate;

    String employeeId;

}
