package com.yunusAhmet.rentACar.Business;

import com.yunusAhmet.rentACar.Core.Exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.Core.Exception.Constant.Constant;
import com.yunusAhmet.rentACar.DataAccess.BrandDao;
import com.yunusAhmet.rentACar.Entity.Brand;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
public class BrandManager {

    private final BrandDao brandDao;


    public BrandManager(BrandDao brandDao) {
        this.brandDao = brandDao;
    }

    protected Brand getBrandByBrandId(int brandId){
       return brandDao.findById(brandId).orElseThrow(() -> new BrandNotFoundException(Constant.BRAND_NOT_FOUND));
    }



}
