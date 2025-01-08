package com.onepieceofjava.JoshuaEmployeeRestApiWithService.integrationTesting;


import com.onepieceofjava.JoshuaEmployeeRestApiWithService.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.service.AssetService;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.service.EmployeeService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;


import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class DatabaseIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private AssetService assetService;

    private Employee testEmployee;

    private Asset testAsset;

    @BeforeEach
    void setUp(){
        testEmployee = new Employee(1L, "Joshua", "IT");
        testAsset = new Asset(1L, "Lenovo","Legion","Developer Laptop","LNV54321");
    }

    //Employee
    @Test
    void shouldSavedAndRetrieveEmployee(){
        //saved
        Employee savedEmployee = employeeService.addEmployee(testEmployee);
        assertNotNull(savedEmployee.getId());

        //retrieved
        Optional<Employee> retrievedEmployee = employeeService.getEmployeeById(savedEmployee.getId());
        assertTrue(retrievedEmployee.isPresent());
        assertEquals("Joshua", retrievedEmployee.get().getName());
        assertEquals("IT", retrievedEmployee.get().getDept());

    }

    //READ
    @Test
    void shouldGetAllEmployees(){
        //saved
        employeeService.addEmployee(testEmployee);
        employeeService.addEmployee(new Employee(null,"Nan", "Security"));

        //retrieved
        List<Employee> employeeList = employeeService.getAllEmployee();
        assertFalse(employeeList.isEmpty());
        assertTrue(employeeList.size() >=2);

    }


    //UPDATE
    @Test
    void shouldUdpateEmployee(){
        //save
        Employee savedEmployee = employeeService.addEmployee(testEmployee);

        //update
        savedEmployee.setDept("HR");
        Employee updatedEmployee = employeeService.updatedEmployee(savedEmployee.getId(),savedEmployee);

        //verify
        assertEquals("HR", updatedEmployee.getDept());
        Optional<Employee> retrievedEmployee = employeeService.getEmployeeById(savedEmployee.getId());
        assertTrue(retrievedEmployee.isPresent());
        assertEquals("HR", retrievedEmployee.get().getDept());
    }

    //DELETE
    @Test
    void shouldDeleteEmployeeById(){

        //saved
        Employee savedEmployee = employeeService.addEmployee(testEmployee);

        //delete
        employeeService.deleteEmployeeById(savedEmployee.getId());

        //verify
        Optional<Employee> retrievedEmployee = employeeService.getEmployeeById(savedEmployee.getId());
        assertFalse(retrievedEmployee.isPresent()," Employee should not exist after deletion");
    }

}
