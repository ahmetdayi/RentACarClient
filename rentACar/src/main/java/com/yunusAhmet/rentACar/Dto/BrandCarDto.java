package com.yunusAhmet.rentACar.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class BrandCarDto {
    private List<CarBrandDtoForBrandCarDto> cars;
}
