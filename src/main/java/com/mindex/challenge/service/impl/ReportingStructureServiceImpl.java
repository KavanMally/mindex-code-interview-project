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

        ArrayList<Employee> directReportees = new ArrayList<>(rootEmployee.getDirectReports());
        ArrayList<Employee> nestedReportees = new ArrayList<>();


        if(rootEmployee.getDirectReports() == null || rootEmployee.getDirectReports().isEmpty())
            return generateReportingStructure(rootEmployee, numberOfReports);


        while(!directReportees.isEmpty()){

            //update counter
            numberOfReports = numberOfReports + directReportees.size();

            //move employees with reportees to own list
            for(Employee directReporteeEmployee: directReportees){

                Employee directReporteeEmployeeFullInfo = employeeService.read(directReporteeEmployee.getEmployeeId());

                if(directReporteeEmployeeFullInfo.getDirectReports() != null && !directReporteeEmployeeFullInfo.getDirectReports().isEmpty())
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

    private ReportingStructure generateReportingStructure(Employee employee, Integer reportCounter){
        ReportingStructure reportingStructure = new ReportingStructure();
        reportingStructure.setNumberOfReports(reportCounter);
        reportingStructure.setEmployee(employee.getEmployeeId());

        return reportingStructure;
    }

    private Boolean doesEmployeeContainReportees(Employee employee){
        return null != employee.getDirectReports() && !employee.getDirectReports().isEmpty();
    }

}