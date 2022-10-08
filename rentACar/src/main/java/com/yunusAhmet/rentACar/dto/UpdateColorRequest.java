package com.yunusAhmet.rentACar.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode
public class UpdateColorRequest {

    @NotNull
    private int colorId;

    @NotBlank
    private String colorName;
}
