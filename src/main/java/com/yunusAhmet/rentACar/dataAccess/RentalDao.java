package com.yunusAhmet.rentACar.dataAccess;

import com.yunusAhmet.rentACar.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface RentalDao extends JpaRepository<Rental,Integer> {

    Optional<Rental> getRentalByCar_CarId(int carId);
    Optional<Rental> getRentalByCustomer_CustomerId(int customerId);

}
