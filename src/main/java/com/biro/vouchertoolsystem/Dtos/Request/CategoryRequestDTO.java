package com.biro.vouchertoolsystem.Dtos.Request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryRequestDTO {
    String nameArabic;
    String nameEnglish;
    String imageUrl;
    Boolean isActive;
}
