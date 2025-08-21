package com.biro.vouchertoolsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "vouchers")
public class Voucher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "batch_id", nullable = false)
    private VoucherBatch batch;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VoucherStatus voucherStatus;

    @Column(name = "expiry_date")
    private Date expiryDate;

    @CreatedDate
    @Column(name = "created_at", insertable = false, updatable = false)
    private Date createdAt;

    @LastModifiedDate
    @Column(name = "updated_at" , insertable = false)
    private Date updatedAt;

}
