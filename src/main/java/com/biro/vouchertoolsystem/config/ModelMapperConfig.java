package com.biro.vouchertoolsystem.config;

import com.biro.vouchertoolsystem.Dtos.Request.OfferRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.OfferProductDTO;
import com.biro.vouchertoolsystem.Dtos.Response.ProductResponseDTO;
import com.biro.vouchertoolsystem.model.Offer;
import com.biro.vouchertoolsystem.model.Product;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        PropertyMap<OfferRequestDTO, Offer> offerRequestMap = new PropertyMap<>() {
            protected void configure() {
                skip(destination.getMerchant());
                skip(destination.getOfferCategories());

            }
        };

        modelMapper.addMappings(offerRequestMap);
        return modelMapper;
    }

    private int calculateDaysRemaining(Date endDate){
        if (endDate == null){
            return 0;
        }

        LocalDate today = LocalDate.now(ZoneId.systemDefault());
        LocalDate offerEndDate = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        if (today.isBefore(offerEndDate)){
            return 0;
        }
        return (int) ChronoUnit.DAYS.between(today, offerEndDate);
    }

}
