package com.onepieceofjava.JoshuaEmployeeRestApi.service;


import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;
import com.onepieceofjava.JoshuaEmployeeRestApi.repository.AssetRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssetService {


    @Autowired
    AssetRepository assetRepository;

    //GET AL
    public List<Asset> getAllAssets(){
        return assetRepository.findAll();
    }

    //GET by Id
    public Optional<Asset> getAssetById(Long id){
        return assetRepository.findById(id);
    }

    //ADD
    public Asset addAsset(Asset asset){
        return assetRepository.save(asset);
    }

    @Transactional
    public Asset saveAsset(Asset asset){
        return assetRepository.save(asset);
    }

    //UPDATE
    public Asset updatedAsset(Long id, Asset updatedAsset){
        if(assetRepository.existsById(id)){
            updatedAsset.setId(id);
            return assetRepository.save(updatedAsset);
        }
        return null;
    }

    //DELETE
    public void deleteAssetById(Long id){
       assetRepository.deleteById(id);
    }

}
