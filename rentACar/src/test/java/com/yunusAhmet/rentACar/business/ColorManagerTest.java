package com.yunusAhmet.rentACar.business;

import com.yunusAhmet.rentACar.core.constant.Constant;
import com.yunusAhmet.rentACar.core.exception.BrandNotFoundException;
import com.yunusAhmet.rentACar.core.exception.ColorAlreadyExistException;
import com.yunusAhmet.rentACar.core.exception.ColorNotFoundException;
import com.yunusAhmet.rentACar.dataAccess.ColorDao;
import com.yunusAhmet.rentACar.dto.ColorDto;
import com.yunusAhmet.rentACar.dto.CreateColorRequest;
import com.yunusAhmet.rentACar.dto.UpdateColorRequest;
import com.yunusAhmet.rentACar.dto.converter.ColorDtoConverter;
import com.yunusAhmet.rentACar.entity.Brand;
import com.yunusAhmet.rentACar.entity.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ColorManagerTest {

    private  ColorDao colorDao;
    private  ColorDtoConverter converter;

    private ColorManager colorManager;

    @BeforeEach
    public void setUp() {
        colorDao = mock(ColorDao.class);
        converter = mock(ColorDtoConverter.class);

        colorManager = new ColorManager(colorDao,converter);
    }

    @Test
    public void testCreateColor_whenColorNameIsNotSame_shouldReturnColorDto(){
        CreateColorRequest request = new CreateColorRequest("blue");
        Color color = new Color(request.getColorName());
        Color saveColor = new Color(1,request.getColorName());
        ColorDto expected = new ColorDto(1,request.getColorName());

        when(colorDao.save(color)).thenReturn(saveColor);
        when(converter.convert(saveColor)).thenReturn(expected);

        ColorDto result = colorManager.createColor(request);

        assertEquals(expected,result);

        verify(colorDao).save(color);
        verify(converter).convert(saveColor);

    }
    @Test
    public void testCreateColor_whenColorNameAlreadyExists_shouldReturnException(){
        CreateColorRequest request = new CreateColorRequest("blue");
        Color color = new Color(1,"blue");

        when(colorDao.findColorByColorName(color.getColorName())).thenReturn(Optional.of(color));

        assertThrows(ColorAlreadyExistException.class,()->colorManager.createColor(request));

        verify(colorDao).findColorByColorName(request.getColorName());

    }
    @Test
    public void testUpdateColor_whenColorIdExists_shouldReturnColorDto(){
        UpdateColorRequest request = new UpdateColorRequest(1,"blue");
        Color color = new Color(1,"gri");
        ColorDto expected = new ColorDto(1,"gri");

        when(colorDao.findColorByColorName(color.getColorName())).thenReturn(Optional.of(color));
        when(colorDao.findById(color.getColorId())).thenReturn(Optional.of(color));
        when(colorDao.save(color)).thenReturn(color);
        when(converter.convert(color)).thenReturn(expected);
        ColorDto result = colorManager.updateColor(request);

        assertEquals(expected,result);
        verify(colorDao).findById(color.getColorId());
        verify(colorDao).save(color);
        verify(colorDao).findColorByColorName(color.getColorName());
        verify(converter).convert(color);
    }

    @Test
    public void testUpdateColor_whenColorNameAlreadyExists_shouldReturnException() {
        UpdateColorRequest request = new UpdateColorRequest(1,"blue");
        Color color= new Color(1,"blue");
        when(colorDao.findColorByColorName(color.getColorName())).
                thenReturn(Optional.of(color));

        assertThrows( ColorAlreadyExistException.class,() -> colorManager.updateColor(request));

        verify(colorDao).findColorByColorName(request.getColorName());
    }
    @Test
    public void testDeleteAndUpdateColor_whenColorIdDoesntExist_shouldReturnException(){

        when(colorDao.findById(1)).thenReturn(Optional.empty());
        assertThrows(ColorNotFoundException.class,()-> colorManager.getColorByColorId(1));
    }
    @Test
    public void testDeleteColor_whenColorIdExists_shouldDeleteColor(){
        int colorId =1;

        Color color = new Color(colorId,"black");

        when(colorDao.findById(1)).thenReturn(Optional.of(color));

        colorManager.deleteColorByColorId(color.getColorId());

        verify(colorDao).findById(colorId);
        verify(colorDao).deleteById(color.getColorId());
    }




}