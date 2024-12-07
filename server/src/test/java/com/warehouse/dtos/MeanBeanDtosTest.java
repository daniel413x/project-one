package com.warehouse.dtos;

import org.junit.jupiter.api.Test;
import org.meanbean.test.BeanVerifier;

import com.warehouse.dtos.car.CarDto;
import com.warehouse.dtos.color.ColorDto;
import com.warehouse.dtos.make.MakeDto;
import com.warehouse.dtos.model.ModelDto;
import com.warehouse.dtos.owner.OwnerDto;

public class MeanBeanDtosTest {

    @Test
    public void testDtosGettersAndSetters() {
        // test getters and setters
        BeanVerifier.verifyBeans(
            CarDto.class,
            ColorDto.class,
            MakeDto.class,
            ModelDto.class,
            OwnerDto.class
        );
    }
}