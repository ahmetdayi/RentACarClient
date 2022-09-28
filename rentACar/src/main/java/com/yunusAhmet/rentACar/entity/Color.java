package com.yunusAhmet.rentACar.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int colorId;

    public Color(String colorName) {
        this.colorName = colorName;
    }

    private String colorName;


}
