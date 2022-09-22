package com.yunusAhmet.rentACar.Dto;

import com.yunusAhmet.rentACar.Entity.Color;
import lombok.Data;

@Data
public class CarListDto {

    private CarDto carId;

    private CarDto carName;

    private CarDto productYear;

    private CarDto dailyPrice;

    private BrandDto brand;

    private Color color;

}
