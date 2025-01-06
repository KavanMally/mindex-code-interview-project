package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompensationServiceImpl implements CompensationService {

    @Autowired
    private CompensationRepository compensationRepository;

    private static final Logger LOG = LoggerFactory.getLogger(CompensationServiceImpl.class);


    @Override
    public Compensation getCompensation(String employeeId) {

        LOG.debug("Reading compensation for |{}|", employeeId);

        Compensation compensation = compensationRepository.findByEmployeeId(employeeId);

        if (compensation == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        return compensation;
    }

    @Override
    public Compensation createCompensation(Compensation compensation) {

        LOG.debug("Creating Compensation: [{}]", compensation);
        compensationRepository.insert(compensation);
        return compensation;

    }
}
