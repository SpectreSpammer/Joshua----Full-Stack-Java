package com.onepieceofjava.JoshuaEmployeeRestApi.serviceTest;


import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApi.model.Employee;
import com.onepieceofjava.JoshuaEmployeeRestApi.repository.AssetRepository;
import com.onepieceofjava.JoshuaEmployeeRestApi.service.AssetService;
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
public class AssetServiceTest {

    @Mock
    private AssetRepository assetRepository;

    @InjectMocks
    private AssetService assetService;

    private Asset testAsset;

    private Asset updateAsset;

    @BeforeEach
    void setUp(){
        testAsset = new Asset(1L, "Lenovo","Legion","Developer Laptop","LNV54321");
        updateAsset = new Asset(1L, "Lenovo","Legion","Front end Developer Laptop","LNV54321");
    }


    //methodName_expectation
    //GET ALl
    @Test
    void getAllEmployee_ShouldReturnAllEmployees(){

        //arrange
        List<Asset> expectListOfAssets = Arrays.asList(
                testAsset,
                new Asset(2L, "Asus","Zephyrus","Backend end Developer Laptop","LNV12345")
        );
        when(assetRepository.findAll()).thenReturn(expectListOfAssets);

        //act
        List<Asset> actualEmployee = assetService.getAllAssets();

        //assert
        assertEquals(expectListOfAssets, actualEmployee);
    }

    //GET By Id
    @Test
    void getEmployeeById_ShouldReturnEmployeeById_WhenEmployeeIsExist(){

        //arrange
        when(assetRepository.findById(5L)).thenReturn(Optional.of(testAsset));

        //act
        Optional<Asset> result = assetService.getAssetById(5L);

        //assert
        assertTrue(result.isPresent());
        assertEquals(testAsset.getModel(), result.get().getModel());
    }

    //ADD
    @Test
    void addEmployee_ShouldReturnSavedEmployee(){

        //arrange
        when(assetRepository.save(testAsset)).thenReturn(testAsset);

        //act
        Asset savedAsset = assetService.addAsset(testAsset);

        //assert
        assertNotNull(savedAsset);
        assertEquals(testAsset.getModel(), savedAsset.getModel());
        verify(assetRepository).save(testAsset);
    }

    //update - if exist
    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenEmployeeIsExist(){

        //arrange
        when(assetRepository.existsById(1L)).thenReturn(true);
        when(assetRepository.save(updateAsset)).thenReturn(updateAsset);

        //act
        Asset result = assetService.updatedAsset(1L, updateAsset);

        //assert
        assertNotNull(result);
        assertEquals(updateAsset.getModel(), result.getModel());
        verify(assetRepository).existsById(1L);
        verify(assetRepository).save(updateAsset);

    }

    //update - if not exist
    @Test
    void updateEmployee_ShouldReturnUpdatedEmployee_WhenEmployeeDoesNotExist(){

        //arrange
        when(assetRepository.existsById(1L)).thenReturn(false);


        //act
        Asset result = assetService.updatedAsset(1L, updateAsset);

        //assert
        assertNull(result);
        verify(assetRepository).existsById(1L);
        verify(assetRepository,never()).save(updateAsset);

    }

    @Test
    void deleteEmployee_ShouldDeleteEmployee_WhenEmployeeIsExist(){

        //arrange
        Long assetId = 1L;

        //act
        assetService.deleteAssetById(assetId);

        //assert
        verify(assetRepository).deleteById(assetId);

    }

}
