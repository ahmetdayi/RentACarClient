package com.yunusAhmet.rentACar.Business;

import com.yunusAhmet.rentACar.Core.Exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.Core.Exception.ColorNotFoundException;
import com.yunusAhmet.rentACar.Core.Exception.Constant.Constant;
import com.yunusAhmet.rentACar.DataAccess.ColorDao;
import com.yunusAhmet.rentACar.Entity.Brand;
import com.yunusAhmet.rentACar.Entity.Color;
import org.springframework.stereotype.Service;

@Service
public class ColorManager {

    private final ColorDao colorDao;


    public ColorManager(ColorDao colorDao) {
        this.colorDao = colorDao;
    }
    protected Color getColorByColorId(int colorId){
        return colorDao.findById(colorId).orElseThrow(() -> new ColorNotFoundException(Constant.COLOR_NOT_FOUND));
    }

}
