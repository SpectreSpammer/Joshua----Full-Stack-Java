package com.onepieceofjava.JoshuaEmployeeRestApiWithService.integrationTesting;



import com.fasterxml.jackson.databind.ObjectMapper;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.service.AssetService;
import com.onepieceofjava.JoshuaEmployeeRestApiWithService.service.EmployeeService;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import org.springframework.http.MediaType;


import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;



@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class EmployeeControllerIntegrationTesting {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;

    @MockBean
    private AssetService assetService;

    private Employee testEmployee;

    private Asset testAsset;


    @BeforeEach
    void setUp(){
        testEmployee = new Employee(1L,"Joshua", "IT");
        testAsset = new Asset(101L, "Lenovo","Legion","Developer Laptop","LNV54321");
    }

    //Employee
    //method that needs to test _ expectation
    @Test
    void getAllEmployees_shouldReturnEmployeeAsList() throws Exception{
        //arrange
        List<Employee> employeeList = Arrays.asList(testEmployee);
        when(employeeService.getAllEmployee()).thenReturn(employeeList);

        //act and assert
        mockMvc.perform(get("/api/inventory/employees")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(testEmployee.getId()))
                .andExpect(jsonPath("$[0].name").value(testEmployee.getName()))
                .andExpect(jsonPath("$[0].dept").value(testEmployee.getDept()));
    }

    @Test
    void getEmployeeById_shouldReturnEmployeeById() throws Exception{
        //arrange
        when(employeeService.getEmployeeById(1l)).thenReturn(Optional.of(testEmployee));

        //act and assert
        mockMvc.perform(get("/api/inventory/employees/{id}",1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testEmployee.getId()))
                .andExpect(jsonPath("$.name").value(testEmployee.getName()))
                .andExpect(jsonPath("$.dept").value(testEmployee.getDept()));
    }

    @Test
    void addEmployee_ShouldReturnCreatedEmployee()throws Exception{
        //arrange
        Employee newEmployee = new Employee(null, "Nan","HR");
        Employee savedEmployee = new Employee(2L,"Nan", "HR");
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(savedEmployee);

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newEmployee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Nan"))
                .andExpect(jsonPath("$.dept").value("HR"))
                .andExpect(jsonPath("$.assets").isArray())
                .andExpect(jsonPath("$.assets").isEmpty());

        verify(employeeService,times(1)).addEmployee(any(Employee.class));
    }

    @Test
    void addEmployeeWithNullName_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Employee invalidEmployee = new Employee(null,null,"IT");

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService,never()).addEmployee(any(Employee.class));
    }

    @Test
    void addEmployeeWithNullDept_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Employee invalidEmployee = new Employee(null,"Joshua",null);

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService,never()).addEmployee(any(Employee.class));
    }


    @Test
    void addEmployeeWithEmptyName_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Employee invalidEmployee = new Employee(null,"","IT");

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService,never()).addEmployee(any(Employee.class));
    }

    @Test
    void addEmployeeWithEmptyDept_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Employee invalidEmployee = new Employee(null,"Joshua","");

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService,never()).addEmployee(any(Employee.class));
    }

    @Test
    void addEmployeeWithExistingId_ShouldReturnIgnoreId() throws Exception{
        //arrange
        Employee employeeWithId = new Employee(1L,"Nan","Sales");
        Employee savedEmployee = new Employee(2L,"Nan","Sales");
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(savedEmployee);

        //act and assert
        mockMvc.perform(post("/api/inventory/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employeeWithId)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.name").value("Nan"))
                .andExpect(jsonPath("$.dept").value("Sales"));

        verify(employeeService,times(1)).addEmployee(any(Employee.class));

    }


    //update
    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee() throws Exception{
        //arrange
        Long employeeId = 1L;
        Employee updateEmployee = new Employee(null, "Joshua", "Sales");
        Employee savedEmployee = new Employee(employeeId, "Joshua", "Sales");
        when(employeeService.updatedEmployee(eq(employeeId),any(Employee.class))).thenReturn(savedEmployee);

        //act and assert
        mockMvc.perform(put("/api/inventory/employees/{id}",employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateEmployee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(employeeId))
                .andExpect(jsonPath("$.name").value("Joshua"))
                .andExpect(jsonPath("$.dept").value("Sales"))
                .andExpect(jsonPath("$.assets").isArray())
                .andExpect(jsonPath("$.assets").isEmpty());

        verify(employeeService,times(1)).updatedEmployee(eq(employeeId),any(Employee.class));
    }

    @Test
    void updateEmployeeIdWithNullName_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long employeeId = 1L;
        Employee invalidEmployee = new Employee(null, null, "Sales");


        //act and assert
        mockMvc.perform(put("/api/inventory/employees/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).updatedEmployee(any(Long.class),any(Employee.class));
    }

    @Test
    void updateEmployeeIdWithNullDept_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long employeeId = 1L;
        Employee invalidEmployee = new Employee(null, "Joshua", null);


        //act and assert
        mockMvc.perform(put("/api/inventory/employees/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).updatedEmployee(any(Long.class),any(Employee.class));
    }

    @Test
    void updateEmployeeIdWithInvalidId_ShouldReturnBadRequest() throws Exception{
        //Arrange
        String  employeeId = "123ABC";


        //act and assert
        mockMvc.perform(put("/api/inventory/employees/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testEmployee)))
                .andExpect(status().isBadRequest());

        verify(employeeService, never()).updatedEmployee(anyLong(),any(Employee.class));
    }

    @Test
    void updateEmployeeWhenNotFound_ShouldReturnNotFound() throws Exception{
        Long employeeId = 999L;
        Employee updateEmployee = new Employee(null, "Joshua", "Sales");
        when(employeeService.updatedEmployee(eq(employeeId),any(Employee.class)))
                .thenThrow(new IllegalArgumentException("Employee with id " + employeeId + " not found"));


        //act and assert
        mockMvc.perform(put("/api/inventory/employees/{id}",employeeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateEmployee)))
                .andExpect(status().isNotFound());

        verify(employeeService, times(1)).updatedEmployee(eq(employeeId),any(Employee.class));
    }

    //delete
    @Test
    void deleteEmployee_ShouldReturnNoContent() throws Exception{
        //arrange
        Long employeeId = 1L;
        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(testEmployee));
        doNothing().when(employeeService).deleteEmployeeById(employeeId);

        //act and assert
        mockMvc.perform(delete("/api/inventory/employees/{id}",employeeId))
                .andExpect(status().isNoContent());

        verify(employeeService,times(1)).deleteEmployeeById(employeeId);
    }


    //ASSET
    @Test
    void getEmployeeAssets_ShouldReturnListOfAssets() throws Exception{
        //arrange
        testEmployee.addAsset(testAsset);
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(testEmployee));

        //act and assert
        mockMvc.perform(get("/api/inventory/employees/1/assets"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].brand").value(testAsset.getBrand()));

    }

    @Test
    void getAssetsById_ShouldReturnAssets() throws Exception{
        //arrange

        when(assetService.getAssetById(101L)).thenReturn(Optional.of(testAsset));

        //act and assert
        mockMvc.perform(get("/api/inventory/assets/{id}",101L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(testAsset.getId()))
                .andExpect(jsonPath("$.brand").value(testAsset.getBrand()))
                .andExpect(jsonPath("$.model").value(testAsset.getModel()))
                .andExpect(jsonPath("$.type").value(testAsset.getType()))
                .andExpect(jsonPath("$.serialNumber").value(testAsset.getSerialNumber()));
    }


    //ADD
    @Test
    void addAsset_ShouldReturnAddAsset() throws Exception{
        //arrange
        Asset newAsset = new Asset(null,"Lenovo","Legion","Developer Laptop","LNV54321");
        Asset savedAsset = new Asset(101L,"Lenovo","Legion","Developer Laptop","LNV54321");
        when(assetService.addAsset(any(Asset.class))).thenReturn(savedAsset);

        //act and assert
        mockMvc.perform(post("/api/inventory/assets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newAsset)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location","/api/inventory/assets/101"))
                .andExpect(jsonPath("$.id").value(testAsset.getId()))
                .andExpect(jsonPath("$.brand").value(testAsset.getBrand()))
                .andExpect(jsonPath("$.model").value(testAsset.getModel()))
                .andExpect(jsonPath("$.type").value(testAsset.getType()))
                .andExpect(jsonPath("$.serialNumber").value(testAsset.getSerialNumber()));

        verify(assetService,times(1)).addAsset(any(Asset.class));

    }

    //update
    @Test
    void updateAssetIdWithNullBrand_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long assetId = 1L;
        Asset invalidAsset = new Asset(null, null,"Legion","Developer Laptop","LNV12345");


        //act and assert
        mockMvc.perform(put("/api/inventory/assets/{id}",assetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAsset)))
                .andExpect(status().isBadRequest());

        verify(assetService, never()).updatedAsset(any(Long.class),any(Asset.class));
    }

    @Test
    void updateAssetIdWithNullModel_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long assetId = 1L;
        Asset invalidAsset = new Asset(null, "Lenovo",null,"Developer Laptop","LNV12345");

        //act and assert
        mockMvc.perform(put("/api/inventory/assets/{id}",assetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAsset)))
                .andExpect(status().isBadRequest());

        verify(assetService, never()).updatedAsset(any(Long.class),any(Asset.class));
    }

    @Test
    void updateAssetIdWithNullType_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long assetId = 1L;
        Asset invalidAsset = new Asset(null, "Lenovo","Legion",null,"LNV12345");

        //act and assert
        mockMvc.perform(put("/api/inventory/assets/{id}",assetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAsset)))
                .andExpect(status().isBadRequest());

        verify(assetService, never()).updatedAsset(any(Long.class),any(Asset.class));
    }

    @Test
    void updateAssetIdWithNullSerialNumber_ShouldReturnBadRequest() throws Exception{
        //Arrange
        Long assetId = 1L;
        Asset invalidAsset = new Asset(null, "Lenovo","Legion","Developer Laptop", null);

        //act and assert
        mockMvc.perform(put("/api/inventory/assets/{id}",assetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidAsset)))
                .andExpect(status().isBadRequest());

        verify(assetService, never()).updatedAsset(any(Long.class),any(Asset.class));
    }

    @Test
    void updateAssetWithInvalidAssetId_ShouldReturnBadRequest() throws Exception{
        //arrange
        String invalidAssetId = "123ABC";

        //act and assert
        mockMvc.perform(put("/api/inventory/assets/{id}", invalidAssetId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testAsset)))
                .andExpect(status().isBadRequest());

        verify(assetService,never()).updatedAsset(any(Long.class),any(Asset.class));

    }


    //delete
    @Test
    void deleteAsset_ShouldReturnNoContent() throws Exception{
        //arrange
        Long assetId = 101L;
        when(assetService.getAssetById(assetId)).thenReturn(Optional.of(testAsset));
        doNothing().when(assetService).deleteAssetById(assetId);

        //act and assert
        mockMvc.perform(delete("/api/inventory/assets/{id}",assetId))
                .andExpect(status().isNoContent());

        verify(assetService,times(1)).deleteAssetById(assetId);
    }

    @Test
    void assignAssetToEmployee_ShouldReturnCreated() throws Exception {

        //Changing Employee to Response Entity in controller
        //arrange
        Long employeeId = 1L;
        Long assetId = 101L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(testEmployee));
        when(assetService.getAssetById(assetId)).thenReturn(Optional.of(testAsset));
        when(employeeService.addEmployee(any(Employee.class))).thenReturn(testEmployee);
        when(assetService.addAsset(any(Asset.class))).thenReturn(testAsset);


        //act and assert
        mockMvc.perform(post("/api/inventory/employees/{employeeId}/assets/{assetId}",employeeId,assetId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(testEmployee.getId()))
                .andExpect(jsonPath("$.name").value(testEmployee.getName()))
                .andExpect(jsonPath("$.dept").value(testEmployee.getDept()));

        verify(employeeService,times(1)).getEmployeeById(employeeId);
        verify(assetService,times(1)).getAssetById(assetId);
        verify(employeeService,times(1)).addEmployee(any(Employee.class));
        verify(assetService,times(1)).addAsset(any(Asset.class));
    }

    @Test
    void assignAssetToEmployee_WhenEmployeeIsNotFound_ShouldReturnNotFound() throws Exception {

        //Changing Employee to Response Entity in controller
        //arrange
        Long employeeId = 999L;
        Long assetId = 1L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.empty());
        when(assetService.getAssetById(assetId)).thenReturn(Optional.of(testAsset));


        //act and assert
        mockMvc.perform(post("/api/inventory/employees/{employeeId}/assets/{assetId}",employeeId,assetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(employeeService,times(1)).getEmployeeById(employeeId);
        verify(assetService,times(1)).getAssetById(assetId);
        verify(employeeService,never()).addEmployee(any(Employee.class));
        verify(assetService,never()).addAsset(any(Asset.class));
    }

    @Test
    void assignAssetToEmployee_WhenAssetIsNotFound_ShouldReturnNotFound() throws Exception {

        //Changing Employee to Response Entity in controller
        //arrange
        Long employeeId = 1L;
        Long assetId = 999L;

        when(employeeService.getEmployeeById(employeeId)).thenReturn(Optional.of(testEmployee));
        when(assetService.getAssetById(assetId)).thenReturn(Optional.empty());


        //act and assert
        mockMvc.perform(post("/api/inventory/employees/{employeeId}/assets/{assetId}",employeeId,assetId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());


        verify(employeeService,times(1)).getEmployeeById(employeeId);
        verify(assetService,times(1)).getAssetById(assetId);
        verify(employeeService,never()).addEmployee(any(Employee.class));
        verify(assetService,never()).addAsset(any(Asset.class));
    }










}
