package com.biro.vouchertoolsystem.controller;

import com.biro.vouchertoolsystem.Dtos.Request.BatchRequestDTO;
import com.biro.vouchertoolsystem.Dtos.Response.BatchVoucherDTO;
import com.biro.vouchertoolsystem.service.VoucherBatchService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/batches")
public class VoucherBatchController {
    private  final VoucherBatchService voucherBatchService;

    public VoucherBatchController(VoucherBatchService voucherBatchService) {
        this.voucherBatchService = voucherBatchService;
    }

    @PostMapping("/new")
    public List<BatchVoucherDTO> insertBatch(@RequestBody BatchRequestDTO batchRequestDTO){
        return voucherBatchService.insertNewBatch(batchRequestDTO);
    }
}
