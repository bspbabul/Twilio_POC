package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.config.TwilioConfig;
import com.example.entity.OtpVerification;
import com.example.model.Otpstatus;
import com.example.model.PasswordResetRequest;
import com.example.model.PasswordResetRespose;
import com.example.repository.OtpVerificationRepository;
import com.twilio.type.PhoneNumber;
import com.twilio.rest.api.v2010.account.Message;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class TwilioService {

	@Autowired
	private TwilioConfig twilioConfig;

	@Autowired
	private OtpVerificationRepository otpVerificationRepository;

	public PasswordResetRespose setOTPforPassword(PasswordResetRequest passwordResetRequest) {
		PasswordResetRespose passwordResetResponse = null;
		try {
			PhoneNumber to = new PhoneNumber(passwordResetRequest.getPhonenumber());
			PhoneNumber from = new PhoneNumber(twilioConfig.getTrial_number());

			String otp = generateOTP();
			String otpMessage = "Dear customer, Your OTP is ## " + otp
					+ " Use this otp for further transaction. ThankYou";
			Message.creator(to, from, otpMessage).create();

			passwordResetRequest.setOtp(otp);
			passwordResetRequest.setOtpExpirationTime(LocalDateTime.now().plusSeconds(60));

			OtpVerification otpVerification = new OtpVerification();
			otpVerification.setPhonenumber(passwordResetRequest.getPhonenumber());
			otpVerification.setUsername(passwordResetRequest.getUsername());
			otpVerification.setOtp(otp);
			otpVerification.setOtpExpirationTime(passwordResetRequest.getOtpExpirationTime());
			otpVerificationRepository.save(otpVerification);

			passwordResetResponse = new PasswordResetRespose(Otpstatus.DELIVERED, otpMessage);
		} catch (Exception e) {
			passwordResetResponse = new PasswordResetRespose(Otpstatus.FAILED, e.getMessage());
		}
		return passwordResetResponse;
	}

	public String validateOtp(String userInputOtp, String phonenumber) {
		OtpVerification otpVerification = otpVerificationRepository.findByphonenumberAndOtp(phonenumber, userInputOtp);
		System.out.println(otpVerification);
		if (otpVerification != null) {
			LocalDateTime currentDateTime = LocalDateTime.now();
			LocalDateTime otpExpirationTime = otpVerification.getOtpExpirationTime();

			if (otpExpirationTime.isAfter(currentDateTime)) {

				otpVerificationRepository.delete(otpVerification);
				return "Valid OTP. Please proceed with the Transaction!";
			} else {
				System.out.println("ifif");
				throw new IllegalArgumentException("Invalid OTP or OTP has expired. Please retry!");
				
			}
		} else {
			System.out.println("if");

			throw new IllegalArgumentException("Invalid OTP or OTP has expired. Please retry!");
		}
	}

	private String generateOTP() {
		return new DecimalFormat("000000").format(new Random().nextInt(999999));
	}
}
