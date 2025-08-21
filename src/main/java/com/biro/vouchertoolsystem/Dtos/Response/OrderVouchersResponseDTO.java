package com.biro.vouchertoolsystem.Dtos.Response;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderVouchersResponseDTO {
    private String code;
    private Date expiryDate;
}
