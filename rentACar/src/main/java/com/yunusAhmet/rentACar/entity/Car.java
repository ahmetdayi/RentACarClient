package com.yunusAhmet.rentACar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int carId;

    private String carName;

    private Long dailyPrice;

    private String productYear;





    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            joinColumns = @JoinColumn,
            inverseJoinColumns = @JoinColumn)
    List<Color> carColors;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
    @JoinColumn()
    private Brand brand;

    @OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "car")
    private Rental rental;

    @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY,mappedBy = "car")
    private List<Image> images;

    public Car( String carName, Long dailyPrice, String productYear, Brand brand, List<Color> colors) {

        this.carColors = colors;
        this.carName = carName;
        this.dailyPrice = dailyPrice;
        this.productYear = productYear;
        this.brand = brand;
    }


}
