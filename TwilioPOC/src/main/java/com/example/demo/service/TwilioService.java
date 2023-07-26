package com.example.demo.service;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.config.TwilioConfig;
import com.example.demo.model.Otpstatus;
import com.example.demo.model.PasswordResetRequest;
import com.example.demo.model.PasswordResetRespose;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import reactor.core.publisher.Mono;

@Service
public class TwilioService {

	@Autowired
	private TwilioConfig twilioConfig;
	
	Map<String, String> otpMap=new HashMap<>();

	public Mono<PasswordResetRespose> setOTPforPassword(PasswordResetRequest passwordResetRequest) {

		PasswordResetRespose passwordResetRespose=null;
		try {
			PhoneNumber to = new PhoneNumber(passwordResetRequest.getPhonenumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());
			
			String otp=generateOTP();
			String otpmessage="Dear customer, Your OTP is ##"+otp+ " Use this otp for further transaction. ThankYou";
		    Message.creator(to, from, otpmessage).create();
			
			otpMap.put(passwordResetRequest.getUsername(), otp);
			passwordResetRespose=new PasswordResetRespose(Otpstatus.DELIVERED,otpmessage);
		} catch (Exception e) {
			passwordResetRespose=new PasswordResetRespose(Otpstatus.FAILED,e.getMessage());

		}
		return Mono.just(passwordResetRespose);
		
	}
	
	public Mono<String> validateOtp(String userInputOtp,String userName){
		if (userInputOtp.equals(otpMap.get(userName))) {
			return Mono.just("Valid OTP please proced with the Transaction !");
		} else {
			return Mono.error(new IllegalArgumentException("Invalid otp please retry !"));
		}
	}

	private String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));

	}
}
