package com.onepieceofjava.JoshuaEmployeeRestApiWithService.repository;

import com.onepieceofjava.JoshuaEmployeeRestApiWithService.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<Asset,Long> {
}
