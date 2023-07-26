package com.example.demo.model;

import lombok.Data;

@Data
public class PasswordResetRequest {
	
	private String phonenumber;
	private String username;
	private String otp;

}
