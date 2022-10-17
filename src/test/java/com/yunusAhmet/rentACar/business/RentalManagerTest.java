package com.yunusAhmet.rentACar.business;


import com.yunusAhmet.rentACar.core.exception.CarNotDeliverException;
import com.yunusAhmet.rentACar.core.exception.CustomerAlreadyRentACar;
import com.yunusAhmet.rentACar.core.exception.WrongReturnDateException;
import com.yunusAhmet.rentACar.dataAccess.RentalDao;
import com.yunusAhmet.rentACar.dto.*;
import com.yunusAhmet.rentACar.dto.converter.RentCarDtoConverter;
import com.yunusAhmet.rentACar.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RentalManagerTest {

    private RentalManager rentalManager;
    private RentalDao rentalDao;

    private CustomerManager customerManager;

    private CarManager carManager;

    private RentCarDtoConverter carDtoConverter;


    @BeforeEach
    public void setUp() {
        customerManager = mock(CustomerManager.class);
        carManager = mock(CarManager.class);
        rentalDao = mock(RentalDao.class);
        carDtoConverter = mock(RentCarDtoConverter.class);

        rentalManager = new RentalManager(rentalDao,customerManager,carManager,carDtoConverter);


    }

    @Test
    public void testRentACar_whenDateIsCorrectAndCustomerDoesntRentCarAndCarHavenTRented_shouldReturnRentCarDto(){
        RentACarRequest request = new RentACarRequest(LocalDateTime.of(2023,12,12,12,24,10),1,2);

        Customer customer = new Customer(1,"Ahmet","Dayi","a@gmail.com","Ahmet.26","Ahmet.26");
        Brand brand = new Brand(1,"a8");
        List<Color> colors = List.of(new Color(1,"black"),
                new Color(2,"blue"));
        Car car = new Car(2,"bmw",1234L,"2001",brand,colors);
        BrandDto brandDto = new BrandDto(brand.getBrandId(),brand.getBrandName());
        List<ColorDto> colorDtos = List.of(new ColorDto(1,"black"),new ColorDto(2,"blue"));
        CarDto carDto = new CarDto
                (
                        2,
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
        Rental rental = new Rental(LocalDateTime.now().withNano(0),LocalDateTime.of(2023,12,12,12,24,10),customer,car);
        Rental saveRental = new Rental(1,rental.getRentDate(),rental.getReturnDate(),customer,car);

        List<Rental> rentals = List.of(
                new Rental(
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.of(2023,12,12,12,24,30),
                        new Customer(2,"ali","genc","AliGenc.26","AliGenc.26"),
                        new Car(3,"audi",brand))
        );

        RentCarDto expected = new RentCarDto(saveRental.getRentalId(),rental.getRentDate(),rental.getReturnDate(),customerDto,carDto);

        when(carManager.findCarByCarId(car.getCarId())).thenReturn(car);
        when(customerManager.getCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);
        when(rentalDao.findAll()).thenReturn(rentals);
        when(rentalDao.save(rental)).thenReturn(saveRental);
        when(carDtoConverter.convert(saveRental)).thenReturn(expected);

        RentCarDto result = rentalManager.rentACar(request);

        assertEquals(expected,result);
        verify(carManager).findCarByCarId(car.getCarId());
        verify(customerManager).getCustomerByCustomerId(customer.getCustomerId());
        verify(rentalDao).findAll();
        verify(rentalDao).save(rental);
        verify(carDtoConverter).convert(saveRental);

    }

    @Test
    public void testRentACar_whenRentDateBeforeReturnDateAndCustomerDoesntRentCarAndCarHavenTRented_shouldThrowException(){
        RentACarRequest request = new RentACarRequest(LocalDateTime.of(2021,12,12,12,24,10),1,2);

        Customer customer = new Customer(1,"Ahmet","Dayi","a@gmail.com","Ahmet.26","Ahmet.26");
        Brand brand = new Brand(1,"a8");
        List<Color> colors = List.of(new Color(1,"black"),
                new Color(2,"blue"));
        Car car = new Car(2,"bmw",1234L,"2001",brand,colors);

        when(carManager.findCarByCarId(car.getCarId())).thenReturn(car);
        when(customerManager.getCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);


        assertThrows(WrongReturnDateException.class,()->rentalManager.rentACar(request));

        verify(carManager).findCarByCarId(car.getCarId());
        verify(customerManager).getCustomerByCustomerId(customer.getCustomerId());


    }

    @Test
    public void testRentACar_whenDateIsCorrectAndCustomerAlreadyRentCarAndCarHavenTRented_shouldThrowException(){

        RentACarRequest request = new RentACarRequest(LocalDateTime.of(2023,12,12,12,24,10),1,2);

        Customer customer = new Customer(1,"Ahmet","Dayi","a@gmail.com","Ahmet.26","Ahmet.26");
        Brand brand = new Brand(1,"a8");
        List<Color> colors = List.of(new Color(1,"black"),
                new Color(2,"blue"));
        Car car = new Car(2,"bmw",1234L,"2001",brand,colors);
        List<Rental> rentals = List.of(
                new Rental(
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.of(2023,12,12,12,24,30),
                        new Customer(1,"ali","genc","AliGenc.26","AliGenc.26"),
                        new Car(3,"audi",brand))
        );


        when(carManager.findCarByCarId(car.getCarId())).thenReturn(car);
        when(customerManager.getCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);
        when(rentalDao.findAll()).thenReturn(rentals);


        assertThrows(CustomerAlreadyRentACar.class,()->rentalManager.rentACar(request));

        verify(carManager).findCarByCarId(car.getCarId());
        verify(customerManager).getCustomerByCustomerId(customer.getCustomerId());
        verify(rentalDao).findAll();


    }

    @Test
    public void testRentACar_whenDateIsCorrectAndCustomerDoesntRentCarAndCarHavenTRented_shouldThrowException(){
        RentACarRequest request = new RentACarRequest(LocalDateTime.of(2023,12,12,12,24,10),1,2);

        Customer customer = new Customer(1,"Ahmet","Dayi","a@gmail.com","Ahmet.26","Ahmet.26");
        Brand brand = new Brand(1,"a8");
        List<Color> colors = List.of(new Color(1,"black"),
                new Color(2,"blue"));
        Car car = new Car(2,"bmw",1234L,"2001",brand,colors);
        List<Rental> rentals = List.of(
                new Rental(
                        3,
                        LocalDateTime.now(),
                        LocalDateTime.of(2023,12,12,12,24,30),
                        new Customer(2,"ali","genc","AliGenc.26","AliGenc.26"),
                        new Car(2,"audi",brand))
        );


        when(carManager.findCarByCarId(car.getCarId())).thenReturn(car);
        when(customerManager.getCustomerByCustomerId(customer.getCustomerId())).thenReturn(customer);
        when(rentalDao.findAll()).thenReturn(rentals);


        assertThrows(CarNotDeliverException.class,()->rentalManager.rentACar(request));

        verify(carManager).findCarByCarId(car.getCarId());
        verify(customerManager).getCustomerByCustomerId(customer.getCustomerId());
        verify(rentalDao).findAll();
    }

}
