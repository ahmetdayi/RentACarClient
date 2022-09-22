package com.yunusAhmet.rentACar.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;

    private String carName;

    private Long dailyPrice;

    private String productYear;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "car_color",
            joinColumns = @JoinColumn(name = "carId"),
            inverseJoinColumns = @JoinColumn(name = "colorId"))
    List<Color> carColors;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "brandId")
    private Brand brand;

    public Car( String carName, Long dailyPrice, String productYear, Brand brand) {

        this.carName = carName;
        this.dailyPrice = dailyPrice;
        this.productYear = productYear;
        this.brand = brand;
    }
}
