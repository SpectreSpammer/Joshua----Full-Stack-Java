package com.onepieceofjava.JoshuaEmployeeRestApi.oldCodes;

public class OldAsset {

    private Long id;
    private String brand;
    private String model;
    private String type;
    private String serialNumber;

    public OldAsset(Long id, String brand, String model, String type, String serialNumber) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.serialNumber = serialNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }


}
