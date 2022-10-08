package com.yunusAhmet.rentACar.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

@Data
@EqualsAndHashCode
public class CreateColorRequest {

    @NotBlank
    private String colorName;
}
