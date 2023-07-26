package com.example.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PasswordResetRequest {
	
	private String phonenumber;
	private String username;
	private String otp;
	private LocalDateTime otpExpirationTime;

}
