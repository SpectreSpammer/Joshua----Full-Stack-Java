package com.onepieceofjava.JoshuaEmployeeRestApiWithService.oldCodes;


import java.util.List;

public class OldEmployee {

    private Long id;
    private String name;
    private String dept;
    private List<OldAsset> oldAssets;

    public OldEmployee(Long id, String name, String dept, List<OldAsset> oldAssets) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.oldAssets = oldAssets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public List<OldAsset> getAssets() {
        return oldAssets;
    }

    public void setAssets(List<OldAsset> oldAssets) {
        this.oldAssets = oldAssets;
    }

    public void setAsset(List<OldAsset> oldAssets){
        this.oldAssets = oldAssets;
    }

    public void addAsset(OldAsset oldAsset){
        this.oldAssets.add(oldAsset);
    }

    public void removeAsset(OldAsset oldAsset){
        this.oldAssets.remove(oldAsset);
    }
}
