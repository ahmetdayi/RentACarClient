package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.BrandAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.dataAccess.BrandDao;
import com.yunusAhmet.rentACar.dto.*;
import com.yunusAhmet.rentACar.entity.Brand;
import com.yunusAhmet.rentACar.entity.Car;
import com.yunusAhmet.rentACar.entity.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BrandManagerTest {

    private BrandDao brandDao;
    private ModelMapper modelMapper;
    private BrandManager brandManager;
    @BeforeEach
    void setUp() {
        brandDao = mock(BrandDao.class);
        modelMapper = mock(ModelMapper.class);
        brandManager = new BrandManager(brandDao,modelMapper);
    }
            @Test
            public void testCreateBrand_whenMustNotFindSameBrandInDataBase_shouldReturnBrandDto(){

            CreateBrandRequest request =new CreateBrandRequest("a8");

            Brand brand= new Brand(0,"a8");
            BrandDto brandDto = new BrandDto(0,"a8");


            when(brandDao.save(brand)).thenReturn(brand);
            when(modelMapper.map(brand,BrandDto.class)).thenReturn(brandDto);


           BrandDto result= brandManager.createBrand(request);

            assertEquals(brandDto,result);
            verify(brandDao).save(brand);
            verify(modelMapper).map(brand, BrandDto.class);
//          BrandDto brandDto=modelMapper.map(brand, BrandDto.class);

    }

    @Test
    public void testCreateBrand_whenBrandNameAlreadyExists_shouldReturnException() {
        CreateBrandRequest request = new CreateBrandRequest("a8");
        Brand brand= new Brand(0,"a8");
        when(brandDao.findBrandByBrandName(brand.getBrandName())).
                thenThrow(new BrandAlreadyExistException(Constant.BRAND_ALREADY_EXIST));

       assertThrows( BrandAlreadyExistException.class,() -> brandManager.createBrand(request) );
        verify(brandDao).findBrandByBrandName(request.getBrandName());


    }
    @Test
    public void testDeleteAndUpdateBrand_whenBrandIdDoesntExist_shouldReturnException(){

        when(brandDao.findById(1)).thenThrow(new BrandNotFoundException(Constant.BRAND_NOT_FOUND));
        assertThrows(BrandNotFoundException.class,()-> brandManager.getBrandByBrandId(1));
    }

    @Test
    public void testUpdateBrand_whenBrandIdExists_shouldReturnBrandDto(){
        UpdateBrandRequest request = new UpdateBrandRequest(0,"a1");
        Brand brand = new Brand(0,"a2");
        BrandDto brandDto = new BrandDto(0,"a2");
        when(brandDao.findBrandByBrandName(brand.getBrandName())).thenReturn(Optional.of(brand));

        when(brandDao.findById(0)).thenReturn(Optional.of(brand));
        when(brandDao.save(brand)).thenReturn(brand);
        when(modelMapper.map(brand,BrandDto.class)).thenReturn(brandDto);

        BrandDto result = brandManager.updateBrand(request);

        assertEquals(brandDto,result);
    }

    @Test
    public void testUpdateBrand_whenBrandNameAlreadyExists_shouldReturnException() {
        UpdateBrandRequest request = new UpdateBrandRequest(0,"a8");
        Brand brand= new Brand(0,"a8");
        when(brandDao.findBrandByBrandName(brand.getBrandName())).
                thenThrow(new BrandAlreadyExistException(Constant.BRAND_ALREADY_EXIST));

        assertThrows( BrandAlreadyExistException.class,() -> brandManager.updateBrand(request));

        verify(brandDao).findBrandByBrandName(request.getBrandName());}

    @Test
    public void testGetAllCar_whenGiveBrandIdExists_shouldReturnListOfCar(){
        int brandId=2;
        BrandCarDto brandCarDto = new BrandCarDto(1, "bmw");

        List<BrandCarDto> brandCarDtos = List.of(brandCarDto);
        Car car = new Car(1, "bmw",new Brand(2,"a8"));
        Brand brand= new Brand(2,"a8",List.of(car));
        List<Car> cars = List.copyOf(List.of(car));

        when(brandDao.findById(brandId)).thenReturn(Optional.of(brand));
       when(modelMapper.map(ArgumentMatchers.any(),ArgumentMatchers.any())).thenReturn(brandCarDto);
        List<BrandCarDto> result = brandManager.getAllCarByBrandId(brandId);
        assertEquals(brandCarDtos,result);

        verify(brandDao).findById(brandId);
        verify(modelMapper).map(car,BrandCarDto.class);




    }
}