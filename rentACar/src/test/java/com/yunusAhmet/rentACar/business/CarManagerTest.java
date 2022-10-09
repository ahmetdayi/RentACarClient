package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.dataAccess.CarDao;
import com.yunusAhmet.rentACar.dto.*;
import com.yunusAhmet.rentACar.dto.converter.CarDtoConverter;
import com.yunusAhmet.rentACar.entity.Brand;
import com.yunusAhmet.rentACar.entity.Car;
import com.yunusAhmet.rentACar.entity.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class CarManagerTest {

    private CarDao carDao;
    private BrandManager brandManager;
    private ColorManager colorManager;

    private CarDtoConverter carDtoConverter;

    private CarManager carManager;
    @BeforeEach
    public void setUp() {
        carDao = mock(CarDao.class);
        brandManager = mock(BrandManager.class);
        colorManager = mock(ColorManager.class);
        carDtoConverter = mock(CarDtoConverter.class);

        carManager = new CarManager(carDao,brandManager,colorManager,carDtoConverter);


    }
    @Test
    public void testCreateCar_shouldReturnCarDto(){

        CreateCarRequest request = new CreateCarRequest
                (
                        "bmw",
                        1234L,
                        "2001",
                        1,
                        List.of(1,2)
                );

        Brand brand = new Brand(request.getBrandId(),"a8");
        List<Color> colors = Arrays.asList(new Color(request.getColorId().get(0),"black"),new Color(request.getColorId().get(1),"blue"));
        BrandDto brandDto = new BrandDto(brand.getBrandId(),brand.getBrandName());
        List<ColorDto> colorDtos = Arrays.asList(new ColorDto(1,"black"),new ColorDto(2,"blue"));
        List<Integer> colorIds = List.of(1,2);

        Car car= new Car
                (
                        request.getCarName(),
                        request.getDailyPrice(),
                        request.getProductYear(),
                        brand,
                        colors
                );
        Car saveCar = new Car
                (
                        1,
                        request.getCarName(),
                        request.getDailyPrice(),
                        request.getProductYear(),
                        brand,
                        colors
                );

        CarDto expected = new CarDto
                (
                        1,
                        request.getCarName(),
                        request.getDailyPrice(),
                        request.getProductYear(),
                        brandDto,
                        colorDtos
                );


        when(brandManager.getBrandByBrandId(brand.getBrandId())).thenReturn(brand);

        when(colorManager.getColorByColorId(colorIds.get(0))).thenReturn(colors.get(0));
        when(colorManager.getColorByColorId(colorIds.get(1))).thenReturn(colors.get(1));
        when(carDao.save(car)).thenReturn(saveCar);
        when(carDtoConverter.convert(saveCar)).thenReturn(expected);



        CarDto result= carManager.createCar(request);

        assertEquals(expected,result);
        verify(brandManager).getBrandByBrandId(brand.getBrandId());
        verify(colorManager).getColorByColorId(colorIds.get(0));
        verify(colorManager).getColorByColorId(colorIds.get(1));
        verify(carDao).save(car);
        verify(carDtoConverter).convert(saveCar);
//          BrandDto brandDto=modelMapper.map(brand, BrandDto.class);

    }


}