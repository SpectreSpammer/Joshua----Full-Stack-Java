package com.onepieceofjava.JoshuaEmployeeRestApi.service;


import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApi.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApi.repository.EmployeeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

   @Autowired
    EmployeeRepository employeeRepository;

   //GET ALL
   public List<Employee> getAllEmployee(){
       return employeeRepository.findAll();
   }

    //GET BY ID
    public Optional<Employee> getEmployeeById(Long id){
        return employeeRepository.findById(id);
    }

    //ADD
    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee saveEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    //UPDATE
    public Employee updatedEmployee(Long id, Employee updatedEmployee){
        if(employeeRepository.existsById(id)){
            updatedEmployee.setId(id);
            return employeeRepository.save(updatedEmployee);
        }
        return null;
    }

    //DELETE BY ID
    public void deleteEmployeeById(Long id){
        employeeRepository.deleteById(id);
    }
}
