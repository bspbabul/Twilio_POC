package com.example.demo.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.example.demo.model.PasswordResetRequest;
import com.example.demo.service.TwilioService;

import reactor.core.publisher.Mono;

@Component
public class TwilioOtpHandler {

	@Autowired
	private TwilioService service;

	public Mono<ServerResponse> sendOTP(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(PasswordResetRequest.class).flatMap(dto -> service.setOTPforPassword(dto))
				.flatMap(dto -> ServerResponse.status(HttpStatus.OK).body(BodyInserters.fromValue(dto)));
	}

	public Mono<ServerResponse> validateOTP(ServerRequest serverRequest) {
		return serverRequest.bodyToMono(PasswordResetRequest.class)
				.flatMap(dto->service.validateOtp(dto.getOtp(), dto.getUsername()))
				.flatMap(dto -> ServerResponse.status(HttpStatus.OK)
				.bodyValue(dto));
				
	}
}
