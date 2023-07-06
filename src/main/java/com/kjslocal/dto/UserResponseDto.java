package com.kjslocal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
	
	@JsonProperty("userId")
	private int id;
	
	@JsonProperty("firstName")
	private String fname;

	@JsonProperty("lastName")
	private String lname;

	@JsonProperty("email")
	private String email;

}
