package com.onepieceofjava.JoshuaEmployeeRestApiWithService.oldCodes;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OldEmployeeService {

    List<OldEmployee> oldEmployees = new ArrayList<>();

    private Long employeeId = 1001L;

    //CRUD

    //GET ALL
    public List<OldEmployee> getAllEmployees(){
        return oldEmployees;
    }

    //GET BY ID
    public Optional<OldEmployee> getEmployeeById(Long id){
        return oldEmployees.stream().filter(oldEmployee ->  oldEmployee.getId().equals(id)).findFirst();
    }


    //ADD
    public OldEmployee addEmployee(OldEmployee oldEmployee){
        oldEmployee.setId(employeeId++);
        oldEmployees.add(oldEmployee);

        return oldEmployee;
    }

    //UPDATE
    public OldEmployee updateEmployee(Long id, OldEmployee updatedOldEmployee){
        Optional<OldEmployee> employee = getEmployeeById(id);
        if(employee.isPresent()){
            updatedOldEmployee.setId(id);
            oldEmployees.set(oldEmployees.indexOf(employee.get()), updatedOldEmployee);
            return updatedOldEmployee;
        }

        return null;
    }

    //DELETE
    public void deleteEmployeeById(Long id){
        oldEmployees.removeIf(oldEmployee -> oldEmployee.getId().equals(id));
    }

}
