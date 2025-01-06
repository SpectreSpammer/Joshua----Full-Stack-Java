package com.onepieceofjava.JoshuaEmployeeRestApi.controller;

import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApi.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApi.service.AssetService;
import com.onepieceofjava.JoshuaEmployeeRestApi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {


   @Autowired
    AssetService assetService;

    @Autowired
    EmployeeService employeeService;


   //EMPLOYEES
   //OK
   @GetMapping("/employees")
   public List<Employee> getAllEmployees(){
       return employeeService.getAllEmployee();
   }

   //OK
    @GetMapping("/employees/{id}")
    public Employee getEmployeeById(@PathVariable Long id){
        return employeeService.getEmployeeById(id).orElse(null);
    }


    @PostMapping("/employees")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){

       try{
           // name
           if( employee.getName() == null || employee.getName().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Employee name cannot be null");
           }

           // dept
           if( employee.getDept() == null || employee.getDept().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Employee Department cannot be null");
           }


           Employee savedEmployee = employeeService.addEmployee(employee);
           return ResponseEntity.created(URI.create("/api/inventory/employees/" + savedEmployee.getId()))
                   .body(savedEmployee);

       }catch (IllegalArgumentException e){
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(e.getMessage());
       }catch (RuntimeException e){
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
       }


    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable Long id, @RequestBody Employee updatedEmployee){

       try{
           // name
           if( updatedEmployee.getName() == null || updatedEmployee.getName().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Employee name cannot be null");
           }

           // dept
           if( updatedEmployee.getDept() == null || updatedEmployee.getDept().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Employee Department cannot be null");
           }

           Employee updated = employeeService.updatedEmployee(id, updatedEmployee);
           return ResponseEntity.ok(updated);
       }catch (IllegalArgumentException e){
           return ResponseEntity
                   .status(HttpStatus.NOT_FOUND)
                   .body(e.getMessage());
       }catch (RuntimeException e){
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
       }

    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployeeById(@PathVariable Long id){
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.noContent().build();
    }

   //ASSETS
    //OK
   @GetMapping("/assets")
    public List<Asset> getAllAssets(){
       return assetService.getAllAssets();
   }

   //OK
   @GetMapping("/assets/{id}")
    public Asset getAssetById(@PathVariable Long id){
       return assetService.getAssetById(id).orElse(null);
   }


   @PostMapping("/assets")
    public ResponseEntity<?> addAsset(@RequestBody Asset asset){
       try{
           // name
           if( asset.getModel() == null || asset.getModel().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset model cannot be null");
           }

           // dept
           if( asset.getBrand() == null || asset.getBrand().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset brand cannot be null");
           }

           if( asset.getType() == null || asset.getType().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset type cannot be null");
           }

           if( asset.getSerialNumber() == null || asset.getSerialNumber().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset Serial Number cannot be null");
           }


           Asset savedAsset = assetService.addAsset(asset);
           return ResponseEntity.created(URI.create("/api/inventory/assets/" + savedAsset.getId()))
                   .body(savedAsset);

       }catch (IllegalArgumentException e){
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(e.getMessage());
       }catch (RuntimeException e){
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
       }
   }

   @PutMapping("/assets/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody Asset updatedAsset){
       try{
           // name
           if( updatedAsset.getModel() == null || updatedAsset.getModel().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset model cannot be null");
           }

           // dept
           if( updatedAsset.getBrand() == null || updatedAsset.getBrand().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset brand cannot be null");
           }

           if( updatedAsset.getType() == null || updatedAsset.getType().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset type cannot be null");
           }

           if( updatedAsset.getSerialNumber() == null || updatedAsset.getSerialNumber().trim().isEmpty()){
               return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                       .body("Asset Serial Number cannot be null");
           }


           Asset updated = assetService.addAsset(updatedAsset);
           return ResponseEntity.ok(updated);

       }catch (IllegalArgumentException e){
           return ResponseEntity
                   .status(HttpStatus.BAD_REQUEST)
                   .body(e.getMessage());
       }catch (RuntimeException e){
           return ResponseEntity
                   .status(HttpStatus.INTERNAL_SERVER_ERROR)
                   .body(e.getMessage());
       }
   }

    @DeleteMapping("/assets/{id}")
    public ResponseEntity<Void> deleteAssetById(@PathVariable Long id){
       assetService.deleteAssetById(id);
       return ResponseEntity.noContent().build();
    }

    //assigning assets to the employee
    //next meeting update the EmployeeObject to ResponseEntity
    @PostMapping("employees/{employeeId}/assets/{assetId}")
    public Employee assignAssetToTheEmployee(@PathVariable Long employeeId, @PathVariable Long assetId){
        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        Optional<Asset> assetOptional = assetService.getAssetById(assetId);

        if(employeeOptional.isPresent() && assetOptional.isPresent()){
            Employee employee = employeeOptional.get();
            Asset asset = assetOptional.get();

            //set relationship
            asset.setEmployee(employee);
            employee.addAsset(asset);

            //save updated asset
            assetService.addAsset(asset);

            return employeeService.addEmployee(employee);
        }
        return null;
    }

    //deleting assets from the employee
    @DeleteMapping("employees/{employeeId}/assets/{assetId}")
    public Employee deleteAssetToTheEmployee(@PathVariable Long employeeId, @PathVariable Long assetId){

        Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
        Optional<Asset> assetOptional = assetService.getAssetById(assetId);

        if(employeeOptional.isPresent() && assetOptional.isPresent()){
            Employee employee = employeeOptional.get();
            employee.removeAsset(assetOptional.get());

           return employeeService.updatedEmployee(employeeId,employee);

        }
        return null;
    }

    //fetching assets from the employees
    @GetMapping("employees/{employeeId}/assets")
    public List<Asset> getEmployeeAssetById(@PathVariable Long employeeId ){
       Optional<Employee> employeeOptional = employeeService.getEmployeeById(employeeId);
       return employeeOptional.map(Employee::getAssets).orElse(null);
    }
}
