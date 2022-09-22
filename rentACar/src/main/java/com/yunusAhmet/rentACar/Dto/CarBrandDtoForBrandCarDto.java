package com.yunusAhmet.rentACar.Dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class CarBrandDtoForBrandCarDto {

    private int carId;


    private String carName;

    private Long dailyPrice;

    private String productYear;

    private List<ColorDto> carColors;
}
