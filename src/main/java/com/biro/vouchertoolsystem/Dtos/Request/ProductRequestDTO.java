package com.biro.vouchertoolsystem.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequestDTO {
    private String nameEnglish;
    private String nameArabic;
    private String descriptionEnglish;
    private String descriptionArabic;
    private String imageUrl;
    private Double priceBefore;
    private Double priceAfter;
}
