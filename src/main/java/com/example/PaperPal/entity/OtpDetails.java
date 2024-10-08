package com.example.PaperPal.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class OtpDetails {

    String otpId;
    String otp;
    Date sendTime;
    Date expiryTime;

}
