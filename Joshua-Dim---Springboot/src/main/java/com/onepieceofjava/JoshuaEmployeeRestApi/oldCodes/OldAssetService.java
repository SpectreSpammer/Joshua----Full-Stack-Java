package com.onepieceofjava.JoshuaEmployeeRestApi.oldCodes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OldAssetService {

    List<OldAsset> oldAssets = new ArrayList<>();

    private Long assetId = 2001L;

    //GET ALL
    public List<OldAsset> getAllAsset(){
        return oldAssets;
    }

    //GET BY ID
    public Optional<OldAsset> getAssetById(Long id){
        return oldAssets.stream().filter(oldAsset ->  oldAsset.getId().equals(id)).findFirst();
    }


    //ADD
    public OldAsset addAsset(OldAsset oldAsset){
        oldAsset.setId(assetId++);
        oldAssets.add(oldAsset);

        return oldAsset;
    }

    //UPDATE
    public OldAsset updateAsset(Long id, OldAsset updatedOldAsset){
        Optional<OldAsset> asset = getAssetById(id);
        if(asset.isPresent()){
            updatedOldAsset.setId(id);
            oldAssets.set(oldAssets.indexOf(asset.get()), updatedOldAsset);
            return updatedOldAsset;
        }

        return null;
    }

    //DELETE
    public void deleteEmployeeById(Long id){
        oldAssets.removeIf(oldAsset -> oldAsset.getId().equals(id));
    }
}
