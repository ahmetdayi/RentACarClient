package com.yunusAhmet.rentACar.DataAccess;

import com.yunusAhmet.rentACar.Entity.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorDao extends JpaRepository<Color,Integer> {


}
