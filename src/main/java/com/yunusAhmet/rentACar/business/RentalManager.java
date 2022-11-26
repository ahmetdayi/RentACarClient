package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.CarNotDeliverException;
import com.yunusAhmet.rentACar.core.exception.CustomerAlreadyRentACar;
import com.yunusAhmet.rentACar.core.exception.WrongReturnDateException;
import com.yunusAhmet.rentACar.dataAccess.RentalDao;
import com.yunusAhmet.rentACar.dto.RentACarRequest;
import com.yunusAhmet.rentACar.dto.RentCarDto;
import com.yunusAhmet.rentACar.dto.converter.RentCarDtoConverter;
import com.yunusAhmet.rentACar.entity.Car;
import com.yunusAhmet.rentACar.entity.Customer;
import com.yunusAhmet.rentACar.entity.Rental;

import org.springframework.stereotype.Service;
import java.time.LocalDateTime;


@Service
public class RentalManager {

    private final RentalDao rentalDao;

    private final CustomerManager customerManager;

    private final CarManager carManager;
    private final RentCarDtoConverter carDtoConverter;




    public RentalManager(RentalDao rentalDao, CustomerManager customerManager, CarManager carManager, RentCarDtoConverter carDtoConverter) {
        this.rentalDao = rentalDao;
        this.customerManager = customerManager;
        this.carManager = carManager;

        this.carDtoConverter = carDtoConverter;

    }

    public RentCarDto rentACar(RentACarRequest request) {

        Car car = carManager.findCarByCarId(request.getCarId());
        Customer customer = customerManager.getCustomerByCustomerId(request.getCustomerId());

        dateControl(request);
        Rental rental = new Rental(LocalDateTime.now().withNano(0),request.getReturnDate(), customer, car);

        if(rentalDao.getRentalByCar_CarId(request.getCarId()).isPresent()){
            throw new CarNotDeliverException(Constant.CAR_NOT_DELIVER);
        }

        if(rentalDao.getRentalByCustomer_CustomerId(request.getCustomerId()).isPresent()){
            throw new CustomerAlreadyRentACar(Constant.CUSTOMER_ALREADY_RENT_A_CAR);
        }

        Rental save = rentalDao.save(rental);

        return carDtoConverter.convert(save);


    }

    private void dateControl(RentACarRequest request) {
        if(LocalDateTime.now().isAfter(request.getReturnDate())){
            throw new WrongReturnDateException(Constant.WRONG_RETURN_DATE);
        }
    }

}






