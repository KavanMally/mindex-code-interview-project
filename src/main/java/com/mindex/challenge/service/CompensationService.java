package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;

public interface CompensationService {
    Compensation getCompensation(String employeeId);
    Compensation createCompensation(Compensation compensation);
}
