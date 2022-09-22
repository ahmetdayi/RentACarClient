package com.yunusAhmet.rentACar.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int brandId;

    private String brandName;


    @OneToMany(cascade = CascadeType.ALL,mappedBy = "brand",fetch = FetchType.LAZY)
    private List<Car> car;
}
