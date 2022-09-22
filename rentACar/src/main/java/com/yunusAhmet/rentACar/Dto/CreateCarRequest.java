package com.yunusAhmet.rentACar.Dto;

import lombok.Data;

import java.util.List;

@Data
public class CreateCarRequest {

    private String carName;

    private Long dailyPrice;

    private String productYear;

    private int brandId;

    private List<Integer> colorId;




}
