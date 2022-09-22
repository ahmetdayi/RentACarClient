package com.yunusAhmet.rentACar.DataAccess;

import com.yunusAhmet.rentACar.Entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandDao extends JpaRepository<Brand,Integer> {



}
