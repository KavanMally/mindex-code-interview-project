package com.mindex.challenge.controller;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.service.CompensationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class CompensationController {

    private static final Logger LOG = LoggerFactory.getLogger(CompensationController.class);

    @Autowired
    private CompensationService compensationService;

    @GetMapping("/compensation/{employeeId}")
    public Compensation getCompensation(@PathVariable String employeeId){
        LOG.debug("Reading compenssation details for employee: [{}]", employeeId);
        return compensationService.getCompensation(employeeId);
    }

    @PostMapping("/compensation")
    public Compensation createCompensation(@RequestBody Compensation compensation){
        LOG.debug("Creating compenssation details with compensation details:: [{}]", compensation);
        return compensationService.createCompensation(compensation);
    }
}
