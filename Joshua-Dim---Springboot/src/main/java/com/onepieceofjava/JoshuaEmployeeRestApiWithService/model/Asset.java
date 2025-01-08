package com.onepieceofjava.JoshuaEmployeeRestApiWithService.model;

import jakarta.persistence.*;


@Entity
@Table(name = "assets")
public class Asset {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "asset_gen")
    @SequenceGenerator(
            name = "asset_seq",
            sequenceName = "asset_sequence",
            initialValue = 2001,
            allocationSize = 1
    )
    private Long id;
    private String brand;
    private String model;
    private String type;
    private String serialNumber;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Asset() {
    }

    public Asset(Long id, String brand, String model, String type, String serialNumber) {
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

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
