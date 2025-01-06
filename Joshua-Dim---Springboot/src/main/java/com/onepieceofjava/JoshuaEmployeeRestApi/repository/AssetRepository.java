package com.onepieceofjava.JoshuaEmployeeRestApi.repository;

import com.onepieceofjava.JoshuaEmployeeRestApi.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
}
