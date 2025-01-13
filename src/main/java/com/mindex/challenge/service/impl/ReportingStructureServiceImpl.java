package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeService employeeService;


    @Override
    public ReportingStructure getNumberOfReports(String employeeIdInput) {

        LOG.debug("reached getNumberOfReports(..) method");

        Employee rootEmployee = employeeService.read(employeeIdInput);

        int numberOfReports = 0;

        if(isDirectReportsEmptyOrNull(rootEmployee))
            return generateReportingStructure(rootEmployee, numberOfReports);

        ArrayList<Employee> directReportees = new ArrayList<>(rootEmployee.getDirectReports());
        ArrayList<Employee> nestedReportees = new ArrayList<>();


        while(!directReportees.isEmpty()){

            //update counter
            numberOfReports = numberOfReports + directReportees.size();

            //move employees with reportees to own list
            for(Employee directReporteeEmployee: directReportees){

                Employee directReporteeEmployeeFullInfo = employeeService.read(directReporteeEmployee.getEmployeeId());

                if(!isDirectReportsEmptyOrNull(directReporteeEmployeeFullInfo))
                    nestedReportees.addAll(directReporteeEmployeeFullInfo.getDirectReports());
            }

            //clear list and add employees with reportees back to list
            directReportees.clear();
            if(!nestedReportees.isEmpty()){
                directReportees.addAll(nestedReportees);
                nestedReportees.clear();
            }

        }


        return generateReportingStructure(rootEmployee, numberOfReports);
    }

    private Boolean isDirectReportsEmptyOrNull(Employee employee){
        return employee.getDirectReports() == null || employee.getDirectReports().isEmpty();
    }

    private ReportingStructure generateReportingStructure(Employee employee, Integer reportCounter){
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setNumberOfReports(reportCounter);
        reportingStructure.setEmployee(employee.getEmployeeId());

        return reportingStructure;
    }

}