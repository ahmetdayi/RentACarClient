package com.yunusahmet.rentacar.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.yunusahmet.rentacar.business.RentalManager;
import com.yunusahmet.rentacar.dto.RentACarRequest;
import com.yunusahmet.rentacar.dto.RentCarDto;

import javax.validation.Valid;

@RequestMapping("/rental")
@RestController
@CrossOrigin(origins = "*")
public class RentalController {

    private final RentalManager rentalManager;

    public RentalController(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
    }

    @PostMapping
    public ResponseEntity<RentCarDto> rentACar(@Valid @RequestBody RentACarRequest request){
        return new ResponseEntity<>(rentalManager.rentACar(request), HttpStatus.CREATED);
    }
}
