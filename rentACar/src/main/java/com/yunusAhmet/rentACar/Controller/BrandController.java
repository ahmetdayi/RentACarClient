package com.yunusAhmet.rentACar.Controller;


import com.yunusAhmet.rentACar.Business.BrandManager;
import com.yunusAhmet.rentACar.Dto.CarBrandDtoForBrandCarDto;
import com.yunusAhmet.rentACar.Dto.CarDto;
import com.yunusAhmet.rentACar.Dto.CreateCarRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/brand")
@RestController
public class BrandController {

    private final BrandManager brandManager;

    public BrandController(BrandManager brandManager) {
        this.brandManager = brandManager;
    }

    @GetMapping("/{brandId}")
    public ResponseEntity<List<CarBrandDtoForBrandCarDto>> createCar(@PathVariable int brandId){
        return ResponseEntity.ok(brandManager.getAllCarByBrandId(brandId));
    }
}
