package com.yunusAhmet.rentACar.dataAccess;

import com.yunusAhmet.rentACar.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ImageDao extends JpaRepository<Image,Integer> {
    Optional<List<Image>> getImagesByCar_CarId(int carId);
}
