package com.yunusAhmet.rentACar.Controller;

import com.yunusAhmet.rentACar.Business.CarManager;
import com.yunusAhmet.rentACar.Dto.CarDto;
import com.yunusAhmet.rentACar.Dto.CarListDto;
import com.yunusAhmet.rentACar.Dto.CreateCarRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/car")
@RestController
public class CarController {

    private final CarManager carManager;

    public CarController(CarManager carManager) {
        this.carManager = carManager;
    }

    @PostMapping()
    public ResponseEntity<CarDto> createCar(@Valid @RequestBody CreateCarRequest request){
        return ResponseEntity.ok(carManager.addCar(request));
    }

    @GetMapping
    public ResponseEntity<List<CarDto>> getAllCar(){
        return ResponseEntity.ok(carManager.getAllCar());
    }

    @DeleteMapping("/{carId}")
    public ResponseEntity<Void> deleteCarByCarId(@PathVariable int carId){
        carManager.deleteCarByCarId(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
