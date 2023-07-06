package com.kjslocal.dto;

import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequestDto {

	@JsonProperty("firstName")
	@NotBlank(message = "firstName is required.")
	@Length(max = 150, message = "firstName max length is 150.")
	private String fname;

	@JsonProperty("lastName")
	@NotBlank(message = "lastName is required.")
	@Length(max = 150, message = "lastName max length is 150.")
	private String lname;

	@JsonProperty("email")
	@NotBlank(message = "email is required.")
	@Length(max = 50, message = "email max length is 50.")
	private String email;

}
