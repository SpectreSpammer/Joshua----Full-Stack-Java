package com.onepieceofjava.JoshuaEmployeeRestApi.serviceTest;


import com.onepieceofjava.JoshuaEmployeeRestApi.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApi.repository.EmployeeRepository;
import com.onepieceofjava.JoshuaEmployeeRestApi.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee testEmployee;

    private Employee updateEmployee;

    @BeforeEach
    void setUp(){
        testEmployee = new Employee(1L, "Joshua", "IT");
        updateEmployee = new Employee(1L, "Joshua Dim - updated", "Head of IT");
    }


    //methodName_expectation
    //GET ALl
    @Test
    void getAllEmployee_ShouldReturnAllEmployees(){

        //arrange
        List<Employee> expectListOfEmployees = Arrays.asList(
               testEmployee,
                new Employee(2L, "Nan", "Security")
        );
        when(employeeRepository.findAll()).thenReturn(expectListOfEmployees);

        //act
        List<Employee> actualEmployee = employeeService.getAllEmployee();

        //assert
        assertEquals(expectListOfEmployees, actualEmployee);
    }

    //GET By Id
    @Test
    void getEmployeeById_ShouldReturnEmployeeById_WhenEmployeeIsExist(){

        //arrange
        when(employeeRepository.findById(5L)).thenReturn(Optional.of(testEmployee));

        //act
        Optional<Employee> result = employeeService.getEmployeeById(5L);

        //assert
        assertTrue(result.isPresent());
        assertEquals(testEmployee.getName(), result.get().getName());
    }

    //ADD
    @Test
    void addEmployee_ShouldReturnSavedEmployee(){

        //arrange
        when(employeeRepository.save(testEmployee)).thenReturn(testEmployee);

        //act
        Employee savedEmployee = employeeService.addEmployee(testEmployee);

        //assert
        assertNotNull(savedEmployee);
        assertEquals(testEmployee.getName(), savedEmployee.getName());
        verify(employeeRepository).save(testEmployee);
    }

    //update - if exist
    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenEmployeeIsExist(){

        //arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);
        when(employeeRepository.save(updateEmployee)).thenReturn(updateEmployee);

        //act
        Employee result = employeeService.updatedEmployee(1L,updateEmployee);

        //assert
        assertNotNull(result);
        assertEquals(updateEmployee.getName(), result.getName());
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository).save(updateEmployee);

    }

    //update - if not exist
    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenEmployeeDoesNotExist(){

        //arrange
        when(employeeRepository.existsById(1L)).thenReturn(false);


        //act
        Employee result = employeeService.updatedEmployee(1L,updateEmployee);

        //assert
        assertNull(result);
        verify(employeeRepository).existsById(1L);
        verify(employeeRepository,never()).save(updateEmployee);

    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenEmployeeIsExist(){

        //arrange
        Long employeeId = 1L;

        //act
        employeeService.deleteEmployeeById(employeeId);

        //assert
        verify(employeeRepository).deleteById(employeeId);

    }

}
