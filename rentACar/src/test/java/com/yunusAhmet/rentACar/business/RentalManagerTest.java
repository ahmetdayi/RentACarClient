package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.TestSupport;
import com.yunusAhmet.rentACar.dataAccess.RentalDao;
import com.yunusAhmet.rentACar.dto.*;
import com.yunusAhmet.rentACar.dto.converter.RentCarDtoConverter;
import com.yunusAhmet.rentACar.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalManagerTest extends TestSupport {

    public RentalManager rentalManager;
    public RentalDao rentalDao;

    public CustomerManager customerManager;

    public CarManager carManager;


    public RentCarDtoConverter carDtoConverter;


    @BeforeEach
    void setUp() {
        customerManager = mock(CustomerManager.class);
        carManager = mock(CarManager.class);
        rentalDao = mock(RentalDao.class);
        carDtoConverter = mock(RentCarDtoConverter.class);

        rentalManager = new RentalManager(rentalDao,customerManager,carManager,carDtoConverter);


    }

    @Test
    public void testRentACar_whenDateIsCorrectAndCustomerDoesntRentCarAndCarHavenTRented_shouldReturnRentCarDto(){
        RentACarRequest request = new RentACarRequest(LocalDateTime.of(2023,12,12,12,24,10),1,2);
        Customer customer = new Customer(1,"Ahmet","Dayı","a@gmail.com","Ahmet.26","Ahmet.26");
        Brand brand = new Brand(1,"a8");
        List<Color> colors = List.of(new Color(1,"black"),
                new Color(2,"blue"));
        Car car = new Car(2,"bmw",1234L,"2001",brand,colors);
        BrandDto brandDto = new BrandDto(brand.getBrandId(),brand.getBrandName());
        List<ColorDto> colorDtos = Arrays.asList(new ColorDto(1,"black"),new ColorDto(2,"blue"));
        CarDto carDto = new CarDto
                (
                        1,
                        car.getCarName(),
                        car.getDailyPrice(),
                        car.getProductYear(),
                        brandDto,
                        colorDtos
                );
        CustomerDto customerDto = new CustomerDto
                (
                        1,
                        customer.getFirstName(),
                        customer.getLastName(),
                        customer.getEmail()
                );
        Rental rental = new Rental(LocalDateTime.of(2023,12,12,12,24,10),customer,car);
        Rental saveRental = new Rental(1,LocalDateTime.of(2023,12,12,12,24,10),customer,car);

        List<Rental> rentals = List.of(
                new Rental(
                        3,
                        LocalDateTime.of(2023,12,12,12,24,10),
                        new Customer(2,"ali","genc","AliGenc.26","AliGenc.26"),
                        new Car(3,"audi",brand))
        );

        RentCarDto expected = new RentCarDto(1,getLocalDateTime(),LocalDateTime.of(2023,12,12,12,24,10),customerDto,carDto);


        when(customerManager.getCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);
        when(carManager.findCarByCarId(car.getCarId())).thenReturn(car);
        when(rentalDao.findAll()).thenReturn(rentals);
        when(rentalDao.save(rental)).thenReturn(saveRental);
        when(carDtoConverter.convert(saveRental)).thenReturn(expected);

        RentCarDto result = rentalManager.rentACar(request);

        assertEquals(expected,result);

        verify(customerManager).getCustomerByCustomerId(customer.getCustomerId());
        verify(carManager).findCarByCarId(car.getCarId());
        verify(rentalDao).findAll();
        verify(rentalDao).save(rental);
        verify(carDtoConverter).convert(saveRental);

    }

}