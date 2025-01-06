package com.onepieceofjava.JoshuaEmployeeRestApi.oldCodes;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class OldInventoryController {

    private List<OldEmployee> oldEmployeeList = new ArrayList<>();

    private List<OldAsset> oldAssetList = new ArrayList<>();

    private Long employeeId = 1001L;
    private Long assetId = 2001L;

    //for employees

    //GET ALL
    //localhost:8080/api/inventory/employees/
    @GetMapping("/employees")
    public List<OldEmployee> getAllEmployees(){
        return oldEmployeeList;
    }

    //GET BY ID
    @GetMapping("/employees/{id}")
    public OldEmployee getEmployeeById(@PathVariable Long id){
        return oldEmployeeList.stream()
                .filter(oldEmployee -> oldEmployee.getId()
                        .equals(id))
                .findFirst().orElse(null);

    }

    @PostMapping("/employees")
    public OldEmployee addEmployee(@RequestBody OldEmployee oldEmployee){
        oldEmployee.setId(employeeId);

        if(oldEmployee.getAssets() != null && !oldEmployee.getAssets().isEmpty()){
            List<OldAsset> processOldAsset = new ArrayList<>();
            for(OldAsset oldAsset : oldEmployee.getAssets()){
                oldAsset.setId(assetId);
                oldAssetList.add(oldAsset);
                processOldAsset.add(oldAsset);
            }
            oldEmployee.setAsset(processOldAsset);
        }
        oldEmployeeList.add(oldEmployee);

        return oldEmployee;
    }

    //update
    @PutMapping("/employees/{id}")
    public OldEmployee updateEmployee(@PathVariable Long id, @RequestBody OldEmployee updatedOldEmployee){
        for(int i = 0; i < oldEmployeeList.size(); i++){
            if(oldEmployeeList.get(i).getId().equals(id)){
                updatedOldEmployee.setId(id);
                oldEmployeeList.set(i, updatedOldEmployee);
                return updatedOldEmployee;

            }
        }
        return null;
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployeeById(@PathVariable Long id){
        oldEmployeeList.removeIf(oldEmployee -> oldEmployee.getId().equals(id));
    }

    //ASSET
    //localhost:8080/api/inventory/employees/
    @GetMapping("/assets")
    public List<OldAsset> getAllAssets(){
        return oldAssetList;
    }


    @GetMapping("/assets/{id}")
    public OldAsset getAssetById(@PathVariable Long id){
        return oldAssetList.stream()
                .filter(oldAsset -> oldAsset.getId()
                        .equals(id))
                .findFirst().orElse(null);

    }

    @PostMapping("/assets")
    public OldAsset addAsset(@RequestBody OldAsset oldAsset){
        oldAsset.setId(employeeId);
        oldAssetList.add(oldAsset);

        return oldAsset;

    }

    @DeleteMapping("/assets/{id}")
    public void deleteAssetById(@PathVariable Long id){
        oldAssetList.removeIf(oldAsset -> oldAsset.getId().equals(id));
    }


}
