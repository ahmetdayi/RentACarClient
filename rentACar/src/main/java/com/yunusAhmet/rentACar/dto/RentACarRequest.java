package com.yunusAhmet.rentACar.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
public class RentACarRequest {

    private Date returnDate;

    private int customerId;

    private int carId;
}
