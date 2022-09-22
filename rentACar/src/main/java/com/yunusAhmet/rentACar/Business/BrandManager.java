package com.yunusAhmet.rentACar.Business;

import com.yunusAhmet.rentACar.Core.Exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.Core.Exception.Constant.Constant;
import com.yunusAhmet.rentACar.DataAccess.BrandDao;
import com.yunusAhmet.rentACar.Dto.BrandCarDto;
import com.yunusAhmet.rentACar.Dto.BrandDto;
import com.yunusAhmet.rentACar.Dto.CarBrandDtoForBrandCarDto;
import com.yunusAhmet.rentACar.Dto.CarDto;
import com.yunusAhmet.rentACar.Entity.Brand;
import com.yunusAhmet.rentACar.Entity.Car;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager {

    private final BrandDao brandDao;
    private final ModelMapper modelMapper;


    public BrandManager(BrandDao brandDao, ModelMapper modelMapper) {
        this.brandDao = brandDao;
        this.modelMapper = modelMapper;
    }

    protected Brand getBrandByBrandId(int brandId){
       return brandDao.findById(brandId).orElseThrow(() -> new BrandNotFoundException(Constant.BRAND_NOT_FOUND));
    }

    protected List<Brand> findAllBrand(){
        return  brandDao.findAll();
    }
    public List<CarBrandDtoForBrandCarDto> getAllCarByBrandId(int brandId) {
      Brand brand =getBrandByBrandId(brandId);

      List<Car> cars= brand.getCar();

      List<CarBrandDtoForBrandCarDto> dtos= cars
              .stream()
              .map(car -> modelMapper
                      .map(car, CarBrandDtoForBrandCarDto.class)).toList();
        return dtos;
    }



}
