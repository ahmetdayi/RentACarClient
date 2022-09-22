package com.yunusAhmet.rentACar.DataAccess;

import com.yunusAhmet.rentACar.Entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarDao extends JpaRepository<Car,Integer> {

}
