package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.OtpVerification;

public interface OtpVerificationRepository extends JpaRepository<OtpVerification, Long>{
	
    OtpVerification findByphonenumberAndOtp(String phonenumber, String otp);


}
