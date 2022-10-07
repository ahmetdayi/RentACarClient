package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.BrandAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.dataAccess.BrandDao;
import com.yunusAhmet.rentACar.dto.BrandDto;
import com.yunusAhmet.rentACar.dto.CreateBrandRequest;
import com.yunusAhmet.rentACar.dto.UpdateBrandRequest;
import com.yunusAhmet.rentACar.dto.UpdateCustomerRequest;
import com.yunusAhmet.rentACar.entity.Brand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

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

}