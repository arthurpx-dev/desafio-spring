package com.example.desafio_spring.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccessTokenBB {

	private String access_token;
	private String token_type;
	private Integer expires_in;

}
