package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {

    private String reportingStructureUrl;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        reportingStructureUrl = "http://localhost:" + port + "/reportingStructure/{id}";

    }

    @Test
    public void testReportingStructureRootEmployee(){
        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setEmployee("16a596ae-edd3-4847-99fe-c4518e82c86f");
        testReportingStructure.setNumberOfReports(4);

        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testReportingStructure.getEmployee()).getBody();

        assertNotNull(createdReportingStructure.getEmployee());
        assertReportingStructureEquivalence(createdReportingStructure, testReportingStructure);
    }

    @Test
    public void testReportingStructure0Reportees(){
        ReportingStructure testReportingStructure = new ReportingStructure();
        testReportingStructure.setEmployee("b7839309-3348-463b-a7e3-5de1c168beb3");
        testReportingStructure.setNumberOfReports(0);

        ReportingStructure createdReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, testReportingStructure.getEmployee()).getBody();

        assertNotNull(createdReportingStructure.getEmployee());
        assertReportingStructureEquivalence(createdReportingStructure, testReportingStructure);
    }

    private static void assertReportingStructureEquivalence(ReportingStructure expected, ReportingStructure actual){
        assertEquals(expected.getNumberOfReports(), actual.getNumberOfReports());
        assertEquals(expected.getEmployee(), actual.getEmployee());
    }
}
