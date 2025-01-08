package com.onepieceofjava.JoshuaEmployeeRestApiWithComponent.service;


import com.onepieceofjava.JoshuaEmployeeRestApiWithComponent.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApiWithComponent.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InventoryService {

    private List<Employee> employeeList = new ArrayList<>();
    private List<Asset> assetList = new ArrayList<>();

    private Long employeeId = 1L;
    private Long assetId = 101L;


    //get all employees
    public List<Employee> getAllEmployees(){
        return employeeList;
    }

    //get by id employee
    public Employee getEmployeeById(Long empId){
        return employeeList.stream()
                .filter(emp -> emp.getId().equals(empId))
                .findFirst().orElse(null);
    }

    //add employee
    public Employee addEmployee(Employee employee){
        employee.setId(employeeId++);

        if(employee.getAssets() != null && !employee.getAssets().isEmpty()){
            List<Asset> prcocessedAssets = new ArrayList<>();
            for(Asset asset : employee.getAssets()){
                asset.setId(assetId++);
                assetList.add(asset);
            }
            employee.setAsset(prcocessedAssets);
        }

        employeeList.add(employee);
        return employee;
    }

    //update employee
    public Employee updateEmployee(Long empId, Employee updatedEmployee){
        for(int i = 0; i < employeeList.size();i++){
            if(employeeList.get(i).getId().equals(empId)){
                updatedEmployee.setId(empId);
                employeeList.set(i, updatedEmployee);
                return updatedEmployee;
            }
        }

        return null;
    }

    //delete
    public void deleteEmployee(Long empId){
        employeeList.removeIf( emp -> emp.getId().equals(empId));
    }


    //ASSET
    //get all assets
    public List<Asset> getAllAssets(){
        return assetList;
    }

    //get by id assets
    public Asset getAssetById(Long assetId){
        return assetList.stream()
                .filter(aset -> aset.getId().equals(assetId))
                .findFirst().orElse(null);
    }

    public Employee assignAssetToTheEmployee(Long empId, Long assetId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(employee -> employee.getId().equals(empId))
                .findFirst();

        Optional<Asset> assetOptional = assetList.stream()
                .filter(asset -> asset.getId().equals(assetId))
                .findFirst();

        if(employeeOptional.isPresent() && assetOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Asset asset = assetOptional.get();
            employee.addAsset(asset);
            return employee;
        }

        return null;
    }


    public Employee removeAssetFromEmployee(Long employeeId, Long assetId){
        Optional<Employee> employeeOptional = employeeList.stream()
                .filter(employee -> employee.getId().equals(employeeId))
                .findFirst();

        Optional<Asset> assetOptional = assetList.stream()
                .filter(asset -> asset.getId().equals(assetId))
                .findFirst();

        if(employeeOptional.isPresent() && assetOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Asset asset = assetOptional.get();
            employee.removeAsset(asset);
            return employee;
        }

        return null;
    }

    public void deleteAsset(Long id){
        assetList.removeIf(asset -> asset.getId().equals(id));
    }

}
