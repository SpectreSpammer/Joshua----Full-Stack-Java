package com.onepieceofjava.JoshuaEmployeeRestApi.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;


@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "employee_gen")
    @TableGenerator(
            name = "employee_seq",
            pkColumnName = "gen_name",
            valueColumnName = "gen_value",
            pkColumnValue = "employee_id",
            initialValue = 1001,
            allocationSize = 1
    )
    private Long id;
    private String name;
    private String dept;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Asset> assets;

    public Employee() {
        assets = new ArrayList<>();
    }

    public Employee(Long id, String name, String dept) {
        this.id = id;
        this.name = name;
        this.dept = dept;
        this.assets = new ArrayList<>();
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

    public List<Asset> getAssets() {
        return assets;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public void setAsset(List<Asset> assets){
        this.assets = assets;
    }

    public void addAsset(Asset asset){
        assets.add(asset);
        asset.setEmployee(this);
    }

    public void removeAsset(Asset asset){
       assets.remove(asset);
        asset.setEmployee(null);
    }
}
