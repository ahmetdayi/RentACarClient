package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.CarHasNotImageException;
import com.yunusAhmet.rentACar.core.exception.CarNotFoundException;
import com.yunusAhmet.rentACar.core.exception.ImageNotFoundException;
import com.yunusAhmet.rentACar.core.exception.MaxImageException;
import com.yunusAhmet.rentACar.dataAccess.ImageDao;
import com.yunusAhmet.rentACar.dto.ImageDto;
import com.yunusAhmet.rentACar.dto.converter.ImageConverter;
import com.yunusAhmet.rentACar.entity.Car;
import com.yunusAhmet.rentACar.entity.Image;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;



@Service
public class ImageManager  {

    private final ImageDao imageDao;

    private final CarManager carManager;

    private final ImageConverter imageConverter;
    private final CloudStorageManager cloudStorageManager;



    public ImageManager
            (
                    ImageDao imageDao ,
             CarManager carManager,
             CloudStorageManager cloudStorageManager,
             ImageConverter imageConverter
            )
    {

        this.imageDao = imageDao;
        this.carManager = carManager;
        this.cloudStorageManager = cloudStorageManager;

        this.imageConverter = imageConverter;
    }

    protected Image getImageByImageId(int imageId){
        return imageDao.findById(imageId).orElseThrow(() -> new ImageNotFoundException(Constant.IMAGE_NOT_FOUND));
    }

    protected void maxImageControl(int carId){
        List<Image> images =
                imageDao.getImagesByCar_CarId(carId).orElseThrow(
                        () -> new CarNotFoundException(Constant.CAR_NOT_FOUND));
        if(images.size()==6){
            throw new MaxImageException(Constant.MAX_IMAGE_LESS_THAN_SIX);
        }
        if(images.size()==0){
            throw new CarHasNotImageException(Constant.CAR_HAS_NOT_IMAGE);
        }
    }

    public ImageDto addImage(MultipartFile multipartFile, int carId) {
        maxImageControl(carId);
        Map<?,?> upload = cloudStorageManager.upload(multipartFile);
        Car car =carManager.findCarByCarId(carId);
        Image image = new Image(upload.get("url").toString(),car);
        return imageConverter.converter(imageDao.save(image));
    }

    public void deleteImageByImageId(int imageId){
        imageDao.deleteById(getImageByImageId(imageId).getId());
    }

}