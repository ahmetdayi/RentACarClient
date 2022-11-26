package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.exception.BrandAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.dataAccess.BrandDao;
import com.yunusAhmet.rentACar.dto.*;

import com.yunusAhmet.rentACar.dto.converter.BrandDtoConverter;
import com.yunusAhmet.rentACar.entity.Brand;

import org.springframework.stereotype.Service;


import java.util.Optional;


@Service
public class BrandManager {

    private final BrandDao brandDao;
    private final BrandDtoConverter brandDtoConverter;


    public BrandManager(
            BrandDao brandDao,
            BrandDtoConverter brandDtoConverter
            ) {
        this.brandDao = brandDao;
        this.brandDtoConverter = brandDtoConverter;


    }

    protected Brand getBrandByBrandId(int brandId){
       return brandDao.findById(brandId).orElseThrow(() -> new BrandNotFoundException(Constant.BRAND_NOT_FOUND));
    }
    public BrandDto createBrand(CreateBrandRequest request){
        Optional<Brand> brand = brandDao.findBrandByBrandName(request.getBrandName());
        if (brand.isPresent()) {
            throw new BrandAlreadyExistException(Constant.BRAND_ALREADY_EXIST);
        }else {
            Brand brand1 = new Brand(request.getBrandName());
            return brandDtoConverter.convert(brandDao.save(brand1));
        }

    }

    public void deleteBrandByBrandId(int brandId){
        brandDao.deleteById(getBrandByBrandId(brandId).getBrandId());
    }

    public BrandDto updateBrand(UpdateBrandRequest request){
        Optional<Brand> brand = brandDao.findBrandByBrandName(request.getBrandName());
        if (brand.isPresent()) {
            throw new BrandAlreadyExistException(Constant.BRAND_ALREADY_EXIST);
        }
        Brand brand1 = getBrandByBrandId(request.getBrandId());
        brand1.setBrandName(request.getBrandName());
        return brandDtoConverter.convert(brandDao.save(brand1));
    }





}
