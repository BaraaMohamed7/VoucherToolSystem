package com.biro.vouchertoolsystem.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "ops_logs")
public class OperationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String operation;
    private String method;
    @Column(name = "user_id")
    private Long userId;
    private String request;
    @Column(name = "request_time")
    private Date requestTime;
    private String response;
    @Column(name = "response_time")
    private Date responseTime;
    private int status;
}
