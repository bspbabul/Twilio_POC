package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.PasswordResetRequest;
import com.example.model.PasswordResetRespose;
import com.example.services.TwilioService;


@RestController
public class TwilioOtpController {

	@Autowired
	private TwilioService service;

	@PostMapping("/router/sendOTP")
	public PasswordResetRespose sendOTP(@RequestBody PasswordResetRequest passwordResetRequest) {
		return service.setOTPforPassword(passwordResetRequest);
	}

	@PostMapping("/router/validateOTP")
	public String validateOTP(@RequestBody PasswordResetRequest passwordResetRequest) {
		return service.validateOtp(passwordResetRequest.getOtp(), passwordResetRequest.getPhonenumber());
	}
}
