package com.yunusAhmet.rentACar;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;



import java.io.IOException;


@EntityScan("com/yunusAhmet/rentACar/entity")
@SpringBootApplication
public class RentACarApplication {

	public static void main(String[] args) throws IOException {

		SpringApplication.run(RentACarApplication.class, args);



	}

}
