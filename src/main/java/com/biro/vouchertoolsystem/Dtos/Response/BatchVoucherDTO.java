package com.biro.vouchertoolsystem.Dtos.Response;

import com.biro.vouchertoolsystem.model.VoucherStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter

public class BatchVoucherDTO {
    private VoucherStatus  status;
    private String code;
    private Date expiryDate;
}
